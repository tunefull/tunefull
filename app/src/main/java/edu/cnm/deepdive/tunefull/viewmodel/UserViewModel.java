package edu.cnm.deepdive.tunefull.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

  private final TunefullWebService webService;
  private final SpotifySignInService signInService;
  private final CompositeDisposable pending;
  private final MutableLiveData<Genre> genre;
  private final MutableLiveData<Throwable> throwable;

  public UserViewModel() {
    webService = TunefullWebService.getInstance();
    signInService = SpotifySignInService.getInstance();
    pending = new CompositeDisposable();
    genre = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Genre> getGenre() {
    return genre;
  }

  public LiveData<Genre> saveGenre(Genre g) {
    pending.add(
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap((token) -> webService.setGenre(token, g))
            .subscribe(
                genre::postValue,
                throwable::postValue
            )
    );
    return this.genre;
  }
}
