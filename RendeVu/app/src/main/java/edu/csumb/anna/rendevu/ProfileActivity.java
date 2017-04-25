package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anna on 4/3/17.
 */
public class ProfileActivity extends AppCompatActivity {

    final String TAG = "ProfileActivity";
    private TextView nameTV;
    private ImageView userIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String userID = userDetails.getString("userID", "no ID");
        String name = userDetails.getString("name", "no name");
        String email = userDetails.getString("email", "no email");
        String imgURL = userDetails.getString("imgURL", "no imgURL");

        nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(name);

        userIV = (ImageView) findViewById(R.id.userIV);

        Picasso.with(this).load(imgURL).into(userIV);

        Log.d(TAG, userID);
        Log.d(TAG, name);
        Log.d(TAG, email);
        Log.d(TAG, imgURL);
    }
    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }
}
