package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.tunefull.adapter.RelationshipRecyclerAdapter.Holder;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.databinding.ItemRelationshipBinding;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.model.Relationship;
import java.util.List;
import org.jetbrains.annotations.NotNull;

//TODO finish this
public class RelationshipRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final OnAddFriendButtonClickListener listener;
  private final Context context;
  private final List<Relationship> relationships;
  private final LayoutInflater inflater;
  private final RelationshipType relationshipType;

  public RelationshipRecyclerAdapter(OnAddFriendButtonClickListener listener, Context context,
      List<Relationship> relationships, RelationshipType relationshipType) {
    this.listener = listener;
    this.context = context;
    this.relationships = relationships;
    this.relationshipType = relationshipType;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @NotNull
  @Override
  public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }


  public class Holder extends RecyclerView.ViewHolder {

    private final ItemRelationshipBinding binding;

    public Holder(@NonNull ItemRelationshipBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      Relationship relationship = relationships.get(position);
    }

  }

  public interface OnAddFriendButtonClickListener {

    void onClick(Clip clip);
  }

}
