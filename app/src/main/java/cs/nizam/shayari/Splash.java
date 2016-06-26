package cs.nizam.shayari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cs.nizam.shayari.model.Category;
import cs.nizam.shayari.model.Messages;
import cs.nizam.shayari.utils.FileUtils;

public class Splash extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Bind(R.id.btn_offline)
    Button offline;
    @Bind(R.id.btn_online)
    Button online;
    @Bind(R.id.btn_upload)
    Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(Constats.TABLE_CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Nzm","data change:"+dataSnapshot.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mDatabase.child(Constats.TABLE_MESSAGE).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        });

        if (!BuildConfig.DEBUG) {
            upload.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_upload)
    public void uploadToFirebase(){
        DatabaseReference  myFirebaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference categoryTable = myFirebaseRef.child(Constats.TABLE_CATEGORY);
        categoryTable.child("1").setValue(new Category(1,"Love Messages"));
        categoryTable.child("2").setValue(new Category(2,"Valentine's Day SMS"));
        categoryTable.child("3").setValue(new Category(3,"Romantic Messages"));
        categoryTable.child("4").setValue(new Category(4,"Love Quotes"));
        categoryTable.child("5").setValue(new Category(5,"I Love You Quotes"));
        categoryTable.child("6").setValue(new Category(6,"Cute Love SMS"));
        categoryTable.child("7").setValue(new Category(7,"Love Status For FB"));
        categoryTable.child("8").setValue(new Category(8,"Love SMS in Hindi"));

        DatabaseReference messageTable = myFirebaseRef.child(Constats.TABLE_MESSAGE);
        messageTable.child("1").setValue(new Messages(1, "Sample message is 'here", 1));
        messageTable.child("2").setValue(new Messages(2,"Second message is 'here",1));
        try {
            JSONObject data = new JSONObject(FileUtils.loadJSONFromAsset(Splash.this, "messages.json"));
            JSONArray rows = data.getJSONArray("rows");
            for (int i = 0; i < rows.length(); i++) {
                JSONArray msg = rows.getJSONArray(i);
                messageTable.child(""+i).setValue(new Messages(i, msg.getString(0), Integer.parseInt(msg.getString(1))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
