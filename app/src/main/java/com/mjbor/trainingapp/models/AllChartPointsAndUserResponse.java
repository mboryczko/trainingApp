package com.mjbor.trainingapp.models;

import java.util.List;

/**
 * Created by mjbor on 10/26/2017.
 */

public class AllChartPointsAndUserResponse {

    List<ChartResponse> exercises;

    public List<ChartResponse> getList() {
        return exercises;
    }

    public void setList(List<ChartResponse> list) {
        this.exercises = list;
    }

    private String message;

    private String avatar;

    private String id_user;

    private boolean error;

    private String email;

    private String name;

    private List<Exercise> exercises;

    private String surname;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }


    public String getId_user ()
    {
        return id_user;
    }

    public void setId_user (String id_user)
    {
        this.id_user = id_user;
    }

    public boolean getError ()
    {
        return error;
    }

    public void setError (boolean error)
    {
        this.error = error;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public boolean isError() {
        return error;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getSurname ()
    {
        return surname;
    }

    public void setSurname (String surname)
    {
        this.surname = surname;
    }
}
