package cs.nizam.shayari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cs.nizam.shayari.model.Messages;

public class Splash extends AppCompatActivity {

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

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        });

        if (!BuildConfig.DEBUG) {
            upload.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_upload)
    public void uploadToFirebase(){
        Firebase myFirebaseRef = new Firebase("https://shayaridb.firebaseio.com/");
        Firebase messageTable = myFirebaseRef.child(Constats.TABLE_MESSAGE);
        messageTable.setValue(new Messages(1,"Sample message is 'here",1));
    }
}
