package hs.http2.core;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 改写Android源码的AsyncTask，给与线程池更多的可能性
 * Created by huangshuo on 17/11/15.
 */
public abstract class HSAsyncTask<Params, Progress, Result> {

    // 原子布尔型，支持高并发访问，标识任务是否被取消
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    // 原子布尔型，支持高并发访问，标识任务是否被使用过
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

    private static final int MESSAGE_POST_RESULT = 0x1;// 消息类型：发送结果
    private static final int MESSAGE_POST_PROGRESS = 0x2;// 消息类型：更新进度
    private static final int MESSAGE_POST_FINISH = 0x3;// 消息类型：异步执行完成
    // 用来发送结果和进度通知，采用UI线程的Looper来处理消息 这就是为什么Task必须在UI线程调用
    private static final InternalHandler mHandler = new InternalHandler();

    private static OnFinishedListener finishedListener;

    /**
     * 耗时执行监听器
     *
     * @return
     */
    public OnFinishedListener getFinishedListener() {
        return finishedListener;
    }

    /**
     * 设置耗时执行监听器
     *
     * @param finishedListener
     */
    public static void setOnFinishedListener(OnFinishedListener finishedListener) {
        HSAsyncTask.finishedListener = finishedListener;
    }

    private static class InternalHandler extends Handler {
        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public void handleMessage(Message msg) {
            HSTaskResult result = (HSTaskResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
                case MESSAGE_POST_FINISH:
                    if (finishedListener != null) {
                        finishedListener.onPostExecute();
                    }
                    break;
            }
        }
    }

    // 任务的状态 默认为挂起，即等待执行，其类型标识为易变的（volatile）
    private volatile Status mStatus = Status.PENDING;

    // 任务的三种状态
    public enum Status {
        /**
         * 任务等待执行
         */
        PENDING,
        /**
         * 任务正在执行
         */
        RUNNING,
        /**
         * 任务已经执行结束
         */
        FINISHED,
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT;// 长期保持活的跃线程数。
    private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;// 线程池最大容量
    // 当前线程数大于活跃线程数时，此为终止多余的空闲线程等待新任务的最长时间
    private static final int KEEP_ALIVE = 10;

    // 静态阻塞式队列，用来存放待执行的任务，初始容量：8个
    private static final BlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue<Runnable>(8);
    // ThreadFactory，通过工厂方法newThread来获取新线程
    private static final ThreadFactory mThreadFactory = new ThreadFactory() {
        // 原子级整数，可以在超高并发下正常工作
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "KJLibrary->KJTaskExecutor #" + mCount.getAndIncrement());
        }
    };

    /********************* 两个效果不同的执行器start *******************************/
    /**
     * 并发线程池任务执行器，它实际控制并执行线程任务，与mSerialExecutor（串行）相对应<br>
     * <b> 优化：</b> 不限制并发总线程数!让任务总能得到执行,且高效执行少量(不大于活跃线程数)的异步任务。<br>
     * 线程完成任务后保持10秒销毁，这段时间内可重用以应付短时间内较大量并发，提升性能。
     * <p>
     * mThreadPoolExcutor会有个问题，线程池是一个核心数为5线程，队列可容纳10线程，最大执行128个任务，
     * 这存在一个问题，当你真的有138个并发时，即使手机没被你撑爆，那么超出这个指标应用绝对crash掉。
     * <p>
     * 这时候如果有个executor能够移除没有执行的，加上已经执行的，就可以了，这就看SmartSerialExecutor。
     */
    public static final ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, mPoolWorkQueue, mThreadFactory);

    /**
     * 串行任务执行器，其内部实现了串行控制， 循环的取出一个个任务交给上述的并发线程池去执行<br>
     * 与mThreadPoolExecutor（并行）相对应
     */
    public static final Executor mSerialExecutor = new SerialExecutor();

    /**
     * 并发量控制: 根据cpu能力控制一段时间内并发数量，并发过量大时采用Lru方式移除旧的异步任务，默认采用LIFO策略调度线程运作，
     * 开发者可选调度策略有LIFO、FIFO。
     */
    public static final Executor mLruSerialExecutor = new SmartSerialExecutor();

    // 设置默认任务执行器为并行执行
    private static volatile Executor mDefaultExecutor = mLruSerialExecutor;

    /**
     * 为KJTaskExecutor设置默认执行器
     */
    public static void setDefaultExecutor(Executor exec) {
        mDefaultExecutor = exec;
    }

    /********************* 三个效果不同的执行器end *******************************/

    private WorkerRunnable<Params, Result> mWork;
    // 待执行的任务
    private FutureTask<Result> mFuture;

    public HSAsyncTask() {

        // http请求将在这里的call方法里执行
        mWork = new WorkerRunnable<Params, Result>() {

            @Override
            public Result call() throws Exception {
                // 表示该线程已经被使用
                mTaskInvoked.set(true);
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                return postResult(doInBackground(mParams));
            }
        };

        mFuture = new FutureTask<Result>(mWork) {
            //
            @Override
            protected void done() {
                try {
                    if (!mTaskInvoked.get()) {
                        postResult(get());
                    }
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
                } catch (CancellationException e) {
                    if (!mTaskInvoked.get()) {
                        postResult(null);
                    }
                }
            }
        };
    }

    // 执行请求入口
    public final HSAsyncTask<Params, Progress, Result> execute(Params... params) {

        return executeOnExecutor(mDefaultExecutor, params);
    }

    /**
     * 提供一个静态方法，方便在外部直接执行一个runnable<br>
     * 用于瞬间大量并发的场景，比如，假设用户拖动ListView时如果需要启动大量异步线程，而拖动过去时间很久的用户已经看不到，允许任务丢失。
     */
    public static void execute(Runnable runnable) {
        mDefaultExecutor.execute(runnable);
    }

    /**
     * @param exe    执行器
     * @param params 执行参数
     * @return
     */
    private HSAsyncTask<Params, Progress, Result> executeOnExecutor(Executor exe, Params... params) {
        // 一个AsyncTask只能执行一个线程，但是executor是static，用的是同一份
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:" + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:" + " the task has already been executed "
                            + "(a task can be executed only once)");
                default:
                    break;
            }
        }

        mStatus = Status.RUNNING;

        // 在开线程工作之前的准备工作
        onPreExecute();
        mWork.mParams = params;
        // 开线程，开始http请求

        // 原理{@link #execute(Runnable runnable)}
        // 接着会有#onProgressUpdate被调用，
        // 最后会是#onPostExecute
        exe.execute(mFuture);

        return this;
    }

    /************** 宏观上HSAsyncTask的执行顺序 start ****************************/

    /**
     * 在doInBackground之前调用，用来做初始化工作 所在线程：UI线程
     */
    protected void onPreExecute() {
    }

    /**
     * 这个方法是我们必须要重写的，用来做后台计算 所在线程：后台线程
     */
    protected abstract Result doInBackground(Params... params);

    /**
     * 打印后台计算进度，onProgressUpdate会被调用<br>
     * 使用内部handle发送一个进度消息，让onProgressUpdate被调用
     */
    protected final void publishProgress(Progress... values) {
        if (!isCancelled()) {
            mHandler.obtainMessage(MESSAGE_POST_PROGRESS, new HSTaskResult<Progress>(this, values)).sendToTarget();
        }
    }

    /**
     * 在publishProgress之后调用，用来更新计算进度 所在线程：UI线程
     */
    protected void onProgressUpdate(Progress... values) {
    }

    /**
     * doInBackground执行完毕，发送消息 所在线程：后台线程
     *
     * @param result
     * @return
     */
    private Result postResult(Result result) {
        @SuppressWarnings("unchecked")
        Message message = mHandler.obtainMessage(MESSAGE_POST_RESULT, new HSTaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }

    /**
     * 在doInBackground之后调用，用来接受后台计算结果更新UI 所在线程：UI线程
     */
    protected void onPostExecute(Result result) {
    }

    /**
     * 任务结束的时候会进行判断：如果任务没有被取消，则调用onPostExecute;否则调用onCancelled
     */
    private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
            if (finishedListener != null) {
                finishedListener.onCancelled();
            }
        } else {
            onPostExecute(result);
            if (finishedListener != null) {
                finishedListener.onPostExecute();
            }
        }
        mStatus = Status.FINISHED;
    }

    /**
     * 返回任务的状态
     */
    public final Status getStatus() {
        return mStatus;
    }

    /**
     * 返回该线程是否已经被取消
     *
     * @see #cancel(boolean)
     */
    public final boolean isCancelled() {
        return mCancelled.get();
    }

    /**
     * 如果task已经执行完成，或被某些其他原因取消，再调用本方法将返回false；<br>
     * 当本task还没有启动就调用cancel(boolean),那么这个task将从来没有运行，此时会返回true。<br>
     * 如果任务已经启动，则由参数决定执行此任务是否被中断。<br>
     *
     * @param mayInterruptIfRunning <tt>true</tt> 表示取消task的执行
     * @return 如果线程不能被取消返回false, 比如它已经正常完成
     */
    public final boolean cancel(boolean mayInterruptIfRunning) {
        mCancelled.set(true);
        return mFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * 所在线程：UI线程<br>
     * doInBackground执行结束并且{@link #cancel(boolean)} 被调用。<br>
     * 如果本函数被调用则表示任务已被取消，这个时候onPostExecute不会再被调用。
     */
    protected void onCancelled(Result result) {
    }

    private static class HSTaskResult<Data> {
        final Data[] mData;
        final HSAsyncTask<?, ?, ?> mTask;

        HSTaskResult(HSAsyncTask<?, ?, ?> task, Data... data) {
            mTask = task;
            mData = data;
        }
    }

    /************** 宏观上HSAsyncTask的执行顺序 end ****************************/

    /**
     * 串行执行器的实现<br>
     * 如果采用串行执行，asyncTask.execute(Params ...)实际上会调用 SerialExecutor的execute方法。
     * {@link #executeOnExecutor}
     */
    public static class SerialExecutor implements Executor {

        // 线性双向队列，用来存储所有的AsyncTask任务
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        /**
         * 当前正在执行的AsyncTask任务
         */
        Runnable mActive = null;

        @Override
        public synchronized void execute(final Runnable r) {
            // 需要执行在线程r中
            mTasks.push(new Runnable() {

                @Override
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }

                }
            });

            if (mActive == null) {
                scheduleNext();
            }
        }

        public synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                mThreadPoolExecutor.execute(this.mActive);
            }
        }

    }

    public static class SmartSerialExecutor implements Executor {
        /**
         * 这里使用{@link ArrayDequeCompat}作为栈比{@link Stack}性能高
         * 所有的AsyncTask公用，每当某个线程执行完了之后就会到这里取任务执行
         */
        private final ArrayDequeCompat<Runnable> mQueue = new ArrayDequeCompat<Runnable>(serialMaxCount);
        private final ScheduleStrategy mStrategy = ScheduleStrategy.LIFO;

        private enum ScheduleStrategy {
            LIFO/* 类似栈 */, FIFO/* 类似管道 */;
        }

        /**
         * 一次同时并发的数量，根据处理器数量调节 <br>
         * 一个时间段内最多并发线程个数： 双核手机：2 四核手机：4 ... 计算公式如下： cpu count : 1 2 3 4 8 16 32 <br>
         * once(base*2): 1 2 3 4 8 16 32 <br>
         */
        private static int serialOneTime;
        /**
         * 并发最大数量，当投入的任务过多大于此值时，根据Lru规则，将最老的任务移除（将得不到执行） <br>
         * cpu count : 1 2 3 4 8 16 32 <br>
         * base(cpu+3) : 4 5 6 7 11 19 35 <br>
         * max(base*16): 64 80 96 112 176 304 560 <br>
         */
        private static int serialMaxCount;

        public SmartSerialExecutor() {
            reSettings(CPU_COUNT);
        }

        private void reSettings(int cpuCount) {
            serialOneTime = cpuCount;
            serialMaxCount = (cpuCount + 3) * 16;
        }

        @Override
        public synchronized void execute(final Runnable command) {
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    command.run();
                    next();
                    mHandler.sendEmptyMessage(MESSAGE_POST_FINISH);
                }
            };


            if (mThreadPoolExecutor.getActiveCount() < serialOneTime) {
                mThreadPoolExecutor.execute(r);
            } else {
                if (mQueue.size() >= serialMaxCount) {
                    mQueue.pollFirst();
                }
                mQueue.offerLast(r);
            }
        }

        public synchronized void next() {
            Runnable mActive;
            switch (mStrategy) {
                case LIFO:
                    mActive = mQueue.pollLast();
                    break;
                case FIFO:
                    mActive = mQueue.pollFirst();
                    break;
                default:
                    mActive = mQueue.pollLast();
                    break;
            }
            if (mActive != null) {
                mThreadPoolExecutor.execute(mActive);
            }
        }

    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    public static abstract class OnFinishedListener {
        public void onCancelled() {
        }

        public void onPostExecute() {
        }
    }
}