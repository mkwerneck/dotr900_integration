package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterListaMateriais;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterListaServicos;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Processos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBProcesso;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.ListaMateriaisExecucaoTarefas;
import br.com.marcosmilitao.idativosandroid.POJO.ListaServicosExecucaoTarefas;
import br.com.marcosmilitao.idativosandroid.POJO.NovoProcesso_Servicos;
import br.com.marcosmilitao.idativosandroid.Processos.NovoProcessoActivity;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterResultadoOS;
import br.com.marcosmilitao.idativosandroid.helper.ListaResultados;
import br.com.marcosmilitao.idativosandroid.helper.TagidResultados;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;

public class ExecutarProcessosActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private Intent intent;

    public static final String EXTRA_CADASTROID = "cadastroId";
    public static final String EXTRA_PROCESSOID = "processoId";

    private int cadastroId = 0;
    private int processoId = 0;
    private int listaTarefaId = 0;
    private int tarefaId = 0;

    private TabHost tab_executar_processo;

    private Handler carregarDadosHandler;
    private Handler connectionBTHandler;
    private Handler readTAGIDHandler;
    private Handler updateListViewMateriaisHandler;
    private Handler updateListViewUsuariosHandler;
    private Handler salvarHandler;
    private Handler mainHandler;

    private HandlerThread carregarDadosThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;
    private HandlerThread updateListViewMateriaisThread;
    private HandlerThread updateListViewUsuariosThread;
    private HandlerThread salvarThread;

    private static VH73Device currentDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private ArrayList<ListaServicosExecucaoTarefas> listViewServicosArrayList;
    private ArrayList<ListaMateriaisExecucaoTarefas> listViewMateriaisArrayList;
    private ArrayList<ListaMateriaisExecucaoTarefas> listaMateriaisArrayList;
    private ArrayList<String> listViewUsuariosArrayList;

    private CustomAdapterListaServicos listViewServicosCustomAdapter;
    private CustomAdapterListaMateriais listViewMateriaisCustomAdapter;
    private ArrayAdapter<String> listViewUsuarioAdapter;

    private ListView lv_servicos;
    private ListView lv_materiais;
    private ListView lv_resultado;

    private TextView lbl_tarefa;

    private EditText et_info;

    private Button btn_cancelar;
    private Button btn_salvar;

    private FloatingActionButton fab_ler_materiais;
    private FloatingActionButton fab_resultado;

    private boolean reading = false;
    private boolean readingUser = false;

    private AlertDialog.Builder resultbuilder;

    private AlertDialog resultalert;

    private SlideDateTimeListener slidelistener;

    private SimpleDateFormat mFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executar_processos);

        readTAGIDThread = new HandlerThread("readTAGIDThread");
        readTAGIDThread.start();
        readTAGIDHandler = new Handler(readTAGIDThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        connectionBTThread = new HandlerThread("ConnectionBTThread");
        connectionBTThread.start();
        connectionBTHandler = new Handler(connectionBTThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        carregarDadosThread = new HandlerThread("carregarDadosThread");
        carregarDadosThread.start();
        carregarDadosHandler = new Handler(carregarDadosThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        updateListViewMateriaisThread = new HandlerThread("updateListViewMateriaisThread");
        updateListViewMateriaisThread.start();
        updateListViewMateriaisHandler = new Handler(updateListViewMateriaisThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        updateListViewUsuariosThread = new HandlerThread("updateListViewUsuariosThread");
        updateListViewUsuariosThread.start();
        updateListViewUsuariosHandler = new Handler(updateListViewUsuariosThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        salvarThread = new HandlerThread("salvarThread");
        salvarThread.start();
        salvarHandler = new Handler(salvarThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        mainHandler = new Handler();

        mFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");

        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        //inicializando Bluetooth propriedades
        foundDevices = new ArrayList<BluetoothDevice>();

        intent = getIntent();
        cadastroId = intent.getIntExtra(EXTRA_CADASTROID, 0);
        processoId = intent.getIntExtra(EXTRA_PROCESSOID, 0);

        //Inicializando a custom toolbar
        SetupToolbar();

        lbl_tarefa = (TextView) findViewById(R.id.lbl_tarefa);
        listaMateriaisArrayList = new ArrayList<ListaMateriaisExecucaoTarefas>();

        fab_ler_materiais = (FloatingActionButton) findViewById(R.id.fab_ler_materiais);
        fab_ler_materiais.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(ExecutarProcessosActivity.currentDevice != null){
                    if (reading == false)
                    {
                        readingUser = false;
                        StartReading(fab_ler_materiais);
                    } else {
                        StopReading(fab_ler_materiais);
                    }
                }else{
                    ConectarDispositivoBT();
                }
            }
        });

        tab_executar_processo = (TabHost) findViewById(R.id.tab_executar_processo);
        tab_executar_processo.setup();

        //Tab Materiais
        TabHost.TabSpec spec = tab_executar_processo.newTabSpec("tab_Materiais");
        spec.setContent(R.id.tab_Materiais);
        spec.setIndicator("Materiais");
        tab_executar_processo.addTab(spec);

        //Tab Serviços
        spec = tab_executar_processo.newTabSpec("tab_Serviços");
        spec.setContent(R.id.tab_Serviços);
        spec.setIndicator("Serviços");
        tab_executar_processo.addTab(spec);

        lv_servicos = (ListView) findViewById(R.id.lv_servicos);
        listViewServicosArrayList = new ArrayList<ListaServicosExecucaoTarefas>();
        listViewServicosCustomAdapter = new CustomAdapterListaServicos(ExecutarProcessosActivity.this, listViewServicosArrayList);
        lv_servicos.setAdapter(listViewServicosCustomAdapter);
        lv_servicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = listViewServicosArrayList.get(position);

                String modalidade = listaServicosExecucaoTarefas.getModalidade();

                //Parando a leitura caso esteja lendo
                StopReading(fab_ler_materiais);

                if (modalidade.equals("Inserção de informações"))
                {
                    ShowResultadoInfo(position);

                } else if (modalidade.equals("Identificação de Usuário"))
                {
                    ShowResultadoUser(position);

                } else if (modalidade.equals("Inserção de Data/Hora"))
                {
                    ShowResultadoDate(position);
                }
            }
        });

        lv_materiais = (ListView) findViewById(R.id.lv_materiais);
        listViewMateriaisArrayList = new ArrayList<ListaMateriaisExecucaoTarefas>();
        listViewMateriaisCustomAdapter = new CustomAdapterListaMateriais(ExecutarProcessosActivity.this, listViewMateriaisArrayList);
        lv_materiais.setAdapter(listViewMateriaisCustomAdapter);

        //Carregando os dados para nova tarefa ou tarefa existente
        CarregarDados();

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ConectarDispositivoBT();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        CloseAct();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_executar_processos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_limpar_et:
                StopReading(fab_ler_materiais);

                LimparListView();
                return true;

            case R.id.action_reconectar_et:
                StopReading(fab_ler_materiais);

                ConectarDispositivoBT();
                return true;

            case R.id.action_salvar_cf:
                StopReading(fab_ler_materiais);

                Salvar();
                return true;

            default:
                return false;
        }
    }

    private void CarregarDados()
    {
        carregarDadosHandler.post(new Runnable() {
            @Override
            public void run() {

                if (processoId == 0)
                {
                    //carregando a primeira tarefa para o modelo do ativo
                    CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(cadastroId);

                    if (cadastroEquipamentos != null)
                    {
                        ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(cadastroEquipamentos.getModeloEquipamentoItemIdOriginal());
                        Tarefas tarefas = dbInstance.tarefasDAO().GetFirstByCategoria(modeloEquipamentos.getCategoria());

                        tarefaId = tarefas.getIdOriginal();

                        //Carregando a Lista de Servicos
                        ArrayList<ListaServicosExecucaoTarefas> temp_listViewServicosArrayList = new ArrayList<ListaServicosExecucaoTarefas>();
                        for(ServicosAdicionais servico : dbInstance.servicosAdicionaisDAO().GetByIdOriginalTarefa(tarefaId))
                        {
                            ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = new ListaServicosExecucaoTarefas();
                            listaServicosExecucaoTarefas.setIdOriginal(0);
                            listaServicosExecucaoTarefas.setServico(servico.getServico());
                            listaServicosExecucaoTarefas.setServicoId(servico.getIdOriginal());
                            listaServicosExecucaoTarefas.setObrigatorio(servico.getFlagObrigatorio());
                            listaServicosExecucaoTarefas.setModalidade(servico.getModalidade());

                            temp_listViewServicosArrayList.add(listaServicosExecucaoTarefas);
                        }

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listViewServicosArrayList.addAll(temp_listViewServicosArrayList);
                                listViewServicosCustomAdapter.notifyDataSetChanged();

                                lbl_tarefa.setText(cadastroEquipamentos.getTraceNumber() + " | " + tarefas.getTitulo());
                            }
                        });
                    }

                } else {

                    //carregando os dados da terafa em execucao
                    Processos processo = dbInstance.processosDAO().GetByIdOriginal(processoId);

                    if (processo != null)
                    {
                        CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(processo.getCadsatroEquipamentosItemIdOriginal());
                        ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetUltimaListaTarefaByCodProcesso(processo.getIdOriginal());
                        Tarefas tarefas = dbInstance.tarefasDAO().GetByIdOriginal(listaTarefas.getTarefaIdOriginal());

                        tarefaId = tarefas.getIdOriginal();
                        listaTarefaId = listaTarefas.getIdOriginal();
                        cadastroId = cadastroEquipamentos.getIdOriginal();

                        //Carregando a Lista de Servicos
                        ArrayList<ListaServicosExecucaoTarefas> temp_listViewServicosArrayList = new ArrayList<ListaServicosExecucaoTarefas>();
                        for(ListaServicosListaTarefas listaServicos : dbInstance.listaServicosListaTarefasDAO().GetByListaTarefaIdOriginal(listaTarefas.getIdOriginal()))
                        {
                            ServicosAdicionais servico = dbInstance.servicosAdicionaisDAO().GetByIdOriginal(listaServicos.getServicoAdicionalIdOriginal());

                            ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = new ListaServicosExecucaoTarefas();
                            listaServicosExecucaoTarefas.setIdOriginal(listaServicos.getIdOriginal());
                            listaServicosExecucaoTarefas.setResultado(listaServicos.getResultado());
                            listaServicosExecucaoTarefas.setServico(servico.getServico());
                            listaServicosExecucaoTarefas.setServicoId(servico.getIdOriginal());
                            listaServicosExecucaoTarefas.setUltimaAtualizacao(listaServicos.getUltimaAtualizacao());
                            listaServicosExecucaoTarefas.setObrigatorio(servico.getFlagObrigatorio());
                            listaServicosExecucaoTarefas.setModalidade(servico.getModalidade());

                            temp_listViewServicosArrayList.add(listaServicosExecucaoTarefas);
                        }

                        //Carregando a Lista de Materiais
                        ArrayList<ListaMateriaisExecucaoTarefas> temp_listViewMateriaisArrayList = new ArrayList<ListaMateriaisExecucaoTarefas>();
                        for(ListaMateriaisListaTarefas materiais : dbInstance.listaMateriaisListaTarefasDAO().GetByProcessoId(processo.getIdOriginal()))
                        {
                            CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(materiais.getCadastroMateriaisIdOriginal());

                            if (cadastroMateriais != null)
                            {
                                ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                                if (modeloMateriais != null)
                                {
                                    ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas = new ListaMateriaisExecucaoTarefas();
                                    listaMateriaisExecucaoTarefas.setCadastroMateriaisId(cadastroMateriais.getIdOriginal());
                                    listaMateriaisExecucaoTarefas.setIdOriginal(materiais.getIdOriginal());
                                    listaMateriaisExecucaoTarefas.setModelo(modeloMateriais.getModelo());
                                    listaMateriaisExecucaoTarefas.setPartNumber(modeloMateriais.getPartNumber());
                                    listaMateriaisExecucaoTarefas.setTagid(cadastroMateriais.getTAGID());
                                    listaMateriaisExecucaoTarefas.setNumSerie(cadastroMateriais.getNumSerie());
                                    listaMateriaisExecucaoTarefas.setFound(materiais.getDataConclusao() != null ? true : false);

                                    temp_listViewMateriaisArrayList.add(listaMateriaisExecucaoTarefas);
                                }
                            }
                        }

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listViewServicosArrayList.addAll(temp_listViewServicosArrayList);
                                listViewMateriaisArrayList.addAll(temp_listViewMateriaisArrayList);

                                listViewServicosCustomAdapter.notifyDataSetChanged();
                                listViewMateriaisCustomAdapter.notifyDataSetChanged();

                                lbl_tarefa.setText(cadastroEquipamentos.getTraceNumber() + " | " + tarefas.getTitulo());
                            }
                        });
                    }
                }
            }
        });
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

    private void ConectarDispositivoBT()
    {
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            connectionBTHandler.post(new Runnable() {
                @Override
                public void run() {
                    VH73Device vh75Device = new VH73Device(ExecutarProcessosActivity.this, bluetoothDevice);

                    boolean succ = vh75Device.connect();

                    if (succ) {
                        ExecutarProcessosActivity.currentDevice = vh75Device;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ExecutarProcessosActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ExecutarProcessosActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void StartReading(FloatingActionButton fab)
    {
        reading = true;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read(fab);
    }

    private void StopReading(FloatingActionButton fab)
    {
        reading = false;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    private void Read(FloatingActionButton fab)
    {
        readTAGIDHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    ExecutarProcessosActivity.currentDevice.SetReaderMode((byte) 1);
                    byte[] res = ExecutarProcessosActivity.currentDevice.getCmdResultWithTimeout(3000);
                    if (!VH73Device.checkSucc(res)) {
                        StopReading(fab);

                        return;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) { // timeout
                    Log.i("READING", "Timeout!!@");
                }

                while (reading)
                {
                    long lnow = SystemClock.uptimeMillis();

                    //TODO LEITURA
                    try{
                        ExecutarProcessosActivity.currentDevice.listTagID(1, 0, 0);
                        byte[] ret = ExecutarProcessosActivity.currentDevice.getCmdResult();
                        if (VH73Device.checkSucc(ret))
                        {
                            VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);

                            //transformando cada tagid lido no padrao com sufixo "H" e atualizando a ListView
                            for(byte[] bs : listTagIDResult.epcs)
                            {
                                final String tagid = "H" + Utility.bytes2HexString(bs);

                                if (readingUser == false)
                                {
                                    AtualizarListViewMateriais(tagid);
                                } else
                                {
                                    AtualizarListViewUsuarios(tagid);
                                }
                            }
                            Log.d("SUCCESS", ret.toString());
                        } else {
                            Log.d("ERRO", ret.toString());
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    while(true)
                    {
                        long lnew = android.os.SystemClock.uptimeMillis();
                        if (lnew - lnow > 50) {
                            break;
                        }
                    }
                }
            }
        });
    }

    private void AtualizarListViewMateriais(String tagid)
    {
        updateListViewMateriaisHandler.post(new Runnable() {
            @Override
            public void run() {
                if (processoId == 0)
                {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);

                    if (cadastroMateriais != null)
                    {
                        ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                        if (modeloMateriais != null)
                        {
                            //Verificando se o tagid lido já existe na ListView
                            for(ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas : listViewMateriaisArrayList)
                            {
                                //caso já exista, retornamos a função sem adicionar o tagid a lista
                                if (listaMateriaisExecucaoTarefas.getTagid().equals(tagid)){
                                    return;
                                }
                            }

                            ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas = new ListaMateriaisExecucaoTarefas();
                            listaMateriaisExecucaoTarefas.setCadastroMateriaisId(cadastroMateriais.getIdOriginal());
                            listaMateriaisExecucaoTarefas.setIdOriginal(0);
                            listaMateriaisExecucaoTarefas.setModelo(modeloMateriais.getModelo());
                            listaMateriaisExecucaoTarefas.setPartNumber(modeloMateriais.getPartNumber());
                            listaMateriaisExecucaoTarefas.setTagid(cadastroMateriais.getTAGID());
                            listaMateriaisExecucaoTarefas.setNumSerie(cadastroMateriais.getNumSerie());
                            listaMateriaisExecucaoTarefas.setFound(false);

                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listViewMateriaisArrayList.add(listaMateriaisExecucaoTarefas);
                                    listViewMateriaisCustomAdapter.notifyDataSetChanged();

                                    if(!listaMateriaisArrayList.contains(listaMateriaisExecucaoTarefas))
                                    {
                                        listaMateriaisArrayList.add(listaMateriaisExecucaoTarefas);

                                    }
                                }
                            });
                        }
                    }

                } else {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);

                    if (cadastroMateriais != null)
                    {
                        ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                        if (modeloMateriais != null)
                        {
                            //Verificando se o tagid lido esta na lista de materiais emprestados
                            for(ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas : listViewMateriaisArrayList)
                            {
                                //caso já exista, marcamos commo encontrado e adicionamos a lista de tags encontrados
                                if (listaMateriaisExecucaoTarefas.getTagid().equals(tagid)){

                                    if (!listaMateriaisExecucaoTarefas.isFound())
                                    {
                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                listaMateriaisExecucaoTarefas.setFound(true);
                                                listViewMateriaisCustomAdapter.notifyDataSetChanged();

                                                if(!listaMateriaisArrayList.contains(listaMateriaisExecucaoTarefas))
                                                {
                                                    listaMateriaisArrayList.add(listaMateriaisExecucaoTarefas);

                                                }
                                            }
                                        });

                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void AtualizarListViewUsuarios(String tagid)
    {
        updateListViewUsuariosHandler.post(new Runnable() {
            @Override
            public void run() {
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                if (usuario != null)
                {
                    if (!listViewUsuariosArrayList.contains(usuario.getNomeCompleto()))
                    {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listViewUsuariosArrayList.add(usuario.getNomeCompleto());
                                listViewUsuarioAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void LimparListView()
    {
        if (processoId == 0){
            listaMateriaisArrayList.clear();

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listViewMateriaisArrayList.clear();
                    listViewMateriaisCustomAdapter.notifyDataSetChanged();
                }
            });
        } else {
            for(ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas : listaMateriaisArrayList){
                String tagid = listaMateriaisExecucaoTarefas.getTagid();

                for(ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas2 : listViewMateriaisArrayList){
                    if (listaMateriaisExecucaoTarefas2.getTagid().equals(tagid)){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listaMateriaisExecucaoTarefas2.setFound(false);
                                listViewMateriaisCustomAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            //limpando a lista de tags lidos
            listaMateriaisArrayList.clear();
        }
    }

    private void Salvar()
    {
        //Verificando se o usuário leu ao menos um material para emprestimo ou devolução
        if (processoId == 0 && listaMateriaisArrayList.size() == 0)
        {
            showMessage("Atenção", "É preciso ler ao menos um material para prosseguir.", 3);

            return;
        }

        //Verificando se todos os serviços obrigatórios foram respondidos
        for(ListaServicosExecucaoTarefas listaServicosExecucaoTarefas : listViewServicosArrayList){
            if (listaServicosExecucaoTarefas.isObrigatorio() && listaServicosExecucaoTarefas.getResultado() == null)
            {
                showMessage("Atenção", "É preciso preencher todos os serviços obrigatórios para prosseguir.", 3);

                return;
            }
        }

        salvarHandler.post(new Runnable() {
            @Override
            public void run() {
                Date dataHoraEvento = Calendar.getInstance().getTime();
                String serialNumber = Build.SERIAL;

                //Cadastrando a UPMOBProcesso
                UPMOBProcesso upmobProcesso = new UPMOBProcesso();
                upmobProcesso.setCadastroEquipamentoId(cadastroId);
                upmobProcesso.setDataHoraEvento(dataHoraEvento);
                upmobProcesso.setDescricaoErro(null);
                upmobProcesso.setCodColetor(serialNumber);
                upmobProcesso.setFlagErro(false);
                upmobProcesso.setFlagProcess(false);
                upmobProcesso.setIdOriginal(processoId);

                dbInstance.upmobProcessoDAO().Create(upmobProcesso);

                //Cadastrando a UPMOBListaTarefas
                UPMOBListaTarefas upmobListaTarefas = new UPMOBListaTarefas();
                upmobListaTarefas.setIdOriginal(listaTarefaId);
                upmobListaTarefas.setCodColetor(serialNumber);
                upmobListaTarefas.setDataHoraEvento(dataHoraEvento);
                upmobListaTarefas.setProcessoId(processoId);
                upmobListaTarefas.setTarefaId(tarefaId);

                dbInstance.upmobListaTarefasDAO().Create(upmobListaTarefas);

                //Cadastrando os UPMOBSListaServicos
                for(ListaServicosExecucaoTarefas listaServicosExecucaoTarefas : listViewServicosArrayList)
                {
                    UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas = new UPMOBListaServicosListaTarefas();
                    upmobListaServicosListaTarefas.setIdOriginal(listaServicosExecucaoTarefas.getIdOriginal());
                    upmobListaServicosListaTarefas.setCodColetor(serialNumber);
                    upmobListaServicosListaTarefas.setDataHoraEvento(dataHoraEvento);
                    upmobListaServicosListaTarefas.setResultado(listaServicosExecucaoTarefas.getResultado());
                    upmobListaServicosListaTarefas.setListaTarefaId(listaTarefaId);
                    upmobListaServicosListaTarefas.setServicoId(listaServicosExecucaoTarefas.getServicoId());
                    upmobListaServicosListaTarefas.setUltimaAtualizacao(listaServicosExecucaoTarefas.getUltimaAtualizacao());

                    dbInstance.upmobListaServicosListaTarefasDAO().Create(upmobListaServicosListaTarefas);
                }

                //Cadastrando os UPMOBSListaMateriais
                for(ListaMateriaisExecucaoTarefas listaMateriaisExecucaoTarefas : listaMateriaisArrayList)
                {
                    UPMOBListaMateriais upmobListaMateriais = new UPMOBListaMateriais();
                    upmobListaMateriais.setCadastroMateriaisId(listaMateriaisExecucaoTarefas.getCadastroMateriaisId());
                    upmobListaMateriais.setCodColetor(serialNumber);
                    upmobListaMateriais.setDataHoraEvento(dataHoraEvento);
                    upmobListaMateriais.setIdOriginal(listaMateriaisExecucaoTarefas.getIdOriginal());
                    upmobListaMateriais.setProcessoId(processoId);

                    dbInstance.upmobListaMateriaisDAO().Create(upmobListaMateriais);
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Execução de Tarefas", "Dados Salvos com sucesso", 1);
                    }
                });

                //Saindo da tela de execução de tarefas
                CloseWindowTimer();
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

    private void CloseWindowTimer()
    {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CloseAct();
            }
        }, 2000);
    }

    public void ShowResultadoInfo(int position)
    {
        resultbuilder = new AlertDialog.Builder(this);
        resultbuilder.setCancelable(false);
        ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = (ListaServicosExecucaoTarefas) listViewServicosArrayList.get(position);

        LayoutInflater inflater = getLayoutInflater();
        View ResultLayout = inflater.inflate(R.layout.content_resultado_info, null);
        resultbuilder.setView(ResultLayout);

        et_info = (EditText) ResultLayout.findViewById(R.id.et_info);
        btn_cancelar = (Button) ResultLayout.findViewById(R.id.btn_cancelar);
        btn_salvar = (Button) ResultLayout.findViewById(R.id.btn_salvar);

        et_info.setText(listaServicosExecucaoTarefas.getResultado());

        btn_salvar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String resultado = et_info.getText().toString();

                if (!resultado.isEmpty())
                {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listaServicosExecucaoTarefas.setResultado(et_info.getText().toString());
                            listaServicosExecucaoTarefas.setUltimaAtualizacao(Calendar.getInstance().getTime());

                            listViewServicosCustomAdapter.notifyDataSetChanged();
                        }
                    });

                    resultalert.cancel();
                    readingUser = false;
                }
            }
        });

        btn_cancelar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                resultalert.cancel();
                readingUser = false;
            }
        });

        resultalert = resultbuilder.create();
        resultalert.show();
    }

    public void ShowResultadoUser(int position)
    {
        resultbuilder = new AlertDialog.Builder(this);
        resultbuilder.setCancelable(false);
        ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = (ListaServicosExecucaoTarefas) listViewServicosArrayList.get(position);

        LayoutInflater inflater = getLayoutInflater();
        View ResultLayout = inflater.inflate(R.layout.content_resultado_user, null);
        resultbuilder.setView(ResultLayout);

        btn_cancelar = (Button) ResultLayout.findViewById(R.id.btn_cancelar);

        listViewUsuariosArrayList = new ArrayList<String>();
        listViewUsuarioAdapter = new ArrayAdapter<String>(ExecutarProcessosActivity.this, android.R.layout.simple_list_item_1, listViewUsuariosArrayList);
        lv_resultado = (ListView) ResultLayout.findViewById(R.id.lv_resultado);
        lv_resultado.setAdapter(listViewUsuarioAdapter);
        lv_resultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user = (String) adapterView.getItemAtPosition(i);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listaServicosExecucaoTarefas.setResultado(user);
                        listaServicosExecucaoTarefas.setUltimaAtualizacao(Calendar.getInstance().getTime());

                        listViewServicosCustomAdapter.notifyDataSetChanged();
                    }
                });

                resultalert.cancel();
                StopReading(fab_resultado);
            }
        });

        fab_resultado = (FloatingActionButton) ResultLayout.findViewById(R.id.fab_resultado);
        fab_resultado.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(ExecutarProcessosActivity.currentDevice != null){
                    if (reading == false)
                    {
                        readingUser = true;
                        StartReading(fab_resultado);
                    } else {
                        StopReading(fab_resultado);
                    }
                }else{
                    ConectarDispositivoBT();
                }
            }
        });

        btn_cancelar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                StopReading(fab_resultado);

                resultalert.cancel();
            }
        });

        resultalert = resultbuilder.create();
        resultalert.show();
    }

    private void ShowResultadoDate(int position)
    {
        ListaServicosExecucaoTarefas listaServicosExecucaoTarefas = (ListaServicosExecucaoTarefas) listViewServicosArrayList.get(position);

        slidelistener = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listaServicosExecucaoTarefas.setResultado(mFormatter.format(date));
                        listaServicosExecucaoTarefas.setUltimaAtualizacao(Calendar.getInstance().getTime());

                        listViewServicosCustomAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onDateTimeCancel(){

            }
        };

        SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
        timerBuilder.setListener(slidelistener);
        timerBuilder.setInitialDate(new Date());
        timerBuilder.setIs24HourTime(true);
        timerBuilder.build().show();
    }
}
