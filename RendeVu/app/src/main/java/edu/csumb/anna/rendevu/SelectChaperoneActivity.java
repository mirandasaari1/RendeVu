package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

import edu.csumb.anna.rendevu.data.Chaperones;
import edu.csumb.anna.rendevu.storage.RendeVuDB;


/**
 * Created by Miranda Saari on 4/23/2017.
 */

public class SelectChaperoneActivity extends AppCompatActivity implements View.OnClickListener {

    private Button selectChaperoneButton;
    private ListView chapListView;
    ArrayAdapter<String> adapter;
    private String dateName;

    @Override
    protected void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_select_chaperones);

        chapListView = (ListView) findViewById(R.id.chaperoneList);
        selectChaperoneButton.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        dateName= new String(b.getString("dateName"));
        RendeVuDB db = new RendeVuDB (this);
        ArrayList<String> chaperones = new ArrayList<>();

        for(Chaperones temp: db.chaperoneNameList()){
            chaperones.add(temp.toString());
        }

       adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,chaperones);
        chapListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        chapListView.setAdapter(adapter);
    }

    public void onClick(View v) {
        SparseBooleanArray checked = chapListView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] chaperoneIDArr = new String[selectedItems.size()];
        RendeVuDB db = new RendeVuDB(this);

        //makes string array of selected chaperones
        for (int i = 0; i < selectedItems.size(); i++) {
            chaperoneIDArr[i]=db.selectChaperoneID(selectedItems.get(i));
        }

        //inserts date id and chaperone id into Date Chaperone's table
        for (int i=0; i<chaperoneIDArr.length;i++){
            db.insertDateChaperones(dateName,chaperoneIDArr[i]);
        }

        Intent intent = new Intent(SelectChaperoneActivity.this,
                AddDateActivity.class);
        startActivity(intent);

    }

}
