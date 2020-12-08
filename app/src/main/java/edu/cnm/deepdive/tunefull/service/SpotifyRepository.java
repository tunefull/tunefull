package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.model.TrackListResponse.ResponseItem;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.stream.Collectors;

public class SpotifyRepository {

  private final Context context;
  private final SpotifySignInService signInService;
  private final SpotifyServiceProxy spotifyServiceProxy;

  public SpotifyRepository(Context context) {
    this.context = context;
    signInService = SpotifySignInService.getInstance();
    spotifyServiceProxy = SpotifyServiceProxy.getInstance();
  }

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
