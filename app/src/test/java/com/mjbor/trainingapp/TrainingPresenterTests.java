package com.mjbor.trainingapp;

import com.mjbor.trainingapp.Home.HomePresenter;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.models.Training;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by mjbor on 9/22/2017.
 */

public class TrainingPresenterTests {

    private TrainingPresenter presenter;

    @Before
    public void setUp(){
        presenter = new TrainingPresenter(MockFactory.mockTrainingFragment(), MockFactory.mockISessionManager());
    }


}
