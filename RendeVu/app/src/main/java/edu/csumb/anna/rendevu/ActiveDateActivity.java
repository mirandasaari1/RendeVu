package edu.csumb.anna.rendevu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Miranda on 4/5/17.
 */

public class ActiveDateActivity extends AppCompatActivity implements View.OnClickListener {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_active_date);

            Button endDateButton = (Button) findViewById(R.id.endDateButton);
            endDateButton.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            //adding startime of date to local dates db
            RendeVuDB db = new RendeVuDB(this);
            db.insertEndTime();
        }

}


