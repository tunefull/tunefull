package edu.cnm.deepdive.tunefull.controller;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import org.jetbrains.annotations.NotNull;

public class ChangeGenreDialog extends DialogFragment {

  private AlertDialog dialog;
  private User user;

  @NonNull
  @NotNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Genre currentGenre = user.getGenre();
    dialog = new Builder(getActivity())
        .setView(R.layout.dialog_change_genre)
        .setPositiveButton(R.string.select, (dialog, which) -> {
          //TODO update user have genre
        })
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .create();
    return dialog;
  }
}
