package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import java.util.Date;

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
  private int beginTimestamp;

  @Expose
  private int endTimestamp;

  @Expose
  private Date dateTimePosted;

  @Expose
  private User user;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSongTitle() {
    return songTitle;
  }

  public void setSongTitle(String songTitle) {
    this.songTitle = songTitle;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getTrackKey() {
    return trackKey;
  }

  public void setTrackKey(String trackKey) {
    this.trackKey = trackKey;
  }

  public int getBeginTimestamp() {
    return beginTimestamp;
  }

  public void setBeginTimestamp(int beginTimestamp) {
    this.beginTimestamp = beginTimestamp;
  }

  public int getEndTimestamp() {
    return endTimestamp;
  }

  public void setEndTimestamp(int endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  public Date getDateTimePosted() {
    return dateTimePosted;
  }

  public void setDateTimePosted(Date dateTimePosted) {
    this.dateTimePosted = dateTimePosted;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
