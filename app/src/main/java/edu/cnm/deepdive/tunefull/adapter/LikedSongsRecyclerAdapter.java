package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.adapter.LikedSongsRecyclerAdapter.Holder;
import edu.cnm.deepdive.tunefull.databinding.ItemSongBinding;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code LikedSongsRecyclerAdapter} allows for tracks to be shown in a scrolling list.
 */
public class LikedSongsRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final OnSongPlayButtonClickListener playButtonListener;
  private final OnAddClipButtonClickListener clipButtonListener;
  private final List<Track> tracks;
  private final LayoutInflater inflater;

  /**
   * The constructor initializes the context, the list of tracks, and listeners for play buttons and
   * add clip buttons.
   *
   * @param context            The application context.
   * @param tracks             A list of tracks to be shown.
   * @param playButtonListener A listener for the clip play button.
   * @param clipButtonListener A listener for the add clip button.
   */
  public LikedSongsRecyclerAdapter(@NonNull Context context, List<Track> tracks,
      OnSongPlayButtonClickListener playButtonListener,
      OnAddClipButtonClickListener clipButtonListener) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.playButtonListener = playButtonListener;
    this.clipButtonListener = clipButtonListener;
    this.tracks = tracks;
  }

  @NonNull
  @NotNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemSongBinding binding = ItemSongBinding.inflate(inflater, parent, false);
    return new Holder(binding);

  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return tracks.size();
  }

  /**
   * The holder class contains methods to bind individual track objects to the recycler.
   */
  class Holder extends RecyclerView.ViewHolder {

    private final ItemSongBinding binding;

    public Holder(@NonNull ItemSongBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(int position) {
      Track track = tracks.get(position);
      binding.songTitle.setText(track.name);
      if (track.artist == null) {
        binding.songArtist.setText(track.artists.get(0).name);
      } else {
        binding.songArtist.setText(track.artist.name);
      }
      binding.playButton.setOnClickListener((v) -> playButtonListener.onClick(track));
      binding.createClipButton.setOnClickListener((v) -> clipButtonListener.onClick(track));
    }
  }

  /**
   * This interface has a method that can be implemented to perform an action upon clicking the
   * track play button.
   */
  public interface OnSongPlayButtonClickListener {

    void onClick(Track track);
  }

  /**
   * This interface has a method that can be implemented to perform an action upon clicking the add
   * clip button.
   */
  public interface OnAddClipButtonClickListener {

    void onClick(Track track);
  }


}
