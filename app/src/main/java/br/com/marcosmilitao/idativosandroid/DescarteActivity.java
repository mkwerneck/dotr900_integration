package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Closeable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBUsuariosSet;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.Processos.NovoProcessoActivity;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterDescarte;
import br.com.marcosmilitao.idativosandroid.helper.Descarte;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class DescarteActivity extends AppCompatActivity {
    /*public SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    public static VH73Device currentDevice;
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
    private ListView lv_descarte;
    private FloatingActionButton fab_descarte, fab_descarte_usr;

    private ArrayList<Descarte> arrayTAGID;
    private ArrayList<String> tags;
    private Descarte descarteItem;
    private Button btn_lv_custom_descarte, btn_cancelar_comments_ad, btn_OK_comments_ad, btn_cancelar_comments_usr, btn_OK_comments_usr;
    private Sync sync;
    private boolean inventoring = false, readingUser = false;
    public int readCount = 0;
    public static final String TAG = "inventory";
    private Query_InventarioMaterial query_inventarioMaterial;
    private Query_UPWEBUsuariosSet query_upwebUsuariosSet;
    private String comments, usuario;
    private AlertDialog.Builder etbuilder, usrbuilder;

    private TextView tv_comments_usuario;
    private AlertDialog alert;

    public static class InventoryEvent {
    }
    public static class InventoryTerminal {
    }
    public class myCancelBtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View view){

        }
    }*/

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private CustomAdapterDescarte listViewAdapter;

    private ArrayList<Descarte> listViewArrayList;
    private ArrayList<String> tagsLidosArrayList;

    private ListView lv_descarte;

    private FloatingActionButton fab_descarte, fab_descarte_usr;

    private static VH73Device currentDevice;
    private List<BluetoothDevice> foundDevices;
    private Set<BluetoothDevice> pairedDevices;

    private String selectedUsuario = null;

    private boolean reading = false;
    private boolean readingUser = false;

    private Handler updateListViewHandler;
    private Handler readTAGIDHandler;
    private Handler connectionBTHandler;
    private Handler salvarHandler;
    private Handler mainHandler;

    private HandlerThread updateListViewThread;
    private HandlerThread connectionBTThread;
    private HandlerThread readTAGIDThread;
    private HandlerThread salvarThread;

    private AlertDialog.Builder motivoDescarteBuilder, usuarioResponsavelBuilder;

    private EditText et_comment_comments_ad;

    private Button btn_cancelar_comments_ad, btn_OK_comments_ad, btn_cancelar_comments_usr, btn_OK_comments_usr;

    private AlertDialog alert;

    private TextView tv_comments_usuario;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarte);

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

        fab_descarte = (FloatingActionButton) findViewById(R.id.fab_descarte);
        fab_descarte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(DescarteActivity.currentDevice != null){
                    if (reading == false)
                    {
                        readingUser = false;
                        StartReading(fab_descarte);
                    } else {
                        StopReading(fab_descarte);
                    }
                }else{
                    ConectarDispositivoBT();
                }
            }
        });

        listViewArrayList = new ArrayList<Descarte>();
        listViewAdapter = new CustomAdapterDescarte(DescarteActivity.this, listViewArrayList);
        listViewAdapter.setListener(new CustomAdapterDescarte.Listener() {
            @Override
            public void onClick(int position) {
                String tagid = (String) listViewAdapter.getItem(position).getTitle();
                Descarte descarte = (Descarte) listViewAdapter.getItem(position);

                tagsLidosArrayList.remove(tagid);
                listViewArrayList.remove(descarte);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        lv_descarte = (ListView) findViewById(R.id.lv_descarte);
        lv_descarte.setAdapter(listViewAdapter);
        lv_descarte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowEditTextMessage(i);
            }
        });

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        //PreencherArrayTeste();
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

    private void CloseAct()
    {
        BA.disable();
        BA.enable();
        this.finish();
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

    private void StartReading(FloatingActionButton fab)
    {
        reading = true;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F30808")));

        Read(fab);
    }

    private void StopReading(FloatingActionButton fab)
    {
        reading = false;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41C05A")));
    }

    private void ConectarDispositivoBT()
    {
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            connectionBTHandler.post(new Runnable() {
                @Override
                public void run() {
                    VH73Device vh75Device = new VH73Device(DescarteActivity.this, bluetoothDevice);

                    boolean succ = vh75Device.connect();

                    if (succ) {
                        DescarteActivity.currentDevice = vh75Device;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DescarteActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DescarteActivity.this, "Leitor não conectado. Tente novamente!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void Read(FloatingActionButton fab)
    {
        readTAGIDHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    DescarteActivity.currentDevice.SetReaderMode((byte) 1);
                    byte[] res = DescarteActivity.currentDevice.getCmdResultWithTimeout(3000);
                    if (!VH73Device.checkSucc(res)) {
                        StopReading(fab);

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
                        DescarteActivity.currentDevice.listTagID(1, 0, 0);
                        byte[] ret = DescarteActivity.currentDevice.getCmdResult();
                        if (VH73Device.checkSucc(ret))
                        {
                            VH73Device.ListTagIDResult listTagIDResult = VH73Device.parseListTagIDResult(ret);

                            //transformando cada tagid lido no padrao com sufixo "H" e atualizando a ListView
                            for(byte[] bs : listTagIDResult.epcs)
                            {
                                final String tagid = "H" + Utility.bytes2HexString(bs);

                                if (readingUser == false)
                                {
                                    AtualizarListView(tagid);
                                } else
                                {
                                    AtualizarEditTextUsuario(tagid);
                                }
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

    public void AtualizarListView(String tagid)
    {
        updateListViewHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!tagsLidosArrayList.contains(tagid))
                {
                    CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);

                    if (cadastroMateriais != null)
                    {
                        ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());

                        if (modeloMateriais != null)
                        {
                            String titulo = cadastroMateriais.getTAGID();
                            String subtitulo = modeloMateriais.getModelo() + " | " + modeloMateriais.getIDOmni();
                            int idOriginal = cadastroMateriais.getIdOriginal();

                            Descarte descarte = new Descarte(titulo, subtitulo, idOriginal);

                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tagsLidosArrayList.add(tagid);
                                    listViewArrayList.add(descarte);
                                    listViewAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void AtualizarEditTextUsuario(String tagid)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                if (usuario != null)
                {
                    selectedUsuario = usuario.getIdOriginal();

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_comments_usuario.setText(usuario.getUserName());

                            StopReading(fab_descarte_usr);
                        }
                    });
                }
            }
        }).start();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_descarte,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_limpar_descarte:
                LimparListView();
                return true;

            case R.id.action_reconectar_descarte:
                ConectarDispositivoBT();
                return true;

            case R.id.action_atualizar_descarte:
                if (tagsLidosArrayList.size() == 0){
                    showMessage("AVISO", "Nenhum TAGID selecionado. Leia um TAG para prosseguir", 3);
                    return false;
                }

                if (!CheckForNullComments())
                {
                    showMessage("AVISO", "Insira o motivo de descarte dos itens clicando sobre cada ferramenta na lista.", 3);
                    return false;
                }

                ShowUserSelectionMessage();
                return true;

            case R.id.action_sincronizar_descarte:
                ESync.GetSyncInstance().SyncDatabase(DescarteActivity.this);
                return true;

            default:
                return false;
        }
    }

    private boolean CheckForNullComments()
    {
        for (Descarte descarte : listViewArrayList)
        {
            if (descarte.getComments() == null || descarte.getComments().toString().isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    private void LimparListView()
    {
        selectedUsuario = null;
        tagsLidosArrayList.clear();
        listViewArrayList.clear();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();
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

    private void PreencherArrayTeste()
    {
        Descarte descarte = new Descarte();

        for (int i = 0; i < descarte.descartes.length; i++){
            listViewArrayList.add(descarte.descartes[i]);
            tagsLidosArrayList.add(descarte.descartes[i].getTitle());

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listViewAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void ShowEditTextMessage(int position)
    {
        motivoDescarteBuilder = new AlertDialog.Builder(this);
        motivoDescarteBuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View edittextLayout = inflaterEt.inflate(R.layout.comments_alertdialog_layout, null);
        motivoDescarteBuilder.setView(edittextLayout);

        et_comment_comments_ad = (EditText) edittextLayout.findViewById(R.id.et_comment_comments_ad);
        et_comment_comments_ad.setText(listViewArrayList.get(position).getComments());

        btn_cancelar_comments_ad = (Button) edittextLayout.findViewById(R.id.btn_cancelar_comments_ad);
        btn_cancelar_comments_ad.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                et_comment_comments_ad.setText(null);
                alert.cancel();
            }
        });

        btn_OK_comments_ad = (Button) edittextLayout.findViewById(R.id.btn_OK_comments_ad);
        btn_OK_comments_ad.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!et_comment_comments_ad.getText().toString().isEmpty()){
                    listViewArrayList.get(position).setComments(et_comment_comments_ad.getText().toString());
                    alert.cancel();
                }
            }
        });

        alert = motivoDescarteBuilder.create();
        alert.show();
    }

    public void ShowUserSelectionMessage()
    {
        usuarioResponsavelBuilder = new AlertDialog.Builder(this);
        usuarioResponsavelBuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View edittextLayout = inflaterEt.inflate(R.layout.alertdialog_descarte_userread, null);
        usuarioResponsavelBuilder.setView(edittextLayout);

        tv_comments_usuario = (TextView) edittextLayout.findViewById(R.id.tv_comments_usuario);
        btn_cancelar_comments_usr = (Button) edittextLayout.findViewById(R.id.btn_cancelar_comments_usr);
        btn_OK_comments_usr = (Button) edittextLayout.findViewById(R.id.btn_OK_comments_usr);
        fab_descarte_usr = (FloatingActionButton) edittextLayout.findViewById(R.id.fab_descarte_usr);

        btn_cancelar_comments_usr.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                StopReading(fab_descarte_usr);

                tv_comments_usuario.setText(null);
                selectedUsuario = null;

                alert.cancel();
            }
        });

        btn_OK_comments_usr.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if (tv_comments_usuario.getText() != null || !tv_comments_usuario.getText().toString().isEmpty() || !tv_comments_usuario.getText().toString().equals("")){
                    StopReading(fab_descarte_usr);

                    //SALVAR DADOS EM BANCO
                    Salvar();

                    alert.cancel();
                }
            }
        });

        fab_descarte_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DescarteActivity.currentDevice != null){
                    if (reading == false)
                    {
                        readingUser = true;
                        StartReading(fab_descarte_usr);
                    } else {
                        StopReading(fab_descarte_usr);
                    }
                }else{
                    ConectarDispositivoBT();
                }
            }
        });

        alert = usuarioResponsavelBuilder.create();
        alert.show();
    }

    private void Salvar()
    {
        salvarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (selectedUsuario == null)
                {
                    showMessage("AVISO", "Nenhum usuário responsável selecionado", 3);
                    return;
                }

                for (Descarte descarte : listViewArrayList)
                {
                    UPMOBDescartes upmobDescartes = new UPMOBDescartes();

                    upmobDescartes.setApplicationUserId(selectedUsuario);
                    upmobDescartes.setCadastromateriaisId(descarte.getIdOriginal());
                    upmobDescartes.setCodColetor(Build.SERIAL);
                    upmobDescartes.setDataHoraEvento(Calendar.getInstance().getTime());
                    upmobDescartes.setDescricaoErro(null);
                    upmobDescartes.setFlagErro(false);
                    upmobDescartes.setFlagProcess(false);
                    upmobDescartes.setMotivo(descarte.getComments());

                    dbInstance.upmobDescartesDAO().Create(upmobDescartes);
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Descarte", "Solicitação de descarte realizada com sucesso", 1 );
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
}
