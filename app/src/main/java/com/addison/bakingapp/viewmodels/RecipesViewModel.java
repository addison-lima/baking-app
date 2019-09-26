package com.addison.bakingapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.addison.bakingapp.repository.RecipesRepository;

public class RecipesViewModel extends AndroidViewModel {

    private RecipesRepository mRecipesRepository;

    public RecipesViewModel(@NonNull Application application) {
        super(application);

        mRecipesRepository = RecipesRepository.getInstance();
    }

    public LiveData<RecipesRepository.RequestState> getRequestState() {
        return mRecipesRepository.getRequestState();
    }

    public void refresh() {
        mRecipesRepository.refreshData();
    }
}
