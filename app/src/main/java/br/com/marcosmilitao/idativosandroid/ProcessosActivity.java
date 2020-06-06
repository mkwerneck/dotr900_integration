package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterProcessos;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerAlmoxarifados;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Processos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.Events.SyncEvent;
import br.com.marcosmilitao.idativosandroid.POJO.Inventario;
import br.com.marcosmilitao.idativosandroid.POJO.ProcessosProc;
import br.com.marcosmilitao.idativosandroid.Processos.ProcessosAtivosActivity;
import br.com.marcosmilitao.idativosandroid.Processos.ProcessosOSActivity;
import de.greenrobot.event.EventBus;

public class ProcessosActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;

    private TextView tv_proc_quantidade;

    private ListView lv_proc;

    private FloatingActionButton fab_proc;

    private ArrayList<ProcessosProc> listViewArrayList;

    private CustomAdapterProcessos listViewAdapter;

    private Handler createListviewHandler;
    private Handler updateListViewHandler;
    private Handler consultaHandler;
    private Handler mainHandler;

    private HandlerThread createListViewThread;
    private HandlerThread updateListViewThread;
    private HandlerThread consultaThread;

    private DateFormat dateFormat;

    private SearchView searchView;

    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processos);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

        consultaThread = new HandlerThread("consultaThread");
        consultaThread.start();
        consultaHandler = new Handler(consultaThread.getLooper())
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

        tv_proc_quantidade = (TextView) findViewById(R.id.tv_proc_quantidade);

        fab_proc = (FloatingActionButton) findViewById(R.id.fab_proc);
        fab_proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ProcessosActivity.this, AtivosProcessoActivity.class);
                startActivity(intent);
            }
        });

        listViewArrayList = new ArrayList<ProcessosProc>();
        listViewAdapter = new CustomAdapterProcessos(ProcessosActivity.this, listViewArrayList);
        lv_proc = (ListView) findViewById(R.id.lv_proc);
        lv_proc.setAdapter(listViewAdapter);
        lv_proc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProcessosProc processosProc = (ProcessosProc) adapterView.getItemAtPosition(i);

                consultaHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int count = dbInstance.upmobProcessoDAO().ExistsByProcessoId(processosProc.getIdOriginal());

                        if (count == 0)
                        {
                            Intent intent = new Intent();
                            intent.setClass(ProcessosActivity.this, ExecutarProcessosActivity.class);
                            intent.putExtra(ExecutarProcessosActivity.EXTRA_PROCESSOID, processosProc.getIdOriginal());
                            startActivity(intent);
                        } else {
                            showMessage("Atencão", "Tarefa já executada. Sincronize o tablet para processar o registro.", 3);
                        }
                    }
                });
            }
        });

        CriarListView();

        // Register as a subscriber
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        // Unregister
        bus.unregister(this);
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_processos, menu);
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
                ESync.GetSyncInstance().SyncDatabase(ProcessosActivity.this);

                return true;
            case R.id.action_novo_proc:
                Intent intent;
                intent = new Intent(ProcessosActivity.this, AtivosProcessoActivity.class);
                startActivity(intent);

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

                if (proprietario != null)
                {
                    //Obtendo a lista de processos da base específica
                    List<Processos> processosList = dbInstance.processosDAO().GetByStatus("Em Andamento", proprietario.getDescricao());

                    for(Processos processo : processosList)
                    {
                        ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetUltimaListaTarefaByCodProcesso(processo.getIdOriginal());

                        if (listaTarefas != null)
                        {
                            CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(processo.getCadsatroEquipamentosItemIdOriginal());
                            Tarefas tarefas = dbInstance.tarefasDAO().GetByIdOriginal(dbInstance.listaTarefasDAO().GetUltimaTarefaByCodProcesso(processo.getIdOriginal()));

                            if (!tarefas.getFlagSaidaAlmoxarifado())
                            {
                                ProcessosProc processoProc = new ProcessosProc();
                                processoProc.setIdOriginal(processo.getIdOriginal());
                                processoProc.setAtivo(cadastroEquipamentos.getTraceNumber());
                                processoProc.setCodProcesso(Integer.toString(processo.getIdOriginal()));
                                processoProc.setDataInicio(dateFormat.format(processo.getDataInicio()));
                                processoProc.setStatus(processo.getStatus());
                                processoProc.setEntradaAlmoxarifado(tarefas.getFlagEntradaAlmoxarifado());
                                processoProc.setTarefa(tarefas.getDescricao());

                                listViewArrayList.add(processoProc);
                            }
                        }
                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listViewAdapter.notifyDataSetChanged();

                            tv_proc_quantidade.setText("TOTAL ENCONTRADO: " + listViewArrayList.size());
                        }
                    });
                }
            }
        });
    }

    public void AtualizarListView(String s)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {

                Integer intQuery = null;
                String stringQuery = s != null ? "%" + s + "%" : null;

                try{
                    intQuery = Integer.parseInt(s);
                } catch (NumberFormatException e){}

                Proprietarios proprietario = dbInstance.proprietariosDAO().GetByIdOriginal(dbInstance.parametrosPadraoDAO().GetBaseId());

                if (proprietario != null)
                {
                    List<Processos> processosList = dbInstance.processosDAO().GetSearchResult(stringQuery, intQuery,  "Em Andamento", proprietario.getDescricao());
                    ArrayList<ProcessosProc> listViewArrayList_temp = new ArrayList<ProcessosProc>();

                    for(Processos processo : processosList)
                    {
                        ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetUltimaListaTarefaByCodProcesso(processo.getIdOriginal());

                        if (listaTarefas != null)
                        {
                            CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(processo.getCadsatroEquipamentosItemIdOriginal());
                            Tarefas tarefas = dbInstance.tarefasDAO().GetByIdOriginal(dbInstance.listaTarefasDAO().GetUltimaTarefaByCodProcesso(processo.getIdOriginal()));

                            if (!tarefas.getFlagSaidaAlmoxarifado())
                            {
                                ProcessosProc processoProc = new ProcessosProc();
                                processoProc.setIdOriginal(processo.getIdOriginal());
                                processoProc.setAtivo(cadastroEquipamentos.getTraceNumber());
                                processoProc.setCodProcesso(Integer.toString(processo.getIdOriginal()));
                                processoProc.setDataInicio(dateFormat.format(processo.getDataInicio()));
                                processoProc.setStatus(processo.getStatus());
                                processoProc.setEntradaAlmoxarifado(tarefas.getFlagEntradaAlmoxarifado());
                                processoProc.setTarefa(tarefas.getDescricao());

                                listViewArrayList_temp.add(processoProc);
                            }
                        }
                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listViewArrayList.clear();
                            listViewArrayList.addAll(listViewArrayList_temp);

                            listViewAdapter.notifyDataSetChanged();

                            tv_proc_quantidade.setText("TOTAL ENCONTRADO: " + listViewArrayList.size());
                        }
                    });
                }
            }
        });
    }

    public void showMessage(String title,String message,int type)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

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

    public void onEvent(SyncEvent event){
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                AtualizarListView(null);
            }
        });
    }
}
