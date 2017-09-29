package com.mjbor.trainingapp.Training.plateCalculator;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjbor on 9/23/2017.
 */

/**
 * Created by mjbor on 9/22/2017.
 */

public class PlateCalculatorDialog extends DialogFragment
implements View.OnClickListener{

    PlateCalculatorDialog.PlateCalculatorListener listener;
    public interface PlateCalculatorListener{
        public void onDialogPositiveCheck(DialogFragment dialog, double weight);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private double weight;
    private PlateCalculator calculator;
    @BindView(R.id.exerciseWeight) EditText weightET;
    @BindView(R.id.minusTV) TextView minus;
    @BindView(R.id.plusTV) TextView plus;
    @BindView(R.id.instructionsTV) TextView instructionsTV;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.plate_calculator_dialog, null);
        ButterKnife.bind(this, view);

        weight = getArguments().getDouble(Constants.WEIGHT);
        weightET.setText(Double.toString(weight));
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        refreshInstructions();


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                            listener.onDialogPositiveCheck(PlateCalculatorDialog.this, weight);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(PlateCalculatorDialog.this);
                    }
                });


        return builder.create();

    }

    public void refreshInstructions(){
        calculator = new PlateCalculator(weight);
        instructionsTV.setText(calculator.getResult());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.minusTV:
                weight -= 2.5;
                break;
            case R.id.plusTV:
                weight += 2.5;
                break;
        }

        weightET.setText(Double.toString(weight));
        refreshInstructions();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (PlateCalculatorListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}


