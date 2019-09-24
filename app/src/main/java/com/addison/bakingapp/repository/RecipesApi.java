package com.addison.bakingapp.repository;

import com.addison.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
