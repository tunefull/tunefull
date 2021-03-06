package edu.cnm.deepdive.tunefull.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.tunefull.R;
import io.reactivex.Single;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

/**
 * The {@code SpotifySignInService} class provides methods that allow the app to authenticate and
 * connect with the Spotify app.
 */
public class SpotifySignInService {

  private static final String AUTH_STATE_KEY = "auth_state";

  @SuppressLint("StaticFieldLeak")
  private static Context context;
  private final AuthState authState;
  private final AuthorizationService service;
  private final String clientId;
  private final Uri redirectUri;
  private final String authScope;
  private final SharedPreferences preferences;

  private SpotifySignInService() {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    AuthState authState = getAuthState();
    if (authState == null) {
      AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
          Uri.parse(context.getString(R.string.authorization_endpoint_uri)),
          Uri.parse(context.getString(R.string.token_endpoint_uri)));
      authState = new AuthState(serviceConfig);
      setAuthState(authState);
    }
    this.authState = authState;
    service = new AuthorizationService(context);
    clientId = context.getString(R.string.client_id);
    redirectUri = Uri.parse(context.getString(R.string.redirect_uri));
    authScope = context.getString(R.string.authorization_scope);
  }

  /**
   * Sets the application context for the class.
   *
   * @param context The application context.
   */
  public static void setContext(Context context) {
    SpotifySignInService.context = context;
  }

  /**
   * Returns an instance of the singleton SpotifySignInService.
   *
   * @return An instance of SpotifySignInService.
   */
  public static SpotifySignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Allows the activity to start a signin request.
   *
   * @param activity          The current activity.
   * @param requestCode       A request code for the signin request.
   * @param completedActivity The {@link edu.cnm.deepdive.tunefull.controller.LoginResponseActivity}
   *                          that the app will direct to.
   * @param cancelledActivity The current {@link edu.cnm.deepdive.tunefull.controller.LoginActivity}.
   */
  public void startSignIn(AppCompatActivity activity, int requestCode,
      Class<? extends AppCompatActivity> completedActivity,
      Class<? extends AppCompatActivity> cancelledActivity) {
    //noinspection ConstantConditions
    AuthorizationRequest request = new AuthorizationRequest.Builder(
        authState.getAuthorizationServiceConfiguration(), clientId,
        ResponseTypeValues.CODE, redirectUri)
        .setScope(authScope)
        .build();
    Intent completedIntent = new Intent(activity, completedActivity)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    Intent cancelledIntent = new Intent(activity, cancelledActivity)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    new AuthorizationService(activity).performAuthorizationRequest(request,
        PendingIntent.getActivity(activity, requestCode, completedIntent,
            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK),
        PendingIntent.getActivity(activity, requestCode, cancelledIntent,
            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
    );
  }

  /**
   * Completes the signin process.
   *
   * @param activity      The current activity.
   * @param response      The response from the authorization request.
   * @param ex            An exception, if authorization did not complete.
   * @param successIntent The {@link edu.cnm.deepdive.tunefull.controller.MainActivity} that
   *                      successful login will redirect to.
   * @param failureIntent The {@code LoginActivity} that unsuccessful login will redirect to.
   */
  public void completeSignIn(AppCompatActivity activity, AuthorizationResponse response,
      AuthorizationException ex, Intent successIntent, Intent failureIntent) {
    authState.update(response, ex);
    setAuthState(authState);
    if (response != null) {
      service.performTokenRequest(
          response.createTokenExchangeRequest(),
          (tokenResponse, authEx) -> {
            authState.update(tokenResponse, authEx);
            setAuthState(authState);
            if (tokenResponse != null) {
              context.startActivity(successIntent);
              // TODO remove logging after development complete
              Log.d(getClass().getSimpleName(), String.valueOf(tokenResponse));
            } else {
              context.startActivity(failureIntent);
            }
          }
      );
    } else {
      context.startActivity(failureIntent);
    }

  }

  /**
   * Refreshes the signin with Spotify.
   *
   * @return The bearer token.
   */
  public Single<String> refresh() {
    return Single.create((emitter) ->
        authState.performActionWithFreshTokens(service,
            (accessToken, idToken, ex) -> {
              if (ex == null) {
                emitter.onSuccess(String.format("Bearer %s", accessToken));
              } else {
                emitter.onError(ex);
              }
            })
    );
  }

  private AuthState getAuthState() {
    try {
      String authState = preferences.getString(AUTH_STATE_KEY, null);
      return AuthState.jsonDeserialize(authState);
    } catch (Throwable e) {
      return null;
    }
  }

  private void setAuthState(AuthState authState) {
    String newAuthState = authState.jsonSerializeString();
    preferences.edit().putString(AUTH_STATE_KEY, newAuthState).commit();
  }

  private static class InstanceHolder {

    @SuppressLint("StaticFieldLeak")
    private static final SpotifySignInService INSTANCE = new SpotifySignInService();
  }

}
