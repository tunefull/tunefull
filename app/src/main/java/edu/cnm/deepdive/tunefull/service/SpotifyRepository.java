package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.model.TrackListResponse.ResponseItem;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code SpotifyRepository} provides methods that talk to the {@link SpotifyServiceProxy} to
 * receive data from the server.
 */
public class SpotifyRepository {

  private final Context context;
  private final SpotifySignInService signInService;
  private final SpotifyServiceProxy spotifyServiceProxy;

  /**
   * The constructor gets instances of the singletons {@code SpotifyServiceProxy} and {@link
   * SpotifySignInService}.
   *
   * @param context The current context.
   */
  public SpotifyRepository(Context context) {
    this.context = context;
    signInService = SpotifySignInService.getInstance();
    spotifyServiceProxy = SpotifyServiceProxy.getInstance();
  }

  /**
   * Gets a list of saved/liked tracks for the current user.
   *
   * @return A list of saved tracks.
   */
  public Single<List<Track>> getAll() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap((token) -> spotifyServiceProxy.getSavedTracks(token)
            .map((response) -> response.getItems().stream()
                .map(ResponseItem::getTrack)
                .collect(Collectors.toList())
            )
        );
  }

}
