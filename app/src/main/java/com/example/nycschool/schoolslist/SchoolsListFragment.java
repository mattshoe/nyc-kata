package com.example.nycschool.schoolslist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nycschool.R;
import com.example.nycschool.models.School;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListAdapter;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListViewHolder;

import java.util.ArrayList;

public class SchoolsListFragment extends Fragment {

    public SchoolsListViewModel mViewModel;

    public static SchoolsListFragment newInstance() {
        return new SchoolsListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SchoolsListViewModel.class);
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.schools_list_fragment, container, false);
        rootView.setTag("");

        RecyclerView recyclerView = rootView.findViewById(R.id.schools_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<School> data = new ArrayList();

        for (int i = 0; i < 100; i++) {
            data.add(new School(
                "School" + i,
                "Address" + i,
                "www.website" +i +".com"
            ));
        }

        SchoolsListAdapter adapter = new SchoolsListAdapter(data);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}