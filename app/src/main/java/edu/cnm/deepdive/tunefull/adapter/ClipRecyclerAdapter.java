package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.adapter.ClipRecyclerAdapter.Holder;
import edu.cnm.deepdive.tunefull.databinding.ItemClipBinding;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.ui.main.ClipFeedFragment.FeedType;
import java.util.List;

public class ClipRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final OnPlayButtonClickListener playListener;
  private final OnAddFriendButtonClickListener addFriendListener;
  private final Context context;
  private final List<Clip> clips;
  private final LayoutInflater inflater;
  private final FeedType feedType;

  public ClipRecyclerAdapter(@NonNull Context context, List<Clip> clips,
      OnPlayButtonClickListener playListener, OnAddFriendButtonClickListener addFriendListener,
      FeedType feedType) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.clips = clips;
    this.playListener = playListener;
    this.addFriendListener = addFriendListener;
    this.feedType = feedType;
  }


  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemClipBinding binding = ItemClipBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return clips.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final ItemClipBinding binding;

    private Holder(@NonNull ItemClipBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      Clip clip = clips.get(position);
      binding.clipPoster.setText(clip.getUser().getUsername());
      binding.clipTitle.setText(clip.getSongTitle());
      if (feedType != FeedType.DISCOVERY) {
        binding.addFriendButton.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_check_24));
      }
      binding.playButton.setOnClickListener((v) -> playListener.onCLick(clip));
      binding.addFriendButton.setOnClickListener((v) -> addFriendListener.onClick(clip));
    }
  }

  public interface OnPlayButtonClickListener {

    void onCLick(Clip clip);
  }

  public interface OnAddFriendButtonClickListener {

    void onClick(Clip clip);
  }

}
