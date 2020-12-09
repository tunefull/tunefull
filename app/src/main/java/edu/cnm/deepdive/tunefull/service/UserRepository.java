package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final SpotifySignInService signInService;

  public UserRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = SpotifySignInService.getInstance();
  }

  public Single<User> getProfileFromServer() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getProfile);
  }

  public Single<Genre> saveGenre(Genre genre) {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.setGenre(token, genre));
  }

}
