package edu.csumb.anna.rendevu.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.ChaperonesActivity;
import edu.csumb.anna.rendevu.R;
import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Sal on 5/2/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Chaperone> list = new ArrayList<Chaperone>();
    private Context context;

    final String TAG = "MyCustomAdapter";

    public MyCustomAdapter(ArrayList<Chaperone> list, Context context) {
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
            view = inflater.inflate(R.layout.listview_edit_delete, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.label);
        listItemText.setText("name: "+list.get(position).getChaperoneName());

        //Handle TextView and display string from your list
        TextView listItemTextNum = (TextView)view.findViewById(R.id.phoneNumber);
        listItemTextNum.setText("phone: "+list.get(position).getChaperoneNumber());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button editBtn = (Button)view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.d(TAG, "delete clicked");
                Log.d(TAG, ""+list.get(position).getChaperoneID());

                //delete from db
                RendeVuDB db = new RendeVuDB(context);
                db.deleteChaperoneFromDB(list.get(position).getChaperoneID());

                //show it
                ArrayList<Chaperone> theChaperones = db.getAllChaperonesFromDB();

                list = theChaperones;
                //list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.d(TAG, "edit clicked");
                Log.d(TAG, list.get(position).getChaperoneName());
                Log.d(TAG, ""+list.get(position).getChaperoneID());

                notifyDataSetChanged();
            }
        });

        return view;
    }
}
