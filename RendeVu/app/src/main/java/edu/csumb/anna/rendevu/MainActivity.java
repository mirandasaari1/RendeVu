package edu.csumb.anna.rendevu;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Random;

import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.api.TextMessageAPI;
import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.data.PlannedDate;
import edu.csumb.anna.rendevu.data.TextMessageResponse;
import edu.csumb.anna.rendevu.storage.RendeVuDB;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    private static Context mContext;
    //0 is permission granted
    //int locationPermission = 1;

    public static final String BASE_URL = "https://rendevu.herokuapp.com/";
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

//    private int notificationId = 1;
    public static int NOTIFICATION_ID = 1;

    String userID = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //sendNotification();
        //startRendeVuService();
        //db.insertDate("derp", "22", "33", "5", "first date");

        /*for(Chaperone chap: theChaperones){
            Log.d(TAG, chap.getChaperoneName());
            Log.d(TAG, chap.getChaperoneNumber());
            String temp = "Name: "+chap.getChaperoneName()+"\n";
            temp += "Number: "+chap.getChaperoneNumber();
        }*/

        //temporary code to add dates to db
        /////////////////////////////////////

        SharedPreferences userDetails = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        userID = userDetails.getString("userID", "no ID");
        toastIt("current user: "+userID);
        /////////////////////////////////////
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
                            case R.id.action_chaperones:
                                intent = new Intent(MainActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                intent = new Intent(MainActivity.this, DatesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(MainActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        final Button button = (Button) findViewById(R.id.add_date_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //api call
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit1 = builder.client(httpClient.build()).build();

                TextMessageAPI client = retrofit1.create(TextMessageAPI.class);

                Call<TextMessageResponse> call = client.makeRequest("1", "8314285108", "hi");

                call.enqueue(new Callback<TextMessageResponse>() {
                    @Override
                    public void onResponse(Call<TextMessageResponse> call, Response<TextMessageResponse> response) {
                        Toast.makeText(MainActivity.this, "API CALL SUCCESS", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TextMessageResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "API CALL FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(MainActivity.this, AddDateActivity.class);
                startActivity(intent);
            }
        });

        final Button endDateButton = (Button) findViewById(R.id.endDateButton);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //posts to end date endpoint
                RendeVuAPI api = new RendeVuAPI();
                api.postEndDate(userID, MainActivity.this);
                stopRendeVuService();
            }
        });

        //to access dev tools in chrome and see the database contents
        Stetho.initializeWithDefaults(this);

        //CHECK FOR PERMISSIONS
        ///////////////////////////////////
        //check for internet permission
        /////////////////////////////////////////////
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET);
        Log.d(TAG, "INTERNET permission " + permissionCheck);

        int permissionCheck3 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "FINE location permission " + permissionCheck3);

        // Assume thisActivity is the current activity
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        Log.d(TAG, "COARSE location permission " + permissionCheck2);

        ///////////////////////////////////////////

        //checks if the user has enabled the right permission
        ////////////////////////////////////////////////////
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissions();
        } else {//permission is good
            //startRendeVuService();
        }

        //toastIt("locationPermission: "+locationPermission);

        //to get rid of the notification when a button has been clicked
    }

    public void checkPermissions() {
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "FINE location permission " + permissionCheck);

        // Assume thisActivity is the current activity
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        Log.d(TAG, "COARSE location permission " + permissionCheck2);

        //NEW
        //ASKS FOR THE PERMISSIONS
        //////////////////////////////////
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        ////////////////////////////////////////////
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.d(TAG, "permission granted");
                    //locationPermission = PackageManager.PERMISSION_GRANTED;

                    //goes to the search portion
                    //onSearch();
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
//                    mGoogleApiClient.connect();
//                    mMap.setMyLocationEnabled(true);
                    //mapLocation();

                    //starts the CUSTOM SERVICE
                    //startRendeVuService();

                }else {
                    //on permission denied
                }
                break;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public static Context getAppContext(){
        return mContext;
    }
    @Override
    protected void onResume() {
        super.onResume();

        //sendNotification();
//        RendeVuDB db = new RendeVuDB(MainActivity.this);
//        db.insertChaperone("anna", "6197");
        //startRendeVuService();
//        //posts to the server
//        RendeVuAPI a = new RendeVuAPI();
//        a.postLocation("12", "300", "400", MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //stopRendeVuService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopRendeVuService();
    }

    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }

    public void startRendeVuService(){
        startService(new Intent(this, RendeVuService.class));
    }

    public void stopRendeVuService(){
        stopService(new Intent(this, RendeVuService.class));
    }

//    public void sendNotification(){
//       //Notifications with a broadcast receiver
//        ///////////////////////////////////////
//        //Create an Intent for the BroadcastReceiver
//        Intent buttonIntentYes = new Intent(MainActivity.this, ButtonReceiver.class);
//        buttonIntentYes.putExtra("notificationId",NOTIFICATION_ID);
//        buttonIntentYes.putExtra("isOK", true);
//
//        Intent buttonIntentNo = new Intent(MainActivity.this, ButtonReceiver.class);
//        buttonIntentNo.putExtra("notificationId",NOTIFICATION_ID+1);
//        buttonIntentNo.putExtra("isOK", false);
//
////Create the PendingIntent
//        PendingIntent btPendingIntentYes = PendingIntent.getBroadcast(MainActivity.this, 0, buttonIntentYes,PendingIntent.FLAG_ONE_SHOT);
//        PendingIntent btPendingIntentNo = PendingIntent.getBroadcast(MainActivity.this, 1, buttonIntentNo,PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(MainActivity.this)
//                .setSmallIcon(R.drawable.ic_notification_rendevu)
//                .setContentTitle("Is the date going ok?")
//                .setContentText("Let us know!")
//                .addAction(android.R.drawable.checkbox_on_background, "Yes", btPendingIntentYes)
//                .addAction(android.R.drawable.ic_delete, "No", btPendingIntentNo)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(Notification.PRIORITY_MAX);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//    }
}
