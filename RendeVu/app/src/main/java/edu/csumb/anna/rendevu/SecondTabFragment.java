package edu.csumb.anna.rendevu;

import android.content.Context;
import android.content.Intent;
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

import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

import static com.google.android.gms.cast.CastRemoteDisplayLocalService.startService;

/**
 * Created by Anna on 4/3/17.
 */
public class SecondTabFragment extends Fragment {
    final String TAG = "SecondTabFragment";
    RecyclerView recyclerView;
    ArrayAdapterPlannedDates itemArrayAdapter;
    static PlannedDates plannedDates;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.planned_dates_2_0, container, false);

        ListView listView = (ListView) view.findViewById(R.id.mobile_list);

        //START LISTVIEW CODE
        ///////////////////////////////////////////
        final ArrayList<String> mobileArray = new ArrayList<String>();
        //list of data to display
        RendeVuDB db = new RendeVuDB(MainActivity.getAppContext());

        ArrayList<String> allDates = db.getAllDates();

        //get data from arraylist onto a regular array
        for (String aDate: allDates){
            mobileArray.add(aDate);
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.activity_listview, mobileArray);

        listView.setAdapter(adapter);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                //get from arraylist based on position
                Log.d(TAG, "position clicked: "+mobileArray.get(itemPosition));
                MainActivity.getAppContext().startService(new Intent(MainActivity.getAppContext(), RendeVuService.class));
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
