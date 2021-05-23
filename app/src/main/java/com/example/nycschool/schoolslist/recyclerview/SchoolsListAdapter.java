package com.example.nycschool.schoolslist.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nycschool.R;
import com.example.nycschool.models.School;

import java.util.ArrayList;

public class SchoolsListAdapter extends RecyclerView.Adapter<SchoolsListViewHolder> {
    private final ArrayList<School> schools;

    public SchoolsListAdapter(ArrayList<School> schools) {
        this.schools = schools;
    }

    @NonNull
    @Override
    public SchoolsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schools_list_item_view, parent, false);

        SchoolsListItemView listItem = new SchoolsListItemView(parent.getContext());
        listItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        SchoolsListViewHolder viewHolder = new SchoolsListViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolsListViewHolder holder, int position) {
        SchoolsListItemView view = holder.getItemView();

        view.setSchoolName(schools.get(position).getName());
        view.setSchoolAddress(schools.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }
}
