package praekelt.visualradio.LoginArea;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


import praekelt.visualradio.MainActivity;
import praekelt.visualradio.R;
import praekelt.visualradio.Rest.API;
import praekelt.visualradio.Rest.Models.ReceivedProfileData;
import praekelt.visualradio.Utils.Constants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        // Login button
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mCreateProfile = (Button) findViewById(R.id.create_account);
        mCreateProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, CreateProfileActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

        // Skip login
        Button mSkipLogin = (Button) findViewById(R.id.skip_login);
        mSkipLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchMain(null);
            }
        });
    }

    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            login(email, password);
        }
    }

    private void login(final String username, final String password) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.JAC_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.VRApi api = restAdapter.create(API.VRApi.class);
        api.getProfile("Basic " + Base64.encodeToString((username + ":" + password).getBytes(), 0), new Callback<ReceivedProfileData>() {
            @Override
            public void success(ReceivedProfileData login, Response response) {
                // Access user here after response is parsed
                Log.d("Login: ", response.getBody().toString());
                Log.d("Login", login.toString());
                //Log.d("Login profile: ", login.getProfile());
                login.setAuthentication(Base64.encodeToString((username + ":" + password).getBytes(), 0));
                launchMain(login);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Error", retrofitError.toString());
                ReceivedProfileData error = (ReceivedProfileData) retrofitError.getBodyAs(ReceivedProfileData.class);

                 Log.d("Error", error.getError());
                alertDialogue(error.getError());
            }
        });
    }

    public void launchMain(ReceivedProfileData profile) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("profileData", profile);
        LoginActivity.this.startActivity(intent);
        finish();
    }

    public void alertDialogue(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}



