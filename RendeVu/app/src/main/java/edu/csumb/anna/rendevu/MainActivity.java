package edu.csumb.anna.rendevu;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , LocationListener {

    final String TAG = "MainActivity";
    private static Context mContext;
    //0 is permission granted
    //int locationPermission = 1;

    public static final String BASE_URL = "https://rendevu.herokuapp.com/";
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private GoogleMap mMap;

    //////////////////////////////////////////////////////////
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    ////////////////////////////////////////////////////////

//    private int notificationId = 1;
    public static int NOTIFICATION_ID = 1;

    String userID = "none";
    String userName = "User";

    int dateID = -1;

    private TextView welcomeTV;
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

        /////////////////////////////////////

        welcomeTV = (TextView) findViewById(R.id.signInWelcome);

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

                //if use is on a date disable service, else,  let the user know
                if(dateID == -1){
                    toastIt("you are not on a date right now");
                }
                else{
                    //adds end time for date
                    RendeVuDB db = new RendeVuDB(MainActivity.this);


                    //update date info
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
                    String format = simpleDateFormat.format(new Date());
                    Log.d(TAG, "Current Timestamp: " + format);

                    db.updateEndTime(dateID, format);

                    //updates the date ID
                    SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                    editor.putInt("dateID", -1);
                    editor.commit();
                    stopRendeVuService();
                }
            }
        });

        final Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //posts to end date endpoint
                RendeVuAPI api = new RendeVuAPI();
                api.postSend(userID, String.valueOf(currentLatitude), String.valueOf(currentLongitude), MainActivity.this);
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

        //START MAP
        ///////////////////////////////////
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //////////////////////////////////

        //NEW CODE FOR LOCATION
        ////////////////////////////////////
        buildGoogleApiClient();
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//set to high accuracy for most accurate latLon
        // .setInterval(10 * 1000);        // 10 seconds, in milliseconds
        //.setFastestInterval(1 * 1000); // 1 second, in milliseconds
        ////////////////////////////////////




        //checks if the user has enabled the right permission
        ////////////////////////////////////////////////////
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissions();
        } else {//if permissions are enabled
            //GoogleApiClient();
            mGoogleApiClient.connect();
        }

        //toastIt("locationPermission: "+locationPermission);

        //to get rid of the notification when a button has been clicked
    }

    protected synchronized void buildGoogleApiClient() {
        //Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
                    mGoogleApiClient.connect();
                    mMap.setMyLocationEnabled(true);

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


        final SharedPreferences userDetails = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        userID = userDetails.getString("userID", "no ID");
        dateID = userDetails.getInt("dateID", -1);
        userName = userDetails.getString("fullName", "User");

        welcomeTV.setText("Hello "+userName);

        toastIt("current date: "+dateID);
        toastIt("current user: "+userID);
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        /*ADD A MARKER
        LatLng csumb = new LatLng(36.652527, -121.797277);

        CameraUpdate center = CameraUpdateFactory.newLatLng(csumb);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);

        //marker
        //mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.addMarker(new MarkerOptions().position(csumb).title(":)"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(csumb, 5));

        */
        //when the map is ready, it checks if the lcoation permissions are set
        //then turns on location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            centerMapOnMyLocation();
            //Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            //       mGoogleApiClient);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
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

        android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (location == null) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG, "location is null");

        } else {
            //If everything went fine lets get latitude and longitude
            //enables the fusedlocation
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            //ToastIt(""+currentLatitude);
        }

    }

    private void centerMapOnMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng myLocation = new LatLng(currentLatitude,
                currentLongitude);

        CameraUpdate center = CameraUpdateFactory.newLatLng(myLocation);
        //CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);

        //marker
        //mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
                Log.d(TAG, "Location services canceled original pendingintern");
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.d(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        centerMapOnMyLocation();

        //ToastIt("Location Changed "+currentLatitude + " WORKS " + currentLongitude + "");
        Log.d(TAG, "onLocationChanged "+currentLatitude + " , " + currentLongitude + "");
    }


}
