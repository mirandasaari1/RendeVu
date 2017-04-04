package edu.csumb.anna.rendevu;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
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
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import edu.csumb.anna.rendevu.api.HerokuService;
import edu.csumb.anna.rendevu.data.Location;
import edu.csumb.anna.rendevu.data.PlannedDates;
import edu.csumb.anna.rendevu.helpers.ArrayAdapterPlannedDates;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayAdapterPlannedDates itemArrayAdapter;
    static PlannedDates plannedDates;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://intense-spire-89631.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final HerokuService service = retrofit.create(HerokuService.class);

        plannedDates = new PlannedDates();


        itemArrayAdapter = new ArrayAdapterPlannedDates(R.layout.list_item_with_buton, plannedDates.getAllPlannedDates());
        recyclerView = (RecyclerView) findViewById(R.id.item_list_with_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

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
//                                intent = new Intent(MainActivity.this, AddDateActivity.class);
//                                startActivity(intent);
//                                break;
                            case R.id.action_chaperones:
                                intent = new Intent(MainActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                intent = new Intent(MainActivity.this, DatesActivity.class);
                                startActivity(intent);
                                break;
//                            case R.id.action_current_location:
//                                //adds the intent for the map
//                                intent = new Intent(MainActivity.this, MapsActivity.class);
//                                startActivity(intent);
                            case R.id.action_login_signup:
                                intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

//        final TextView textView = (TextView) findViewById(R.id.response_textview);
//        final Button button = (Button) findViewById(R.id.test_button);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Call<ResponseBody> call = service.hello();
////                call.enqueue(new Callback<ResponseBody>() {
////                    @Override
////                    public void onResponse(Call<ResponseBody> _,
////                                           Response<ResponseBody> response) {
////                        try {
////                            textView.setText(response.body().string());
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                            textView.setText(e.getMessage());
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<ResponseBody> _, Throwable t) {
////                        t.printStackTrace();
////                        textView.setText(t.getMessage());
////                    }
////                });
//
//                Location location = new Location();
//
//                Call<Location> createCall = service.sendLocationData(location);
//                createCall.enqueue(new Callback<Location>() {
//                    @Override
//                    public void onResponse(Call<Location> _, Response<Location> resp) {
//                        Location newLocation = resp.body();
//                        textView.setText("Response: " + newLocation.getlang());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Location> _, Throwable t) {
//                        t.printStackTrace();
//                        textView.setText(t.getMessage());
//                    }
//                });
//
//            }
//        });

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
