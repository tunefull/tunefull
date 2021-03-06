package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.tunefull.adapter.LikedSongsRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentLikedSongsBinding;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.SpotifyViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.TrackViewModel;

/**
 * The liked songs fragment is the fragment that will allow the user to see the songs from Spotify and
 * create clips.
 */
public class LikedSongsFragment extends Fragment {

  private FragmentLikedSongsBinding binding;
  private TrackViewModel trackViewModel;
  private SpotifyViewModel spotifyViewModel;

  /**
   *
   * @param inflater           A {@code LayoutInflater}.
   * @param container          A {@code ViewGroup}.
   * @param savedInstanceState A {@code Bundle}.
   * @return A {@code View}.
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentLikedSongsBinding.inflate(inflater);
    return binding.getRoot();
  }

  /**
   * Shows the songs that the user has liked on Spotify and allows the user to create clips.
   *
   * @param view               A {@code View} object.
   * @param savedInstanceState A {@code Bundle}
   */
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
    ClipViewModel clipViewModel = new ViewModelProvider(getActivity()).get(ClipViewModel.class);
    clipViewModel.getThrowable().observe(lifecycleOwner, (throwable) -> {
      if (throwable != null) {
        // TODO add this back in when debugging
//        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public void onStop() {
    super.onStop();
    spotifyViewModel.disconnect();
  }
}
