package com.example.projetandroid2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {
   TextInputEditText nomEditText, prenomEditText,emailEditText,passwordEditText;
    Button btnRegister;
    TextView login;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
          nomEditText=findViewById(R.id.NomEditText);
          prenomEditText=findViewById(R.id.PrenomEditText);
          emailEditText=findViewById(R.id.EmailEditText);
          passwordEditText=findViewById(R.id.passwordEditText);
          btnRegister=(Button)findViewById(R.id.Registerbtn);
          progressBar=(ProgressBar)findViewById(R.id.progress);
          login=(TextView)findViewById(R.id.loginText);
          login.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                  startActivity(intent);
                  finish();
              }
          });
          btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String nom,prenom,email,password;
                 nom=String.valueOf(nomEditText.getText());
                 prenom=String.valueOf(prenomEditText.getText());
                 email=String.valueOf(emailEditText.getText());
                 password=String.valueOf(passwordEditText.getText());
                 if(!nom.equals("")&& !prenom.equals("") && !email.equals("") && !password.equals("") ) {
                     //Start ProgressBar first (Set visibility VISIBLE)
                     progressBar.setVisibility(View.VISIBLE);
                     Handler handler = new Handler(Looper.getMainLooper());
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             //Starting Write and Read data with URL
                             //Creating array for parameters
                             String[] field = new String[4];
                             field[0] = "nom";
                             field[1] = "prenom";
                             field[2] = "email";
                             field[3] = "password";
                             //Creating array for data
                             String[] data = new String[4];
                             data[0] = nom;
                             data[1] = prenom;
                             data[2] =email;
                             data[3] = password;
                             PutData putData = new PutData("http://192.168.56.1/projetandroid/signup.php", "POST", field, data);
                             if (putData.startPut()) {
                                 if (putData.onComplete()) {
                                     String result = putData.getResult();
                                   if(result.equals("Sign Up Success")){
                                       createNotify();
                                       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                       startActivity(intent);
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
    //Méthode qui crée la notification
    private void createNotify(){
        //Intent intent = new Intent(this, ActivityNotification.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //ActivityNotification sera donc le nom de notre seconde Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, LoginActivity.class), 0);

        String channelId = "0";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_user)
                        .setContentTitle("Notification!")
                        .setContentText("Inscription Réussi ")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        // Pour faire apparaître la notification, appele de  NotificationManagerCompat.notify(),
        // en lui transmettant un identifiant unique pour la notification et le résultat de NotificationCompat.Builder.build().
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}