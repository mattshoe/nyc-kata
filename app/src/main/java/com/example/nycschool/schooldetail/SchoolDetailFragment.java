package com.example.nycschool.schooldetail;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nycschool.R;
import com.example.nycschool.common.BaseFragment;
import com.example.nycschool.models.SATSummary;

public class SchoolDetailFragment extends BaseFragment<SchoolDetailViewModel> {
    private TextView schoolName;
    private TextView website;
    private TextView description;
    private TextView phoneNumber;
    private TextView email;
    private TextView participants;
    private TextView readingScore;
    private TextView mathScore;
    private TextView writingScore;

    @Override
    protected void initialize() {
        vmClass = SchoolDetailViewModel.class; // Type erasure forces explicit declaration of view model type
        layoutId = R.layout.school_detail_fragment;
        enableBackNav = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeSATSummaryData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        populateSchoolTextViews();
    }

    private void observeSATSummaryData() {
        // I'm ok with newing up these observers because i can capture them in tests via
        // the mocked viewmodel
        viewModel.observeSATSummary(this, new SATSummarySuccessObserver());
        viewModel.observeSATSummaryError(this, new SATSummaryErrorObserver());
        viewModel.loadSATSummary();
    }

    private void initializeViews() {
        schoolName = rootView.findViewById(R.id.school_detail_name);
        website = rootView.findViewById(R.id.school_website);
        description = rootView.findViewById(R.id.school_description);
        phoneNumber = rootView.findViewById(R.id.school_phone);
        email = rootView.findViewById(R.id.school_email);
        participants = rootView.findViewById(R.id.sat_participants);
        readingScore = rootView.findViewById(R.id.sat_reading_score);
        mathScore = rootView.findViewById(R.id.sat_math_score);
        writingScore = rootView.findViewById(R.id.sat_writing_score);
    }

    private void populateSchoolTextViews() {
        schoolName.setText(viewModel.getSchoolName());
        website.setText(viewModel.getSchoolWebsite());
        description.setText(viewModel.getSchoolDescription());
        phoneNumber.setText(viewModel.getSchoolPhoneNumber());
        email.setText(viewModel.getSchoolEmail());
    }

    private void populateSATTextViews(SATSummary summary) {
        participants.setText(summary.getParticipants());
        readingScore.setText(summary.getReadingScore());
        mathScore.setText(summary.getMathScore());
        writingScore.setText(summary.getWritingScore());
    }

    private void showDataLoadError() {
        /*
            In a production app this would be more robust and include remote error logging,
            update the view to error state, etc
         */
        toaster.show(stringProvider.getStringResource(R.string.failed_to_load_sat_summary), Toast.LENGTH_SHORT);
        populateSATTextViews(SATSummary.Companion.getEmpty());
    }

    private class SATSummarySuccessObserver implements Observer<SATSummary> {
        @Override
        public void onChanged(SATSummary summary) {
            if (summary == null)
                showDataLoadError();
            else
                populateSATTextViews(summary);
        }
    }

    private class SATSummaryErrorObserver implements Observer<Throwable> {
        @Override
        public void onChanged(Throwable throwable) {
            if (throwable != null)
                showDataLoadError();
        }
    }
}