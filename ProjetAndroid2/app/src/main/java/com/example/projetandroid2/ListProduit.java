package com.example.projetandroid2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListProduit extends AppCompatActivity {
  ListView ls;
  MySQL m= new MySQL(ListProduit.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produit);
        ls=findViewById(R.id.ListView_Container);
        Cursor c= m.getAllProduct();
        //il va falloir utiliser cette adapter pour le Cursor concerné
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(ListProduit.this, R.layout.product_item,c,
                new String[]{c.getColumnName(0),c.getColumnName(1),c.getColumnName(2),c.getColumnName(3)},
                new int[]{R.id.id,R.id.nom,R.id.prix,R.id.quantite},1);
        //affecter l'adaptateur a notre liste
         ls.setAdapter(adapter);

         ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 //il faut d'abort récuperer l'id de 'item selectionné
                 TextView t= view.findViewById(R.id.id);
                 Intent x= new Intent(ListProduit.this,DetailsProduit.class);
                 x.putExtra("id",t.getText().toString());
                 startActivity(x);
             }
         });




    }
}