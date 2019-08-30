package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_Gravar_Tag extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "tagidMaterial";
    private TextView tv_tagid;
    private EditText et_novo_tagid;
    private FloatingActionButton fab_gravar;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    public static VH73Device currentDevice;
    private boolean inventoring = false;
    public int readCount = 0;
    public static final String TAG = "inventory";
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };
    private Handler fhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
        }
    };
    static Epc epcToBeAccess;

    public static class InventoryEvent {
    }
    public static class InventoryTerminal {
    }
    public static class EpcSelectedEvent {
        Epc epc;

        public EpcSelectedEvent(Epc epc) {
            this.epc = epc;
        }

        public Epc getEpc() {
            return epc;
        }

        @Override
        public String toString() {
            return "EpcSelectedEvent [epc=" + epc + "]";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_gravar_tag);

        setupToolbar();

        tv_tagid = (TextView) findViewById(R.id.tv_tagid);
        et_novo_tagid = (EditText) findViewById(R.id.et_novo_tagid);
        fab_gravar = (FloatingActionButton) findViewById(R.id.fab_gravar);

        tv_tagid.setText(getIntent().getStringExtra(EXTRA_MESSAGE).toString());
        fab_gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
                if(Act_Gravar_Tag.currentDevice != null){
                    writeData();
                }else{
                    showMessage("Atenção", "Leitor RFID não conectado.", 3);
                }
            }
        });

        Epc epc = new Epc(tv_tagid.getText().toString().substring(1), 1);
        EventBus.getDefault().postSticky(new Act_Gravar_Tag.EpcSelectedEvent(epc));

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();
    }

    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        try {
            list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EpcSelectedEvent epcSelectedEvent = (Act_Gravar_Tag.EpcSelectedEvent) EventBus
                .getDefault().getStickyEvent(EpcSelectedEvent.class);

        if (epcSelectedEvent != null) {
            Log.i(TAG, "epc selected " + epcSelectedEvent);
            epcToBeAccess = epcSelectedEvent.getEpc();
            EventBus.getDefault().removeStickyEvent(epcSelectedEvent);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
        close_at();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_gravartag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_reconectar_gravar:
                list();
                return true;
            default:
                return false;
        }
    }

    void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
                                ConfigUI.setConfigLastConnect(Act_Gravar_Tag.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Gravar_Tag.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                        }
                    }.start();
                }
            }
        }
    }

    private void connect(final BluetoothDevice device) {
        new Thread() {
            public void run() {
                VH73Device vh75Device = new VH73Device(Act_Gravar_Tag.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {
                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Gravar_Tag.this, currentDevice.getAddress());

                    Message message = mhandler.obtainMessage();
                    message.sendToTarget();

                    EventBus.getDefault().post(new SettingsActivity.GetHandsetParam());

                } else {
                    Message message = fhandler.obtainMessage();
                    message.sendToTarget();
                }
            }
        }.start();
    }

    private void writeData() {
        if (!checkAccessWriteEnable())
            return;

        int mem;
        int addr;
        String passwd;
        String data;
        String tagid;

        tagid = tv_tagid.getText().toString().substring(1);
        mem = 1;
        addr = 0;
        data = et_novo_tagid.getText().toString();
        passwd = "00000000";

        try {
            currentDevice.WriteWordBlock(tagid, mem, addr, data, passwd);
            byte[] ret = currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(ret)) {
                Utility.showTostInNonUIThread(this, "good");
            } else {
                Utility.showTostInNonUIThread(this, "bad");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
//			e.printStackTrace();
            Utility.showTostInNonUIThread(this, "timeout");
        }
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

    public void onEventBackgroundThread(Act_Gravar_Tag.InventoryEvent e) {
        int i = 0;
        try {
            Act_Gravar_Tag.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_Gravar_Tag.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_Gravar_Tag.InventoryTerminal());
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
            long lnow = android.os.SystemClock.uptimeMillis();
            //doInventory();
            while (true) {
                long lnew = android.os.SystemClock.uptimeMillis();
                if (lnew - lnow > 50) {
                    break;
                }
            }
        }

        EventBus.getDefault().post(new Act_Gravar_Tag.InventoryTerminal());

        try {
            Act_Gravar_Tag.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Gravar_Tag.currentDevice.getCmdResultWithTimeout(3000);
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

    public void onEventMainThread(Act_Gravar_Tag.InventoryTerminal e) {
        inventoring = false;
    }

    private boolean checkAccessWriteEnable() {
        if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {
            Utility.showTostInNonUIThread(this, "Teste");
            return false;
        }

        if (et_novo_tagid.getText().length() <= 0) {
            Utility.showTostInNonUIThread(this, "Teste1");
            return false;
        }

        return true;
    }
}
