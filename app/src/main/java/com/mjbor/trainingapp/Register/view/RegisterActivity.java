package com.mjbor.trainingapp.Register.view;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Register.dialog.TrainingHistoryDialog;
import com.mjbor.trainingapp.Register.presenter.RegisterPresenter;
import com.mjbor.trainingapp.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements IRegisterView,
        TrainingHistoryDialog.TrainingHistoryListener{


    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.surnameEditText) EditText surnameEditText;
    @BindView(R.id.submitButton) Button submitButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;


    private RegisterPresenter presenter;
    private TrainingHistoryDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        presenter = new RegisterPresenter(this);
    }

    @Override
    public void promptRecordPopup() {
        dialog = new TrainingHistoryDialog();
        dialog.show(getSupportFragmentManager(), "TrainingHistoryDialog");
    }

    @Override
    public void onDialogPositiveCheck(DialogFragment dialog) {
        List<Double> list = ((TrainingHistoryDialog) dialog).getList();
        presenter.userHistoryProvided(list);
        dialog.dismiss();
        //Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
        Toast.makeText(this, "NOOO", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setButtonText(String text) {
        submitButton.setText(text);
    }

    @Override
    public void setProgressBarInvisible() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRegisterSuccess() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void submitClicked(View v){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();


        User user = new User(email, password, name, surname);
        presenter.registerClicked(user);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG ).show();
    }
}
