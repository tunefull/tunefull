package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import java.util.Date;

/**
 * Receives data from the server database for clips.
 */
public class Clip {

  private long id;

  @Expose
  private String songTitle;

  @Expose
  private String artist;

  @Expose
  private String album;

  @Expose
  private String trackKey;

  @Expose
  private long beginTimestamp;

  @Expose
  private long endTimestamp;

  @Expose
  private Date dateTimePosted;

  @Expose
  private User user;

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
   * Returns the song title of the clip.
   *
   * @return
   */
  public String getSongTitle() {
    return songTitle;
  }

  /**
   * Sets the song title of the clip.
   *
   * @param songTitle The song title of the clip.
   */
  public void setSongTitle(String songTitle) {
    this.songTitle = songTitle;
  }

  /**
   * Returns the artist's name of the clip.
   *
   * @return
   */
  public String getArtist() {
    return artist;
  }

  /**
   * Sets the artist's name of the clip.
   *
   * @param artist The artist's name of the clip.
   */
  public void setArtist(String artist) {
    this.artist = artist;
  }

  /**
   * Returns the album name of the clip.
   *
   * @return
   */
  public String getAlbum() {
    return album;
  }

  /**
   * Sets the album name of the clip.
   *
   * @param album The album name of the clip.
   */
  public void setAlbum(String album) {
    this.album = album;
  }

  /**
   * Returns the track key of the clip.
   *
   * @return
   */
  public String getTrackKey() {
    return trackKey;
  }

  /**
   * Sets the track key of the clip.
   *
   * @param trackKey The track key of the clip.
   */
  public void setTrackKey(String trackKey) {
    this.trackKey = trackKey;
  }

  /**
   * Returns the beginning timestamp of the clip.
   *
   * @return
   */
  public long getBeginTimestamp() {
    return beginTimestamp;
  }

  /**
   * Sets the beginning timestamp of the clip.
   *
   * @param beginTimestamp The beginning timestamp of the clip.
   */
  public void setBeginTimestamp(long beginTimestamp) {
    this.beginTimestamp = beginTimestamp;
  }

  /**
   * Returns the end timestamp of the clip.
   *
   * @return
   */
  public long getEndTimestamp() {
    return endTimestamp;
  }

  /**
   * Sets the end timestamp of the clip.
   *
   * @param endTimestamp The end timestamp of the clip.
   */
  public void setEndTimestamp(long endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  /**
   * Returns the date and time the clip was posted.
   *
   * @return
   */
  public Date getDateTimePosted() {
    return dateTimePosted;
  }

  /**
   * Sets the date and time the clip was posted.
   *
   * @param dateTimePosted The date and time the clip was posted.
   */
  public void setDateTimePosted(Date dateTimePosted) {
    this.dateTimePosted = dateTimePosted;
  }

  /**
   * Returns the {@link User} that posted the clip.
   *
   * @return
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the {@code User} that posted the clip.
   *
   * @param user The user that posted the clip.
   */
  public void setUser(User user) {
    this.user = user;
  }
}
