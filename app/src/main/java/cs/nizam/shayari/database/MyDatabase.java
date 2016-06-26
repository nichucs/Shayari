package cs.nizam.shayari.database;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs.nizam.shayari.Constats;
import cs.nizam.shayari.listener.OnErrorListener;
import cs.nizam.shayari.listener.OnSuccessListener;

/**
 * Created by nizamcs on 26/6/16.
 */
public class MyDatabase {

    private static MyDatabase database;
    private DatabaseReference databaseReference;

    public static MyDatabase getInstance(){
        if (database == null) database = new MyDatabase();
        return database;
    }

    public MyDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void refreshData(final OnSuccessListener onSuccessListener, final OnErrorListener onErrorListener) {
        singleQuery(databaseReference, onSuccessListener, onErrorListener);
    }

    public void getCategories(OnSuccessListener onSuccessListener, OnErrorListener onErrorListener){
        singleQuery(databaseReference.child(Constats.TABLE_CATEGORY), onSuccessListener, onErrorListener);
    }

    public void getMessages(OnSuccessListener onSuccessListener, OnErrorListener onErrorListener){
        singleQuery(databaseReference.child(Constats.TABLE_MESSAGE), onSuccessListener, onErrorListener);
    }

    private void singleQuery(DatabaseReference databaseReference, final OnSuccessListener onSuccessListener, final OnErrorListener onErrorListener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onSuccessListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onErrorListener.onError(databaseError);
            }
        });
    }

}
