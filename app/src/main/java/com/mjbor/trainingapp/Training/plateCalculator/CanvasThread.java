package com.mjbor.trainingapp.Training.plateCalculator;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by mjbor on 9/30/2017.
 */

public class CanvasThread extends Thread {

    private int FPS = 5;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private PlateCalculatorDialog dialog;
    private boolean running;
    public static Canvas canvas;


    public CanvasThread(SurfaceHolder surfaceHolder, PlateCalculatorDialog dialog){
        this.surfaceHolder = surfaceHolder;
        this.dialog = dialog;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.dialog.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;

                Log.e("canvasThread", ""+averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}
