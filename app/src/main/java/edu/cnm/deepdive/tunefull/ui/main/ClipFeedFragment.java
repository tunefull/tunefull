package edu.cnm.deepdive.tunefull.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.FragmentClipFeedBinding;

public class ClipFeedFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private PageViewModel pageViewModel;
  private FragmentClipFeedBinding binding;
  private int index;

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
    pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
    index = 1;
    if (getArguments() != null) {
      index = getArguments().getInt(ARG_SECTION_NUMBER);
    }
    pageViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentClipFeedBinding.inflate(inflater);
    binding.sectionLabel.setText((index == 0)? R.string.discovery : R.string.feed);
    // TODO why isn't this working correctly? Is index not ever zero?
    return binding.getRoot();
  }
}