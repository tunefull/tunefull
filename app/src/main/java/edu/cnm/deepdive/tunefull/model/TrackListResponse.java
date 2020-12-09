package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import com.spotify.protocol.types.Track;
import java.util.List;

/**
 * Holds data in the database for the track list responses.
 */
public class TrackListResponse {


  @Expose
  private List<ResponseItem> items;

  /**
   * Returns a list of items.
   *
   * @return
   */
  public List<ResponseItem> getItems() {
    return items;
  }

  /**
   * Sets a list of items.
   *
   * @param items The list of items.
   */
  public void setItems(List<ResponseItem> items) {
    this.items = items;
  }

  public static class ResponseItem {

    @Expose
    private Track track;

    /**
     * Returns a song track.
     *
     * @return
     */
    public Track getTrack() {
      return track;
    }

    /**
     * Sets a song track.
     *
     * @param track The song track.
     */
    public void setTrack(Track track) {
      this.track = track;
    }

  }

}
