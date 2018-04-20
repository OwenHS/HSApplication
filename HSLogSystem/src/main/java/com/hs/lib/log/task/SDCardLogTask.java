package com.hs.lib.log.task;

import android.content.Context;
import android.os.Environment;

import com.hs.lib.log.BaseLogTask;
import com.hs.lib.log.HLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huangshuo on 16/8/29.
 */
public class SDCardLogTask extends BaseLogTask {

    /**
     * 判断是否可以写入SDCard
     **/
    public boolean Writeable = false;

    /**
     * log文件接收地址
     */
    private String fileDir;
    private String currentFileName;

    /**
     * 生成一个日期文件名格式的日式格式对象
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    /**
     * 生成一个日期文件名格式的日式格式对象
     */
    private static final SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    // 是否重新创建新的log文件
    private boolean ifStartNew = false;
    private static final int LOG_MAX_SIZE = 6 * 1024 * 1024;

    private Context context;

    /**
     * 写入传入的SDcard目录
     **/
    public SDCardLogTask(String fileDir, Context context) {
        if (checkSDcard()) {
            Writeable = true;
            this.fileDir = context.getExternalFilesDir(fileDir).getAbsolutePath();
            this.context = context.getApplicationContext();
        }
    }

    @Override
    public void printLog(int type, String tagStr, String headString, String msg) {
        if (Writeable)
            writeLog(type, tagStr, msg);
    }

    /**
     * 写入log
     *
     * @param priority
     * @param tag
     * @param message
     */
    private void writeLog(int priority, String tag, Object message) {
        File file = getFile(tag);
        if (file != null) {
            try {
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                Date now = new Date();
                bw.append("\r\n");
                bw.append(getPriority(priority) + " " + nowFormat.format(now) + " " + tag
                        + " " + message);
                bw.append("\r\n");
                bw.flush();
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getFile(String tag) {
        if ("".equals(fileDir)) {
            return null;
        }

        //获得本次log的文件名称
        currentFileName = fileDir + File.separator + tag + File.separator + getCurrTimeDir() + "_0.txt";

        //设置的是一个tag为一个文件
        File dir = new File(fileDir + File.separator + tag);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(currentFileName);

        if (!file.exists()) {
            // 当文件不存在的时候新建
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!ifStartNew) {
                // 追加文件而不是，重新开一个新文件
                if (file.length() >= LOG_MAX_SIZE) {
                    ifStartNew = true;
                    // 一个文件如果大于LOG_MAX_SIZE，就重新生成
                    return getFile(tag);
                }
                return file;
            } else {
                // 如果是新建文件，那么文件名就要修改了
                return new File(fileDir
                        + File.separator
                        + tag
                        + File.separator
                        + getCurrTimeDir() + "_"
                        + (getFileNameLastCount(currentFileName) + 1)
                        + ".txt");
            }
        }

        return file;
    }

    private String getPriority(int priority) {
        String result = "";
        switch (priority) {
            case HLog.V:
                result = "VERBOSE";
                break;
            case HLog.D:
                result = "DEBUG";
                break;
            case HLog.I:
                result = "INFO";
                break;
            case HLog.W:
                result = "WARN";
                break;
            case HLog.E:
                result = "ERROR";
                break;
        }
        return result;
    }

    /**
     * 获取当前时间
     */
    public String getCurrTimeDir() {
        return sdf.format(new Date());
    }

    public static int getFileNameLastCount(String fileName) {
        return Integer.parseInt(fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf(".")));
    }

    /**
     * 检测SD卡是否存在
     */
    public boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
