package edu.cnm.deepdive.tunefull.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.ui.main.ClipFeedFragment.FeedType;
import java.util.List;

public class ClipViewModel extends ViewModel {

  private MutableLiveData<List<Clip>> discoveryClips;
  private MutableLiveData<List<Clip>> feedClips;
  private MutableLiveData<Integer> index;
  private FeedType feedType;

  public ClipViewModel() {
    discoveryClips = new MutableLiveData<>();
    feedClips = new MutableLiveData<>();
    index = new MutableLiveData<>();
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
    if (feedType == FeedType.DISCOVERY) {
      return discoveryClips;
    } else {
      return feedClips;
    }
  }
}