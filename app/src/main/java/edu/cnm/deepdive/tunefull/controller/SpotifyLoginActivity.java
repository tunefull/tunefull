package edu.cnm.deepdive.tunefull.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.tunefull.databinding.ActivitySpotifyLoginBinding;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;

public class SpotifyLoginActivity extends AppCompatActivity {

    private ActivitySpotifyLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpotifyLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signIn.setOnClickListener((v) -> {
            SpotifySignInService service = SpotifySignInService.getInstance();
            service.startSignIn(this, 1000, LoginResponseActivity.class, getClass());
        });
    }
}