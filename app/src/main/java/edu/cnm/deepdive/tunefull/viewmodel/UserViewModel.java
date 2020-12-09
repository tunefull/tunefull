package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends AndroidViewModel {

  private final TunefullWebService webService;
  private final SpotifySignInService signInService;
  private final CompositeDisposable pending;
  private final MutableLiveData<Genre> genre;
  private final MutableLiveData<Throwable> throwable;

  public UserViewModel(Application application) {
    super(application);
    webService = TunefullWebService.getInstance();
    signInService = SpotifySignInService.getInstance();
    pending = new CompositeDisposable();
    genre = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Genre> getGenre() {
    return genre;
  }

  public LiveData<Genre> saveGenre(Genre genre) {
    pending.add(
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap((token) -> webService.setGenre(token, genre))
            .subscribe(
                this.genre::postValue,
                throwable::postValue
            )
    );
    return this.genre;
  }
}
