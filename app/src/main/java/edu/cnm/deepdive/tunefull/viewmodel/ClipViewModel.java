package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.controller.ClipFeedFragment.FeedType;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.service.ClipRepository;
import edu.cnm.deepdive.tunefull.service.GoogleSignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class ClipViewModel extends AndroidViewModel {

  private final MutableLiveData<Clip> clip;
  private final MutableLiveData<List<Clip>> clips;
  private final MutableLiveData<Integer> index;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final GoogleSignInService signInService;
  private final TunefullWebService webService;
  private final ClipRepository clipRepository;

  private FeedType feedType;

  public ClipViewModel(Application application) {
    super(application);
    clip = new MutableLiveData<>();
    clips = new MutableLiveData<>();
    index = new MutableLiveData<>();
    pending = new CompositeDisposable();
    signInService = GoogleSignInService.getInstance();
    webService = TunefullWebService.getInstance();
    clipRepository = new ClipRepository(application);
    throwable = new MutableLiveData<>();
  }

  public void setIndex(int index) {
    this.index.setValue(index);
    feedType = (index == 0) ? FeedType.DISCOVERY : FeedType.FRIENDS_FOLLOWS;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public FeedType getFeedType() {
    return feedType;
  }

  public LiveData<List<Clip>> getClips() {
    loadClips();
    return clips;
  }

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