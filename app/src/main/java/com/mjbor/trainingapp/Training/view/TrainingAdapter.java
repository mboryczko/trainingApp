package com.mjbor.trainingapp.Training.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.model.Circle;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by mjbor on 9/20/2017.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder>
        implements RecyclerViewClickListener {

    private List<Exercise> exercises;
    private List<CircleAdapter> circleAdapterList;
    private Context context;
    private boolean trainingView;

    public TrainingAdapter(List<Exercise> exercises, Context context, boolean trainingView) {
        this.exercises = exercises;
        this.context = context;
        this.trainingView = trainingView;
        circleAdapterList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.exercise_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    public boolean isMainExercise(String name){
        if(name.equals("Squat")){
            return true;
        }
        if(name.equals("Bench press")){
            return true;
        }

        if(name.equals("Barbell row")){
            return true;
        }

        if(name.equals("Over head press")){
            return true;
        }

        if(name.equals("Deadlift")){
            return true;
        }

        return false;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        if(!isMainExercise(exercise.getName()) && !trainingView){
            holder.deleteIV.setVisibility(View.VISIBLE);
        }

        holder.exerciseTitleTextView.setText(exercise.getName());
        holder.weightEditText.setText(Double.toString(exercise.getWeight()));


        List<Integer> reps  = exercise.getReps();


        List<Circle> circles = new ArrayList<>();
        for(int i=0; i< reps.size(); i++){
            //circles.add(new Circle(Color.rgb(205,205,205), 0, reps.get(i)));
            if(trainingView){
                //last training
                //second parameter (value - has to be as it was)
                circles.add(new Circle(Color.rgb(205,205,205), reps.get(i), exercise.getReps_to_do()));
            }

            else {
                //next training
                //second parameter (value - has to be 0)
                circles.add(new Circle(Color.rgb(205,205,205), 0, exercise.getReps_to_do()));
            }

            //circles.add(new Circle(Color.rgb(205,205,205), reps.get(i), exercise.getReps_to_do()));
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

    @Override
    public void recyclerViewListClicked(int row, int position, int value) {
        exercises.get(row).setReps(position, value);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseTitleTextView;
        private TextView weightEditText;
        private RecyclerView circlesRecyclerView;
        private ImageView deleteIV;


        public ViewHolder(View itemView) {
            super(itemView);
            exerciseTitleTextView = (TextView) itemView.findViewById(R.id.exerciseTitleTextView);
            weightEditText = (TextView) itemView.findViewById(R.id.weigtClickableTV);
            circlesRecyclerView = (RecyclerView) itemView.findViewById(R.id.circlesRecyclerView);
            deleteIV = (ImageView) itemView.findViewById(R.id.deleteIV);

            weightEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if(!s.toString().isEmpty()){
                        exercises.get(getLayoutPosition()).setWeight(Double.parseDouble(s.toString()));
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });


            deleteIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exercises.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                   // notifyItemRangeChanged();
                    //notifyDataSetChanged();
                }
            });
        }
    }

}
