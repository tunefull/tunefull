package edu.cnm.deepdive.tunefull.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;

public class User {

  @Expose
  private long id;

  @Expose
  private String username;

  private String email;

  @Expose
  private Genre genre;

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

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  /**
   * The {@code Genre} enum enumerates different musical genres that the user can select from for
   * their favorite genre.
   */
  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN, FILM;

    @NonNull
    @Override
    public String toString() {
      return super.toString()
          .toLowerCase()
          .replace("_", " ");
    }
  }
}
