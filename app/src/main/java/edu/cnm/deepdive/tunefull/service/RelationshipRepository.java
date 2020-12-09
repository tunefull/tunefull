package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.Relationship;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class RelationshipRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final SpotifySignInService signInService;


  public RelationshipRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = SpotifySignInService.getInstance();
  }

  public Single<Relationship> saveRelationship(Relationship relationship) {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.postRelationship(token, relationship.getRequested()));
  }

  public Single<List<Relationship>> getFriendships() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFriendships);
  }

  public Single<List<Relationship>> getFollowing() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFollows);
  }

  public Single<List<Relationship>> getPendingRelationships() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getPending);
  }
}
