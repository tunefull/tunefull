package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.Relationship;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class RelationshipRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;

  public RelationshipRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  public Single<Relationship> saveRelationship(Relationship relationship) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.postRelationship(token, relationship.getRequested()));
  }

  public Single<List<Relationship>> getFriendships() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFriendships);
  }

  public Single<List<Relationship>> getFollowing() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFollows);
  }

  public Single<List<Relationship>> getPendingRelationships() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getPending);
  }

  public Single<Relationship> updateRelationship(Relationship relationship, boolean friendAccepted) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.updateRelationship(token, relationship.getId(), friendAccepted));
  }
}
