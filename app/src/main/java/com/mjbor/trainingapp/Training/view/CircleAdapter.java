package com.mjbor.trainingapp.Training.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.model.Circle;
import com.mjbor.trainingapp.models.Exercise;

import java.util.List;

/**
 * Created by mjbor on 9/20/2017.
 */

public class CircleAdapter extends  RecyclerView.Adapter<CircleAdapter.ViewHolder>{

    private List<Circle> circles;
    private int row;

    public List<Circle> getCircles() {
        return circles;
    }

    public void setCircles(List<Circle> circles) {
        this.circles = circles;
    }

    private static RecyclerViewClickListener itemListener;

    public CircleAdapter(List<Circle> circles,RecyclerViewClickListener itemListener, int row) {
        this.circles = circles;
        this.row = row;
        this.itemListener = itemListener;
    }

    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.circle_row, parent, false);

        CircleAdapter.ViewHolder viewHolder = new CircleAdapter.ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return circles.size();
    }

    @Override
    public void onBindViewHolder(CircleAdapter.ViewHolder holder, int position) {
        Circle circle = circles.get(position);

        holder.circle.setColorFilter(circle.getColor());
        if(circle.getValue() > 0){
            holder.number.setVisibility(View.VISIBLE);
            holder.number.setText(Integer.toString(circle.getValue()));
        }
        else{
            holder.number.setVisibility(View.GONE);
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{
        private ImageView circle;
        private TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            circle = (ImageView) itemView.findViewById(R.id.circleImageView);
            number = (TextView) itemView.findViewById(R.id.numberTextView);


            circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();


                    Circle circle = circles.get(position);
                    int value = circle.getValue();
                    int startValue = circle.getStartValue();
                    int newValue = -1;

                    if(value == 0){
                        circle.setColor(Color.RED);
                        circle.setValue(startValue);
                        newValue = startValue;
                    }

                    if(value == 1){
                        circle.setValue(0);
                        circle.setColor(Color.GRAY);
                        newValue = 0;
                    }

                    if(value > 1){
                        circle.setValue(--value);
                        newValue = value;
                    }

                    onBindViewHolder(ViewHolder.this, position);
                    itemListener.recyclerViewListClicked(row, getLayoutPosition(), Integer.toString(newValue));
                }
            });
//int row, int position, String value


        }

        /*@Override
        public void onClick(View view) {
            //itemListener.recyclerViewListClicked(view, this.getLayoutPosition());

            int position = getLayoutPosition();

            Circle circle = circles.get(position);
            int value = circle.getValue();
            if(value == 0){
                circle.setColor(Color.RED);
                circle.setValue(5);
            }

            if(value == 1){
                circle.setValue(0);
                circle.setColor(Color.GRAY);
            }

            if(value > 1){
                circle.setValue(--value);
            }
        }*/
    }

}
