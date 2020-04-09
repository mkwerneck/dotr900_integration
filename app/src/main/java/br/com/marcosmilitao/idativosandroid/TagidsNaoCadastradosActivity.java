package br.com.marcosmilitao.idativosandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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

public class TagidsNaoCadastradosActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private List<String> tagsLidosArrayList;
    private ArrayAdapter<String> tagsLidosArrayAdapter;
    private ListView lv_tagidnc;

    private FloatingActionButton fab_tagidnc;
    private List<BluetoothDevice> foundDevices;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                if (TagidsNaoCadastradosActivity.currentDevice != null)
                {
                    if (reading == false)
                    {
                        StartReading();
                    } else {
                        StopReading();
                    }
                } else {
                    ConectarDispositivoBT();
                }
            }
        });

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
                ConectarDispositivoBT();
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

    private void ConectarDispositivoBT()
    {
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            connectionBTHandler.post(new Runnable() {
                @Override
                public void run() {
                    VH73Device vh75Device = new VH73Device(TagidsNaoCadastradosActivity.this, bluetoothDevice);

                    boolean succ = vh75Device.connect();

                    if (succ) {
                        TagidsNaoCadastradosActivity.currentDevice = vh75Device;
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
    }

    private void StartReading()
    {
        reading = true;
        fab_tagidnc.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
    }

    private void StopReading()
    {
        reading = false;
        fab_tagidnc.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    private void Read()
    {
        readTAGIDHandler.post(new Runnable() {
            @Override
            public void run() {
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
        });
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
}
