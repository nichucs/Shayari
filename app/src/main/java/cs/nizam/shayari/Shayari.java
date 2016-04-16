package cs.nizam.shayari;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nizamcs on 10/4/16.
 */
public class Shayari extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsTrackers.initialize(this);
        Firebase.setAndroidContext(this);
    }
}
