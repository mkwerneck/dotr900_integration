package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.dotel.libr900.BluetoothActivity;
import com.dotel.libr900.OnBtEventListener;
import com.dotel.libr900.R900Protocol;
import com.dotel.libr900.R900RecvPacketParser;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTagid;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterModeloMateriais;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterPosicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapter;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class CadastrarFerramentasActivity extends BluetoothActivity implements OnBtEventListener {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private CustomAdapterModeloMateriais modeloMateriaisAdapter;
    private CustomAdapterPosicoes posicoesAdapter;
    private CustomAdapterCadastrarTagid listViewAdapter;

    private ArrayList<ModeloMateriaisCF> modeloMateriaisArrayList;
    private ArrayList<PosicaoCF> posicoesArrayList;
    private ArrayList<CadastrarTagid> listViewArrayList;
    private List<String> tagsLidosArrayList;

    private AppCompatAutoCompleteTextView et_cadfer_modelo, et_cadfer_posicao;

    private TextView et_cadfer_numserie, et_cadfer_patrimonio, et_cadfer_quantidade, et_notafiscal;
    private TextView tvsp_cadfer_dtnotafiscal, tvsp_cadfer_dtvalidade, tvsp_cadfer_dtfabricacao;

    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    private SlideDateTimeListener slideListenernf, slideListenerdv, slideListenerdf;

    private CurrencyEditText et_cadfer_valorunitario;

    private ListView lv_cadfer_tagid;

    private FloatingActionButton fab_cadfer;

    private static VH73Device currentDevice;
    private BluetoothDevice mConnectedDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private boolean reading = false;

    private Integer listViewSelected = null;
    private Integer posicaoSelected = null;
    private Integer modeloSelected = null;

    public static final String TAG_READING = "reading";
    public static final String TAG = "inventory";
    public static final String EXTRA_TAGID = "tagid";
    private String extra_tagid = null;

    private Handler connectionBTHandler;
    private Handler readTAGIDHandler;
    private Handler mainHandler;

    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;

    public static class InventoryEvent {
    }

    public static class InventoryTerminal {
    }

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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_ferramentas);

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

        mainHandler = new Handler();

        //iniciando a toolbar para a activity
        setupToolbar();

        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        //inicializando Bluetooth propriedades
        foundDevices = new ArrayList<BluetoothDevice>();
        tagsLidosArrayList = new ArrayList<String>();

        fab_cadfer = (FloatingActionButton) findViewById(R.id.fab_cadfer);
        fab_cadfer.setOnClickListener(new View.OnClickListener(){

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

        FillAdapterModeloMateriais();
        FillAdapterPosicoes();
        //FillListViewTeste();

        //determinando os listeners dos datepickers
        slideListenernf = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_cadfer_dtnotafiscal.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };
        slideListenerdv = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_cadfer_dtvalidade.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };
        slideListenerdf = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_cadfer_dtfabricacao.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };

        tvsp_cadfer_dtnotafiscal = (TextView) findViewById(R.id.tvsp_cadfer_dtnotafiscal);
        tvsp_cadfer_dtnotafiscal.setText("DD/MM/AAAA");
        tvsp_cadfer_dtnotafiscal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slideListenernf);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        tvsp_cadfer_dtvalidade = (TextView) findViewById(R.id.tvsp_cadfer_dtvalidade);
        tvsp_cadfer_dtvalidade.setText("DD/MM/AAAA");
        tvsp_cadfer_dtvalidade.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slideListenerdv);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        tvsp_cadfer_dtfabricacao = (TextView) findViewById(R.id.tvsp_cadfer_dtfabricacao);
        tvsp_cadfer_dtfabricacao.setText("DD/MM/AAAA");
        tvsp_cadfer_dtfabricacao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slideListenerdf);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        et_cadfer_valorunitario = (CurrencyEditText) findViewById(R.id.et_cadfer_valorunitario);

        //Inicializando ArrayList, Adapter e Objeto da ListView
        listViewArrayList = new ArrayList<CadastrarTagid>();
        listViewAdapter = new CustomAdapterCadastrarTagid(CadastrarFerramentasActivity.this, listViewArrayList );
        lv_cadfer_tagid = (ListView) findViewById(R.id.lv_cadfer_tagid);
        lv_cadfer_tagid.setAdapter(listViewAdapter);
        lv_cadfer_tagid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    intent.setClass(CadastrarFerramentasActivity.this, EditarFerramentasActivity.class);
                    intent.putExtra(EditarFerramentasActivity.EXTRA_MESSAGE, tagid);
                    startActivity(intent);
                }
            }
        });

        et_cadfer_numserie = (TextView) findViewById(R.id.et_cadfer_numserie);
        et_cadfer_patrimonio = (TextView) findViewById(R.id.et_cadfer_patrimonio);
        et_cadfer_quantidade = (TextView) findViewById(R.id.et_cadfer_quantidade);
        et_notafiscal = (TextView) findViewById(R.id.et_notafiscal);

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
        EventBus.getDefault().register(this);
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
        EventBus.getDefault().unregister(this);
        CloseAct();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cadastro_ferramentas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_limpar_cf:
                LimparListView();
                return true;
            case R.id.action_reconectar_cf:
                ConectarDispositivoBT(modeloLeitor);
                return true;
            case R.id.action_salvar_cf:
                Salvar();
                return true;
            case R.id.action_sync_cf:
                ESync.GetSyncInstance().SyncDatabase(CadastrarFerramentasActivity.this);
                return true;
            default:

                return false;
        }
    }

    void setupToolbar()
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

    private void FillAdapterModeloMateriais()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ModeloMateriaisCF> modeloMateriaisList = dbInstance.modeloMateriaisDAO().GetAllModelosCustomAdapter();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        modeloMateriaisArrayList = new ArrayList<ModeloMateriaisCF>(modeloMateriaisList);
                        modeloMateriaisAdapter = new CustomAdapterModeloMateriais(CadastrarFerramentasActivity.this, modeloMateriaisArrayList, dbInstance);

                        et_cadfer_modelo = (AppCompatAutoCompleteTextView) findViewById(R.id.et_cadfer_modelo);
                        et_cadfer_modelo.setAdapter(modeloMateriaisAdapter);
                        et_cadfer_modelo.setThreshold(1);
                        et_cadfer_modelo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ModeloMateriaisCF modeloMateriaisCF = (ModeloMateriaisCF) adapterView.getItemAtPosition(i);
                                modeloSelected = modeloMateriaisCF.getIdOriginal();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void FillAdapterPosicoes()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PosicaoCF> posicoesList = dbInstance.posicoesDAO().GetAllPosicoesCustomAdapter();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        posicoesArrayList = new ArrayList<PosicaoCF>(posicoesList);
                        posicoesAdapter = new CustomAdapterPosicoes(CadastrarFerramentasActivity.this, posicoesArrayList, dbInstance);

                        et_cadfer_posicao = (AppCompatAutoCompleteTextView) findViewById(R.id.et_cadfer_posicao);
                        et_cadfer_posicao.setAdapter(posicoesAdapter);
                        et_cadfer_posicao.setThreshold(1);
                        et_cadfer_posicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                PosicaoCF posicaoCF = (PosicaoCF) adapterView.getItemAtPosition(i);
                                posicaoSelected = posicaoCF.getIdOriginal();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void FillListViewTeste()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> tagisList = dbInstance.cadastroMateriaisDAO().TesteTelaCadastroFerramentas();

                for(String tagid : tagisList)
                {
                    CadastrarTagid cadastrarTagid = new CadastrarTagid(tagid, true, false);
                    listViewArrayList.add(cadastrarTagid);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void CloseAct()
    {
        BA.disable();
        BA.enable();
        this.finish();
    }

    public void Listar()
    {
        queryPairedDevices();
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bt : pairedDevices) {
            connect(bt);
        }
    }

    //CLASSE SEM ENTENDIMENTO
    public void queryPairedDevices()
    {
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
                                ConfigUI.setConfigLastConnect(CadastrarFerramentasActivity.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(CadastrarFerramentasActivity.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                        }
                    }.start();
                }
            }
        }
    }

    private void connect(final BluetoothDevice device)
    {
        new Thread() {
            public void run() {
                VH73Device vh75Device = new VH73Device(CadastrarFerramentasActivity.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {
                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(CadastrarFerramentasActivity.this, currentDevice.getAddress());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    EventBus.getDefault().post(new SettingsActivity.GetHandsetParam());

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.start();
    }

    public void onEventBackgroundThread(CadastrarFerramentasActivity.InventoryEvent e)
    {
        int i = 0;
        try {
            CadastrarFerramentasActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = CadastrarFerramentasActivity.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                reading = false;
                EventBus.getDefault().post(new CadastrarFerramentasActivity.InventoryTerminal());
                return;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (TimeoutException e1) { // timeout
            Log.i(TAG, "Timeout!!@");
        }

        while (reading) {
            long lnow = android.os.SystemClock.uptimeMillis();
            doInventory();
            while (true) {
                long lnew = android.os.SystemClock.uptimeMillis();
                if (lnew - lnow > 50) {
                    break;
                }
            }
        }

        EventBus.getDefault().post(new CadastrarFerramentasActivity.InventoryTerminal());

        try {
            CadastrarFerramentasActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = CadastrarFerramentasActivity.currentDevice.getCmdResultWithTimeout(3000);
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

    public void onEventMainThread(CadastrarFerramentasActivity.InventoryTerminal e)
    {
        reading = false;
    }

    private void doInventory()
    {
        try {
            CadastrarFerramentasActivity.currentDevice.listTagID(1, 0, 0);
            byte[] ret = CadastrarFerramentasActivity.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    CadastrarFerramentasActivity.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    CadastrarFerramentasActivity.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = CadastrarFerramentasActivity.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void addEpc(VH73Device.ListTagIDResult list)
    {
        ArrayList<byte[]> epcs = list.epcs;
        for (byte[] bs : epcs) {
            final  String tagid = "H" + Utility.bytes2HexString(bs);

            try {
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AtualizarListView(tagid.trim(), false);
                    }
                });
            } catch (Exception e) {
                showMessage("AVISO",e.toString(), 2);
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

    public void AtualizarListView(String tagid, boolean isSelected)
    {
        new Thread(new Runnable() {
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
                    if (cadastroMateriais != null)
                    {
                        //se ferramenta nao estiver em uso (descartada) não adicionaomos a lista
                        if (!cadastroMateriais.isEmUso())
                        {
                            return;
                        }

                        cadastrarTagid.setCadastrado(true);

                    } else if (cadastroMateriais == null && cadastroEquipamentos == null && posicao == null && usuario == null){
                        cadastrarTagid.setCadastrado(false);

                    } else {
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tagsLidosArrayList.add(tagid);
                            listViewArrayList.add(cadastrarTagid);
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void LimparListView()
    {
        listViewArrayList.clear();
        tagsLidosArrayList.clear();
        listViewSelected = null;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public void LimparDados()
    {
        posicaoSelected = null;
        modeloSelected = null;

        et_cadfer_modelo.setText(null);
        et_cadfer_posicao.setText(null);
        et_cadfer_numserie.setText(null);
        et_cadfer_patrimonio.setText(null);
        et_cadfer_quantidade.setText(null);
        et_cadfer_valorunitario.setText(null);
        et_notafiscal.setText(null);
        tvsp_cadfer_dtnotafiscal.setText("DD/MM/AAAA");
        tvsp_cadfer_dtvalidade.setText("DD/MM/AAAA");
        tvsp_cadfer_dtfabricacao.setText("DD/MM/AAAA");
    }

    public void Salvar()
    {
        if (listViewSelected == null){
            showMessage("AVISO", "Escolha o TAGID da Ferramenta", 3);

        } else if(posicaoSelected == null) {
            showMessage("AVISO", "Informe a Posição Original da Ferramenta", 3);

        } else if(modeloSelected == null) {
            showMessage("AVISO", "Informe o Modelo da Ferramenta", 3);

        } else if(et_notafiscal.getText().toString() == null || et_notafiscal.getText().toString().equals("0") || et_notafiscal.getText().toString().equals("")){
            showMessage("AVISO", "Informe a Nota Fiscal da Ferramenta", 3);

        } else if(et_cadfer_quantidade.getText().toString() == null || et_cadfer_quantidade.getText().toString().equals("0") || et_cadfer_quantidade.getText().toString().equals("")){
            showMessage("AVISO", "Informe a Quantidade da Ferramenta", 3);

        }else if(tvsp_cadfer_dtnotafiscal.getText().toString().equals("DD/MM/AAAA")){
            showMessage("AVISO", "Informe a Data de Entrada da Nota Fiscal da Ferramenta", 3);

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Date dataHoraEvento = Calendar.getInstance().getTime();

                    String tagid = listViewAdapter.getItem(listViewSelected).getTagid();
                    Date dataValidade, dataEntradaNF, dataFabricacao;
                    BigDecimal valorUnitario;

                    //Formatando as datas Validade e Entrada NF
                    if (ValidarData(tvsp_cadfer_dtvalidade.getText().toString()) != null){
                        dataValidade = ValidarData(tvsp_cadfer_dtvalidade.getText().toString());
                    } else {
                        dataValidade = null;
                    }

                    if (ValidarData(tvsp_cadfer_dtnotafiscal.getText().toString()) != null){
                        dataEntradaNF = ValidarData(tvsp_cadfer_dtnotafiscal.getText().toString());
                    } else {

                        //Se a data de entrada da nota fiscal não for valida, saímos da função
                        showMessage("AVISO", "Data de Entrada da Nota Fiscal inválida", 3);

                        return;
                    }

                    if (ValidarData(tvsp_cadfer_dtfabricacao.getText().toString()) != null){
                        dataFabricacao = ValidarData(tvsp_cadfer_dtfabricacao.getText().toString());
                    } else {
                        dataFabricacao = null;
                    }

                    //Formatando Valor Unitario
                    valorUnitario = FormatarValor(String.valueOf(et_cadfer_valorunitario.getRawValue()));

                    UPMOBCadastroMateriais upmobCadastroMateriais = new UPMOBCadastroMateriais();
                    upmobCadastroMateriais.setIdOriginal(0);
                    upmobCadastroMateriais.setPatrimonio(et_cadfer_patrimonio.getText().toString());
                    upmobCadastroMateriais.setNumSerie(et_cadfer_numserie.getText().toString());
                    upmobCadastroMateriais.setQuantidade(Integer.parseInt(et_cadfer_quantidade.getText().toString()));
                    upmobCadastroMateriais.setDadosTecnicos("");
                    upmobCadastroMateriais.setTAGID(tagid);
                    upmobCadastroMateriais.setPosicaoOriginalItemId(posicaoSelected);
                    upmobCadastroMateriais.setModeloMateriaisItemId(modeloSelected);
                    upmobCadastroMateriais.setNotaFiscal(et_notafiscal.getText().toString());
                    upmobCadastroMateriais.setValorUnitario(valorUnitario.doubleValue());
                    upmobCadastroMateriais.setCodColetor(Build.SERIAL);
                    upmobCadastroMateriais.setDescricaoErro(null);
                    upmobCadastroMateriais.setFlagErro(false);
                    upmobCadastroMateriais.setFlagAtualizar(false);
                    upmobCadastroMateriais.setFlagProcess(false);
                    upmobCadastroMateriais.setDataHoraEvento(dataHoraEvento);
                    upmobCadastroMateriais.setDataValidade(dataValidade);
                    upmobCadastroMateriais.setDataEntradaNotaFiscal(dataEntradaNF);
                    upmobCadastroMateriais.setDataFabricacao(dataFabricacao);

                    dbInstance.upmobCadastroMateriaisDAO().Create(upmobCadastroMateriais);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Cadastro", "Cadastro Realizado com Sucesso", 1 );
                            closeWindowTimer();
                        }
                    });
                }
            }).start();
        }
    }

    public Date ValidarData(String data_validar)
    {
        Date date = null;
        Date dataMinimo = null;
        Date dataMaximo = null;

        //tentando fazer o parse da data minima e maxima do SQL Server
        try {
            dataMinimo = formato.parse("01/01/1753");
        } catch(ParseException e)
        {
            dataMinimo = null;
        }

        try {
            dataMaximo = formato.parse("31/12/9999");
        } catch(ParseException e)
        {
            dataMaximo = null;
        }


        if (data_validar == null){
            return date;
        }

        try {
            date = formato.parse(data_validar);
        } catch (Exception e){
            return date;
        }

        //Verificando se a data não é inválida
        if (date.before(dataMinimo) || date.after(dataMaximo))
        {
            date = null;
        }

        return date;
    }

    public BigDecimal FormatarValor(String valorRaw)
    {
        BigDecimal bigv;
        int count = valorRaw.length();
        String newValue = new String();

        if (count == 1){
            newValue = "0.0" + valorRaw;
        } else if (count == 2){
            newValue = "0." + valorRaw;
        } else {
            newValue = valorRaw.substring(0, (count - 2)) + "." + valorRaw.substring((count - 2), count);
        }

        bigv = new BigDecimal(newValue);

        return bigv;
    }

    private void closeWindowTimer()
    {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LimparListView();
                LimparDados();
            }
        }, 1500);
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

    private void ConectarVANCH75(BluetoothDevice bluetoothDevice)
    {
        connectionBTHandler.post(new Runnable() {
            @Override
            public void run() {
                VH73Device vh75Device = new VH73Device(CadastrarFerramentasActivity.this, bluetoothDevice);

                boolean succ = vh75Device.connect();

                if (succ) {

                    CadastrarFerramentasActivity.currentDevice = vh75Device;
                    mConnectedDevice = bluetoothDevice;

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CadastrarFerramentasActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CadastrarFerramentasActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
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

    private void StartReading()
    {
        reading = true;
        fab_cadfer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
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

    private void StartReadingDOTR900()
    {
        sendInventParam(1, 5, 0);
        setOpMode(false, false, 0, false);
        sendCmdInventory();
    }

    private void StartReadingVANCH75()
    {
        try{
            CadastrarFerramentasActivity.currentDevice.SetReaderMode((byte) 1);
            byte[] res = CadastrarFerramentasActivity.currentDevice.getCmdResultWithTimeout(3000);
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
                CadastrarFerramentasActivity.currentDevice.listTagID(1, 0, 0);
                byte[] ret = CadastrarFerramentasActivity.currentDevice.getCmdResult();
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

    private void StopReading()
    {
        fab_cadfer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));

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
                Toast.makeText(CadastrarFerramentasActivity.this, "Leitor conectado!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CadastrarFerramentasActivity.this, "Leitor desconectado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtConnectFail(BluetoothDevice device, String msg)
    {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CadastrarFerramentasActivity.this, "Não foi possível conectar ao leitor. Tente novamente!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CadastrarFerramentasActivity.this, msg, Toast.LENGTH_SHORT).show();
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

                AtualizarListView(tagid.toUpperCase(), false);
            }
        }
    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) { }
    }
}
