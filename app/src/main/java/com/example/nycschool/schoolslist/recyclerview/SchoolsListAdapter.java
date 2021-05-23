package com.example.nycschool.schoolslist.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nycschool.R;
import com.example.nycschool.models.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolsListAdapter extends RecyclerView.Adapter<SchoolsListViewHolder> {
    private final List<School> schools;
    private final SchoolsListNavigationDelegate navDelegate;

    public SchoolsListAdapter(
        List<School> schools,
        SchoolsListNavigationDelegate navDelegate
    ) {
        this.schools = schools;
        this.navDelegate = navDelegate;
    }

    @NonNull
    @Override
    public SchoolsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schools_list_item_view, parent, false);

        SchoolsListItemView listItem = new SchoolsListItemView(parent.getContext());
        listItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new SchoolsListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolsListViewHolder holder, int position) {
        SchoolsListItemView view = holder.getItemView();

        view.setSchoolName(schools.get(position).name);
        view.setSchoolAddress(schools.get(position).address);
        view.setOnClickListener(new ListItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    private class ListItemClickListener implements View.OnClickListener {
        private final int index;

        public ListItemClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            navDelegate.navigateToDetail(schools.get(index));
        }
    }
}
