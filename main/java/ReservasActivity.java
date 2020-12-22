package com.trab.hotel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReservasActivity extends AppCompatActivity {

    ListView lista;
    ReservaDAO reservadao;
    ReservaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        reservadao = new ReservaDAO(this);
        List<Reserva> reservas = reservadao.construtor();

        lista = (ListView) findViewById(R.id.lvReservas);
        adapter = new ReservaAdapter(reservas, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeReserva(reservas.get(position).getId());
            }
        });

    }
    protected void onStart() { super.onStart();}
    protected void onResume() { super.onResume();}

    public void removeReserva(String id){
        reservadao.removeReserva(id);
        startActivity(new Intent(this, ReservasActivity.class));
        finish();
    }

    public void addReserva(View view){
        startActivity(new Intent(this, addReservaActivity.class));
        finish();
    }

    public class Reserva {
        String id;
        String preco;
        String data;
        String tipoid;
        String lotacaoid;

        public Reserva(String id, String preco, String data, String tipoid, String lotacaoid) {
            this.id = id;
            this.preco = preco;
            this.data = data;
            this.tipoid = tipoid;
            this.lotacaoid = lotacaoid;
        }
        public String getId() {
            return id;
        }
        public String getPreco() {
            return preco;
        }
        public String getData() {
            return data;
        }
        public String getTipoid() {
            return tipoid;
        }
        public String getLotacaoid() {
            return lotacaoid;
        }
    }
    public class ReservaDAO {
        private Context context;
        private SQLiteDatabase database;

        public ReservaDAO(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public List<Reserva> construtor() {
            List<Reserva> resultado = new ArrayList<Reserva>();
            Cursor cursor = database.rawQuery("SELECT * FROM reservas", null);
            while (cursor.moveToNext())
                resultado.add(new Reserva(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            return resultado;
        }

        public void removeReserva(String id){
            try {
                database.execSQL("DELETE FROM reservas WHERE ID = '"+ id + "'");
            }catch(SQLException e){};
        }

    }
    public class ReservaAdapter extends BaseAdapter {

        private final List<Reserva> reservas;
        private final Activity act;

        public ReservaAdapter(List<Reserva> reservas, Activity act) { this.reservas = reservas; this.act = act; }
        @Override public int getCount() { return reservas.size(); }
        @Override public Object getItem(int position) { return reservas.get(position); }
        @Override public long getItemId(int position) { return 0; }
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = act.getLayoutInflater().inflate(R.layout.lista_reservas, parent, false);
            Reserva reserva = reservas.get(position);

            TextView id = (TextView) view.findViewById(R.id.reserv_id);
            TextView preco = (TextView) view.findViewById(R.id.reserv_preco);
            TextView es = (TextView) view.findViewById(R.id.reserv_es);
            TextView tipoid = (TextView) view.findViewById(R.id.reserv_tipoid);
            TextView lotacaoid = (TextView) view.findViewById(R.id.reserv_lotacaoid);

            id.setText(reserva.getId());
            preco.setText(reserva.getPreco());
            es.setText(reserva.getData());
            tipoid.setText(reserva.getTipoid());
            lotacaoid.setText(reserva.getLotacaoid());

            return view; }
    }
}