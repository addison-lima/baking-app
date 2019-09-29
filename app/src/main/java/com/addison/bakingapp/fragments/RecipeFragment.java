package com.addison.bakingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.addison.bakingapp.RecipeActivity;
import com.addison.bakingapp.R;
import com.addison.bakingapp.adapters.IngredientsAdapter;
import com.addison.bakingapp.adapters.StepsAdapter;
import com.addison.bakingapp.databinding.FragmentRecipeBinding;
import com.addison.bakingapp.models.Step;

public class RecipeFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvIngredients.setHasFixedSize(true);

        binding.rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvSteps.setHasFixedSize(true);

        RecipeActivity recipeActivity = (RecipeActivity) getActivity();
        if (recipeActivity != null) {
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipeActivity.getRecipe().getIngredients());
            StepsAdapter stepsAdapter = new StepsAdapter(recipeActivity.getRecipe().getSteps(), this);

            binding.rvIngredients.setAdapter(ingredientsAdapter);
            binding.rvSteps.setAdapter(stepsAdapter);
        }

        return binding.getRoot();
    }

    @Override
    public void onClick(Step step) {
        RecipeActivity recipeActivity = (RecipeActivity) getActivity();
        if (recipeActivity != null) {
            recipeActivity.onStepSelected(step);
        }
    }
}
