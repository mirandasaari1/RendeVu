package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
