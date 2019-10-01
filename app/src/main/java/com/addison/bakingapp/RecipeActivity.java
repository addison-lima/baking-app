package com.addison.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.addison.bakingapp.fragments.IRecipeInfo;
import com.addison.bakingapp.fragments.StepFragment;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;
import com.addison.bakingapp.widget.BakingWidgetProvider;

public class RecipeActivity extends AppCompatActivity implements IRecipeInfo {

    public static final String RECIPE_EXTRA_KEY = "recipe";
    public static final String RECIPE_NAME_PREFERENCE_KEY = "recipe_name";

    private Recipe mRecipe = null;
    private Step mStep = null;
    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if ((extras != null) && (extras.containsKey(RECIPE_EXTRA_KEY))) {
            mRecipe = getIntent().getParcelableExtra(RECIPE_EXTRA_KEY);
        }
        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            setContentView(R.layout.activity_recipe);
            mIsTablet = (findViewById(R.id.fragment_more_detail) != null);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_widget) {
            updateWidget();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Recipe getRecipe() {
        return mRecipe;
    }

    @Override
    public Step getStep() {
        return mStep;
    }

    public void onStepSelected(Step step) {
        mStep = step;
        if (mIsTablet) {
            StepFragment stepFragment = new StepFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_more_detail, stepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.STEP_EXTRA_KEY, mStep);
            intent.putExtra(StepActivity.RECIPE_EXTRA_KEY, mRecipe);
            startActivity(intent);
        }
    }

    private void updateWidget() {
        SharedPreferences sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(RECIPE_NAME_PREFERENCE_KEY, mRecipe.getName())
                .apply();

        ComponentName provider = new ComponentName(this, BakingWidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        BakingWidgetProvider bakingWidgetProvider = new BakingWidgetProvider();
        bakingWidgetProvider.onUpdate(this, appWidgetManager, ids);
    }
}
