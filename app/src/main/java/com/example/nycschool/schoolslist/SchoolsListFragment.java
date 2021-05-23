package com.example.nycschool.schoolslist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nycschool.R;
import com.example.nycschool.models.School;
import com.example.nycschool.schoolslist.recyclerview.LayoutManagerFactory;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListAdapterFactory;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListNavigationDelegate;
import com.example.nycschool.util.Toaster;

import java.util.List;

/*
    Given more time, I'd have implemented a quarantine pattern to extract all logic into a
    collaborator that holds a reference to the fragment so that the fragment logic could be tested
    in a vacuum without requiring instrumented tests
 */
public class SchoolsListFragment extends Fragment implements SchoolsListNavigationDelegate {
    /*
        Breaking encapsulation here only because we do not control instantiation of fragments,
        so in order to make it testable must do member injection as opposed to constructor
        injection. This would be unnecessary if I'd have used the quarantine pattern as i could
        inject dependencies directly into the constructor
     */
    public View rootView;
    public ViewModelProvider viewModelProvider;
    public LayoutManagerFactory layoutManagerFactory;
    public SchoolsListAdapterFactory schoolsListAdapterFactory;
    public Toaster toaster;

    private SchoolsListViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct dependencies
        viewModelProvider = new ViewModelProvider(this);
        mViewModel = viewModelProvider.get(SchoolsListViewModel.class);
        layoutManagerFactory = new LayoutManagerFactory();
        schoolsListAdapterFactory = new SchoolsListAdapterFactory();
        toaster = new Toaster();
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        rootView = inflater.inflate(R.layout.schools_list_fragment, container, false);

        // I'm ok with newing up these observers because i can capture them in tests via
        // the mock viewmodel
        mViewModel.observeSchoolsList(this, new SchoolsListSuccessObserver());
        mViewModel.observeSchoolsListError(this, new SchoolsListErrorObserver());
        mViewModel.loadSchools();

        return rootView;
    }

    private void configureRecyclerView(List<School> schools) {
        RecyclerView recyclerView = rootView.findViewById(R.id.schools_list_recycler_view);
        RecyclerView.LayoutManager layoutManager = layoutManagerFactory.linearLayoutManager(getActivity());
        RecyclerView.Adapter adapter = schoolsListAdapterFactory.make(schools, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showDataLoadError() {
        toaster.show(getContext(), "Unable to load schools list", Toast.LENGTH_SHORT);
    }

    @Override
    public void navigateToDetail(School school) {
        toaster.show(getContext(), school.name, Toast.LENGTH_LONG);
    }

    public class SchoolsListSuccessObserver implements Observer<List<School>> {
        @Override
        public void onChanged(List<School> schools) {
            if (schools == null)
                showDataLoadError();
            else
                configureRecyclerView(schools);
        }
    }

    public class SchoolsListErrorObserver implements Observer<Throwable> {
        @Override
        public void onChanged(Throwable throwable) {
            if (throwable != null)
                showDataLoadError();
        }
    }
}