package com.mjbor.trainingapp.Main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.presenter.MainPresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Register.view.RegisterActivity;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements IMainView{

    @BindView(R.id.logoutTextView) TextView logoutTextView;

    private MainPresenter presenter;
    private ISessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        session = new SessionManager(this);
        presenter = new MainPresenter(this,session );

    }

    @Override
    public void onLogout() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        startActivity(i);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void logoutClicked(View v){
        presenter.logOut();
    }
}
