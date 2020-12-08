package edu.cnm.deepdive.tunefull.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.spotify.protocol.types.Track;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class TrackViewModel extends ViewModel {

  private final MutableLiveData<List<Track>> tracks;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final SpotifySignInService signInService;

  public TrackViewModel() {
    tracks = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    signInService = SpotifySignInService.getInstance();
  }

  public LiveData<List<Track>> getTracks() {
//    TODO add spotify webservice class? or does the sdk automatically let us get tracks - if so, how?
//    pending.add(
//        signInService.refresh()
//            .observeOn(Schedulers.io())
//            .flatMap((token) -> /*go to spotify api*/)
//            .subscribe(
//                tracks::postValue,
//                throwable::postValue
//            )
//    );
    return tracks;
  }


}
