package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.tunefull.model.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;
  // TODO Add fields as appropriate for access to DAOs, etc.

  public UserRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  public Single<User> getProfileFromServer() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap((account) -> webService.getProfile(getBearerToken(account)));
    // TODO Add additional logic for persistence as appropriate
  }

  private String getBearerToken(GoogleSignInAccount account) {
    return String.format("Bearer %s", account.getIdToken());
  }

}
