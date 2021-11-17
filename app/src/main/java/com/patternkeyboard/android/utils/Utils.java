package com.patternkeyboard.android.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.fragment.app.FragmentActivity;

import com.patternkeyboard.android.task.CopyDBTask;

import java.io.File;

public class Utils {

    public static void copySQLiteDb(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            new CopyDBTask(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            new CopyDBTask(context).execute();
    }

    public static boolean isSQLiteDbExists(Context context) {
        boolean exists = false;
        exists = new File(Utils.getSQLiteDbPath(context)).exists();
        return exists;
    }

    public static String getSQLiteDbPath(Context context) {
        String fileName = getMainPath(context) + Constants.SQ_DB_NAME;
        return fileName;
    }

    public static String getMainPath(Context context) {
        //String path = getEnviornmentPath() + File.separator + "quickpassword" + File.separator;
        String path = context.getFilesDir().getPath() + File.separator + "patternkey" + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    public static void openKeyboardSettings(FragmentActivity activity) {
        activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
    }

}