package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;

/**
 * Holds data in the database for relationships between users.
 */
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

  /**
   * Returns the auto-generated id for the clip.
   *
   * @return
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the auto-generated id for the clip.
   *
   * @param id The id to be set.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the user who sent the friend request.
   *
   * @return
   */
  public User getRequester() {
    return requester;
  }

  /**
   * Sets the user who sent the friend request.
   *
   * @param requester The user who sent the friend request.
   */
  public void setRequester(User requester) {
    this.requester = requester;
  }

  /**
   * Returns the user who was sent the friend request.
   *
   * @return
   */
  public User getRequested() {
    return requested;
  }

  /**
   * Sets the user was sent the friend request.
   *
   * @param requested The user who was sent the friend request.
   */
  public void setRequested(User requested) {
    this.requested = requested;
  }

  /**
   * Returns the boolean that determines whether the users are in a friend relationship.
   *
   * @return
   */
  public boolean isFriendRelationship() {
    return friendRelationship;
  }

  /**
   * Sets the boolean that determines whether the users are in a friend relationship.
   *
   * @param friendRelationship The boolean that determines whether the users are in a friend relationship.
   */
  public void setFriendRelationship(boolean friendRelationship) {
    this.friendRelationship = friendRelationship;
  }

  /**
   * Returns the boolean that determines whether one user accepted another user's
   * request for a friend relationship.
   *
   * @return
   */
  public Boolean getFriendAccepted() {
    return friendAccepted;
  }

  /**
   * Sets the boolean that determines whether one user accepted another user's
   * request for a friend relationship.
   *
   * @param friendAccepted The boolean that determines whether the users are in a friend relationship.
   */
  public void setFriendAccepted(Boolean friendAccepted) {
    this.friendAccepted = friendAccepted;
  }
}
