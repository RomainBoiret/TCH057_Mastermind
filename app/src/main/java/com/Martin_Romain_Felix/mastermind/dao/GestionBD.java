package com.Martin_Romain_Felix.mastermind.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionBD extends SQLiteOpenHelper {

    //Données de la BD
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "Mastermind.db";
    private static final String TABLE_PARTIE = "Partie";
    private static final String COLONNE_ID = "id";
    private static final String COLONNE_COURRIEL = "courriel";
    private static final String COLONNE_CODE = "code";
    private static final String COLONNE_NBCOULEURS = "nbCouleurs";
    private static final String COLONNE_RESULTAT = "resultat";
    private static final String COLONNE_NBTENTATIVES = "nbTentatives";

    public GestionBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Création de la base de données "Mastermind.db"
        String query = "CREATE TABLE " + TABLE_PARTIE + "(" + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNE_COURRIEL + " TEXT, " +
                COLONNE_CODE + " TEXT, " +
                COLONNE_NBCOULEURS + " INTEGER, " +
                COLONNE_RESULTAT + " TEXT, " +
                COLONNE_NBTENTATIVES + " INTEGER);";
        db.execSQL(query);
    }

    //Fonction onUpgrade
    //  - Détruit et recrée les tables de la BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // détruire la BD si elle existe
        String query = "DROP TABLE IF EXISTS " + DATABASE_NOM;
        db.execSQL(query);
        // recréer la BD
        onCreate(db);
    }

    public void setPartie(Partie partie) {

        ContentValues valeurs = new ContentValues();
        valeurs.put(COLONNE_COURRIEL, partie.getCourriel());
        valeurs.put(COLONNE_CODE, partie.getCode());
        valeurs.put(COLONNE_NBCOULEURS, partie.getNbCouleurs());
        valeurs.put(COLONNE_RESULTAT, partie.getResultat());
        valeurs.put(COLONNE_NBTENTATIVES, partie.getNbTentatives());



        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PARTIE, null, valeurs);
        db.close();
    }
}

