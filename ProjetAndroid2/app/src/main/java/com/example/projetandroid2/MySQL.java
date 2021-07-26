package com.example.projetandroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQL extends SQLiteOpenHelper {
    public MySQL(@Nullable Context context) {
        super(context,"ProjetAndroid" ,null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("CREATE TABLE produit(_id INTEGER PRIMARY KEY,nom TEXT, prix REAL,quantiteStock INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //valable ou cas ou le user instale une autre application et si une colonne a été ajouté, il va falloir mettre à jour la ase
        db.execSQL("DROP TABLE IF EXISTS produit");
        //recreer la able à nouveau
        onCreate(db);

    }
    public void insertProduct(Produit p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        // le remplir
        cv.put("nom",p.getNom());
        cv.put("prix",p.getPrix());
        cv.put("quantiteStock",p.getQuantiteStock());

        db.insert("produit",null,cv);
        //fermer la connexion
        db.close();
    }
    public void updateProduct(Produit p){
        //creer une instance de la classe SQLLiteDataBase en ecriture
        SQLiteDatabase db=this.getWritableDatabase();
        // on lutilise  pour pouvoir inserer les misse à jour dans le conten Value
        ContentValues cv= new ContentValues();
        cv.put("nom",p.getNom());
        cv.put("prix",p.getPrix());
        cv.put("quantiteStock",p.getQuantiteStock());
        db.update("produit", cv,"_id=?",new String[]{String.valueOf(p.getId())});//il faut convertir le int en string
        db.close();

    }

    public void deleteProduct(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("produit","_id=?",new String[]{String.valueOf(id)});//il faut convertir le int en string
        db.close();

    }

    public Cursor getAllProduct(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor  c=db.rawQuery("SELECT * FROM produit", null);
        return c;
    }

    public Produit getOneProduct(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.query("produit",new String[]{"_id","nom","prix","quantiteStock"},"_id=?",
                new String[]{String.valueOf(id)},null,null,null);
        c.moveToFirst();// pour que le cursor contient un seul enregistrement
        Produit p= new Produit(c.getInt(0),c.getString(1),c.getDouble(2),c.getInt(3));
        return p;

    }


}
