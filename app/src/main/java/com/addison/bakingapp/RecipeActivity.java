package com.addison.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.addison.bakingapp.fragments.IRecipeInfo;
import com.addison.bakingapp.fragments.StepFragment;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;

public class RecipeActivity extends AppCompatActivity implements IRecipeInfo {

    private Recipe mRecipe = null;
    private Step mStep = null;
    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if ((extras != null) && (extras.containsKey("recipe"))) {
            mRecipe = getIntent().getParcelableExtra("recipe");
        }
        if (mRecipe == null) {
            Toast.makeText(this, "Something bad happened.", Toast.LENGTH_LONG).show();
            finish();
        }

        setTitle(mRecipe.getName());
        setContentView(R.layout.activity_recipe);

        mIsTablet = (findViewById(R.id.fragment_more_detail) != null);
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
            intent.putExtra("step", mStep);
            intent.putExtra("recipe", mRecipe);
            startActivity(intent);
        }
    }
}
