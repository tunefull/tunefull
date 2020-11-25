package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.service.UserRepository;
import edu.cnm.deepdive.tunefull.ui.main.SectionsPagerAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

  private UserRepository userRepository; // FIXME

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
        getSupportFragmentManager());
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = findViewById(R.id.tabs);
    tabs.setupWithViewPager(viewPager);
    FloatingActionButton fab = findViewById(R.id.fab);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    // FIXME (this is just temporary to verify roundtrip)
    userRepository = new UserRepository(this);
    userRepository.getProfileFromServer()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (user) -> Toast.makeText(this, user.getUsername(), Toast.LENGTH_LONG).show(),
            (throwable) -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show()
        );
  }
}