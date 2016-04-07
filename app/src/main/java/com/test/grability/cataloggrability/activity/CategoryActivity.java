package com.test.grability.cataloggrability.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.baoyz.widget.PullRefreshLayout;
import com.gc.materialdesign.widgets.SnackBar;
import com.test.grability.cataloggrability.R;
import com.test.grability.cataloggrability.adapter.CatalogGridAdapter;
import com.test.grability.cataloggrability.data.LocalData;
import com.test.grability.cataloggrability.data.RestData;
import com.test.grability.cataloggrability.data.RestMethod;
import com.test.grability.cataloggrability.fragment.ToolbarFragment;
import com.test.grability.cataloggrability.model.AppModel;
import com.test.grability.cataloggrability.model.CategoryModel;
import com.test.grability.cataloggrability.util.Constants;
import com.test.grability.cataloggrability.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aurent on 03/04/2016.
 */
public class CategoryActivity extends FragmentActivity {

    private GridView catalogGrid;
    private CatalogGridAdapter mCatalogGridAdapter;
    private ArrayList<CategoryModel> mCategoryModel = new ArrayList<>();
    private RestData mRestData;
    private PullRefreshLayout pullRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        if(Utils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        ToolbarFragment mToolbarFragment = ToolbarFragment.newInstance(getString(R.string.category_title));

        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.category_toolbar_content, mToolbarFragment);
        mFragmentTransaction.commit();

        catalogGrid = (GridView) findViewById(R.id.catalogGrid);
        mCatalogGridAdapter = new CatalogGridAdapter(this, mCategoryModel);
        catalogGrid.setAdapter(mCatalogGridAdapter);

        if(Utils.isTablet(this)){
            catalogGrid.setNumColumns(3);
        }else {
            catalogGrid.setNumColumns(1);
        }

        catalogGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToApps(position);
            }
        });

        goToGetData();

        pullRefresh = (PullRefreshLayout) findViewById(R.id.category_pull_refresh);
        pullRefresh.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        pullRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mRestData != null) {
            mRestData.cancel();
        }
    }

    private void goToGetData(){
        if(Utils.isConnectingToInternet(this)) {
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            getData();
        }else{
            showMessage(getString(R.string.connection_fail));
        }
    }

    private void getData() {
        mRestData = new RestData();
        mRestData.request(RestMethod.GET, Constants.API_GRABILITY, null, new RestData.OnRequestCompleteListener() {
            @Override
            public void OnRequestComplete(int statusCode, String result) {
                getDataResult(result, statusCode);
            }
        });
    }

    private void getDataResult(String result, int statusCode) {
        if (statusCode == 200) {
            mCategoryModel.clear();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONObject("feed").getJSONArray("entry");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonCategory = jsonArray.getJSONObject(i).getJSONObject("category").getJSONObject("attributes");
                    JSONObject jsonApp = jsonArray.getJSONObject(i);
                    boolean found = false;
                    for (int j = 0; j < mCategoryModel.size(); j++) {
                        if (mCategoryModel.get(j).getId().equals(jsonCategory.getString("im:id"))) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setId(jsonCategory.getString("im:id"));
                        categoryModel.setTitle(jsonCategory.getString("label"));
                        categoryModel.setImage(jsonApp.getJSONArray("im:image").getJSONObject(2).getString("label"));

                        mCategoryModel.add(categoryModel);
                    }

                    for (int j = 0; j < mCategoryModel.size(); j++) {
                        if (mCategoryModel.get(j).getId().equals(jsonCategory.getString("im:id"))) {
                            AppModel appModel = new AppModel();
                            appModel.setName(jsonApp.getJSONObject("im:name").getString("label"));
                            appModel.setTitle(jsonApp.getJSONObject("title").getString("label"));
                            appModel.setImage(jsonApp.getJSONArray("im:image").getJSONObject(2).getString("label"));
                            appModel.setDescription(jsonApp.getJSONObject("summary").getString("label"));
                            appModel.setPrice(jsonApp.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
                            appModel.setArtist(jsonApp.getJSONObject("im:artist").getString("label"));
                            appModel.setDate(jsonApp.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));

                            mCategoryModel.get(j).addMAppModel(appModel);
                            break;
                        }
                    }
                }

                LocalData.setCategories(this, mCategoryModel);
                setData();
            } catch (Exception e) {
                e.printStackTrace();
                setData();
                showMessage(getString(R.string.server_fail) + String.valueOf(statusCode));
            }
        }else{
            setData();
            showMessage(getString(R.string.server_fail));
        }

        findViewById(R.id.loading).setVisibility(View.GONE);
        pullRefresh.setRefreshing(false);
    }

    private void setData() {
        if (LocalData.getCategories(this) != null) {
            mCategoryModel = LocalData.getCategories(this);
            mCatalogGridAdapter.setItems(mCategoryModel);
        }
    }

    private void showMessage(String msn){
        SnackBar snackbar = new SnackBar(this, msn, getString(R.string.connection_button_retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        snackbar.setIndeterminate(true);
        snackbar.show();
    }

    private void goToApps(int position) {
        Intent mIntent = new Intent(this, AppActivity.class);
        mIntent.putExtra("mCategoryModel", mCategoryModel.get(position));
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_horizontal_in, R.anim.slide_horizontal_out);
    }

}
