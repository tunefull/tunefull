package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

public class UserViewModel extends AndroidViewModel {

  private final UserRepository userRepository;
  private final CompositeDisposable pending;
  private final MutableLiveData<Genre> genre;
  private final MutableLiveData<Throwable> throwable;

  public UserViewModel(Application application) {
    super(application);
    userRepository = new UserRepository(application);
    pending = new CompositeDisposable();
    genre = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Genre> getGenre() {
    return genre;
  }

  // TODO don't have return values for this type of method
  // TODO set throwables to null before talking to the repository
  public void saveGenre(Genre genre) {
    throwable.setValue(null);
    this.genre.setValue(genre); // FIXME this is a workaround for not having the server communication in place yet
    pending.add(
        userRepository.saveGenre(genre)
        .subscribe(
            this.genre::postValue,
            throwable:: postValue
        )
    );
  }
}
