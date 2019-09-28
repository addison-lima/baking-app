package com.addison.bakingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.addison.bakingapp.models.Recipe;

public class DetailActivity extends AppCompatActivity {

    private Recipe mRecipe = null;
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
        setContentView(R.layout.activity_detail);

        mIsTablet = (findViewById(R.id.fragment_more_detail) != null);
    }

    public Recipe getRecipe() {
        return mRecipe;
    }
}
