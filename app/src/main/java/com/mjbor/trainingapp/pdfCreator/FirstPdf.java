package com.mjbor.trainingapp.pdfCreator;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.http.Path;

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
            image.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);


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

    public static boolean createPdf(String fileName, List<Bitmap> listOfChartBitmaps){
        try {
            //create file
            File pdfDirectory = new File(FilesUtils.getSaveFileDirectory()+"/trainingAppReports");
            pdfDirectory.mkdir();

            File pdfFile = new File(pdfDirectory, fileName+".pdf");
            String absoluteDestination = pdfFile.getAbsolutePath();

           /* String imagePath = FilesUtils.getSaveFileDirectory() + "/trainingAppCharts/" + fileName + ".png";
            Image image = new Image(ImageDataFactory.create(imagePath));
            image.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);*/


            PdfWriter writer = new PdfWriter(absoluteDestination);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("TrainingApp Progress Report"));

           for(Bitmap b : listOfChartBitmaps){
               Image image = convertBitmapToImage(b);
               image.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);
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
