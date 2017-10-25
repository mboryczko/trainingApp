package com.mjbor.trainingapp;

import com.mjbor.trainingapp.Home.HomePresenter;
import com.mjbor.trainingapp.Home.IHomeFragment;
import com.mjbor.trainingapp.Register.presenter.RegisterPresenter;
import com.mjbor.trainingapp.Register.view.IRegisterView;
import com.mjbor.trainingapp.models.Training;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HomePresenterTests {


    private HomePresenter presenter;

    @Before
    public void setUp(){
        presenter = new HomePresenter(mockHomeFragment(), mockToken());
    }


    public IHomeFragment mockHomeFragment(){
        IHomeFragment iHomeFragment = new IHomeFragment() {
            @Override
            public void setEx1(String text) {

            }

            @Override
            public void refreshData() {

            }

            @Override
            public void trainingFetched(Training trainingFetched) {

            }
        };

        return iHomeFragment;
    }

    public String mockToken(){
        return "3a27dc39a7e2a4c1be582bea7817abb9";
    }


    @Test
    public void formatingSeriesTest(){
        assertEquals("5x5", presenter.formatSeries("5/5/5/5/5/") );
        assertEquals("3x5", presenter.formatSeries("5/5/5/"));
        assertEquals("4x12", presenter.formatSeries("12/12/12/12/"));

        assertEquals("6/6/5/", presenter.formatSeries("6/6/5/"));
        assertEquals("12/11/11/", presenter.formatSeries("12/11/11/"));
        assertEquals("1/2/3/", presenter.formatSeries("1/2/3/"));
    }


    @Test
    public void getFirstNumber(){
        assertEquals("1", presenter.getFirstNumber("1/2/3"));
        assertEquals("12", presenter.getFirstNumber("12/13/14"));
    }

}