package edu.csumb.anna.rendevu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Anna on 3/25/17.
 */
public class AddChaperoneActivity extends AppCompatActivity{

    EditText chaperoneName;
    EditText chaperonePhone;
    Button addChaperoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chaperones);

        chaperoneName = (EditText) findViewById(R.id.enter_name);
        chaperonePhone = (EditText) findViewById(R.id.enter_number);
        addChaperoneButton = (Button) findViewById(R.id.button);

        final RendeVuDB db = new RendeVuDB(this);

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
                                intent = new Intent(AddChaperoneActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                intent = new Intent(AddChaperoneActivity.this, DatesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(AddChaperoneActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        addChaperoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = chaperoneName.getText().toString();
                String number = chaperonePhone.getText().toString();

                db.insertChaperone(name, number);
                Toast.makeText(AddChaperoneActivity.this, "Chaperone Added", Toast.LENGTH_SHORT).show();
                toastIt("New Chaperone Added!");
                finish();
            }
        });
    }
    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }
}
