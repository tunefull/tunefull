package edu.cnm.deepdive.tunefull.service;

import android.content.Context;
import edu.cnm.deepdive.tunefull.model.Relationship;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * The {@code RelationshipRepository} provides methods that talk to the {@link TunefullWebService}
 * to receive and save data from and to the server.
 */
public class RelationshipRepository {

  private final Context context;
  private final TunefullWebService webService;
  private final GoogleSignInService signInService;

  /**
   * The constructor gets instances of the singletons {@code TunefullWebService} and {@link
   * GoogleSignInService}.
   *
   * @param context The current context.
   */
  public RelationshipRepository(Context context) {
    this.context = context;
    webService = TunefullWebService.getInstance();
    signInService = GoogleSignInService.getInstance();
  }

  /**
   * Saves a relationship between two users.
   *
   * @param relationship The relationship to be saved.
   * @return A {@code Single} of the saved relationship.
   */
  public Single<Relationship> saveRelationship(Relationship relationship) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webService.postRelationship(token, relationship.getRequested()));
  }

  /**
   * Returns the current user's friendships.
   *
   * @return A {@code Single} of the current user's friendships.
   */
  public Single<List<Relationship>> getFriendships() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFriendships);
  }

  /**
   * Returns the current user's following relationships.
   *
   * @return A {@code Single} of the current user's following relationships.
   */
  public Single<List<Relationship>> getFollowing() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFollows);
  }

  /**
   * Returns the current user's pending (i.e. unaccepted) relationships.
   *
   * @return A {@code Single} of the current user's profile.
   */
  public Single<List<Relationship>> getPendingRelationships() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(webService::getPending);
  }

  /**
   * Updates a selected relationship.
   *
   * @param relationship   The relationship to be updated.
   * @param friendAccepted Whether the friend request has been accepted or not.
   * @return A {@code Single} of the updated relationship.
   */
  public Single<Relationship> updateRelationship(Relationship relationship,
      boolean friendAccepted) {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(
            (token) -> webService.updateRelationship(token, relationship.getId(), friendAccepted));
  }
}
