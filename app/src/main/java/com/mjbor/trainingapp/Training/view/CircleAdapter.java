package com.mjbor.trainingapp.Training.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

public class CircleAdapter extends  RecyclerView.Adapter<CircleAdapter.ViewHolder>
implements RecyclerViewClickListener{

    private List<Circle> circles;

    private static RecyclerViewClickListener itemListener;

    public CircleAdapter(List<Circle> circles, RecyclerViewClickListener itemListener) {
        this.circles = circles;
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

    @Override
    public void recyclerViewListClicked(View v, int position){
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

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView circle;
        private TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            circle = (ImageView) itemView.findViewById(R.id.circleImageView);
            number = (TextView) itemView.findViewById(R.id.numberTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }

}