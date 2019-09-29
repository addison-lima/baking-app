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
import com.google.android.exoplayer2.SimpleExoPlayer;

public class StepFragment extends Fragment {

    private FragmentStepBinding mBinding;
    private SimpleExoPlayer mPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        return mBinding.getRoot();
    }
}
