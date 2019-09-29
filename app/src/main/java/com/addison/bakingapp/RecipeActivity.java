package com.addison.bakingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;

public class RecipeActivity extends AppCompatActivity {

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

    public void onStepSelected(Step step) {
//        if (!step.getThumbnailURL().isEmpty()) {
//            String mimeType = AppUtils.getMimeType(this, Uri.parse(step.getThumbnailURL()));
//            if (mimeType.startsWith(AppUtils.MIME_VIDEO)) {
//                step.swapVideoWithThumb();
//            }
//        }

//        if (!step.getVideoURL().isEmpty()) {
//            String mimeType = AppUtils.getMimeType(this, Uri.parse(step.getVideoURL()));
//            if (mimeType.startsWith(AppUtils.MIME_IMAGE)) {
//                step.swapVideoWithThumb();
//            }
//        }

        if (mIsTablet) {
//            RecipeInfoDetailFragment detailFragment = new RecipeInfoDetailFragment();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("step", step);
//            detailFragment.setArguments(bundle);
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_detail, detailFragment)
//                    .commit();
        } else {
//            Intent intent = new Intent(this, RecipeInfoDetailActivity.class);
//            intent.putExtra(AppUtils.EXTRAS_STEP, step);
//            intent.putExtra(AppUtils.EXTRAS_RECIPE_NAME, recipe.getName());
//            startActivity(intent);
        }
    }
}