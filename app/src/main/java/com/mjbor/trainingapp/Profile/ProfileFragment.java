package com.mjbor.trainingapp.Profile;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.mjbor.trainingapp.Utils.Constants.REQUEST_GALLERY_CODE;

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

    private Uri uri;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String token = getArguments().getString(Constants.TOKEN, null);
        this.presenter = new ProfilePresenter(this, token);

    }

    @Override
    public void openGallery(){
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public boolean checkPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK){
                uri = data.getData();
                String filePath = getRealPathFromURIPath(uri, getActivity());
                File file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);

                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


                presenter.uploadImge(fileToUpload, filename);
            }else {
                requestPermissions();
        }

    }

    @Override
    public RequestBody createTextRequestBody(String text){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), text);

        return requestBody;
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.imageClicked();
            }
        });

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
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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
