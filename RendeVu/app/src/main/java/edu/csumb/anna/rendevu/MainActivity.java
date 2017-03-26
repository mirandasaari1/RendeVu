package edu.csumb.anna.rendevu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startChaperoneActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, ChaperonesActivity.class);
        startActivity(intent);
    }

//    public void startAddDateActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, AddDateActivity.class);
//        startActivity(intent);
//    }
//
//    public void startPastDatesActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, PastDatesActivity.class);
//        startActivity(intent);
//    }
//
//    public void startPlannedDatesActivity(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, PlannedDatesActivity.class);
//        startActivity(intent);
//    }
}
