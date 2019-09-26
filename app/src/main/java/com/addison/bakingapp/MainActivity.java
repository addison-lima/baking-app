package com.addison.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.bakingapp.adapters.RecipesAdapter;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.repository.RecipesRepository;
import com.addison.bakingapp.viewmodels.RecipesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipesAdapter.RecipesAdapterOnClickHandler {

    private RecipesAdapter mRecipesAdapter;

    private RecipesViewModel mRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? 5 : 3;

        mRecipesAdapter = new RecipesAdapter(this, this);

        RecyclerView rvRecipes = findViewById(R.id.rv_recipes);
        rvRecipes.setLayoutManager(new GridLayoutManager(this, spanCount));
        rvRecipes.setAdapter(mRecipesAdapter);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_refresh:
                mRecipesViewModel.refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
    }

    private Observer<RecipesRepository.RequestState> getRequestStateObserver() {
        return new Observer<RecipesRepository.RequestState>() {
            @Override
            public void onChanged(RecipesRepository.RequestState requestState) {
                if (requestState != null) {
                    TextView textView = findViewById(R.id.tv_test);
                    textView.setText(requestState.toString());
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
