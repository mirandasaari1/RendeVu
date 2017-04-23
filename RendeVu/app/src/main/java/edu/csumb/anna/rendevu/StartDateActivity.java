package edu.csumb.anna.rendevu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.csumb.anna.rendevu.storage.RendeVuDB;

/**
 * Created by Anna on 3/26/17.
 */

public class StartDateActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_date);

    }

    @Override
    public void onClick(View v) {
        //adding startime of date to local dates db
        RendeVuDB db = new RendeVuDB(this);
        db.insertStartTime();
    }
}
