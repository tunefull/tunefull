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
import edu.cnm.deepdive.tunefull.adapter.RelationshipRecyclerAdapter;
import edu.cnm.deepdive.tunefull.databinding.FragmentRelationshipBinding;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.viewmodel.RelationshipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.UserViewModel;

/**
 * Currently unimplemented relationship fragment
 */
public class RelationshipFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";

  private FragmentRelationshipBinding binding;
  private RelationshipViewModel relationshipViewModel;
  private UserViewModel userViewModel;
  private RelationshipType relationshipType;

  public static RelationshipFragment newInstance(RelationshipType type) {
    RelationshipFragment fragment = new RelationshipFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, type.ordinal());
    fragment.setArguments(bundle);
    return fragment;
  }

  /**
   * Sets up navigation
   *
   * @param savedInstanceState A {@code Bundle}.
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    relationshipType = (getArguments() != null)
        ? RelationshipType.values()[getArguments().getInt(ARG_SECTION_NUMBER)]
        : RelationshipType.FRIENDS;
  }

  /**
   * Currently unimplemented. Will allow users to set relationships
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

  /**
   * Currently unimplemented.
   *
   * @param view               A {@code View} object.
   * @param savedInstanceState A {@code Bundle}.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    relationshipViewModel = new ViewModelProvider(getActivity()).get(RelationshipViewModel.class);
    userViewModel= new ViewModelProvider(getActivity()).get(UserViewModel.class);
    // TODO there has to be a better way to get this info inside the lamda on line 75
    final User[] currentUser = new User[1];
    userViewModel.getUser().observe(lifecycleOwner, user -> {
      currentUser[0] = user;
    });
    relationshipViewModel.getRelationships(relationshipType).observe(lifecycleOwner, (relationships) -> {
      RelationshipRecyclerAdapter adapter = new RelationshipRecyclerAdapter(getContext(),
          relationships,
          (relationship) -> relationshipViewModel.updateRelationship(relationship, true),
          relationshipType,
          currentUser[0]);
      binding.userRecycler.setAdapter(adapter);
    });
  }

  public enum RelationshipType {
    FRIENDS, FOLLOWING, PENDING
  }
}
