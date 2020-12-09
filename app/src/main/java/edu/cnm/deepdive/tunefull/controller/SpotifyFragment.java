package edu.cnm.deepdive.tunefull.controller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.FragmentSpotifyBinding;

// TODO take this out, most of it is in other classes
public class SpotifyFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  private static final String TRACK_FORMAT = "spotify:track:%s";

  private static String clientId;
  private static String redirectUri;
  private SpotifyAppRemote spotifyAppRemote;
  private FragmentSpotifyBinding binding;

  private String trackId = "622SzYSd4p6ZahVRqS3DSv";
  private long beginTimestamp = 159000;
  private long endTimestamp = 189000;

  public static SpotifyFragment newInstance(int index, Context context) {
    SpotifyFragment fragment = new SpotifyFragment();
    Bundle bundle = new Bundle();
    clientId = context.getString(R.string.client_id);
    redirectUri = context.getString(R.string.redirect_uri);
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentSpotifyBinding.inflate(inflater);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ConnectionParams connectionParams = new ConnectionParams.Builder(clientId)
        .setRedirectUri(redirectUri)
        .showAuthView(true)
        .build();
    SpotifyAppRemote.connect(getContext(), connectionParams, new ConnectionListener() {
      @Override
      public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        SpotifyFragment.this.spotifyAppRemote = spotifyAppRemote;
        connected();
      }

      @Override
      public void onFailure(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
      }
    });
  }

  @Override
  public void onStop() {
    super.onStop();
    SpotifyAppRemote.disconnect(spotifyAppRemote);
  }

  private void connected() {
    spotifyAppRemote.getPlayerApi().play(String.format(TRACK_FORMAT, trackId));
    spotifyAppRemote.getPlayerApi().seekTo(beginTimestamp);
    spotifyAppRemote.getPlayerApi()
        .subscribeToPlayerState()
        .setEventCallback((playerState) -> {
          final Track track = playerState.track;
          if (track != null) {
            Log.d("SpotifyActivity", track.name + " by " + track.artist.name);
          }
        });
    spotifyAppRemote.getPlayerApi().pause();
  }
}
