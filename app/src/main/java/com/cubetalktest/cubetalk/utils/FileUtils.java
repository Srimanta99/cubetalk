package com.cubetalktest.cubetalk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils() {

    }

    public static File getFile(Context context, ContentResolver contentResolver, Uri uri) {
        if (uri != null) {
            try {
                ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(uri, "r");
                FileInputStream inputStream = new FileInputStream(pfd.getFileDescriptor());

                File file = new File(context.getCacheDir(), getFileName(contentResolver, uri));
                FileOutputStream outputStream = new FileOutputStream(file);

                ByteStreams.copy(inputStream, outputStream);
                return file;
            } catch (IOException e) {
                Log.d(TAG, "getFile: e.getMessage(): " + e.getMessage());
            }
        }
        return null;
    }

    public static String getFileName(ContentResolver contentResolver, Uri fileUri) {
        String name = "";

        Cursor returnCursor = contentResolver.query(fileUri, null, null, null, null);
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return name;
    }
}
