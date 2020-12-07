package edu.cnm.deepdive.tunefull.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import edu.cnm.deepdive.tunefull.ui.main.ClipFeedFragment.FeedType;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class ClipViewModel extends ViewModel {

  private MutableLiveData<List<Clip>> clips;
  private MutableLiveData<Integer> index;
  private FeedType feedType = FeedType.ME;
  private CompositeDisposable pending;
  private final SpotifySignInService signInService;
  private final TunefullWebService webService;
  private final MutableLiveData<Throwable> throwable;

  public ClipViewModel() {
    clips = new MutableLiveData<>();
    index = new MutableLiveData<>();
    pending = new CompositeDisposable();
    signInService = SpotifySignInService.getInstance();
    webService = TunefullWebService.getInstance();
    throwable = new MutableLiveData<>();
  }

  public void setIndex(int index) {
    this.index.setValue(index);
    feedType = (index == 0)? FeedType.DISCOVERY : FeedType.FRIENDS_FOLLOWS;
  }

  public FeedType getFeedType() {
    return feedType;
  }

  // TODO fix this method to get clips from the webservice
  public LiveData<List<Clip>> getClips() {
    Source source;
    if (feedType == FeedType.DISCOVERY) {
      source = Source.ALL;
    } else if (feedType == FeedType.FRIENDS_FOLLOWS){
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

  /**
   * The Source enum enumerates the different types of lists of clips available for access.
   */
  public enum Source {
    ME, FRIENDS, FOLLOWING, RELATIONSHIPS, ALL
  }

}