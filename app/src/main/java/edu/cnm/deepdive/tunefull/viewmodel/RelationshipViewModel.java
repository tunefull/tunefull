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
import edu.cnm.deepdive.tunefull.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class RelationshipViewModel extends AndroidViewModel {

  private final CompositeDisposable pending;
  private final RelationshipRepository relationshipRepository;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<List<Relationship>> friendships;
  private final MutableLiveData<List<Relationship>> follows;
  private final MutableLiveData<List<Relationship>> pendingRelationships;
  private final MutableLiveData<Relationship> relationship;

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

  public void getFriendships() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getFriendships()
            .subscribe(
                friendships::postValue,
                throwable:: postValue
            )
    );
  }

  public void getFollows() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getFollowing()
            .subscribe(
                follows::postValue,
                throwable:: postValue
            )
    );
  }

  public void getPendingRelationships() {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.getPendingRelationships()
            .subscribe(
                pendingRelationships::postValue,
                throwable:: postValue
            )
    );
  }

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
                throwable:: postValue
            )
    );
  }

  public void updateRelationship(Relationship relationship, boolean friendAccepted) {
    throwable.setValue(null);
    pending.add(
        relationshipRepository.updateRelationship(relationship, friendAccepted)
            .subscribe(
                this.relationship::postValue,
                throwable:: postValue
            )
    );
  }

}
