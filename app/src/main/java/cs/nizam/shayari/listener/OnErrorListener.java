package cs.nizam.shayari.listener;

import com.google.firebase.database.DatabaseError;

/**
 * Created by nizamcs on 26/6/16.
 */
public interface OnErrorListener {
    void onError(DatabaseError databaseError);
}
