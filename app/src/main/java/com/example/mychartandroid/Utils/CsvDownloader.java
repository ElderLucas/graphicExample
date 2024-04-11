package com.example.mychartandroid.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CsvDownloader {

    public static void saveCsvToDownloads(Context context, String csvContent, String fileName) {
        // Uri para o arquivo que será salvo
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Usando MediaStore para Android 10 (API 29) e superior
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName); // Nome do arquivo
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv"); // Tipo do arquivo
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS); // Localização

            uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

            try (OutputStream out = context.getContentResolver().openOutputStream(uri)) {
                PrintWriter printWriter = new PrintWriter(out);
                printWriter.write(csvContent);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Para versões anteriores ao Android 10
            java.io.File file = new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.write(csvContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface Callback {
        void onSuccess(String filePath);
        void onFailure(IOException e);
    }

    public static void saveCsvToDownloads(Context context, String csvContent, String fileName, Callback callback) {
        try {
            // Versões Android 10 (API 29) ou superior
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    throw new IOException("Falha ao criar novo registro no MediaStore");
                }

                try (OutputStream out = context.getContentResolver().openOutputStream(uri)) {
                    out.write(csvContent.getBytes(StandardCharsets.UTF_8));
                }

                callback.onSuccess(uri.toString());
            } else {
                // Para versões anteriores ao Android 10
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, fileName);

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(csvContent);
                }

                callback.onSuccess(file.getAbsolutePath());
            }
        } catch (IOException e) {
            callback.onFailure(e);
        }
    }




}
