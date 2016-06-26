package cs.nizam.shayari;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by nizamcs on 10/4/16.
 */
public class Shayari extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsTrackers.initialize(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
