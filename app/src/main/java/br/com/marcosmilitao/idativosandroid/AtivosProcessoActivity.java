package br.com.marcosmilitao.idativosandroid;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterAtivos;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterProcessos;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Processos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.POJO.AtivosProc;
import br.com.marcosmilitao.idativosandroid.POJO.ProcessosProc;
import br.com.marcosmilitao.idativosandroid.Processos.ProcessosAtivosActivity;

public class AtivosProcessoActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;

    private TextView tv_atproc_quantidade;

    private ListView lv_atproc;

    private ArrayList<AtivosProc> listViewArrayList;

    private CustomAdapterAtivos listViewAdapter;

    private Handler createListviewHandler;
    private Handler updateListViewHandler;
    private Handler mainHandler;

    private HandlerThread createListViewThread;
    private HandlerThread updateListViewThread;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos_processo);

        createListViewThread = new HandlerThread("createListViewThread");
        createListViewThread.start();
        createListviewHandler = new Handler(createListViewThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        updateListViewThread = new HandlerThread("updateListViewThread");
        updateListViewThread.start();
        updateListViewHandler = new Handler(updateListViewThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        mainHandler = new Handler();

        //Inicializando a custom toolbar
        SetupToolbar();

        //instancia do banco de dados
        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        tv_atproc_quantidade = (TextView) findViewById(R.id.tv_atproc_quantidade);

        listViewArrayList = new ArrayList<AtivosProc>();
        listViewAdapter = new CustomAdapterAtivos(AtivosProcessoActivity.this, listViewArrayList);
        lv_atproc = (ListView) findViewById(R.id.lv_atproc);
        lv_atproc.setAdapter(listViewAdapter);
        lv_atproc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AtivosProc ativosProc = (AtivosProc) adapterView.getItemAtPosition(i);

                Intent intent = new Intent();
                intent.setClass(AtivosProcessoActivity.this, ExecutarProcessosActivity.class);
                intent.putExtra(ExecutarProcessosActivity.EXTRA_CADASTROID, ativosProc.getIdOriginal());
                startActivity(intent);
            }
        });

        CriarListView();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_ativos_processos, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                AtualizarListView(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                AtualizarListView(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sync_proc:
                ESync.GetSyncInstance().SyncDatabase(AtivosProcessoActivity.this);

                return true;
            default:

                return false;
        }
    }

    private void SetupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseAct();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void CloseAct()
    {
        this.finish();
    }

    public void CriarListView()
    {
        createListviewHandler.post(new Runnable() {
            @Override
            public void run() {

                Proprietarios proprietario = dbInstance.proprietariosDAO().GetByIdOriginal(dbInstance.parametrosPadraoDAO().GetBaseId());

                List<CadastroEquipamentos> equipamentosList = dbInstance.cadastroEquipamentosDAO().GetCadastroEquipamentos(proprietario.getDescricao());
                ArrayList<AtivosProc> listViewArrayList_temp = new ArrayList<AtivosProc>();

                for(CadastroEquipamentos equipamento : equipamentosList)
                {
                    ModeloEquipamentos modeloEquipamento = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(equipamento.getModeloEquipamentoItemIdOriginal());

                    if (modeloEquipamento != null)
                    {
                        AtivosProc ativoProc = new AtivosProc();
                        ativoProc.setIdOriginal(equipamento.getIdOriginal());
                        ativoProc.setAtivo(equipamento.getTraceNumber());
                        ativoProc.setModelo(modeloEquipamento.getModelo());

                        listViewArrayList_temp.add(ativoProc);
                    }
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewArrayList.addAll(listViewArrayList_temp);
                        listViewAdapter.notifyDataSetChanged();

                        tv_atproc_quantidade.setText("TOTAL ENCONTRADO: " + listViewArrayList.size());
                    }
                });
            }
        });
    }

    public void AtualizarListView(String s)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {

                String stringQuery = s != null ? "%" + s + "%" : null;

                Proprietarios proprietario = dbInstance.proprietariosDAO().GetByIdOriginal(dbInstance.parametrosPadraoDAO().GetBaseId());

                List<CadastroEquipamentos> equipamentosList = dbInstance.cadastroEquipamentosDAO().GetSearchResult(stringQuery, proprietario.getDescricao());
                ArrayList<AtivosProc> listViewArrayList_temp = new ArrayList<AtivosProc>();

                for(CadastroEquipamentos equipamento : equipamentosList)
                {
                    ModeloEquipamentos modeloEquipamento = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(equipamento.getModeloEquipamentoItemIdOriginal());

                    if (modeloEquipamento != null)
                    {
                        AtivosProc ativoProc = new AtivosProc();
                        ativoProc.setIdOriginal(equipamento.getIdOriginal());
                        ativoProc.setAtivo(equipamento.getTraceNumber());
                        ativoProc.setModelo(modeloEquipamento.getModelo());

                        listViewArrayList_temp.add(ativoProc);
                    }
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewArrayList.clear();
                        listViewArrayList.addAll(listViewArrayList_temp);
                        listViewAdapter.notifyDataSetChanged();

                        tv_atproc_quantidade.setText("TOTAL ENCONTRADO: " + listViewArrayList.size());
                    }
                });
            }
        });
    }
}
