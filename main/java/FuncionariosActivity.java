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

public class FuncionariosActivity extends AppCompatActivity {

    ListView lista;
    FuncionarioDAO funcdao;
    FuncionarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        funcdao = new FuncionarioDAO(this);
        List<Funcionario> funcionarios = funcdao.construtor();

        lista = (ListView) findViewById(R.id.lvFuncionarios);
        adapter = new FuncionarioAdapter(funcionarios, this);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeQuartos(funcionarios.get(position).getId());
            }
        });
    }
    protected void onStart() { super.onStart();}
    protected void onResume() { super.onResume();}
    public void clickButton(View view){
        startActivity(new Intent(this, addFuncActivity.class));
        finish();
    }
    public void removeQuartos(String id){
        funcdao.removeFuncionario(id);
        startActivity(new Intent(this, FuncionariosActivity.class));
        finish();
    }

    public class Funcionario {
        String id;
        String nome;
        String senha;

        Funcionario(String id,String nome, String senha){
            this.id = id;
            this.nome = nome;
            this.senha = senha;
        }
        public String getId() { return id; }
        public String getNome() { return nome; }
        public String getSenha() { return senha; }
    }
    public class FuncionarioDAO {
        private Context context;
        private SQLiteDatabase database;

        public FuncionarioDAO(Context context) {
            this.context = context;
            this.database = (new Database(context)).getWritableDatabase();
        }
        public List<Funcionario> construtor() {
            List<Funcionario> resultado = new ArrayList<Funcionario> ();
            Cursor cursor = database.rawQuery("SELECT * FROM funcionarios", null);
            while (cursor.moveToNext())
                resultado.add(new Funcionario(cursor.getString(0), cursor.getString(1),cursor.getString(2)));
            return resultado;
        }
        public void removeFuncionario(String id){
            try {
                database.execSQL("DELETE FROM funcionarios WHERE ID = '"+ id + "'");
            }catch(SQLException e){};

        }
    }
    public class FuncionarioAdapter extends BaseAdapter {

        private final List<Funcionario> funcionarios;
        private final Activity act;

        public FuncionarioAdapter(List<Funcionario> funcionarios, Activity act) { this.funcionarios = funcionarios; this.act = act; }
        @Override public int getCount() { return funcionarios.size(); }
        @Override public Object getItem(int position) { return funcionarios.get(position); }
        @Override public long getItemId(int position) { return 0; }
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = act.getLayoutInflater().inflate(R.layout.lista_funcionarios, parent, false);
            Funcionario funcionario = funcionarios.get(position);

            TextView id = (TextView) view.findViewById(R.id.func_id);
            TextView nome = (TextView) view.findViewById(R.id.func_nome);
            TextView senha = (TextView) view.findViewById(R.id.func_senha);

            id.setText(funcionario.getId());
            nome.setText(funcionario.getNome());
            senha.setText(funcionario.getSenha());

            return view; }
    }
}