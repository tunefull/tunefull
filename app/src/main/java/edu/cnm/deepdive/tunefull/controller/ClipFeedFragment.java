package edu.cnm.deepdive.tunefull.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.adapter.ClipRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentClipFeedBinding;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.RelationshipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.SpotifyViewModel;

/**
 * The {@code ClipFeedFragment} hosts the heart of the app: the Discovery and TuneFull feed screens.
 */
public class ClipFeedFragment extends Fragment {

  private static final String MAIN_SCREENS_PREF_KEY = "main_index";
  private static final String ARG_SECTION_NUMBER = "section_number";
  private ClipViewModel clipViewModel;
  private SpotifyViewModel spotifyViewModel;
  private RelationshipViewModel relationshipViewModel;
  private FragmentClipFeedBinding binding;
  private int index;
  private SharedPreferences preferences;

  public static ClipFeedFragment newInstance(int index) {
    ClipFeedFragment fragment = new ClipFeedFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    index = (getArguments() != null) ? getArguments().getInt(ARG_SECTION_NUMBER) : 0;
    preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    if (preferences.getInt(MAIN_SCREENS_PREF_KEY, -1) != -1) {
      preferences.edit().putInt(MAIN_SCREENS_PREF_KEY, -1).apply();
      ((MainActivity) getActivity()).switchToProfile();
    }

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentClipFeedBinding.inflate(inflater);
    int sectionText;
    switch (index) {
      case 1:
        sectionText = R.string.feed;
        break;
      case 2:
        sectionText = R.string.my_clips;
        break;
      default:
        sectionText = R.string.discovery;
    }
    binding.sectionLabel.setText(sectionText);
    binding.newClip.setOnClickListener((v) -> {
      ((MainActivity) getActivity()).switchToNewClip();
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    clipViewModel = new ViewModelProvider(getActivity()).get(ClipViewModel.class);
    spotifyViewModel = new ViewModelProvider(getActivity()).get(SpotifyViewModel.class);
    relationshipViewModel = new ViewModelProvider(getActivity()).get(RelationshipViewModel.class);
    clipViewModel.setIndex(index);
    clipViewModel.getClips().observe(lifecycleOwner, (clips) -> {
          ClipRecyclerAdapter adapter = new ClipRecyclerAdapter(getContext(),
              clips,
              (clip) -> spotifyViewModel.play(clip),
              (clip) -> relationshipViewModel.saveRelationship(clip.getUser()),
              clipViewModel.getFeedType()
          );
          binding.clipRecycler.setAdapter(adapter);
        }
    );
  }

  @Override
  public void onStop() {
    super.onStop();
    spotifyViewModel.disconnect();
  }

  /**
   * An enumerated type that lists the possible types of clip feeds.
   */
  public enum FeedType {
    DISCOVERY, FRIENDS_FOLLOWS, ME
  }
}