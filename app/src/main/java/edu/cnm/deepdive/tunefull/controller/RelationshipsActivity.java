package edu.cnm.deepdive.tunefull.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.controller.RelationshipFragment.RelationshipType;
import edu.cnm.deepdive.tunefull.databinding.ActivityRelationshipsBinding;

/**
 * The relationships activity hosts relationship fragments.
 */
public class RelationshipsActivity extends AppCompatActivity {

    private static final String MAIN_SCREENS_PREF_KEY = "main_index";
    private static final String RELATIONSHIP_PREF_KEY = "relationship_index";

    private ActivityRelationshipsBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRelationshipsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_relationships);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RelationshipType type = RelationshipType.values()[preferences.getInt(RELATIONSHIP_PREF_KEY, 0)];
        binding.backButton.setOnClickListener((v) -> {
            preferences.edit().putInt(MAIN_SCREENS_PREF_KEY, 1).apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

}