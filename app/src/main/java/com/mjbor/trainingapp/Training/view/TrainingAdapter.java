package com.mjbor.trainingapp.Training.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.model.Circle;
import com.mjbor.trainingapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjbor on 9/20/2017.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private Context context;

    public TrainingAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.exercise_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.exerciseTitleTextView.setText(exercise.getName());
        holder.weightEditText.setText(exercise.getWeight());

        String[] reps  = exercise.getReps();
        //TODO

        List<Circle> circles = new ArrayList<>();
        for(String s : reps){
            circles.add(new Circle(Color.GRAY, 0));
        }

        holder.circlesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.circlesRecyclerView.setLayoutManager(llm);

        CircleAdapter adapter = new CircleAdapter(circles, this);
        holder.circlesRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseTitleTextView;
        private EditText weightEditText;
        private RecyclerView circlesRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            exerciseTitleTextView = (TextView) itemView.findViewById(R.id.exerciseTitleTextView);
            weightEditText = (EditText) itemView.findViewById(R.id.weightEditText);
            circlesRecyclerView = (RecyclerView) itemView.findViewById(R.id.circlesRecyclerView);
        }
    }

}
