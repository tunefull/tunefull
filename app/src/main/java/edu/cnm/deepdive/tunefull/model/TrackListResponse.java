package edu.cnm.deepdive.tunefull.model;

import com.google.gson.annotations.Expose;
import com.spotify.protocol.types.Track;
import java.util.List;

/**
 * Receives responses from the Spotify API with lists of tracks.
 */
public class TrackListResponse {


  @Expose
  private List<ResponseItem> items;

  /**
   * Returns a list of Spotify {@code Item} objects.
   *
   * @return
   */
  public List<ResponseItem> getItems() {
    return items;
  }

  /**
   * Sets a list of Spotify {@code Item} objects.
   *
   * @param items The list of Items.
   */
  public void setItems(List<ResponseItem> items) {
    this.items = items;
  }

  /**
   * An individual {@code Item} within a Spotify API response.
   */
  public static class ResponseItem {

    @Expose
    private Track track;

    /**
     * Returns a {@code Track}.
     *
     * @return
     */
    public Track getTrack() {
      return track;
    }

    /**
     * Sets a {@code Track}.
     *
     * @param track The song's track..
     */
    public void setTrack(Track track) {
      this.track = track;
    }

  }

}
