package edu.cnm.deepdive.tunefull.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.databinding.FragmentProfileBinding;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel;
import edu.cnm.deepdive.tunefull.viewmodel.RelationshipViewModel;

public class ProfileFragment extends Fragment {

  private static final String RELATIONSHIP_PREF_KEY = "relationship_index";
  private static final String ARG_SECTION_NUMBER = "section_number";

  private SharedPreferences preferences;
  private ClipViewModel clipViewModel;
  private RelationshipViewModel relationshipViewModel;
  private FragmentProfileBinding binding;
  private NavController navController;

  public static ProfileFragment newInstance(int index) {
    ProfileFragment fragment = new ProfileFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  //TODO fix viewmodel placement/indexing
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
//    navController = NavHostFragment.findNavController(this);
    binding = FragmentProfileBinding.inflate(inflater);
    binding.sectionLabel.setText(R.string.profile);
    binding.setGenre.setOnClickListener((v) -> {
          ChangeGenreDialog dialog = new ChangeGenreDialog();
          dialog.show(getChildFragmentManager(), dialog.getClass().getSimpleName());
        }
    );
    preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    binding.friendRequests.setOnClickListener((v) -> {
      preferences.edit().putInt(RELATIONSHIP_PREF_KEY, RelationshipType.PENDING.ordinal()).apply();
      Intent intent = new Intent(getContext(), RelationshipsActivity.class);
      startActivity(intent);
    });
    binding.myFriends.setOnClickListener((v) -> {
      preferences.edit().putInt(RELATIONSHIP_PREF_KEY, RelationshipType.FRIENDS.ordinal()).apply();
      Intent intent = new Intent(getContext(), RelationshipsActivity.class);
      startActivity(intent);
    });
    binding.myFollows.setOnClickListener((v) -> {
      preferences.edit().putInt(RELATIONSHIP_PREF_KEY, RelationshipType.FOLLOWING.ordinal()).apply();
      Intent intent = new Intent(getContext(), RelationshipsActivity.class);
      startActivity(intent);
    });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}