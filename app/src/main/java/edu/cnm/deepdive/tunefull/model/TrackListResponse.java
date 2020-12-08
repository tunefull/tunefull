package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import com.spotify.protocol.types.Track;
import java.util.List;

public class TrackListResponse {

  @Expose
  private List<ResponseItem> items;

  public List<ResponseItem> getItems() {
    return items;
  }

  public void setItems(List<ResponseItem> items) {
    this.items = items;
  }

  public static class ResponseItem {

    @Expose
    private Track track;

    public Track getTrack() {
      return track;
    }

    public void setTrack(Track track) {
      this.track = track;
    }

  }

}
