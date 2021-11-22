package com.patternkeyboard.android.task;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.patternkeyboard.android.utils.Constants;
import com.patternkeyboard.android.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDBTask extends AsyncTask<String, Void, Object> {

    private Context context;
    private AssetManager assetManager;
    private AppCompatActivity activity;

    public CopyDBTask(AppCompatActivity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        assetManager = context.getAssets();
    }

    @Override
    protected Object doInBackground(String... strings) {
        try {
            if (!Utils.isSQLiteDbExists(context)) {

                // copy db
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(Constants.SQ_DB_NAME);
                    File outFile = new File(Utils.getSQLiteDbPath(context));
                    out = new FileOutputStream(outFile);
                    // copy file
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (o instanceof Exception) {
            Toast.makeText(context, "Fatal error occurred, cannot proceed - " + ((Exception) o).toString(), Toast.LENGTH_LONG).show();
            activity.finishAffinity();
            System.exit(0);
        }
    }
}