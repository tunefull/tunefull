package edu.cnm.deepdive.tunefull;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.tunefull.service.SpotifySignInService;

public class TunefullApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    SpotifySignInService.setContext(this);

  }
}
