package com.mjbor.trainingapp.TrainingShow;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.model.Circle;
import com.mjbor.trainingapp.Training.view.CircleAdapter;
import com.mjbor.trainingapp.Training.view.RecyclerViewClickListener;
import com.mjbor.trainingapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjbor on 9/20/2017.
 */

public class TrainingShowAdapter extends RecyclerView.Adapter<TrainingShowAdapter.ViewHolder>
        implements RecyclerViewClickListener {

    private List<Exercise> exercises;
    private List<CircleAdapter> circleAdapterList;
    private Context context;

    public TrainingShowAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;

        circleAdapterList = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.exercise_show_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void recyclerViewListClicked(int row, int position, int value) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.exerciseTitleTextView.setText(exercise.getName());
        holder.weightEditText.setText(Double.toString(exercise.getWeight()));


        List<Integer> reps  = exercise.getReps();


        List<Circle> circles = new ArrayList<>();
        for(int i=0; i< reps.size(); i++){
            circles.add(new Circle(Color.rgb(205,205,205), reps.get(i), reps.get(i)));
            //reps.set(i, -1);
        }

        holder.circlesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.circlesRecyclerView.setLayoutManager(llm);

        CircleAdapter adapter = new CircleAdapter(circles, this, position);
        circleAdapterList.add(adapter);
        holder.circlesRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseTitleTextView;
        private TextView weightEditText;
        private RecyclerView circlesRecyclerView;


        public ViewHolder(View itemView) {
            super(itemView);
            exerciseTitleTextView = (TextView) itemView.findViewById(R.id.exerciseTitleTextView);
            weightEditText = (TextView) itemView.findViewById(R.id.weigtClickableTV);
            circlesRecyclerView = (RecyclerView) itemView.findViewById(R.id.circlesRecyclerView);

        }
    }

}
