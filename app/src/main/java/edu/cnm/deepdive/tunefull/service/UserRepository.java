package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * The {@code UserRepository} provides methods that talk to the {@link TunefullWebService} to
 * receive and save data from and to the server.
 */
public class UserRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;

  /**
   * The constructor gets instances of the singletons {@code TunefullWebService} and {@link
   * GoogleSignInService}.
   *
   * @param context The current context.
   */
  public UserRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  /**
   * Returns the current user's profile.
   *
   * @return A {@code Single} of the current user's profile.
   */
  public Single<User> getProfileFromServer() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getProfile);
  }

  /**
   * Saves and returns the current user's updated genre.
   *
   * @param genre The genre to be saved.
   * @return A {@code Single} of the genre that was saved.
   */
  public Single<Genre> saveGenre(Genre genre) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.setGenre(token, genre));
  }

}
