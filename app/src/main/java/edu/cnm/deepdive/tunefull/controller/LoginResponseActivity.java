package edu.cnm.deepdive.tunefull.controller;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;

public class LoginResponseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AuthorizationResponse response = AuthorizationResponse.fromIntent(getIntent());
    AuthorizationException ex = AuthorizationException.fromIntent(getIntent());
    Intent successIntent = new Intent(this, MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    Intent failureIntent = new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    setContentView(R.layout.activity_login_response);
    SpotifySignInService.getInstance().completeSignIn(this, response, ex, successIntent, failureIntent);
  }
}