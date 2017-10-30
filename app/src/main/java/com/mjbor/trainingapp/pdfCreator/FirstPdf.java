package com.mjbor.trainingapp.pdfCreator;

import android.content.Context;
import android.graphics.Bitmap;

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
import com.mjbor.trainingapp.models.UserResponse;

import java.io.File;
import java.util.List;

/**
 * Created by mjbor on 10/24/2017.
 */

public class FirstPdf {



    private String destination;
    public FirstPdf(String destination){
        this.destination = destination;
    }

    public static void createPdf(String fileName){
        try {
            //create file
            File pdfDirectory = new File(FilesUtils.getSaveFileDirectory()+"/trainingAppReports");
            pdfDirectory.mkdir();

            File pdfFile = new File(pdfDirectory, fileName+".pdf");
            String absoluteDestination = pdfFile.getAbsolutePath();

            String imagePath = FilesUtils.getSaveFileDirectory() + "/trainingAppCharts/" + fileName + ".png";
            Image image = new Image(ImageDataFactory.create(imagePath));
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());


            PdfWriter writer = new PdfWriter(absoluteDestination);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("TrainingApp Progress Report"));
            document.add(new Paragraph().add(image));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean createPdf(String fileName, List<Bitmap> listOfChartBitmaps, UserResponse userResponse){
        try {
            //create font

            //create file
            File pdfDirectory = new File(FilesUtils.getSaveFileDirectory()+"/trainingAppReports");
            pdfDirectory.mkdir();

            File pdfFile = new File(pdfDirectory, fileName+".pdf");
            String absoluteDestination = pdfFile.getAbsolutePath();

           /* String imagePath = FilesUtils.getSaveFileDirectory() + "/trainingAppCharts/" + fileName + ".png";
            Image image = new Image(ImageDataFactory.create(imagePath));
            image.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);*/



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



           for(Bitmap b : listOfChartBitmaps){
               Image image = convertBitmapToImage(b);
               image.scaleToFit(PageSize.A4.getWidth()*0.95f, PageSize.A4.getHeight()*0.95f);
               document.add(new Paragraph().add(image));
           }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static Image convertBitmapToImage(Bitmap bmp){
        // load image
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = new Image(ImageDataFactory.create(stream.toByteArray()));
            return image;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

}
