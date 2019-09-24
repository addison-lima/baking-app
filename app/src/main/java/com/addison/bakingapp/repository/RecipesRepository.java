package com.addison.bakingapp.repository;

import android.util.Log;

import com.addison.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRepository {

    public RecipesRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipesApi mService = retrofit.create(RecipesApi.class);
        Call<List<Recipe>> call = mService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d("ADD_TEST", "onResponse");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("ADD_TEST", "onFailure");
            }
        });
    }
}
