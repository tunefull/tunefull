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
import edu.cnm.deepdive.tunefull.databinding.FragmentRelationshipBinding;
import edu.cnm.deepdive.tunefull.viewmodel.RelationshipViewModel;

public class RelationshipFragment extends Fragment {

  private FragmentRelationshipBinding binding;
  private RelationshipViewModel viewModel;
  private RelationshipType relationshipType;

  // TODO set relationshipType somehow
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentRelationshipBinding.inflate(inflater);
    viewModel = new ViewModelProvider(getActivity()).get(RelationshipViewModel.class);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
    viewModel.getRelationships(relationshipType).observe(getViewLifecycleOwner(), (relationships) -> {
      //set up adapter
      //if the type is friends, put havefriendship icon, if the type is following, no icon
    });
  }

  public enum RelationshipType {
    FRIENDS, FOLLOWING, PENDING
  }
}