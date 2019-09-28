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
import com.addison.bakingapp.databinding.FragmentRecipeBinding;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailActivity detailActivity = (DetailActivity) getActivity();
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(detailActivity.getRecipe().getIngredients());

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        mBinding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvIngredients.setHasFixedSize(true);
        mBinding.rvIngredients.setAdapter(ingredientsAdapter);

        return mBinding.getRoot();
    }
}
