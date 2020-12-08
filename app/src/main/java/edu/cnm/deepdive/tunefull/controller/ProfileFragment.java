package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.FragmentProfileBinding;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;

public class ProfileFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private ClipViewModel clipViewModel;
  private FragmentProfileBinding binding;
  private NavController navController;

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
    clipViewModel = new ViewModelProvider(this).get(ClipViewModel.class);
    int index = 1;
    if (getArguments() != null) {
      index = getArguments().getInt(ARG_SECTION_NUMBER);
    }
    clipViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    navController = NavHostFragment.findNavController(this);
    binding = FragmentProfileBinding.inflate(inflater);
    binding.sectionLabel.setText(R.string.profile);
    return binding.getRoot();
  }

  // TODO navigation
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.myClips.setOnClickListener((v) -> {
//      navController.navigate(ProfileFragmentDirections.openSpotify());
    });
  }
}