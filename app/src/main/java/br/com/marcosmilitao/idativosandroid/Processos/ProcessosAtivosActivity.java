package br.com.marcosmilitao.idativosandroid.Processos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioEquipamento;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBTarefasEqReadinessTable;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class ProcessosAtivosActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    private TextView lbl_Novo_Processo;
    private AutoCompleteTextView txt_Buscar;
    private ImageButton btn_Buscar;
    private ListView list_Processos_ativo;
    private ArrayAdapter<String> listaAtivos1_Equipment_Type1;
    private ArrayAdapter<String> listaAtivos1_Trace_Number1;
    private ArrayAdapter<String>  listaAtivos1_Categoria_Equipamento1;

    private ArrayAdapter<String>  adapterAtivos;
    private ArrayList<String> arrayAtivos;
    private ArrayList<CadastroEquipamentos> arrayCadastroEquipamentos;

    private Idativos02Data idativos02Data;
    private SQLiteDatabase connect;
    private Cursor listaAtivos1;
    private Query_InventarioEquipamento query_InventarioEquipamento;
    private Query_UPWEBTarefasEqReadinessTable query_UPWEBTarefasEqReadinessTable;
    Sync sync;
    SQLiteDatabase db;
    Button btn_p_ativo_pesquisar;
    EditText edt_p_ativo_pesquisar;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processos_ativos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProcessAtivos);
        setSupportActionBar(toolbar);

        arrayCadastroEquipamentos = new ArrayList<CadastroEquipamentos>();
        arrayAtivos = new ArrayList<String>();
        adapterAtivos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayAtivos);
        list_Processos_ativo = (ListView) findViewById(R.id.list_Processos_ativo);
        list_Processos_ativo.setOnItemClickListener(this);
        list_Processos_ativo.setAdapter(adapterAtivos);

        btn_p_ativo_pesquisar = (Button) findViewById(R.id.btn_p_ativo_pesquisar);
        edt_p_ativo_pesquisar = (EditText) findViewById(R.id.edt_p_ativo_pesquisar);

        ListaAtivos();

        final FloatingActionButton fb_Adicionar = (FloatingActionButton) findViewById(R.id.btn_Adicionar_ativos);
        fb_Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ProcessosAtivosActivity.this,NovoProcessoActivity.class);
                startActivity(intent);

            }
        });

        btn_p_ativo_pesquisar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ListaAtivos();
            }

        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProcessosAtivosActivity.this , NovoProcessoActivity.class);

                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                CadastroEquipamentos cadastroEquipamentos = arrayCadastroEquipamentos.get(position);
                ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(cadastroEquipamentos.getModeloEquipamentoItemIdOriginal());
                Tarefas tarefa = dbInstance.tarefasDAO().GetFirstByCategoria(modeloEquipamentos.getCategoria());

                if (tarefa != null)
                {
                    intent.putExtra(NovoProcessoActivity.EXTRA_TRACENUMBER, cadastroEquipamentos.getTraceNumber());
                    intent.putExtra(NovoProcessoActivity.EXTRA_IDORIGINAL,0);
                    intent.putExtra(NovoProcessoActivity.EXTRA_CODTAREFA, tarefa.getCodigo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_IDTAREFA, tarefa.getIdOriginal());
                    //intent.putExtra(NovoProcessoActivity.EXTRA_TIPOTAREFA, tarefa.getTipo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_TITULOTAREFA, tarefa.getTitulo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_MODELOEQUIPAMENTO, modeloEquipamentos.getModelo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_TAGEDITAR,false);

                    startActivity(intent);
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Não Existem Tarefas Cadastradas para o Ativo Selecionado.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProcessosAtivos Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, NovoProcessoActivity.class);
        startActivity(intent);
    }

    public void ListaAtivos(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<CadastroEquipamentos> cadastroEquipamentosList;
                arrayAtivos.clear();
                arrayCadastroEquipamentos.clear();

                if (edt_p_ativo_pesquisar.getText() != null)
                {
                    cadastroEquipamentosList = dbInstance.cadastroEquipamentosDAO().GetCadastroEquipamentosFilter( "%" + edt_p_ativo_pesquisar.getText().toString() + "%");
                }
                else
                {
                    Proprietarios proprietario = dbInstance.proprietariosDAO().GetByIdOriginal(dbInstance.parametrosPadraoDAO().GetBaseId());

                    cadastroEquipamentosList = dbInstance.cadastroEquipamentosDAO().GetCadastroEquipamentos(proprietario.getDescricao());
                }

                for (CadastroEquipamentos cadastroEquipamento : cadastroEquipamentosList)
                {
                    ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(cadastroEquipamento.getModeloEquipamentoItemIdOriginal());
                    String item = cadastroEquipamento.getTraceNumber() + " | " + modeloEquipamentos.getModelo();

                    arrayCadastroEquipamentos.add(cadastroEquipamento);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayAtivos.add(item);
                            adapterAtivos.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void showMessage(String title,String message,int type){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        switch (type){
            case 1:
                builder.setIcon(R.mipmap.ic_check_circle_green_36dp);
                break;
            case 2:
                builder.setIcon(R.mipmap.ic_error_red_36dp);
                break;
            case 3:
                builder.setIcon(R.mipmap.ic_warning_yellow_36dp);
                break;
            default:
        }
        builder.create().show();
    }
}
