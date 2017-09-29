package com.mjbor.trainingapp.Login.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Register.view.RegisterActivity;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView {


    @BindView(R.id.usernameEditText) EditText usernameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.btnLogin) Button submitButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private LoginPresenter presenter;
    private ISessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new SessionManager(this);
        presenter = new LoginPresenter(this, session);

    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressBarInvisible() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setButtonText(String text) {
        submitButton.setText(text);
    }


    /*
    USER CLICKS
     */

    public void loginClicked(View v){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        presenter.login(username, password);
    }

    public void registerHereClicked(View v){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void tutorialClicked(View v){


    }


}
