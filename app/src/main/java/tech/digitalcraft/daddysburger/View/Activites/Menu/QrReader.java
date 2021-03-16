package tech.digitalcraft.daddysburger.View.Activites.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.gson.Gson;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import tech.digitalcraft.daddysburger.Controller.Helpers;
import tech.digitalcraft.daddysburger.Model.APIs.Meal;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;

public class QrReader extends AppCompatActivity {

    private SurfaceView mSurfaceView;
    private QREader mQrReader;
    private Vibrator mVibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);

        mSurfaceView = findViewById(R.id.camera_view);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        startQrReader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Init and Start with SurfaceView
        // -------------------------------

        mQrReader.initAndStart(mSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Cleanup in onPause()
        // --------------------


        mQrReader.releaseAndCleanup();


    }


    public void startQrReader()
    {
        mQrReader = new QREader.Builder(this, mSurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);
                System.out.println("Value : " + data);
                mVibrator.vibrate(40);

                Gson g = new Gson();
                try {
                    Meal meal = g.fromJson(data , Meal.class);
                    Shared.get.mMeal = meal;
                    Intent i = new Intent(QrReader.this , MealDetails.class);
                    i.putExtra("img",   Helpers.getImageIdByString(meal.getImageUrl()));
                    startActivity(i);
                    finish();
                }catch (Exception err)
                {
                    Toast.makeText(QrReader.this, "يوجد مشكلة حاول مرة اخري", Toast.LENGTH_SHORT).show();
                    finish();
                }



            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mSurfaceView.getMeasuredHeight())
                .width(mSurfaceView.getMeasuredWidth())
                .build();

    }
}