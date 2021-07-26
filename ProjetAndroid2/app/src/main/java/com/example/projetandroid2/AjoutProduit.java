package com.example.projetandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutProduit extends AppCompatActivity {
    EditText nom,prix,quantite;
    Button btnAjout;
    MySQL  m= new MySQL(AjoutProduit.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);
        nom=findViewById(R.id.nomProduitM);
        prix=findViewById(R.id.prixProduitM);
        quantite=findViewById(R.id.quantiteProduitM);
        btnAjout=findViewById(R.id.btnAddM);

        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produit p= new Produit(nom.getText().toString(),Double.parseDouble(prix.getText().toString()),Integer.parseInt(quantite.getText().toString()));
                m.insertProduct(p);
                Intent i= new Intent(getApplicationContext(),ListProduit.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"un produit ajouté avec succés",Toast.LENGTH_LONG).show();

            }
        });

    }
}