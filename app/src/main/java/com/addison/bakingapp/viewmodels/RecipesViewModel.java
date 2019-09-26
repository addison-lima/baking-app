package com.addison.bakingapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.repository.RecipesRepository;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private RecipesRepository mRecipesRepository;

    public RecipesViewModel(@NonNull Application application) {
        super(application);

        mRecipesRepository = RecipesRepository.getInstance();
    }

    public LiveData<RecipesRepository.RequestState> getRequestState() {
        return mRecipesRepository.getRequestState();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipesRepository.getRecipesData();
    }

    public void refresh() {
        mRecipesRepository.refreshData();
    }
}
