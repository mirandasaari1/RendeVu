package edu.csumb.anna.rendevu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        addChaperoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = chaperoneName.getText().toString();
                String number = chaperonePhone.getText().toString();

                db.insertChaperone(name, number);
                Toast.makeText(AddChaperoneActivity.this, "Chaperone Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
