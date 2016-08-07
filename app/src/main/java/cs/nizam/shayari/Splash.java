package cs.nizam.shayari;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.FillableLoaderBuilder;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.clippingtransforms.PlainClippingTransform;
import com.github.jorgecastillo.listener.OnStateChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import cs.nizam.shayari.database.MyDatabase;
import cs.nizam.shayari.listener.OnErrorListener;
import cs.nizam.shayari.listener.OnSuccessListener;
import cs.nizam.shayari.model.Paths;

public class Splash extends AppCompatActivity implements OnStateChangeListener {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.btn_offline)
    Button offline;
    @Bind(R.id.btn_online)
    Button online;
    @Bind(R.id.btn_upload)
    Button upload;
    private FillableLoader fillableLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        FillableLoaderBuilder loaderBuilder = new FillableLoaderBuilder();
        fillableLoader = loaderBuilder
                .parentView((FrameLayout) findViewById(R.id.main_container))
                .layoutParams(params)
                .svgPath(Paths.HEART)
                .originalDimensions(1175, 1100)
                .strokeWidth(4)
                .strokeColor(Color.parseColor("#d50000"))
                .fillColor(Color.parseColor("#ff1744"))
                .strokeDrawingDuration(2000)
//                .fillDuration(5000)
                .clippingTransform(new PlainClippingTransform())
                .build();
        fillableLoader.setOnStateChangeListener(this);
        fillableLoader.postDelayed(new Runnable() {
            @Override
            public void run() {
                fillableLoader.start();
            }
        },500);

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

    @Override
    public void onStateChange(int state) {
        switch(state) {
            case State.STROKE_STARTED:
                break;
            case State.FILL_STARTED:
                break;
            case State.FINISHED:
                offline.setVisibility(View.VISIBLE);
                online.setVisibility(View.VISIBLE);
//                fillableLoader.setVisibility(View.INVISIBLE);
        }
    }
}
