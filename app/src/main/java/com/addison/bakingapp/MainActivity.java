package com.addison.bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.addison.bakingapp.repository.RecipesRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipesRepository repository = new RecipesRepository();
    }
}
