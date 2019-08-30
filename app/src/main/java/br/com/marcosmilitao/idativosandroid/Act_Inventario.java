package br.com.marcosmilitao.idativosandroid;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioEquipamento;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_Parametros_Padrao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBAlmoxarifado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEB_ColetoresDados;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.RetrieveXMLResult;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import br.com.marcosmilitao.idativosandroid.helper.XR400ReadTags;
import de.greenrobot.event.EventBus;
import br.com.marcosmilitao.idativosandroid.ViewModel.TAGIDInventarioViewModel;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_Inventario extends AppCompatActivity {

    TextView rfidTextView,textView11;
    ListView listView;
    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    ListAdapter adapter2;
    private  int count=0;
    int i_test = 0;
    private Sync sync;
    RadioButton rb_equipmento, rb_material;
    List<Epc> epcs = new ArrayList<Epc>();
    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> epc2num2 = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> BLEMap = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> XR400Map = new ConcurrentHashMap<String, Integer>();

    TextView textView_qtd_inventario;

    private String[] _arrayProcessoSpiner;
    private ArrayAdapter<String> _adapterProcessoSpiner;

    private ArrayList<String> _arrayPosicaoSpiner;
    private ArrayAdapter<String> _adapterPosicaoSpiner;

    SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    public static VH73Device currentDevice;
    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Query_InventarioEquipamento query_InventarioEquipamento;
    private Query_UPWEB_ColetoresDados query_upweb_coletoresDados;
    private Query_Parametros_Padrao query_parametros_padrao;
    private Query_UPWEBAlmoxarifado query_upwebAlmoxarifado;

    Spinner sp_inventario_processo;
    Spinner sp_inventario_posicao;
    private SpinnerAdapter arraySpinner_posicao;
    private SpinnerAdapter arraySpinner_almoxarifado;
    private List<String> subject_list;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> tagid_inventario_list;
    private ArrayAdapter<String> cod_posicao_list;
    public static String tagId ="";
    ProgressDialog progressDialog;
    private AlertDialog.Builder adbuilder;
    private TextView et_modelo_in, et_produto_in, et_tracenumber_in, et_numserie_in, et_patrimonio_in, et_posicao_in, et_proprietario_in, et_datavalidade_in, et_tagid_in;
    private Query_UPWEBWorksheets query_upwebWorksheets;
    private AlertDialog alert;
    private ArrayList<TAGIDInventarioViewModel> tagidInventarioViewModels;
    private int readCount = 0;
    private int BLEdeviceCount = 0;

    boolean inventoring = false;
    public static final String TAG = "inventory";
    Button btn_inventarioPlan_add;

    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Handler mHandler;
    public boolean mScanning, usingXR400 = false;
    public static final String key_ipxr400 = "key_ipxr400";
    public static final long SCAN_PERIOD = 60000;
    private RetrieveXMLResult getXML;
    private static final int REQUEST_ENABLE_BT = 1;

    private SharedPreferences pref;

    private static final String cor = "";
    private GoogleApiClient client;
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Act_Inventario Page") // TODO: Define a title for the content shown.
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
        setContentView(R.layout.activity_act__inventario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();

            }
        });

        setTitle("INVENTÁRIO");

        tagid_inventario_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);        ;
        cod_posicao_list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        tagidInventarioViewModels = new ArrayList<TAGIDInventarioViewModel>();
        textView11 = (TextView) findViewById(R.id.textView11);

        btn_inventarioPlan_add = (Button) findViewById(R.id.btn_inventarioPlan_add);

        rb_equipmento = (RadioButton) findViewById(R.id.rd_inventario_equipamento);
        rb_material= (RadioButton) findViewById(R.id.rd_inventario_material);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        listView = (ListView) findViewById(R.id.lv_Inventario);

        textView_qtd_inventario = (TextView) findViewById(R.id.tv_inventario_qtd);

        sp_inventario_processo = (Spinner) findViewById(R.id.sp_inventario_processo);
        FillProcessoSpinner();

        sp_inventario_posicao = (Spinner) findViewById(R.id.sp_inventario_posicao);
        FillPosicaoSpinner();

        //Carregando SharedPreferences MyPref
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String key_modelo_leitor_rfid = pref.getString("key_modelo_leitor_rfid","Vanch_VH75");
        if (key_modelo_leitor_rfid == "Vanch_VH75") {
            try {
                list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {

                if (sp_inventario_posicao.getSelectedItem().toString().isEmpty() || sp_inventario_posicao.getSelectedItem() == "Selecione uma posição") {
                    showMessage("AVISO", "Selecione a Posição!", 3);
                    return;
                } else if (sp_inventario_processo.getSelectedItem().toString().isEmpty()) {
                    showMessage("AVISO", "Selecione o Processo!", 3);
                    return;
                }

                if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
                    // Conexão Leitor RFID Vanch VH75
                    list();
                    if (Act_Inventario.currentDevice != null) {

                        if (!inventoring) {
                            inventoring = !inventoring;
                            readCount = 0;
                            //clearList();
                            EventBus.getDefault().post(new Act_Inventario.InventoryEvent());
                            color = "#F30808";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        } else {
                            inventoring = !inventoring;
                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    } else {
                        Utility.WarningAlertDialg(Act_Inventario.this, "!", "Leitor RFID VH75 NÃO Conectado.").show();
                    }
                } else if (key_modelo_leitor_rfid.equals("BluetoothLE")) {
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
                        Utility.WarningAlertDialg(Act_Inventario.this, "!", "Não foi possível inciar a conexão Bluetooth.").show();
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
                    Utility.WarningAlertDialg(Act_Inventario.this, "!", "Leitor " + key_modelo_leitor_rfid + " Não Habilitado neste dispositivo.").show();
                }
            }
        });

        rb_material.setOnClickListener(new View.OnClickListener() {
            String color;

            @Override
            public void onClick(View view) {
                limparInventario();
            }
        });

        rb_equipmento.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                limparInventario();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String description = listView.getItemAtPosition(position).toString();
                String tagid = new String();

                for (TAGIDInventarioViewModel tagidinventario : tagidInventarioViewModels) {
                    if (tagidinventario.getDescription().equals(description)){
                        tagid = tagidinventario.getTAGID();
                        break;
                    }
                }
                
                showEditTextmessage(tagid);
                return false;
            }
        });

        textView11.setVisibility(View.GONE);
        btn_inventarioPlan_add.setVisibility(View.GONE);
        updateLang();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        runtimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_inventario,menu);
        return true;
    }

    private void close_at(){

        BA.disable();
        BA.enable();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.inventarioAtualizar:

                if(sp_inventario_posicao.getSelectedItem() == "Selecione uma posição"){
                    showMessage("AVISO", "Selecione a Posição!", 3);
                    return false;
                }else if(sp_inventario_processo.getSelectedItem() == null){
                    showMessage("AVISO", "Selecione o Processo !", 3);
                    return false;
                }else if(listView.getAdapter().getCount() == 0){
                    showMessage("AVISO", "Identifique pelo menos um material ou equipamento antes de atualizar!", 3);
                    return false;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                        count = tagid_inventario_list.getCount();

                        if (rb_equipmento.isChecked())
                        {
                            for (int i = 0; i < count; i++)
                            {
                                CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByTAGID(tagid_inventario_list.getItem(i));
                                Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(cod_posicao_list.getItem(i));

                                if (cadastroEquipamentos != null && posicao != null)
                                {
                                    UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao = new UPMOBHistoricoLocalizacao();
                                    upmobHistoricoLocalizacao.setCodColetor(Build.SERIAL);
                                    upmobHistoricoLocalizacao.setDataHoraEvento(Calendar.getInstance().getTime());
                                    upmobHistoricoLocalizacao.setDescricaoErro("");
                                    upmobHistoricoLocalizacao.setDominio("MOBILE");
                                    upmobHistoricoLocalizacao.setFlagAtualizar(false);
                                    upmobHistoricoLocalizacao.setFlagErro(false);
                                    upmobHistoricoLocalizacao.setFlagProcess(false);
                                    upmobHistoricoLocalizacao.setModalidade("Equipamento");
                                    upmobHistoricoLocalizacao.setQuantidade(1);
                                    upmobHistoricoLocalizacao.setProcesso(sp_inventario_processo.getSelectedItem().toString());
                                    upmobHistoricoLocalizacao.setTAGID(cadastroEquipamentos.getTAGID());
                                    upmobHistoricoLocalizacao.setTAGIDPosicao(posicao.getTAGID());

                                    dbInstance.upmobHistoricoLocalizacaoDAO().Create(upmobHistoricoLocalizacao);
                                }
                                else
                                {
                                    return;
                                }
                            }
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
                                    upmobHistoricoLocalizacao.setDominio("MOBILE");
                                    upmobHistoricoLocalizacao.setFlagAtualizar(false);
                                    upmobHistoricoLocalizacao.setFlagErro(false);
                                    upmobHistoricoLocalizacao.setFlagProcess(false);
                                    upmobHistoricoLocalizacao.setModalidade("Material");
                                    upmobHistoricoLocalizacao.setQuantidade(cadastroMateriais.getQuantidade());
                                    upmobHistoricoLocalizacao.setProcesso(sp_inventario_processo.getSelectedItem().toString());
                                    upmobHistoricoLocalizacao.setTAGID(cadastroMateriais.getTAGID());
                                    upmobHistoricoLocalizacao.setTAGIDPosicao(posicao.getTAGID());

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



                /*if(rb_equipmento.isChecked() == true)
                {
                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setTitle("Aguarde !");
                    pd.setMessage("ATUALIZANDO INVENTÁRIO ...");
                    pd.setIndeterminate(true);
                    pd.setCancelable(false);
                    pd.show();

                    final SQLiteDatabase db2 = db;
                    final Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            count = listView.getAdapter().getCount();
                            for (int i = 0; i < count; i++) {

                                String dataHoraLocalizacao = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(Calendar.getInstance().getTime());
                                String listItem1 = listView.getAdapter().getItem(i).toString();
                                String TAGID_equipment = listItem1.substring(listItem1.lastIndexOf("|") + 1);
                                // String TAGID_equipment = tagid_inventario_list.getItem(i);
                                TAGID_equipment = TAGID_equipment.split(" ")[1];

                                String cod_posicao1 = cod_posicao_list.getItem(i);
                                String tagid_posicao1 = new String();

                                try {
                                    query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                                    tagid_posicao1 = query_UPWEBPosicao.UPWEBCod_PosicaoQuery(cod_posicao1);
                                } catch (SQLException ex) {
                                    showMessage("AVISO","Erro ao Identificar a Posição do Equipamento.", 2);

                                }
                                Cursor c_TAGID = db.rawQuery("SELECT * FROM InventarioEquipamento WHERE TAGID_Equipment='" + TAGID_equipment +"'"+ "LIMIT 1",null);
                                while (c_TAGID.moveToNext()) {

                                    try {
                                        db.execSQL("INSERT INTO UPMOBHistoricoLocalizacao VALUES('" + c_TAGID.getString(c_TAGID.getColumnIndex("Equipment_Type")).replace("'", "''") + "', '" + c_TAGID.getString(c_TAGID.getColumnIndex("Trace_Number")) + "','" + "Equipamento" + "','" + TAGID_equipment + "','" + tagid_posicao1 + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + dataHoraLocalizacao + "','" + "" + "','" + sp_inventario_processo.getSelectedItem() + "','" + "MOBILE" + "','" + "" + "','" + 1 + "');");

                                    } catch (final SQLException e) {

                                        pd.dismiss();
                                        Handler mHandler = new Handler(Looper.getMainLooper());
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                showMessage("Error ao atualizar inventário de materiais. ", e.toString(), 2);
                                            }
                                        });

                                    }
                                }
                            }
                        }
                    };
                    pd.dismiss();
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("AVISO", "Inventário atualizado com sucesso", 1);
                        }
                    });
                    mThread.start();
                }

                else if(rb_material.isChecked())
                {
                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setTitle("Aguarde !");
                    pd.setMessage("ATUALIZANDO INVENTÁRIO ...");
                    pd.setIndeterminate(true);
                    pd.setCancelable(false);
                    pd.show();
                    final SQLiteDatabase db2 = db;

                    final Thread mThread = new Thread() {
                        @Override
                        public void run() {


                            count = listView.getAdapter().getCount();
                            for(int i=0;i<count;i++) {
                                String dataHoraLocalizacao = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(Calendar.getInstance().getTime());
                                String listItem1 = listView.getAdapter().getItem(i).toString();
                                String TAGID_material = tagid_inventario_list.getItem(i);
                                //TAGID_material = TAGID_material.split(" ")[1];

                                String cod_posicao1 = cod_posicao_list.getItem(i);
                                //cod_posicao1 = cod_posicao1.split(" ")[1];
                                String tagid_posicao1 = new String();

                                try {
                                    query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                                    tagid_posicao1 = query_UPWEBPosicao.UPWEBCod_PosicaoQuery(cod_posicao1);
                                }
                                catch(final SQLException ex){

                                    pd.dismiss();
                                    Handler mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(Act_Inventario.this);
                                            dlg.setMessage("Erro ao Identificar a Posição do Material. " + ex.getMessage());
                                            dlg.setNeutralButton("OK", null);
                                            dlg.show();
                                        }
                                    });
                                }

                                Cursor c_TAGID = db.rawQuery("SELECT * FROM InventarioMaterial WHERE TAGID_Material='" + TAGID_material +"'"+ "LIMIT 1",null);
                                while(c_TAGID.moveToNext()){

                                    try {
                                        db.execSQL("INSERT INTO UPMOBHistoricoLocalizacao VALUES('" +c_TAGID.getString(c_TAGID.getColumnIndex("Material_Type")).replace("'","''")+"', '" +c_TAGID.getString(c_TAGID.getColumnIndex("lote_material"))+"','" +"Materiais"+"','" + TAGID_material+"','" + tagid_posicao1 +"','" +"" +"','" + ""+"','" +"" +"','" +"" +"','" +"" +"','" + dataHoraLocalizacao +"','" + ""+"','" +sp_inventario_processo.getSelectedItem() +"','" + "MOBILE"+"','" +"" +"','" + c_TAGID.getString(c_TAGID.getColumnIndex("Quantidade"))+"');");

                                    } catch (final SQLException e) {

                                        pd.dismiss();
                                        Handler mHandler = new Handler(Looper.getMainLooper());
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                showMessage("Error ao atualizar inventário de materiais. ", e.toString(), 2);
                                            }
                                        });
                                    }
                                }
                            }

                        }
                    };


                    pd.dismiss();
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("AVISO", "Inventário atualizado com sucesso", 1);
                        }
                    });
                    mThread.start();
                }*/
                return true;
            case R.id.inventario_limpar:
                clearList();
                clearLeDeviceList();
                listView.setAdapter(null);
                readCount = 0;

                return true;
            case R.id.inventario_conexao:
                list();
                return true;
            case R.id.inventarioPlan:
                //showMessage("Aviso!","Esta funcionalidade não está disponível nesta Versão.");
                BA.disable();
                BA.enable();
                Intent intent = new Intent(this,Act_Inventario_Planejado.class);
                startActivity(intent);

                return true;
            case R.id.inventario_sync:
                //Nova Chamada para Sincronismo
                ESync.GetSyncInstance().SyncDatabase(Act_Inventario.this);
                return true;
            default:
                return false;
        }
    }

    public void list() {
        queryPairedDevices();
        pairedDevices = BA.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
            connect(bt);
        }
    }

    // ================================= VH75 READER PROCESS ================================= //

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(Act_Inventario.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Inventario.this, currentDevice.getAddress());

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
                                ConfigUI.setConfigLastConnect(Act_Inventario.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Inventario.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }


            }
        }

    }

    private void clearList() {
        if (epc2num != null && epc2num.size() > 0) {
            epc2num.clear();
            epc2num2.clear();
            tagid_inventario_list.clear();
            cod_posicao_list.clear();
            tagidInventarioViewModels.clear();
            refreshList();
        }
    }

    public void onEventBackgroundThread(Act_Inventario.InventoryEvent e) {
        int i = 0;
        int timeout = 500;
        try {
            Act_Inventario.currentDevice.SetReaderMode((byte) 1);


            byte[] res = Act_Inventario.currentDevice.getCmdResultWithTimeout(timeout);

            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_Inventario.InventoryTerminal());
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
        EventBus.getDefault().post(new Act_Inventario.InventoryTerminal());

        try {
            MainActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Inventario.currentDevice.getCmdResultWithTimeout(timeout);
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

    private void doInventory() {
        try {
            Act_Inventario.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_Inventario.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            EventBus.getDefault().post(new Act_Inventario.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_Inventario.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_Inventario.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_Inventario.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);

                addEpc(listTagIDResultLeft);

                EventBus.getDefault().post(new Act_Inventario.EpcInventoryEvent());
            }
            // EventBus.getDefault().post(new InventoryTerminal());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onEventMainThread(Act_Inventario.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;//
//        btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
//        btnInventory.setText(Strings.getString(R.string.inventory));
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        String tagid_posicao1 = new String();
        ArrayList<byte[]> epcs = list.epcs;

        for (byte[] bs : epcs) {
            String tag_inventario = "H" + Utility.bytes2HexString(bs);
            String tag_posicao2 = tag_inventario.substring(0, 5);

            if (tag_posicao2 == "HF13"){
                if (tagid_posicao1 != tag_inventario){
                    query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                    String cod_posicao2 = query_UPWEBPosicao.UPWEBTAGID_PosicaoQuery(tag_inventario);
                    if (cod_posicao2 != null){
                        try {
                            sp_inventario_posicao.setSelection(((ArrayAdapter<String>)sp_inventario_posicao.getAdapter()).getPosition(cod_posicao2));
                            tagid_posicao1 = tag_inventario;
                        } catch (Exception e) {
                        }
                    }
                }
            }
            else {
                listViewUpdateVH75(tag_inventario, sp_inventario_posicao.getSelectedItem().toString());
            }
        }
    }

    private void listViewUpdateVH75(String tag_inventario, String cod_posicao) {
        if (epc2num2.containsKey(tag_inventario)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                String listViewItem = new String();

                if (rb_equipmento.isChecked())
                {
                    CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByTAGID(tag_inventario);

                    if (cadastroEquipamentos != null)
                    {
                        ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(cadastroEquipamentos.getModeloEquipamentoItemIdOriginal());

                        listViewItem = cadastroEquipamentos.getTraceNumber() + " | " + modeloEquipamentos.getModelo() + " | " + cadastroEquipamentos.getTAGID();
                    }
                }
                else if (rb_material.isChecked())
                {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tag_inventario, true);

                    if (cadastroMateriais != null)
                    {
                        ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                        listViewItem = modeloMateriais.getModelo() + " | " + modeloMateriais.getIDOmni() + " | " + modeloMateriais.getPartNumber() + " | " + cadastroMateriais.getTAGID();
                    }
                }

                if (!listViewItem.isEmpty())
                {
                    epc2num.put(listViewItem, 1);
                    epc2num2.put(tag_inventario, 1);
                    tagid_inventario_list.add(tag_inventario);
                    cod_posicao_list.add(cod_posicao);
                    tagidInventarioViewModels.add(new TAGIDInventarioViewModel(tag_inventario, listViewItem));
                }

                readCount();
            }
        }).start();
    }

    public void onEventMainThread(Act_Inventario.EpcInventoryEvent e) {
        refreshList();
    }

    Act_Inventario.InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;


        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;

        }

        public void run() {
            try {
                Act_Inventario.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_Inventario.currentDevice.getCmdResult();
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
    public void onEventMainThread(Act_Inventario.TimeoutEvent e) {
        progressDialog.dismiss();
        inventoring = false;
        EventBus.getDefault().post(new Act_Inventario.InventoryTerminal());
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
            LayoutInflater inflater = Act_Inventario.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);

            String id = (String) getItem(position);
            rfidTextView.setText(id);

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

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();

        //LeDeviceListAdapter leDeviceListAdapter1 = new LeDeviceListAdapter();
        //return LeDeviceListAdapter().mLeDevices;
    }

    // Adapter for holding devices found through scanning.
    public class LeDeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = Act_Inventario.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                listViewUpdateBluetoothLeGatt(device.toString(), sp_inventario_posicao.getSelectedItem().toString());
                BLEdeviceCount = mLeDevices.size();
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

            String id = (String) getItem(position);
            //list_tag[listView.getCount()] = id;
            rfidTextView.setText(id);

            return view;
        }

    }

    // Device scan callback.
    public BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                            refreshLeDeviceList();
                        }
                    });
                }
            };

    public void listViewUpdateBluetoothLeGatt(String tag_inventario, String cod_posicao) {
            if (BLEMap.containsKey(tag_inventario)) {
                return;
            }

        String listItem = listViewUpdate(tag_inventario, cod_posicao);

        //if (!ConfigUI.getConfigSkipsame(this) && listItem.length() > 1) {
        if (listItem.length() > 1) {
            BLEMap.put(listItem, 1);
            tagid_inventario_list.add(tag_inventario);
            cod_posicao_list.add(cod_posicao);

            //Beep
            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

            listItem = new String();
        } else {
            listItem = new String();
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
        refreshLeDeviceList();
        return mBluetoothAdapter;
    }

    private void refreshLeDeviceList() {
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        listView.setAdapter(mLeDeviceListAdapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
        textView_qtd_inventario.setText(String.valueOf(listView.getAdapter().getCount()));
    }

    private void clearLeDeviceList() {
        if (mLeDeviceListAdapter != null) {
            BLEMap.clear();
            mLeDeviceListAdapter.clear();
            tagid_inventario_list.clear();
            tagidInventarioViewModels.clear();
            cod_posicao_list.clear();
            refreshLeDeviceList();
        }
    }

    private void BluetoothLeDisconnect() {
        mScanning = false;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        invalidateOptionsMenu();

        //Atualizando Botão Fab e Flag inventoring
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        if (epc2num2.containsKey(tag_inventario)) {
            return;
        }

        String listItem = listViewUpdate(tag_inventario, cod_posicao);

        if (listItem.length() > 1) {
            epc2num.put(listItem, 1);
            epc2num2.put(tag_inventario, 1);
            tagid_inventario_list.add(tag_inventario);
            cod_posicao_list.add(cod_posicao);
            tagidInventarioViewModels.add(new TAGIDInventarioViewModel(tag_inventario, listItem));
            
            listItem = new String();
        } else {
            listItem = new String();
        }
        readCount();
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
                            listViewUpdateXR400(tag_inventario, sp_inventario_posicao.getSelectedItem().toString());
                        }
                        EventBus.getDefault().post(new Act_Inventario.EpcInventoryEvent());
                    }

                    getXML = new RetrieveXMLResult(pref.getString(key_ipxr400, "192.168.0.100"));
                    getXML.execute();
                }
                handler.postDelayed(this, 2000);
            }
        });
    }

    public String listViewUpdate(String tag_inventario, String cod_posicao) {
        ArrayAdapter<String> item1;
        String listItem = new String();

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

        }else if(rb_material.isChecked())
        {
            try {
                query_InventarioMaterial = new Query_InventarioMaterial(db);
                item1 = query_InventarioMaterial.InventarioMaterialTAGIDQuery(this,tag_inventario);
                if (item1.getCount() > 0){
                    listItem = item1.getItem(0) + " | " + item1.getItem(1) + " | " + item1.getItem(4) + " | " +item1.getItem(2)+  " | " + cod_posicao +  " | " + tag_inventario ;
                }
            }
            catch(SQLException ex){
            }
        }

        return listItem;
    }

    public void limparInventario(){
        listView.setAdapter(null);
        clearList();
        clearLeDeviceList();
    }

    public void readCount() {
        readCount = epc2num.size() + BLEdeviceCount;
    }

    private void updateLang() {
        //btnInventory.setText(Strings.getString(R.string.inventory));
        //btnSave.setText(Strings.getString(R.string.save));
        refreshList();
        //refreshLeDeviceList();
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

    public boolean tagid_contains(List<String> lista_tags_inventariados, String tagid){
        for (String item : lista_tags_inventariados) {
            if (item.equals(tagid)){
                return true;
            }
        }
        return false;
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

    public void showEditTextmessage(String tagid){
        adbuilder = new AlertDialog.Builder(this);
        adbuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View edittextLayout = inflaterEt.inflate(R.layout.tagiddetails_alertdialog_layout, null);
        adbuilder.setView(edittextLayout);

        et_proprietario_in = (TextView) edittextLayout.findViewById(R.id.et_proprietario_in);
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

    private void FillPosicaoSpinner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                String codigoAlmoxarifado = dbInstance.parametrosPadraoDAO().GetCodigoAlmoxarifado();

                _arrayPosicaoSpiner = new ArrayList<String>();
                _arrayPosicaoSpiner.addAll(dbInstance.posicoesDAO().GetPosicoesByAlmoxarifado(codigoAlmoxarifado));

                _adapterPosicaoSpiner = new ArrayAdapter<String>(Act_Inventario.this, R.layout.spinner_item, _arrayPosicaoSpiner);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sp_inventario_posicao.setAdapter(_adapterPosicaoSpiner);
                    }
                });
            }
        }).start();
    }

    private void FillProcessoSpinner(){
        _arrayProcessoSpiner = new String[] {
                "Inventario",
                "Entrada",
                "Saida"
        };

        _adapterProcessoSpiner = new ArrayAdapter<String>(this,
                R.layout.spinner_item, _arrayProcessoSpiner);
        sp_inventario_processo.setAdapter(_adapterProcessoSpiner);
    }
}