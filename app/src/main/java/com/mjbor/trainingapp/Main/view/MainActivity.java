package com.mjbor.trainingapp.Main.view;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mjbor.trainingapp.Home.HomeFragment;
import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.presenter.MainPresenter;
import com.mjbor.trainingapp.Profile.ProfileFragment;
import com.mjbor.trainingapp.Progress.ProgressFragment;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.view.TrainingActivity;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.app.TrainingApplication;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener,
        SwipeRefreshLayout.OnRefreshListener,
        HomeFragment.OnTrainingFetched{

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager) ViewPager viewPager;
    public @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    private MainPresenter presenter;

    @Inject
    ISessionManager session;

    private HomeFragment homeFragment;
    private ProgressFragment progressFragment;
    private ProfileFragment profileFragment;

    private MenuItem prevMenuItem;
    private int currentPage;
    private ViewPagerAdapter adapter;

    private Training training;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((TrainingApplication)getApplication()).getAppComponent().inject(this);
//        session = new SessionManager(this);
        presenter = new MainPresenter(this, session);

        //----------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);

        setupViewPager(viewPager);
        swipeRefreshLayout.setOnRefreshListener(this);


    }

    @Override
    public void onTrainingFetched(Training training) {
        presenter.gotLastTraining(training);
    }



    @Override
    public void onRefresh() {

        Log.e(this.getClass().toString(), "onRefresh");
        Log.e(this.getClass().toString(), "onRefresh");
        //setupViewPager(viewPager);
        switch(currentPage){
            case 0:
                homeFragment.refreshData();
                break;
            case 1:
                //progressFragment
                break;
            case 2:
                profileFragment.refreshData();
                break;

        }

        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }





    @Override
    public void killApp() {
        this.finishAffinity();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();

    }







    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        progressFragment = new ProgressFragment();
        profileFragment = new ProfileFragment();

        String token = session.getUserToken();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOKEN, token);
        profileFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);
        progressFragment.setArguments(bundle);

        adapter.addFragment(homeFragment);
        adapter.addFragment(progressFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }

    public void logoutClicked(View v) {
        presenter.logoutClicked();
    }

    public void lastTrainingClicked(View v){
        presenter.lastTrainingClicked();
    }



    public void trainingClicked(View v) {
        Intent i = new Intent(this, TrainingActivity.class);
        //TODO
        startActivity(i);
    }

    @Override
    public void openLastTraining(Training training) {
        Intent i = new Intent(this, TrainingActivity.class);
        i.putExtra(Constants.TRAINING, training);
        startActivity(i);
    }

    @Override
    public void onLogout() {
        LoginManager.getInstance().logOut();
        Intent i = new Intent(this, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        startActivity(i);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_progress:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_profile:
                viewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    progressFragment.presenter.storagePermissionGranted();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    progressFragment.presenter.storagePermissionGranted();
                    profileFragment.presenter.readPermissionGranted();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
