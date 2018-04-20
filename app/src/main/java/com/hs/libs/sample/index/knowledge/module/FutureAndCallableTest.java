package com.hs.libs.sample.index.knowledge.module;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 我们知道创建线程的方式有两种，一种是实现Runnable接口，另一种是继承Thread，
 * 但是这两种方式都有个缺点，那就是在任务执行完成之后无法获取返回结果，那如果我们想要获取返回结果该如何实现呢？
 * 从JAVA SE 5.0开始引入了Callable和Future，通过它们构建的线程，在任务执行完成后就可以获取执行结果，
 * 今天我们就来测试线程创建的第三种方式，那就是实现Callable接口。
 * <p>
 * Created by huangshuo on 17/11/7.
 */

public class FutureAndCallableTest extends AbstractKnowledge {
    @Override
    public String getTitle() {
        return "多线程--Future and Callable";
    }


    /**
     * Executor：一个接口，其定义了一个接收Runnable对象的方法executor，其方法签名为executor(Runnable command),
     * ExecutorService：是一个比Executor使用更广泛的子类接口，其提供了生命周期管理的方法，以及可跟踪一个或多个异步任务执行状况返回Future的方法
     * <p>
     * 主要对于线程的处理是在ExecutorService中实现的，而真正的使用是Executors.newXXXThreadPool(),这种构造出不同的线程池
     **/
    @Override
    public void testKnowledge() {

//        threadPoolTest();

        futureTest();
    }


    /**
     * 我对future的理解，比如在一个可以运行4个线程的线程池里，我放入100个任务（callable或者runnable），
     * 如果不适用future，那么每一个任务运行完，都得去存他的运行结果，而使用future就可以直接存相对应的返回，
     * 可以把请求和回复结合了起来。
     */
    private void futureTest() {
        Callable<String> testCall = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "成功";
            }
        };

        try {
            Future<String> testFuture = Executors.newSingleThreadExecutor().submit(testCall);
            testFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            //使用这种方式，拥有请求的那个对象这样就可以获取返回值的值，直接获取，无需来了数据就判断
            FutureTask<String> testFutureTask = new FutureTask<>(testCall);
            Executors.newSingleThreadExecutor().submit(testFutureTask);
            testFutureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * 线程池的作用：
     * <p>
     * 线程池作用就是限制系统中执行线程的数量。
     * 根据系统的环境情况，可以自动或手动设置线程数量，达到运行的最佳效果；
     * 少了浪费了系统资源，多了造成系统拥挤效率不高。用线程池控制线程数量，其他线程排 队等候。
     * 一个任务执行完毕，再从队列的中取最前面的任务开始执行。若队列中没有等待进程，线程池的这一资源处于等待。
     * 当一个新任务需要运行时，如果线程池 中有等待的工作线程，就可以开始运行了；否则进入等待队列。
     * <p>
     * 为什么要用线程池:
     * <p>
     * 1.减少了创建和销毁线程的次数，每个工作线程都可以被重复利用，可执行多个任务。
     * <p>
     * 2.可以根据系统的承受能力，调整线程池中工作线线程的数目，防止因为消耗过多的内存，而把服务器累趴下(每个线程需要大约1MB内存，线程开的越多，消耗的内存也就越大，最后死机)。
     * <p>
     * Java里面线程池的顶级接口是Executor，但是严格意义上讲Executor并不是一个线程池，而只是一个执行线程的工具。真正的线程池接口是ExecutorService。
     *
     * 最后 来个各种阻塞队列的说明和比较：

     　　Java并发包中的阻塞队列一共7个，当然他们都是线程安全的。

     　　ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列。

     　　LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。

     　　PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。

     　　DealyQueue：一个使用优先级队列实现的无界阻塞队列。

     　　SynchronousQueue：一个不存储元素的阻塞队列。

     　　LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。

     　　LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。
     */
    private void threadPoolTest() {

//        testCacheThreadPool();

//        testFixedThreadPool();

//        testScheduledThreadPool();

        testSingleThreadExecutor();

    }

    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * <p>
     * new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
     */
    private void testCacheThreadPool() {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                //如果sleep了时间，就是说线程里按顺序进行，一个线程执行完执行第二个，复用了只用到一个线程对象
                //如果sleep没有事间，也就是所有线程是同时执行，所以有10个线程对象
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("线程名字： " + Thread.currentThread().getName() + "  任务名为： " + index);
                }
            });

        }
    }

    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     * new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
     * 使用一个基于FIFO排序的阻塞队列，在所有corePoolSize线程都忙时新任务将在队列中等待
     */
    private void testFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程名字： " + Thread.currentThread().getName() + "  任务名为： " + index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     */
    private void testScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     */
    private void testSingleThreadExecutor() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
