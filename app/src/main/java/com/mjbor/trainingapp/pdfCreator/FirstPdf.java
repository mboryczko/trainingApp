package com.mjbor.trainingapp.pdfCreator;

import android.content.Context;
import android.graphics.Bitmap;


import android.graphics.Bitmap;
import android.os.FileUriExposedException;
import android.util.Log;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.mjbor.trainingapp.Utils.FilesUtils;
import com.mjbor.trainingapp.Utils.StringUtils;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.PdfWrapper;
import com.mjbor.trainingapp.models.UserResponse;

import java.util.List;

/**
 * Created by mjbor on 10/24/2017.
 */

public class FirstPdf {

    public static final String TAG = FirstPdf.class.toString();

    public static String createPdf(PdfWrapper wrapper){
        try {

            Log.e(TAG, "Started writing to PDF on" + Thread.currentThread().getName());
            UserResponse userResponse = wrapper.getUserResponse();


            String absoluteDestination =
                    FilesUtils.createFileInDirectory("trainingAppReports", wrapper.getFilename());


            List<Exercise> exerciseList = userResponse.getExercises();
            String exercisesParagraph = StringUtils.prepareListOfBestResults(exerciseList);


            PdfWriter writer = new PdfWriter(absoluteDestination);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("TrainingApp Progress Report"));
            document.add(new Paragraph(userResponse.getName()));
            document.add(new Paragraph(userResponse.getSurname()));
            document.add(new Paragraph(userResponse.getEmail()));
            document.add(new Paragraph("Best exercises: "));
            document.add(new Paragraph(exercisesParagraph));

           for(Bitmap b : wrapper.getListOfChartsBitmaps()){
               Image image = convertBitmapToImage(b);
               image.scaleToFit(PageSize.A4.getWidth()*0.95f, PageSize.A4.getHeight()*0.95f);
               document.add(new Paragraph().add(image));
           }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error writing pdf raport";
        }

        Log.e(TAG, "Started writing to PDF on" + Thread.currentThread().getName());

        return "Raport generated successfully";
    }


    public static Image convertBitmapToImage(Bitmap bmp){
        // load image
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = new Image(ImageDataFactory.create(stream.toByteArray()));
            return image;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

}
