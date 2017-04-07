package edu.csumb.anna.rendevu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Miranda on 4/5/17.
 */

public class ActiveDateActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_active_date);

            Button endDateButton = (Button) findViewById(R.id.endDateButton);
            endDateButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
                //create time stamp for end date
            });
        }

}


