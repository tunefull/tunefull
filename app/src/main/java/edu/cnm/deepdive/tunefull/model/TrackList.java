package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import com.spotify.protocol.types.Track;
import java.util.List;

//TODO probably take this out - I don't think it's needed.
public class TrackList {

  @Expose
  private List<Track> tracks;

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}