package com.mjbor.trainingapp.Training.plateCalculator;

/**
 * Created by mjbor on 9/25/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mjbor on 9/23/2017.
 */

/**
 * Created by mjbor on 9/22/2017.
 */

public class PlateCalculatorDialog extends DialogFragment
implements View.OnClickListener,
    IPlateCalculatorView{

    PlateCalculatorDialog.PlateCalculatorListener listener;
    public interface PlateCalculatorListener{
        public void onDialogPositiveCheck(DialogFragment dialog, double weight);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    @BindView(R.id.exerciseWeight) EditText weightET;
    @BindView(R.id.minusTV) TextView minus;
    @BindView(R.id.plusTV) TextView plus;
    @BindView(R.id.instructionsTV) TextView instructionsTV;
    @BindView(R.id.container) FrameLayout container;


    private boolean created;
    private PlateCalculator calculator;
    private Paint paint;
    private PlateCalculatorPresenter presenter;
    private Canvas canvas;
    private double weight;


    @Override
    public void setCreated(boolean b) {
        this.created = b;
    }

    @Override
    public void drawRectangle(int left, int top, int right, int bottom) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void setColor(String hex) {
        paint.setColor(Color.parseColor(hex));
    }

    @Override
    public void recreateSurfaceView(){
        container.removeAllViews();
        SurfaceView surface = new SurfaceView(getActivity());
        container.addView(surface);
        surface.setZOrderOnTop(true);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(!created){
                    holder.setFormat(PixelFormat.TRANSPARENT);
                    Canvas canvas = holder.lockCanvas();
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                    created = true;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    public void draw(Canvas canvas){
        this.canvas = canvas;
        presenter.draw(canvas.getWidth(), canvas.getHeight());
    }



    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        calculator = new PlateCalculator(weight);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.plate_calculator_dialog, null);
        ButterKnife.bind(this, view);

        weight = getArguments().getDouble(Constants.WEIGHT);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        presenter = new PlateCalculatorPresenter(this, weight);

        weightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    weight = Double.parseDouble(s.toString());
                    presenter.weightChanged(weight);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

        });


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

    @Override
    public void setInstructions(String instructions) {
        instructionsTV.setText(instructions);
    }

    @Override
    public void setWeightLabel(double weight) {
        weightET.setText(Double.toString(weight));
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.minusTV:
                presenter.minusClicked();
                break;
            case R.id.plusTV:
                presenter.plusClicked();
                break;
        }
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


