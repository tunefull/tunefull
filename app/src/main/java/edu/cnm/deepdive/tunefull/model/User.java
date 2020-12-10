package edu.cnm.deepdive.tunefull.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;

/**
 * Receives data from the server database for users. This also includes enumerated types from
 * the {@link Genre} enums.
 */
public class User {

  @Expose
  private long id;

  @Expose
  private String username;

  private String email;

  @Expose
  private Genre genre;

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
   * Returns the username of the user.
   *
   * @return
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username The username to be set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the email of the user.
   *
   * @return
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email The email to be set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the current favorite genre of the user.
   *
   * @return
   */
  public Genre getGenre() {
    return genre;
  }

  /**
   * Sets the current favorite genre of the user.
   *
   * @param genre The current favorite genre of the user.
   */
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
