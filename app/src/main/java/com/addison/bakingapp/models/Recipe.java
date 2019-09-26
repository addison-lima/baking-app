package com.addison.bakingapp.models;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return image;
    }
}
