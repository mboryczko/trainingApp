package com.mjbor.trainingapp.Main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
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
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener{

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager) ViewPager viewPager;

    private MainPresenter presenter;
    private ISessionManager session;

    private HomeFragment homeFragment;
    private ProgressFragment progressFragment;
    private ProfileFragment profileFragment;

    private MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        session = new SessionManager(this);
        presenter = new MainPresenter(this,session );


        //----------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2);

        setupViewPager(viewPager);


    }


    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        progressFragment = new ProgressFragment();
        profileFragment = new ProfileFragment();

        String token = session.getUserToken();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOKEN, token);
        profileFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);

        adapter.addFragment(homeFragment);
        adapter.addFragment(progressFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
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
        }
        else
        {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
