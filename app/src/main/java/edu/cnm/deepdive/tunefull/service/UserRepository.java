package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final SpotifySignInService signInService;
  // TODO Add fields as appropriate for access to DAOs, etc.

  public UserRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = SpotifySignInService.getInstance();
  }

  public Single<User> getProfileFromServer() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getProfile);
    // TODO Add additional logic for persistence as appropriate
  }

}
