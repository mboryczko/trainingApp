package com.mjbor.trainingapp.Training.plateCalculator;

/**
 * Created by mjbor on 9/25/2017.
 */

import android.app.Activity;
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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

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


    @BindView(R.id.exerciseWeight) EditText weightET;
    @BindView(R.id.minusTV) TextView minus;
    @BindView(R.id.plusTV) TextView plus;
    @BindView(R.id.instructionsTV) TextView instructionsTV;
    @BindView(R.id.surface) SurfaceView surface;

    private double weight;
    private PlateCalculator calculator;
    private int canvasWidth, canvasHeight;
    private Paint paint;
    private int padding = 32;


    private int barWidth, barHeight;
    private int startPlateX, startPlateY;
    private int barX, barY;
    private boolean created;


    public void draw(Canvas canvas){
/*        int rectWidth = canvasWidth/3;
        int rectHeight = canvasHeight/3;
        int startX = (canvasWidth/2)-(rectWidth/2);
        int startY = (canvasHeight/2) - (rectHeight/2);*/

        calculator = new PlateCalculator(weight);
        drawBar(canvas);
        drawPlateSeparator(canvas);
        drawPlates(canvas);
    }


    public void drawPlates(Canvas canvas){
        int plateWidth = (int)(canvasWidth * 0.05);
        int plateHeight;

        Map<Double, Integer> plates = calculator.getPlates();

        for (Map.Entry<Double, Integer> entry : plates.entrySet()) {
            Double weight = entry.getKey();
            Integer platesCount = entry.getValue();
            paint.setColor(getPlateColor(weight));


            plateHeight = (int)(canvasHeight * 0.1 * Math.sqrt(weight));
            startPlateY = (canvasHeight/2) - (plateHeight/2);


            for(int i = 0; i < platesCount; i++){
                canvas.drawRect(startPlateX, startPlateY, startPlateX+plateWidth, startPlateY+plateHeight, paint);
                startPlateX += plateWidth;

            }
        }
    }

    public int getPlateColor(double weight){
        if(weight == 20d){
            return Color.RED;
        }

        if(weight == 10d){
            return Color.YELLOW;
        }

        if(weight == 5d){
            return Color.GREEN;
        }

        if(weight == 2.5d){
            return Color.BLUE;
        }


        return Color.BLACK;

    }

    public void drawPlateSeparator(Canvas canvas){
        int separatorWidth = (int)(canvasWidth * 0.1);
        int separatorHeight = barHeight * 3;
        int separatorX = padding + (barWidth / 3);
        int separatorY = (canvasHeight/2) - (separatorHeight/2) ;

        startPlateX = separatorX+separatorWidth;
        startPlateY = separatorY+separatorHeight;
        paint.setColor(Color.GRAY);
        canvas.drawRect(separatorX, separatorY, separatorX+separatorWidth, separatorY+separatorHeight, paint);
    }


    public void drawBar(Canvas canvas){
        barWidth = (int)(canvasWidth * 0.7);
        barHeight = (int)(canvasHeight * 0.06);
        barX = padding;
        barY = (canvasHeight/2) - (barHeight/2) ;
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(barX, barY, barX+barWidth, barY+barHeight, paint);
    }

    /*@Override
    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int dialogWidth = width - 50; // specify a value here
        int dialogHeight = height; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        // ... other stuff you want to do in your onStart() method
    }*/

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        calculator = new PlateCalculator(weight);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.plate_calculator_dialog, null);
        ButterKnife.bind(this, view);


        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        weight = getArguments().getDouble(Constants.WEIGHT);
        weightET.setText(Double.toString(weight));
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        refreshInstructions();


        surface.setZOrderOnTop(true);
        canvasWidth = surface.getWidth();
        canvasHeight = surface.getHeight();

        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if(!created){
                    holder.setFormat(PixelFormat.TRANSPARENT);
                    Canvas canvas = holder.lockCanvas();
                    canvasWidth = canvas.getWidth();
                    canvasHeight = canvas.getHeight();
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


