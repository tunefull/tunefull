package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.controller.ClipFeedFragment.FeedType;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class ClipViewModel extends AndroidViewModel {

  private final MutableLiveData<Clip> clip;
  private final MutableLiveData<List<Clip>> clips;
  private final MutableLiveData<Integer> index;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final SpotifySignInService signInService;
  private final TunefullWebService webService;

  private FeedType feedType = FeedType.ME;

  public ClipViewModel(Application application) {
    super(application);
    clip = new MutableLiveData<>();
    clips = new MutableLiveData<>();
    index = new MutableLiveData<>();
    pending = new CompositeDisposable();
    signInService = SpotifySignInService.getInstance();
    webService = TunefullWebService.getInstance();
    throwable = new MutableLiveData<>();
  }

  public void setIndex(int index) {
    this.index.setValue(index);
    feedType = (index == 0) ? FeedType.DISCOVERY : FeedType.FRIENDS_FOLLOWS;
  }

  public FeedType getFeedType() {
    return feedType;
  }

  public LiveData<List<Clip>> getClips() {
    Source source;
    if (feedType == FeedType.DISCOVERY) {
      source = Source.ALL;
    } else if (feedType == FeedType.FRIENDS_FOLLOWS) {
      source = Source.RELATIONSHIPS;
    } else {
      source = Source.ME;
    }
    pending.add(
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap((token) -> webService.getClips(token, source))
            .subscribe(
                clips::postValue,
                throwable::postValue
            )
    );
    return clips;
  }

  public LiveData<Clip> postClip(Track track, long beginTimestamp, long endTimestamp) {
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
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap((token) -> webService.postClip(token, clip))
            .subscribe(
                this.clip::postValue,
                throwable::postValue
            )
    );
    return this.clip;
  }

  /**
   * The Source enum enumerates the different types of lists of clips available for access.
   */
  public enum Source {
    ME, FRIENDS, FOLLOWING, RELATIONSHIPS, ALL
  }

}