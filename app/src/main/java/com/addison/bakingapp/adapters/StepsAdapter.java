package com.addison.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.addison.bakingapp.R;
import com.addison.bakingapp.databinding.ItemStepBinding;
import com.addison.bakingapp.models.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private final StepsAdapterOnClickHandler mOnClickHandler;

    private List<Step> mStepsData;

    public StepsAdapter(List<Step> steps, StepsAdapterOnClickHandler onClickHandler) {
        mStepsData = steps;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_step, parent, false);
        return new StepsAdapter.StepsAdapterViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
        Step step = mStepsData.get(position);
        holder.setStepId(step.getId());
        holder.setStepName(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepsData == null) {
            return 0;
        }
        return mStepsData.size();
    }

    public interface StepsAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemStepBinding mBinding;

        public StepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mOnClickHandler.onClick(mStepsData.get(adapterPosition));
        }

        void setStepId(int stepId) {
            mBinding.tvStepId.setText(String.valueOf(++stepId));
        }

        void setStepName(String stepName) {
            mBinding.tvStepName.setText(stepName);
        }
    }
}
