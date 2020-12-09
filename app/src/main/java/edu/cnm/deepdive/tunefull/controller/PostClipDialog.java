package edu.cnm.deepdive.tunefull.controller;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.slider.Slider;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.DialogPostClipBinding;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.TrackViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PostClipDialog extends DialogFragment {

  private static final int MILLIS_PER_SEC = 1_000;
  private static final int MAX_CLIP_SEC = 30;
  private static final String TIME_ZONE = "UTC";

  private AlertDialog dialog;
  private Track track;
  private ClipViewModel viewModel;
  private TrackViewModel trackViewModel;
  private DialogPostClipBinding binding;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    trackViewModel = new ViewModelProvider(getActivity()).get(TrackViewModel.class);
    binding = DialogPostClipBinding.inflate(LayoutInflater.from(getContext()));
    Slider slider = binding.beginTimestampSlider;
    //noinspection IntegerDivisionInFloatingPointContext
    slider.setValueTo((track.duration / MILLIS_PER_SEC) - MAX_CLIP_SEC);
    slider.setLabelFormatter(value -> {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
      dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
      return dateFormat.format(new Date((long) value * MILLIS_PER_SEC));
    });
    dialog = new Builder(getActivity())
        .setView(R.layout.dialog_post_clip)
        .setPositiveButton(R.string.post, (dialog, which) -> {
          long beginTimestamp = (long) slider.getValue() * 1_000;
          viewModel.postClip(track, beginTimestamp, beginTimestamp + 30_000);
        })
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        })
        .create();
    return dialog;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    trackViewModel.getTrack().observe(getViewLifecycleOwner(), (track) -> {
          this.track = track;
        }
    );
  }

}