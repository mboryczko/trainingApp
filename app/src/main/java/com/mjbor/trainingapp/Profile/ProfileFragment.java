package com.mjbor.trainingapp.Profile;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.model.MainWebService;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.User;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements IProfileFragment,
 AppBarLayout.OnOffsetChangedListener{

    public ProfilePresenter presenter;

    @BindView(R.id.topNameTextView) TextView topNameTextView;
    @BindView(R.id.surnameTextView) TextView surnameTextView;
    @BindView(R.id.emailTextView) TextView emailTextView;
    @BindView(R.id.bestResultsTextView) TextView bestResultsTextView;
    @BindView(R.id.profileImage) FloatingActionButton floatingActionButton;
    @BindView(R.id.backImage) ImageView backImage;


    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String token = getArguments().getString(Constants.TOKEN, null);
        this.presenter = new ProfilePresenter(this, token);

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        ((MainActivity)getActivity()).swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    @Override
    public void refreshData() {
        presenter.getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.NAME, collapsingToolbarLayout.getTitle().toString());
        outState.putString(Constants.SURNAME, surnameTextView.getText().toString());
        outState.putString(Constants.EMAIL, emailTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        if(savedInstanceState != null){
            setCollapsingTootlbarTitle(Constants.NAME);
            surnameTextView.setText(savedInstanceState.getString(Constants.SURNAME));
            emailTextView.setText(savedInstanceState.getString(Constants.EMAIL));
        }

        return view;
    }


    @Override
    public void setProfileAsync(String url) {

        Glide.with(this)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        presenter.onImageFailed();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        presenter.onImageFetched();
                        return false;
                    }
                })
                .into(floatingActionButton);

    }

    @Override
    public void setCoverAsync(String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        presenter.onImageFailed();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        presenter.onImageFetched();
                        return false;
                    }
                })
                .into(backImage);
    }

    @Override
    public void setBestResults(String text) {
        bestResultsTextView.setText(text);
    }

    @Override
    public void setCollapsingTootlbarTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setProfileTopName(String topName) {
        topNameTextView.setText(topName);
    }



    @Override
    public void setProfileSurname(String surname) {
        surnameTextView.setText(surname);
    }

    @Override
    public void setProfileEmail(String email) {
        emailTextView.setText(email);
    }


}
