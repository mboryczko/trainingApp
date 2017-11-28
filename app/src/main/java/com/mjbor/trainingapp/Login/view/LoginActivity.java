package com.mjbor.trainingapp.Login.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mjbor.trainingapp.Login.dialog.ForgotPasswordDialog;
import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Register.dialog.TrainingHistoryDialog;
import com.mjbor.trainingapp.Register.view.RegisterActivity;
import com.mjbor.trainingapp.app.TrainingApplication;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.Profile.fetchProfileForCurrentAccessToken;

public class LoginActivity extends AppCompatActivity
        implements ILoginView, FacebookCallback<LoginResult>,
        TrainingHistoryDialog.TrainingHistoryListener,
        ForgotPasswordDialog.ForgottenPasswordListener{


    @BindView(R.id.emailEditText) EditText emailEdiText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.btnLogin) Button submitButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.login_button) LoginButton loginButton;
    private LoginPresenter presenter;

    @Inject
    ISessionManager session;
    private CallbackManager callbackManager;

    private TrainingHistoryDialog dialog;
    private ForgotPasswordDialog forgotPasswordDialog;

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        ((TrainingApplication)getApplication()).getAppComponent().inject(this);

        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        //LoginManager.getInstance().registerCallback(callbackManager, this);
        loginButton.registerCallback(callbackManager, this);

        //session = new SessionManager(this);
        presenter = new LoginPresenter(this, session);
    }


    @Override
    public void onDialogPositiveCheck(DialogFragment dialog) {
        List<Double> list = ((TrainingHistoryDialog) dialog).getList();
        presenter.userHistoryProvided(list);
        dialog.dismiss();
    }

    @Override
    public void onForgottenClicked(DialogFragment dialog) {
        String email = ((ForgotPasswordDialog) dialog).getEmail();
        presenter.forgottenPassword(email);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void promptRecordPopup() {
        dialog = new TrainingHistoryDialog();
        dialog.show(getSupportFragmentManager(), "TrainingHistoryDialog");
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        getFacebookData(loginResult);

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    private void getFacebookData(final LoginResult loginResult)
    {
        final GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            //String cover = response.getJSONObject().getString("cover");
                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String avatar = "https://graph.facebook.com/" + id + "/picture?type=large";
                            //String url = object.getJSONObject("photos").getJSONArray("data").getJSONObject(0).getString("source");
                            String cover = "";

                            if(!response.getJSONObject().isNull("cover")){
                                cover = response.getJSONObject().getJSONObject("cover").getString("source");
                            }

                            else {
                                cover = null;
                            }



                            //String url = "https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-9/20374474_103615050331582_1220629774814276710_n.jpg?oh=79f0b9dcf39e591c6c6c5d70a0426ce0&oe=5A3FB70B";

                            User user = new User();
                            user.setEmail(email);
                            user.setName(firstName);
                            user.setSurname(lastName);
                            user.setAvatar(avatar);
                            user.setCover(cover);

                            presenter.onFacebookLoginSuccess(user);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,cover,photos{source}");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        String email = emailEdiText.getText().toString();
        String password = passwordEditText.getText().toString();

        presenter.login(email, password);
    }

    public void facebookLoginClicked(View v){

    }

    public void registerHereClicked(View v){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void forgottenClicked(View v){
        forgotPasswordDialog = new ForgotPasswordDialog();
        forgotPasswordDialog.show(getSupportFragmentManager(), "ForgottenPasswordDialog");
    }


}
