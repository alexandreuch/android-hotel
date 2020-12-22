package com.trab.hotel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class addReservaActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreserva);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    public class ReservaAux {
        private Context context;
        private SQLiteDatabase database;

        public ReservaAux(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public void adicionaReserva(String preco, String es, String tipoid, String lotacaoid) {
            String sql = "INSERT INTO reservas VALUES (NULL ,'R$ " + preco + "','" + es + "','" + tipoid + "','" + lotacaoid + "')";
            try {
                database.execSQL(sql);
            } catch (SQLException e) {
            }
        }

        public String verificaidQuartos(String tipo, int entrada, int saida) {
            Auxiliares auxiliares = new Auxiliares();
            Cursor cursor = database.rawQuery("SELECT * FROM quartos", null);
            List<String> ids = new ArrayList<String>();
            while (cursor.moveToNext())
                if (cursor.getString(1).equals(tipo)) ids.add(cursor.getString(0));

            cursor = database.rawQuery("SELECT * FROM reservas", null);
            String buffer;
            while (cursor.moveToNext()) {
                buffer = cursor.getString(3).substring(0, cursor.getString(3).indexOf('-'));
                if (ids.contains(buffer)) ids.remove(buffer);
            }
            if (ids.size() > 0) return ids.get(0);
            else {
                int parser;
                int bufentrada;
                int bufsaida;
                cursor = database.rawQuery("SELECT * FROM reservas", null);
                while (cursor.moveToNext()) {
                    if (tipo.equals(cursor.getString(3).substring(cursor.getString(3).indexOf('-') + 1))) {
                        buffer = cursor.getString(2);
                        parser = buffer.indexOf('-');
                        bufentrada = auxiliares.diaReserva(buffer.substring(0, parser));
                        bufsaida = auxiliares.diaReserva(buffer.substring(parser + 1));
                        if (bufentrada > saida || bufsaida < entrada)
                            return cursor.getString(3).substring(0, cursor.getString(3).indexOf('-'));
                    }
                }
            }
            return "";
        }

        public boolean existeUsuarios(List<String> users){
            int cont = 0;
            for(int i = 0; i< users.size(); i++){
                Cursor cursor = database.rawQuery("SELECT * FROM clientes", null);
                while (cursor.moveToNext()) if(cursor.getString(0).equals(users.get(i))) cont++;
            }
            if(cont == users.size()) return true;
            else return false;
        }
    }

    public void addClicker(View view) {
        ReservaAux reserva = new ReservaAux(this);
        Auxiliares aux = new Auxiliares();

        EditText ETtipo = findViewById(R.id.tipoQuarto);
        EditText ETnClientes = findViewById(R.id.nClientes);
        EditText ETidClientes = findViewById(R.id.idClientes);
        EditText ETdEntrada = findViewById(R.id.dEntrada);
        EditText ETdSaida = findViewById(R.id.dSaida);

        String tipo = ETtipo.getText().toString().toLowerCase();
        String nClientes = ETnClientes.getText().toString();
        String idClientes = ETidClientes.getText().toString();
        String dEntrada = ETdEntrada.getText().toString();
        String dSaida = ETdSaida.getText().toString();

        List<String> idsPartidos = aux.partirClientes(idClientes);
        if (!tipo.equals("") && !nClientes.equals("") && !idClientes.equals("") && !dEntrada.equals("") && !dSaida.equals("")) {

            int numClientes = Integer.parseInt(nClientes);
            if ((idsPartidos.size() == numClientes) && reserva.existeUsuarios(idsPartidos) == true) {
                int dias = Integer.parseInt(dSaida);
                int preco = aux.verificaRetorna(tipo, numClientes, dias);
                String stringSaida = aux.dataSaida(dEntrada, Integer.parseInt(dSaida));

                if (aux.verificaData(dEntrada) && aux.verificaData(stringSaida) && (dias > 0) && numClientes > 0 && preco != -1) {
                    int numEntrada = aux.diaReserva(dEntrada);
                    int numSaida = aux.diaReserva(stringSaida);
                    String idReserva = reserva.verificaidQuartos(tipo, numEntrada, numSaida);
                    if (!idReserva.equals("")){
                        String es = dEntrada+ "-" + stringSaida;
                        String tipoid = idReserva+"-"+tipo;
                        String lotacao = "[" + nClientes + "]" + aux.transfomarClientes(idsPartidos);
                        reserva.adicionaReserva(""+preco,es,tipoid,lotacao);
                        startActivity(new Intent(this, ReservasActivity.class));
                        finish();
                    }

                }
            }

        }

    }
}