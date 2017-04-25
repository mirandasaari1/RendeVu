package edu.csumb.anna.rendevu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;

/**
 * Created by Anna on 4/3/17.
 */
public class SecondTabFragment extends Fragment {
    final String TAG = "SecondTabFragment";
    RecyclerView recyclerView;
    ArrayAdapterPlannedDates itemArrayAdapter;
    static PlannedDates plannedDates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.planned_dates_2_0, container, false);

        ListView listView = (ListView) view.findViewById(R.id.mobile_list);

        //START LISTVIEW CODE
        ///////////////////////////////////////////
        final ArrayList<String> mobileArray = new ArrayList<String>();
        //list of data to display

//        ArrayList<User> users = db.getUsers();

        //get data from arraylist onto a regular array
//        for (User aUser: users){
//
//            Log.d(TAG, aUser.getUsername());
//            mobileArray.add("username: "+aUser.getUsername());
//            mobileArray.add("password: "+aUser.getPassword());
//
//            mobileArray.add("");
//        }

        mobileArray.add("derp");
        mobileArray.add("herp");

        ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.activity_listview, mobileArray);

        listView.setAdapter(adapter);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                //get from arraylist based on position
                Log.d(TAG, "position clicked: "+mobileArray.get(itemPosition));
            }
        });
        //END LISTVIEW CODE
        ////////////////////////////////////////////
//        plannedDates = new PlannedDates();
//
//        itemArrayAdapter = new ArrayAdapterPlannedDates(R.layout.list_item_with_buton, plannedDates.getAllPlannedDates());
//        recyclerView = (RecyclerView) view.findViewById(R.id.item_list_with_button);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(itemArrayAdapter);

        return view;

    }


}
