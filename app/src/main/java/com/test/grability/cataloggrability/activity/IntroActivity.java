package com.test.grability.cataloggrability.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.test.grability.cataloggrability.R;
import com.test.grability.cataloggrability.manager.ImageManager;
import com.test.grability.cataloggrability.util.Utils;

/**
 * Created by Aurent on 03/04/2016.
 */
public class IntroActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        if(Utils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        ImageManager.init(this);
        delay();
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToCategory();
            }
        }, 2500);
    }

    private void goToCategory() {
        Intent mIntent = new Intent(this, CategoryActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        finish();
    }
}
