package com.example.nycschool.schoolslist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nycschool.R;
import com.example.nycschool.common.BaseFragment;
import com.example.nycschool.models.School;
import com.example.nycschool.schoolslist.recyclerview.LayoutManagerFactory;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListAdapterFactory;
import com.example.nycschool.schoolslist.recyclerview.SchoolsListNavigationDelegate;

import java.util.List;

/*
    Given more time, I'd have implemented a quarantine pattern to extract all logic into a
    collaborator that holds a reference to the fragment so that the fragment logic could be tested
    in a vacuum without requiring instrumented tests.
 */
public class SchoolsListFragment extends BaseFragment<SchoolsListViewModel> implements SchoolsListNavigationDelegate {
    /*
        Breaking encapsulation here only because we do not control instantiation of fragments,
        so in order to make it testable I'm doing member injection as opposed to constructor
        injection. This would be unnecessary if I'd have used the quarantine pattern as i could
        inject dependencies directly into the constructor

        Alternatively, I believe another common practice is to use an IoC container like Dagger to
        manage dependency injection.
     */
    public LayoutManagerFactory layoutManagerFactory;
    public SchoolsListAdapterFactory schoolsListAdapterFactory;
    public NavDirectionsProvider navDirectionsProvider;

    @Override
    protected void initialize() {
        vmClass = SchoolsListViewModel.class; // Type Erasure forces us to provide class here where type is known
        layoutId = R.layout.schools_list_fragment;
        enableBackNav = false; // This is the root view so we don't wnat to pop back stack
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct dependencies
        layoutManagerFactory = new LayoutManagerFactory();
        schoolsListAdapterFactory = new SchoolsListAdapterFactory();
        navDirectionsProvider = new NavDirectionsProvider();
    }

    public void onResume() {
        super.onResume();

        if (viewModel.getSchools() != null) {
            configureRecyclerView(viewModel.getSchools());
        }
        else {
            observeSchoolData();
        }
    }

    private void observeSchoolData() {
        // I'm ok with newing up these observers because i can capture them in tests via
        // the mocked viewmodel
        viewModel.observeSchoolsList(this, new SchoolsListSuccessObserver());
        viewModel.observeSchoolsListError(this, new SchoolsListErrorObserver());
        viewModel.loadSchools();
    }

    private void configureRecyclerView(List<School> schools) {
        RecyclerView recyclerView = rootView.findViewById(R.id.schools_list_recycler_view);
        RecyclerView.LayoutManager layoutManager = layoutManagerFactory.linearLayoutManager(getActivity());
        RecyclerView.Adapter adapter = schoolsListAdapterFactory.make(schools, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showDataLoadError() {
        /*
            In a production app this would be more robust and include remote error logging,
            update the view to error state, etc
         */
        toaster.show(stringProvider.getStringResource(R.string.failed_to_load_schools), Toast.LENGTH_SHORT);
    }

    @Override
    public void navigateToDetail(School school) {
        navController.navigate(navDirectionsProvider.navigateToSchoolDetail(school));
    }

    private class SchoolsListSuccessObserver implements Observer<List<School>> {
        @Override
        public void onChanged(List<School> schools) {
            if (schools == null)
                showDataLoadError();
            else
                configureRecyclerView(schools);
        }
    }

    private class SchoolsListErrorObserver implements Observer<Throwable> {
        @Override
        public void onChanged(Throwable throwable) {
            if (throwable != null)
                showDataLoadError();
        }
    }

    class NavDirectionsProvider {
        public NavDirections navigateToSchoolDetail(School school) {
            return SchoolsListFragmentDirections.Companion.actionSchoolsListFragmentToSchoolDetailtFragment(school);
        }
    }
}