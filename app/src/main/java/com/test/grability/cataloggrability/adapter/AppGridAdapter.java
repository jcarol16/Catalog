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
import com.test.grability.cataloggrability.model.AppModel;
import com.test.grability.cataloggrability.util.Utils;

import java.util.ArrayList;

/**
 * Created by Aurent on 05/04/2016.
 */
public class AppGridAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<AppModel> items;

    private int lastPosition = 8;

    public AppGridAdapter(Activity activity, ArrayList<AppModel> items) {
        this.activity = activity;
        this.items = items;
    }

    public void setItems(ArrayList<AppModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        ImageView image;
        TextView name;
        TextView author;
        TextView price;

        if (convertView == null) {
            if (Utils.isTablet(activity)) {
                view = inflater.inflate(R.layout.item_app_grid, parent, false);
                view.getLayoutParams().width = (Utils.getWidth(activity) / 4);
                view.getLayoutParams().height = (Utils.getWidth(activity) / 4);

            }else{
                view = inflater.inflate(R.layout.item_app_list, parent, false);
            }

            image = (ImageView) view.findViewById(R.id.icon_app);
            name = (TextView) view.findViewById(R.id.name_app);
            author = (TextView) view.findViewById(R.id.author_content);
            price = (TextView) view.findViewById(R.id.price_content);

            Object[] objects = new Object[]{image, name, author, price};
            view.setTag(objects);
        }else {
                view = convertView;
                Object[] objects = (Object[]) view.getTag();

                image = (ImageView) objects[0];
                name = (TextView) objects[1];
                author = (TextView) objects[2];
                price = (TextView) objects[3];
        }

        image.setImageBitmap(null);
        ImageManager.displayImage(items.get(position).getImage(), image, ImageManager.getDisplayOptions());

        name.setText(items.get(position).getName());
        author.setText(items.get(position).getArtist());
        price.setText("$" + Float.parseFloat(items.get(position).getPrice()));

        return view;
    }
}
