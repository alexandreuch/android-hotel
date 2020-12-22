package com.trab.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    protected void onStart() { super.onStart();}
    protected void onResume() { super.onResume();}


    public void reservasClicado(View view) {
        startActivity(new Intent(this, ReservasActivity.class));
    }

    public void clientesClicado(View view) {
        startActivity(new Intent(this, ClientesActivity.class));
    }

    public void quartosClicado(View view) {
        startActivity(new Intent(this, QuartosActivity.class));
    }

    public void funcionariosClicado(View view) {
        startActivity(new Intent(this, FuncionariosActivity.class));
    }

    public void logoutClicado(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}