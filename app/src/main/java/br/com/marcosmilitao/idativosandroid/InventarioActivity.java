package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dotel.libr900.BluetoothActivity;
import com.dotel.libr900.OnBtEventListener;
import com.dotel.libr900.R900Protocol;
import com.dotel.libr900.R900RecvPacketParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTagid;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterInventario;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerAlmoxarifados;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerFuncoes;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerPosicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosInv;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;
import br.com.marcosmilitao.idativosandroid.POJO.Inventario;
import br.com.marcosmilitao.idativosandroid.POJO.PosicoesInv;
import br.com.marcosmilitao.idativosandroid.ViewModel.TAGIDInventarioViewModel;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;

public class InventarioActivity extends BluetoothActivity implements OnBtEventListener {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private List<String> tagsLidosArrayList;

    private FloatingActionButton fab_inv;

    private ArrayList<AlmoxarifadosCP> almoxarifadosArrayList;
    private ArrayList<PosicoesInv> posicoesArrayList;
    private ArrayList<Inventario> listViewArrayList;

    private CustomAdapterSpinnerAlmoxarifados almoxarifadosAdapter;
    private CustomAdapterSpinnerPosicoes posicoesAdapter;
    private CustomAdapterInventario listViewAdapter;

    private Spinner sp_inv_almoxarifados;
    private Spinner sp_inv_posicao;

    private Handler updateListViewHandler;
    private Handler readTAGIDHandler;
    private Handler connectionBTHandler;
    private Handler salvarHandler;
    private Handler mainHandler;
    private Handler showTAGIDDetailsHandler;

    private HandlerThread updateListViewThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;
    private HandlerThread salvarThread;
    private HandlerThread showTAGIDDetailsThread;

    private static VH73Device currentDevice;
    private BluetoothDevice mConnectedDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private boolean reading = false;

    private Integer posicaoSelected = 0;

    public static final String TAG_READING = "reading";

    private ListView lv_inv_tagid;

    private TextView tv_inv_quantidade;
    private TextView et_tagid_in;
    private TextView et_datavalidade_in;
    private TextView et_posicao_in;
    private TextView et_patrimonio_in;
    private TextView et_numserie_in;
    private TextView et_tracenumber_in;
    private TextView et_produto_in;
    private TextView et_modelo_in;

    private AlertDialog.Builder adbuilder;

    private AlertDialog alert;

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
        setContentView(R.layout.activity_inventario);

        modelo_leitor_rfid_Default = this.getResources().getString(R.string.modelo_leitor_default);

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

        showTAGIDDetailsThread = new HandlerThread("showTAGIDDetailsThread");
        showTAGIDDetailsThread.start();
        showTAGIDDetailsHandler = new Handler(showTAGIDDetailsThread.getLooper())
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

        posicoesArrayList = new ArrayList<PosicoesInv>();
        posicoesAdapter = new CustomAdapterSpinnerPosicoes(InventarioActivity.this, posicoesArrayList);
        sp_inv_posicao = (Spinner) findViewById(R.id.sp_inv_posicao);
        sp_inv_posicao.setAdapter(posicoesAdapter);

        FillSpinerAlmoxarifados();

        fab_inv = (FloatingActionButton) findViewById(R.id.fab_inv);
        fab_inv.setOnClickListener(new View.OnClickListener()
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

        listViewArrayList = new ArrayList<Inventario>();
        listViewAdapter = new CustomAdapterInventario(InventarioActivity.this, listViewArrayList);
        lv_inv_tagid = (ListView) findViewById(R.id.lv_inv_tagid);
        lv_inv_tagid.setAdapter(listViewAdapter);
        lv_inv_tagid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Inventario inventario = (Inventario) lv_inv_tagid.getItemAtPosition(position);

                ShowTAGIDDetails(inventario.getCadastroMateriaisId());
                return false;
            }
        });

        tv_inv_quantidade = (TextView) findViewById(R.id.tv_inv_quantidade);

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
        super.onResume();

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
        inflater.inflate(R.menu.menu_act_inventario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.inventario_sync:
                ESync.GetSyncInstance().SyncDatabase(InventarioActivity.this);
                return true;
            case R.id.inventario_limpar:
                LimparListView();
                return true;
            case R.id.inventarioAtualizar:

                showMessage("ATENÇÃO", "Você não está autorizado a realizar essa ação", 3);

                return true;
            case R.id.inventario_conexao:
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
        mConnectedDevice = null;

        BA.disable();
        BA.enable();
        this.finish();
    }

    private void StartReading()
    {
        reading = true;
        fab_inv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
    }

    private void StartReadingVANCH75()
    {
        try{
            InventarioActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = InventarioActivity.currentDevice.getCmdResultWithTimeout(3000);
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
                InventarioActivity.currentDevice.listTagID(1, 0, 0);
                byte[] ret = InventarioActivity.currentDevice.getCmdResult();
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

    private void StopReading()
    {
        fab_inv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));

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

    public void AtualizarListView(String tagid)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {
                PosicoesInv posicaoInv = (PosicoesInv) sp_inv_posicao.getSelectedItem();

                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);
                Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(posicaoInv.getIdOriginal());

                if (cadastroMateriais != null && posicao != null)
                {
                    if (!tagsLidosArrayList.contains(tagid))
                    {
                        ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                        if (modeloMateriais != null)
                        {
                            Inventario inventario = new Inventario(cadastroMateriais.getIdOriginal(),
                                    cadastroMateriais.getTAGID(),
                                    modeloMateriais.getModelo(),
                                    modeloMateriais.getPartNumber(),
                                    posicao.getIdOriginal(),
                                    posicao.getDescricao(),
                                    false);

                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tagsLidosArrayList.add(tagid);
                                    listViewArrayList.add(inventario);
                                    listViewAdapter.notifyDataSetChanged();

                                    //atualizando o contador de TOTAL ENCONTRADO
                                    tv_inv_quantidade.setText("TOTAL ENCONTRADO: " + tagsLidosArrayList.size());
                                }
                            });
                        }
                    }
                }
            }
        });
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

    private void FillSpinerAlmoxarifados()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int baseId = dbInstance.parametrosPadraoDAO().GetBaseId();
                List<AlmoxarifadosCP> almoxarifadosList = dbInstance.almoxarifadosDAO().GetSpinnerItems(baseId);

                almoxarifadosArrayList = new ArrayList<AlmoxarifadosCP>(almoxarifadosList);
                almoxarifadosAdapter = new CustomAdapterSpinnerAlmoxarifados(InventarioActivity.this, almoxarifadosArrayList);

                sp_inv_almoxarifados = (Spinner) findViewById(R.id.sp_inv_almoxarifado);
                sp_inv_almoxarifados.setAdapter(almoxarifadosAdapter);
                sp_inv_almoxarifados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        AlmoxarifadosCP almoxarifados = (AlmoxarifadosCP) parentView.getItemAtPosition(position);

                        FillSpinerPosicao(almoxarifados.getIdOriginal());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        //TODO
                    }
                });

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        almoxarifadosAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void FillSpinerPosicao(int almoxarifadoId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PosicoesInv> posicoesList = dbInstance.posicoesDAO().GetSpinnerItems(almoxarifadoId);

                posicoesArrayList.clear();
                posicoesArrayList.addAll(posicoesList);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (posicoesArrayList.size() > 0)
                        {
                            posicoesAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

    public void LimparListView()
    {
        listViewArrayList.clear();
        tagsLidosArrayList.clear();

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();

                //atualizando o contador de TOTAL ENCONTRADO
                tv_inv_quantidade.setText("TOTAL ENCONTRADO: " + tagsLidosArrayList.size());
            }
        });
    }

    private void Salvar()
    {
        salvarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (tagsLidosArrayList.size() == 0){
                    showMessage("AVISO", "Nenhuma ferramenta encontrada", 3);
                    return;
                }

                for (Inventario inventario : listViewArrayList)
                {
                    UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao = new UPMOBHistoricoLocalizacao();
                    upmobHistoricoLocalizacao.setCadastroMateriaisId(inventario.getCadastroMateriaisId());
                    upmobHistoricoLocalizacao.setPosicaoId(inventario.getPosicaoId());
                    upmobHistoricoLocalizacao.setProcesso("Inventario");
                    upmobHistoricoLocalizacao.setFlagProcess(false);
                    upmobHistoricoLocalizacao.setFlagErro(false);
                    upmobHistoricoLocalizacao.setDataHoraEvento(Calendar.getInstance().getTime());
                    upmobHistoricoLocalizacao.setCodColetor(Build.SERIAL);

                    dbInstance.upmobHistoricoLocalizacaoDAO().Create(upmobHistoricoLocalizacao);
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Inventario", "Inventario salvo com Sucesso", 1 );
                        closeWindowTimer();
                    }
                });
            }
        });
    }

    public void closeWindowTimer()
    {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LimparListView();
            }
        }, 1500);
    }

    public void ShowTAGIDDetails(int idOriginal){

        showTAGIDDetailsHandler.post(new Runnable() {
            @Override
            public void run() {
                adbuilder = new AlertDialog.Builder(InventarioActivity.this);
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

                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(idOriginal);

                if (cadastroMateriais != null)
                {
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());
                    Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(cadastroMateriais.getPosicaoOriginalItemIdoriginal());

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            et_tagid_in.setText(cadastroMateriais.getTAGID());
                            et_datavalidade_in.setText(cadastroMateriais.getDataValidadeInspecao() != null ? cadastroMateriais.getDataValidadeInspecao().toString() : null);
                            et_posicao_in.setText(posicao.getDescricao());
                            et_patrimonio_in.setText(cadastroMateriais.getPatrimonio());
                            et_numserie_in.setText(cadastroMateriais.getNumSerie());
                            et_tracenumber_in.setText(modeloMateriais.getPartNumber());
                            et_produto_in.setText(modeloMateriais.getIDOmni());
                            et_modelo_in.setText(modeloMateriais.getModelo());

                            alert = adbuilder.create();
                            alert.show();
                        }
                    });
                }
            }
        });
    }

    private void ConectarVANCH75(BluetoothDevice bluetoothDevice)
    {
        connectionBTHandler.post(new Runnable() {
            @Override
            public void run() {
                VH73Device vh75Device = new VH73Device(InventarioActivity.this, bluetoothDevice);

                boolean succ = vh75Device.connect();

                if (succ) {
                    InventarioActivity.currentDevice = vh75Device;
                    mConnectedDevice = bluetoothDevice;

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InventarioActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InventarioActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                Toast.makeText(InventarioActivity.this, "Leitor conectado!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(InventarioActivity.this, "Leitor desconectado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtConnectFail(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InventarioActivity.this, "Não foi possível conectar ao leitor. Tente novamente!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(InventarioActivity.this, msg, Toast.LENGTH_SHORT).show();
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
