package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.adapter.ClipRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentClipFeedBinding;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;

public class ClipFeedFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";

  private NavController navController;
  private ClipViewModel clipViewModel;
  private FragmentClipFeedBinding binding;
  private int index;

  public static ClipFeedFragment newInstance(int index) {
    ClipFeedFragment fragment = new ClipFeedFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  // TODO Figure out a way to reuse this fragment for the user's posted clips
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    clipViewModel = new ViewModelProvider(getActivity()).get(ClipViewModel.class);
    int index = (getArguments() != null) ? getArguments().getInt(ARG_SECTION_NUMBER) : 0;
    clipViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentClipFeedBinding.inflate(inflater);
//    navController = Navigation.findNavController(binding.getRoot());
    int sectionText;
    switch (clipViewModel.getFeedType()) {
      case FRIENDS_FOLLOWS:
        sectionText = R.string.feed;
        break;
      case ME:
        sectionText = R.string.my_clips;
        break;
      default:
        sectionText = R.string.discovery;
    }
    binding.sectionLabel.setText(sectionText);
    binding.testSwitch.setOnClickListener((v) -> {
      ((MainActivity) getActivity()).testSwitch();
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    clipViewModel.getClips().observe(lifecycleOwner, (clips) -> {
          ClipRecyclerAdapter adapter = new ClipRecyclerAdapter(getContext(),
              clips,
              (clip) -> {
//                navController.navigate(ClipFeedFragmentDirections.openSpotify("hello"));
              },
              (clip) -> {
                User user = clip.getUser();
                // Post a new relationship between the current user and this user
              },
              clipViewModel.getFeedType()
          );
        }
    );
  }

  public enum FeedType {
    DISCOVERY, FRIENDS_FOLLOWS, ME
  }
}