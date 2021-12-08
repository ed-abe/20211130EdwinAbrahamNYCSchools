package com.edwin.a20211130_edwinabraham_nycschools.model;

import android.app.Application;

import com.edwin.a20211130_edwinabraham_nycschools.pojo.SATScores;
import com.edwin.a20211130_edwinabraham_nycschools.repository.SchoolRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SATScoresViewModel extends AndroidViewModel {

    private SchoolRepository mRepository;

    public SATScoresViewModel(@NonNull Application application) {
        super(application);
        mRepository = SchoolRepository.getRepository(application.getApplicationContext());
    }

    public LiveData<SATScores> getScoresForSchool(String schoolDBN) { return mRepository.getSATScoresForSchool(schoolDBN);}

}
