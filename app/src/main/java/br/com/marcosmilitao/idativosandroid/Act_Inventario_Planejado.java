package br.com.marcosmilitao.idativosandroid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioEquipamento;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_Parametros_Padrao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBAlmoxarifado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBInventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaMateriaisInvPlanejado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEB_ColetoresDados;
import br.com.marcosmilitao.idativosandroid.POJO.InventarioPlanejado_Materiais;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapter;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.RetrieveXMLResult;
import br.com.marcosmilitao.idativosandroid.helper.TagIDInventario;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import br.com.marcosmilitao.idativosandroid.helper.XR400ReadTags;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_Inventario_Planejado extends AppCompatActivity {
    TextView rfidTextView;
    ListView listView;
    private ListAdapter adapter;
    private BluetoothAdapter BA;

    private Set<BluetoothDevice> pairedDevices;
    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    private  int count=0;
    private Sync sync;
    RadioButton rb_equipmento, rb_material;
    CheckBox ckb_tagid_pos;

    List<Epc> epcs = new ArrayList<Epc>();
    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> epc2num2 = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> BLEMap = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> tagposprox = new ConcurrentHashMap<String, Integer>();

    private String key_modelo_leitor_rfid = "Vanch_VH75";
    private SharedPreferences pref;

    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Handler mHandler;
    public boolean mScanning;
    public static final long SCAN_PERIOD = 60000;
    private static final int REQUEST_ENABLE_BT = 1;

    TextView textView_qtd_inventario;
    private String[] arraySpinner;
    private String[] list_tag;
    private String tagid_posicao_atual = "0", tagid_posicao_process = "0", processo_atual;
    SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    public static VH73Device currentDevice;
    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Query_InventarioEquipamento query_InventarioEquipamento;
    private Query_UPWEB_ColetoresDados query_upweb_coletoresDados;
    private Query_Parametros_Padrao query_parametros_padrao;
    private Query_UPWEBInventarioPlanejado query_inventario_planejado;
    private Query_UPWEBListaMateriaisInvPlanejado query_upwebListaMateriaisInvPlanejado;
    private Query_UPWEBWorksheets query_upwebWorksheets;
    private FloatingActionButton fab;
    private Query_UPWEBAlmoxarifado query_upwebAlmoxarifado;
    private Spinner sp_inventario_processo, sp_inventario_posicao, sp_inventario_almoxarifado, sp_inventario_planejado;
    private TextView lb_inventario_posicao, lb_inventario_almoxarifado;
    private TextView et_modelo_in, et_produto_in, et_tracenumber_in, et_numserie_in, et_patrimonio_in, et_posicao_in, et_proprietario_in, et_datavalidade_in, et_tagid_in;
    private SpinnerAdapter arraySpinner_posicao, arraySpinner_almoxarifado;
    private ArrayAdapter<String> inventario_list1, tagid_inventario_list, tagid_inventario_plan, tagid_inventario_color,cod_posicao_list;
    private ArrayAdapter<String> adapterProcesso;
    private ArrayAdapter<String> adapterAlmoxarifados;
    private ArrayAdapter<String> adapterPosicoes;
    private ArrayAdapter<String> adapterInventarioPlanejado;
    private ArrayList<String> alistAlmoxarifados;
    private ArrayList<String> alistPosicoes;
    private ArrayList<String> arrayInventarioPlanejado;
    private CustomAdapter inventario_list_custom;
    List<String> lista_tags_inventariados;
    public static String tagId ="";
    ProgressDialog progressDialog;
    boolean stoop = false;
    private boolean flag_posicao_process = true;

    private int readCount = 0;
    private int BLEdeviceCount = 0;

    boolean inventoring = false, usingXR400 = false;
    public static final String TAG = "inventory";
    public static final String key_ipxr400 = "key_ipxr400";
    Button btn_inventarioPlan_add;
    private HandsetParam param;
    private static final String cor = "";
    private GoogleApiClient client;
    private RetrieveXMLResult getXML;
    private String[] menuItens = null;
    private AlertDialog.Builder adbuilder;
    private AlertDialog alert;
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };
    ArrayList<String> tagid_inventario_arraylist;
    ArrayList<TagIDInventario> arrayOfTags;
    private LinearLayout ll_invplan;

    public Act_Inventario_Planejado() {
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Act_Inventario_Planejado Page") // TODO: Define a title for the content shown.
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
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();

        if (mBluetoothAdapter != null) {
            BluetoothLeDisconnect();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__inventario__planejado);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();
            }
        });

        setTitle("INVENTÁRIO PLANEJADO");
        tagid_inventario_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        tagid_inventario_color = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);        ;
        tagid_inventario_plan = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        inventario_list1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        cod_posicao_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        arrayOfTags = new ArrayList<TagIDInventario>();
        inventario_list_custom = new CustomAdapter(this, arrayOfTags);
        btn_inventarioPlan_add = (Button) findViewById(R.id.btn_inventarioPlan_add);
        lb_inventario_almoxarifado = (TextView) findViewById(R.id.lb_inventario_almoxarifado);
        lb_inventario_posicao = (TextView) findViewById(R.id.lb_inventario_posicao);
        rb_equipmento = (RadioButton) findViewById(R.id.rb_equipmento);
        rb_material= (RadioButton) findViewById(R.id.rb_material);
        ckb_tagid_pos = (CheckBox) findViewById(R.id.ckb_tagid_pos);

        ll_invplan = (LinearLayout) findViewById(R.id.ll_invplan);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        listView = (ListView) findViewById(R.id.lv_Inventario_planejado);
        listView.setAdapter(inventario_list_custom);

        textView_qtd_inventario = (TextView) findViewById(R.id.tv_inventario__planejado_qtd);

        //Iniciando Spiner de processos
        sp_inventario_processo = (Spinner) findViewById(R.id.sp_inventario_processo);
        FillProcessoSpinner();

        //Preenchendo spinner com a lista de inventarios planejados - REVISAR PARA VERSAO NOVA ID-ATIVOS 2019
        arrayInventarioPlanejado = new ArrayList<String>();
        adapterInventarioPlanejado = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayInventarioPlanejado);
        sp_inventario_planejado = (Spinner) findViewById(R.id.sp_inventario_planejado);
        sp_inventario_planejado.setAdapter(adapterInventarioPlanejado);

        sp_inventario_almoxarifado = (Spinner) findViewById(R.id.sp_inventario_almoxarifado);
        FillAlmoxarifadoSpinner();

        alistPosicoes = new ArrayList<String>();
        adapterPosicoes = new ArrayAdapter<String>(this, R.layout.spinner_item, alistPosicoes);
        sp_inventario_posicao = (Spinner) findViewById(R.id.sp_inventario_posicao);
        sp_inventario_posicao.setAdapter(adapterPosicoes);

        tagid_inventario_arraylist = new ArrayList<String>();

        updateLang();

        //Carregando SharedPreferences MyPref
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        key_modelo_leitor_rfid = pref.getString("key_modelo_leitor_rfid","Vanch_VH75");
        if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
            try {
                list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // TAGID Posição atualmente APENAS para VH75
            ckb_tagid_pos.setVisibility(View.GONE);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab_lertag_inventario_planejado);
        //registerForContextMenu(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                if (sp_inventario_processo.getSelectedItem().toString() != "Inventário Proximidade"){
                    if(!inventoring && !ckb_tagid_pos.isChecked() && listView.getCount() == 0){
                        showMessage("AVISO", "Atualize a Lista de Inventário Planejado antes de iniciar!", 3);
                        return;
                    }
                } else if (sp_inventario_processo.getSelectedItem().toString().isEmpty()){
                    showMessage("AVISO", "Selecione o Processo antes de iniciar!", 3);
                    return;
                }

                key_modelo_leitor_rfid = pref.getString("key_modelo_leitor_rfid","Vanch_VH75");
                if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
                    list();
                    if(Act_Inventario_Planejado.currentDevice != null){
                        if(!inventoring){
                            inventoring = true;
                            readCount=0;

                            if (ckb_tagid_pos.isChecked()){
                                flag_posicao_process = true;
                            }

                            EventBus.getDefault().post(new Act_Inventario_Planejado.InventoryEvent());
                            color = "#F30808";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }else {
                            inventoring = false;
                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    }else{
                        showMessage("Atenção", "Leitor RFID não conectado", 3);
                    }

                }  else if (key_modelo_leitor_rfid.equals("BluetoothLE")) {
                    //boolean BluetoothLE = bluetoothLeGatt1.BluetoothLeGattIni();
                    DeviceScanActivity_ini ();
                    if (mBluetoothAdapter != null) {
                        if (!inventoring) {
                            inventoring = !inventoring;
                            readCount = 0;
                            //clearLeDeviceList();
                            color = "#FFFF00";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

                            BluetoothLeGatt_ini();
                        } else {
                            scanLeDevice(false);
                            inventoring = !inventoring;
                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    } else {
                        Utility.WarningAlertDialg(Act_Inventario_Planejado.this, "!", "Não foi possível inciar a conexão Bluetooth.").show();
                    }
                } else if (key_modelo_leitor_rfid.equals("XR_400")) {
                    usingXR400 = true;

                    if (isXR400Alive()){
                        if(!inventoring){
                            inventoring = !inventoring;
                            readCount = 0;

                            color = "#FFFF00";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

                            getXML = new RetrieveXMLResult(pref.getString(key_ipxr400, "192.168.0.100"));

                            try{
                                getXML.ClearBuffer();
                            } catch (Exception e){
                                Log.d("ERROR HTTP", e.getMessage().toString());
                            }
                            getXML = null;

                        } else {
                            inventoring = !inventoring;
                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    } else {
                        showMessage("Atenção", "Nenhum leitor RFID XR 400 encontrado", 3);
                    }
                } else {
                    Utility.WarningAlertDialg(Act_Inventario_Planejado.this, "!", "Leitor " + key_modelo_leitor_rfid + " Não Habilitado neste dispositivo.").show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String lc_tagid = tagid_inventario_plan.getItem(position);
                showEditTextmessage(lc_tagid);
                return false;
            }
        });

        rb_material.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                //clearList();
                Inventario_Posicao_Add();
            }
        });

        rb_equipmento.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                //clearList();
                ClearListViewOnSpinnerChanges();
            }
        });

        ckb_tagid_pos.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                if (inventoring && ckb_tagid_pos.isChecked()){
                    ckb_tagid_pos.setSelected(true);
                } else if (!ckb_tagid_pos.isChecked()){
                    Inventario_Posicao_Add();
                } else if (ckb_tagid_pos.isChecked()){
                    ClearListViewOnSpinnerChanges();
                }
            }
        });

        sp_inventario_almoxarifado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long arg3) {
                FillPosicaoSpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });

        sp_inventario_posicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long arg3) {
                if (!ckb_tagid_pos.isChecked()){
                    try{
                        ckb_tagid_pos.setSelected(false);
                        Inventario_Posicao_Add();
                    } catch(Exception e){ }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });

        sp_inventario_planejado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InventarioPlanejado_Add();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_inventario_processo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                processo_atual = sp_inventario_processo.getSelectedItem().toString();

                if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade") {
                    EsconderMostrarSpinnerPosicaoAlmoxarifado(false);
                    clearList();

                } else if(sp_inventario_processo.getSelectedItem().toString() == "Inventário Planejado"){
                    EsconderMostrarSpinnerPosicaoAlmoxarifado(false);
                    ll_invplan.setVisibility(View.VISIBLE);

                    FillInventarioPlanejadoSpiner();

                } else {
                    EsconderMostrarSpinnerPosicaoAlmoxarifado(true);

                    if (sp_inventario_posicao.getSelectedItem() != null){
                        try{
                            clearList();
                            Carregar_Almoxarifado();
                            ckb_tagid_pos.setSelected(false);
                            Inventario_Posicao_Add();
                        } catch(Exception e){ }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        runtimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_inventario_planejado,menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if (v.getId() == R.id.fab_lertag_inventario_planejado){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menuItens = new String[]{"VANCH HandHeld", "XR400"};
            menu.setHeaderTitle("Escolha o leitor:");

            for (int i = 0; i<menuItens.length; i++){
                menu.add(Menu.NONE, i, i, menuItens[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItemIndex = item.getItemId();
        String menuItemName = menuItens[menuItemIndex];
        String color;

        switch (menuItemName){
            case "VANCH HandHeld":
                if(Act_Inventario_Planejado.currentDevice != null){
                    inventoring = !inventoring;
                    readCount=0;

                    if (ckb_tagid_pos.isChecked()){
                        flag_posicao_process = true;
                    }

                    EventBus.getDefault().post(new Act_Inventario_Planejado.InventoryEvent());
                    color = "#F30808";
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                }else{
                    showMessage("Atenção", "Leitor RFID não conectado", 3);
                }
                break;
            case "XR400":
                if (isXR400Alive()){
                    inventoring = !inventoring;
                    usingXR400 = true;
                    readCount = 0;

                    color = "#F30808";
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                } else {
                    showMessage("Atenção", "Leitor RFID XR400 não disponível", 3);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.inventario_palanejado_Atualizar:
                //showMessage("Inventário!", "Atualizando Inventário ...!");
                if(sp_inventario_posicao.getSelectedItem() == null){
                    showMessage("AVISO", "Selecione a Posição !", 3);
                    //return false;
                }else if(sp_inventario_processo.getSelectedItem()== null){
                    showMessage("AVISO", "Selecione o Processo !", 3);
                    //return false;
                }

                Atualizar_Inventario();
                return true;
            case R.id.inventario_planejado_limpar:
                clearList();

                if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Posição" || sp_inventario_processo.getSelectedItem().toString() == "Inventário Completo"){
                    Inventario_Posicao_Add();
                } else if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Planejado"){
                    InventarioPlanejado_Add();
                }

                readCount = 0;

                return true;
            case R.id.inventario_planejado_conexao:
                list();
                return true;
            case R.id.inventario_planejado_sync:
                //Nova Chamada para Sincronismo
                ESync.GetSyncInstance().SyncDatabase(Act_Inventario_Planejado.this);
                return true;
            default:
                return false;
        }
    }

    public void Atualizar_Inventario() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                count = tagid_inventario_list.getCount();

                if (rb_equipmento.isChecked())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("AVISO", "Esta funcionalidade NÃO está disponível para Equipamentos nesta Versão do Aplicativo.", 3);
                        }
                    });
                }
                else if (rb_material.isChecked())
                {
                    for (int i = 0; i < count; i++)
                    {
                        CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid_inventario_list.getItem(i), true);
                        Posicoes posicao = dbInstance.posicoesDAO().GetByCodPosicao(cod_posicao_list.getItem(i));

                        if (cadastroMateriais != null && posicao != null)
                        {
                            UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao = new UPMOBHistoricoLocalizacao();
                            upmobHistoricoLocalizacao.setCodColetor(Build.SERIAL);
                            upmobHistoricoLocalizacao.setDataHoraEvento(Calendar.getInstance().getTime());
                            upmobHistoricoLocalizacao.setDescricaoErro("");
                            upmobHistoricoLocalizacao.setFlagErro(false);
                            upmobHistoricoLocalizacao.setFlagProcess(false);
                            upmobHistoricoLocalizacao.setProcesso(sp_inventario_processo.getSelectedItem().toString());

                            dbInstance.upmobHistoricoLocalizacaoDAO().Create(upmobHistoricoLocalizacao);
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage("ATENÇÃO", "Posição não encontrada", 3);
                                }
                            });
                            return;
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("AVISO", "Inventário atualizado com sucesso", 1);
                    }
                });
            }
        }).start();
    }

    // ================================= VH75 READER PROCESS ================================= //

    private void close_at(){
        BA.disable();
        BA.enable();
        this.finish();
    }

    public void list() {
        queryPairedDevices();
        pairedDevices = BA.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
            connect(bt);
        }
    }

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(Act_Inventario_Planejado.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Inventario_Planejado.this, currentDevice.getAddress());

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
                                ConfigUI.setConfigLastConnect(Act_Inventario_Planejado.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Inventario_Planejado.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }


            }
        }

    }

    private void clearVH75List() {
        if (epc2num != null && epc2num.size() > 0) {
            epc2num.clear();
            epc2num2.clear();
            EventBus.clearCaches();
        }
    }

    private void ClearListViewOnSpinnerChanges(){
        if (epc2num != null && epc2num.size() > 0) {
            epc2num.clear();
            epc2num2.clear();
            EventBus.clearCaches();
        }

        tagid_inventario_plan.clear();
        arrayOfTags.clear();
        inventario_list_custom.notifyDataSetChanged();

        lista_tags_inventariados = new ArrayList<String>();
        textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventBackgroundThread(Act_Inventario_Planejado.InventoryEvent e) {
        int i = 0;
        int timeout = 500;
        try {
            Act_Inventario_Planejado.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_Inventario_Planejado.currentDevice.getCmdResultWithTimeout(timeout);

            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_Inventario_Planejado.InventoryTerminal());
                return;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            Log.i(TAG, "Timeout!!@");
        }

        inventoring = false;
        SystemClock.sleep(1000);
        inventoring = true;

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

        EventBus.getDefault().post(new Act_Inventario_Planejado.InventoryTerminal());

        try {
            MainActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Inventario_Planejado.currentDevice.getCmdResultWithTimeout(timeout);
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

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void doInventory() {
        try {
            Act_Inventario_Planejado.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_Inventario_Planejado.currentDevice.getCmdResult();

            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }

            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            EventBus.getDefault().post(new Act_Inventario_Planejado.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_Inventario_Planejado.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_Inventario_Planejado.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_Inventario_Planejado.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                EventBus.getDefault().post(new Act_Inventario_Planejado.EpcInventoryEvent());
            }
            // EventBus.getDefault().post(new InventoryTerminal());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void addEpc(VH73Device.ListTagIDResult list) {
        String tagid_posicao1 = new String();
        ArrayList<byte[]> epcs = list.epcs;

        for (byte[] bs : epcs) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String tag_inventario = "H" + Utility.bytes2HexString(bs);
                    String tag_prefixo = tag_inventario.substring(0,4);
                    String posicao_prefixo = "HF13";

                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                    Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tag_inventario);

                    Boolean flag_tagid_pos = (tag_prefixo.equals(posicao_prefixo) && posicao != null);

                    TAGID_Process(flag_tagid_pos, tag_inventario);
                }
            }).start();
        }
    }

    public void listViewUpdateVH75(String tag_inventario, String cod_posicao) {
        int position1 = -1;
        if (epc2num2.containsKey(tag_inventario)) {
            return;
        }

        String[] listItens = listViewUpdate(tag_inventario);
        String listItem1 = listItens[0];
        String listItem2 = listItens[2];

        try {
            position1 = Integer.parseInt(listItens[1]);
        } catch (Exception e) {}

        if (!listItem1.isEmpty() || !listItem2.isEmpty()){
            if (!tagid_contains(lista_tags_inventariados, tag_inventario)){

                epc2num.put(tag_inventario, 1);
                epc2num2.put(tag_inventario, 1);

                tagid_inventario_list.add(tag_inventario);
                cod_posicao_list.add(cod_posicao);
                lista_tags_inventariados.add(tag_inventario);

                if (!listItem2.isEmpty()){
                    tagid_inventario_arraylist.add(listItem2);
                }

                listItem2 = new String();
                listItem1 = new String();

                if (position1 > -1 || sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
                    tagid_inventario_color.add(tag_inventario);
                }
            }
        } else {
            listItem2 = new String();
            listItem1 = new String();
        }

        readCount();
    }

    Act_Inventario_Planejado.InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;


        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;

        }

        public void run() {
            try {
                Act_Inventario_Planejado.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_Inventario_Planejado.currentDevice.getCmdResult();
                Log.i(TAG, "read ok!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void freshStatus() {

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventMainThread(Act_Inventario_Planejado.EpcInventoryEvent e) {
        listView_Update();
    }
    public void onEventMainThread(AccessUI.StatusChangeEvent e) {
        freshStatus();
    }
    public void onEventMainThread(ConfigUI.LangChanged e) {
        updateLang();
    }
    public void onEventMainThread(Act_Inventario_Planejado.TimeoutEvent e) {
        progressDialog.dismiss();
        inventoring = false;
        EventBus.getDefault().post(new Act_Inventario_Planejado.InventoryTerminal());
    }
    public void onEventMainThread(Act_Inventario_Planejado.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;//
//        btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
//        btnInventory.setText(Strings.getString(R.string.inventory));
    }

    private void refreshList() {
        adapter = new IdListAdaptor();
        listView.setAdapter(adapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
        textView_qtd_inventario.setText(String.valueOf(listView.getAdapter().getCount()));

    }

    private class IdListAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return epc2num.size();
        }

        @Override
        public Object getItem(int position) {
            String[] ids = new String[epc2num.size()];
            epc2num.keySet().toArray(ids);
            return ids[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = Act_Inventario_Planejado.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
//            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            //list_tag[listView.getCount()] = id;
            //int count = epc2num.get(id);
            rfidTextView.setText(id);
            view.setBackgroundColor(Color.GREEN);

//            countTextView.setText("" + count);

//            TextView textViewNoTitle = (TextView) view.findViewById(R.id.txt_no_title);
//            textViewNoTitle.setText(Strings.getString(R.string.count_lable));
            //textView_qtd_inventario.setText(listView.getAdapter().getCount().toString());

            return view;
        }

    }

    // ================================= VH75 READER PROCESS ================================= //


    // ================================= BluetoothLeGatt PROCESS ================================= //

    public void BluetoothLeGatt_ini () {
        mHandler = new Handler();
        if (mBluetoothAdapter != null && inventoring) {
            scanLeDevice(true);
        }
    }

    public void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BluetoothLeDisconnect();
                }
            }, SCAN_PERIOD);

            // Start scanning
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback_InvPlan);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback_InvPlan);
        }
        //invalidateOptionsMenu();

        //LeDeviceListAdapter leDeviceListAdapter1 = new LeDeviceListAdapter();
        //return LeDeviceListAdapter().mLeDevices;
    }

    public class LeDeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = Act_Inventario_Planejado.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                BLEdeviceCount = mLeDevices.size();
/*              TAGID_Process (boolean flag_tagid_pos, String tag_inventario)
                listViewUpdateBluetoothLeGatt(device.toString(), sp_inventario_posicao.getSelectedItem().toString());*/
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return BLEMap.size();
            //return mLeDevices.size();
        }

        @Override
        public Object getItem(int position) {
            String[] ids = new String[BLEMap.size()];
            BLEMap.keySet().toArray(ids);
            return ids[position];
            //return mLeDevices.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = mInflator.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
//            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            //list_tag[listView.getCount()] = id;
            rfidTextView.setText(id);
            view.setBackgroundColor(Color.GREEN);
            return view;
        }

    }

    public BluetoothAdapter.LeScanCallback mLeScanCallback_InvPlan =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boolean flag_tagid_pos = false;
                            try {
                                flag_tagid_pos = device.getName().contains("HF13");
                            }catch (Exception e) { }

                            TAGID_Process(flag_tagid_pos, device.getAddress());
                            AddDevice(device);
                            //mLeDeviceListAdapter.notifyDataSetChanged();
                            //refreshLeDeviceList();
                        }
                    });
                }
            };

    public void AddDevice(BluetoothDevice device){
        mLeDeviceListAdapter.addDevice(device);
        mLeDeviceListAdapter.notifyDataSetChanged();
        listView_Update();
        refreshLeDeviceList();
    }

    public void listViewUpdateBluetoothLeGatt(String tag_inventario, String cod_posicao) {
        int position1 = -1;
        if (BLEMap.containsKey(tag_inventario)) {
            return;
        }

        String[] listItens = listViewUpdate(tag_inventario);
        String listItem1 = listItens[0];
        String listItem2 = listItens[2];
        try {
            position1 = Integer.parseInt(listItens[1]);
        } catch (Exception e) {}

        if (!listItem1.isEmpty() || !listItem2.isEmpty()){
            if (!tagid_contains(lista_tags_inventariados, tag_inventario)){

                BLEMap.put(tag_inventario, 1);
                tagid_inventario_list.add(tag_inventario);
                cod_posicao_list.add(cod_posicao);
                lista_tags_inventariados.add(tag_inventario);
                if (!listItem1.isEmpty()){
                    tagid_inventario_arraylist.add(listItem2);
                }

                listItem2 = new String();
                listItem1 = new String();

                if (position1 > -1 || sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
                    tagid_inventario_color.add(tag_inventario);
                }

                //Beep
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            }
        } else {
            listItem2 = new String();
            listItem1 = new String();
        }

        readCount();
    }

    public BluetoothAdapter DeviceScanActivity_ini () {
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, com.example.android.bluetoothlegatt.R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            return null;
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        //refreshLeDeviceList();
        return mBluetoothAdapter;
    }

    private void refreshLeDeviceList() {
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();

/*        listView.setAdapter(mLeDeviceListAdapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
        textView_qtd_inventario.setText(String.valueOf(listView.getAdapter().getCount()));*/
    }

    private void clearLeDeviceList() {
        if (mLeDeviceListAdapter != null) {
            BLEMap.clear();
            //mLeDeviceListAdapter.clear();
            tagid_inventario_list.clear();
            cod_posicao_list.clear();

            refreshLeDeviceList();
        }
    }

   private void BluetoothLeDisconnect() {
        mScanning = false;
        mBluetoothAdapter.stopLeScan(mLeScanCallback_InvPlan);
        //invalidateOptionsMenu();

        //Atualizando Botão Fab e Flag inventoring
        inventoring = !inventoring;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    // ================================= BluetoothLeGatt PROCESS ================================= //


    // ================================= XR400 PROCESS ================================= //
    private boolean isXR400Alive(){
        boolean isAlive = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress(pref.getString(key_ipxr400, "192.168.0.100"), 80);
            Socket sock = new Socket();

            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            isAlive = true;
            sock.close();
        } catch(IOException e) {
            String teste = e.getMessage();
        }

        return isAlive;
    }

    public void listViewUpdateXR400(String tag_inventario, String cod_posicao) {
        int position1 = -1;
        if (epc2num2.containsKey(tag_inventario)) {
            return;
        }

        String[] listItens = listViewUpdate(tag_inventario);
        String listItem1 = listItens[0];
        String listItem2 = listItens[2];
        try {
            position1 = Integer.parseInt(listItens[1]);
        } catch (Exception e) {
        }

        if (!listItem2.isEmpty() || !listItem1.isEmpty()){
            if (!tagid_contains(lista_tags_inventariados, tag_inventario)){

                epc2num.put(tag_inventario, 1);
                epc2num2.put(tag_inventario, 1);

                tagid_inventario_list.add(tag_inventario);
                cod_posicao_list.add(cod_posicao);
                lista_tags_inventariados.add(tag_inventario);

                if (!listItem2.isEmpty()){
                    tagid_inventario_arraylist.add(listItem2);
                }

                listItem2 = new String();
                listItem1 = new String();

                if (position1 > -1 || sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
                    tagid_inventario_color.add(tag_inventario);
                }
            }
        } else {
            listItem2 = new String();
            listItem1 = new String();
        }

        readCount = epc2num.size();
    }

    //runnable que roda de 1 em 1 segundo
    private void runtimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (inventoring && usingXR400){
                    if (getXML != null){
                        ArrayList<XR400ReadTags> newarray = getXML.ArraydeTags();
                        for (XR400ReadTags tag : newarray){
                            String tag_inventario = "H" + tag.getTagid();
                            if (!tagid_contains(lista_tags_inventariados, tag_inventario)){
                                listViewUpdateXR400(tag_inventario, sp_inventario_posicao.getSelectedItem().toString());
                            }
                        }
                        EventBus.getDefault().post(new Act_Inventario_Planejado.EpcInventoryEvent());
                    }

                    getXML = new RetrieveXMLResult(pref.getString(key_ipxr400, "192.168.0.100"));
                    getXML.execute();
                }
                handler.postDelayed(this, 2000);
            }
        });
    }
    // ================================= XR400 PROCESS ================================= //

    public String[] listViewUpdate(String tag_inventario) {
        ArrayAdapter<String> item1;
        String[] listItens = new String[3];
        String listItem = new String(); String listItem2 = new String();
        int position1 = -1;

        if(rb_equipmento.isChecked() == true)
        {
            try {
                query_InventarioEquipamento = new Query_InventarioEquipamento(db);
                item1 = query_InventarioEquipamento.InventarioEquipamentoTAGIDQuery(this,tag_inventario);
                if (item1.getCount() > 0){
                    listItem = item1.getItem(0) + " | " + item1.getItem(1) + " | " + item1.getItem(2);
                }
            }
            catch(SQLException ex){
            }

        } else if(rb_material.isChecked())
        {
            try {
                position1 = tagid_inventario_plan.getPosition(tag_inventario);

                if (position1 > -1){
                    listItem2 = String.valueOf(inventario_list_custom.getItem(position1).getTagid());
                }
                else if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Completo" || sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
                    try {
                        query_InventarioMaterial = new Query_InventarioMaterial(db);
                        item1 = query_InventarioMaterial.InventarioMaterialTAGIDQuery(this,tag_inventario);
                        if (item1.getCount() > 0){
                            listItem = item1.getItem(3);
                        }
                    }
                    catch(SQLException ex){
                        showMessage("AVISO", "Erro ao carregar registro de Inventário: " + ex.toString(), 2);
                    }
                }
            }
            catch(SQLException ex){
                showMessage("AVISO", "Erro ao carregar registro de Inventário: " + ex.toString(), 2);
            }
        }

        listItens[0] = listItem;
        listItens[1] = String.valueOf(position1);
        listItens[2] = String.valueOf(listItem2);
        return listItens;
    }

    public void listView_Update(){
        if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
            count = tagid_inventario_color.getCount();
            for (int i = 0; i < count; i++){
                String item1 = tagid_inventario_color.getItem(i);
                Cursor inventario1 = null;
                query_InventarioMaterial = new Query_InventarioMaterial(db);
                inventario1 = query_InventarioMaterial.InventarioMaterialTAGIDQuery3(item1);
                while (inventario1.moveToNext()){
                    String listitem1 = inventario1.getString(inventario1.getColumnIndex("Quantidade")) + " | " + inventario1.getString(inventario1.getColumnIndex("Material_Type")) + " | " + inventario1.getString(inventario1.getColumnIndex("ID_Omni")) + " | " + inventario1.getString(inventario1.getColumnIndex("lote_material")) + " | " + inventario1.getString(inventario1.getColumnIndex("TAGID_Material"));
                    TagIDInventario listitem2 = new TagIDInventario(listitem1, true);

                    inventario_list_custom.add(listitem2);
                    tagid_inventario_color.remove(item1);
                }
                listView.setAdapter(inventario_list_custom);
            }
            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));

        } else {
            if (!tagid_posicao_process.equals("0")) {
                if (flag_posicao_process == true) {
                    flag_posicao_process = false;

                    Carregar_Posicoes_TAGID(tagid_posicao_process);
                    tagid_posicao_process = "0";
                    fab.callOnClick();
                }
            }

            if (!tagid_inventario_color.isEmpty()) {
                count = tagid_inventario_color.getCount();
                for (int i = 0; i < count; i++) {
                    String item1 = tagid_inventario_color.getItem(i);
                    int position1 = tagid_inventario_plan.getPosition(item1);
                    if (position1 != -1){
                        if (listView.getChildAt(position1 - listView.getFirstVisiblePosition()) == null){
                            inventario_list_custom.getItem(position1).isTaged = true;
                        } else {
                            inventario_list_custom.getItem(position1).isTaged = true;
                            listView.getChildAt(position1 - listView.getFirstVisiblePosition()).setBackgroundColor(Color.GREEN);
                            tagid_inventario_color.remove(item1);
                        }
                    }
                }
            }

            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));
        }
    }

    public void TAGID_Process (boolean flag_tagid_pos, String tag_inventario){

        if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade"){
            if (flag_tagid_pos){
                if (tagposprox.containsKey(tag_inventario)){
                    int value = tagposprox.get(tag_inventario);
                    tagid_posicao_process = tag_inventario;
                    tagposprox.clear();
                    tagposprox.put(tag_inventario, value + 1);
                } else {
                    tagposprox.put(tag_inventario, 1);
                }
            } else {
                if (!tagid_posicao_process.equals("0")){
                    query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                    String cod_posicao1 = query_UPWEBPosicao.UPWEBTAGID_PosicaoQuery(tagid_posicao_process);
                    listViewReader_Process(tag_inventario, cod_posicao1);
                }
            }

        } else {
            if (flag_tagid_pos && ckb_tagid_pos.isChecked()){
                tagid_posicao_process = tag_inventario;

            } else if (!ckb_tagid_pos.isChecked()){
                listViewReader_Process(tag_inventario, sp_inventario_posicao.getSelectedItem().toString());
            }
        }
    }

    public void listViewReader_Process (String tag_inventario, String cod_posicao1){
        switch (key_modelo_leitor_rfid){
            case "Vanch_VH75":
                listViewUpdateVH75(tag_inventario, cod_posicao1);
                break;
            case "BluetoothLE":
                listViewUpdateBluetoothLeGatt(tag_inventario, cod_posicao1);
                break;
            default:
        }
    }

    public void InventarioPlanejado_Add(){
        ClearListViewOnSpinnerChanges();

        if(rb_equipmento.isChecked() == true) {
            showMessage("AVISO", "Esta funcionalidade NÃO está disponível para Equipamentos nesta Versão do Aplicativo.", 3);

        }else if(rb_material.isChecked()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                    int inventarioPlanejadoItemId = dbInstance.inventarioPlanejadoDAO().GetIdOriginalByDescricao(sp_inventario_planejado.getSelectedItem().toString());
                    //List<Integer> cadastroMateriaisItemIdList = dbInstance.listaMateriaisInventarioPlanejadoDAO().GetListaMateriaisByInventario(inventarioPlanejadoItemId);
                    List<Integer> cadastroMateriaisItemIdList = new ArrayList<Integer>();

                    lista_tags_inventariados = new ArrayList<String>();

                    for (Integer cadastroMateriaisItemId : cadastroMateriaisItemIdList)
                    {
                        InventarioPlanejado_Materiais materiais = dbInstance.cadastroMateriaisDAO().GetInventarioPlanejadoByIdOriginal(cadastroMateriaisItemId);
                        String item = materiais.Modelo + " | " + materiais.IDOmni + " | " + materiais.NumSerie + " | " + materiais.TAGID;
                        tagid_inventario_plan.add(materiais.TAGID);
                        TagIDInventario newTag = new TagIDInventario(item, false);

                        for (String itemInv : tagid_inventario_arraylist)
                        {
                            if (item.equals(itemInv))
                            {
                                newTag.isTaged = true;
                                lista_tags_inventariados.add(materiais.TAGID);
                            }
                        }
                        arrayOfTags.add(newTag);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inventario_list_custom.notifyDataSetChanged();
                            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));
                        }
                    });

                }
            }).start();

        }
    }

    private void Inventario_Posicao_Add(){
        ClearListViewOnSpinnerChanges();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (rb_equipmento.isChecked())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("AVISO", "Esta funcionalidade NÃO está disponível para Equipamentos nesta Versão do Aplicativo.", 3);
                        }
                    });
                }
                else if (rb_material.isChecked())
                {
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                    List<InventarioPlanejado_Materiais> cadastroMateriaisList = dbInstance.cadastroMateriaisDAO().GetByPosicaoOriginal(sp_inventario_posicao.getSelectedItem().toString());

                    lista_tags_inventariados = new ArrayList<String>();

                    for (InventarioPlanejado_Materiais materiais : cadastroMateriaisList)
                    {
                        String item = materiais.Modelo + " | " + materiais.IDOmni + " | " + materiais.NumSerie + " | " + materiais.TAGID;
                        tagid_inventario_plan.add(materiais.TAGID);
                        TagIDInventario newTag = new TagIDInventario(item, false);

                        for (String itemInv : tagid_inventario_arraylist)
                        {
                            if (item.equals(itemInv))
                            {
                                newTag.isTaged = true;
                                lista_tags_inventariados.add(materiais.TAGID);
                            }
                        }
                        arrayOfTags.add(newTag);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inventario_list_custom.notifyDataSetChanged();
                            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));
                        }
                    });
                }
            }
        }).start();
    }

    private void updateLang() {
        refreshLeDeviceList();
    }

    public void readCount() {
        readCount = epc2num.size() + BLEdeviceCount;
        //readCount = epc2num.size() + BLEdeviceCount + xr400deviceCount;
    }

    @Override
    public void onResume() {

        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void Carregar_Posicoes(){
        if (flag_posicao_process == true) {
            if (sp_inventario_almoxarifado.getSelectedItem() != null) {
                query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                query_upwebAlmoxarifado = new Query_UPWEBAlmoxarifado(db);
                String cod_almoxarifado1 = query_upwebAlmoxarifado.UPWEBCod_AlmoxarifadoQuery(sp_inventario_almoxarifado.getSelectedItem().toString());
                arraySpinner_posicao = query_UPWEBPosicao.UPWEBAlmoxarifado_PosicaoQuery2(Act_Inventario_Planejado.this, cod_almoxarifado1);
                sp_inventario_posicao.setAdapter(arraySpinner_posicao);
                Inventario_Posicao_Add();

                if (arraySpinner_posicao.isEmpty()){
                    showMessage("AVISO", "NÃO foram localizadas posições para o Almoxarifado " + sp_inventario_almoxarifado.getSelectedItem().toString() + ".", 3);
                }
            } else {
                showMessage("AVISO", "NÃO foi possível carregar nenhuma Posição. Almoxarifado NÃO selecionado.", 3);
                sp_inventario_posicao.setAdapter(null);
            }
        }
    }

    public void Carregar_Posicoes_TAGID(String tagid_posicao1){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!tagid_posicao1.equals("0") && flag_posicao_process == false){
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                    Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tagid_posicao1);

                    if (posicao != null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sp_inventario_almoxarifado.setSelection(2, false);
                                FillPosicaoSpinner();

                                sp_inventario_posicao.setSelection(adapterPosicoes.getPosition(posicao.getCodigo()), false);
                                Inventario_Posicao_Add();
                            }
                        });
                    }
                    else
                    {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 showMessage("AVISO", "NÃO foi localizada a Posição do TAGID " + tagid_posicao1 + ".", 3);
                             }
                         });
                    }
                }
            }
        }).start();

        /*if (!tagid_posicao1.equals("0") && flag_posicao_process == false){

            query_UPWEBPosicao = new Query_UPWEBPosicao(db);
            String codPosicao = query_UPWEBPosicao.UPWEBTAGID_PosicaoQuery(tagid_posicao1);
            String codAlmoxarifado = query_UPWEBPosicao.UPWEBTAGID_Cod_Almoxarifadoquery(tagid_posicao1);
            arraySpinner_posicao = query_UPWEBPosicao.UPWEBAlmoxarifado_PosicaoQuery2(Act_Inventario_Planejado.this, codAlmoxarifado);

            sp_inventario_posicao.setAdapter(arraySpinner_posicao);

            for (int i = 0; i < arraySpinner_posicao.getCount(); i++){
                String arraycodPosicao = arraySpinner_posicao.getItem(i).toString();
                if (arraycodPosicao.equals(codPosicao)){
                    sp_inventario_posicao.setSelection(i);
                }
            }

            Inventario_Posicao_Add();
            Carregar_Almoxarifado();

            if (arraySpinner_posicao.isEmpty()){
                sp_inventario_posicao.setAdapter(null);
                showMessage("AVISO", "NÃO foi localizada a Posição do TAGID " + tagid_posicao1 + ".", 3);
            }
        }*/
    }

    public void Carregar_Almoxarifado(){
        if (arraySpinner_almoxarifado == null){
            arraySpinner_almoxarifado = query_upwebAlmoxarifado.UPWEBAlmoxarifadoQuery(this);
            sp_inventario_almoxarifado.setAdapter(arraySpinner_almoxarifado);
        }

        if (sp_inventario_posicao.getSelectedItem() != null) {
            query_UPWEBPosicao = new Query_UPWEBPosicao(db);
            query_upwebAlmoxarifado = new Query_UPWEBAlmoxarifado(db);

            String cod_almoxarifado1 = query_UPWEBPosicao.UPWEBCod_Posicao_Cod_Almoxarifadoquery(sp_inventario_posicao.getSelectedItem().toString());
            String nome_almoxarifado1 = query_upwebAlmoxarifado.UPWEBNome_AlmoxarifadoQuery(cod_almoxarifado1);

            if (nome_almoxarifado1 != null) {
                sp_inventario_almoxarifado.setSelection(((ArrayAdapter<String>)sp_inventario_almoxarifado.getAdapter()).getPosition(nome_almoxarifado1));
            } else {
                arraySpinner_almoxarifado = null;
                sp_inventario_almoxarifado.setAdapter(arraySpinner_almoxarifado);
                showMessage("AVISO", "Almoxarifado NÃO localizado para Posição " + sp_inventario_posicao.getSelectedItem().toString() + ".", 3);
            }
        } else {
            arraySpinner_almoxarifado = null;
            sp_inventario_almoxarifado.setAdapter(arraySpinner_almoxarifado);
            showMessage("AVISO", "Posição e Almoxarifado NÃO IDENTIFICADOS.", 3);
        }
    }

    private void clearList() {
        clearVH75List();
        clearLeDeviceList();

        tagid_inventario_list.clear();
        tagid_inventario_color.clear();
        tagid_inventario_arraylist.clear();
        cod_posicao_list.clear();
        tagposprox.clear();
        tagid_inventario_plan.clear();
        arrayOfTags.clear();
        inventario_list_custom.notifyDataSetChanged();;
        lista_tags_inventariados = new ArrayList<String>();

        if (sp_inventario_processo.getSelectedItem().toString() == "Inventário Proximidade" || sp_inventario_processo.getSelectedItem().toString() == "Inventário Completo"){
            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()));
        } else {
            textView_qtd_inventario.setText(String.valueOf(lista_tags_inventariados.size()) + "/" + String.valueOf(listView.getAdapter().getCount()));
        }
    }

    public void showMessage(String title,String message,int type){
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = this;
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            BA.disable();
            BA.enable();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean tagid_contains(List<String> lista_tags_inventariados, String tagid){
        for (String item : lista_tags_inventariados) {
            if (item.equals(tagid)){
                return true;
            }
        }
        return false;
    }

    private void EsconderMostrarSpinnerPosicaoAlmoxarifado(boolean acao){
        ll_invplan.setVisibility(View.GONE);

        if (acao){
            lb_inventario_posicao.setVisibility(View.VISIBLE);
            sp_inventario_posicao.setVisibility(View.VISIBLE);

            lb_inventario_almoxarifado.setVisibility(View.VISIBLE);
            sp_inventario_almoxarifado.setVisibility(View.VISIBLE);

            // TAGID Posição atualmente APENAS para VH75
            if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
                ckb_tagid_pos.setVisibility(View.VISIBLE);
            }
        } else {
            lb_inventario_posicao.setVisibility(View.GONE);
            sp_inventario_posicao.setVisibility(View.GONE);

            lb_inventario_almoxarifado.setVisibility(View.GONE);
            sp_inventario_almoxarifado.setVisibility(View.GONE);

            ckb_tagid_pos.setVisibility(View.GONE);
        }
    }

    public void showEditTextmessage(String tagid){
        adbuilder = new AlertDialog.Builder(this);
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

    private void FillProcessoSpinner(){
        arraySpinner = new String[] {
                "Inventário Posição",
                "Inventário Planejado",
                "Inventário Completo",
                "Inventário Proximidade"
        };

        adapterProcesso = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinner);
        sp_inventario_processo.setAdapter(adapterProcesso);
    }

    private void FillAlmoxarifadoSpinner() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                String almoxarifadoPadrao = "Teste";

                alistAlmoxarifados = new ArrayList<String>();

                adapterAlmoxarifados = new ArrayAdapter<String>(Act_Inventario_Planejado.this, R.layout.spinner_item, alistAlmoxarifados);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sp_inventario_almoxarifado.setAdapter(adapterAlmoxarifados);
                        sp_inventario_almoxarifado.setSelection(adapterAlmoxarifados.getPosition(almoxarifadoPadrao));
                    }
                });
            }
        }).start();
    }

    private void FillPosicaoSpinner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                String almoxarifado = sp_inventario_almoxarifado.getSelectedItem().toString();

                alistPosicoes.clear();
                alistPosicoes.addAll(dbInstance.posicoesDAO().GetPosicoesByAlmoxarifado(2));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterPosicoes.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void FillInventarioPlanejadoSpiner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                arrayInventarioPlanejado.clear();
                arrayInventarioPlanejado.addAll(dbInstance.inventarioPlanejadoDAO().GetAllDescricoes());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterInventarioPlanejado.notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }
}
