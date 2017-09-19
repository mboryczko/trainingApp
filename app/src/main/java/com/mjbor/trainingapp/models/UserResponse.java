package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 9/18/2017.
 */

public class UserResponse
{
    private String message;

    private String username;

    private String id_user;

    private boolean error;

    private String email;

    private String name;

    private String surname;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getId_user ()
    {
        return id_user;
    }

    public void setId_user (String id_user)
    {
        this.id_user = id_user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
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

    public String getSurname ()
    {
        return surname;
    }

    public void setSurname (String surname)
    {
        this.surname = surname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", username = "+username+", id_user = "+id_user+", error = "+error+", email = "+email+", name = "+name+", surname = "+surname+"]";
    }
}
