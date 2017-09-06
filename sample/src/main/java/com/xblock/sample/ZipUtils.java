package com.xblock.sample;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility to unzip files.
 */

public class ZipUtils {

    private static final String TAG = "ZipUtils";
    private static final int BUFFER_SIZE = 4096;

    public interface ProgressUpdateListener {
        public void onProgressUpdate(int progress);

        public void onFinished();
    }

    public static void unzip(Context context, String from, boolean isFromAssets, String to, ProgressUpdateListener progressUpdateListener) {
        try {
            // final AssetFileDescriptor fd = context.getAssets().openFd(from);
            // long length = fd.getLength();
            long length = 0;
            long totalBytesRead = 0L;

            InputStream inStream = null;
            if (isFromAssets) {
                inStream = context.getAssets().open(from);
            } else {
                File source = new File(context.getExternalFilesDir(null), from);
                inStream = new FileInputStream(source);
            }
            ZipInputStream zipInputStream = new ZipInputStream(inStream);
            ZipEntry zipEntry = null;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    createDirectoryIfRequired(to, zipEntry.getName());
                } else {
                    try {
                        int bytesRead = 0;
                        FileOutputStream outputStream = new FileOutputStream(to + File.separator + zipEntry.getName());
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                        }
                        zipInputStream.closeEntry();
                        outputStream.close();
                    } catch (Exception e) {
                        LogUtils.LOGE(TAG, ">>> Error while writing to internal assets ", e);
                        e.printStackTrace();
                    }
                    //TODO: make this progress accurate if possible (Not required for now)
                    //if (progressUpdateListener != null && length != AssetFileDescriptor.UNKNOWN_LENGTH) {
                    //    progressUpdateListener.onProgressUpdate((int) (100 * (totalBytesRead / (float) length)));
                    //}
                }
            }

            zipInputStream.close();
            if (progressUpdateListener != null) {
                progressUpdateListener.onFinished();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectoryIfRequired(String location, String dir) {
        File f = new File(location, dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}
