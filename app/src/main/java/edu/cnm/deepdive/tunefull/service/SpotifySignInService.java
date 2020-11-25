package edu.cnm.deepdive.tunefull.service;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.tunefull.R;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

public class SpotifySignInService {

  @SuppressLint("StaticFieldLeak")
  private static Context context;
  private final AuthState authState;
 // private final AuthorizationService service;
  private final String clientId;
  private final Uri redirectUri;
  private final String authScope;

  private SpotifySignInService() {
    // TODO Look for auth state in shared preferences
    AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
        Uri.parse(context.getString(R.string.authorization_endpoint_uri)),
        Uri.parse(context.getString(R.string.token_endpoint_uri)));
    authState = new AuthState(serviceConfig);
    // TODO update shared preferences
    clientId = context.getString(R.string.client_id);
    redirectUri = Uri.parse(context.getString(R.string.redirect_uri));
    authScope = context.getString(R.string.authorization_scope);
  }

  public static void setContext(Context context) {
    SpotifySignInService.context = context;
  }

  public static SpotifySignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public void startSignIn(AppCompatActivity activity, int requestCode,
      Class<? extends AppCompatActivity> completedActivity,
      Class<? extends AppCompatActivity> cancelledActivity) {
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

  public void completeSignIn(AppCompatActivity activity, AuthorizationResponse response, AuthorizationException ex,
      Intent successIntent, Intent failureIntent) {
    authState.update(response, ex);
    // TODO update shared preferences
    if (response != null) {
      new AuthorizationService(activity).performTokenRequest(
          response.createTokenExchangeRequest(),
          (tokenResponse, authEx) -> {
            authState.update(tokenResponse, authEx);
            // TODO update shared preferences
            if (tokenResponse != null) {
              context.startActivity(successIntent);
            } else {
              context.startActivity(failureIntent);
            }
          }
      );
    } else {
      context.startActivity(failureIntent);
    }

  }

  // TODO Create refresh token method that returns a bearer token
  // TODO Create a method to check if we're already logged in (combine with refresh)

  private static class InstanceHolder {

    @SuppressLint("StaticFieldLeak")
    private static final SpotifySignInService INSTANCE = new SpotifySignInService();
  }

}
