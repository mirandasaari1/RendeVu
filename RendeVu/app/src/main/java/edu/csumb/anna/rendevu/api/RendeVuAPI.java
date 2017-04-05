package edu.csumb.anna.rendevu.api;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Sal on 4/5/2017.
 */

public class RendeVuAPI {

    private final String TAG = "RendeVuAPI";


    //local test url
    private final String localURL = "https://salvhernandez2-salvhernandez.c9users.io/api/v1.0/postInfo";
    //heroku url
    private final String herokuURL = "https://rendevu.herokuapp.com/api/v1.0/postInfo";


    public RendeVuAPI(){}


    /**
     * This function posts to the api the latitude, longitude, userID and timestamp of when it happened.
     * Runs the request on its own thread, otherwise an error is raised.
     * @param id
     * @param latitude
     * @param longitude
     * @param anActivity
     */
    public void postLocation(final String id, final String latitude, final String longitude, final Activity anActivity){
        //done with koush ion


        Thread thread_two = new Thread() {
            @Override
            public void run() {

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
                        .load(herokuURL)
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

//                                .asJsonObject()
//                                .setCallback(new FutureCallback<JsonObject>() {
//                                    @Override
//                                    public void onCompleted(Exception e, JsonObject result) {
//                                        // do stuff with the result or error
//                                        if(result != null)
//                                            Log.d(TAG, result.toString());
//                                        //error
//                                        else
//                                            Log.d(TAG, e.toString());
//
//                                    }
//                                });
            }
        };

        thread_two.start();
    }
}
