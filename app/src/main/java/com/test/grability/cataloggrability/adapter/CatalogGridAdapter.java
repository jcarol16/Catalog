package com.test.grability.cataloggrability.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.grability.cataloggrability.R;
import com.test.grability.cataloggrability.manager.ImageManager;
import com.test.grability.cataloggrability.model.CategoryModel;
import com.test.grability.cataloggrability.util.Utils;

import java.util.ArrayList;

/**
 * Created by Aurent on 04/04/2016.
 */
public class CatalogGridAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<CategoryModel> mCategoryModel;

    public CatalogGridAdapter(Activity activity,
                              ArrayList<CategoryModel> mCategoryModel) {
        this.activity = activity;
        this.mCategoryModel = mCategoryModel;
    }

    public void setItems(ArrayList<CategoryModel> mCategoryModel) {
        this.mCategoryModel = mCategoryModel;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCategoryModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        TextView title;
        ImageView image = null;

        if (convertView == null) {

            if(Utils.isTablet(activity)){
                view = inflater.inflate(R.layout.item_category_grid, parent, false);
                view.getLayoutParams().width = (Utils.getWidth(activity) / 3);
                view.getLayoutParams().height = (Utils.getWidth(activity) / 3)  - 200;
                image = (ImageView) view.findViewById(R.id.icon_category);

            }else{
                view = inflater.inflate(R.layout.item_category_list, parent, false);
            }

            title = (TextView) view.findViewById(R.id.category_title);
            Object[] objects = new Object[]{title, image};
            view.setTag(objects);
        } else {
            view = convertView;
            Object[] objects = (Object[]) view.getTag();

            title = (TextView) objects[0];
            image = (ImageView) objects[1];
        }

        if(Utils.isTablet(activity)) {
            image.setImageBitmap(null);
            ImageManager.displayImage(mCategoryModel.get(position).getImage(), image, ImageManager.getDisplayOptions());
        }

        title.setText(mCategoryModel.get(position).getTitle());

        return view;
    }
}
