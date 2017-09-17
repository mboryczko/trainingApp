package com.mjbor.trainingapp.Main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mjbor.trainingapp.Home.HomeFragment;
import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.presenter.MainPresenter;
import com.mjbor.trainingapp.Profile.ProfileFragment;
import com.mjbor.trainingapp.Progress.ProgressFragment;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView,
        BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    private MainPresenter presenter;
    private ISessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        session = new SessionManager(this);
        presenter = new MainPresenter(this,session );
        setUpSpecificFragment(new HomeFragment());


        //----------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void setUpSpecificFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commit();
    }

    public void logoutClicked(View v){
        presenter.logoutClicked();
    }

    @Override
    public void onLogout() {
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
                item.setChecked(true);
                setUpSpecificFragment(new HomeFragment());
                break;
            case R.id.action_progress:
                item.setChecked(true);
                setUpSpecificFragment(new ProgressFragment());
                break;
            case R.id.action_profile:
                item.setChecked(true);
                setUpSpecificFragment(new ProfileFragment());
                break;
        }
        return false;
    }
}
