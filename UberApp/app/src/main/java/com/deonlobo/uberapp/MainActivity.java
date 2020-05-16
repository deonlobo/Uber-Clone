package com.deonlobo.uberapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    public void redirectActivity(){

        if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){

            Intent intent = new Intent(getApplicationContext(),RiderActivity.class);

            startActivity(intent);

        }else{

            Intent intent = new Intent(getApplicationContext(),ViewRequestsActivity.class);

            startActivity(intent);

        }

    }

    public void getStarted(View view){

        Switch userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

        String userType = "rider";

        if(userTypeSwitch.isChecked()){

            userType = "driver";
            Log.i("Value",String.valueOf(userTypeSwitch.isChecked())+ParseUser.getCurrentUser().getUsername());

        }

        ParseUser.getCurrentUser().put("riderOrDriver",userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //ParseUser.logOut();
        if(ParseUser.getCurrentUser() == null){

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if(e==null){

                        Log.i("Login","Successful");

                    }else{

                        Log.i("Login","Failed");

                    }

                }
            });

        }else{

            if(ParseUser.getCurrentUser().get("riderOrDriver") != null){

                System.out.println("Hreeeee"+ParseUser.getCurrentUser().get("riderOrDriver"));
                redirectActivity();

            }

        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
