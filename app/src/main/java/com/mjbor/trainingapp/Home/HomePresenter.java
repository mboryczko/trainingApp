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

        getLastTrainingInfo(token);

    }

    public void onNoTrainingFetched(Training response){
        view.setEx1(response.getMessage());
    }

    public void getLastTrainingInfo(String token){
        interactor.getLastTrainingInfo(token);
    }


    public void onTrainingFetchedSuccesfully(Training response){
        List<String> list = new ArrayList<>();

        for(Exercise e : response.getExercises()){
            String name = e.getName();
            String weight = e.getWeight();
            String reps = "";
            for(String rep : e.getReps()){
                reps += rep + "/";
            }

            if(reps.equals("5/5/5/5/5/"))
                reps = "5x5";

            list.add(name + " " + reps + " " + weight + " kg");
        }

        callViewMethods(list);
    }


    public void callViewMethods(List<String> list){
        view.setEx1(list.get(0));
        view.setEx2(list.get(1));
        view.setEx3(list.get(2));
        view.setEx4(list.get(3));
    }
}
