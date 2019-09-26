package com.addison.bakingapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.addison.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRepository {

    private static final String RECIPES_END_POINT = "https://d17h27t6h515a5.cloudfront.net";

    private static final Object LOCK = new Object();
    private static RecipesRepository sRecipesRepository;

    private final MutableLiveData<RequestState> mRequestState = new MutableLiveData<>();
    private final MutableLiveData<List<Recipe>> mRecipesData = new MutableLiveData<>();

    private RecipesRepository() {
        retrieveRecipes();
    }

    public static RecipesRepository getInstance() {
        if (sRecipesRepository == null) {
            synchronized (LOCK) {
                sRecipesRepository = new RecipesRepository();
            }
        }
        return sRecipesRepository;
    }

    public LiveData<RequestState> getRequestState() {
        return mRequestState;
    }

    public LiveData<List<Recipe>> getRecipesData() {
        return mRecipesData;
    }

    public void refreshData() {
        if ((mRequestState.getValue() != null)
                && !mRequestState.getValue().equals(RequestState.LOADING)) {
            retrieveRecipes();
        }
    }

    private void retrieveRecipes() {
        mRequestState.setValue(RequestState.LOADING);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipesApi mService = retrofit.create(RecipesApi.class);

        Call<List<Recipe>> call = mService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                    @NonNull Response<List<Recipe>> response) {
                mRequestState.setValue(RequestState.SUCCESS);
                mRecipesData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                mRequestState.setValue(RequestState.FAILURE);
            }
        });
    }

    public enum RequestState {
        LOADING,
        SUCCESS,
        FAILURE
    }
}
