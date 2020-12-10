package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel.Source;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * The {@code ClipRepository} provides methods that talk to the {@link TunefullWebService} to
 * receive and save data from and to the server.
 */
public class ClipRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;

  /**
   * The constructor gets instances of the singletons {@code TunefullWebService} and {@link
   * GoogleSignInService}.
   *
   * @param context The current context.
   */
  public ClipRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  /**
   * Gets a list of clips from the server.
   *
   * @param source The type of clip list to get.
   * @return A {@code Single} of the clip list.
   */
  public Single<List<Clip>> getClips(Source source) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(bearerToken -> webService.getClips(bearerToken, source));
  }

  /**
   * Posts a single clip to the server.
   *
   * @param clip The clip to post.
   * @return A {@code Single} of the posted clip.
   */
  public Single<Clip> postClip(Clip clip) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.postClip(token, clip));
  }
}
