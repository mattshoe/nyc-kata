package com.example.nycschool.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nycschool.R;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    public View rootView;
    public ViewModelProvider viewModelProvider;
    public VM viewModel;
    public NavController navController;
    public IToaster toaster;

    protected Class<VM> vmClass;

    protected abstract void initialize();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(vmClass);
        toaster = new Toaster();

        viewModel.initialize(getArguments());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}
