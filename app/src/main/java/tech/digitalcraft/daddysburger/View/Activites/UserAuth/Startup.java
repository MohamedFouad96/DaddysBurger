package tech.digitalcraft.daddysburger.View.Activites.UserAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tech.digitalcraft.daddysburger.R;

public class Startup extends AppCompatActivity {

    Button Login,Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        Login = findViewById(R.id.login);
        Register = findViewById(R.id.register);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Startup.this , Login.class);
                startActivity(i);
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Startup.this , Register.class);
                startActivity(i);
            }
        });
    }
}