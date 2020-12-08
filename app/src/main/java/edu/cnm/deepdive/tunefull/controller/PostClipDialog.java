package edu.cnm.deepdive.tunefull.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.R;
import org.jetbrains.annotations.NotNull;

public class PostClipDialog extends DialogFragment {

  private AlertDialog dialog;
  private Track track;

  @NonNull
  @NotNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    dialog = new AlertDialog.Builder(getActivity())
        // TODO set slider max value (track length - 30) and formatter (to display the time values in a logical way)
        .setView(R.layout.dialog_post_clip)
        .setPositiveButton("Post", (dialog, which) -> {
          //TODO post clip to webservice
        })
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .create();
    return dialog;
  }
}
