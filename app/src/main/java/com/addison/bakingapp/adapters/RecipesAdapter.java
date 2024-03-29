package com.addison.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.bakingapp.R;
import com.addison.bakingapp.databinding.ItemRecipeBinding;
import com.addison.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private final RecipesAdapterOnClickHandler mOnClickHandler;

    private Context mContext;
    private List<Recipe> mRecipesData;

    public RecipesAdapter(Context context, RecipesAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutId = R.layout.item_recipe;
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipesData.get(position);
        if (recipe != null) {
            holder.setRecipeImage(recipe.getImagePath());
            holder.setRecipeName(recipe.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipesData == null) {
            return 0;
        }
        return mRecipesData.size();
    }

    public void setRecipesData(List<Recipe> recipesData) {
        mRecipesData = recipesData;
        notifyDataSetChanged();
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private ItemRecipeBinding mBinding;

        public RecipesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickHandler.onClick(mRecipesData.get(adapterPosition));
        }

        void setRecipeImage(String imagePath) {
            if (imagePath.trim().equals("")) {
                mBinding.ivRecipe.setImageResource(R.color.colorPrimary);
            } else {
                Picasso.with(mContext)
                        .load(imagePath)
                        .error(R.color.colorPrimary)
                        .into(mBinding.ivRecipe);
            }
        }

        void setRecipeName(String recipeName) {
            mBinding.tvRecipe.setText(recipeName);
        }
    }
}
