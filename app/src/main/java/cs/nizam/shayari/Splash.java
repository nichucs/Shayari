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
}
