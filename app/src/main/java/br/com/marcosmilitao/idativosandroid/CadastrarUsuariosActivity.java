package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTagid;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerFuncoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;

import com.dotel.libr900.BluetoothActivity;
import com.dotel.libr900.OnBtEventListener;
import com.dotel.libr900.R900Protocol;
import com.dotel.libr900.R900RecvPacketParser;
import com.dotel.libr900.R900Status;
import com.dotel.rfid.LampView;
import com.dotel.rfid.MaskActivity;

public class CadastrarUsuariosActivity extends BluetoothActivity implements OnBtEventListener {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private CustomAdapterSpinnerFuncoes funcoesAdapter;
    private CustomAdapterCadastrarTagid listViewAdapter;

    private ArrayList<FuncoesCU> funcoesArrayList;
    private ArrayList<CadastrarTagid> listViewArrayList;
    private List<String> tagsLidosArrayList;

    private EditText et_cadusu_username, et_cadusu_nomecompleto, et_cadusu_email;

    private CheckBox ckb_cadusu_enviarsenha;

    private Spinner sp_cadusu_funcao;

    private ListView lv_cadusu_tagid;

    private FloatingActionButton fab_cadusu;

    private static VH73Device currentDevice;
    private BluetoothDevice mConnectedDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private boolean reading = false;

    private Integer listViewSelected = null;
    private String funcaoSelected = null;

    private Handler updateListViewHandler;
    private Handler readTAGIDHandler;
    private Handler connectionBTHandler;
    private Handler salvarHandler;
    private Handler mainHandler;

    private HandlerThread updateListViewThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;
    private HandlerThread salvarThread;

    public static final String TAG_READING = "reading";

    public static final String EXTRA_TAGID = "tagid";
    private String extra_tagid = null;
    private Intent intent;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String modeloLeitor;
    public static final String key_modelo_leitor_rfid = "key_modelo_leitor_rfid";
    private String modelo_leitor_rfid_Default = this.getResources().getString(R.string.modelo_leitor_default);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuarios);

        connectionBTThread = new HandlerThread("ConnectionBTThread");
        connectionBTThread.start();
        connectionBTHandler = new Handler(connectionBTThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        readTAGIDThread = new HandlerThread("readTAGIDThread");
        readTAGIDThread.start();
        readTAGIDHandler = new Handler(readTAGIDThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        updateListViewThread = new HandlerThread("updateListViewThread");
        updateListViewThread.start();
        updateListViewHandler = new Handler(updateListViewThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                //Processar mensagens
            }
        };

        salvarThread = new HandlerThread("salvarThread");
        salvarThread.start();
        salvarHandler = new Handler(salvarThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
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

        fab_cadusu = (FloatingActionButton) findViewById(R.id.fab_cadusu);
        fab_cadusu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (CadastrarUsuariosActivity.currentDevice != null)
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

        //inicializando o spinner de funcoes
        FillSpinerRoles();

        listViewArrayList = new ArrayList<CadastrarTagid>();
        listViewAdapter = new CustomAdapterCadastrarTagid(CadastrarUsuariosActivity.this, listViewArrayList);
        lv_cadusu_tagid = (ListView) findViewById(R.id.lv_cadusu_tagid);
        lv_cadusu_tagid.setAdapter(listViewAdapter);
        lv_cadusu_tagid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listViewSelected != null)
                {
                    CadastrarTagid cadastrarTagidSelecionado = (CadastrarTagid) adapterView.getItemAtPosition(listViewSelected);
                    cadastrarTagidSelecionado.setIsSelected(false);
                }

                CadastrarTagid cadastrarTagid = (CadastrarTagid) adapterView.getItemAtPosition(i);

                if (!cadastrarTagid.getIsCadastrado())
                {
                    cadastrarTagid.setIsSelected(true);
                    listViewSelected = i;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else if (cadastrarTagid.getIsCadastrado())
                {
                    CadastrarTagid editarTagid = (CadastrarTagid) adapterView.getItemAtPosition(i);
                    String tagid = editarTagid.getTagid();

                    Intent intent = new Intent();
                    intent.setClass(CadastrarUsuariosActivity.this, EditarUsuariosActivity.class);
                    intent.putExtra(EditarFerramentasActivity.EXTRA_MESSAGE, tagid);
                    startActivity(intent);
                }
            }
        });

        et_cadusu_nomecompleto = (EditText) findViewById(R.id.et_cadusu_nomecompleto);
        et_cadusu_email = (EditText) findViewById(R.id.et_cadusu_email);
        et_cadusu_username = (EditText) findViewById(R.id.et_cadusu_username);

        ckb_cadusu_enviarsenha = (CheckBox) findViewById(R.id.ckb_cadusu_enviarsenha);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        intent = getIntent();
        extra_tagid = intent.getStringExtra(EXTRA_TAGID);

        //Se activity vier como intent de outra activity, usa-se o tagid para já incluir na lista e seleciona-lo
        PreencherListaTAGID(extra_tagid);

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
        super.onResume();

        //Obtendo o modelo preferencial
        modeloLeitor = LeitorPreferencial();

        ConectarDispositivoBT(modeloLeitor);
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
        inflater.inflate(R.menu.menu_act_cadastro_usuarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sync_ci:
                ESync.GetSyncInstance().SyncDatabase(CadastrarUsuariosActivity.this);
                return true;
            case R.id.action_limpar_ci:
                LimparListView();
                return true;
            case R.id.action_atualizar_ci:
                Salvar();
                return true;
            case R.id.action_reconectar_ci:
                ConectarDispositivoBT(modeloLeitor);
                return true;
            default:

                return false;
        }
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

    private void FillSpinerRoles()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FuncoesCU> funcoesCUList = dbInstance.gruposDAO().GetSpinnerItems();

                funcoesArrayList = new ArrayList<FuncoesCU>(funcoesCUList);
                funcoesAdapter = new CustomAdapterSpinnerFuncoes(CadastrarUsuariosActivity.this, funcoesArrayList);

                sp_cadusu_funcao = (Spinner) findViewById(R.id.sp_cadusu_funcao);
                sp_cadusu_funcao.setAdapter(funcoesAdapter);
                sp_cadusu_funcao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        FuncoesCU funcoesCU = (FuncoesCU) parentView.getItemAtPosition(position);
                        funcaoSelected = funcoesCU.getIdOriginal();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        //TODO
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        funcoesAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void ConectarDispositivoBT(String modeloLeitor)
    {
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            connectionBTHandler.post(new Runnable() {
                @Override
                public void run() {

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
            });
        }
    }

    private void StartReading()
    {
        reading = true;
        fab_cadusu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
    }

    private void StopReading()
    {
        reading = false;
        fab_cadusu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
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
                }
            }
        });
    }

    public void AtualizarListView(String tagid, boolean isSelected)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {
                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);
                CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByTAGID(tagid);
                Posicoes posicao = dbInstance.posicoesDAO().GetByTAGID(tagid);
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                CadastrarTagid cadastrarTagid = new CadastrarTagid();
                cadastrarTagid.setTagid(tagid);
                cadastrarTagid.setIsSelected(isSelected);

                if (!tagsLidosArrayList.contains(tagid))
                {
                    if (usuario != null)
                    {
                        cadastrarTagid.setCadastrado(true);

                    } else if (cadastroMateriais == null && cadastroEquipamentos == null && posicao == null && usuario == null){
                        cadastrarTagid.setCadastrado(false);

                    } else {
                        return;
                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tagsLidosArrayList.add(tagid);
                            listViewArrayList.add(cadastrarTagid);
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void Salvar()
    {
        salvarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (listViewSelected == null){
                    showMessage("AVISO", "Escolha o TAGID do usuário", 3);
                    return;
                } else if(funcaoSelected == null) {
                    showMessage("AVISO", "Informe a Função do usuário", 3);
                    return;
                }else if(et_cadusu_username.getText().toString() == null || et_cadusu_username.getText().toString().equals("0") || et_cadusu_username.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Username do usuário", 3);
                    return;
                }else if(et_cadusu_email.getText().toString() == null || et_cadusu_email.getText().toString().equals("0") || et_cadusu_email.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Email do usuário", 3);
                    return;
                }else if(et_cadusu_nomecompleto.getText().toString() == null || et_cadusu_nomecompleto.getText().toString().equals("0") || et_cadusu_nomecompleto.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Nome Completo do usuário", 3);
                    return;
                }

                String tagid = listViewAdapter.getItem(listViewSelected).getTagid();

                Usuarios usuario = dbInstance.usuariosDAO().GetByUsername(et_cadusu_username.getText().toString());
                if (usuario != null)
                {
                    showMessage("AVISO", "Nome de usuario já cadastrado no sistema", 3);
                    return;
                }

                UPMOBUsuarios upmobUsuarios = new UPMOBUsuarios();
                upmobUsuarios.setIdOriginal("0");
                upmobUsuarios.setUsername(et_cadusu_username.getText().toString());
                upmobUsuarios.setRoleIdOriginal(funcaoSelected);
                upmobUsuarios.setNomeCompleto(et_cadusu_nomecompleto.getText().toString());
                upmobUsuarios.setEmail(et_cadusu_email.getText().toString());
                upmobUsuarios.setTAGID(tagid);
                upmobUsuarios.setEnviarSenhaEmail(ckb_cadusu_enviarsenha.isChecked());
                upmobUsuarios.setDescricaoErro(null);
                upmobUsuarios.setFlagErro(false);
                upmobUsuarios.setFlagAtualizar(false);
                upmobUsuarios.setFlagProcess(false);
                upmobUsuarios.setCodColetor(Build.SERIAL);

                dbInstance.upmobUsuariosDAO().Create(upmobUsuarios);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Cadastro", "Cadastro Realizado com Sucesso", 1 );
                        closeWindowTimer();
                    }
                });
            }
        });
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

    public void closeWindowTimer()
    {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LimparListView();
                LimparDados();
            }
        }, 1500);
    }

    public void LimparListView()
    {
        listViewArrayList.clear();
        tagsLidosArrayList.clear();
        listViewSelected = null;

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public void LimparDados()
    {
        et_cadusu_username.setText(null);
        et_cadusu_nomecompleto.setText(null);
        et_cadusu_email.setText(null);
        ckb_cadusu_enviarsenha.setChecked(false);

    }

    private void PreencherListaTAGID(String tagid)
    {
        if (tagid != null)
        {
            AtualizarListView(tagid, true);

            listViewSelected = 0;
        }
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

    private void StartReadingVANCH75()
    {
        try{
            CadastrarUsuariosActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = CadastrarUsuariosActivity.currentDevice.getCmdResultWithTimeout(3000);
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
                CadastrarUsuariosActivity.currentDevice.listTagID(1, 0, 0);
                byte[] ret = CadastrarUsuariosActivity.currentDevice.getCmdResult();
                if (VH73Device.checkSucc(ret))
                {
                    VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);

                    //transformando cada tagid lido no padrao com sufixo "H" e atualizando a ListView
                    for(byte[] bs : listTagIDResult.epcs)
                    {
                        final String tagid = "H" + Utility.bytes2HexString(bs);

                        AtualizarListView(tagid, false);
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

    private void ConectarVANCH75(BluetoothDevice bluetoothDevice)
    {
        VH73Device vh75Device = new VH73Device(CadastrarUsuariosActivity.this, bluetoothDevice);

        boolean succ = vh75Device.connect();

        if (succ) {
            CadastrarUsuariosActivity.currentDevice = vh75Device;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CadastrarUsuariosActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CadastrarUsuariosActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void StartReadingDOTR900()
    {
        setOpMode(true, false, 1000, false);
        sendCmdInventory();
    }

    private void ConectarDOTR900(BluetoothDevice bluetoothDevice)
    {
        if (mConnected == false)
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
                Toast.makeText(CadastrarUsuariosActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtDisconnected(BluetoothDevice device)
    {}

    public void onBtConnectFail(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CadastrarUsuariosActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtDataSent(byte[] data)
    {}

    public void onBtDataTransException(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CadastrarUsuariosActivity.this, msg, Toast.LENGTH_SHORT).show();
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

            if( mConnected == false )
                break;

            if( parameter != null && parameter.length() > 4 && mLastCmd != null )
            {
                if (mLastCmd.equalsIgnoreCase(R900Protocol.CMD_INVENT))
                {
                    final String tagid = "H" + parameter.substring(0, parameter.length() - 4);

                    AtualizarListView(tagid, false);
                }
            }
            else
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {}
}
