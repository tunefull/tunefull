package edu.cnm.deepdive.tunefull.controller;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.DialogChangeGenreBinding;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.viewmodel.UserViewModel;

/**
 * The change genre dialog allows the user to change their current genre mood.
 */
public class ChangeGenreDialog extends DialogFragment {

  private AlertDialog dialog;
  private Spinner spinner;
  private DialogChangeGenreBinding binding;
  private Genre[] genres = Genre.values();
  private UserViewModel viewModel;

  /**
   * Creates the dialog and sets the spinner.
   *
   * @param savedInstanceState A {@code Bundle}.
   * @return The dialog that was created.
   */
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    binding = DialogChangeGenreBinding.inflate(LayoutInflater.from(getContext()));
    spinner = binding.genreSpinner;
    ArrayAdapter<Genre> adapter = new ArrayAdapter<>(
        getActivity(), R.layout.custom_spinner_item, genres);
    adapter.setDropDownViewResource(R.layout.custom_dropdown_item);
    spinner.setAdapter(adapter);
    dialog = new Builder(getActivity())
        .setView(binding.getRoot())
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
          viewModel.saveGenre(genres[binding.genreSpinner.getSelectedItemPosition()]);
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

  //TODO get genre from the webservice upon signin

  /**
   * Sets the genre spinner's current selection.
   * @param view The current view.
   * @param savedInstanceState A {@code Bundle}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    viewModel.getGenre().observe(getViewLifecycleOwner(), (genre) -> {
      for (int i = 0; i < genres.length; i++) {
        if (genres[i] == genre) {
          binding.genreSpinner.setSelection(i);
          break;
        }
      }
    });
  }

}
