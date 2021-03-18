package tech.digitalcraft.daddysburger.View.Activites.UserAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.digitalcraft.daddysburger.Controller.Retrofit.RetrofitClientAdapter;
import tech.digitalcraft.daddysburger.Controller.Retrofit.ServiceGenerator;
import tech.digitalcraft.daddysburger.Model.APIs.LoginResponse;
import tech.digitalcraft.daddysburger.Model.Shared;
import tech.digitalcraft.daddysburger.R;
import tech.digitalcraft.daddysburger.View.Activites.MainActivity;
import tech.digitalcraft.daddysburger.View.Activites.Restaurant.RestaurantOrders;

public class Login extends AppCompatActivity {

    private String USERNAME,PASSWORD;

    private EditText Username, Password;
    private Button Login ;
    private ImageButton Back;

    private ProgressDialog mProgressDialog;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        Back = findViewById(R.id.back);
        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري تسجيل الدخول...");
        mProgressDialog.setCancelable(false);

        mSharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog.show();

                USERNAME = Username.getText().toString();
                PASSWORD = Password.getText().toString();


                if (USERNAME.isEmpty() || PASSWORD.isEmpty())
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(Login.this, "تأكد من ادخال اسم المستخدم و كلمة المرور", Toast.LENGTH_SHORT).show();
                }else if (isEmailValid(USERNAME))
                {

                    LoginReq();
                }
                else
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(Login.this, "البريد الالكتروني غير صحيح", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void LoginReq ()
    {

        RetrofitClientAdapter.login(USERNAME,PASSWORD).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mProgressDialog.dismiss();

                switch (response.code())
                {
                    case 200:
                    {

                        mEditor.putString(Shared.get.USERNAME_KEY , response.body().getFullName());
                        mEditor.putString(Shared.get.TOKEN_KEY , response.body().getToken());
                        mEditor.putInt(Shared.get.USER_TYPE , response.body().getUserType());
                        mEditor.putBoolean(Shared.get.CHECK_KEY , true);
                        mEditor.commit();

                        Shared.get.USERNAME = response.body().getFullName();
                        Shared.get.TOKEN = response.body().getToken();

                        if(response.body().getUserType() == 1)
                        {
                            Intent i = new Intent(Login.this , MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Intent i = new Intent(Login.this , RestaurantOrders.class);
                            startActivity(i);
                            finish();
                        }
                 }
                    break;
                    default:
                    {
                        Toast.makeText(Login.this, "كلمة المرور او البريد الالكتروني غير صحيح", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                mProgressDialog.dismiss();

                Log.d("LOGIN_LOG_ERROR", t.getMessage());
                Toast.makeText(Login.this, "لا يوجد اتصال بشبكة الانترنت", Toast.LENGTH_SHORT).show();


            }
        });
    }


    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }
}