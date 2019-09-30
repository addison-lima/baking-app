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
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class StepFragment extends Fragment {

    private static final long DEFAULT_POSITION = -1;
    private static final String APPLICATION_NAME = "com.addison.bakingapp";
    private static final String LAST_POSITION_KEY = "last_position";
    private static final String MIME_IMAGE = "image/";
    private static final String MIME_VIDEO = "video/";

    private FragmentStepBinding mBinding;
    private SimpleExoPlayer mPlayer;

    private Step mStep;

    private long mLastPosition = DEFAULT_POSITION;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);

        if (savedInstanceState != null) {
            mLastPosition = savedInstanceState.getLong(LAST_POSITION_KEY, DEFAULT_POSITION);
        }

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

    @Override
    public void onPause() {
        super.onPause();

        mLastPosition = (mPlayer != null) ? mPlayer.getCurrentPosition() : DEFAULT_POSITION;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(LAST_POSITION_KEY, mLastPosition);
    }

    private void updateMedia() {
        String videoUrl = getMediaUrl(MIME_VIDEO);
        if (!videoUrl.isEmpty()) {
            setPlayer(videoUrl);
        } else {
            String thumbnailUrl = getMediaUrl(MIME_IMAGE);
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

            if (getContext() != null) {
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                        Util.getUserAgent(getContext(), APPLICATION_NAME));
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(videoUrl));

                mPlayer.prepare(videoSource);
                if (mLastPosition > 0) {
                    mPlayer.seekTo(mLastPosition);
                }
                mPlayer.setPlayWhenReady(true);
            }
        }
    }

    private void setImage(String imageUrl) {
        mBinding.ivThumbnail.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(imageUrl)
                .into(mBinding.ivThumbnail);
    }
}
