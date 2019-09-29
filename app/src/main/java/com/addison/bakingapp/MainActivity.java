package com.addison.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.addison.bakingapp.adapters.RecipesAdapter;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.repository.RecipesRepository;
import com.addison.bakingapp.viewmodels.RecipesViewModel;
import com.addison.bakingapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipesAdapter.RecipesAdapterOnClickHandler {

    private ActivityMainBinding mBinding;
    private RecipesAdapter mRecipesAdapter;
    private RecipesViewModel mRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? 2 : 1;

        mRecipesAdapter = new RecipesAdapter(this, this);

        mBinding.rvRecipes.setLayoutManager(new GridLayoutManager(this, spanCount));
        mBinding.rvRecipes.setAdapter(mRecipesAdapter);

        mRecipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mRecipesViewModel.getRequestState().observe(this, getRequestStateObserver());
        mRecipesViewModel.getRecipes().observe(this, getRecipesObserver());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            mRecipesViewModel.refresh();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", recipe);
        startActivityForResult(intent, 1);
    }

    private Observer<RecipesRepository.RequestState> getRequestStateObserver() {
        return new Observer<RecipesRepository.RequestState>() {
            @Override
            public void onChanged(RecipesRepository.RequestState requestState) {
                if (requestState != null) {
                    mBinding.tvRequestState.setText(requestState.toString());
                    if (requestState.equals(RecipesRepository.RequestState.SUCCESS)) {
                        mBinding.tvRequestState.setVisibility(View.INVISIBLE);
                        mBinding.rvRecipes.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.rvRecipes.setVisibility(View.INVISIBLE);
                        mBinding.tvRequestState.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }

    private Observer<List<Recipe>> getRecipesObserver() {
        return new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    mRecipesAdapter.setRecipesData(recipes);
                }
            }
        };
    }
}
