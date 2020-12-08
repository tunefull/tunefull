package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.tunefull.adapter.LikedSongsRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentLikedSongsBinding;
import edu.cnm.deepdive.tunefull.viewmodel.TrackViewModel;

public class LikedSongsFragment extends Fragment {

  private NavController navController;
  private FragmentLikedSongsBinding binding;
  private TrackViewModel trackViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentLikedSongsBinding.inflate(inflater);
    navController = NavHostFragment.findNavController(this);
    trackViewModel = new ViewModelProvider(this).get(TrackViewModel.class);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    trackViewModel.getTracks().observe(lifecycleOwner, (tracks) -> {
      LikedSongsRecyclerAdapter adapter = new LikedSongsRecyclerAdapter(getContext(), tracks,
          (track) -> {
            //play the track
          },
          v -> {
            //pop up create-clip dialog
          });
    });
  }

}
