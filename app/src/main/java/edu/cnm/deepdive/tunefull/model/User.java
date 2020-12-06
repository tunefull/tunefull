package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;

public class User {

  @Expose
  private long id;

  @Expose
  private String username;

  private String email;

  @Expose
  private String genre;

  private String oauth;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getOauth() {
    return oauth;
  }

  public void setOauth(String oauth) {
    this.oauth = oauth;
  }
}
