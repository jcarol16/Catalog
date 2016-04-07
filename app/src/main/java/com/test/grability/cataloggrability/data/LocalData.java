package com.test.grability.cataloggrability.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.grability.cataloggrability.model.CategoryModel;
import com.test.grability.cataloggrability.util.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by JoseDaniel on 04/04/2016.
 */
public class LocalData {

    /**
     * Categories
     */
    public static void setCategories(Activity activity, ArrayList<CategoryModel> mCategoryModel) {
        try {
            String categories = new Gson().toJson(mCategoryModel);

            SharedPreferences prefs = activity.getSharedPreferences(
                    Constants.NAME_USER_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("categories", categories);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CategoryModel> getCategories(Activity activity) {
        try {
            SharedPreferences prefs = activity.getSharedPreferences(
                    Constants.NAME_USER_PREFERENCES, Context.MODE_PRIVATE);
            Type listType = new TypeToken<ArrayList<CategoryModel>>() {
            }.getType();

            return (ArrayList<CategoryModel>) new Gson().fromJson(prefs.getString("categories", null), listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
