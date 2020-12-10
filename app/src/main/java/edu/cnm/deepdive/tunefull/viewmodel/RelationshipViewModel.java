package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.model.Relationship;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.service.RelationshipRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

/**
 * The {@code RelationshipViewModel} talks to the {@link RelationshipRepository} to communicate with
 * the server.
 */
public class RelationshipViewModel extends AndroidViewModel {

  private final CompositeDisposable pending;
  private final RelationshipRepository relationshipRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<List<Relationship>> friendships;
  private final MutableLiveData<List<Relationship>> follows;
  private final MutableLiveData<List<Relationship>> pendingRelationships;
  private final MutableLiveData<Relationship> relationship;

  /**
   * The constructor initializes the {@code MutableLiveData} used in the viewmodel.
   *
   * @param application The current application.
   */
  public RelationshipViewModel(@NonNull Application application) {
    super(application);
    pending = new CompositeDisposable();
    relationshipRepository = new RelationshipRepository(application);
    throwable = new MutableLiveData<>();
    friendships = new MutableLiveData<>();
    follows = new MutableLiveData<>();
    pendingRelationships = new MutableLiveData<>();
    relationship = new MutableLiveData<>();
  }

  /**
   * Returns LiveData of a list of relationships.
   *
   * @param type The type of relationship.
   * @return LiveData of a list of relationships.
   */
  public LiveData<List<Relationship>> getRelationships(RelationshipType type) {
    switch (type) {
      case FRIENDS:
        getFriendships();
        return friendships;
      case FOLLOWING:
        getFollows();
        return follows;
      default:
        getPendingRelationships();
        return pendingRelationships;
    }
  }

  /**
   * Updates LiveData with a list of friendships.
   */
  public void getFriendships() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getFriendships()
            .subscribe(
                friendships::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Updates LiveData with a list of follows.
   */
  public void getFollows() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getFollowing()
            .subscribe(
                follows::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Updates LiveData with a list of pending friend requests.
   */
  public void getPendingRelationships() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getPendingRelationships()
            .subscribe(
                pendingRelationships::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Saves a relationship to the server.
   *
   * @param user The other user in the relationship.
   */
  public void saveRelationship(User user) {
    throwable.setValue(null);
    Relationship relationship = new Relationship();
    relationship.setFriendRelationship(true);
    relationship.setRequested(user);
    this.relationship.setValue(relationship);
    pending.add(
        relationshipRepository.saveRelationship(relationship)
            .subscribe(
                this.relationship::postValue,
                throwable::postValue
            )
    );
  }

  /**
   * Updates a relationship to the server.
   *
   * @param relationship   The relationship to update.
   * @param friendAccepted Whether the friend request has been accepted or declined.
   */
  public void updateRelationship(Relationship relationship, boolean friendAccepted) {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.updateRelationship(relationship, friendAccepted)
            .subscribe(
                this.relationship::postValue,
                throwable::postValue
            )
    );
  }

}
