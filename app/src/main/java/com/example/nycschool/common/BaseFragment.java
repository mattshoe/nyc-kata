package com.example.nycschool.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.nycschool.R;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    public View rootView;
    public ViewModelProvider viewModelProvider;
    public VM viewModel;
    public IToaster toaster;
    public NavController navController;
    public IStringProvider stringProvider;

    protected Class<VM> vmClass;
    protected boolean enableBackNav = true;
    protected int layoutId;

    protected abstract void initialize();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        viewModelProvider = new ViewModelProvider(this);
        viewModel = viewModelProvider.get(vmClass);
        toaster = new Toaster(getContext());
        navController = NavHostFragment.findNavController(this);
        stringProvider = new StringProvider(getContext());

        viewModel.initialize(getArguments());

        getActivity()
            .getOnBackPressedDispatcher()
            .addCallback(this, new BackStackBackNavCallback(enableBackNav));
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        rootView = inflater.inflate(layoutId, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class BackStackBackNavCallback extends OnBackPressedCallback {
        public BackStackBackNavCallback(boolean enabled) {
            super(enabled);
        }

        @Override
        public void handleOnBackPressed() {
            navController.popBackStack();
        }
    }
}
