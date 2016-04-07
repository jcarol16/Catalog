package com.test.grability.cataloggrability.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.test.grability.cataloggrability.R;
import com.test.grability.cataloggrability.adapter.AppGridAdapter;
import com.test.grability.cataloggrability.fragment.ToolbarFragment;
import com.test.grability.cataloggrability.model.CategoryModel;
import com.test.grability.cataloggrability.util.Utils;

public class AppActivity extends FragmentActivity {
    private CategoryModel mCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        mCategoryModel = (CategoryModel) getIntent().getSerializableExtra("mCategoryModel");

        if (Utils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setUpToolbar();

        GridView grid = (GridView) findViewById(R.id.AppGrid);
        if (Utils.isTablet(this)) {
            grid.setNumColumns(4);
        } else {
            grid.setNumColumns(1);
        }

        AppGridAdapter mAppGridAdapter = new AppGridAdapter(this, mCategoryModel.getmAppModel());
        grid.setAdapter(mAppGridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDescription(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_horizontal_back_in, R.anim.slide_horizontal_back_out);
    }

    public void setUpToolbar() {
        ToolbarFragment mToolbarFragment = ToolbarFragment.newInstance(mCategoryModel.getTitle());
        mToolbarFragment.addButtonBack();

        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.app_toolbar_content, mToolbarFragment);
        mFragmentTransaction.commit();
    }

    public void goToDescription(int pos){
        Intent mIntent = new Intent(this, AppDetailActivity.class);
        mIntent.putExtra("mAppModel", mCategoryModel.getmAppModel().get(pos));
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_horizontal_in, R.anim.slide_horizontal_out);
    }

}
