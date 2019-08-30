package br.com.marcosmilitao.idativosandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Processos.ProcessosOSActivity;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.ConfigParam;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button b1, b2, b3, b4;
    ProgressDialog progress;
    private Idativos02Data idativos02Data;
    //private SQLiteDatabase connect;
    private Sync sync;

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    ListView listViewUse;
    protected static final int CONNECTING = 1;
    protected static final int CONNECTING_OK = 2;
    protected static final int CONNECTING_FAILE = 3;
    protected static final int DISCONNECT = 4;
    public static VH73Device currentDevice;
    ProgressDialog progressDialog;
    private static final String TAG = "link";
    BaseAdapter adapter;
    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();

    public static final String key_last_connected = "last_connected";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
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

    public static class FreshList {

    }
    public static class DoReadParam {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(MainActivity.this);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView) findViewById(R.id.listView);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        db = openOrCreateDatabase("Idativos02Data", Context.MODE_PRIVATE, null);
        idativos02Data = new Idativos02Data(this);
    }
    public static class BTDeviceFoundEvent {
        BluetoothDevice device;

        public BTDeviceFoundEvent(BluetoothDevice device) {
            this.device = device;
        }

        public BluetoothDevice getDevice() {
            return device;
        }

        public void setDevice(BluetoothDevice device) {
            this.device = device;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        return super.onOptionsItemSelected(item);

        switch (id) {
            case R.id.action_inventario:
                intent = new Intent(this, Act_Inventario.class);
                intent.putExtra("main",MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_cadastro:
                intent = new Intent(this, Act_Cadastro.class);
                startActivity(intent);
                return true;
            case R.id.action_sync:
                if(DbConection() != null) {

                    if (db != null){
                        final ProgressDialog pd = new ProgressDialog(this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("SINCRONIZANDO...");
                        pd.setIndeterminate(true);
                        pd.setCancelable(false);
                        pd.show();
                        sync = new Sync(this, db);
                        pd.dismiss();
                    }
                    else {
                        showMessage("AVISO","NÃO FOI POSSÍVEL SINCRONIZAR. VERIFIQUE SUA CONEXÃO DE DADOS.");
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Problema na Conexão de Dados !", Toast.LENGTH_SHORT).show();
                }


                return true;
            case R.id.action_tracking:
                intent = new Intent(this, Act_Tracking.class);
                startActivity(intent);
                return true;
            case R.id.action_tagid_nao_cadastrado:
                intent = new Intent(this, Act_TagID_Nao_Cadastrado.class);
                startActivity(intent);
                return true;
            case R.id.action_mail_sair:
                Context context = this;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                close();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Tem certeza que deseja sair?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();
            case R.id.action_processo_os:
                intent = new Intent(this, ProcessosOSActivity.class);
                startActivity(intent);

                return true;
            default:
                return false;
        }
    }
    public Connection DbConection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "sa";
            String password = "idutto@04d";
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://200.98.67.133:1433/idativos_omni04_teste;user=" + username + ";password=" + password);

            return DbConn;
        } catch (InstantiationException e1) {
            //e1.printStackTrace();

        } catch (IllegalAccessException e1) {
            //e1.printStackTrace();

        } catch (ClassNotFoundException e1) {
            //e1.printStackTrace();

        } catch (java.sql.SQLException e1) {
            //e1.printStackTrace();

        }
        return null;
    }
    public void on(View v) {
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Bluetooth LIGADO!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth já está LIGADO!", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v) {
        BA.disable();
        Toast.makeText(getApplicationContext(), "Bluetooth DESLIGADO!", Toast.LENGTH_LONG).show();
    }


    public void visible(View v) {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void list(View v) {
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) {
            //connect(bt);
            list.add(bt.getAddress());
        }
        Toast.makeText(getApplicationContext(), "Dispositivos Pareados!", Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);


    }

    public void queryPairedDevices() {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                foundDevices.add(device);
                EventBus.getDefault().post(new BTDeviceFoundEvent(device));

                String lastDeviceMac = getConfigLastConnected(this);
                if (lastDeviceMac.equals(device.getAddress()) && currentDevice == null) {
                    currentDevice = new VH73Device(this, device);
                    new Thread() {
                        public void run() {
                            if (currentDevice.connect()) {
                                ConfigUI.setConfigLastConnect(MainActivity.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(MainActivity.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new FreshList());
                        }
                    }.start();
                }


            }
        }

    }
    private void deactivateBtMonitor() {
        MainActivity.this.unregisterReceiver(mReceiver);
    }

    //Discovering devices
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceFound(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                discoveryStarted();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                discoveryEnded();
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        Log.d(TAG, "正在配对......");
//                        progressDialog.setMessage(Strings.getString(R.string.msg_paring));
//                        progressDialog.show();
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.d("BlueToothTestActivity", "完成配对");
//                        progressDialog.setMessage(Strings.getString(R.string.msg_paired));
//                        progressDialog.show();
                        connect(device);//连接设备
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Log.d(TAG, "取消配对");
                    default:
                        break;
                }
            }
        }


        private void discoveryEnded() {
            Log.i(TAG, "finish discovery");
//            progressDialog.setMessage(Strings.getString(R.string.msg_scan_over));
//            progressDialog.dismiss();
            refreshList();
        }

        private void discoveryStarted() {
            Log.i(TAG, "start discovery");
//            progressDialog.setMessage(Strings.getString(R.string.msg_scaning));
//            progressDialog.show();
            foundDevices = new ArrayList<BluetoothDevice>();
            queryPairedDevices();
        }

        private void deviceFound(BluetoothDevice device) {
            if (!hasFoundDevice(device)) {
                Log.i(TAG, "Device " + device.getName() +" found " + device.toString());
                foundDevices.add(device);
                EventBus.getDefault().post(new BTDeviceFoundEvent(device));
            }
        }
    };
    private void connect(final BluetoothDevice device) {
        handle.sendEmptyMessage(CONNECTING);
        new Thread() {

            public void run() {
                VH73Device vh75Device = new VH73Device(MainActivity.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {
                    handle.sendEmptyMessage(CONNECTING_OK);
                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new DoReadParam());
                    ConfigUI.setConfigLastConnect(MainActivity.this, currentDevice.getAddress());
                } else {
                    handle.sendEmptyMessage(CONNECTING_FAILE);
                }
            }
        }.start();
    }
    public void onEventBackgroundThread(DoReadParam e) {
        try {
            ConfigParam param = currentDevice.readConfigParam();
            Log.d(TAG, "read param:"+param);
            EventBus.getDefault().postSticky(param);
        } catch (IOException e1) {
            e1.printStackTrace();
//            Utility.showDialogInNonUIThread(MainActivity.this, Strings.getString(R.string.msg_waring),
//                    Strings.getString(R.string.msg_read_config_fail));
            disconnect();
        }
    }
    private void disconnect() {
        if (currentDevice!=null && currentDevice.isConnected()) {
            try {
                currentDevice.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentDevice = null;
            refreshList();
        }
    }
    private void refreshList() {
        Log.i(TAG, "refreshList");
        if (foundDevices == null)
            return;

        if (foundDevices.size() <= 0) {
            lv.setAdapter((new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, new String[]{"Not Found"})));
        } else {
            adapter = new DeviceListAdaptor();
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(itemClickListener);
            //listViewUse.setOnItemLongClickListener(longClick);
        }
    }

    OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (foundDevices.size() > 0) {
                Boolean returnValue = false;
                BluetoothDevice btDev = foundDevices.get(position);
                if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {

                    Method createBondMethod;
                    try {
                        Class[] par = {};
                        createBondMethod = BluetoothDevice.class.getMethod("createBond", par);
                        returnValue = (Boolean) createBondMethod.invoke(btDev);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), "NoSuchMethodException", Toast.LENGTH_LONG).show();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), "IllegalArgumentException", Toast.LENGTH_LONG).show();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), "IllegalAccessException", Toast.LENGTH_LONG).show();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), "InvocationTargetException", Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "开始配对");
                } else if (currentDevice==null){
                    //connect
                    connect(btDev);
                    View listViewChildAt = lv.getChildAt(0);
                    listViewChildAt.setBackgroundColor(Color.BLUE);
                } else {
                    //Toast.makeText(getActivity(), Strings.getString(R.string.msg_already_connect), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private Handler handle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what) {
                case CONNECTING:
//                    progressDialog.setMessage(Strings.getString(R.string.msg_connecting));
//                    progressDialog.show();
                    break;
                case CONNECTING_OK:
//                    progressDialog.setMessage(Strings.getString(R.string.msg_connected));
//                    progressDialog.show();
//                    progressDialog.dismiss();
//                    refreshList();
                    break;
                case CONNECTING_FAILE:
                    //progressDialog.setMessage("Connecting failed...");
                    //progressDialog.show();
//                    progressDialog.dismiss();
//                    Utility.WarningAlertDialg(getActivity(), "", Strings.getString(R.string.msg_connect_fail)).show();
                    break;
                case DISCONNECT:
                    break;
            }
        }
    };




    private class DeviceListAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return foundDevices.size();
        }

        @Override
        public Object getItem(int position) {
            return foundDevices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BluetoothDevice device = foundDevices.get(position);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            View view = inflater.inflate(null, null);
//            TextView nameTextView = (TextView) view.findViewById(R.id.txt_device_name);
//            nameTextView.setText(device.getName());
//            TextView macTextView = (TextView) view.findViewById(R.id.txt_device_mac);
//            macTextView.setText(device.getAddress());

//            ImageView statusImageView = (ImageView) view.findViewById(R.id.ic_connect_status);
//
//            TextView connectTextView = (TextView) view.findViewById(R.id.txt_connect_status);
            if (hasDeviceConnected(device)) {
                //connectTextView.setText("Connected");
//                connectTextView.setText(Strings.getString(R.string.connected));
//                connectTextView.setTextColor(Color.GREEN);
//                statusImageView.setImageResource(R.drawable.ic_bluetooth_connected);
            } else {
                //connectTextView.setText("Disconnectd");
//                connectTextView.setText(Strings.getString(R.string.notconnected));
//                connectTextView.setTextColor(Color.RED);
//                statusImageView.setImageResource(R.drawable.ic_bluetooth);
            }
            return view;
        }
    }

    private boolean hasDeviceConnected(BluetoothDevice device) {
        if (currentDevice!=null
                && currentDevice.getAddress().equals(device.getAddress())
                && currentDevice.isConnected()) {
            return true;
        }
        return false;
    }

    private boolean hasFoundDevice(BluetoothDevice device) {
        for (BluetoothDevice dev : foundDevices) {
            if(device.getAddress().equals(dev.getAddress()))
                return true;
        }
        return false;
    }

    private static String getString(Activity activity, String key, String defaultVal) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(key, defaultVal);
    }

    public static String getConfigLastConnected(Activity activity) {
        return getString(activity, key_last_connected, "");
    }

    public void onEventMainThread(BTDeviceFoundEvent e) {
        refreshList();
    }

    public void onEventMainThread(ConfigUI.LangChanged e) {
        updateLang();
    }
    private void updateLang() {
//        btnDisconnect.setText(Strings.getString(R.string.disconnect));
//        btnScan.setText(Strings.getString(R.string.scan));
//        useful.setText(Strings.getString(R.string.available));
        refreshList();
    }
    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    protected void freshConnectStatus() {
        assert(currentDevice!=null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    private void close(){
        this.finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = this;
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                                close();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Tem certeza que deseja sair?").setPositiveButton("Sim", dialogClickListener)
                    .setNegativeButton("Não", dialogClickListener).show();


        }
        return super.onKeyDown(keyCode, event);
    }
}


