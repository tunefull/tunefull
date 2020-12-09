package edu.cnm.deepdive.tunefull.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.model.Relationship;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import edu.cnm.deepdive.tunefull.service.TunefullWebService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class RelationshipViewModel extends AndroidViewModel {

  private final CompositeDisposable pending;
  private final SpotifySignInService signInService;
  private final TunefullWebService webService;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<List<Relationship>> friendships;
  private final MutableLiveData<List<Relationship>> follows;
  private final MutableLiveData<List<Relationship>> pendingRelationships;
  private final MutableLiveData<RelationshipType> relationshipType;

  public RelationshipViewModel(@NonNull Application application) {
    super(application);
    pending = new CompositeDisposable();
    signInService = SpotifySignInService.getInstance();
    webService = TunefullWebService.getInstance();
    throwable = new MutableLiveData<>();
    friendships = new MutableLiveData<>();
    follows = new MutableLiveData<>();
    pendingRelationships = new MutableLiveData<>();
    relationshipType = new MutableLiveData<>();
  }

  public void setRelationshipType(RelationshipType type) {
    relationshipType.setValue(type);
  }

  public LiveData<List<Relationship>> getRelationships(RelationshipType type) {
    switch (type) {
      case FRIENDS:
        return getFriendships();
      case FOLLOWING:
        return getFollows();
      default:
        return getPendingRelationships();
    }
  }

  private LiveData<List<Relationship>> getFriendships() {
    pending.add(
        signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap(webService::getFriendships)
        .subscribe(
            friendships::postValue,
            throwable::postValue
        )
    );
    return friendships;
  }

  private LiveData<List<Relationship>> getFollows() {
    pending.add(
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap(webService::getFollows)
            .subscribe(
                follows::postValue,
                throwable::postValue
            )
    );
    return follows;
  }

  private LiveData<List<Relationship>> getPendingRelationships() {
    pending.add(
        signInService.refresh()
            .observeOn(Schedulers.io())
            .flatMap(webService::getPending)
            .subscribe(
                pendingRelationships::postValue,
                throwable::postValue
            )
    );
    return pendingRelationships;
  }
}
