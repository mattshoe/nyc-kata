package com.example.nycschool.schoolslist.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

public class SchoolsListViewHolder extends RecyclerView.ViewHolder {
    private SchoolsListItemView view;

    public SchoolsListViewHolder(SchoolsListItemView view) {
        super(view);
        this.view = view;
    }

    public SchoolsListItemView getItemView() {
        return view;
    }
}
