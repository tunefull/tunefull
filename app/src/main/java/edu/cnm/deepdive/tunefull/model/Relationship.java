package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;

public class Relationship {

  private long id;

  @Expose
  private User requester;

  @Expose
  private User requested;

  @Expose
  private boolean friendRelationship;

  @Expose
  private Boolean friendAccepted;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getRequester() {
    return requester;
  }

  public void setRequester(User requester) {
    this.requester = requester;
  }

  public User getRequested() {
    return requested;
  }

  public void setRequested(User requested) {
    this.requested = requested;
  }

  public boolean isFriendRelationship() {
    return friendRelationship;
  }

  public void setFriendRelationship(boolean friendRelationship) {
    this.friendRelationship = friendRelationship;
  }

  public Boolean getFriendAccepted() {
    return friendAccepted;
  }

  public void setFriendAccepted(Boolean friendAccepted) {
    this.friendAccepted = friendAccepted;
  }
}
