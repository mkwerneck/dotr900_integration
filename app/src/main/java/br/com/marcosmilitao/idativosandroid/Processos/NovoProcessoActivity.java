package br.com.marcosmilitao.idativosandroid.Processos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import br.com.marcosmilitao.idativosandroid.Act_Inventario;
import br.com.marcosmilitao.idativosandroid.CustomAdapterTester;
//import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
//import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPMOBListaMateriaisListaTarefaEqReadnesses;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaMateriaisListaTarefaEqReadnesses;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaResultados;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBServicos_AdicionaisSet;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBUsuariosSet;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.MainActivity;
import br.com.marcosmilitao.idativosandroid.POJO.NovoProcesso_Servicos;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.Sync.Update;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapter;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterResultadoOS;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterResultados;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterTagidResultado;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.ListaResultados;
import br.com.marcosmilitao.idativosandroid.helper.TagidResultados;
import br.com.marcosmilitao.idativosandroid.helper.TagIDInventario;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class NovoProcessoActivity  extends AppCompatActivity implements View.OnClickListener{

    private int count = 0;
    private int listaTarefasEqReadinessTables;
    private TextView lbl_Nome_Tarefa;
    private TextView lbl_Lista_Servicos;
    private TabHost tab_Novo_Processo;
    private Intent intent1;
    private Boolean FlagLeituraUser = false;
    private Boolean flagDo;
    private ListView list_Lista_Servicos;
    private ListView lv_material_encontrado;
    private ListView lv_novo_processo_tagiduser;
    private Cursor listaServicos1;
    private Cursor listaMateriais1;
    private Cursor listaResultados1;
    private String data_Inicio1, power_lvl_string;
    private int lv_servicos_position, lv_servicos_firstVisibleItem, power_lvl;
    private Boolean flagNovoProcesso1;
    private Boolean flagAtualizarDados;
    private AlertDialog alert, resultalert, resultadoalert;
    private android.app.AlertDialog.Builder adbuilder, resultbuilder, choiceresultbuilder;

    private Query_UPWEBServicos_AdicionaisSet query_UPWEBServicos_AdicionaisSet;
    private Query_UPMOBListaMateriaisListaTarefaEqReadnesses.Query_UPWEBLista_Serviços_AdicionaisSet query_upwebLista_serviços_adicionaisSet;
    private Query_UPWEBListaMateriaisListaTarefaEqReadnesses query_upwebListaMateriaisListaTarefaEqReadnesses;
    private Query_InventarioMaterial query_inventarioMaterial;
    private Query_UPWEBUsuariosSet query_UPWEBUsuariosSet;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Query_UPWEBListaResultados query_upwebListaResultados;

    private ArrayAdapter<String> listaServicosId_Original1, listaServicosModalidade1, listaServicosServico1;
    private ArrayList<Boolean> listaServicosFlagMultiplosResults;
    public ArrayList<String> arrayUsuario;
    public ArrayAdapter<String> adapterUsuario;
    public ArrayList<String> resultado_servico;
    public ArrayList<String> observacao_material;
    public ArrayList<String> listresultados, listresultados_temp;

    private ArrayList<TagidResultados> arrayMateriais_listView;
    private CustomAdapterTagidResultado adapterMateriais_listView;
    private ArrayList<ListaMateriaisListaTarefas> arrayListMateriais;

    public ArrayList<ServicosAdicionais> arrayListaServicos;
    public ArrayList<NovoProcesso_Servicos> arrayServicos_listView;
    public CustomAdapterServicosAdicionais adapterServicos_listView;
    private int tempPositionServico_listView;

    private ArrayList<String> arrayUsuario_listView;
    private ArrayAdapter<String> adapterUsuario_listView;

    private TextView lbl_Materiais_Encontrados;
    private TextView lbl_Lista_Materiais;
    private ListView list_Lista_Materiais, lv_ad_resultado, lv_choicelist_resultado;
    private ArrayAdapter<String> listaMateriais1_TAGID_Material1, listaMateriaisArray1, tagid_inventario_list, id_Original_Lista_Materiais, tagid_inventario_color;

    private SQLiteDatabase connect;

    private TextView tv_usuario;
    private TextView rfidTextView;
    private EditText edt_resultado, et_adicionar_ad_resultado;

    private Sync sync;
    private ArrayList<String> myStringArray1 = new ArrayList<String>();
    private RadioButton rb_equipmento, rb_material;
    private List<Epc> epcs = new ArrayList<Epc>();
    private Map<String, Integer> epc2num_material = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> epc2num_usuario = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> epc2num_tagid_material = new ConcurrentHashMap<String, Integer>();
    private Map<String, Integer> epc2num_tagid_usuario = new ConcurrentHashMap<String, Integer>();
    Boolean flag_list_Lista_Servicos = true;
    private TextView textView_qtd_inventario;
    private TextView et_modelo_in, et_produto_in, et_tracenumber_in, et_numserie_in, et_patrimonio_in, et_posicao_in, et_proprietario_in, et_datavalidade_in, et_tagid_in;
    private EditText edt_nLoteTracking;
    private String[] arraySpinner;
    private Idativos02Data idativos02Data;
    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_UPWEBWorksheets query_upwebWorksheets;
    private SpinnerAdapter arraySpinner_posicao;
    private List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    String trace_number, equipment_type, titulo_tarefa;
    private TextView tv_novo_processo_titulo;

    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;
    private static String device1;
    public ArrayAdapter<String> nome_completo;
    public CustomAdapterResultadoOS temp_adapter;
    public CustomAdapterResultados result_adapter;
    SQLiteDatabase db;

    ScrollView scroll_Lista_Materiais;
    private Boolean FlagNovoProcesso = false;
    private ArrayAdapter<String> listAdapter ;
    private ArrayAdapter<String> listAdapterMaterial ;
    public static String tagId ="";
    ProgressDialog progressDialog;
    boolean stoop = false;
    int readCount = 0;
    boolean inventoring = false;
    public static final String TAG = "inventory";
    private HandsetParam param;

    private static final String cor = "";
    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
    private SlideDateTimeListener slidelistener;
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };

    private Button btn_resultado, btn_Voltar, btn_OK_ad_resultado, btn_cancelar_ad_resultado, btn_adicionar_ad_resultado;

    private InputMethodManager imm;

    private List<String> AbastractTestList;

    private ArrayList<TagidResultados> listaMaterialResultados;

    private CustomAdapterTagidResultado adapterTagid;

    private GoogleApiClient client;

    public static final String EXTRA_IDORIGINAL = "idOriginal";
    public static final String EXTRA_IDTAREFA = "idTarefa";
    public static final String EXTRA_TRACENUMBER = "traceNumber";
    public static final String EXTRA_CODPROCESSO = "codProcesso";
    public static final String EXTRA_CODTAREFA = "codTarefa";
    public static final String EXTRA_TIPOTAREFA = "tipoTarefa";
    public static final String EXTRA_TITULOTAREFA = "tituloTarefa";
    public static final String EXTRA_MODELOEQUIPAMENTO = "modeloEquipamento";
    public static final String EXTRA_TAGEDITAR = "tagEditar";

    public int intentIdOriginal;
    public int intentIdTarefa;
    public String intentTraceNumber;
    public String intentCodProcesso;
    public String intentCodTarefa;
    public String intentTipoTarefa;
    public String intentTituloTarefa;
    public String intentModeloEquipamento;
    public boolean intentTagEditar;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_processo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();

            }
        });

        tagid_inventario_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        tagid_inventario_color = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        id_Original_Lista_Materiais  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listresultados = new ArrayList<String>();
        listresultados_temp = new ArrayList<String>();
        listaMaterialResultados = new ArrayList<TagidResultados>();
        temp_adapter = new CustomAdapterResultadoOS(this, listresultados_temp);

        tv_novo_processo_titulo = (TextView) findViewById(R.id.tv_novo_processo_titulo);

        tab_Novo_Processo = (TabHost) findViewById(R.id.Tab_Novo_Processo);
        tab_Novo_Processo.setup();
        intent1 = getIntent();
        device1 = Build.SERIAL;

        FlagLeituraUser = false;

        //Tab Materiais
        TabHost.TabSpec spec = tab_Novo_Processo.newTabSpec("tab_Materiais");
        spec.setContent(R.id.tab_Materiais);
        spec.setIndicator("Materiais");
        tab_Novo_Processo.addTab(spec);

        //Tab Serviços
        spec = tab_Novo_Processo.newTabSpec("tab_Serviços");
        spec.setContent(R.id.tab_Serviços);
        spec.setIndicator("Serviços");
        tab_Novo_Processo.addTab(spec);

        intent = getIntent();

        intentIdOriginal = intent.getIntExtra(EXTRA_IDORIGINAL, 0);
        intentIdTarefa = intent.getIntExtra(EXTRA_IDTAREFA, 0);
        intentTraceNumber = intent.getStringExtra(EXTRA_TRACENUMBER);
        intentModeloEquipamento = intent.getStringExtra(EXTRA_MODELOEQUIPAMENTO);
        intentTituloTarefa = intent.getStringExtra(EXTRA_TITULOTAREFA);
        intentTagEditar = intent.getBooleanExtra(EXTRA_TAGEDITAR, false);
        intentCodTarefa = intent.getStringExtra(EXTRA_CODTAREFA);

        tv_novo_processo_titulo.setText(intentTraceNumber + " | " + intentModeloEquipamento + " | " + intentTituloTarefa );

        //Indicando Tab Atual
        tab_Novo_Processo.setCurrentTab(0);

        btn_resultado = (Button) findViewById(R.id.btn_resultado);
        btn_Voltar = (Button) findViewById(R.id.btn_Voltar);
        lbl_Nome_Tarefa = (TextView) findViewById(R.id.lbl_Nome_Tarefa);
        lbl_Lista_Servicos = (TextView) findViewById(R.id.lbl_Lista_Servicos);
        lbl_Materiais_Encontrados = (TextView) findViewById(R.id.lbl_Materiais_Encontrados);

        //ListView de Serviços
        arrayListaServicos = new ArrayList<ServicosAdicionais>();
        arrayServicos_listView = new ArrayList<NovoProcesso_Servicos>();
        adapterServicos_listView = new CustomAdapterServicosAdicionais(this, arrayServicos_listView);
        list_Lista_Servicos = (ListView) findViewById(R.id.list_Lista_Servicos);
        list_Lista_Servicos.setAdapter(adapterServicos_listView);

        //ListView de Materiais
        arrayListMateriais = new ArrayList<ListaMateriaisListaTarefas>();
        arrayMateriais_listView = new ArrayList<TagidResultados>();
        adapterMateriais_listView = new CustomAdapterTagidResultado(this, arrayMateriais_listView);
        list_Lista_Materiais = (ListView) findViewById(R.id.list_Lista_Materiais);
        list_Lista_Materiais.setAdapter(adapterMateriais_listView);
        lv_material_encontrado = (ListView) findViewById(R.id.lv_material_encontrado);
        lv_material_encontrado.setAdapter(adapterMateriais_listView);

        //ListView de Usuarios
        arrayUsuario_listView = new ArrayList<String>();
        adapterUsuario_listView = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayUsuario_listView);
        lv_novo_processo_tagiduser = (ListView) findViewById(R.id.lv_novo_processo_tagiduser);
        lv_novo_processo_tagiduser.setAdapter(adapterUsuario_listView);

        lbl_Lista_Materiais = (TextView) findViewById(R.id.lbl_Lista_Materiais);
        scroll_Lista_Materiais = (ScrollView) findViewById(R.id.scroll_Lista_Materiais);
        edt_resultado = (EditText)findViewById(R.id.edt_resultado);
        tv_usuario = (TextView)findViewById(R.id.tv_usuario);
        edt_resultado.setVisibility(View.GONE);
        btn_resultado.setVisibility(View.GONE);
        btn_Voltar.setVisibility(View.GONE);
        lv_novo_processo_tagiduser.setVisibility(View.GONE);
        tv_usuario.setVisibility(View.GONE);
        flagAtualizarDados = true;

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        try {
            list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!intentTagEditar){

            lbl_Lista_Materiais.setVisibility(View.GONE);
            list_Lista_Materiais.setVisibility(View.GONE);
            scroll_Lista_Materiais.setVisibility(View.GONE);

            ListaServicosNovoProcessoItens();

        }else {

            lv_material_encontrado.setVisibility(View.GONE);
            lbl_Materiais_Encontrados.setVisibility(View.GONE);

            ListaServicosEditarProcessoItens();
        }

        ListaMateriaisItens();

        try {
            arrayUsuario = new ArrayList<String>();
            adapterUsuario = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayUsuario);

            ListaUsuarios();

        } catch (Exception e) {
            this.finish();
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_lertag_novoprocesso);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                list();
                if(NovoProcessoActivity.currentDevice != null){
                    if(!inventoring){
                        inventoring = !inventoring;
                        readCount=0;

                        EventBus.getDefault().post(new NovoProcessoActivity.InventoryEvent());
                        color = "#F30808";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }else {
                        inventoring = !inventoring;
                        color = "#41C05A";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }
                }else{
                    showMessage("Atenção", "Leitor RFID não conectado.", 3);
                }
            }
        });

        flagDo = true;

        list_Lista_Servicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(flag_list_Lista_Servicos == true) {
                    try {
                        //lv_servicos_position = position - lv_servicos_firstVisibleItem;
                        NovoProcesso_Servicos servico = adapterServicos_listView.getItem(position);

                        if (arrayListaServicos.get(position).getModalidade().equals("Inserção de informações"))
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(NovoProcessoActivity.this);
                            final EditText edt_resultado1 = new EditText(NovoProcessoActivity.this);
                            edt_resultado1.setHint("Digite o Resultado");
                            edt_resultado1.setText(servico.getResultado());

                            alert.setTitle("RESULTADO");
                            alert.setMessage("Digite o Resultado:");
                            alert.setView(edt_resultado1);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    edt_resultado.setText(edt_resultado1.getText().toString());
                                    SalvarResultado(position);
                                }
                            });
                            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            });

                            alert.show();
                            FlagLeituraUser = false;
                        }
                        else if (arrayListaServicos.get(position).getModalidade().equals("Identificação de Usuário"))
                        {
                            if (tv_usuario.getVisibility() != View.VISIBLE || lv_novo_processo_tagiduser.getVisibility() != View.VISIBLE) {
                                flag_list_Lista_Servicos = false;

                                tv_usuario.setVisibility(View.VISIBLE);
                                lv_novo_processo_tagiduser.setVisibility(View.VISIBLE);
                                view.setBackgroundColor(Color.YELLOW);
                                btn_Voltar.setVisibility(View.VISIBLE);

                                FlagLeituraUser = true;
                                tempPositionServico_listView = position;

                            } else {
                                tv_usuario.setVisibility(View.GONE);
                                lv_novo_processo_tagiduser.setVisibility(View.GONE);
                                FlagLeituraUser = false;
                            }
                        } else if (arrayListaServicos.get(position).getModalidade().equals("Inserção de Data/Hora"))
                        {
                            slidelistener = new SlideDateTimeListener(){
                                @Override
                                public void onDateTimeSet(Date date){
                                    edt_resultado.setText(mFormatter.format(date));
                                    SalvarResultado(position);
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

                            edt_resultado.setText(servico.getResultado());

                            if (edt_resultado != null)
                            {
                                timerBuilder.setInitialDate(new Date(edt_resultado.getText().toString()));
                            }

                            FlagLeituraUser = false;
                        }

                        flagDo = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(NovoProcessoActivity.this,"Selecione do Usuário Antes de Prosseguir!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        list_Lista_Servicos.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                //lv_servicos_position = view.getPositionForView(view);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lv_servicos_firstVisibleItem = firstVisibleItem;
            }
        });

        tab_Novo_Processo.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
         @Override
         public void onTabChanged(String arg0) {
             if (tab_Novo_Processo.getCurrentTab() == 0) {
                 FlagLeituraUser = false;
             }
             else if (tab_Novo_Processo.getCurrentTab() == 1) {
                 FlagLeituraUser = true;
             }
         };
        });

        lv_material_encontrado.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l){

                if (listresultados.size() == 0){
                    return;
                }

                TagidResultados tagid = adapterTagid.getItem(position);
                showCheckResultados(tagid);
            }
        });

        list_Lista_Materiais.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l){

                if (listresultados.size() == 0){
                    return;
                }

                TagidResultados tagid = adapterMateriais_listView.getItem(position);
                showCheckResultados(tagid);
            }
        });

        lv_novo_processo_tagiduser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                edt_resultado.setText(lv_novo_processo_tagiduser.getItemAtPosition(position).toString());
                SalvarResultado(tempPositionServico_listView);

                flagDo = true;
                tv_usuario.setVisibility(View.GONE);
                lv_novo_processo_tagiduser.setVisibility(View.GONE);
                btn_Voltar.setVisibility(View.GONE);
                flag_list_Lista_Servicos = true;
                Toast.makeText(NovoProcessoActivity.this,"Usuário Salvo com Sucesso!", Toast.LENGTH_SHORT).show(); }
        });

        btn_resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_resultado();
            }
        });

        btn_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultado_servico.get(lv_servicos_position).length() > 0){
                    list_Lista_Servicos.getChildAt(lv_servicos_position).setBackgroundColor(Color.TRANSPARENT);
                }
                else {
                    //int count = list_Lista_Servicos.getChildCount();
                    resultado_servico.set(lv_servicos_position, new String());
                    list_Lista_Servicos.getChildAt(lv_servicos_position).setBackgroundColor(Color.TRANSPARENT);
                }
                //FlagLeituraUser = false;
                //list_Lista_Servicos.setVerticalScrollBarEnabled(true);
                flagDo = true;

                edt_resultado.setText(new String());
                edt_resultado.setVisibility(View.GONE);
                btn_resultado.setVisibility(View.GONE);
                btn_Voltar.setVisibility(View.GONE);
                flag_list_Lista_Servicos = true;
                tv_usuario.setVisibility(View.GONE);
                lv_novo_processo_tagiduser.setVisibility(View.GONE);

//                finish();
//                startActivity(getIntent());
            }
        });

        edt_resultado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() || actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_resultado();

                    //Escondendo Teclado
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edt_resultado.getWindowToken(), 0);
                }
                return false;
            }
        });

        lv_material_encontrado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < tagid_inventario_list.getCount(); i++){
                    String tagid = tagid_inventario_list.getItem(i);
                    if (lv_material_encontrado.getItemAtPosition(position).toString().contains(tagid)){
                        showEditTextmessage(tagid);
                        break;
                    }
                }
                return false;
            }
        });
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProcessosOS Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    public Connection DbConection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "iduttosqladm";
            String password = "1dutto@04d";
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://162.144.63.165:1433/idativos_omni02_teste;user=" + username + ";password=" + password);

            return DbConn;
        } catch (InstantiationException e1) {
            //e1.printStackTrace();
            this.finish();
        } catch (IllegalAccessException e1) {
            //e1.printStackTrace();
            this.finish();
        } catch (ClassNotFoundException e1) {
            //e1.printStackTrace();
            this.finish();
        } catch (java.sql.SQLException e1) {
            //e1.printStackTrace();
            this.finish();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_novo_processo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_novo_processo_novo:
                intent = new Intent(NovoProcessoActivity.this, ProcessosAtivosActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_novo_processo_reconectar:
                list();
                return true;

            case R.id.action_novo_processo_atualizar:

                AtualizarDados();
                return true;

            case R.id.action_limpar_lista_materiais_encontrados:

                ClearList();
                return true;
            case R.id.action_novo_processo_sync:

                ESync.GetSyncInstance().SyncDatabase(NovoProcessoActivity.this);
                return true;
            default:

                return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onClick(View v) {

    }

    private void ClearList() {
        epc2num_material.clear();
        epc2num_tagid_material.clear();
        arrayMateriais_listView.clear();

        for (NovoProcesso_Servicos servico : arrayServicos_listView)
        {
            servico.setResultado(null);
        }

        adapterMateriais_listView.notifyDataSetChanged();
        adapterServicos_listView.notifyDataSetChanged();

        /*if (FlagLeituraUser == false && epc2num_material != null && epc2num_material.size() > 0) {
            epc2num_material.clear();
            epc2num_tagid_material.clear();
            tagid_inventario_list.clear();
            tagid_inventario_color.clear();
            id_Original_Lista_Materiais.clear();
            listaMaterialResultados.clear();
            try {
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshList();
                        ListaMateriaisItens(listaTarefasEqReadinessTables);
                    }
                });
            } catch (Exception e) {
                showMessage("AVISO","clearList: " + e.toString(), 2);}
        }
        else if (FlagLeituraUser == true && epc2num_usuario != null && epc2num_usuario.size() > 0){
            epc2num_usuario.clear();
            epc2num_tagid_usuario.clear();

            try {
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshList();
                    }
                });
            } catch (Exception e) {
                showMessage("AVISO","clearList: " + e.toString(), 2);}
        }*/
    }

    private void close_at(){
        BA.disable();
        BA.enable();
        this.finish();
    }

    private void AtualizarDados(){

        //Verificando se a tarefa já foi atualizada
        if (flagAtualizarDados == false) {
            showMessage("AVISO","ESTA TAREFA JÁ FOI ATUALIZADA. INICIE UMA NOVA TAREFA", 3);
            return;
        }

        //Verificando se ao menos um material foi lido
        if (!intentTagEditar && arrayMateriais_listView.size() < 1)
        {
            showMessage("AVISO", "Nenhum material encontrado", 3);
            return;
        }
        else if (intentTagEditar)
        {

        }

        //Verificando se os serviços obrigatórios foram preenchidos
        for (NovoProcesso_Servicos servico : arrayServicos_listView)
        {
            if (servico.getResultado() == null)
            {
                ServicosAdicionais servicoAdicional = servico.getServicosAdicional();

                if (servicoAdicional.getFlagObrigatorio())
                {
                    showMessage("AVISO", "Preencha todos os serviços obrigatórios", 3);
                    return;
                }
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                Date dataHoraEvento = Calendar.getInstance().getTime();

                //Atualizando ListaTarefa
                UPMOBListaTarefas upmobListaTarefas = new UPMOBListaTarefas();
                upmobListaTarefas.setIdOriginal(intentIdOriginal);
                upmobListaTarefas.setDataHoraEvento(dataHoraEvento);
                upmobListaTarefas.setCodColetor(Build.SERIAL);

                dbInstance.upmobListaTarefasDAO().Create(upmobListaTarefas);

                //Atualizando ListaMateriais
                /*for (TagidResultados tagid : arrayMateriais_listView)
                {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid.getTagid(), true);

                    UPMOBListaMateriaisListaTarefas upmobListaMateriaisListaTarefas = new UPMOBListaMateriaisListaTarefas();
                    upmobListaMateriaisListaTarefas.setIdOriginal(tagid.getIdOriginal());
                    upmobListaMateriaisListaTarefas.setStatus(intentTagEditar && !tagid.isTaged ? "Pendente" : "Concluido");
                    upmobListaMateriaisListaTarefas.setQuantidade(cadastroMateriais.getQuantidade());
                    upmobListaMateriaisListaTarefas.setCodTarefa(intentCodTarefa);
                    upmobListaMateriaisListaTarefas.setDataHoraEvento(dataHoraEvento);
                    upmobListaMateriaisListaTarefas.setCodColetor(Build.SERIAL);
                    upmobListaMateriaisListaTarefas.setTAGID(tagid.getTagid());
                    upmobListaMateriaisListaTarefas.setFlagErro(false);
                    upmobListaMateriaisListaTarefas.setFlagAtualizar(intentTagEditar);
                    upmobListaMateriaisListaTarefas.setFlagProcess(false);

                    dbInstance.upmobListaMateriaisListaTarefasDAO().Create(upmobListaMateriaisListaTarefas);
                }*/

                //Atualizando ListaServicos
                for (NovoProcesso_Servicos servico : arrayServicos_listView)
                {
                    ServicosAdicionais servicoAdicional = servico.getServicosAdicional();

                    UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas = new UPMOBListaServicosListaTarefas();
                    upmobListaServicosListaTarefas.setIdOriginal(servico.getIdOriginal());
                    upmobListaServicosListaTarefas.setResultado(servico.getResultado());
                    upmobListaServicosListaTarefas.setDataHoraEvento(dataHoraEvento);
                    upmobListaServicosListaTarefas.setCodColetor(Build.SERIAL);

                    dbInstance.upmobListaServicosListaTarefasDAO().Create(upmobListaServicosListaTarefas);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("AVISO","Tarefa atualizada com sucesso", 1);
                        flagAtualizarDados = false;
                    }
                });
            }
        }).start();
    }

    public void onEventBackgroundThread(NovoProcessoActivity.InventoryEvent e) {
        int i = 0;
        try {
            NovoProcessoActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = NovoProcessoActivity.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new NovoProcessoActivity.InventoryTerminal());
                return;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            Log.i(TAG, "Timeout!!@");
        }

        while (inventoring) {
            long lnow = android.os.SystemClock.uptimeMillis(); // 起始时间
            doInventory();
            while (true) {
                long lnew = android.os.SystemClock.uptimeMillis(); // 结束时间
                if (lnew - lnow > 50) {
                    break;
                }
            }
        }
        EventBus.getDefault().post(new NovoProcessoActivity.InventoryTerminal());

        try {
            NovoProcessoActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = NovoProcessoActivity.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(ret)) { // TODO show error message //
                Log.i(TAG, "SetReaderMode Fail!"); // return;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            Log.i(TAG, "Timeout!!@");
        }


    }

    public static class InventarioTerminal {

    }

    public static class InventoryEvent {
    }

    public static class InventoryTerminal {
    }

    public static class EpcInventoryEvent {
    }

    public static class TimeoutEvent {
    }

    private void doInventory() {
        try {
            NovoProcessoActivity.currentDevice.listTagID(1, 0, 0);
            byte[] ret = NovoProcessoActivity.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            EventBus.getDefault().post(new NovoProcessoActivity.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    NovoProcessoActivity.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    NovoProcessoActivity.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = NovoProcessoActivity.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
                    continue;
                }

                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                EventBus.getDefault().post(new NovoProcessoActivity.EpcInventoryEvent());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onEventMainThread(NovoProcessoActivity.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        ArrayList<byte[]> epcs = list.epcs;

        for (byte[] bs : epcs) {
            final  String tagid1 = "H" + Utility.bytes2HexString(bs);

            if(FlagLeituraUser == false && intentTagEditar == true) {

                for (TagidResultados tagidResultados : arrayMateriais_listView)
                {
                    if (tagidResultados.getTagid().equals(tagid1))
                    {
                        tagidResultados.isTaged = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterMateriais_listView.notifyDataSetChanged();
                            }
                        });
                        return;
                    }
                }
            }
            else if (FlagLeituraUser == false && intentTagEditar == false){

                listViewUpdateMaterial(tagid1);

            }
            else if (FlagLeituraUser == true) {

                listViewUpdateUsuario(tagid1);

            }
        }
    }

    public void listViewUpdateMaterial(String tag_inventario) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                ArrayAdapter<String> item1 = new ArrayAdapter<String>(NovoProcessoActivity.this, android.R.layout.simple_list_item_1);
                String listItem = new String();

                if (epc2num_tagid_material.containsKey(tag_inventario) || epc2num_tagid_usuario.containsKey(tag_inventario)) {
                    return;
                }

                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tag_inventario, true);

                if (cadastroMateriais != null)
                {
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());
                    listItem = modeloMateriais.getModelo() + " | " + modeloMateriais.getIDOmni() + " | " + modeloMateriais.getPartNumber();

                    epc2num_material.put(listItem, 1);
                    epc2num_tagid_material.put(tag_inventario, 1);
                    tagid_inventario_list.add(tag_inventario);
                    tagid_inventario_color.add(tag_inventario);

                    TagidResultados tagidResultado = new TagidResultados(listItem, tag_inventario, new ArrayList<ListaResultados>(), false, 0);
                    arrayMateriais_listView.add(tagidResultado);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterMateriais_listView.notifyDataSetChanged();
                        }
                    });
                }

                readCount = epc2num_material.size();
            }
        }).start();
    }

    public  ArrayAdapter<String> listViewUpdateUsuario(String tag_inventario) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tag_inventario);

                if (usuario != null)
                {
                    if (epc2num_tagid_material.containsKey(tag_inventario) || epc2num_tagid_usuario.containsKey(tag_inventario)) {
                        return;
                    }

                    arrayUsuario_listView.add(usuario.getNomeCompleto());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterUsuario_listView.notifyDataSetChanged();
                        }
                    });
                }

                readCount = epc2num_usuario.size();
            }
        }).start();

        ArrayAdapter<String> item1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        String listItem = new String();

        if (epc2num_tagid_material.containsKey(tag_inventario) || epc2num_tagid_usuario.containsKey(tag_inventario)) {
            return null;
        }

        try {
            query_UPWEBUsuariosSet = new Query_UPWEBUsuariosSet(db);
            item1 = query_UPWEBUsuariosSet.UsuarioTAGIDQuery(this,tag_inventario);
            if (item1.getCount() > 0){
                listItem = item1.getItem(0);
                item1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
                item1.add(listItem);
            }
        }
        catch(SQLException ex){
        }

        if (listItem.length() > 1) {
            epc2num_usuario.put(listItem, 1);
            epc2num_tagid_usuario.put(tag_inventario, 1);
        }
        readCount = epc2num_usuario.size();
        return item1;
    }

    public void onEventMainThread(NovoProcessoActivity.EpcInventoryEvent e) {
        if (!tagid_inventario_color.isEmpty() && FlagLeituraUser == false && intentTagEditar) {
            count = tagid_inventario_color.getCount();
            for (int i = 0; i < count; i++) {
                String item1 = tagid_inventario_color.getItem(i);
                int position1 = listaMateriais1_TAGID_Material1.getPosition(item1);
                if (position1 != -1){
                    if (list_Lista_Materiais.getChildAt(position1 - list_Lista_Materiais.getFirstVisiblePosition()) == null){
                        arrayMateriais_listView.get(position1).isTaged = true;
                    } else {
                        arrayMateriais_listView.get(position1).isTaged = true;
                        list_Lista_Materiais.getChildAt(position1 - list_Lista_Materiais.getFirstVisiblePosition()).setBackgroundColor(Color.GREEN);
                        tagid_inventario_color.remove(item1);
                    }
                    adapterMateriais_listView.notifyDataSetChanged();
                }
            }
        }
    }

    NovoProcessoActivity.InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;
        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;
        }

        public void run() {
            try {
                NovoProcessoActivity.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                NovoProcessoActivity.currentDevice.getCmdResult();
                Log.i(TAG, "read ok!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void freshStatus() {

    }

    public void onEventMainThread(AccessUI.StatusChangeEvent e) {
        freshStatus();
    }
    public void onEventMainThread(ConfigUI.LangChanged e) {
        updateLang();
    }
    public void onEventMainThread(NovoProcessoActivity.TimeoutEvent e) {
        progressDialog.dismiss();
        inventoring = false;
        EventBus.getDefault().post(new NovoProcessoActivity.InventoryTerminal());
    }

    private void refreshList() {
        if(FlagLeituraUser == false) {
            arrayMateriais_listView.clear();
            adapterMateriais_listView.notifyDataSetChanged();

        }else{

            adapter = new IdListAdaptor_Usuario();
            lv_novo_processo_tagiduser.setAdapter(adapter);
            lv_novo_processo_tagiduser.setSelection(lv_novo_processo_tagiduser.getAdapter().getCount() -1);

        }
    }

    private class IdListAdaptor_Material extends BaseAdapter {
        @Override
        public int getCount() {
            return epc2num_material.size();
        }

        @Override
        public Object getItem(int position) {
            String[] ids = new String[epc2num_material.size()];
            epc2num_material.keySet().toArray(ids);
            return ids[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = NovoProcessoActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
//            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            int count = epc2num_material.get(id);
            rfidTextView.setText(id);

            return view;
        }
    }

    private class IdListAdaptor_Usuario extends BaseAdapter {
        @Override
        public int getCount() {
            return epc2num_usuario.size();
        }

        @Override
        public Object getItem(int position) {
            String[] ids = new String[epc2num_usuario.size()];
            epc2num_usuario.keySet().toArray(ids);
            return ids[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = NovoProcessoActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
//            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            //list_tag[listView.getCount()] = id;
            int count = epc2num_usuario.get(id);
            rfidTextView.setText(id);


//            countTextView.setText("" + count);

//            TextView textViewNoTitle = (TextView) view.findViewById(R.id.txt_no_title);
//            textViewNoTitle.setText(Strings.getString(R.string.count_lable));
            //textView_qtd_inventario.setText(listView.getAdapter().getCount().toString());

            return view;
        }
    }

    private void updateLang() {
        //btnInventory.setText(Strings.getString(R.string.inventory));
        //btnSave.setText(Strings.getString(R.string.save));
        refreshList();
    }

    @Override
    public void onResume() {

        EventBus.getDefault().register(this);
        //refreshList();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        //findEpcSound.stop();

        super.onDestroy();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void list() {

        queryPairedDevices();
        pairedDevices = BA.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
            connect(bt);
        }
        //Toast.makeText(getApplicationContext(), "Bluetooth Conectado !", Toast.LENGTH_SHORT).show();
    }

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(NovoProcessoActivity.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(NovoProcessoActivity.this, currentDevice.getAddress());

                    Message message = mhandler.obtainMessage();
                    message.sendToTarget();
                } else {

                }
            }
        }.start();
    }

    public void queryPairedDevices() {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                foundDevices.add(device);
                EventBus.getDefault().post(new MainActivity.BTDeviceFoundEvent(device));

                String lastDeviceMac = getConfigLastConnected(this);
                if (lastDeviceMac.equals(device.getAddress()) && currentDevice == null) {
                    currentDevice = new VH73Device(this, device);
                    new Thread() {
                        public void run() {
                            if (currentDevice.connect()) {
                                ConfigUI.setConfigLastConnect(NovoProcessoActivity.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(NovoProcessoActivity.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }
            }
        }

    }

    public void showMessage(String title,String message,int type) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = this;
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            BA.disable();
            BA.enable();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ListaServicosItens(int listaTarefasEqReadinessTables1){
        listaServicosId_Original1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listaServicosModalidade1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listaServicosServico1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        resultado_servico = new ArrayList<String>();
        listaServicosFlagMultiplosResults = new ArrayList<Boolean>();

        query_upwebLista_serviços_adicionaisSet = new Query_UPMOBListaMateriaisListaTarefaEqReadnesses.Query_UPWEBLista_Serviços_AdicionaisSet(connect);
        listaServicos1 = query_upwebLista_serviços_adicionaisSet.ListaServicoItens(listaTarefasEqReadinessTables1);

        if (listaServicos1.getCount() > 0)
        {
            listaServicos1.moveToFirst();
            do{
                listaServicosId_Original1.add(listaServicos1.getString(listaServicos1.getColumnIndex("Id_Original")));
                listaServicosModalidade1.add(listaServicos1.getString(listaServicos1.getColumnIndex("Modalidade_Servico")));
                listaServicosServico1.add(listaServicos1.getString(listaServicos1.getColumnIndex("Servico")));
                listaServicosFlagMultiplosResults.add(listaServicos1.getInt(listaServicos1.getColumnIndex("FlagMultiplosResultados")) != 0);

                String Resultado1 = listaServicos1.getString(listaServicos1.getColumnIndex("Resultado"));
                if (Resultado1.contains("null")) {
                    resultado_servico.add(new String());
                } else {
                    resultado_servico.add(Resultado1);
                }

                if ((listaServicos1.getInt(listaServicos1.getColumnIndex("FlagMultiplosResultados")) != 0)){
                    List<String> items = Arrays.asList(Resultado1.split("\\s*,\\s*"));
                    for (String item : items){
                        listresultados.add(item);
                    }
                }


            }while (listaServicos1.moveToNext());
            list_Lista_Servicos.setAdapter(listaServicosServico1);
        }
    }

    public void ListaServicosNovoProcessoItens(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<ServicosAdicionais> servicosAdicionaisList = dbInstance.servicosAdicionaisDAO().GetByIdOriginalTarefa(intentIdTarefa);

                for (ServicosAdicionais servicosAdicional : servicosAdicionaisList)
                {
                    NovoProcesso_Servicos newServico = new NovoProcesso_Servicos();
                    newServico.setServicosAdicional(servicosAdicional);
                    newServico.setIdOriginal(0);

                    arrayListaServicos.add(servicosAdicional);
                    arrayServicos_listView.add(newServico);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterServicos_listView.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public void ListaServicosEditarProcessoItens() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<ListaServicosListaTarefas> listaServicosListaTarefasList = dbInstance.listaServicosListaTarefasDAO().GetByListaTarefaIdOriginal(intentIdOriginal);

                for (ListaServicosListaTarefas listaServico : listaServicosListaTarefasList)
                {
                    ServicosAdicionais servicoAdicional = dbInstance.servicosAdicionaisDAO().GetByIdOriginal(listaServico.getServicoAdicionalIdOriginal());

                    NovoProcesso_Servicos newServico = new NovoProcesso_Servicos();
                    newServico.setServicosAdicional(servicoAdicional);
                    newServico.setResultado(listaServico.getResultado() != null ? listaServico.getResultado() : null);
                    newServico.setIdOriginal(listaServico.getIdOriginal());

                    arrayListaServicos.add(servicoAdicional);
                    arrayServicos_listView.add(newServico);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterServicos_listView.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void ListaMateriaisItens(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<ListaMateriaisListaTarefas> listaMateriaisList = dbInstance.listaMateriaisListaTarefasDAO().GetAll();

                for (ListaMateriaisListaTarefas listaMaterial : listaMateriaisList)
                {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(listaMaterial.getCadastroMateriaisIdOriginal());
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());
                    String tagid = cadastroMateriais.getTAGID();

                    String item = modeloMateriais.getModelo() + " | " + modeloMateriais.getIDOmni() + " | " + modeloMateriais.getPartNumber();
                    TagidResultados newTag = new TagidResultados(item, tagid, new ArrayList<ListaResultados>(), false, listaMaterial.getIdOriginal());

                    epc2num_material.put(item, 1);
                    epc2num_tagid_material.put(tagid, 1);
                    tagid_inventario_list.add(tagid);
                    tagid_inventario_color.add(tagid);
                    arrayMateriais_listView.add(newTag);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterMateriais_listView.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();

        /*query_upwebListaMateriaisListaTarefaEqReadnesses = new Query_UPWEBListaMateriaisListaTarefaEqReadnesses(connect);
        listaMateriais1 = query_upwebListaMateriaisListaTarefaEqReadnesses.ListaMateriaisItens(listaTarefasEqReadinessTables1);
        listaMateriais1_TAGID_Material1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        arrayOfTags = new ArrayList<TagidResultados>();
        inventario_list_custom = new CustomAdapterTagidResultado(this, arrayOfTags);

        observacao_material = new ArrayList<String>();
        listaMateriaisArray1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        if (listaMateriais1.getCount() > 0)
        {
            listaMateriais1.moveToFirst();
            do{
                String material = listaMateriais1.getString(listaMateriais1.getColumnIndex("Quantidade")) + " | " + listaMateriais1.getString(listaMateriais1.getColumnIndex("Modelo_Material")) + " | " + listaMateriais1.getString(listaMateriais1.getColumnIndex("ID_Omni")) + " | " + listaMateriais1.getString(listaMateriais1.getColumnIndex("Part_Number"));
                listaMateriais1_TAGID_Material1.add(listaMateriais1.getString(listaMateriais1.getColumnIndex("TAGID_Material")));

                query_upwebListaResultados = new Query_UPWEBListaResultados(connect);
                ArrayList<ListaResultados> listaResultados = new ArrayList<ListaResultados>();
                for (String result : listresultados){
                    boolean isChecked = query_upwebListaResultados.ResultsByListaMaterialAny(listaMateriais1.getColumnIndex("Id_Original"), result);
                    ListaResultados resultado = new ListaResultados(result, isChecked, "2");
                    listaResultados.add(resultado);
                }


                TagidResultados newTag = new TagidResultados(material, listaMateriais1.getString(listaMateriais1.getColumnIndex("TAGID_Material")), listaResultados, false );
                arrayOfTags.add(newTag);
                observacao_material.add(listaMateriais1.getString(listaMateriais1.getColumnIndex("Observacao")));
                resultado_servico.add(new String()); // Reiniciando serviços no Botão Limpar
                listaMateriaisArray1.add(material);

            }while (listaMateriais1.moveToNext());

            list_Lista_Materiais.setAdapter(inventario_list_custom);
        }*/
    }

    public void ListaUsuarios () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                arrayUsuario.addAll(dbInstance.usuariosDAO().GetAllRecords());
            }
        }).start();
    }

    public void btn_resultado() {
        if (edt_resultado.length() != 0 && edt_resultado.getTextSize() != 0 && edt_resultado.getText() != null && edt_resultado.getText().toString() != "null"){
            resultado_servico.set(lv_servicos_position, edt_resultado.getText().toString());
            list_Lista_Servicos.getChildAt(lv_servicos_position).setBackgroundColor(Color.GREEN);
            Toast.makeText(NovoProcessoActivity.this,"Resultado Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        }
        else{
            resultado_servico.set(lv_servicos_position, new String());
            list_Lista_Servicos.getChildAt(lv_servicos_position).setBackgroundColor(Color.TRANSPARENT);
        }

        flagDo = true;

        edt_resultado.setText(new String());
        edt_resultado.setVisibility(View.GONE);
        btn_Voltar.setVisibility(View.GONE);
        btn_resultado.setVisibility(View.GONE);
    }

    public void SalvarResultado(int position){
        NovoProcesso_Servicos servico = arrayServicos_listView.get(position);

        if (edt_resultado.length() != 0 && edt_resultado.getTextSize() != 0 && edt_resultado.getText() != null && edt_resultado.getText().toString() != "null"){

            servico.setResultado(edt_resultado.getText().toString());
            adapterServicos_listView.notifyDataSetChanged();
        } else
        {
            servico.setResultado(null);
            adapterServicos_listView.notifyDataSetChanged();
        }
    }

    public void Limpar() {
        resultado_servico.set(lv_servicos_position, new String());
        edt_resultado.setText(new String());
        try {
            list_Lista_Servicos.getChildAt(lv_servicos_position).setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (FlagLeituraUser == true){
            Toast.makeText(NovoProcessoActivity.this,"Os dados do Serviço Selecionado Foram Apagados.", Toast.LENGTH_SHORT).show();
        }
        else if (FlagLeituraUser == false){
            Toast.makeText(NovoProcessoActivity.this,"Os Materiais Encontrados Foram Apagados.", Toast.LENGTH_SHORT).show();
        }

        //FlagLeituraUser = false;
        flagDo = true;
    }

    public void showCheckResultados(TagidResultados tagid){
        choiceresultbuilder = new android.app.AlertDialog.Builder(this);
        choiceresultbuilder.setCancelable(true);


        LayoutInflater inflater = getLayoutInflater();
        View ResultLayout = inflater.inflate(R.layout.choice_list_resultados, null);
        choiceresultbuilder.setView(ResultLayout);

        result_adapter = new CustomAdapterResultados(this, tagid.getResultados());
        lv_choicelist_resultado = (ListView) ResultLayout.findViewById(R.id.lv_choicelist_resultado);
        lv_choicelist_resultado.setAdapter(result_adapter);

        result_adapter.SetListener(new CustomAdapterResultados.Listener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked, CompoundButton button) {
                tagid.ChangeResultado(position, isChecked);

                if (!tagid.AnyResultTrue()){
                    showMessage("AVISO", "A Ferramenta selecionada deve ter ao menos uma OS/DB", 3);
                    button.setChecked(true);
                    tagid.ChangeResultado(position, true);
                }

            }
        });

        resultadoalert = choiceresultbuilder.create();
        resultadoalert.show();
    }

    public void showEditTextResultado(String idServico){
        listresultados_temp.clear();
        listresultados_temp.addAll(listresultados);
        temp_adapter.notifyDataSetChanged();

        resultbuilder = new android.app.AlertDialog.Builder(this);
        resultbuilder.setCancelable(true);

        LayoutInflater inflater = getLayoutInflater();
        View ResultLayout = inflater.inflate(R.layout.resultado_edittext_alertdialog_layout, null);
        resultbuilder.setView(ResultLayout);

        btn_cancelar_ad_resultado = (Button) ResultLayout.findViewById(R.id.btn_cancelar_ad_resultado);
        btn_OK_ad_resultado = (Button) ResultLayout.findViewById(R.id.btn_OK_ad_resultado);
        btn_adicionar_ad_resultado = (Button) ResultLayout.findViewById(R.id.btn_adicionar_ad_resultado);
        et_adicionar_ad_resultado = (EditText) ResultLayout.findViewById(R.id.et_adicionar_ad_resultado);

        lv_ad_resultado = (ListView) ResultLayout.findViewById(R.id.lv_ad_resultado);
        lv_ad_resultado.setAdapter(temp_adapter);

        btn_adicionar_ad_resultado.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (listresultados_temp.size() == 10) {
                    showMessage("AVISO", "Limite de 10 OS/DB atingido.", 3);

                } else if(listresultados_temp.contains(et_adicionar_ad_resultado.getText().toString())) {
                    showMessage("AVISO", "Item já cadastrado.", 3);

                } else if (et_adicionar_ad_resultado.getText().length() > 0) {
                        listresultados_temp.add(et_adicionar_ad_resultado.getText().toString());
                        temp_adapter.notifyDataSetChanged();
                        et_adicionar_ad_resultado.setText("");

                        // code to hide the soft keyboard
                        imm = (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(et_adicionar_ad_resultado.getWindowToken(), 0);
                }
            }
        });

        btn_cancelar_ad_resultado.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                listresultados_temp.clear();
                listresultados_temp.addAll(listresultados);
                temp_adapter.notifyDataSetChanged();

                resultalert.cancel();
            }
        });

        btn_OK_ad_resultado.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if (listresultados_temp.size() < 10 && et_adicionar_ad_resultado.getText().length() > 0){
                    listresultados_temp.add(et_adicionar_ad_resultado.getText().toString());
                    temp_adapter.notifyDataSetChanged();
                }

                listresultados.clear();
                listresultados.addAll(listresultados_temp);

                edt_resultado.setText(CreateStringFromArray(listresultados));
                btn_resultado();

                //ADD NEW ITERATOR
                for (Iterator<TagidResultados> addit = listaMaterialResultados.iterator(); addit.hasNext();){
                    TagidResultados tagid = addit.next();
                    for (String resultado : listresultados) {
                        if (!tagid.ExistsResultado(resultado)) {
                            tagid.AddToResultados(new ListaResultados(resultado, true, idServico));
                        }
                    }
                }

                //REMOVE ITERATOR
                for (Iterator<TagidResultados> removeit = listaMaterialResultados.iterator(); removeit.hasNext();){
                    TagidResultados tagid = removeit.next();
                    ArrayList<ListaResultados> resultados = new ArrayList<ListaResultados>();
                    for (ListaResultados resultado : tagid.getResultados()){
                        if (listresultados.contains(resultado.getResultado())){
                            resultados.add(resultado);
                        }
                    }

                    tagid.setRestulados(resultados);
                }

                resultalert.cancel();
            }
        });

        temp_adapter.SetListener(new CustomAdapterResultadoOS.Listener() {
            @Override
            public void onClick(int position) {
                boolean allowRemove = true;

                for (Iterator<TagidResultados> it = listaMaterialResultados.iterator(); it.hasNext();){
                    TagidResultados tagid = it.next();
                    if (tagid.getResultadobyName(listresultados_temp.get(position)) != null){
                        if (tagid.getResultadobyName(listresultados_temp.get(position)).getIsChecked() == true){
                            allowRemove = false;
                            showMessage("AVISO", "Não foi possível exluir. Resultado em uso em um ou mais materiais.", 3);
                            return;
                        }
                    }
                }

                if (!listresultados.contains(listresultados_temp.get(position)) || allowRemove){
                    listresultados_temp.remove(position);
                    temp_adapter.notifyDataSetChanged();
                }

            }
        });

        resultalert = resultbuilder.create();
        resultalert.show();
    }

    public void showEditTextmessage(String tagid){
        adbuilder = new android.app.AlertDialog.Builder(this);
        adbuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View edittextLayout = inflaterEt.inflate(R.layout.tagiddetails_alertdialog_layout, null);
        adbuilder.setView(edittextLayout);

        et_tagid_in = (TextView) edittextLayout.findViewById(R.id.et_tagid_in);
        et_datavalidade_in = (TextView) edittextLayout.findViewById(R.id.et_datavalidade_in);
        et_posicao_in = (TextView) edittextLayout.findViewById(R.id.et_posicao_in);
        et_patrimonio_in = (TextView) edittextLayout.findViewById(R.id.et_patrimonio_in);
        et_numserie_in = (TextView) edittextLayout.findViewById(R.id.et_numserie_in);
        et_tracenumber_in = (TextView) edittextLayout.findViewById(R.id.et_tracenumber_in);
        et_produto_in = (TextView) edittextLayout.findViewById(R.id.et_produto_in);
        et_modelo_in = (TextView) edittextLayout.findViewById(R.id.et_modelo_in);

        Cursor material = null;
        query_upwebWorksheets = new Query_UPWEBWorksheets(db);
        material = query_upwebWorksheets.UPWEBWorksheetTAGIDQuery(tagid);

        Cursor inventario = null;
        query_InventarioMaterial = new Query_InventarioMaterial(db);
        inventario = query_InventarioMaterial.InventarioMaterialTAGIDQuery3(tagid);

        if (material.moveToFirst() && inventario.moveToFirst()){
            et_proprietario_in.setText(inventario.getString(inventario.getColumnIndex("Proprietario_SETOR")));
            et_tagid_in.setText(inventario.getString(inventario.getColumnIndex("TAGID_Material")));
            et_datavalidade_in.setText(material.getString(material.getColumnIndex("Data_Validade")));
            et_posicao_in.setText(inventario.getString(inventario.getColumnIndex("Cod_Posicao")));
            et_patrimonio_in.setText(material.getString(material.getColumnIndex("PK_Lote")));
            et_numserie_in.setText(material.getString(material.getColumnIndex("PK_Serie")));
            et_tracenumber_in.setText(inventario.getString(inventario.getColumnIndex("ID_Omni")));
            et_produto_in.setText(material.getString(material.getColumnIndex("Part_Number")));
            et_modelo_in.setText(inventario.getString(inventario.getColumnIndex("Material_Type")));
        }

        alert = adbuilder.create();
        alert.show();
    }

    public String CreateStringFromArray(ArrayList<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i ++){
            if (i != 0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(list.get(i).toString());
        }

        return stringBuilder.toString();
    }
}
