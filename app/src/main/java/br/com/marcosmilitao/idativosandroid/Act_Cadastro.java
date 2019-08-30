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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.AsyncLayoutInflater;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTags;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_Parametros_Padrao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBUsuariosSet;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEB_ColetoresDados;
import br.com.marcosmilitao.idativosandroid.POJO.Cadastro_Tags;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

// deve implementar AdapterView.OnItemClickListener para voltar a usar onItemclickListener (implements AdapterView.OnItemClickListener)
public class Act_Cadastro extends AppCompatActivity {

    ListView listView;
    List<Epc> epcs = new ArrayList<Epc>();
    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> BLEMap = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> BLEMap2 = new ConcurrentHashMap<String, Integer>();

    EditText etv_cadastro_quantidade;
    EditText tv_cadastro_nlote_tracen;
    EditText tv_cadastro_part_number;
    Spinner sp_cadastro_posicao;
    Spinner sp_cadastro_categoria;
    CheckBox cb_cadastro_posicao;

    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;

    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();

    private String key_modelo_leitor_rfid = "Vanch_VH75";
    private String tagid_posicao_process = "0";
    private SharedPreferences pref;

    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Handler mHandler;
    public boolean mScanning;
    public static final long SCAN_PERIOD = 5000;
    private static final int REQUEST_ENABLE_BT = 1;

    private String[] arraySpinner_processo;
    private SpinnerAdapter posicao1;

    SQLiteDatabase db;
    //Teste ListView
    CheckBox cbx_editar_cadastro;
    //private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private Idativos02Data idativos02Data;
    private SQLiteDatabase connect;

    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_UPWEB_ColetoresDados query_upweb_coletoresDados;
    private Query_Parametros_Padrao query_parametros_padrao;
    private Query_UPWEBWorksheets query_UPWEBWorksheets;
    private Query_UPWEBUsuariosSet query_upwebUsuariosSet;

    ProgressDialog progressDialog;
    private boolean stoop = false, flag_posicao_process = true;
    int readCount = 0;
    boolean inventoring = false;
    public static final String TAG = "inventory";
    private HandsetParam param;
    private FloatingActionButton fab;
    private static final String cor = "";
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };
    private Handler readExceedHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Limite de leitura atingido", Toast.LENGTH_SHORT).show();
        }
    };
    private Handler dtHandler = new Handler(Looper.getMainLooper());
    private ContextMenu _menu;
    private Intent intent;

    private CustomAdapterCadastrarTags adapterCadastrarTags;
    private ArrayList<Cadastro_Tags> arrayCadastrarTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("CADASTRO");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();
            }
        });

        arrayCadastrarTags = new ArrayList<Cadastro_Tags>();
        adapterCadastrarTags = new CustomAdapterCadastrarTags(this, arrayCadastrarTags);
        listView = (ListView) findViewById(R.id.lv_cadastro);
        listView.setAdapter(adapterCadastrarTags);
        registerForContextMenu(listView);

        //idativos02Data = new Idativos02Data(this);
        //db = idativos02Data.getReadableDatabase();

        tv_cadastro_part_number = (EditText)  findViewById(R.id.tv_cadastro_part_number);
        etv_cadastro_quantidade = (EditText) findViewById(R.id.etv_cadastro_quantidade);
        etv_cadastro_quantidade.setText("1");
        tv_cadastro_nlote_tracen = (EditText) findViewById(R.id.tv_cadastro_nlote_tracen);
        sp_cadastro_posicao = (Spinner) findViewById(R.id.sp_cadastro_posicao);
        sp_cadastro_categoria = (Spinner) findViewById(R.id.sp_cadastro_categoria);
        cb_cadastro_posicao = (CheckBox) findViewById(R.id.cb_cadastro_posicao);

        //Preenchendo spinner de processos
        Spinner s_processo = (Spinner) findViewById(R.id.sp_cadastro_categoria);
        FillProcessoSpinner(s_processo);

        //Preenchendo spinner de posição
        Spinner s_posicao = (Spinner) findViewById(R.id.sp_cadastro_posicao);
        FillPosicaoSpinner(s_posicao);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        try {
            list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Carregando SharedPreferences MyPref
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        key_modelo_leitor_rfid = pref.getString("key_modelo_leitor_rfid","Vanch_VH75");
        if (key_modelo_leitor_rfid == "Vanch_VH75") {
            try {
                list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Carregando e preenchendo ação de click para o FloatingActionButton
        fab = (FloatingActionButton) findViewById(R.id.fab_lertag_cadastro);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
                    if(Act_Cadastro.currentDevice != null){

                        if(!inventoring){
                            inventoring = !inventoring;
                            readCount=0;
                            if (cb_cadastro_posicao.isChecked()){
                                flag_posicao_process = true;
                            }

                            clearList();
                            EventBus.getDefault().post(new InventoryEvent());

                            color = "#F30808";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }else {
                            inventoring = !inventoring;
                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    }else{
                        Utility.WarningAlertDialg(Act_Cadastro.this,"!","Leitor RFID Desconectado!").show();
                    }

                } else if (key_modelo_leitor_rfid.equals("BluetoothLE")) {
                    //boolean BluetoothLE = bluetoothLeGatt1.BluetoothLeGattIni();
                    DeviceScanActivity_ini ();
                    if (mBluetoothAdapter != null) {
                        if (!inventoring) {
                            inventoring = !inventoring;
                            readCount = 0;
                            clearLeDeviceList();
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
                        Utility.WarningAlertDialg(Act_Cadastro.this, "!", "Não foi possível inciar a conexão Bluetooth.").show();
                    }
                }
            }
        });

        cb_cadastro_posicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inventoring && cb_cadastro_posicao.isChecked()){
                    cb_cadastro_posicao.setSelected(true);
                }

                if (cb_cadastro_posicao.isChecked()){
                    Toast.makeText(Act_Cadastro.this, "Leitura de posição habilitada",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Act_Cadastro.this, "Leitura de posição desabilitada",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //updateLang();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private GoogleApiClient client;

    public Act_Cadastro() {
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Act_Cadastro Page") // TODO: Define a title for the content shown.
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

        close_at();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        _menu = menu;

        super.onCreateContextMenu(_menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        Cadastro_Tags tagid = adapterCadastrarTags.getItem(info.position);
        _menu.setHeaderTitle(tagid.getTagid());

        if(tagid.getDescricao().equals("novo"))
        {
            getMenuInflater().inflate(R.menu.menu_context_cadastro_novo, _menu);

        } else if (tagid.getDescricao().equals("material"))
        {
            getMenuInflater().inflate(R.menu.menu_context_cadastro_editar, _menu);

        } else if (tagid.getDescricao().equals("usuario")){

            showMessage("ATENÇÃO", "TAGID atualmente cadastrado para um usuário", 3);
        } else
        {
            showMessage("ATENÇÃO", "TAGID atualmente cadastrado para uma posição", 3);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String tagid = adapterCadastrarTags.getItem(info.position).getTagid();
        String idOriginal = adapterCadastrarTags.getItem(info.position).getIdOriginal();

        switch (item.getItemId()){
            case R.id.opt_cadastrar_ferramenta:

                intent = new Intent();
                intent.setClass(Act_Cadastro.this, Act_Cadastro_dados.class);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE, tagid);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE2, 0);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE3, false);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE4, sp_cadastro_categoria.getSelectedItem().toString());
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE5, sp_cadastro_posicao.getSelectedItem().toString());
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE6, etv_cadastro_quantidade.getText().toString());
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE7, tv_cadastro_part_number.getText().toString());
                startActivity(intent);

                break;

            case R.id.opt_cadastrar_usuario:

                CadastrarUsuario(info.position);
                break;

            case R.id.opt_editar_ferramenta:

                intent = new Intent();
                intent.setClass(Act_Cadastro.this, Act_Cadastro_dados.class);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE, tagid);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE2, Integer.valueOf(idOriginal));
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE3, true);
                intent.putExtra(Act_Cadastro_dados.EXTRA_MESSAGE4, sp_cadastro_categoria.getSelectedItem().toString());
                startActivity(intent);

                break;

            case R.id.opt_itens_contentor:

                intent = new Intent();
                intent.setClass(Act_Cadastro.this, Act_Consulta_ItensKit.class);
                intent.putExtra(Act_Consulta_ItensKit.EXTRA_MESSAGE, tagid);
                startActivity(intent);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cadastro,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cadastro_reconectar:
                list();

                return true;
            case R.id.action_consultar_materiais:
                BA.disable();
                BA.enable();
                Intent intent = new Intent();
                intent.setClass(this, act_consulta_material.class);
                startActivity(intent);

                return true;
            case R.id.action_cadastrar_ferramenta:
                Intent intentCadastrarFerramentas = new Intent();
                intentCadastrarFerramentas.setClass(this, CadastrarFerramentasActivity.class);
                startActivity(intentCadastrarFerramentas);

                return true;
            default:
                return false;
        }
    }

    // ================================= VH75 READER PROCESS ================================= //
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

    public static class UIEvent{
        private int position;

        public UIEvent(int position){
            this.position = position;
        }
    }

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
                VH73Device vh75Device = new VH73Device(Act_Cadastro.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {
                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Cadastro.this, currentDevice.getAddress());

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
                                ConfigUI.setConfigLastConnect(Act_Cadastro.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Cadastro.this, "Erro de Conexão" + currentDevice.getAddress());
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
            arrayCadastrarTags.clear();
            adapterCadastrarTags.notifyDataSetChanged();
        }
    }

    public void onEventBackgroundThread(InventoryEvent e) {
        int i = 0;

        try {
            Act_Cadastro.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_Cadastro.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message
                inventoring = false;
                EventBus.getDefault().post(new InventoryTerminal());
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
                if (lnew - lnow > 110) {
                    break;
                }
            }
        }

        EventBus.getDefault().post(new InventoryTerminal());

        try {
            Act_Cadastro.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Cadastro.currentDevice.getCmdResultWithTimeout(3000);
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

            Act_Cadastro.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_Cadastro.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            //EventBus.getDefault().post(new EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_Cadastro.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_Cadastro.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_Cadastro.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device.parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                //EventBus.getDefault().post(new EpcInventoryEvent());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        ArrayList<byte[]> epcs = list.epcs;
        for (byte[] bs : epcs) {
            String string ="H" + Utility.bytes2HexString(bs);
            String tag_posicao2 = string.substring(0,4);
            String  prefixo_pos = "HF13";
            query_UPWEBPosicao = new Query_UPWEBPosicao(db);

            Boolean flag_tagid_pos = (tag_posicao2.equals(prefixo_pos));

            TAGID_Process(flag_tagid_pos, string);

            if (!ConfigUI.getConfigSkipsame(Act_Cadastro.this)) {
                if (!epc2num.containsKey(string)) {
                    epc2num.put(string, 1);
                    AtualizarListView(string);
                }
            } else {
                if (!epc2num.containsKey(string))
                {
                    epc2num.put(string, 1);
                    AtualizarListView(string);
                }
            }

            readCount = epc2num.size();
        }

    }

    public void TAGID_Process (boolean flag_tagid_pos, String tag_inventario){
        if (flag_tagid_pos && cb_cadastro_posicao.isChecked()){
            tagid_posicao_process = tag_inventario;

            if (flag_posicao_process == true) {
                flag_posicao_process = false;

                Carregar_Posicoes_TAGID(tagid_posicao_process);
                tagid_posicao_process = "0";
                fab.callOnClick();
            }
        }
    }

    public void Carregar_Posicoes_TAGID(String tagid_posicao1){
        if (!tagid_posicao1.equals("0") && flag_posicao_process == false){
            query_UPWEBPosicao = new Query_UPWEBPosicao(db);
            String codPosicao = query_UPWEBPosicao.UPWEBTAGID_PosicaoQuery(tagid_posicao1);

            if (codPosicao.isEmpty()){
                return;
            } else {
                for (int i = 0; i < sp_cadastro_posicao.getCount(); i++){
                    String arraycodPosicao = sp_cadastro_posicao.getAdapter().getItem(i).toString();
                    if (arraycodPosicao.equals(codPosicao)){
                        EventBus.getDefault().post(new UIEvent(i));
                    }
                }
            }
        }
    }

    InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;


        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;

        }

        public void run() {
            try {
                Act_Cadastro.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_Cadastro.currentDevice.getCmdResult();
                Log.i(TAG, "read ok!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void freshStatus() {

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventMainThread(TimeoutEvent e) {
        progressDialog.dismiss();
        if (inventoring){
            fab.callOnClick();
        }
    }
    public void onEventMainThread(InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;
    }
    public void onEventMainThread(EpcInventoryEvent e) {
    }
    public void onEventMainThread(AccessUI.StatusChangeEvent e) {
        freshStatus();
    }
    public void onEventMainThread(ConfigUI.LangChanged e) {
        updateLang();
    }
    public void onEventMainThread(UIEvent e){
        sp_cadastro_posicao.setSelection(e.position);
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
            LayoutInflater inflater = Act_Cadastro.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            TextView rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
            //TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            //int count = epc2num.get(id);
            rfidTextView.setText(id);
            //countTextView.setText("Quantidade:" + count);

            TextView textViewNoTitle = (TextView) view.findViewById(R.id.txt_teste);
            //textViewNoTitle.setText("test test teste");
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

    public class LeDeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = Act_Cadastro.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
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
            TextView rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);

            String id = (String) getItem(position);
            rfidTextView.setText(id);

            return view;
        }

    }

    // Device scan callback.
    public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            String listItem = device.getAddress() + " | "+ (100 - rssi);
                            String tagid = device.getAddress();

                            if (!BLEMap2.containsKey(tagid)) {
                                BLEMap.put(listItem, 1);
                                BLEMap2.put(tagid, 1);

                                //Beep
                                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                            }
                            readCount = BLEMap.size();

                            mLeDeviceListAdapter.notifyDataSetChanged();
                            refreshLeDeviceList();
                        }
                    });
                }
            };

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
        mLeDeviceListAdapter = new Act_Cadastro.LeDeviceListAdapter();
        listView.setAdapter(mLeDeviceListAdapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
    }

    private void clearLeDeviceList() {
        if (mLeDeviceListAdapter != null) {
            BLEMap.clear(); BLEMap2.clear();
            mLeDeviceListAdapter.clear();
            refreshLeDeviceList();
        }
    }

    private void BluetoothLeDisconnect() {
        mScanning = false;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        invalidateOptionsMenu();

        //Atualizando Botão Fab e Flag inventoring
        inventoring = !inventoring;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    // ================================= BluetoothLeGatt PROCESS ================================= //

    private void updateLang() {

        refreshLeDeviceList();
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

    @Override
    public void onRestart(){
        clearList();
        list();

        super.onRestart();
    }

    public void showMessage(String title, String message, int type) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

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

    private void CadastrarFerramenta(int position) {
        String tagid = null;
        if (key_modelo_leitor_rfid.equals("BluetoothLE")){
            String listItem[] = listView.getItemAtPosition(position).toString().split(" | ");
            tagid = listItem[0];
        } else {
            tagid = listView.getItemAtPosition(position).toString();
        }

        String Quantidade = etv_cadastro_quantidade.getText().toString();
        String categoria = sp_cadastro_categoria.getSelectedItem().toString();
        String posicao = sp_cadastro_posicao.getSelectedItem().toString();
        String trace_number_lote_material = tv_cadastro_nlote_tracen.getText().toString();
        String Part_Number = tv_cadastro_part_number.getText().toString();

        Intent intent = new Intent();
        intent.setClass(this, Act_Cadastro_dados.class);

        if(posicao.isEmpty() || posicao == "Selecione uma posição"){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Entre com a Posição", 3);
                }
            });

        }else if(categoria.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Entre com a Categoria", 3);
                }
            });

        }else if(categoria != "Material Almoxarifado"){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Categoria " +categoria+" Não Disponível", 3);
                }
            });

        }else if(Quantidade.isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Entre com a Quantidade", 3);
                }
            });

        }else if(categoria != "Material Almoxarifado"){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Categoria " +categoria+" Não Disponível", 3);
                }
            });

        }else if(tv_cadastro_part_number.getText().toString().isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO", "Entre com o Número de Produto", 3);
                }
            });

        }else {
            intent.putExtra("tagid", tagid);
            intent.putExtra("flagEditarCadastro1", false);
            intent.putExtra("Quantidade", Quantidade);
            intent.putExtra("categoria", categoria);
            intent.putExtra("cod_posicao_original", posicao);
            intent.putExtra("trace_number_lote_material", trace_number_lote_material);
            intent.putExtra("Part_Number", Part_Number);
            intent.putExtra("FlagMobileUpdate", "false");
            intent.putExtra("FlagMobileInsert", "true");

            BA.disable();
            BA.enable();
            startActivity(intent);
        }
    }

    private void CadastrarUsuario(int position){
        String tagid = null;
        if (key_modelo_leitor_rfid.equals("BluetoothLE")){
            String listItem[] = listView.getItemAtPosition(position).toString().split(" | ");
            tagid = listItem[0];
        } else {
            tagid = listView.getItemAtPosition(position).toString();
        }

        Intent intent = new Intent();
        intent.setClass(this, Act_Cadastro_Usuarios.class);
        intent.putExtra("tagid", tagid);

        BA.disable();
        BA.enable();
        startActivity(intent);
    }

    public void EditarCadastro(int position){
        String tagid = listView.getItemAtPosition(position).toString();
        String Quantidade = etv_cadastro_quantidade.getText().toString();
        String categoria = sp_cadastro_categoria.getSelectedItem().toString();
        String posicao = sp_cadastro_posicao.getSelectedItem().toString();
        String trace_number_lote_material = tv_cadastro_nlote_tracen.getText().toString();
        String Part_Number = tv_cadastro_part_number.getText().toString();

        Intent intent = new Intent();
        intent.setClass(this, Act_Cadastro_dados.class);

        if(posicao.isEmpty() || posicao == "Selecione uma posição"){
            showMessage("AVISO", "Entre com a Posição", 3);
        } else {

            Cursor c_tagid = null;
            String Trace_Number;
            try {
                query_UPWEBWorksheets = new Query_UPWEBWorksheets(connect);
                c_tagid = query_UPWEBWorksheets.UPWEBWorksheetTAGIDQuery(tagid);
            } catch (Exception e) {
                showMessage("Editar Cadastro", e.toString(), 2);
            }
            if (c_tagid.getCount() > 0) {
                while (c_tagid.moveToNext()) {
                    /*categoria = c_tagid.getString(c_tagid.getColumnIndex("Categoria"));
                    if (!categoria.equals("Material Almoxarifado")) {
                        Trace_Number = c_tagid.getString(c_tagid.getColumnIndex("Trace_Number"));
                        intent.putExtra("Trace_Number", Trace_Number);
                    } else {
                        Trace_Number = c_tagid.getString(c_tagid.getColumnIndex("lote_material"));
                        intent.putExtra("trace_number_lote_material", Trace_Number);
                    }*/

                    intent.putExtra("cod_posicao_original", c_tagid.getString(c_tagid.getColumnIndex("Codigo")));
                    intent.putExtra("categoria", c_tagid.getString(c_tagid.getColumnIndex("Categoria")));
                    intent.putExtra("tagid", tagid);
                    intent.putExtra("Quantidade", c_tagid.getString(c_tagid.getColumnIndex("Quantidade")));
                    intent.putExtra("Part_Number", c_tagid.getString(c_tagid.getColumnIndex("PartNumber")));
                    intent.putExtra("PK_Serie", c_tagid.getString(c_tagid.getColumnIndex("NumSerie")));
                    intent.putExtra("PK_Lote", c_tagid.getString(c_tagid.getColumnIndex("Patrimonio")));
                    intent.putExtra("Dados_Tecnicos", c_tagid.getString(c_tagid.getColumnIndex("DadosTecnicos")));
                    intent.putExtra("num_NF", c_tagid.getString(c_tagid.getColumnIndex("NotaFiscal")));
                    intent.putExtra("Data_Validade", c_tagid.getString(c_tagid.getColumnIndex("DataValidade")));
                    intent.putExtra("flagEditarCadastro1", "true");
                    intent.putExtra("FlagMobileUpdate", "true");
                    intent.putExtra("FlagMobileInsert", "false");
                    intent.putExtra("Data_EntradaNF", c_tagid.getString(c_tagid.getColumnIndex("DataEntradaNotaFiscal")));
                    intent.putExtra("Valor_Unitario", c_tagid.getDouble(c_tagid.getColumnIndex("ValorUnitario")));

                    close_at();
                    //startActivity(intent);
                    BA.disable();
                    BA.enable();
                }
                startActivity(intent);
            } else {
                showMessage("AVISO", "Item Não Cadastrado", 3);
            }
        }

    }

    private void FillPosicaoSpinner(Spinner s_posicao) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                ArrayAdapter<String> posicaoAdapter = new ArrayAdapter<String>(Act_Cadastro.this, R.layout.spinner_item);
                String codigoAlmoxarifado = dbInstance.parametrosPadraoDAO().GetCodigoAlmoxarifado();

                for (String posicao : dbInstance.posicoesDAO().GetPosicoesByAlmoxarifado(codigoAlmoxarifado))
                {
                    posicaoAdapter.add(posicao);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        s_posicao.setAdapter(posicaoAdapter);
                    }
                });
            }
        }).start();
    }

    private void FillProcessoSpinner(Spinner s_processo) {
        arraySpinner_processo = new String[] {
                "Material Almoxarifado"
        };

        ArrayAdapter<String> adapter_processo = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinner_processo);
        s_processo.setAdapter(adapter_processo);
    }

    private void AtualizarListView(String tagid){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                CadastroMateriais material = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);
                Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tagid);

                Cadastro_Tags item = new Cadastro_Tags();
                item.setTagid(tagid);

                if (material != null)
                {
                    item.setDescricao("material");
                    item.setIdOriginal(String.valueOf(material.getIdOriginal()));

                } else if (usuario != null)
                {
                    item.setDescricao("usuario");
                    item.setIdOriginal(usuario.getIdOriginal());

                } else if (posicao != null)
                {
                    item.setDescricao("posicao");
                    item.setIdOriginal(String.valueOf(posicao.getIdOriginal()));

                } else
                {

                    item.setDescricao("novo");
                    item.setIdOriginal(String.valueOf(0));

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayCadastrarTags.add(item);
                        adapterCadastrarTags.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }
}
