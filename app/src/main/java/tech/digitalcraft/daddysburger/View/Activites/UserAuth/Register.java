package tech.digitalcraft.daddysburger.View.Activites.UserAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import tech.digitalcraft.daddysburger.Controller.Validator;
import tech.digitalcraft.daddysburger.R;

public class Register extends AppCompatActivity {

    private String USERNAME,PASSWORD,EMAIL,PHONE;

    private EditText Username, Password,Phone,Email;
    private Button Register;
    private ImageButton Back;

    private ProgressDialog mProgressDialog;

    private Validator mValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        Register = findViewById(R.id.register);
        Back = findViewById(R.id.back);

        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري إنشاء الحساب...");
        mProgressDialog.setCancelable(false);

        mValidator = new Validator();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog.show();

                USERNAME = Username.getText().toString();
                EMAIL = Email.getText().toString();
                PHONE = Phone.getText().toString();
                PASSWORD = Password.getText().toString();


                if (USERNAME.isEmpty() || EMAIL.isEmpty() || PHONE.isEmpty() || PASSWORD.isEmpty())
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(Register.this, "تأكد من ادخال جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                }
                else if (checkValidation(USERNAME,EMAIL,PASSWORD,PASSWORD))
                {
                    RegisterReq();
                } else
                    mProgressDialog.dismiss();

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void RegisterReq()
    {
        RetrofitClientAdapter.register(USERNAME , EMAIL , PHONE , PASSWORD).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                mProgressDialog.dismiss();
                switch (response.code())
                {

                    case 200:
                    {
                        Toast.makeText(Register.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    break;
                    default:
                    {


                        System.out.println("Response: "+response.body().toString());
                    }
                    break;
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mProgressDialog.dismiss();

                Log.d("LOGIN_LOG_ERROR", t.getMessage());
                Toast.makeText(Register.this, "لا يوجد اتصال بشبكة الانترنت", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public boolean checkValidation(String username,String email , String password ,String confirmPassowrd)
    {

        if (username.contains(" ") || email.contains(" ")) {
            mProgressDialog.dismiss();
            Toast.makeText(Register.this, "يجب ان لا يحتوي اسم المستخدم و البريد الالكتروني علي اي مسافات", Toast.LENGTH_SHORT).show();

        } else {

            if (mValidator.validateUsername(username)) {

                if (mValidator.validatePassword(password)) {

                    if (isEmailValid(email)) {



                        return true;




                    } else {

                        Toast.makeText(Register.this, "البريد الالكتروني غير صحيح", Toast.LENGTH_SHORT).show();
                        return false;

                    }

                } else {

                    Toast.makeText(Register.this, "كلمة المرور غير قوية تأكد من وجود حروف كبيرة و رمز و ان لا تقل عن 8 حروف", Toast.LENGTH_SHORT).show();
                    return false;

                }

            } else {
                Toast.makeText(Register.this, "تأكد من ان اسم المستخدم يحتوي فقط علي: A-Z, a-z, ., _, 8-20", Toast.LENGTH_SHORT).show();

                return false;

            }



        }
        return false;

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