package com.example.projetandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
 Button btnAjout, btnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAjout=(Button)findViewById(R.id.buttonAj);
        btnList=(Button)findViewById(R.id.buttonList);
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x= new Intent(getApplicationContext(),AjoutProduit.class);
                startActivity(x);

            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x= new Intent(getApplicationContext(),ListProduit.class);
                startActivity(x);

            }
        });

    }
}