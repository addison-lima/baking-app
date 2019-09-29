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

import com.addison.bakingapp.DetailActivity;
import com.addison.bakingapp.R;
import com.addison.bakingapp.adapters.IngredientsAdapter;
import com.addison.bakingapp.adapters.StepsAdapter;
import com.addison.bakingapp.databinding.FragmentRecipeBinding;
import com.addison.bakingapp.models.Step;

public class RecipeFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    private FragmentRecipeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        mBinding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvIngredients.setHasFixedSize(true);

        mBinding.rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvSteps.setHasFixedSize(true);

        DetailActivity detailActivity = (DetailActivity) getActivity();
        if (detailActivity != null) {
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(detailActivity.getRecipe().getIngredients());
            StepsAdapter stepsAdapter = new StepsAdapter(detailActivity.getRecipe().getSteps(), this);

            mBinding.rvIngredients.setAdapter(ingredientsAdapter);
            mBinding.rvSteps.setAdapter(stepsAdapter);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onClick(Step step) {
    }
}
