package com.test.grability.cataloggrability.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.test.grability.cataloggrability.R;

/**
 * Created by Aurent on 03/04/2016.
 */
public class ToolbarFragment extends Fragment {
    private TextView textTitle;
    private boolean withButtonBack = false;

    public static ToolbarFragment newInstance(String title) {
        ToolbarFragment mToolbarFragment = new ToolbarFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        mToolbarFragment.setArguments(args);
        return mToolbarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_toolbar, container, false);

        textTitle = (TextView) rootView.findViewById(R.id.toolbar_text_title);
        textTitle.setText(getArguments().getString("title"));

        ImageButton buttonBack = (ImageButton) rootView.findViewById(R.id.toolbar_button_back);
        if (withButtonBack) {
            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_horizontal_back_in, R.anim.slide_horizontal_back_out);
                }
            });
        }

        if (withButtonBack) {
            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_horizontal_back_in, R.anim.slide_horizontal_back_out);
                }
            });
        }

        return rootView;
    }

    public void addButtonBack() {
        withButtonBack = true;
    }
    public void setTitle(String title) {
        textTitle.setText(Html.fromHtml(title));
    }
}
