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

public class LikedSongsRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final OnSongPlayButtonClickListener playButtonListener;
  private final OnAddClipButtonClickListener clipButtonListener;
  private final List<Track> tracks;
  private final LayoutInflater inflater;


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

  public interface OnSongPlayButtonClickListener {

    void onClick(Track track);
  }

  public interface OnAddClipButtonClickListener {

    void onClick(Track track);
  }


}
