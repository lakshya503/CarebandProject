package com.example.carebandproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        registerUser();

        }
    private void registerUser() {
        final EditText inputEmail = findViewById(R.id.emailAddress);
        final EditText inputPassword =findViewById(R.id.password);
        final EditText inputSerialNumber = findViewById(R.id.serialNumber);

        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        final String TAG = "MainActivity";


        final SignUpHandler signupCallBack = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                //Signup was successful
                Log.i(TAG, "sign up success...is confirmed: " + signUpConfirmationState);
                // Check if this user (cognitoUser) needs to be confirmed
                if (!signUpConfirmationState) {
                    Log.i(TAG, "sign up success...not confirmed, verification code sent to: "
                            + cognitoUserCodeDeliveryDetails.getDestination());
                } else {
                    // The user has already been confirmed
                    Log.i(TAG, "sign up success...confirmed");
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-up failed, check exception for the cause
                Log.i(TAG, "sign up failure: " + exception.getLocalizedMessage());
            }
        };

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAttributes.addAttribute("email_id", String.valueOf(inputEmail.getText()));
                userAttributes.addAttribute("serial_number", String.valueOf(inputSerialNumber.getText()));
                userAttributes.addAttribute("password", String.valueOf(inputPassword.getText()));

                CognitoSettings cognitoSettings = new CognitoSettings(MainActivity.this);

                cognitoSettings.getUserPool().signUpInBackground(String.valueOf(inputEmail.getText())
                        , String.valueOf(inputPassword.getText()), userAttributes
                        , null, signupCallBack);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}