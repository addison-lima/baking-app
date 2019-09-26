package com.addison.bakingapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.addison.bakingapp.repository.RecipesRepository;
import com.addison.bakingapp.viewmodels.RecipesViewModel;

public class MainActivity extends AppCompatActivity {

    private RecipesViewModel mRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mRecipesViewModel.getRequestState().observe(this, getRequestStateObserver());
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
}
