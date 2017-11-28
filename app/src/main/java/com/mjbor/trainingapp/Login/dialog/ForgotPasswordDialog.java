package com.mjbor.trainingapp.Login.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Register.dialog.TrainingHistoryDialog;

import java.util.ArrayList;

/**
 * Created by mjbor on 11/22/2017.
 */

public class ForgotPasswordDialog extends DialogFragment {

    ForgotPasswordDialog.ForgottenPasswordListener listener;
    public interface ForgottenPasswordListener{
        public void onForgottenClicked(DialogFragment dialog);
    }

    private EditText emailEditText;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_forgot_password, null);

        emailEditText = view.findViewById(R.id.forgottenEmailEditText);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(validate()){
                            listener.onForgottenClicked(ForgotPasswordDialog.this);
                        }

                        else {
                            Toast.makeText(getContext(), "Please provide proper email", Toast.LENGTH_LONG).show();
                        }


                    }
                });


        return builder.create();

    }

    public String getEmail(){
        return emailEditText.getText().toString();
    }

    //TODO
    public boolean validate() {

        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ForgotPasswordDialog.ForgottenPasswordListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
