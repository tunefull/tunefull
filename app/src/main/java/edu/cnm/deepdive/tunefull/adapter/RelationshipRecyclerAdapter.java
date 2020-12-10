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

/**
 * The {@code RelationshipRecyclerAdapter} allows for relationships (or more specifically, the
 * non-current users in the relationships) to be shown in a scrolling list.
 */
public class RelationshipRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final OnAddFriendButtonClickListener listener;
  private final Context context;
  private final List<Relationship> relationships;
  private final LayoutInflater inflater;
  private final RelationshipType relationshipType;
  private final User currentUser;

  /**
   * The constructor initializes the context, the list of relationships, a listener for the add
   * friend button, the type of relationships being shown, and the current user.
   *
   * @param context          The application context.
   * @param relationships    A list of relationships to be shown.
   * @param listener         A listener for the add friend button.
   * @param relationshipType The type of relationships being shown.
   * @param currentUser      The current user.
   */
  public RelationshipRecyclerAdapter(Context context, List<Relationship> relationships,
      OnAddFriendButtonClickListener listener, RelationshipType relationshipType,
      User currentUser) {
    this.listener = listener;
    this.context = context;
    this.relationships = relationships;
    this.relationshipType = relationshipType;
    this.currentUser = currentUser;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemRelationshipBinding binding = ItemRelationshipBinding.inflate(inflater, parent, false);
    return new RelationshipRecyclerAdapter.Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return relationships.size();
  }

  /**
   * The holder class contains methods to bind individual relationship objects to the recycler.
   */
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
        binding.addFriendButton
            .setIcon(ContextCompat.getDrawable(context, R.drawable.ic_add_friend_24));
        binding.addFriendButton.setClickable(false);
      } else if (currentUser.equals(requester) || !relationship.getFriendAccepted()) {
        binding.addFriendButton.setVisibility(View.GONE);
      }
    }

  }

  /**
   * This interface has a method that can be implemented to perform an action upon clicking the add
   * friend button.
   */
  public interface OnAddFriendButtonClickListener {

    void onClick(Relationship relationship);
  }

}
