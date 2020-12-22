package com.trab.hotel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 30;
    public static final String DATABASE_NAME = "Hotel.db";

    private static final String SQL_CREATE_USERS = "CREATE TABLE funcionarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, password TEXT)";
    private static final String SQL_POPULATE_USERS = "INSERT INTO funcionarios VALUES " +
            "(NULL, 'admin', '1236')";
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS funcionarios";

    private static final String SQL_CREATE_QUARTOS = "CREATE TABLE quartos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
    private static final String SQL_POPULATE_QUARTOS = "INSERT INTO quartos VALUES " +
            "(NULL, 'single')";
    private static final String SQL_DELETE_QUARTOS = "DROP TABLE IF EXISTS quartos";

    private static final String SQL_CREATE_CLIENTES = "CREATE TABLE clientes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, email TEXT, telefone TEXT UNIQUE)";
    private static final String SQL_POPULATE_CLIENTES = "INSERT INTO clientes VALUES " +
            "(NULL, 'Alexandre','qalexandrep','982437102')";
    private static final String SQL_DELETE_CLIENTES = "DROP TABLE IF EXISTS clientes";

    private static final String SQL_CREATE_RESERVAS = "CREATE TABLE reservas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, preco TEXT , es TEXT, tipoid TEXT , lotacaoid TEXT)";
    private static final String SQL_POPULATE_RESERVAS = "INSERT INTO reservas VALUES " +
            "(NULL, 'R$50,00','10/10/10-11/10/10','1-single','[1]1')";
    private static final String SQL_DELETE_RESERVAS = "DROP TABLE IF EXISTS reservas";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_POPULATE_USERS);

        db.execSQL(SQL_CREATE_QUARTOS);
        db.execSQL(SQL_POPULATE_QUARTOS);

        db.execSQL(SQL_CREATE_CLIENTES);
        db.execSQL(SQL_POPULATE_CLIENTES);

        db.execSQL(SQL_CREATE_RESERVAS);
        db.execSQL(SQL_POPULATE_RESERVAS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_QUARTOS);
        db.execSQL(SQL_DELETE_CLIENTES);
        db.execSQL(SQL_DELETE_RESERVAS);
        onCreate(db);
    }

}