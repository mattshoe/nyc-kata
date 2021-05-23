package com.example.nycschool.schoolslist.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import com.example.nycschool.models.School;

import java.util.List;

public class SchoolsListAdapterFactory {

    public RecyclerView.Adapter make(
            List<School> schools,
            SchoolsListNavigationDelegate navDelegate
    ) {
        return new SchoolsListAdapter(schools, navDelegate);
    }
}
