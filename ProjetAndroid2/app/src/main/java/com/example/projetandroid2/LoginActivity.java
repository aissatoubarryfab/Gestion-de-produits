package com.example.projetandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText emailEditText,passwordEditText;
    Button btnLogin;
    ProgressBar progressBar;
    TextView RegisterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText=findViewById(R.id.email);
        passwordEditText=findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.buttonLogin);
        RegisterView=(TextView)findViewById(R.id.signUpText);
        RegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=String.valueOf(emailEditText.getText());
                password=String.valueOf(passwordEditText.getText());
                if(!email.equals("") && !password.equals("") ) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    // progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] =email;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.56.1/projetandroid/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Connexion Réussi",Toast.LENGTH_SHORT).show();
                                        finish();

                                    }else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"tout les champs sont requis ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}