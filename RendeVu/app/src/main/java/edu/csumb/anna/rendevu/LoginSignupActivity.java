package edu.csumb.anna.rendevu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.common.api.Status;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csumb.anna.rendevu.api.RendeVuAPI;
import edu.csumb.anna.rendevu.storage.RendeVuDB;

public class LoginSignupActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    final String TAG = LoginSignupActivity.class.getSimpleName();

    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]


    private GoogleApiClient mGoogleApiClient;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);


        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }


/*
                //gets extras
                Bundle extras = getIntent().getExtras();
                boolean reSignIn = false;

                if (extras != null)
                {
                    reSignIn = extras.getBoolean("reSignIn", false);
                }

*/

//                //if the user wants to reSignIn, then signout
//                if(reSignIn) {
//                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//                    signOut();
//                }
//                else
                updateUI(user);
            }
        };
        // [END auth_state_listener]
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginSignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        boolean isUserInServer = false;

        try {
            if (user != null) {
                //            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
                //            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
                toastIt("Welcome: " + user.getDisplayName());
                //toastIt(user.getEmail());
                //toastIt(user.getUid());

                //adding user to local users db
                RendeVuDB db = new RendeVuDB(this);
                db.insertUser(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());

                ////////////////////////////////////////////////////////////////////////////////////////
                //if user is not in the server, then it needs to signup
                //posts to the server
                RendeVuAPI apiLink = new RendeVuAPI();
                String resp = null;
                resp = apiLink.postLogin(user.getUid(), LoginSignupActivity.this);

                if (resp == null)
                    return;

                Log.d(TAG, "FROM THE OBJECT...LOGIN" + resp);

                JSONObject json = new JSONObject(resp);
                if (json != null) {
                    JSONObject data = json.getJSONObject("data");
                    String userIDHeader = data.getString("userID");
                    Log.d(TAG, "FROM LOGIN JSON" + userIDHeader);

                    //user has ot signed up
                    if(userIDHeader.equals("false"))
                        isUserInServer = false;
                    else if(userIDHeader.equals("true"))
                        isUserInServer = true;
                }

                //if user is not in the server, sign up
                if (!isUserInServer) {

                    Intent intent = new Intent(LoginSignupActivity.this, GetNumberActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("userID", user.getUid());
                    intent.putExtra("fullName", user.getDisplayName());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("imgURL", user.getPhotoUrl().toString());

                    SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                    editor.putString("userID", user.getUid());
                    editor.putString("fullName", user.getDisplayName());
                    editor.putString("email", user.getEmail());
                    editor.putString("imgURL", user.getPhotoUrl().toString());
                    editor.commit();

                    startActivity(intent);
                }

                //if the user is in the server, move on to the next activity and clear the stack
                else{

                    //updates the current user preference
                    SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                    editor.putString("userID", user.getUid());
                    editor.putString("fullName", user.getDisplayName());
                    editor.putString("email", user.getEmail());
                    editor.putString("imgURL", user.getPhotoUrl().toString());
                    editor.commit();

                    Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("userID", user.getUid());
                    startActivity(intent);
                }
                ////////////////////////////////////////////////////////////////////////////////////////

                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            } else {
                //mStatusTextView.setText(R.string.signed_out);
                //mDetailTextView.setText(null);

                toastIt("signed out");

                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void toastIt(String aMessage){
        Toast.makeText(this, aMessage,
                Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }
}