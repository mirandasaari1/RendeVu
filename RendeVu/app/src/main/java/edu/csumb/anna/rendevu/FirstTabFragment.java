package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.PlannedDate;
import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;
import edu.csumb.anna.rendevu.helpers.PastDatesCustomAdapter;
import edu.csumb.anna.rendevu.helpers.PlannedDatesCustomAdapter;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Anna on 4/3/17.
 */
public class FirstTabFragment extends Fragment {
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
        RendeVuDB db = new RendeVuDB(DatesActivity.getAppContext());

        ArrayList<PlannedDate> pastDates = db.getPastDates();

//        //get data from arraylist onto a regular array
//        for (String aDate: allDates){
//            mobileArray.add(aDate);
//        }

        final Button addDateButton = (Button) view.findViewById(R.id.addDatePlanned);
        addDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //posts to end date endpoint;
                Intent intent = new Intent(DatesActivity.getAppContext(), AddDateActivity.class);
                startActivity(intent);
            }
        });
        //ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.activity_listview, mobileArray);

        PastDatesCustomAdapter adapter = new PastDatesCustomAdapter(pastDates, DatesActivity.getAppContext());
        listView.setAdapter(adapter);
//
//        plannedDates = new PlannedDates();
//
//        itemArrayAdapter = new ArrayAdapterPlannedDates(R.layout.past_dates_item_list, plannedDates.getAllPlannedDates());
//        recyclerView = (RecyclerView) view.findViewById(R.id.item_list_with_button);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(itemArrayAdapter);

        return view;

    }
}
