package com.patternkeyboard.android.task;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

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

    public CopyDBTask(Context context) {
        this.context = context;
        assetManager = context.getAssets();
    }

    @Override
    protected Object doInBackground(String... strings) {
        try {
            if (Utils.isSQLiteDbExists(context)) {

                // copy db
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(Utils.getSQLiteDbPath(context));
                    File outFile = new File(Constants.SQ_DB_NAME);
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
        }
        return null;
    }

}