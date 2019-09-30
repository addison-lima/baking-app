package com.addison.bakingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.addison.bakingapp.R;
import com.addison.bakingapp.databinding.FragmentStepBinding;
import com.addison.bakingapp.models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;

public class StepFragment extends Fragment {

    private FragmentStepBinding mBinding;
    private SimpleExoPlayer mPlayer;

    private Step mStep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);

        IRecipeInfo recipeInfo = (IRecipeInfo) getActivity();
        if (recipeInfo != null) {
            mStep = recipeInfo.getStep();
            mBinding.tvStepShortDescription.setText(mStep.getShortDescription());
            mBinding.tvStepDescription.setText(mStep.getDescription());
        }

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mPlayer == null) {
            mBinding.playerView.setVisibility(View.VISIBLE);
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mBinding.playerView.setPlayer(mPlayer);
            mBinding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }
    }
}
