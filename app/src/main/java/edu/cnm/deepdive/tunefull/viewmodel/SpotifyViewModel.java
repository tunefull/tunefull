package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Capabilities;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.model.Clip;
import java.util.concurrent.TimeUnit;

/**
 * The {@code SpotifyViewModel} uses the Spotify SDK to talk to the Spotify app on the device.
 */
public class SpotifyViewModel extends AndroidViewModel {

  private static String clientId;
  private static String redirectUri;
  private SpotifyAppRemote spotifyAppRemote;

  /**
   * The constructor sets up the clientID and redirectURI for the app with Spotify authorization.
   *
   * @param application The application.
   */
  public SpotifyViewModel(Application application) {
    super(application);
    clientId = application.getString(R.string.client_id);
    redirectUri = application.getString(R.string.redirect_uri);
  }

  /**
   * Plays a clip.
   *
   * @param clip The clip to be played.
   */
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

  /**
   * Plays a track.
   * @param track The track to be played.
   */
  public void play(Track track) {
    ConnectionParams connectionParams = new ConnectionParams.Builder(clientId)
        .setRedirectUri(redirectUri)
        .showAuthView(true)
        .build();
    SpotifyAppRemote.connect(getApplication(), connectionParams, new ConnectionListener() {
      @Override
      public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        SpotifyViewModel.this.spotifyAppRemote = spotifyAppRemote;
        Capabilities capabilities = new Capabilities(true);
        playTrack(track);
      }

      @Override
      public void onFailure(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
      }
    });
  }

  /**
   * Disconnects from the Spotify App Remote.
   */
  public void disconnect() {
    SpotifyAppRemote.disconnect(spotifyAppRemote);
  }

  // TODO spotify plays, but doesn't always play the right tracks ugh
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
                // TODO use a different api or something
                // this has the right track, so somehow in translation to the spotify sdk it's changing to a different one?
                // capabilities.canPlayOnDemand must be true to play a track - and this is only true for premium users........ugh
              }
            }
        );
  }

}
