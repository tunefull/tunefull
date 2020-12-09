package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.service.SpotifyRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class TrackViewModel extends AndroidViewModel {

  private final MutableLiveData<Track> track;
  private final MutableLiveData<List<Track>> tracks;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final SpotifyRepository spotifyRepository;

  public TrackViewModel(Application application) {
    super(application);
    track = new MutableLiveData<>();
    tracks = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    spotifyRepository = new SpotifyRepository(application);
    loadTracks();
    // TODO take this out?
  }

  public LiveData<List<Track>> getTracks() {
    return tracks;
  }

  public LiveData<Track> getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track.setValue(track);
  }

  public void loadTracks() {
    pending.add(
        spotifyRepository.getAll()
        .subscribe(
            tracks::postValue,
            throwable::postValue
        )
    );
  }
}
