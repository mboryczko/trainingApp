package com.mjbor.trainingapp.pdfCreator;

import android.content.Context;
import android.graphics.Bitmap;


import android.graphics.Bitmap;
import android.os.FileUriExposedException;
import android.util.Log;
import com.itextpdf.io.font.*;
import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.mjbor.trainingapp.Utils.FilesUtils;
import com.mjbor.trainingapp.Utils.StringUtils;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.PdfWrapper;
import com.mjbor.trainingapp.models.UserResponse;

import java.util.List;

/**
 * Created by mjbor on 10/24/2017.
 */

public class PdfGenerator {

    public static final String TAG = PdfGenerator.class.toString();

    public String createPdf(PdfWrapper wrapper){
        try {
            Log.e(TAG, "Started writing to PDF on" + Thread.currentThread().getName());

            UserResponse userResponse = wrapper.getUserResponse();
            String absoluteDestination =
                    FilesUtils.createFileInDirectory("trainingAppReports", wrapper.getFilename());


            List<Exercise> exerciseList = userResponse.getExercises();
            List<Bitmap> bitmapsList = wrapper.getListOfChartsBitmaps();
            String exercisesParagraph = StringUtils.prepareListOfBestResults(exerciseList);
            String userInfoParagraph = StringUtils.preparePersonalInfo(userResponse);


            PdfWriter writer = new PdfWriter(absoluteDestination);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);


            document.add(new Paragraph()
                .add(new Text("Training Progress Report").setFontSize(40f)));

            //get profile picture
            addImageToDocument(document, bitmapsList.get(0), 30);

            document.add(new Paragraph()
                    .add(new Text("Information").setFontSize(40f)));
            document.add(new Paragraph(userInfoParagraph));

            document.add(new Paragraph()
                    .add(new Text("Best exercises").setFontSize(40f)));
            document.add(new Paragraph(exercisesParagraph));

           for(int i=1; i < bitmapsList.size(); i++){
               Bitmap b = bitmapsList.get(i);
               addImageToDocument(document, b, 90);
           }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error writing pdf raport";
        }

        Log.e(TAG, "Finished writing to PDF on" + Thread.currentThread().getName());

        return "Raport generated successfully";
    }


    public Image convertBitmapToImage(Bitmap bmp){
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

    public void addImageToDocument(Document document, Bitmap b, int pagePercentage){
        Image image = convertBitmapToImage(b);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        float value = pagePercentage /100f;

        image.scaleToFit(PageSize.A4.getWidth() * value, PageSize.A4.getHeight() * value);
        document.add(new Paragraph().add(image).setHorizontalAlignment(HorizontalAlignment.CENTER));
    }

}
