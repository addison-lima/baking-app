package com.addison.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.bakingapp.R;
import com.addison.bakingapp.databinding.ItemIngredientBinding;
import com.addison.bakingapp.models.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private List<Ingredient> mIngredientsData;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        mIngredientsData = ingredients;
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_ingredient, parent, false);
        return new IngredientsAdapter.IngredientsAdapterViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredient = mIngredientsData.get(position);
        holder.setIngredientName(ingredient.getIngredient());
        holder.setIngredientDose(ingredient.getDose());
    }

    @Override
    public int getItemCount() {
        if (mIngredientsData == null) {
            return 0;
        }
        return mIngredientsData.size();
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        private ItemIngredientBinding mBinding;

        public IngredientsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }

        void setIngredientName(String ingredientName) {
            mBinding.tvIngredientName.setText(ingredientName);
        }

        void setIngredientDose(String ingredientDose) {
            mBinding.tvIngredientDose.setText(ingredientDose);
        }
    }
}
