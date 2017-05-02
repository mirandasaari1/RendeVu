package edu.csumb.anna.rendevu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.Object;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Button;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.helpers.ArrayAdapter;

import edu.csumb.anna.rendevu.storage.RendeVuDB;


public class AddDateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText additionalEditText;
    private Calendar calendar;
    private NumberPicker comfortNumberPicker;
    //private Button selectChaperoneButton;
    private int year, month, day, hour, minute;
    private Button timeButton;
    private Button dateButton;
    private Button submitDateButton;
    private EditText timeEditText;
    private EditText dateEditText;
    private TextView selectedComfortTextView;

    private String DateName;
    private String DateInfo;
    private String DateoDate;
    private String TimeoDate;
    private String ComfortLevel;
    private CharSequence text = "";
    private int duration = Toast.LENGTH_SHORT;

    RecyclerView recyclerView;
    static Chaperones chaperones;
    ArrayAdapter itemArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date_2);
        Log.d("access", "hello");
        //widget variables
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        additionalEditText = (EditText) findViewById(R.id.additionalEditText);
        timeButton = (Button) findViewById(R.id.timeButton);
        dateButton = (Button) findViewById(R.id.dateButton);
        submitDateButton = (Button) findViewById(R.id.submitDateButton);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        comfortNumberPicker = (NumberPicker) findViewById(R.id.comfortNumberPicker);
        selectedComfortTextView = (TextView) findViewById(R.id.selectedComfortTextView);

        //Listeners
        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        submitDateButton.setOnClickListener(this);

        //extra variables
        DateName = nameEditText.getText().toString();
        DateInfo = additionalEditText.getText().toString();

        //number picker for comfort level
        String[] nums = new String[11];

        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        comfortNumberPicker.setMinValue(0);
        comfortNumberPicker.setMaxValue(10);
        comfortNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                selectedComfortTextView.setText("Beginning comfort level is: " + newVal);
            }
        });
        comfortNumberPicker.setWrapSelectorWheel(false);
        comfortNumberPicker.setDisplayedValues(nums);
        comfortNumberPicker.setValue(0);

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
                                intent = new Intent(AddDateActivity.this, ChaperonesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dates:
                                intent = new Intent(AddDateActivity.this, DatesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(AddDateActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

    }

    //handles chaperone selection and date submission
    @Override
    public void onClick(View v) {

//        //opens activity for chaperone selection
//        if (v.getId() == R.id.selectChaperoneButton) {
//            Intent intent = new Intent(AddDateActivity.this, SelectChaperoneActivity.class);
//            startActivity(intent);
//
//            // Create a bundle object
//            Bundle b = new Bundle();
//            b.putString("dateName", DateName);
//            // Add the bundle to the intent.
//            intent.putExtras(b);
//            // start the ResultActivity
//            startActivity(intent);
//        }

        //pick the date of the date
        if (v == dateButton) {
            //current date
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
                    dateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + newYear);
                }
            }, year, month, day);
            datePickerDialog.show();
        }


        //pick time of the date
        if (v == timeButton) {
            //current time
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int newMinute) {
                    timeEditText.setText(hourOfDay + ":" + newMinute);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }

        //submits the date information
        if (v.getId() == R.id.submitDateButton) {

            //formats to string to be inserted into db
            DateName = nameEditText.getText().toString();
            DateInfo = additionalEditText.getText().toString();
            ComfortLevel = String.valueOf(comfortNumberPicker.getValue());
            TimeoDate = timeEditText.getText().toString();
            DateoDate = dateEditText.getText().toString();

            text = "Date Added!";
            duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(AddDateActivity.this, MainActivity.class);
            startActivity(intent);

            // adds date information to local db
            RendeVuDB db = new RendeVuDB(this);
            db.insertDate(DateName, DateoDate, TimeoDate, ComfortLevel, DateInfo);

        }
    }
}


