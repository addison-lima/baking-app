package com.addison.bakingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.addison.bakingapp.fragments.IRecipeInfo;
import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;

public class StepActivity extends AppCompatActivity implements IRecipeInfo {

    private Recipe mRecipe = null;
    private Step mStep = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if ((extras != null) && (extras.containsKey("recipe"))) {
            mRecipe = getIntent().getParcelableExtra("recipe");
        }
        if ((extras != null) && (extras.containsKey("step"))) {
            mStep = getIntent().getParcelableExtra("step");
        }
        if ((mRecipe == null) || (mStep == null)) {
            Toast.makeText(this, "Something bad happened.", Toast.LENGTH_LONG).show();
            finish();
        }

        if (mStep.getId() > 0) {
            setTitle(String.valueOf(mStep.getId()));
        } else {
            setTitle("");
        }

        setContentView(R.layout.activity_step);
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
