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
import org.jetbrains.annotations.NotNull;

public class PostClipDialog extends DialogFragment {

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
    slider.setValueTo((track.duration / 1_000) - 30);
    dialog = new Builder(getActivity())
        // TODO set formatter (to display the time values in a logical way)
        .setView(R.layout.dialog_post_clip)
        .setPositiveButton("Post", (dialog, which) -> {
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
