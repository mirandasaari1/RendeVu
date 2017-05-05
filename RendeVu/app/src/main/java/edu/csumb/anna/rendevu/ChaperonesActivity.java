package edu.csumb.anna.rendevu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.data.PlannedDate;
import edu.csumb.anna.rendevu.helpers.ArrayAdapter;
import edu.csumb.anna.rendevu.helpers.MyCustomAdapter;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Anna on 3/25/17.
 */

public class ChaperonesActivity extends AppCompatActivity{
    final String TAG = "ChaperonesActivity";


    public static int NOTIFICATION_ID = 1;

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
                            case R.id.action_home:
                                intent = new Intent(ChaperonesActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                intent = new Intent(ChaperonesActivity.this, DatesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(ChaperonesActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        Button addChaperone = (Button) findViewById(R.id.addChaperone);

        addChaperone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addChaperoneActivity(v);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        //START LISTVIEW CODE
        ///////////////////////////////////////////
        final ArrayList<String> mobileArray = new ArrayList<String>();
        //list of data to display


        RendeVuDB db = new RendeVuDB(this);
        ArrayList<Chaperone> theChaperones = db.getAllChaperonesFromDB();

        MyCustomAdapter adapter = new MyCustomAdapter(theChaperones, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.mobile_list_chaperones);
        lView.setAdapter(adapter);
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
