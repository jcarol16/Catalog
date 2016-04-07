package com.test.grability.cataloggrability.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aurent on 04/04/2016.
 */
public class CategoryModel implements Serializable{
    private String id = "";
    private String title= "";
    private String image = "";
    private ArrayList<AppModel> mAppModel = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AppModel> getmAppModel() {
        return mAppModel;
    }

    public void setmAppModel(ArrayList<AppModel> mAppModel) {
        this.mAppModel = mAppModel;
    }

    public void addMAppModel(AppModel mAppModel){
        this.mAppModel.add(mAppModel);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
