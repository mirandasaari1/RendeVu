package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.helpers.ArrayAdapter;

/**
 * Created by Anna on 3/25/17.
 */

public class ChaperonesActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    static Chaperones chaperones;
    ArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaperones);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView)  bottomNavigationView.getChildAt(0);
        for(int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(false);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
//                            case R.id.action_add_date:
//                                intent = new Intent(ChaperonesActivity.this, AddDateActivity.class);
//                                startActivity(intent);
//                                break;
                            case R.id.action_chaperones:
                                intent = new Intent(ChaperonesActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                break;

//                            case R.id.action_current_location:
//                                //adds the intent for the map
//                                intent = new Intent(ChaperonesActivity.this, MapsActivity.class);
//                                startActivity(intent);

                        }
                        return true;
                    }
                });

        chaperones = new Chaperones();

        // Initializing list view with the custom adapter
        itemArrayAdapter = new ArrayAdapter(R.layout.list_item, chaperones.getAllChaperones());
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        int listSize = chaperones.getAllChaperones().size();

        for (int i = 0; i<listSize; i++){
            Log.i("name: ", chaperones.getAllChaperones().get(i).getChaperoneName());
            Log.i("nnum: ", chaperones.getAllChaperones().get(i).getChaperoneNumber());

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        itemArrayAdapter.notifyDataSetChanged();
    }

    public void removeButtonClicked() {
        Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
    }

    public void addChaperoneActivity(View view)
    {
        Intent intent = new Intent(ChaperonesActivity.this, AddChaperoneActivity.class);
        startActivity(intent);
    }
}
