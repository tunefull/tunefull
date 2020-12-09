package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.adapter.RelationshipRecyclerAdapter.Holder;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.databinding.ItemRelationshipBinding;
import edu.cnm.deepdive.tunefull.model.Relationship;
import edu.cnm.deepdive.tunefull.model.User;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RelationshipRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final OnAddFriendButtonClickListener listener;
  private final Context context;
  private final List<Relationship> relationships;
  private final LayoutInflater inflater;
  private final RelationshipType relationshipType;
  private final User currentUser;

  public RelationshipRecyclerAdapter(Context context, List<Relationship> relationships,
      OnAddFriendButtonClickListener listener, RelationshipType relationshipType, User currentUser) {
    this.listener = listener;
    this.context = context;
    this.relationships = relationships;
    this.relationshipType = relationshipType;
    this.currentUser = currentUser;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @NotNull
  @Override
  public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    ItemRelationshipBinding binding = ItemRelationshipBinding.inflate(inflater, parent, false);
    return new RelationshipRecyclerAdapter.Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return relationships.size();
  }


  public class Holder extends RecyclerView.ViewHolder {

    private final ItemRelationshipBinding binding;

    public Holder(@NonNull ItemRelationshipBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      Relationship relationship = relationships.get(position);
      User requester = relationship.getRequester();
      User requested = relationship.getRequested();
      if (currentUser.equals(requester)) {
        binding.userName.setText(requested.getUsername());
        binding.userGenre.setText(requested.getGenre().toString());
      } else {
        binding.userName.setText(requester.getUsername());
        binding.userName.setText(requester.getGenre().toString());
      }
      if (relationship.isFriendRelationship()) {
        binding.addFriendButton.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_add_friend_24));
        binding.addFriendButton.setClickable(false);
      } else if (currentUser.equals(requester) || !relationship.getFriendAccepted()) {
        binding.addFriendButton.setVisibility(View.GONE);
      }
    }

  }

  public interface OnAddFriendButtonClickListener {

    void onClick(Relationship relationship);
  }

}
