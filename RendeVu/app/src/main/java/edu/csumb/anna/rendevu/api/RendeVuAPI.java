package edu.csumb.anna.rendevu.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import edu.csumb.anna.rendevu.MainActivity;
import edu.csumb.anna.rendevu.data.Chaperone;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Sal on 4/5/2017.
 */

public class RendeVuAPI {

    private final String TAG = "RendeVuAPI";


    //local test url
    private final String localSignup = "https://salvhernandez2-salvhernandez.c9users.io/api/v1.0/signup";
    private final String localLocation = "https://salvhernandez2-salvhernandez.c9users.io/api/v1.0/postInfo";
    private final String localLogin = "https://salvhernandez2-salvhernandez.c9users.io/api/v1.0/login";

    private final String  herokuSignup = "https://rendevu.herokuapp.com/api/v1.0/signup";
    private final String herokuLocation = "https://rendevu.herokuapp.com/api/v1.0/postInfo";
    private final String herokuStartDate = "https://rendevu.herokuapp.com/api/v1.0/startDate";
    private final String herokuEndDate = "https://rendevu.herokuapp.com/api/v1.0/endDate";
    private final String herokuLogin = "https://rendevu.herokuapp.com/api/v1.0/login";
    private final String herokuEmergency = "https://rendevu.herokuapp.com/api/v1.0/emergency";


    public RendeVuAPI(){}


    /**
     * This function posts to the api the latitude, longitude, userID and timestamp of when it happened.
     * Runs the request on its own thread, otherwise an error is raised. Async.
     * @param id
     * @param latitude
     * @param longitude
     * @param anActivity
     */
    public void postLocation(final String id, final String latitude, final String longitude, final Context anActivity){
        //done with koush ion

        //gets system time
        Long tsLong = System.currentTimeMillis()/1000;
        final String timestamp = tsLong.toString();

        //add json properties
        JsonObject json = new JsonObject();
        json.addProperty("userID", id);
        json.addProperty("latitude", latitude);
        json.addProperty("longitude", longitude);
        json.addProperty("timestamp", timestamp);
        Ion.with(anActivity)
                .load(herokuLocation)
                .setLogging("RendeVuApi", Log.DEBUG)
                .setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        // response contains both the headers and the string result
                        // print the response code, ie, 200
                        if(response.getHeaders().code() != 200)
                            Log.d(TAG,"something went wrong");

                        else{
                            Log.d(TAG,"Code: "+response.getHeaders().code());
                            // print the String that was downloaded
                            Log.d(TAG,"Response: "+response.getResult());

                            //creates an object out of the result and print outs the values
                            try {
                                JSONObject json = new JSONObject(response.getResult().toString());
                                Iterator<String> iter = json.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = json.get(key);
                                        Log.d(TAG,"from key: "+key);
                                        Log.d(TAG,"from value: "+value.toString());
                                    } catch (JSONException ex) {
                                        // Something went wrong!
                                    }
                                }
                                //Log.d(TAG,"from json: "+json.getString("description"));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     * Makes the signup post synchronously
     * @param uID
     * @param fullName
     * @param anEmail
     * @param imgURL
     * @param anActivity
     */
    public String postSignup(final String uID, final String fullName, final String anEmail, String imgURL, String phoneNumber, final Activity anActivity){
        String responseString = null;
        //gets system time
        Long tsLong = System.currentTimeMillis()/1000;
        final String timestamp = tsLong.toString();

        String firstName = "";
        String lastName = "";

        try{
            String[] split = fullName.split(" ");
            firstName = split[0];
            lastName = split[1];

            Log.d("RendeVuDB", firstName);
            Log.d("RendeVuDB", lastName);
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        //add json properties
        JsonObject json = new JsonObject();
        json.addProperty("userID", uID);
        json.addProperty("firstName", firstName);
        json.addProperty("lastName", lastName);
        json.addProperty("email", anEmail);
        json.addProperty("phoneNumber", phoneNumber);
        json.addProperty("timestamp", timestamp);
        json.addProperty("imgURL", imgURL);


        try {
            String test = Ion.with(anActivity)
                    .load(herokuSignup)
                    .setJsonObjectBody(json)
                    .asString()
                    .get();

            Log.d(TAG, test);
            responseString = test;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e){
            e.printStackTrace();
        }
        return responseString;
    }

    /**
     * Makes login post and waits for the response, synchronously
     * @param uID
     * @param aContext
     * @return
     */
    public String postLogin(String uID, Context aContext){
        String responseString = null;

        Long tsLong = System.currentTimeMillis()/1000;
        final String timestamp = tsLong.toString();

        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(61616, "sal");
        hm.put(79797, "derp");


        String chapPayload = "{";

        boolean firstCheck = false;
        for (Map.Entry<Integer, String> entry : hm.entrySet()) {

            if (firstCheck)
                chapPayload +=",";

            chapPayload += "\""+entry.getValue()+"\":["+
                    "{\"name\":\""+entry.getValue()+"\"," +
                    "\"phone_number\":\""+entry.getKey()+"\"}" +
                    "]";
            firstCheck = true;
        }
        chapPayload += "}";
      
        JsonObject json = new JsonObject();
        json.addProperty("userID", uID);
        //json.addProperty("chaperones", chapPayload);

        try {
            String test = Ion.with(aContext)
                    .load(herokuLogin)
                    .setJsonObjectBody(json)
                    .asString()
                    .get();
            responseString = test;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e){
            e.printStackTrace();
        }
        return responseString;
    }

    public void postStartDate(final String id, Context aContext){
        //done with koush ion


        String chapPayload = "{";
        boolean firstCheck = false;

        RendeVuDB db = new RendeVuDB(MainActivity.getAppContext());

        ArrayList<Chaperone> theChaperones = db.getAllChaperonesFromDB();

        int count = 0;
        for (Chaperone a : theChaperones) {
            if (firstCheck)
                chapPayload +=",";

            chapPayload += "\""+(count++)+"\":["+
                    "{\"name\":\""+a.getChaperoneName()+"\"," +
                    "\"phone_number\":\""+a.getChaperoneNumber()+"\"}" +
                    "]";
            firstCheck = true;
        }
        chapPayload += "}";
        //add json properties
        JsonObject json = new JsonObject();
        json.addProperty("userID", id);
        json.addProperty("chaperones", chapPayload);
        Ion.with(aContext)
                .load(herokuStartDate)
                .setLogging("RendeVuApi", Log.DEBUG)
                .setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        // response contains both the headers and the string result
                        // print the response code, ie, 200
                        if(response.getHeaders().code() != 200)
                            Log.d(TAG,"something went wrong");

                        else{
                            Log.d(TAG,"Code: "+response.getHeaders().code());
                            // print the String that was downloaded
                            Log.d(TAG,"Response: "+response.getResult());

                            //creates an object out of the result and print outs the values
                            try {
                                JSONObject json = new JSONObject(response.getResult().toString());
                                Iterator<String> iter = json.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = json.get(key);
                                        Log.d(TAG,"from key: "+key);
                                        Log.d(TAG,"from value: "+value.toString());
                                    } catch (JSONException ex) {
                                        // Something went wrong!
                                    }
                                }
                                //Log.d(TAG,"from json: "+json.getString("description"));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void postEndDate(final String id, Context aContext){
        //done with koush ion

        //add json properties
        JsonObject json = new JsonObject();
        json.addProperty("userID", id);
        Ion.with(aContext)
                .load(herokuEndDate)
                .setLogging("RendeVuApi", Log.DEBUG)
                .setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        // response contains both the headers and the string result
                        // print the response code, ie, 200
                        if(response.getHeaders().code() != 200)
                            Log.d(TAG,"something went wrong");

                        else{
                            Log.d(TAG,"Code: "+response.getHeaders().code());
                            // print the String that was downloaded
                            Log.d(TAG,"Response: "+response.getResult());

                            //creates an object out of the result and print outs the values
                            try {
                                JSONObject json = new JSONObject(response.getResult().toString());
                                Iterator<String> iter = json.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = json.get(key);
                                        Log.d(TAG,"from key: "+key);
                                        Log.d(TAG,"from value: "+value.toString());
                                    } catch (JSONException ex) {
                                        // Something went wrong!
                                    }
                                }
                                //Log.d(TAG,"from json: "+json.getString("description"));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void postEmergency(final String id, Context aContext){
        //done with koush ion

        //add json properties
        JsonObject json = new JsonObject();
        json.addProperty("userID", id);
        Ion.with(aContext)
                .load(herokuEmergency)
                .setLogging("RendeVuApi", Log.DEBUG)
                .setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> response) {
                        // response contains both the headers and the string result
                        // print the response code, ie, 200
                        if(response.getHeaders().code() != 200)
                            Log.d(TAG,"something went wrong");

                        else{
                            Log.d(TAG,"Code: "+response.getHeaders().code());
                            // print the String that was downloaded
                            Log.d(TAG,"Response: "+response.getResult());

                            //creates an object out of the result and print outs the values
                            try {
                                JSONObject json = new JSONObject(response.getResult().toString());
                                Iterator<String> iter = json.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = json.get(key);
                                        Log.d(TAG,"from key: "+key);
                                        Log.d(TAG,"from value: "+value.toString());
                                    } catch (JSONException ex) {
                                        // Something went wrong!
                                    }
                                }
                                //Log.d(TAG,"from json: "+json.getString("description"));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }
}
