package edu.cnm.deepdive.tunefull.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import edu.cnm.deepdive.tunefull.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;


  @SuppressLint("CheckResult")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SpotifySignInService service = SpotifySignInService.getInstance();
    //noinspection ResultOfMethodCallIgnored
    service.refresh()
        .subscribe((token) -> {
              Intent intent = new Intent(this, MainActivity.class)
                  .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
            },
            (throwable) -> {
              binding = ActivityLoginBinding.inflate(getLayoutInflater());
              setContentView(binding.getRoot());
              binding.signIn.setOnClickListener((v) -> {
                service.startSignIn(this, 1000, LoginResponseActivity.class, getClass());
              });
            }
        );
  // TODO check if the spotify app has been installed someplace in this activity
    if (!SpotifyAppRemote.isSpotifyInstalled(this)) {
      //do something to tell user that they must install and sign into spotify first
    }
  }
}