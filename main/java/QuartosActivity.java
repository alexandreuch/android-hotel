package com.trab.hotel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuartosActivity extends AppCompatActivity {

    ListView lista;
    QuartosDAO quartodao;
    QuartoAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quartos);

        quartodao = new QuartosDAO(this);
        List<Quarto> quartos = quartodao.construtor();

        lista = (ListView) findViewById(R.id.lvQuartos);
        adapter = new QuartoAdapter(quartos, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                atualizaQuartos(quartos.get(position).getId(),"");
            }
        });

    }

    protected void onStart() {
        super.onStart();
    }
    protected void onResume() {
        super.onResume();
    }

    private void refresh(){
        startActivity(new Intent(this, QuartosActivity.class));
        finish();
    }

    public void atualizaQuartos(String id,String tipo){
        quartodao = new QuartosDAO(this);
        if(tipo.equals("")){
            if(quartodao.verificaQuarto(id) == false) {
                quartodao.removeQuarto(id);
                refresh();
            }
        }else{
            if(id.equals("1")) quartodao.adicionaQuarto(0);
            else if(id.equals("2")) quartodao.adicionaQuarto(1);
            else quartodao.adicionaQuarto(2);
            refresh();
        }
    }

    public void addSingle(View view) {
        atualizaQuartos("0","single");
    }
    public void addDouble(View view) {
        atualizaQuartos("1","double");
    }
    public void addSuite(View view) {
        atualizaQuartos("2","suite");
    }

    public class Quarto {
        String id;
        String tipo;

        Quarto(String id, String tipo){
            this.id = id;
            this.tipo = tipo;
        }

        public String getId() {
            return id;
        }

        public String getTipo() {
            return tipo;
        }
    }

    public class QuartosDAO {
        private Context context;
        private SQLiteDatabase database;

        public QuartosDAO(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public List<Quarto> construtor() {
            List<Quarto> resultado = new ArrayList<Quarto>();
            Cursor cursor = database.rawQuery("SELECT * FROM quartos", null);
            while (cursor.moveToNext())
                resultado.add(new Quarto(cursor.getString(0), cursor.getString(1)));

            return resultado;
        }

        public void adicionaQuarto(int flag) {
            String tipo;
            if(flag == 0) tipo = "Single";
            else if(flag == 1) tipo = "Double";
            else tipo = "Suite";

            String sql = "INSERT INTO quartos VALUES (NULL,'" + tipo + "')";
            try {
                database.execSQL(sql);
            }catch(SQLException e){};
        }

        public void removeQuarto(String id){
            try {
                database.execSQL("DELETE FROM quartos WHERE ID = '"+ id + "'");
            }catch(SQLException e){};

        }

        public boolean verificaQuarto(String quarto){
            Cursor cursor = database.rawQuery("SELECT * FROM reservas", null);
            String buffer;
            while (cursor.moveToNext()){
                buffer = cursor.getString(3).substring(0,cursor.getString(3).indexOf('-'));
                if(buffer.equals(quarto)) return true;
            }
            return false;
        }

    }
        public class QuartoAdapter extends BaseAdapter {

        private final List<Quarto> quartos;
        private final Activity act;

        public QuartoAdapter(List<Quarto> quartos, Activity act) { this.quartos = quartos; this.act = act; }

        @Override public int getCount() { return quartos.size(); }

        @Override public Object getItem(int position) { return quartos.get(position); }

        @Override public long getItemId(int position) { return 0; }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = act.getLayoutInflater().inflate(R.layout.lista_quartos, parent, false);
            Quarto quarto = quartos.get(position);

            TextView id = (TextView) view.findViewById(R.id.quarto_id);
            TextView tipo = (TextView) view.findViewById(R.id.quarto_tipo);

            id.setText(quarto.getId());
            tipo.setText(quarto.getTipo());

            return view; }
    }
}