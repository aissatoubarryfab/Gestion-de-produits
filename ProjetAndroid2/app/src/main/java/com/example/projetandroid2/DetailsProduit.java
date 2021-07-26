package com.example.projetandroid2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class DetailsProduit extends AppCompatActivity {
    EditText nom, prix,quantite;
    Button mod,supp;
    String idp;
    MySQL m= new MySQL(DetailsProduit.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_produit);
        nom=(EditText) findViewById(R.id.nomProduit);
        prix=(EditText)findViewById(R.id.prixProduit);
        quantite=(EditText)findViewById(R.id.quantiteProduit);
        mod=(Button) findViewById(R.id.mod);
        supp=(Button) findViewById(R.id.supp);

        idp=getIntent().getStringExtra("id");
        Produit p= m.getOneProduct(Integer.parseInt(idp));

        nom.setText(String.valueOf(p.getNom()));
        prix.setText(String.valueOf(p.getPrix()));
        quantite.setText(String.valueOf(p.getQuantiteStock()));
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produit pr=new Produit (Integer.parseInt(idp),nom.getText().toString(),
                        Double.parseDouble(prix.getText().toString()),Integer.parseInt(quantite.getText().toString()));
                m.updateProduct(pr);
                Intent i= new Intent(DetailsProduit.this,ListProduit.class);
                startActivity(i);
                Toast.makeText(getApplicationContext()
                        ,"un produit modifié avec succés",Toast.LENGTH_LONG).show();
            }
        });

        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog().show();


            }
        });



    }

    private Dialog  onCreateDialog() {
        // Création d'un boite de dialogue
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Voulez-vous supprimer le produit ?");
        builder.setCancelable(false);
        builder.setTitle("Confirmation");

        builder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        m.deleteProduct(Integer.parseInt(idp));
                        Intent i= new Intent(DetailsProduit.this,ListProduit.class);
                        startActivity(i);


                    }
                });

        builder.setNegativeButton("NON",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
        return dialog;
    }
}