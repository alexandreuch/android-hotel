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

public class ClientesActivity extends AppCompatActivity {

    ListView lista;
    ClientesDAO clientesdao;
    ClienteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        clientesdao = new ClientesDAO(this);
        List<Cliente> clientes = clientesdao.construtor();

        lista = (ListView) findViewById(R.id.lvClientes);
        adapter = new ClienteAdapter(clientes, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeClientes(clientes.get(position).getId());
            }
        });
    }

    protected void onStart() { super.onStart();}
    protected void onResume() { super.onResume();}
    public void addPage(View view){
        startActivity(new Intent(this, addClientActivity.class));
        finish();
    }
    public void removeClientes(String id){
        if(clientesdao.verificaUserReserva(id)== false){
            clientesdao.removeCliente(id);
            startActivity(new Intent(this, ClientesActivity.class));
            finish();
        }
    }

    public class Cliente {
        String id;
        String nome;
        String email;
        String telefone;

        Cliente(String id,String nome, String email, String telefone){
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }
        public String getId() { return id; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
        public String getTelefone() { return telefone; }
    }
    public class ClientesDAO {
        private Context context;
        private SQLiteDatabase database;

        public ClientesDAO(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }

        public List<Cliente> construtor() {
            List<Cliente> resultado = new ArrayList<Cliente>();
            Cursor cursor = database.rawQuery("SELECT * FROM clientes", null);
            while (cursor.moveToNext())
                resultado.add(new Cliente(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3)));

            return resultado;
        }

        public void removeCliente(String id){
            try {
                database.execSQL("DELETE FROM clientes WHERE ID = '"+ id + "'");
            }catch(SQLException e){};

        }

        public boolean verificaUserReserva(String user){
            Cursor cursor = database.rawQuery("SELECT * FROM reservas", null);
            String buffer;
            while (cursor.moveToNext()){
                buffer = cursor.getString(4).substring(cursor.getString(3).indexOf(']')+1);
                if(buffer.contains(user)) return true;
            }
            return false;
        }

    }

    public class ClienteAdapter extends BaseAdapter {

        private final List<Cliente> clientes;
        private final Activity act;

        public ClienteAdapter(List<Cliente> clientes, Activity act) { this.clientes = clientes; this.act = act; }
        @Override public int getCount() { return clientes.size(); }
        @Override public Object getItem(int position) { return clientes.get(position); }
        @Override public long getItemId(int position) { return 0; }
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = act.getLayoutInflater().inflate(R.layout.lista_clientes, parent, false);
            Cliente cliente = clientes.get(position);

            TextView id = (TextView) view.findViewById(R.id.cliente_id);
            TextView nome = (TextView) view.findViewById(R.id.cliente_nome);
            TextView email = (TextView) view.findViewById(R.id.cliente_email);
            TextView telefone = (TextView) view.findViewById(R.id.cliente_telefone);

            id.setText(cliente.getId());
            nome.setText(cliente.getNome());
            email.setText(cliente.getEmail());
            telefone.setText(cliente.getTelefone());

            return view; }
    }
}

