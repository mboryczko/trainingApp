package com.mjbor.trainingapp.Training.newExercise;

/**
 * Created by mjbor on 9/25/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mjbor on 9/23/2017.
 */

public class NewExerciseDialog extends DialogFragment {

    NewExerciseDialog.NewExerciseListener listener;
    public interface NewExerciseListener{
        public void onDialogPositiveCheck(DialogFragment dialog, Exercise exercise);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    @BindView(R.id.newExerciseName) EditText newExerciseName;
    @BindView(R.id.newExerciseReps) EditText newExerciseReps;
    @BindView(R.id.newExerciseSets) EditText newExerciseSets;
    @BindView(R.id.newExerciseWeight) EditText newExerciseWeight;
    private String message;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.new_exercise_dialog, null);
        ButterKnife.bind(this, view);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(NewExerciseDialog.this);
                    }
                });


        return builder.create();

    }


    @Override
    public void onResume(){
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(validate()){
                        int reps = Integer.parseInt(newExerciseReps.getText().toString());
                        int sets = Integer.parseInt(newExerciseSets.getText().toString());
                        double weight = Double.parseDouble(newExerciseWeight.getText().toString());
                        String name = newExerciseName.getText().toString();
                        List<Integer> list = generateNewReps(sets, reps);


                        Exercise exercise = new Exercise(list, weight, name, reps);
                        listener.onDialogPositiveCheck(NewExerciseDialog.this, exercise);
                        d.dismiss();
                    }

                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }


    public boolean validate(){

        message = "";

        if(newExerciseName.getText().toString().equals("")){
            message = "Invalid exercise name";
            return false;
        }

        if(newExerciseReps.getText().toString().equals("")){
            message = "Invalid reps number";
            return false;
        }

        if(newExerciseSets.getText().toString().equals("")){
            message = "Invalid sets number";
            return false;
        }


        if(newExerciseWeight.getText().toString().equals("")){
            message = "Invalid weight";
            return false;
        }

        return true;
    }

    public List<Integer> generateNewReps(int sets, int reps){
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<sets; i++){
            list.add(reps);
        }

        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NewExerciseListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}


