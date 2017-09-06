package com.xblock.sample;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Util class to read a file from Assets
 */

public class AssetUtils {

    private static final String TAG = AssetUtils.class.getSimpleName();
    private static final String DEFAULT_ENCODING = "UTF-8";

    static Map<String, String> fileContentCache = new HashMap<String, String>();

    public static String getFileContent(Context context, String filePath) {
        String contents = null;

        if (fileContentCache != null) {
            contents = fileContentCache.get(filePath);
        }

        if (contents == null) {
            contents = getFileContentDirect(context, filePath);
            if (fileContentCache == null) {
                fileContentCache = new HashMap<String, String>();
            }
            fileContentCache.put(filePath, contents);
        }
        return contents;
    }

    private static String getFileContentDirect(Context context, String filePath) {
        String contents = null;
        StringBuilder sb = new StringBuilder();
        InputStream is = null;

        try {
            // @mtalha Sep 15, 2015
            String baseDir = getContextBaseDir(context);
            // baseDir += File.separator + Urls.INTERNAL_ASSETS_DIRECTORY + File.separator + "assets";
            baseDir += File.separator + "internal_assets" + File.separator + "assets";
            String absolutePath = baseDir + File.separator + filePath;
            File file = new File(absolutePath);
            if (file.exists()) {
                is = new FileInputStream(file);
            }
            else {
                AssetManager am = context.getResources().getAssets();
                if (am != null) {
                    is = am.open(filePath);
                }
            }

            int size = 0;
            while ((size = is.available()) > 0) {
                byte[] buffer = new byte[size];
                is.read(buffer, 0, size);
                sb.append(new String(buffer, 0, size, DEFAULT_ENCODING));
            }

            contents = sb.toString();
        } catch (IOException e) {
            LogUtils.LOGE(TAG, "Error getting file content for " + filePath, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtils.LOGE(TAG, "Error closing inputStream while getting " + filePath, e);
                }
            }
        }
        return contents;
    }

    public static String getContextBaseDir(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }


}
