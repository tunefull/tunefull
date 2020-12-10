package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.controller.ClipFeedFragment.FeedType;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.service.ClipRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/**
 * The {@code ClipViewModel} talks to the {@link ClipRepository} to communicate with the server.
 */
public class ClipViewModel extends AndroidViewModel {

  private final MutableLiveData<Clip> clip;
  private final MutableLiveData<List<Clip>> clips;
  private final MutableLiveData<Integer> index;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final ClipRepository clipRepository;

  private FeedType feedType;

  /**
   * The constructor initializes the {@code MutableLiveData} used in the viewmodel.
   *
   * @param application The current application.
   */
  public ClipViewModel(Application application) {
    super(application);
    clip = new MutableLiveData<>();
    clips = new MutableLiveData<>();
    index = new MutableLiveData<>();
    pending = new CompositeDisposable();
    clipRepository = new ClipRepository(application);
    throwable = new MutableLiveData<>();
  }

  /**
   * Sets the index of the screen as well as the clip feed type.
   *
   * @param index The index of the screen.
   */
  public void setIndex(int index) {
    this.index.setValue(index);
    if (index < FeedType.values().length) {
      feedType = FeedType.values()[index];
    } else {
      feedType = FeedType.DISCOVERY;
    }
  }

  /**
   * Returns LiveData of a throwable.
   *
   * @return LiveData of a throwable.
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Returns the current clip feed type.
   *
   * @return The current clip feed type.
   */
  public FeedType getFeedType() {
    return feedType;
  }

  /**
   * Returns LiveData of a list of clips.
   *
   * @return LiveData of a list of clips.
   */
  public LiveData<List<Clip>> getClips() {
    loadClips();
    return clips;
  }

  /**
   * Loads a list of clips based on the feed type.
   */
  public void loadClips() {
    throwable.setValue(null);
    Source source;
    if (feedType == FeedType.DISCOVERY) {
      source = Source.ALL;
    } else if (feedType == FeedType.FRIENDS_FOLLOWS) {
      source = Source.RELATIONSHIPS;
    } else {
      source = Source.ME;
    }
    pending.add(
        clipRepository.getClips(source)
            .subscribe(
                clips::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Posts a clip to the server.
   *
   * @param track          The track of the clip.
   * @param beginTimestamp The beginning timestamp of the clip.
   * @param endTimestamp   The ending timestamp of the clip.
   */
  public void postClip(Track track, long beginTimestamp, long endTimestamp) {
    throwable.setValue(null);
    Clip clip = new Clip();
    clip.setSongTitle(track.name);
    if (track.artist != null) {
      clip.setArtist(track.artist.name);
    } else {
      clip.setArtist(track.artists.get(0).name);
    }
    if (track.album != null) {
      clip.setAlbum(track.album.name);
    }
    clip.setTrackKey(track.uri);
    clip.setBeginTimestamp(beginTimestamp);
    clip.setEndTimestamp(endTimestamp);
    pending.add(
        clipRepository.postClip(clip)
            .subscribe(
                this.clip::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * The Source enum enumerates the different types of lists of clips available for access.
   */
  public enum Source {
    ME, FRIENDS, FOLLOWING, RELATIONSHIPS, ALL
  }

}