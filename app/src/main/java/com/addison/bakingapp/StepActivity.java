package com.addison.bakingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.addison.bakingapp.fragments.IRecipeInfo;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;

public class StepActivity extends AppCompatActivity implements IRecipeInfo {

    public static final String RECIPE_EXTRA_KEY = "recipe";
    public static final String STEP_EXTRA_KEY = "step";

    private Recipe mRecipe = null;
    private Step mStep = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if ((extras != null) && (extras.containsKey(RECIPE_EXTRA_KEY))) {
            mRecipe = getIntent().getParcelableExtra(RECIPE_EXTRA_KEY);
        }
        if ((extras != null) && (extras.containsKey(STEP_EXTRA_KEY))) {
            mStep = getIntent().getParcelableExtra(STEP_EXTRA_KEY);
        }
        if ((mRecipe != null) && (mStep != null)) {
            if (mStep.getId() > 0) {
                setTitle(String.valueOf(mStep.getId()));
            } else {
                setTitle("");
            }
            setContentView(R.layout.activity_step);
        } else {
            finish();
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
}
