package edu.csumb.anna.rendevu;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.storage.RendeVuDB;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//                            case R.id.action_profile:
//                                intent = new Intent(MainActivity.this, ProfileActivity.class);
//                                startActivity(intent);
//                                break;
                            case R.id.action_login_signup:
                                intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        final Button button = (Button) findViewById(R.id.add_date_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDateActivity.class);
                startActivity(intent);
            }
        });

        //to access dev tools in chrome and see the database contents
        Stetho.initializeWithDefaults(this);
        }

    @Override
    protected void onResume() {
        super.onResume();

        //posts to the server
        RendeVuAPI a = new RendeVuAPI();
        a.postLocation("12", "300", "400", MainActivity.this);
    }

    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }

}
