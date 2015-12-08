package lib.hs.com.logsystemlib.support;

import android.content.Context;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lib.hs.com.logsystemlib.support.interfaces.ConsoleLogTraceListener;
import lib.hs.com.logsystemlib.support.interfaces.FileLogTraceListener;

public class Log {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static Trace trace = new Trace("log");
    public static int logfilesSaveDays = 7;

    public  static final boolean DEBUG = true;
    public  static final boolean RELEASE = false;

    private static Context logContext;
    /**
     * 打印日志保存路径
     */
    private static String logFileDir = "";
    /**
     * 控制打印级别 在level级别之上才可以被打印出来
     */
    public static int Level = 5;

    /**
     * 生成一个日期文件名格式的日式格式对象
     */
    private static ConsoleLogTraceListener logTraceListener = new ConsoleLogTraceListener();
    private static FileLogTraceListener fileTraceListener;

    public static final void i(final String tag, final String message) {
        trace.information(tag, message);
    }

    public static final void i(String tag, Exception exp) {
        trace.information(tag, exp);
    }

    public static final void e(final String tag, final String message) {
        trace.error(tag, message);
    }

    public static final void e(String tag, Exception exp) {
        trace.error(tag, exp);
    }

    public static final void w(final String tag, final String message) {
        trace.warning(tag, message);
    }

    public static final void w(String tag, Exception exp) {
        trace.warning(tag, exp);
    }

    public static final void v(final String tag, final String message) {
        trace.verbose(tag, message);
    }

    public static final void v(String tag, Exception exp) {
        trace.verbose(tag, exp);
    }

    public static final void d(final String tag, final String message) {
        trace.debug(tag, message);
    }

    public static final void d(String tag, Exception exp) {
        trace.debug(tag, exp);
    }

    /**
     * 强制打印信息
     *
     * @param message
     */
    public static final void print(final String tag, final String message) {
        trace.print(Trace.ALL, tag, message);
    }

    public static final void print(String tag, Throwable throwable) {
        trace.print(Trace.ALL, tag, throwable);
    }


    public static final void setLocalLogFileOutput(Context context, int filesSaveDays) {
        setLocalLogFileOutput(context, filesSaveDays, true);
    }

    public static void setLocalLogFileOutput(Context context, int filesSaveDays, boolean debug) {
        logContext = context;
        logfilesSaveDays = filesSaveDays;

        logFileDir = logContext.getExternalFilesDir(trace.getName()).getAbsolutePath();
        fileTraceListener = new FileLogTraceListener(logFileDir);
        fileTraceListener.setDebug(debug);
        trace.addListener(logTraceListener).addListener(fileTraceListener);

        new LogCollectorThread("LogCollector");
    }

    public static String getFileNameCreateDate(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("_"));
    }

    public static final void setLevel(int level) {
        trace.setPriority(level);
    }

    public static final void setDebug(boolean debug) {
        logTraceListener.setDebug(debug);
    }

    public static class LogCollectorThread extends Thread {
        public LogCollectorThread(final String threadName) {
            setName(threadName);
            start();
        }

        @Override
        public void run() {
            try {
                handleLog();
            } catch (Exception e) {
            }
        }

        private void handleLog() {
            File file = new File(logFileDir);
            if (file.isDirectory()) {
                File[] allFiles = file.listFiles();
                for (File logFile : allFiles) {
                    String fileName = logFile.getName();
                    String createDateInfo = getFileNameCreateDate(fileName);
                    if (canDeleteSDLog(createDateInfo)) {
                        logFile.delete();
                    }
                }
            }
        }

        /**
         * 判断sdcard上的日志文件是否可以删除
         *
         * @param createDateStr
         * @return
         */
        public boolean canDeleteSDLog(String createDateStr) {
            boolean canDel = false;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1 * logfilesSaveDays);//删除7天之前日志
            Date expiredDate = calendar.getTime();
            try {
                Date createDate = sdf.parse(createDateStr);
                canDel = createDate.before(expiredDate);
            } catch (ParseException e) {
                canDel = false;
            }
            return canDel;
        }
    }
}
