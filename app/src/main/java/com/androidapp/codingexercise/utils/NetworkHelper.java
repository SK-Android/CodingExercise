package com.androidapp.codingexercise.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkHelper {

    public static boolean hasNetworkAccess(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            return activeNetwork != null && activeNetwork.isConnected();
        } catch (Exception e) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }
}
