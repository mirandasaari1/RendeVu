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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.helpers.ArrayAdapter;
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

    }

    @Override
    public void onResume(){
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.mobile_list_chaperones);

        //START LISTVIEW CODE
        ///////////////////////////////////////////
        final ArrayList<String> mobileArray = new ArrayList<String>();
        //list of data to display

        RendeVuDB db = new RendeVuDB(this);
        ArrayList<Chaperone> theChaperones = db.getAllChaperonesFromDB();

        for(Chaperone chap: theChaperones){
            Log.d(TAG, chap.getChaperoneName());
            Log.d(TAG, chap.getChaperoneNumber());
            String temp = "Name: "+chap.getChaperoneName()+"\n";
            temp += "Number: "+chap.getChaperoneNumber();
            mobileArray.add(temp);
        }

        android.widget.ArrayAdapter adapter = new android.widget.ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);

        listView.setAdapter(adapter);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                //get from arraylist based on position
                Log.d(TAG, "position clicked: "+mobileArray.get(itemPosition));
                //MainActivity.getAppContext().startService(new Intent(MainActivity.getAppContext(), RendeVuService.class));
            }
        });
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
