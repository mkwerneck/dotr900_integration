package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTagid;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerAlmoxarifados;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerFuncoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;

public class CadastrarPosicoesActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private CustomAdapterSpinnerAlmoxarifados almoxarifadosAdapter;
    private CustomAdapterCadastrarTagid listViewAdapter;

    private ArrayList<AlmoxarifadosCP> almoxarifadosArrayList;
    private ArrayList<CadastrarTagid> listViewArrayList;
    private List<String> tagsLidosArrayList;

    private EditText et_cadpos_codigo, et_cadpos_descricao;

    private Spinner sp_cadpos_almoarifado;

    private ListView lv_cadpos_tagid;

    private FloatingActionButton fab_cadpos;

    private static VH73Device currentDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private boolean reading = false;

    private Integer listViewSelected = null;
    private Integer almoxarifadoSelected = null;

    private Handler updateListViewHandler;
    private Handler readTAGIDHandler;
    private Handler connectionBTHandler;
    private Handler salvarHandler;
    private Handler mainHandler;

    private HandlerThread updateListViewThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;
    private HandlerThread salvarThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_posicoes);

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

        fab_cadpos = (FloatingActionButton) findViewById(R.id.fab_cadpos);
        fab_cadpos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (CadastrarPosicoesActivity.currentDevice != null)
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

        //TODO SPINNER COM ALMOXARIFADOS

        listViewArrayList = new ArrayList<CadastrarTagid>();
        listViewAdapter = new CustomAdapterCadastrarTagid(CadastrarPosicoesActivity.this, listViewArrayList);
        lv_cadpos_tagid = (ListView) findViewById(R.id.lv_cadpos_tagid);
        lv_cadpos_tagid.setAdapter(listViewAdapter);
        lv_cadpos_tagid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    intent.setClass(CadastrarPosicoesActivity.this, EditarUsuariosActivity.class);
                    intent.putExtra(EditarFerramentasActivity.EXTRA_MESSAGE, tagid);
                    startActivity(intent);
                }
            }
        });

        et_cadpos_codigo = (EditText) findViewById(R.id.et_cadpos_codigo);
        et_cadpos_descricao = (EditText) findViewById(R.id.et_cadpos_descricao);

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();
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
                    VH73Device vh75Device = new VH73Device(CadastrarPosicoesActivity.this, bluetoothDevice);

                    boolean succ = vh75Device.connect();

                    if (succ) {
                        CadastrarPosicoesActivity.currentDevice = vh75Device;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CadastrarPosicoesActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CadastrarPosicoesActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
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
        fab_cadpos.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read();
    }

    private void StopReading()
    {
        reading = false;
        fab_cadpos.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    private void Read()
    {
        readTAGIDHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    CadastrarPosicoesActivity.currentDevice.SetReaderMode((byte) 1);
                    byte[] res = CadastrarPosicoesActivity.currentDevice.getCmdResultWithTimeout(3000);
                    if (!VH73Device.checkSucc(res)) {
                        StopReading();

                        return;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) { // timeout
                    Log.i("READING", "Timeout!!@");
                }

                while (reading)
                {
                    long lnow = SystemClock.uptimeMillis();

                    //TODO LEITURA
                    try{
                        CadastrarPosicoesActivity.currentDevice.listTagID(1, 0, 0);
                        byte[] ret = CadastrarPosicoesActivity.currentDevice.getCmdResult();
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

    /*private void FillSpinerRoles()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FuncoesCU> funcoesCUList = dbInstance.gruposDAO().GetSpinnerItems();

                almoxarifadosArrayList = new ArrayList<AlmoxarifadosCP>(funcoesCUList);
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
    }*/

    public void AtualizarListView(String tagid)
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
                cadastrarTagid.setIsSelected(false);

                if (!tagsLidosArrayList.contains(tagid))
                {
                    if (posicao != null)
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
        et_cadpos_codigo.setText(null);
        et_cadpos_descricao.setText(null);
    }
}
