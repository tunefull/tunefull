package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel.Source;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class ClipRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;

  public ClipRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  public Single<List<Clip>> getClips(Source source) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(bearerToken -> webService.getClips(bearerToken, source));
  }

  public Single<Clip> postClip(Clip clip) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.postClip(token, clip));
  }
}
