package cs.nizam.shayari;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import cs.nizam.shayari.database.MyDatabase;
import cs.nizam.shayari.listener.OnErrorListener;
import cs.nizam.shayari.listener.OnSuccessListener;

public class Splash extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
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
                MyDatabase.getInstance().refreshData(new OnSuccessListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Snackbar.make(coordinatorLayout, "Update success", Snackbar.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                offline.performClick();
                            }
                        }, 2000);
                    }
                }, new OnErrorListener() {
                    @Override
                    public void onError(DatabaseError databaseError) {
                        Snackbar.make(coordinatorLayout, "Update failed", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if (!BuildConfig.DEBUG) {
            upload.setVisibility(View.GONE);
        }

    }
}
