package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Anna on 3/25/17.
 */

public class ChaperonesActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    static Chaperones chaperones;
    ChaperoneArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaperones);

        // Initializing list view with the custom adapter

        chaperones = new Chaperones();

        itemArrayAdapter = new ChaperoneArrayAdapter(R.layout.list_item, chaperones.getAllChaperones());
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        // Populate list items with existing chaperones
//        for (int i = 0; i < 10; i++) {
//            itemList.add(new Chaperone("item " + i));
//        }


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

    public void addChaperoneActivity(View view)
    {
        Intent intent = new Intent(ChaperonesActivity.this, AddChaperoneActivity.class);
        startActivity(intent);
    }
}
