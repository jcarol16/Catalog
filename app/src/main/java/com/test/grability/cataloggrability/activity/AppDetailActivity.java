package com.test.grability.cataloggrability.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.grability.cataloggrability.R;
import com.test.grability.cataloggrability.fragment.ToolbarFragment;
import com.test.grability.cataloggrability.manager.ImageManager;
import com.test.grability.cataloggrability.model.AppModel;
import com.test.grability.cataloggrability.util.Utils;

/**
 * Created by Aurent on 05/04/2016.
 */
public class AppDetailActivity extends FragmentActivity {

    AppModel mAppModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        if(Utils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mAppModel = (AppModel) getIntent().getSerializableExtra("mAppModel");

        setUpToolbar();
        setupView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_horizontal_back_in, R.anim.slide_horizontal_back_out);
    }

    public void setUpToolbar() {
        ToolbarFragment mToolbarFragment = ToolbarFragment.newInstance(mAppModel.getTitle());
        mToolbarFragment.addButtonBack();

        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.app_toolbar_content, mToolbarFragment);
        mFragmentTransaction.commit();
    }

    public void setupView(){
        ImageView mImage = (ImageView) findViewById(R.id.icon_app);
        TextView name = (TextView) findViewById(R.id.name_app);
        TextView autor = (TextView) findViewById(R.id.autor);
        TextView price = (TextView) findViewById(R.id.price);
        TextView description = (TextView) findViewById(R.id.description);
        TextView date = (TextView) findViewById(R.id.date);

        mImage.setImageBitmap(null);
        ImageManager.displayImage(mAppModel.getImage(), mImage, ImageManager.getDisplayOptions());
        name.setText(mAppModel.getName());
        autor.setText(mAppModel.getArtist());
        price.setText("$"+ Float.parseFloat(mAppModel.getPrice()));
        description.setText(mAppModel.getDescription());
        date.setText(mAppModel.getDate());


    }
}
