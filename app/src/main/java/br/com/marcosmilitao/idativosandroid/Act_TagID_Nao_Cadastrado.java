package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_TagID_Nao_Cadastrado extends AppCompatActivity {
    ListView listView;
    List<Epc> epcs = new ArrayList<Epc>();
    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();
    EditText etv_cadastro_quantidade;
    EditText tv_cadastro_nlote_tracen;
    EditText tv_cadastro_part_number;
    Spinner sp_cadastro_posicao;
    Spinner sp_cadastro_categoria;

    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;

    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();

    private String[] arraySpinner_processo;
    private SpinnerAdapter posicao1;
    SQLiteDatabase db;
    //Teste ListView
    CheckBox cbx_editar_cadastro;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private Idativos02Data idativos02Data;
    private SQLiteDatabase connect;

    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Query_UPWEBWorksheets query_upwebWorksheets;

    ProgressDialog progressDialog;
    boolean stoop = false;
    int readCount = 0;
    boolean inventoring = false;
    public static final String TAG = "inventory";
    private HandsetParam param;
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };

    private static final String cor = "";

    private GoogleApiClient client;

    public Act_TagID_Nao_Cadastrado() {
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
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
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
        setContentView(R.layout.activity_act__tag_id__nao__cadastrado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();
            }
        });

        listView = (ListView) findViewById(R.id.lv_tagid_nao_cadastrado);

        //Teste ListView
        mainListView = (ListView) findViewById( R.id.lv_cadastro );

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                if(Act_TagID_Nao_Cadastrado.currentDevice != null){

                    if(!inventoring){
                        inventoring = !inventoring;
                        readCount=0;
                        clearList();
                        EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.InventoryEvent());
                        color = "#F30808";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }else {
                        inventoring = !inventoring;


                        color = "#41C05A";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }
                }else{
                    Utility.WarningAlertDialg(Act_TagID_Nao_Cadastrado.this,"!","Leitor RFID NÃO Conectado.").show();
                }

            }
        });
        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        try {
            list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_tagid_nao_cadastrado,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_tagid_nao_cadastrado_reconectar:
                list();
                return true;
            default:
                return false;
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
                VH73Device vh75Device = new VH73Device(Act_TagID_Nao_Cadastrado.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_TagID_Nao_Cadastrado.this, currentDevice.getAddress());

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
                                ConfigUI.setConfigLastConnect(Act_TagID_Nao_Cadastrado.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_TagID_Nao_Cadastrado.this, "Erro de Conexão" + currentDevice.getAddress());
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
            refreshList();
        }
    }

    public void onEventBackgroundThread(Act_TagID_Nao_Cadastrado.InventoryEvent e) {

        int i = 0;

        try {
            Act_TagID_Nao_Cadastrado.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_TagID_Nao_Cadastrado.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.InventoryTerminal());
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
        EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.InventoryTerminal());

        try {
            Act_TagID_Nao_Cadastrado.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_TagID_Nao_Cadastrado.currentDevice.getCmdResultWithTimeout(3000);
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

            Act_TagID_Nao_Cadastrado.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_TagID_Nao_Cadastrado.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);

            addEpc(listTagIDResult);
            EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_TagID_Nao_Cadastrado.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_TagID_Nao_Cadastrado.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_TagID_Nao_Cadastrado.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.EpcInventoryEvent());
            }
            // EventBus.getDefault().post(new InventoryTerminal());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onEventMainThread(Act_TagID_Nao_Cadastrado.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;
//
//        btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
//        btnInventory.setText(Strings.getString(R.string.inventory));
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        //ArrayAdapter<String> item1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ArrayList<byte[]> epcs = list.epcs;
        for (byte[] bs : epcs) {
            String tagid ="H" + Utility.bytes2HexString(bs);
            if (epc2num.containsKey(tagid)) {
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);
                    CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByTAGID(tagid);
                    Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tagid);
                    Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                    if (cadastroMateriais == null && cadastroEquipamentos == null && posicao == null && usuario == null)
                    {
                        epc2num.put(tagid, 1);
                    }

                    readCount = epc2num.size();
                }
            }).start();

            /*query_upwebWorksheets = new Query_UPWEBWorksheets(db);
            item1 = query_upwebWorksheets.UPWEBWorksheetTAGIDQuery2(this, tag_inventario);

            if (item1.isEmpty() == false) {
                tag_inventario = new String();
                item1 = null;
            } else {
                epc2num.put(tag_inventario, 1);
                tag_inventario = new String();
                item1 = null;
            }
            readCount = epc2num.size();*/
        }
    }

    public void onEventMainThread(Act_TagID_Nao_Cadastrado.EpcInventoryEvent e) {
        refreshList();
        //txtCount.setText("" + readCount);
        //
    }

    Act_TagID_Nao_Cadastrado.InventoryThread inventoryThread;

    class InventoryThread extends Thread {
        int len, addr, mem;
        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;
        }

        public void run() {
            try {
                Act_TagID_Nao_Cadastrado.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_TagID_Nao_Cadastrado.currentDevice.getCmdResult();
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

    public void onEventMainThread(Act_TagID_Nao_Cadastrado.TimeoutEvent e) {
        progressDialog.dismiss();
        inventoring = false;
        EventBus.getDefault().post(new Act_TagID_Nao_Cadastrado.InventoryTerminal());
    }

    private void refreshList() {
        adapter = new Act_TagID_Nao_Cadastrado.IdListAdaptor();
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
            LayoutInflater inflater = Act_TagID_Nao_Cadastrado.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            TextView rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            int count = epc2num.get(id);
            rfidTextView.setText(id);
            //countTextView.setText("Quantidade:" + count);

            TextView textViewNoTitle = (TextView) view.findViewById(R.id.txt_teste);
            //textViewNoTitle.setText("test test teste");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = this;
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            BA.disable();
            BA.enable();
        }
        return super.onKeyDown(keyCode, event);
    }
}
