package com.addison.bakingapp.fragments;

import com.addison.bakingapp.models.Recipe;
import com.addison.bakingapp.models.Step;

public interface IRecipeInfo {

    Recipe getRecipe();

    Step getStep();
}
