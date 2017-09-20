package com.mjbor.trainingapp.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjbor.trainingapp.Profile.ProfilePresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHomeFragment {

    @BindView(R.id.exerciseTitle1) TextView ex1;
    @BindView(R.id.exerciseTitle2) TextView ex2;
    @BindView(R.id.exerciseTitle3) TextView ex3;
    @BindView(R.id.exerciseTitle4) TextView ex4;

    private HomePresenter presenter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String token = getArguments().getString(Constants.TOKEN, null);
        this.presenter = new HomePresenter(this, token);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void setEx1(String text) {
        ex1.setVisibility(View.VISIBLE);
        ex1.setText(text);
    }

    @Override
    public void setEx2(String text) {
        ex2.setVisibility(View.VISIBLE);
        ex2.setText(text);
    }

    @Override
    public void setEx3(String text) {
        ex3.setVisibility(View.VISIBLE);
        ex3.setText(text);
    }

    @Override
    public void setEx4(String text) {
        ex4.setVisibility(View.VISIBLE);
        ex4.setText(text);
    }



}
