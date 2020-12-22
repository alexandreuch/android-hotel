package com.trab.hotel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    protected void onStart() { super.onStart();}
    protected void onResume() { super.onResume();}

    public void entrarClicado(View view) throws InterruptedException {
        EditText userText = findViewById(R.id.userField);
        EditText passText = findViewById(R.id.passField);
        String user = userText.getText().toString();
        String pass = passText.getText().toString();
        MainDAO maindao = new MainDAO(this);

            if (maindao.verificaLogin(user, pass) == true) {
                Toast.makeText(this, "LOGIN EFETUADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MenuActivity.class));
            } else Toast.makeText(this, "ERRO NAS INFORMAÇÕES!", Toast.LENGTH_SHORT).show();
    }

    public class MainDAO {
        private Context context;
        private SQLiteDatabase database;

        public MainDAO(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public boolean verificaLogin(String user, String senha) {
            String sql = "SELECT * FROM funcionarios";
            Cursor cursor = database.rawQuery(sql, null);

            while (cursor.moveToNext()) if(user.equals(cursor.getString(1)) && senha.equals(cursor.getString(2)))return true;
            return false;
            }

        }

}