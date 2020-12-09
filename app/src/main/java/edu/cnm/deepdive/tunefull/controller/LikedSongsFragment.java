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
import androidx.recyclerview.widget.DividerItemDecoration;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import edu.cnm.deepdive.tunefull.adapter.LikedSongsRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentLikedSongsBinding;
import edu.cnm.deepdive.tunefull.viewmodel.SpotifyViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.TrackViewModel;

public class LikedSongsFragment extends Fragment {

  private NavController navController;
  private FragmentLikedSongsBinding binding;
  private TrackViewModel trackViewModel;
  private SpotifyViewModel spotifyViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentLikedSongsBinding.inflate(inflater);
//    navController = NavHostFragment.findNavController(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    trackViewModel = new ViewModelProvider(getActivity()).get(TrackViewModel.class);
    spotifyViewModel = new ViewModelProvider(getActivity()).get(SpotifyViewModel.class);
    trackViewModel.getTracks().observe(lifecycleOwner, (tracks) -> {
          LikedSongsRecyclerAdapter adapter = new LikedSongsRecyclerAdapter(getContext(), tracks,
              (track) -> {
                trackViewModel.setTrack(track);
                spotifyViewModel.play(track);
              },
              (track) -> {
                trackViewModel.setTrack(track);
                PostClipDialog dialog = new PostClipDialog();
                dialog.show(getChildFragmentManager(), dialog.getClass().getSimpleName());
              });
          binding.songRecycler.setAdapter(adapter);
        }
    );
  }

  @Override
  public void onStop() {
    super.onStop();
    spotifyViewModel.disconnect();
  }
}
