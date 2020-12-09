package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.model.Clip;
import java.util.concurrent.TimeUnit;

public class SpotifyViewModel extends AndroidViewModel {

  private static String clientId;
  private static String redirectUri;
  private SpotifyAppRemote spotifyAppRemote;

  public SpotifyViewModel(Application application) {
    super(application);
    clientId = application.getString(R.string.client_id);
    redirectUri = application.getString(R.string.redirect_uri);
  }

  public void play(Clip clip) {
    ConnectionParams connectionParams = new ConnectionParams.Builder(clientId)
        .setRedirectUri(redirectUri)
        .showAuthView(true)
        .build();
    SpotifyAppRemote.connect(getApplication(), connectionParams, new ConnectionListener() {
      @Override
      public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        SpotifyViewModel.this.spotifyAppRemote = spotifyAppRemote;
        playClip(clip);
      }

      @Override
      public void onFailure(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
      }
    });
  }

  public void play(Track track) {
    ConnectionParams connectionParams = new ConnectionParams.Builder(clientId)
        .setRedirectUri(redirectUri)
        .showAuthView(true)
        .build();
    SpotifyAppRemote.connect(getApplication(), connectionParams, new ConnectionListener() {
      @Override
      public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        SpotifyViewModel.this.spotifyAppRemote = spotifyAppRemote;
        playTrack(track);
      }

      @Override
      public void onFailure(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
      }
    });
  }

  public void disconnect() {
    SpotifyAppRemote.disconnect(spotifyAppRemote);
  }

  // TODO spotify plays, but doesn't play the right tracks ugh
  private void playClip(Clip clip) {
    spotifyAppRemote.getPlayerApi().play(clip.getTrackKey());
    long beginTimestamp = clip.getBeginTimestamp();
    long endTimestamp = clip.getEndTimestamp();
    spotifyAppRemote.getPlayerApi().seekTo(beginTimestamp);
    spotifyAppRemote.getPlayerApi()
        .subscribeToPlayerState()
        .setEventCallback((playerState) -> {
          final Track track = playerState.track;
          if (track != null) {
            Log.d("SpotifyActivity", track.name + " by " + track.artist.name);
          }
        });
    Runnable r = () -> {
      try {
        TimeUnit.MILLISECONDS.sleep(endTimestamp - beginTimestamp);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      spotifyAppRemote.getPlayerApi().pause();
    };
  }

  private void playTrack(Track track) {
    spotifyAppRemote.getPlayerApi().play(track.uri);
    spotifyAppRemote.getPlayerApi()
        .subscribeToPlayerState()
        .setEventCallback((playerState) -> {
              final Track t = playerState.track;
              // TODO remove after development complete
              if (t != null) {
                Log.d("SpotifyActivity", track.name + " by " + track.artist.name);
              }
            }
        );
  }

}
