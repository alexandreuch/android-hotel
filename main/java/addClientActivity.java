package com.trab.hotel;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class addClientActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcliente);
    }
    protected void onStart() {
        super.onStart();
    }
    protected void onResume() {
        super.onResume();
    }
    public class ClienteAUX {
        private Context context;
        private SQLiteDatabase database;

        public ClienteAUX(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public void adicionaCliente(String nome, String email, String telefone) {
            String sql = "INSERT INTO clientes VALUES (NULL,'" + nome + "','" + email + "','" + telefone + "')";
            try {
                database.execSQL(sql);
            } catch (SQLException e) {
            }
        }
    }
    public void addClicker(View view){
        EditText nameText = findViewById(R.id.namePerson);
        EditText emailText = findViewById(R.id.senhaPerson);
        EditText telText = findViewById(R.id.telPerson);
        String user = nameText.getText().toString();
        String email = emailText.getText().toString();
        String tel = telText.getText().toString();

        if (!user.equals("") && !email.equals("") && !tel.equals("")) {
            ClienteAUX cliente = new ClienteAUX(this);
            cliente.adicionaCliente(user,email,tel);
            startActivity(new Intent(this, ClientesActivity.class));
            finish();
        }
    }
}