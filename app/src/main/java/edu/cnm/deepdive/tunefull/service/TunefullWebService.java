package edu.cnm.deepdive.tunefull.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.tunefull.BuildConfig;
import edu.cnm.deepdive.tunefull.model.Clip;
import edu.cnm.deepdive.tunefull.model.Relationship;
import edu.cnm.deepdive.tunefull.model.User;
import edu.cnm.deepdive.tunefull.model.User.Genre;
import edu.cnm.deepdive.tunefull.viewmodel.ClipViewModel.Source;
import io.reactivex.Single;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * An interface with methods that can be sent to the TuneFull server to get certain types of
 * information. All methods include a bearer token for authorization.
 */
public interface TunefullWebService {

  /**
   * Returns an instance of the singleton {@code TunefullWebService}.
   *
   * @return An instance of TunefullWebService.
   */
  static TunefullWebService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Gets the profile of the current user.
   *
   * @param bearerToken The bearer token for authorization.
   * @return A {@code Single} of the server's response.
   */
  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  /**
   * Updates the genre of the current user.
   *
   * @param bearerToken The bearer token for authorization.
   * @param genre       The genre to be set.
   * @return A {@code Single} of the server's response.
   */
  @PUT("users/me/genre")
  Single<Genre> setGenre(@Header("Authorization") String bearerToken, @Query("genre") Genre genre);

  /**
   * Gets a list of clips from the server.
   *
   * @param bearerToken The bearer token for authorization.
   * @param source      An enum describing what type of clip list to return.
   * @return A {@code Single} of the server's response.
   */
  @GET("clips")
  Single<List<Clip>> getClips(@Header("Authorization") String bearerToken,
      @Query("source") Source source);

  /**
   * Posts a clip to the server.
   *
   * @param bearerToken The bearer token for authorization.
   * @param clip        The clip to be posted.
   * @return A {@code Single} of the server's response.
   */
  @POST("clips")
  Single<Clip> postClip(@Header("Authorization") String bearerToken, @Body Clip clip);

  /**
   * Returns a list of friendships.
   *
   * @param bearerToken The bearer token for authorization.
   * @return A {@code Single} of the server's response.
   */
  @GET("friendships")
  Single<List<Relationship>> getFriendships(@Header("Authorization") String bearerToken);

  /**
   * Returns a list of follow relationships.
   *
   * @param bearerToken The bearer token for authorization.
   * @return A {@code Single} of the server's response.
   */
  @GET("follows")
  Single<List<Relationship>> getFollows(@Header("Authorization") String bearerToken);

  /**
   * Returns a list of pending relationships.
   *
   * @param bearerToken The bearer token for authorization.
   * @return A {@code Single} of the server's response.
   */
  @GET("pending")
  Single<List<Relationship>> getPending(@Header("Authorization") String bearerToken);

  /**
   * Posts a relationship to the server.
   *
   * @param bearerToken The bearer token for authorization.
   * @param user        The user that the current user has requested a relationship with.
   * @return A {@code Single} of the server's response.
   */
  @POST("friendships")
  Single<Relationship> postRelationship(@Header("Authorization") String bearerToken,
      @Body User user);

  /**
   * Updates a relationship to the server.
   *
   * @param bearerToken    The bearer token for authorization.
   * @param relationshipId The id of the relationship to be updated.
   * @param accepted       Whether the relationship has been excepted.
   * @return A {@code Single} of the server's response.
   */
  @PUT("friendships/{relationshipId}")
  Single<Relationship> updateRelationship(@Header("Authorization") String bearerToken,
      @Path("relationshipId") long relationshipId, @Body boolean accepted);

  /**
   * Holds an instance of the Tunefull web service.
   */
  class InstanceHolder {

    private static final TunefullWebService INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
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
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(TunefullWebService.class);
    }
  }
}