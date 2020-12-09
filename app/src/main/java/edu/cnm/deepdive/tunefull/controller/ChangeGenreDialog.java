package edu.cnm.deepdive.tunefull.controller;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

public class ChangeGenreDialog extends DialogFragment implements OnItemSelectedListener {

  private AlertDialog dialog;
  private Spinner spinner;
  private DialogChangeGenreBinding binding;
  private Genre[] genres = Genre.values();
  private UserViewModel viewModel;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    binding = DialogChangeGenreBinding.inflate(LayoutInflater.from(getContext()));
    spinner = binding.genreSpinner;
    ArrayAdapter<Genre> adapter = new ArrayAdapter<>(
        getActivity(), R.layout.custom_spinner_item, genres);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    dialog = new Builder(getActivity())
        .setView(binding.getRoot())
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .create();
    return dialog;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    viewModel.saveGenre(genres[position]);
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }
}
