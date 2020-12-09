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
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.FragmentRelationshipBinding;
import edu.cnm.deepdive.tunefull.viewmodel.RelationshipViewModel;

public class RelationshipFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";

  private FragmentRelationshipBinding binding;
  private RelationshipViewModel viewModel;
  private RelationshipType relationshipType;

  public static RelationshipFragment newInstance(RelationshipType type) {
    RelationshipFragment fragment = new RelationshipFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, type.ordinal());
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    relationshipType = (getArguments() != null)
        ? RelationshipType.values()[getArguments().getInt(ARG_SECTION_NUMBER)]
        : RelationshipType.FRIENDS;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentRelationshipBinding.inflate(inflater);
    int sectionText;
    switch (relationshipType) {
      case FRIENDS:
        sectionText = R.string.my_friends;
        break;
      case FOLLOWING:
        sectionText = R.string.my_follows;
        break;
      default:
        sectionText = R.string.friend_requests;
    }
    binding.sectionLabel.setText(sectionText);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    viewModel = new ViewModelProvider(getActivity()).get(RelationshipViewModel.class);
    viewModel.getRelationships(relationshipType).observe(getViewLifecycleOwner(), (relationships) -> {
      //set up adapter
      //if the type is friends, put havefriendship icon, if the type is following, no icon
    });
  }

  public enum RelationshipType {
    FRIENDS, FOLLOWING, PENDING
  }
}
