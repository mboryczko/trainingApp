package com.mjbor.trainingapp.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by mjbor on 10/24/2017.
 */

public class FilesUtils {

    public static String getSaveFileDirectory(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return dir.toString();
    }


    public static String createFileInDirectory(String directory, String fileName){
        //create directory
        File pdfDirectory = new File(getSaveFileDirectory()+ "/" + directory);
        pdfDirectory.mkdir();


        //create file
        File pdfFile = new File(pdfDirectory, fileName + ".pdf");
        String absoluteDestination = pdfFile.getAbsolutePath();

        return absoluteDestination;
    }
}
