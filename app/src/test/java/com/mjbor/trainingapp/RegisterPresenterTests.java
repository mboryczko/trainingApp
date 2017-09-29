package com.mjbor.trainingapp;

import com.mjbor.trainingapp.Register.presenter.RegisterPresenter;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mjbor on 9/23/2017.
 */

public class RegisterPresenterTests {

    private RegisterPresenter presenter;

    @Before
    public void setUp(){
        presenter = new RegisterPresenter(MockFactory.mockRegisterView());
    }

    @Test
    public void generateFirstTrainingValuesTest(){
        List<Double> list = new ArrayList<>();
        list.add(107d);
        list.add(100d);
        list.add(92.5d);
        list.add(77.5d);
        list.add(55d);
        list.add(54d);

        List <Double> formatedList = presenter.generateFirstTrainingValues(list);

        assertEquals("52.5", formatedList.get(0));
        assertEquals("50.0", formatedList.get(1));
        assertEquals("45.0", formatedList.get(2));
        assertEquals("37.5", formatedList.get(3));
        assertEquals("27.5", formatedList.get(4));
        assertEquals("25.0", formatedList.get(5));

    }
}
