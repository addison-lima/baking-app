package com.addison.bakingapp.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

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
import com.squareup.picasso.Picasso;

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

        updateMedia();
    }

    private void updateMedia() {
        String videoUrl = getMediaUrl("video/");
        if (!videoUrl.isEmpty()) {
            setPlayer(videoUrl);
        } else {
            String thumbnailUrl = getMediaUrl("image/");
            if (!thumbnailUrl.isEmpty()) {
                setImage(thumbnailUrl);
            }
        }
    }

    private String getMediaUrl(String mime) {
        String mediaUrl = "";

        if (isMedia(mime, mStep.getVideoUrl())) {
            mediaUrl = mStep.getVideoUrl();
        } else if (isMedia(mime, mStep.getThumbnailUrl())) {
            mediaUrl = mStep.getThumbnailUrl();
        }

        return mediaUrl;
    }

    private boolean isMedia(String mime, String url) {
        boolean isMedia = false;

        if (!url.isEmpty()) {
            String mimeType = getMimeType(Uri.parse(url));
            isMedia = (!mimeType.isEmpty() && mimeType.startsWith(mime));
        }

        return isMedia;
    }

    private String getMimeType(Uri uri) {
        String mimeType = "";
        String scheme = uri.getScheme();
        if ((scheme != null) && (scheme.equals(ContentResolver.SCHEME_CONTENT))) {
            Context context = getContext();
            if (context != null) {
                ContentResolver cr = context.getContentResolver();
                mimeType = cr.getType(uri);
            }
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private void setPlayer(String videoUrl) {
        if (mPlayer == null) {
            mBinding.pvPlayer.setVisibility(View.VISIBLE);
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mBinding.pvPlayer.setPlayer(mPlayer);
            mBinding.pvPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }
    }

    private void setImage(String imageUrl) {
        mBinding.ivThumbnail.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(imageUrl)
                .into(mBinding.ivThumbnail);
    }
}
