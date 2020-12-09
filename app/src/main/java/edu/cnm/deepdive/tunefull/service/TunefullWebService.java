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

public interface TunefullWebService {

  static TunefullWebService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  @PUT("users/me/genre")
  Single<Genre> setGenre(@Header("Authorization") String bearerToken, @Query("genre") Genre genre);

  @GET("clips")
  Single<List<Clip>> getClips(@Header("Authorization") String bearerToken, @Query("source") Source source);

  @POST("clips")
  Single<Clip> postClip(@Header("Authorization") String bearerToken, @Body Clip clip);

  @GET("friendships")
  Single<List<Relationship>> getFriendships(@Header("Authorization") String bearerToken);

  @GET("follows")
  Single<List<Relationship>> getFollows(@Header("Authorization") String bearerToken);

  @GET("pending")
  Single<List<Relationship>> getPending(@Header("Authorization") String bearerToken);

  @POST("friendships")
  Single<Relationship> postRelationship(@Header("Authorization") String bearerToken, @Body User user);

  @PUT("friendships/{relationshipId}")
  Single<Relationship> updateRelationship(@Header("Authorization") String bearerToken, @Path("relationshipId") long relationshipId, @Body boolean accepted);

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