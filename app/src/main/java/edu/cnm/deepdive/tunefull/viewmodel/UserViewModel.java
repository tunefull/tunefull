package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

/**
 * The {@code UserViewModel} talks to the {@link UserRepository} to communicate with the server.
 */
public class UserViewModel extends AndroidViewModel {

  private final UserRepository userRepository;
  private final CompositeDisposable pending;
  private final MutableLiveData<Genre> genre;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<User> user;

  /**
   * The constructor initializes the {@code MutableLiveData} used in the viewmodel.
   *
   * @param application The current application.
   */
  public UserViewModel(Application application) {
    super(application);
    userRepository = new UserRepository(application);
    pending = new CompositeDisposable();
    genre = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    user = new MutableLiveData<>();
  }

  /**
   * Returns LiveData of the user's current genre.
   *
   * @return LiveData of the user's current genre.
   */
  public LiveData<Genre> getGenre() {
    return genre;
  }

  /**
   * Returns LiveData of the current user.
   *
   * @return LiveData of the current user.
   */
  public LiveData<User> getUser() {
    loadUser();
    return user;
  }

  /**
   * Saves a genre for the current user.
   *
   * @param genre The genre to be saved.
   */
  public void saveGenre(Genre genre) {
    throwable.setValue(null);
    this.genre.setValue(
        genre); // FIXME this is a workaround for not having the server communication in place yet
    pending.add(
        userRepository.saveGenre(genre)
            .subscribe(
                this.genre::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Loads a user from the server.
   */
  public void loadUser() {
    throwable.setValue(null);
    pending.add(
        userRepository.getProfileFromServer()
            .subscribe(
                this.user::postValue,
                throwable::postValue
            )
    );
  }
}
