package br.com.marcosmilitao.idativosandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dotel.libr900.BluetoothActivity;
import com.dotel.libr900.OnBtEventListener;
import com.dotel.libr900.R900Protocol;
import com.dotel.libr900.R900RecvPacketParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerPosicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.Cadastro_Tags;
import br.com.marcosmilitao.idativosandroid.POJO.PosicoesInv;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;

public class TagidsNaoCadastradosActivity extends BluetoothActivity implements OnBtEventListener {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private List<String> tagsLidosArrayList;
    private ArrayAdapter<String> tagsLidosArrayAdapter;
    private ListView lv_tagidnc;

    private FloatingActionButton fab_tagidnc;

    private List<BluetoothDevice> foundDevices;
    private BluetoothDevice mConnectedDevice;
    private static VH73Device currentDevice;
    private Set<BluetoothDevice> pairedDevices;

    private boolean reading = false;

    public static final String TAG_READING = "reading";

    private Handler updateListViewHandler;
    private Handler readTAGIDHandler;
    private Handler connectionBTHandler;
    private Handler mainHandler;

    private HandlerThread updateListViewThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;

    private ContextMenu _menu;

    private Intent intent;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String modeloLeitor;
    public static final String key_modelo_leitor_rfid = "key_modelo_leitor_rfid";
    private String modelo_leitor_rfid_Default;

    private static final int[] TX_DUTY_OFF =
            { 10, 40, 80, 100, 160, 180 };

    private static final int[] TX_DUTY_ON =
            { 190, 160, 70, 40, 20 };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagids_nao_cadastrados);

        connectionBTThread = new HandlerThread("ConnectionBTThread");
        connectionBTThread.start();
        connectionBTHandler = new Handler(connectionBTThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //Processar mensagens
            }
        };

        readTAGIDThread = new HandlerThread("readTAGIDThread");
        readTAGIDThread.start();
        readTAGIDHandler = new Handler(readTAGIDThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //Processar mensagens
            }
        };

        updateListViewThread = new HandlerThread("updateListViewThread");
        updateListViewThread.start();
        updateListViewHandler = new Handler(updateListViewThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //Processar mensagens
            }
        };

        mainHandler = new Handler();

        //Inicializando a custom toolbar
        SetupToolbar();

        //instancia do banco de dados
        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        //inicializando Bluetooth propriedades
        foundDevices = new ArrayList<BluetoothDevice>();

        tagsLidosArrayList = new ArrayList<String>();
        tagsLidosArrayAdapter = new ArrayAdapter<String>(TagidsNaoCadastradosActivity.this, android.R.layout.simple_list_item_1, tagsLidosArrayList);
        lv_tagidnc = (ListView)findViewById(R.id.lv_tagidnc);
        lv_tagidnc.setAdapter(tagsLidosArrayAdapter);
        registerForContextMenu(lv_tagidnc);

        fab_tagidnc = (FloatingActionButton) findViewById(R.id.fab_tagidnc);
        fab_tagidnc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mConnectedDevice != null)
                {
                    if (reading == false)
                    {
                        StartReading();
                    } else {
                        StopReading();
                    }
                } else {
                    ConectarDispositivoBT(modeloLeitor);
                }
            }
        });

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        setOnBtEventListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        //Obtendo o modelo preferencial
        modeloLeitor = LeitorPreferencial();

        ConectarDispositivoBT(modeloLeitor);

        super.onResume();
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
        inflater.inflate(R.menu.menu_act_tagid_nao_cadastrado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_tagid_nao_cadastrado_limpar:
                LimparListView();
                return true;
            case R.id.action_tagid_nao_cadastrado_reconectar:
                ConectarDispositivoBT(modeloLeitor);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        _menu = menu;

        super.onCreateContextMenu(_menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        String tagid = tagsLidosArrayAdapter.getItem(info.position);
        _menu.setHeaderTitle(tagid);

        getMenuInflater().inflate(R.menu.menu_context_tagnaocadastrado, _menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String tagid = tagsLidosArrayAdapter.getItem(info.position);

        switch (item.getItemId()){
            case R.id.opt_cadastrar_ferramenta:

                intent = new Intent();
                intent.setClass(TagidsNaoCadastradosActivity.this, CadastrarFerramentasActivity.class);
                intent.putExtra(CadastrarFerramentasActivity.EXTRA_TAGID, tagid);
                startActivity(intent);

                break;

            case R.id.opt_cadastrar_usuario:

                intent = new Intent();
                intent.setClass(TagidsNaoCadastradosActivity.this, CadastrarUsuariosActivity.class);
                intent.putExtra(CadastrarUsuariosActivity.EXTRA_TAGID, tagid);
                startActivity(intent);

                break;

            default:
                break;
        }
        return true;
    }

    void SetupToolbar()
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
        BA.disable();
        BA.enable();

        this.finish();
    }

    private void ConectarDispositivoBT(String modeloLeitor)
    {
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            switch (modeloLeitor)
            {
                case "Vanch_VH75":

                    ConectarVANCH75(bluetoothDevice);
                    break;

                case "DOTR_900":

                    ConectarDOTR900(bluetoothDevice);
                    break;

                default:
                    break;
            }
        }
    }

    private void StartReading()
    {
        reading = true;
        fab_tagidnc.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
    }

    private void StopReading()
    {
        fab_tagidnc.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));

        switch (modeloLeitor)
        {
            case "Vanch_VH75":
                reading = false;
                break;

            case "DOTR_900":
                reading = false;
                sendCmdStop();
                break;

            default:
                break;
        }
    }

    private void Read()
    {
        readTAGIDHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (modeloLeitor)
                {
                    case "Vanch_VH75":
                        StartReadingVANCH75();

                        break;

                    case "DOTR_900":
                        StartReadingDOTR900();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void StartReadingVANCH75()
    {
        try{
            TagidsNaoCadastradosActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = TagidsNaoCadastradosActivity.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                StopReading();

                return;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            Log.i(TAG_READING, "Timeout!!@");
        }

        while (reading)
        {
            long lnow = SystemClock.uptimeMillis();

            //TODO LEITURA
            try{
                TagidsNaoCadastradosActivity.currentDevice.listTagID(1, 0, 0);
                byte[] ret = TagidsNaoCadastradosActivity.currentDevice.getCmdResult();
                if (VH73Device.checkSucc(ret))
                {
                    VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);

                    //transformando cada tagid lido no padrao com sufixo "H" e atualizando a ListView
                    for(byte[] bs : listTagIDResult.epcs)
                    {
                        final String tagid = "H" + Utility.bytes2HexString(bs);

                        AtualizarListView(tagid);
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

    private void StartReadingDOTR900()
    {
        sendInventParam(1, 5, 0);
        setOpMode(false, false, 0, false);
        sendCmdInventory();
    }

    private void AtualizarListView(String tagid)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {
                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);
                CadastroMateriais cadastroMateriaisDescartados = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, false);
                CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByTAGID(tagid);
                Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tagid);
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                if (!tagsLidosArrayList.contains(tagid))
                {
                    if (cadastroMateriais == null && cadastroMateriaisDescartados == null && cadastroEquipamentos == null && posicao == null && usuario == null){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tagsLidosArrayList.add(tagid);
                                tagsLidosArrayAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        return;
                    }
                }
            }
        });
    }

    private void LimparListView()
    {
        tagsLidosArrayList.clear();

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                tagsLidosArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private String LeitorPreferencial()
    {
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        if (!pref.contains(key_modelo_leitor_rfid)){
            editor.putString(key_modelo_leitor_rfid, modelo_leitor_rfid_Default);
            editor.commit();

            return modelo_leitor_rfid_Default;
        } else {
            return pref.getString(key_modelo_leitor_rfid, modelo_leitor_rfid_Default);
        }
    }

    private void ConectarVANCH75(BluetoothDevice bluetoothDevice)
    {
        connectionBTHandler.post(new Runnable() {
            @Override
            public void run() {
                VH73Device vh75Device = new VH73Device(TagidsNaoCadastradosActivity.this, bluetoothDevice);

                boolean succ = vh75Device.connect();

                if (succ) {

                    TagidsNaoCadastradosActivity.currentDevice = vh75Device;
                    mConnectedDevice = bluetoothDevice;

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TagidsNaoCadastradosActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TagidsNaoCadastradosActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void ConectarDOTR900(BluetoothDevice bluetoothDevice)
    {
        if (mConnectedDevice == null)
        {
            if (mR900Manager.isTryingConnect() == false)
            {
                mR900Manager.connectToBluetoothDevice(bluetoothDevice, MY_UUID);
            }
        }
    }

    public void onBtFoundNewDevice(BluetoothDevice device)
    {}

    public void onBtScanCompleted()
    {}

    public void onBtConnected( BluetoothDevice device )
    {
        mConnectedDevice = device;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TagidsNaoCadastradosActivity.this, "Leitor conectado!", Toast.LENGTH_SHORT).show();
            }
        });

        sendCmdOpenInterface1();

        sendSettingTxCycle(TX_DUTY_ON[0], TX_DUTY_OFF[0]);

        this.sleep(1000);
    }

    public void onBtDisconnected(BluetoothDevice device)
    {
        mConnectedDevice = null;

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TagidsNaoCadastradosActivity.this, "Leitor desconectado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtConnectFail(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TagidsNaoCadastradosActivity.this, "Não foi possível conectar ao leitor. Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtDataSent(byte[] data)
    {
    }

    public void onBtDataTransException(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TagidsNaoCadastradosActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onNotifyBtDataRecv()
    {
        if( mR900Manager == null )
            return;

        R900RecvPacketParser packetParser = mR900Manager.getRecvPacketParser();

        while( true )
        {
            final String parameter = packetParser.popPacket();

            if( mConnectedDevice == null )
                break;

            if( parameter != null )
            {
                processPacket(parameter);
            }
            else
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {}

    private synchronized void processPacket(final String param){
        if (param == null || param.length() <= 0)
            return;

        final String CMD = param.toLowerCase();

        if ( CMD.indexOf("^") == 0 || CMD.indexOf("$") == 0
                || CMD.indexOf("ok") == 0 || CMD.indexOf("err") == 0
                || CMD.indexOf("end") == 0 )
        {
            if (CMD.indexOf("$trigger=1") == 0){
                StartReading();
            }
            else if (CMD.indexOf("$trigger=0") == 0){
                StopReading();
            }
        }
        else
        {
            if (mLastCmd == null)
                return;

            if (mLastCmd.equalsIgnoreCase(R900Protocol.CMD_INVENT))
            {
                if(param == null || param.length() <= 4)
                    return;

                final String tagid = "H" + param.substring(4, param.length() - 4);

                AtualizarListView(tagid.toUpperCase());
            }
        }
    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) { }
    }
}
