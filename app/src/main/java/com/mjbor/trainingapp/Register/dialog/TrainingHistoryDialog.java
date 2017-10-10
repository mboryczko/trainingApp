package com.mjbor.trainingapp.Register.dialog;

/**
 * Created by mjbor on 9/23/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.mjbor.trainingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjbor on 9/22/2017.
 */

public class TrainingHistoryDialog extends DialogFragment {

    TrainingHistoryDialog.TrainingHistoryListener listener;
    public interface TrainingHistoryListener{
        public void onDialogPositiveCheck(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    private List<Double> list;

    public List<Double> getList() {
        return list;
    }

    private EditText squat;
    private EditText bench;
    private EditText barbell;
    private EditText ohp;
    private EditText deadlift;
    private CheckBox checkBox;


    public void setEditTexts(View v){
        this.squat = v.findViewById(R.id.squatEditText);
        this.bench = v.findViewById(R.id.benchPressEditText);
        this.barbell = v.findViewById(R.id.barbellRowEditText);
        this.ohp = v.findViewById(R.id.ohpEditText);
        this.deadlift = v.findViewById(R.id.deadliftEditText);
        this.checkBox = v.findViewById(R.id.haventTrainedCheckBox);
    }

    public boolean validate() {
        for(double d : list){
            if(d == 0.0d)
                return false;
        }

        return true;
    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_history, null);
        setEditTexts(view);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    squat.setText("50");
                    bench.setText("40");
                    barbell.setText("20");
                    ohp.setText("20");
                    deadlift.setText("50");

                }

                else {
                    squat.setText("");
                    bench.setText("");
                    barbell.setText("");
                    ohp.setText("");
                    deadlift.setText("");
                }
            }
        });

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        list = new ArrayList<>();
                        list.add(Double.parseDouble(squat.getText().toString()));
                        list.add(Double.parseDouble(bench.getText().toString()));
                        list.add(Double.parseDouble(barbell.getText().toString()));
                        list.add(Double.parseDouble(ohp.getText().toString()));
                        list.add(Double.parseDouble(deadlift.getText().toString()));

                        if(validate()){
                            listener.onDialogPositiveCheck(TrainingHistoryDialog.this);
                        }

                        else {
                            Toast.makeText(getContext(), "Please provide all data", Toast.LENGTH_LONG).show();
                        }


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(TrainingHistoryDialog.this);
                    }
                });


        return builder.create();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (TrainingHistoryDialog.TrainingHistoryListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}

