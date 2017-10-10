package com.mjbor.trainingapp.Home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjbor.trainingapp.Profile.ProfilePresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.Training;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHomeFragment {

    @BindView(R.id.exerciseTitle1) TextView ex1;
    private HomePresenter presenter;
    private String token;
    OnTrainingFetched onTrainingFetched;

    public interface OnTrainingFetched {
        public void onTrainingFetched(Training training);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onTrainingFetched = (OnTrainingFetched) context;
    }

    @Override
    public void trainingFetched(Training trainingFetched) {
        onTrainingFetched.onTrainingFetched(trainingFetched);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        token = getArguments().getString(Constants.TOKEN, null);
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
    public void refreshData() {
        presenter.getLastTrainingInfo();
    }


}
