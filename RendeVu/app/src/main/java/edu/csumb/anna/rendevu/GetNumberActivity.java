package edu.csumb.anna.rendevu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

import static java.security.AccessController.getContext;

public class GetNumberActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "GenNumberActivity";

    private Button phoneButton;
    private EditText numberInput;

    private int nDigits = 10;

    private String userID = "";
    private String fullName = "";
    private String email = "";
    private String imgURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        phoneButton = (Button) findViewById(R.id.buttonPhone);

        numberInput = (EditText) findViewById(R.id.numberET);

        phoneButton.setOnClickListener(this);


        //gets extras
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            userID = extras.getString("userID");
            fullName= extras.getString("fullName");
            email = extras.getString("email");
            imgURL = extras.getString("imgURL");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPhone:
                checkCredentials();
        }
    }

    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks that the number enters is of the proper lengths and that it is a number
     */
    private void checkCredentials(){
        String input = numberInput.getText().toString();

        //gets rid of all spaces in the string
        input = input.replaceAll(" ","");

        //gets the total lenght of the input
        int inputLength = input.length();

        //if it is a number and it has the right lenght
        if(isNum(input) && inputLength == nDigits){

            RendeVuAPI apiLink = new RendeVuAPI();
            //post to server, check the response
            String resp = apiLink.postSignup(userID, fullName, email, imgURL, input, GetNumberActivity.this);

            //good
            if (resp != null) {
                toastIt("Welcome to RendeVu!");

                //adds phone number of user to local db
                RendeVuDB db = new RendeVuDB(this);
                db.insertPhoneNumber(input);
//                Log.d(TAG, "FROM THE OBJECT...SIGNUP" + resp);

//                SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
//                SharedPreferences.Editor edit = userDetails.edit();
//                edit.putString("userID", userID);
//                edit.commit();

                SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                editor.putString("userID", userID);
                editor.putString("fullName", fullName);
                editor.putString("email", email);
                editor.putString("imgURL", imgURL);
                editor.commit();

                //clears the activity stack
                Intent intent = new Intent(GetNumberActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
            else{
                toastIt("something went wrong :(");
            }

        }
        else{
            numberInput.setText("");
            toastIt("Please try again");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GetNumberActivity.this, LoginSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("reSignIn", true);
        startActivity(intent);
    }
    /**
     * Checks that the input can be converted onto a number (Long)
     * @param aString
     * @return true, if there are only numbers in teh string
     */
    private boolean isNum(String aString){
        boolean success = false;
        try {
            Long.parseLong(aString);
            success = true;
        }catch(NumberFormatException ex){
            success = false;
        }

        return success;
    }

}
