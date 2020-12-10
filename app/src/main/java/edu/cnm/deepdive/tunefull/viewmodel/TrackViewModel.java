package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.service.SpotifyRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/**
 * The {@code TrackViewModel} talks to the {@link SpotifyRepository} to communicate with the
 * server.
 */
public class TrackViewModel extends AndroidViewModel {

  private final MutableLiveData<Track> track;
  private final MutableLiveData<List<Track>> tracks;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final SpotifyRepository spotifyRepository;

  /**
   * The constructor initializes the {@code MutableLiveData} used in the viewmodel.
   *
   * @param application The current application.
   */
  public TrackViewModel(Application application) {
    super(application);
    track = new MutableLiveData<>();
    tracks = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    spotifyRepository = new SpotifyRepository(application);
    loadTracks();
  }

  /**
   * Returns LiveData of a list of tracks.
   *
   * @return LiveData of a list of tracks.
   */
  public LiveData<List<Track>> getTracks() {
    return tracks;
  }

  /**
   * Returns LiveData of a single track.
   *
   * @return LiveData of a single track.
   */
  public LiveData<Track> getTrack() {
    return track;
  }

  /**
   * Sets the value of LiveData to a specified track.
   *
   * @param track The track to be set.
   */
  public void setTrack(Track track) {
    this.track.setValue(track);
  }

  /**
   * Loads tracks.
   */
  public void loadTracks() {
    throwable.setValue(null);
    pending.add(
        spotifyRepository.getAll()
            .subscribe(
                tracks::postValue,
                throwable::postValue
            )
    );
  }
}
