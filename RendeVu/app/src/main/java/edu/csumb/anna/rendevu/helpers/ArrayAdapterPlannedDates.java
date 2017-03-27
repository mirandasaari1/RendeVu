package edu.csumb.anna.rendevu.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.ChaperonesActivity;
import edu.csumb.anna.rendevu.MainActivity;
import edu.csumb.anna.rendevu.R;
import edu.csumb.anna.rendevu.StartDateActivity;
import edu.csumb.anna.rendevu.data.PlannedDate;


public class ArrayAdapterPlannedDates extends RecyclerView.Adapter<ArrayAdapterPlannedDates.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<PlannedDate> itemList;

    // Constructor of the class
    public ArrayAdapterPlannedDates(int layoutId, ArrayList<PlannedDate> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView item = holder.item;

        String listText = itemList.get(position).getName() + " " + itemList.get(position).getDateDate()
                + " " + itemList.get(position).getDateTime() + " " + itemList.get(position).getDateLocation();
        item.setText(listText);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item;
        private final Context context;
//        LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);

            context = itemView.getContext();
//            linearLayout = (LinearLayout) itemView.findViewById(R.id.main_layout);

            itemView.setOnClickListener(this);


            item = (TextView) itemView.findViewById(R.id.row_item);

//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(itemView.getContext(), "Position: " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                }
//            });

        }
        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getText());
            Intent intent = new Intent(context, StartDateActivity.class);

            context.startActivity(intent);

        }
    }
}