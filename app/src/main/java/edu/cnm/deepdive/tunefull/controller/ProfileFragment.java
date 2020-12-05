package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.tunefull.databinding.FragmentProfileBinding;
import edu.cnm.deepdive.tunefull.ui.main.PageViewModel;

public class ProfileFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private PageViewModel pageViewModel;
  private FragmentProfileBinding binding;

  public static ProfileFragment newInstance(int index) {
    ProfileFragment fragment = new ProfileFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
    int index = 1;
    if (getArguments() != null) {
      index = getArguments().getInt(ARG_SECTION_NUMBER);
    }
    pageViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      binding = FragmentProfileBinding.inflate(inflater);
    pageViewModel.getText().observe(getViewLifecycleOwner(), s -> binding.sectionLabel.setText(s));
    return binding.getRoot();
  }

}