package com.mjbor.trainingapp.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by mjbor on 10/24/2017.
 */

public class FilesUtils {


    public static String createFileInDirectory(String directory, String fileName) {
        File pdfDirectory = new File(FilesUtils.getSaveFileDirectory() + directory);
        pdfDirectory.mkdirs();
        File pdfFile = new File(pdfDirectory, fileName);

        return pdfFile.getAbsolutePath();

    }

    public static String getSaveFileDirectory(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return dir.toString();
    }
}
