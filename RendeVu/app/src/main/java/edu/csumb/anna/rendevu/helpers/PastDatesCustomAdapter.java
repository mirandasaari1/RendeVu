package edu.csumb.anna.rendevu.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.csumb.anna.rendevu.ChaperonesActivity;
import edu.csumb.anna.rendevu.DatesActivity;
import edu.csumb.anna.rendevu.R;
import edu.csumb.anna.rendevu.RendeVuService;
import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.data.PlannedDate;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sal on 5/2/2017.
 */

public class PastDatesCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<PlannedDate> list = new ArrayList<PlannedDate>();
    private Context context;

    final String TAG = "PlannedDatesCA";

    public PastDatesCustomAdapter(ArrayList<PlannedDate> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_past_dates, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.label);
        listItemText.setText("name: "+list.get(position).getName());

        //Handle TextView and display string from your list
        TextView listItemTextNum = (TextView)view.findViewById(R.id.info);
        listItemTextNum.setText("info: "+list.get(position).getDateInformation());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button startBtn = (Button)view.findViewById(R.id.start_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //Log.d(TAG, "delete clicked");
                //Log.d(TAG, ""+list.get(position).getId());

                //delete from db
                RendeVuDB db = new RendeVuDB(context);
                db.deleteDateFromDB(list.get(position).getId());
                toastIt("date deleted");
                //show it
                ArrayList<PlannedDate> theChaperones = db.getPastDates();
                list = theChaperones;
                //list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        return view;
    }
    public void toastIt(String aMessage){
        Toast.makeText(DatesActivity.getAppContext(), aMessage,
                Toast.LENGTH_SHORT).show();
    }
}
