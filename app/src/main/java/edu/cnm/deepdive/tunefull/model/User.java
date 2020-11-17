package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO Make any additions to change into an entity class (if appropriate)
public class User {

  @Expose
  @SerializedName("id")
  private long externalId;

  @Expose
  private String username;

  public long getExternalId() {
    return externalId;
  }

  public void setExternalId(long externalId) {
    this.externalId = externalId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
