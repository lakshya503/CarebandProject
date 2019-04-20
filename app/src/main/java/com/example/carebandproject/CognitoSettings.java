package com.example.carebandproject;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    private String userPoolId = "us-east-2_8msrgRHGW";
    private String clientId = "kdnvphc65upfq9cjscdoenm9t";
    private String clientSecret = "u27trpfrnbo3uqufv3a1dv7v47mkfrt3gpcrjitj5vh4lqlgnpl";
    private Regions cognitoRegion = Regions.US_EAST_1;

    private String identityPoolId = "eu-west-1:74e4c7ad-5f4d-4a94-a02d-703978da6ddd";

    private Context context;


    public CognitoSettings(Context context) {
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    /*the entry point for all interactions with your user pool from your application*/
    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolId, clientId
                , clientSecret, cognitoRegion);
    }

    public CognitoCachingCredentialsProvider getCredentialsProvider() {
        return new CognitoCachingCredentialsProvider(
                context.getApplicationContext(),
                identityPoolId, // Identity pool ID
                cognitoRegion// Region;
        );
    }

}

