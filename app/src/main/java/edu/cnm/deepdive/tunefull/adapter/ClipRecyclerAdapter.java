package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.adapter.ClipRecyclerAdapter.Holder;
import edu.cnm.deepdive.tunefull.databinding.ItemClipBinding;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.controller.ClipFeedFragment.FeedType;
import java.util.List;

/**
 * The {@code ClipRecyclerAdapter} allows for clips to be shown in a scrolling list.
 */
public class ClipRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final OnClipPlayButtonClickListener playListener;
  private final OnAddFriendButtonClickListener addFriendListener;
  private final Context context;
  private final List<Clip> clips;
  private final LayoutInflater inflater;
  private final FeedType feedType;

  /**
   * The constructor initializes the context, the list of clips, listeners for play buttons and add
   * friend buttons, and the type of clip feed selected.
   *
   * @param context           The application context.
   * @param clips             The list of clips to be shown.
   * @param playListener      A listener for the clip play button.
   * @param addFriendListener A listener for the clip add friend button.
   * @param feedType          The type of clip feed selected.
   */
  public ClipRecyclerAdapter(@NonNull Context context, List<Clip> clips,
      OnClipPlayButtonClickListener playListener, OnAddFriendButtonClickListener addFriendListener,
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

  /**
   * The holder class contains methods to bind individual clip objects to the recycler.
   */
  class Holder extends RecyclerView.ViewHolder {

    private final ItemClipBinding binding;

    private Holder(@NonNull ItemClipBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    // TODO this isn't getting the feed-type variables correctly
    private void bind(int position) {
      Clip clip = clips.get(position);
      binding.clipPoster.setText(clip.getUser().getUsername());
      binding.clipTitle.setText(clip.getSongTitle());
      if (feedType == FeedType.FRIENDS_FOLLOWS) {
        binding.addFriendButton.setIcon(ContextCompat.getDrawable(context, R.drawable.ic_check_24));
        binding.addFriendButton.setClickable(false);
      } else if (feedType == FeedType.ME) {
        binding.addFriendButton.setVisibility(View.GONE);
      } else {
        binding.addFriendButton.setOnClickListener((v) -> addFriendListener.onClick(clip));
      }
      binding.playButton.setOnClickListener((v) -> playListener.onCLick(clip));
    }
  }

  /**
   * This interface has a method that can be implemented to perform an action upon clicking the clip
   * play button.
   */
  public interface OnClipPlayButtonClickListener {

    void onCLick(Clip clip);
  }

  /**
   * This interface has a method that can be implemented to perform an action upon clicking the add
   * friend button.
   */
  public interface OnAddFriendButtonClickListener {

    void onClick(Clip clip);
  }

}
