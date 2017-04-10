package edu.csumb.anna.rendevu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;

/**
 * Created by Anna on 4/3/17.
 */
public class FirstTabFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayAdapterPlannedDates itemArrayAdapter;
    static PlannedDates plannedDates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.planned_dates, container, false);

        plannedDates = new PlannedDates();

        itemArrayAdapter = new ArrayAdapterPlannedDates(R.layout.past_dates_item_list, plannedDates.getAllPlannedDates());
        recyclerView = (RecyclerView) view.findViewById(R.id.item_list_with_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        return view;

    }
}
