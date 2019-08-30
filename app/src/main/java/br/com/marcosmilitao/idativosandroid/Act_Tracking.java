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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_Parametros_Padrao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEB_ColetoresDados;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.RetrieveXMLResult;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import br.com.marcosmilitao.idativosandroid.helper.XR400ReadTags;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_Tracking extends AppCompatActivity {
    TextView rfidTextView;
    TextView txt_count_item;
    ListView listView;
    ListView lv_tracking_inv;
    private Query_UPWEB_ColetoresDados query_upweb_coletoresDados;
    private Sync sync;
    ArrayList<String> myStringArray1 = new ArrayList<String>();
    int i_test = 0;

    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
    Map<String, Integer> BLEMap = new ConcurrentHashMap<String, Integer>();

    EditText edt_nLoteTracking;
    private String[] arraySpinner;
    private Idativos02Data idativos02Data;
    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_Parametros_Padrao query_parametros_padrao;
    private SpinnerAdapter arraySpinner_posicao;
    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();

    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;

    private String key_modelo_leitor_rfid = "Vanch_VH75";
    public static final String key_ipxr400 = "key_ipxr400";
    private RetrieveXMLResult getXML;
    private SharedPreferences pref;

    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Handler mHandler;
    public boolean mScanning, usingXR400 = false;
    public static final long SCAN_PERIOD = 60000;
    private static final int REQUEST_ENABLE_BT = 1;

    SQLiteDatabase db;
    Spinner sp_tracking_processo;
    Spinner sp_tracking_posicao;
    Button btn_tracking_addItem;
    private ListView mainListView ;
    private FloatingActionButton fab;
    private HandsetParam param;
    private ArrayList<String> _tagidList;
    private ArrayAdapter<String> _listAdapter;

    ProgressDialog progressDialog;

    int readCount = 0;
    boolean inventoring = false;
    public static final String TAG = "inventory";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__tracking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();

            }
        });

        _tagidList = new ArrayList<String>();
        _listAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, _tagidList);

        listView = (ListView) findViewById(R.id.lv_tracking);
        edt_nLoteTracking = (EditText) findViewById(R.id.edt_nLoteTracking);

        lv_tracking_inv = (ListView) findViewById(R.id.lv_tracking_inv);
        lv_tracking_inv.setAdapter(_listAdapter);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();
        list();

        sp_tracking_processo = (Spinner) findViewById(R.id.sp_tracking_processo);
        FillProcessoSpinner();

        sp_tracking_posicao = (Spinner) findViewById(R.id.sp_tracking_posicao);
        FillPosicaoSpinner();

        btn_tracking_addItem = (Button) findViewById(R.id.btn_tracking_addItem);
        btn_tracking_addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String filter = "%" + edt_nLoteTracking.getText().toString() + "%";

                if (filter.isEmpty() || filter.equals("%%"))
                {
                    showMessage("AVISO", "Digite o TAGID ou N° do Produto", 3);
                    return;
                }

                SearchByFilter(filter);
            }
        });

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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                boolean flag_lv = false;
                try {
                    flag_lv = !lv_tracking_inv.getAdapter().isEmpty();
                } catch (Exception e) { }
                if (flag_lv == false) {
                    showMessage("AVISO", "Adicione pelo menos UM item na Lista!", 3);
                    return;
                }

                if (key_modelo_leitor_rfid.equals("Vanch_VH75")) {
                    if(Act_Tracking.currentDevice != null){
                        list();
                        if(!inventoring){
                            inventoring = !inventoring;
                            readCount=0;
                            clearList();
                            EventBus.getDefault().post(new Act_Tracking.InventoryEvent());
                            color = "#F30808";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }else {
                            inventoring = !inventoring;


                            color = "#41C05A";
                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                        }
                    }else{
                        showMessage("ATENÇÃO", "Leitor RFID não conectado", 3 );
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
                        Utility.WarningAlertDialg(Act_Tracking.this, "!", "Não foi possível inciar a conexão Bluetooth.").show();
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
                    Utility.WarningAlertDialg(Act_Tracking.this, "!", "Leitor " + key_modelo_leitor_rfid + " Não Habilitado neste dispositivo.").show();
                }
            }
        });
        updateLang();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        runtimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_tracking,menu);
        return true;



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        int count;
        switch (id) {
            case R.id.inventarioAtualizarTracking:

//                if(rb_equipmento.isChecked() == true)
//                {
//                    count = listView.getAdapter().getCount();
//                    for(int i=0;i<count;i++) {
//                        String dataHoraLocalizacao = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//
//                        Cursor c_TAGID =db.rawQuery("SELECT TOP 1 * FROM InventarioEquipamento WHERE TAGID_Equipment=" + listView.getAdapter().getItem(i).toString(),null);
//                        while(c_TAGID.moveToNext()){
//
//                            Cursor c_posicao =db.rawQuery("SELECT TOP 1 * FROM UPMOBPosicao WHERE Desc_Posicao=" + sp_inventario_posicao.getSelectedItem(),null);
//                            while (c_posicao.moveToNext()){
//                                try {
//                                    db.execSQL("INSERT INTO UPMOBHistoricoLocalizacaos VALUES('" +c_TAGID.getString(c_TAGID.getColumnIndex("Equipment_Type"))+"', '" +c_TAGID.getString(c_TAGID.getColumnIndex("Trace_Number"))+"','" +"Equipamento"+"','" + listView.getAdapter().getItem(i).toString()+"','" + c_posicao.getString(c_posicao.getColumnIndex("TAGID_Posicao"))+"','" +"" +"','" + ""+"','" +"" +"','" +"" +"','" +"" +"','" + dataHoraLocalizacao +"','" + ""+"','" +sp_inventario_processo.getSelectedItem() +"','" + "MOBILE"+"','" +"" +"','" + 1+"');");
//                                } catch (SQLException e) {
//                                    showMessage("Error", e.toString());
//                                }
//                            }
//                        }
//                        //showMessage("Success", listView.getAdapter().getItem(i).toString());
//                        //Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" +  + "'", null);
//
//                    }
//
//                    showMessage("Alerta!!","Equipamento  ");
//                }else if(rb_material.isChecked())
//                {
//
                count = listView.getAdapter().getCount();
                for(int i=0;i<count;i++) {
                    String dataHoraLocalizacao = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(Calendar.getInstance().getTime());

                    String TAGID_material = listView.getAdapter().getItem(i).toString();

                    Cursor c_TAGID = db.rawQuery("SELECT * FROM InventarioMaterial WHERE TAGID_Material='" + TAGID_material +"'"+ "LIMIT 1",null);
                    while(c_TAGID.moveToNext()){
                        String posicao_material = sp_tracking_posicao.getSelectedItem().toString();
                        Cursor c_posicao =db.rawQuery("SELECT * FROM UPMOBPosicao WHERE Desc_Posicao='" + posicao_material+"'"+ "LIMIT 1",null);
                        while (c_posicao.moveToNext()){
                            try {

                                db.execSQL("INSERT INTO UPMOBHistoricoLocalizacao VALUES('" +c_TAGID.getString(c_TAGID.getColumnIndex("Material_Type"))+"', '" +c_TAGID.getString(c_TAGID.getColumnIndex("lote_material"))+"','" +"Materiais"+"','" + listView.getAdapter().getItem(i).toString()+"','" + c_posicao.getString(c_posicao.getColumnIndex("TAGID_Posicao"))+"','" +"" +"','" + ""+"','" +"" +"','" +"" +"','" +"" +"','" + dataHoraLocalizacao +"','" + ""+"','" +sp_tracking_processo.getSelectedItem() +"','" + "MOBILE"+"','" +"" +"','" + c_TAGID.getString(c_TAGID.getColumnIndex("Quantidade"))+"');");
                                showMessage("AVISO", "Inventário Realizado com Sucesso !", 1);
                                //db.execSQL("INSERT INTO UPMOBHistoricoLocalizacao VALUES('" +c_TAGID.getString(c_TAGID.getColumnIndex("Material_Type"))+"', '" +c_TAGID.getString(c_TAGID.getColumnIndex("lote_material"))+"','" +"Materiais"+"','" +c_TAGID.getString(c_TAGID.getColumnIndex("TAGID_Material"))+"','" + c_posicao.getString(c_posicao.getColumnIndex("TAGID_Posicao")) +"','" + c_posicao.getString(c_posicao.getColumnIndex("Cod_Posicao"))+"','" + c_posicao.getString(c_posicao.getColumnIndex("Cod_Almoxarifado")) +"','" + c_posicao.getString(c_posicao.getColumnIndex("Desc_Local"))+"','" +"" +"','" +"" +"','" +dataHoraLocalizacao +"','" + "" +"','" + sp_inventario_processo.getSelectedItem()+"','" + "MOBILE" +"','" + ""+"','"+ c_TAGID.getString(c_posicao.getColumnIndex("Quantidade")) + "');");
                            } catch (SQLException e) {
                                showMessage("ERRO", e.toString(), 2);
                            }
                        }
                    }
                    //showMessage("Success", listView.getAdapter().getItem(i).toString());
                    //Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" +  + "'", null);
                }

                return true;
            case R.id.limparTracking:
                listView.setAdapter(null);

                //Limpando ListView Principal
                _tagidList.clear();
                _listAdapter.notifyDataSetChanged();

                return true;
            case R.id.tracking_sync:
                //Nova Chamada para Sincronismo
                ESync.GetSyncInstance().SyncDatabase(Act_Tracking.this);
                return true;
            case R.id.reconectarTracking:
                list();
            default:
                return false;
        }
    }

    // ================================= VH75 READER PROCESS ================================= //
    private void clearList() {
        if (epc2num != null && epc2num.size() > 0) {
            epc2num.clear();
            refreshList();
        }
    }

    private void close_at(){

        BA.disable();
        BA.enable();
        this.finish();
    }

    public void onEventBackgroundThread(Act_Tracking.InventoryEvent e) {

        int i = 0;

        try {
            Act_Tracking.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_Tracking.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_Tracking.InventoryTerminal());
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
        EventBus.getDefault().post(new Act_Tracking.InventoryTerminal());

        try {
            Act_Tracking.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Tracking.currentDevice.getCmdResultWithTimeout(3000);
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

            Act_Tracking.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_Tracking.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            EventBus.getDefault().post(new Act_Tracking.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_Tracking.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_Tracking.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_Tracking.currentDevice
                        .getCmdResultWithTimeout(3000);
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                EventBus.getDefault().post(new Act_Tracking.EpcInventoryEvent());
            }
            // EventBus.getDefault().post(new InventoryTerminal());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            // e1.printStackTrace();
            // EventBus.getDefault().post(new TimeoutEvent());
            Log.i(TAG, "Timeout!!!");
        }
    }

    public void onEventMainThread(Act_Tracking.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;

//
//        btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
//        btnInventory.setText(Strings.getString(R.string.inventory));
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        ArrayList<byte[]> epcs = list.epcs;
        for (byte[] bs : epcs) {

            String tagid = "H" + Utility.bytes2HexString(bs);
            int count = lv_tracking_inv.getAdapter().getCount();

            for(int i = 0; i < count; i++) {
                if (tagid.equals(lv_tracking_inv.getAdapter().getItem(i).toString())) {
                    if (!ConfigUI.getConfigSkipsame(Act_Tracking.this)) {
                        if (epc2num.containsKey(tagid)) {
                            epc2num.put(tagid, epc2num.get(tagid) + 1);
                        } else {
                            epc2num.put(tagid, 1);
                        }
                    } else {
                        epc2num.put(tagid, 1);
                    }

                    readCount = epc2num.size();
                }
            }

        }

    }

    public void onEventMainThread(Act_Tracking.EpcInventoryEvent e) {
        refreshList();
    }

    Act_Tracking.InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;


        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;

        }

        public void run() {
            try {
                Act_Tracking.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_Tracking.currentDevice.getCmdResult();
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

    public void onEventMainThread(Act_Tracking.TimeoutEvent e) {
        progressDialog.dismiss();

        inventoring = false;

        EventBus.getDefault().post(new Act_Tracking.InventoryTerminal());
    }

    private void refreshList() {

        adapter = new Act_Tracking.IdListAdaptor();
        listView.setAdapter(adapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
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
            LayoutInflater inflater = Act_Tracking.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
            txt_count_item = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            //int count = epc2num.get(id);
            rfidTextView.setText(id);
            rfidTextView.setBackgroundColor(Color.GREEN);

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
            mInflator = Act_Tracking.this.getLayoutInflater();
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
            rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
            txt_count_item = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            rfidTextView.setText(id);
            rfidTextView.setBackgroundColor(Color.GREEN);

            return view;
        }

    }

    public BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            //String listItem = device.getAddress() + " | "+ (100 - rssi);
                            String tagid = device.getAddress();
                            int count = lv_tracking_inv.getAdapter().getCount();
                            for(int i=0;i<count;i++) {
                                if (tagid.equals(lv_tracking_inv.getAdapter().getItem(i).toString())) {
                                    if (!BLEMap.containsKey(tagid)) {
                                        BLEMap.put(tagid, 1);
                                        BLEMap.put(tagid, 1);

                                        //Beep
                                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                    }
                                    readCount = BLEMap.size();
                                }
                            }

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
    }

    private void clearLeDeviceList() {
        if (mLeDeviceListAdapter != null) {
            BLEMap.clear(); //BLEMap2.clear();
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

    public void listViewUpdateXR400(String tag_inventario) {
        boolean flagDO = true;
        int count = lv_tracking_inv.getAdapter().getCount();
        for(int i=0;i<count;i++) {
            if (tag_inventario.equals(lv_tracking_inv.getAdapter().getItem(i).toString())) {
                flagDO = false;
                if (!ConfigUI.getConfigSkipsame(Act_Tracking.this)) {
                    if (epc2num.containsKey(tag_inventario)) {
                        epc2num.put(tag_inventario, epc2num.get(tag_inventario) + 1);
                    } else {
                        epc2num.put(tag_inventario, 1);
                    }
                } else {
                    epc2num.put(tag_inventario, 1);
                }

                readCount = epc2num.size();
            }
        }
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
                            listViewUpdateXR400(tag_inventario);
                        }
                        EventBus.getDefault().post(new Act_Tracking.EpcInventoryEvent());
                    }

                    getXML = new RetrieveXMLResult(pref.getString(key_ipxr400, "192.168.0.100"));
                    getXML.execute();
                }
                handler.postDelayed(this, 2000);
            }
        });
    }

    // ================================= XR400 PROCESS ================================= //

    private void updateLang() {
        //btnInventory.setText(Strings.getString(R.string.inventory));
        //btnSave.setText(Strings.getString(R.string.save));
        refreshList();
        refreshLeDeviceList();
    }

    @Override
    public void onResume() {

        EventBus.getDefault().register(this);
        //refreshList();
        refreshList();
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
       // Toast.makeText(getApplicationContext(), "Conectado !", Toast.LENGTH_SHORT).show();

    }

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(Act_Tracking.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Tracking.this, currentDevice.getAddress());

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
                                ConfigUI.setConfigLastConnect(Act_Tracking.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Tracking.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }


            }
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

    private void FillPosicaoSpinner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                ArrayAdapter<String> posicaoAdapter = new ArrayAdapter<String>(Act_Tracking.this, R.layout.spinner_item);
                String codigoAlmoxarifado = dbInstance.parametrosPadraoDAO().GetCodigoAlmoxarifado();

                for (String posicao : dbInstance.posicoesDAO().GetPosicoesByAlmoxarifado(codigoAlmoxarifado))
                {
                    posicaoAdapter.add(posicao);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sp_tracking_posicao.setAdapter(posicaoAdapter);
                    }
                });
            }
        }).start();
    }

    private void FillProcessoSpinner(){
        String [] arraySpinner_processo = new String[] {
                "INVENTÁRIO",
                "ENTRADA",
                "SAÍDA",
                "SAÍDA-CONSUMO",
                "SAÍDA-DESTRUICAO",
                "SEPARACAO"
        };

        ArrayAdapter<String> adapter_processo = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinner_processo);
        sp_tracking_processo.setAdapter(adapter_processo);
    }

    private void SearchByFilter(String filter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<String> TagidList =  dbInstance.cadastroMateriaisDAO().GetByFilter(filter);

                //reiniciando lista de tagids sempre que clicar em 'Adicionar'
                _tagidList.clear();
                _tagidList.addAll(TagidList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _listAdapter.notifyDataSetChanged();
                    }
                });

                return;
            }
        }).start();
    }
}
