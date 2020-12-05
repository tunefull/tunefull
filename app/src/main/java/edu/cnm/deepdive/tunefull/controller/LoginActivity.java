package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.tunefull.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signIn.setOnClickListener((v) -> {
            SpotifySignInService service = SpotifySignInService.getInstance();
            service.startSignIn(this, 1000, LoginResponseActivity.class, getClass());
        });
    }
}