package edu.cnm.deepdive.tunefull.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.tunefull.BuildConfig;
import edu.cnm.deepdive.tunefull.model.TrackListResponse;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * An interface with methods that can be sent to the Spotify API.
 */
public interface SpotifyServiceProxy {

  /**
   * Returns a response of a list of saved or liked tracks from the current user.
   *
   * @param bearerToken The bearer token for authorization.
   * @return A response with the user's saved tracks.
   */
  @GET("me/tracks")
  Single<TrackListResponse> getSavedTracks(@Header("Authorization") String bearerToken);

  /**
   * Returns an instance of the singleton {@code SpotifyServiceProxy}.
   *
   * @return An instance of SpotifyServiceProxy.
   */
  static SpotifyServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Holds an instance of the Spotify service proxy.
   */
  class InstanceHolder {

    private static final SpotifyServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.SPOTIFY_BASE_URL)
          .build();
      INSTANCE = retrofit.create(SpotifyServiceProxy.class);
    }
  }

}
