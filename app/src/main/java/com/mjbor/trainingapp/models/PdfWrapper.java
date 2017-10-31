package com.mjbor.trainingapp.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by mjbor on 10/31/2017.
 */

public class PdfWrapper {

    private String filename;
    private UserResponse userResponse;
    private List<Bitmap> listOfChartsBitmaps;

    public PdfWrapper(String filename, UserResponse userResponse, List<Bitmap> listOfChartsBitmaps) {
        this.filename = filename;
        this.userResponse = userResponse;
        this.listOfChartsBitmaps = listOfChartsBitmaps;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public List<Bitmap> getListOfChartsBitmaps() {
        return listOfChartsBitmaps;
    }

    public void setListOfChartsBitmaps(List<Bitmap> listOfChartsBitmaps) {
        this.listOfChartsBitmaps = listOfChartsBitmaps;
    }
}
