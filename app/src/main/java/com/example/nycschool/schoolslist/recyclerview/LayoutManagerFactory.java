package com.example.nycschool.schoolslist.recyclerview;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class LayoutManagerFactory {

    public LinearLayoutManager linearLayoutManager(FragmentActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
