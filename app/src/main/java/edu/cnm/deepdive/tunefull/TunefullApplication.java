package edu.cnm.deepdive.tunefull;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.tunefull.service.GoogleSignInService;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;

/**
 * This class serves as the entry point for the ScaleScroller application.
 */
public class TunefullApplication extends Application {

  /**
   * Initializes Stetho, {@link GoogleSignInService}, and {@link SpotifySignInService}.
   */
  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);
    SpotifySignInService.setContext(this);

  }
}
