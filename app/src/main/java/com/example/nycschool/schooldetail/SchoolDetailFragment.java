package com.example.nycschool.schooldetail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nycschool.R;
import com.example.nycschool.common.BaseFragment;

public class SchoolDetailFragment extends BaseFragment<SchoolDetailViewModel> {

    @Override
    protected void initialize() {
        vmClass = SchoolDetailViewModel.class; // Type erasure
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.school_detail_fragment, container, false);
    }

}