package com.example.nycschool.schoolslist.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.nycschool.R;

public class SchoolsListItemView extends FrameLayout {
    private View rootView;
    private TextView schoolName;
    private TextView schoolAddress;
    private TextView schoolWebsite;

    public SchoolsListItemView(Context context) {
        super(context);
        init(context);
    }

    public SchoolsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchoolsListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.schools_list_item_view, this);

        schoolName = rootView.findViewById(R.id.school_name);
        schoolAddress = rootView.findViewById(R.id.school_address);
    }

    public void setSchoolName(String name) {
        schoolName.setText(name);
    }

    public void setSchoolAddress(String address) {
        schoolAddress.setText(address);
    }
 }