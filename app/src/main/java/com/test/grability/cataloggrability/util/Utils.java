package com.test.grability.cataloggrability.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

public class Utils {

    // Validar si el device es smartphone o tablet //
    public static boolean isTablet(Activity activity) {
        try {
            if ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
                Log.d("Dispositivo", "Tablet");
                return true;
            } else {
                Log.d("Dispositivo", "Smartphone");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Screen Width //
    public static int getWidth(Activity activity) {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();

        Log.d("WIDTH: ", String.valueOf(metrics.widthPixels));
        return metrics.widthPixels;
    }

    // Screen Height //
    public static int getHeight(Activity activity) {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();

        Log.d("HEIGTH: ", String.valueOf(metrics.heightPixels));
        return metrics.heightPixels;
    }

    // Valida conexion a internet //
    public static boolean isConnectingToInternet(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

}