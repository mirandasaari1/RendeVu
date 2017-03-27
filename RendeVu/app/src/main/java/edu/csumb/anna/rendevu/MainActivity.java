package edu.csumb.anna.rendevu;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayAdapterPlannedDates itemArrayAdapter;
    static PlannedDates plannedDates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plannedDates = new PlannedDates();


        itemArrayAdapter = new ArrayAdapterPlannedDates(R.layout.list_item_with_buton, plannedDates.getAllPlannedDates());
        recyclerView = (RecyclerView) findViewById(R.id.item_list_with_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.action_home:

                            case R.id.action_add_date:
                                intent = new Intent(MainActivity.this, AddDateActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_chaperones:
                                intent = new Intent(MainActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_past_dates:

                            case R.id.action_planned_dates:

                        }
                        return true;
                    }
                });


    }

    public void startDateActivity(View view) {
        Intent intent = new Intent(MainActivity.this, StartDateActivity.class);
        startActivity(intent);
    }

//    public void startAddDateActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, AddDateActivity.class);
//        startActivity(intent);
//    }
//
//    public void startPastDatesActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, PastDatesActivity.class);
//        startActivity(intent);
//    }
//
//    public void startPlannedDatesActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, PlannedDatesActivity.class);
//        startActivity(intent);
//    }
}
