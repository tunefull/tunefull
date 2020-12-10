package edu.cnm.deepdive.tunefull.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import edu.cnm.deepdive.tunefull.R;
import edu.cnm.deepdive.tunefull.controller.ClipFeedFragment;
import edu.cnm.deepdive.tunefull.controller.LikedSongsFragment;
import edu.cnm.deepdive.tunefull.controller.ProfileFragment;

/**
 * A {@code FragmentPagerAdapter} that returns a fragment corresponding to one of the tabs.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

  @StringRes
  private static final int[] TAB_TITLES = new int[]{R.string.discovery, R.string.feed,
      R.string.my_clips, R.string.my_liked_songs, R.string.profile};
  private final Context context;

  /**
   * The constructor initializes the context and calls the superclass constructor.
   *
   * @param context The application context.
   * @param fm      A parameter passed to the superclass constructor.
   */
  public SectionsPagerAdapter(Context context, FragmentManager fm) {
    super(fm);
    this.context = context;
  }

  @Override
  public Fragment getItem(int position) {
    if (position < 3) {
      return ClipFeedFragment.newInstance(position);
    } else if (position == 3) {
      return new LikedSongsFragment();
    } else {
      return ProfileFragment.newInstance(position);
    }
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return context.getResources().getString(TAB_TITLES[position]);
  }

  @Override
  public int getCount() {
    return 5;
  }
}