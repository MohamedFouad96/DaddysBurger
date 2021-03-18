package tech.digitalcraft.daddysburger.View.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Controller.Retrofit.ServiceGenerator;
import tech.digitalcraft.daddysburger.Controller.SqlLite.DatabaseHelper;
import tech.digitalcraft.daddysburger.Model.APIs.LoginResponse;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.Restaurant.RestaurantOrders;
import tech.digitalcraft.daddysburger.View.Activites.UserAuth.Login;
import tech.digitalcraft.daddysburger.View.Activites.UserAuth.Startup;

public class Splash extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    String androidId;

    private boolean CHECK;
    private int USER_TYPE;
    private Dialog dialog;
    private EditText editText;
    private Button submit;

    private String KEY;


    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        CHECK = mSharedPreferences.getBoolean(Shared.get.CHECK_KEY,false);

        androidId =  Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_check);
        dialog.setCancelable(false);

        editText = dialog.findViewById(R.id.edittext);
        submit  = dialog.findViewById(R.id.submit);


        databaseHelper = new DatabaseHelper(this);


        if (mSharedPreferences.getBoolean(Shared.get.ONE_TIME_CHECKER_KEY,false))
        {
            checkAuth();
        }
        else
        dialog.show();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KEY = editText.getText().toString();

                if(!KEY.isEmpty())
                {
                    dialog.dismiss();
                    activeOneTime();

                }
                else
                    Toast.makeText(Splash.this, "Enter Email Address.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void checkAuth()
    {
        Shared.get.ACTIVATION_TOKEN = mSharedPreferences.getString(Shared.get.ACTIVATION_TOKEN_KEY , "");

        if (CHECK)
        {

            USER_TYPE = mSharedPreferences.getInt(Shared.get.USER_TYPE,1);

            Shared.get.TOKEN = mSharedPreferences.getString(Shared.get.TOKEN_KEY , "");


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(USER_TYPE == 1)
                    {
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(Splash.this, RestaurantOrders.class);
                        startActivity(i);
                        finish();
                    }

                }
            }, 3000);

        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, Startup.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);

        }

    }

    public void activeOneTime()
    {

        ServiceGenerator.changeBaseUrl("http://bsnextdeveloping-001-site2.itempurl.com");

        RetrofitClientAdapter.activation(KEY , androidId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.code())
                {
                    case 200:
                    {
                        Shared.get.ACTIVATION_TOKEN = response.body().getToken();
                        mEditor.putString(Shared.get.ACTIVATION_TOKEN_KEY , response.body().getToken());
                        mEditor.putBoolean(Shared.get.ONE_TIME_CHECKER_KEY , true);
                        mEditor.commit();
                        checkAuth();
                    }
                    break;
                    default:
                    {
                        Toast.makeText(Splash.this, "هناك مشكلة بالرمز الخاص بك", Toast.LENGTH_SHORT).show();
                        dialog.show();

                    }
                    break;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dialog.show();


                Log.d("LOGIN_LOG_ERROR", t.getMessage());
                Toast.makeText(Splash.this, "لا يوجد اتصال بشبكة الانترنت", Toast.LENGTH_SHORT).show();
            }
        });

    }
}