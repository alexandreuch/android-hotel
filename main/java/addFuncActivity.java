package com.trab.hotel;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class addFuncActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfunc);
    }
    protected void onStart() {
        super.onStart();
    }
    protected void onResume() {
        super.onResume();
    }
    public class funcAUX {
        private Context context;
        private SQLiteDatabase database;

        public funcAUX(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public void adicionaFunc(String nome, String senha) {
            String sql = "INSERT INTO funcionarios VALUES (NULL,'" + nome + "','" +  senha + "')";
            try {
                database.execSQL(sql);
            } catch (SQLException e) {
            }
        }
    }
    public void addClicker(View view){
        EditText nameText = findViewById(R.id.namePerson);
        EditText senhaText = findViewById(R.id.senhaPerson);
        String user = nameText.getText().toString();
        String senha = senhaText.getText().toString();

        if (!user.equals("") && !senha.equals("")) {
            funcAUX func = new funcAUX(this);
            func.adicionaFunc(user,senha);
            startActivity(new Intent(this, FuncionariosActivity.class));
            finish();
        }
    }

}