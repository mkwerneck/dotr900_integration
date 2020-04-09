package br.com.marcosmilitao.idativosandroid.Processos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Entity.UPMOBListaTarefasEqReadinessTables;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaTarefasEqReadinessTables;
import br.com.marcosmilitao.idativosandroid.MainActivity;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class ProcessosOSActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AutoCompleteTextView txt_Buscar;
    private ImageButton btn_Buscar;
    private ListView list_Tarefas;
    private ArrayList<String> array_Lista_Tarefas;
    private ArrayAdapter<String> adapter_Lista_Tarefas;
    private Cursor listaTarefas1;
    private Idativos02Data idativos02Data;
    private SQLiteDatabase connect;
    private Query_UPWEBListaTarefasEqReadinessTables query_upwebListaTarefasEqReadinessTables;
    private UPMOBListaTarefasEqReadinessTables upmobListaTarefasEqReadinessTables;
    private List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    private ArrayList<Integer> lista_Tarefa_Id_Original;
    private static String device1;
    Sync sync;
    SQLiteDatabase db;
    Button btn_p_os_pesquisar;
    EditText edt_p_os_pesquisar;
    public static VH73Device currentDevice;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private final SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private GoogleApiClient client;

    public boolean isConnected() throws InterruptedException, IOException{
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processos_os);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();
        edt_p_os_pesquisar = (EditText) findViewById(R.id.edt_p_os_pesquisar);
        btn_p_os_pesquisar = (Button) findViewById(R.id.btn_p_os_pesquisar);

        lista_Tarefa_Id_Original = new ArrayList<Integer>();
        array_Lista_Tarefas = new ArrayList<String>();
        adapter_Lista_Tarefas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_Lista_Tarefas);
        list_Tarefas = (ListView) findViewById(R.id.list_Processos);
        list_Tarefas.setAdapter(adapter_Lista_Tarefas);
        list_Tarefas.setOnItemClickListener(this);

        ListarTarefas();

        final FloatingActionButton fb_Adicionar = (FloatingActionButton) findViewById(R.id.btn_Adicionar);

        fb_Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ProcessosOSActivity.this, ProcessosAtivosActivity.class);
                startActivity(intent);
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        btn_p_os_pesquisar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ListarTarefas();
            }

        });
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProcessosOS Page") // TODO: Define a title for the content shown.
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

    public void onClick(View v) {
        // Set listaTarefas1 null before Create ProcessosAtivosActivity.class
        //listaTarefas1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        String cod_tarefa1 = listaTarefas1.getString(listaTarefas1.getColumnIndex("Id_Original"));
        Intent intent = new Intent(this, ProcessosAtivosActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_processos_os,menu);
        return true;
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
                                ConfigUI.setConfigLastConnect(ProcessosOSActivity.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(ProcessosOSActivity.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }
            }
        }

    }

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(ProcessosOSActivity.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(ProcessosOSActivity.this, currentDevice.getAddress());
                } else {

                }
            }
        }.start();
    }

    public void list() {

        queryPairedDevices();
        pairedDevices = BA.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
            connect(bt);
            device1 = bt.getName();
        }
       // Toast.makeText(getApplicationContext(), "Bluetooth Conectado !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_limpar_lista_refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.action_processos_os_sync:
                //Nova Chamada para Sincronismo
                ESync.GetSyncInstance().SyncDatabase(ProcessosOSActivity.this);
                return true;
            case R.id.action_processos_os_adicionar:

                intent = new Intent(ProcessosOSActivity.this, ProcessosAtivosActivity.class);
                startActivity(intent);

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                Intent intent = new Intent(ProcessosOSActivity.this, NovoProcessoActivity.class);//
                int idOriginal = lista_Tarefa_Id_Original.get(position);

                ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetByIdOriginal(idOriginal);

                if (listaTarefas != null)
                {
                    CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(listaTarefas.getProcessoIdOriginal());
                    Tarefas tarefas = dbInstance.tarefasDAO().GetByIdOriginal(listaTarefas.getTarefaIdOriginal());
                    ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(cadastroEquipamentos.getModeloEquipamentoItemIdOriginal());

                    intent.putExtra(NovoProcessoActivity.EXTRA_TRACENUMBER, cadastroEquipamentos.getTraceNumber());
                    intent.putExtra(NovoProcessoActivity.EXTRA_IDORIGINAL, idOriginal);
                    intent.putExtra(NovoProcessoActivity.EXTRA_CODPROCESSO, listaTarefas.getIdOriginal());
                    intent.putExtra(NovoProcessoActivity.EXTRA_CODTAREFA, tarefas.getCodigo());
                    //intent.putExtra(NovoProcessoActivity.EXTRA_TIPOTAREFA, tarefas.getTipo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_TITULOTAREFA, tarefas.getTitulo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_MODELOEQUIPAMENTO, modeloEquipamentos.getModelo());
                    intent.putExtra(NovoProcessoActivity.EXTRA_TAGEDITAR, true);
                    intent.putExtra(NovoProcessoActivity.EXTRA_IDTAREFA, 0);

                    startActivity(intent);
                }
            }
        }).start();
    }

    public Connection DbConection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "iduttosqladm";
            String password = "1dutto@04d";
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://162.144.63.165:1433/idativos_omni02_teste;user=" + username + ";password=" + password);

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

    public void showMessage(String title,String message,int type){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);

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

    public void ListarTarefas(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<ListaTarefas> listaTarefasList;

                array_Lista_Tarefas.clear();
                lista_Tarefa_Id_Original.clear();

                String filtro = edt_p_os_pesquisar.getText().toString();


                if (!filtro.matches(""))
                {
                    listaTarefasList = dbInstance.listaTarefasDAO().GetAll();
                }
                else
                {
                    listaTarefasList = dbInstance.listaTarefasDAO().GetAll();
                }

                for (ListaTarefas listaTarefas : listaTarefasList)
                {
                    CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(listaTarefas.getTarefaIdOriginal());
                    Tarefas tarefa = dbInstance.tarefasDAO().GetByIdOriginal(listaTarefas.getTarefaIdOriginal());

                    String item = "Processo: " + listaTarefas.getIdOriginal() + " | " + cadastroEquipamentos.getTraceNumber() + " | " + tarefa.getTitulo() + " | " + listaTarefas.getStatus() + " | " + sdformat.format(listaTarefas.getDataInicio());
                    array_Lista_Tarefas.add(item);
                    lista_Tarefa_Id_Original.add(listaTarefas.getIdOriginal());

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter_Lista_Tarefas.notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }

}
