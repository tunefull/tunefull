package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.service.UserRepository;
import edu.cnm.deepdive.tunefull.adapter.SectionsPagerAdapter;
import edu.cnm.deepdive.tunefull.viewmodel.TrackViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

  private UserRepository userRepository; // FIXME
  private TabLayout tabs;
  private static final int[] TAB_ICONS = new int[]{R.drawable.ic_earth, R.drawable.ic_tunefull_logo_text, R.drawable.ic_person_24};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
        getSupportFragmentManager());
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setAdapter(sectionsPagerAdapter);
    tabs = findViewById(R.id.tabs);
    tabs.setupWithViewPager(viewPager);
    setUpTabs();

    // FIXME (this is just temporary to verify roundtrip)
    userRepository = new UserRepository(this);
    userRepository.getProfileFromServer()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (user) -> Toast.makeText(this, user.getUsername(), Toast.LENGTH_LONG).show(),
            (throwable) -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show()
        );
    // FIXME (again, temporary to verify roundtrip with spotify)
    TrackViewModel viewModel = new ViewModelProvider(this).get(TrackViewModel.class);
    viewModel.getTracks().observe(this, (tracks) -> {
      Log.d(getClass().getSimpleName(), tracks.toString());
    });
  }

  private void setUpTabs() {
    for (int i = 0; i < TAB_ICONS.length; i++) {
      ImageView imageView = new ImageView(getApplicationContext());
      imageView.setImageResource(TAB_ICONS[i]);
      tabs.getTabAt(i).setCustomView(imageView);
    }
  }

  public void testSwitch() {
    ViewPager viewPager = findViewById(R.id.view_pager);
    viewPager.setCurrentItem(2, true);
  }
}