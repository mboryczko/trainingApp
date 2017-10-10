package com.mjbor.trainingapp.Home;

import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjbor on 9/19/2017.
 */

public class HomePresenter {

    private IHomeFragment view;
    private HomeInteractor interactor;

    private String token;

    public HomePresenter(IHomeFragment view, String token){
        this.view = view;
        this.interactor = new HomeInteractor(this);
        this.token = token;

        getLastTrainingInfo();

    }

    public void onNoTrainingFetched(Training response){
        view.setEx1(response.getMessage());
    }

    public void getLastTrainingInfo(){
        interactor.getLastTrainingInfo(token);
    }


    public void onTrainingFetchedSuccesfully(Training response){

        view.trainingFetched(response);

        String text = "";

        for(Exercise e : response.getExercises()){
            String name = e.getName();
            double weight = e.getWeight();
            String reps = "";
            for(int rep : e.getReps()){
                reps += rep + "/";
            }

            reps = formatSeries(reps);


            text += name + " " + reps + " " + weight + " kg\n";
        }

        view.setEx1(text);
    }


    public String formatSeries(String text){
        int appearances = 0;

        String firstNumber = getFirstNumber(text);
        String currentNumber = "";

        for(char c : text.toCharArray()){
            if(c != '/') {
                currentNumber += c;
            }

            else{
                if(!currentNumber.equals(firstNumber)){
                    return text;
                }
                appearances++;
                currentNumber = "";
            }
        }

        return ""+appearances+"x"+firstNumber;
    }

    public String getFirstNumber(String text){
        String firstNumber = "";
        for(char c : text.toCharArray()){
            if(c != '/')
                firstNumber += c;
            else
                return firstNumber;
        }

        return firstNumber;
    }



}
