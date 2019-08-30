package br.com.marcosmilitao.idativosandroid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.CustomerAdapterSettings;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.Settings;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView sobre_list_view, gerais_list_view;
    private SeekBar seek_bar_ad_layout;
    private EditText et_et_ad_layout;
    private Button btn_salvar_et_ad_layout, btn_conectar_et_ad_layout;
    private CustomerAdapterSettings sobre_list_custom, gerais_list_custom;
    private ArrayList<Settings> arrayListSobre, arrayListGerais;
    public SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    private Settings settingsItem;
    private BluetoothAdapter BA;
    private List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;
    private Spinner sp_dialog;
    public String[] arraySpinner;
    public ArrayAdapter<String> adapter;
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
    private Handler sucessConnectHandle = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "XR400 Conectado!", Toast.LENGTH_SHORT).show();
        }
    };
    private Handler faildConnectHandle = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Não foi possível conectar. Procure o administrador de redes.", Toast.LENGTH_SHORT).show();
        }
    };
    private byte potencia, bip;
    private HandsetParam param;
    public static final String TAG = "handsetparam";
    public static final String key_potencia = "key_potencia";
    public static final String key_bip = "key_bip";
    public static final String key_ipxr400 = "key_ipxr400";
    public static final String key_modelo_leitor_rfid = "key_modelo_leitor_rfid";
    private String modelo_leitor_rfid_Default = "Vanch_VH75";
    private String ipDefault = "0.0.0.0";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder etbuilder;
    private AlertDialog alert;

    public static class GetHandsetParam {

    }
    public static class ChangeBipParam{
        private boolean bip;

        ChangeBipParam(boolean bip){
            this.bip = bip;
        }
    }
    public static class ChangePowerParam{
        private int power;

        ChangePowerParam(int power){
            this.power = power;
        }
    }
    public static class ConnectXR400{
        String ipAddress;

        ConnectXR400(String ipAddress){
            this.ipAddress = ipAddress;
        }

    }
    public class myListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int min = 15;
            if (progress < min){
                progress = min;
            }

            for (int i = 0; i < arrayListGerais.size(); i++){
                switch (arrayListGerais.get(i).getTitle()){
                    case "Potência do leitor":
                        arrayListGerais.get(i).setSubtitle(progress + "db");
                        potencia = (byte)progress;

                        EventBus.getDefault().post(new SettingsActivity.ChangePowerParam(progress));
                        editor.putInt(key_potencia, progress);
                        editor.commit();
                        break;
                    default:
                        break;
                }
            }
            gerais_list_custom.notifyDataSetChanged();
        }
    }
    public class myBtnConectarListener implements Button.OnClickListener{

        @Override
        public void onClick(View view){
            EventBus.getDefault().post(new SettingsActivity.ConnectXR400(et_et_ad_layout.getText().toString()));
        }
    }
    public class myBtnSalvarListener implements Button.OnClickListener{

        @Override
        public void onClick(View view){
            String ipAddress = et_et_ad_layout.getText().toString();

            if (ValidarIP(ipAddress)){
                for (int i = 0; i < arrayListGerais.size(); i++){
                    switch (arrayListGerais.get(i).getTitle()){
                        case "IP do XR400":
                            arrayListGerais.get(i).setSubtitle(ipAddress);
                            editor.putString(key_ipxr400, ipAddress);
                            editor.commit();
                            break;
                        default:
                            break;
                    }
                }
                gerais_list_custom.notifyDataSetChanged();
                alert.cancel();
            } else {
                showMessage("Atenção", "IP Address inválido", 3);
            }
        }
    }
    public class myBtnSalvarModeloLeitorRfid implements Button.OnClickListener{

        @Override
        public void onClick(View view){
            String modelo_leitor = (String) sp_dialog.getSelectedItem();

            for (int i = 0; i < arrayListGerais.size(); i++){
                switch (arrayListGerais.get(i).getTitle()){
                    case "Modelo Leitor RFID":
                        arrayListGerais.get(i).setSubtitle(modelo_leitor);
                        editor.putString(key_modelo_leitor_rfid, modelo_leitor);
                        editor.commit();
                        break;
                    default:
                        break;
                }
            }
            gerais_list_custom.notifyDataSetChanged();
            alert.cancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //COMENTADO PARA TESTES
        //idativos02Data = new Idativos02Data(this);
        //db = idativos02Data.getReadableDatabase();

        setupToolbar();

        arrayListSobre = new ArrayList<Settings>();
        arrayListGerais = new ArrayList<Settings>();

        sobre_list_view = (ListView) findViewById(R.id.sobre_list_view);
        gerais_list_view = (ListView) findViewById(R.id.gerais_list_view);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        PreencherSettings();

        sobre_list_custom = new CustomerAdapterSettings(this, arrayListSobre);
        sobre_list_view.setAdapter(sobre_list_custom);
        sobre_list_view.setOnItemClickListener(this);

        gerais_list_custom = new CustomerAdapterSettings(this, arrayListGerais);
        gerais_list_view.setAdapter(gerais_list_custom);
        gerais_list_view.setOnItemClickListener(this);

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
    }

    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
        close_at();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_reconectar_settings:
                list();
                return true;
            default:
                return false;
        }
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Object settingsObject = l.getAdapter().getItem(position);
        Field declaredField;
        String title = new String();

        try{
            declaredField = settingsObject.getClass().getDeclaredField("title");
            declaredField.setAccessible(true);

            try {
                title = (String)declaredField.get(settingsObject);
            } catch (IllegalAccessException iae){
                showMessage("Erro", iae.toString(), 2);
            }

        } catch (NoSuchFieldException e){
            showMessage("Erro", e.toString(), 2);
        }

        if (title != null){
            switch (title){
                case "FAQ":
                    Toast.makeText(getApplicationContext(), "Função ainda não habilitada", Toast.LENGTH_SHORT).show();
                    break;

                case "Bip do leitor":
                    if (SettingsActivity.currentDevice == null || pairedDevices.size() == 0){
                        showMessage("Atenção", "Leitor de dados não conectado", 3);
                    } else {
                        boolean bip;
                        try {
                            declaredField = settingsObject.getClass().getDeclaredField("isChecked");
                            declaredField.setAccessible(true);
                            try {
                                bip = (Boolean) declaredField.get(settingsObject);

                                if (!bip) {
                                    declaredField.set(settingsObject, true);

                                    editor.putInt(key_bip, 1);
                                    editor.commit();
                                } else {
                                    declaredField.set(settingsObject, false);

                                    editor.putInt(key_bip, 0);
                                    editor.commit();
                                }

                                sobre_list_custom.notifyDataSetChanged();
                                gerais_list_custom.notifyDataSetChanged();

                                EventBus.getDefault().post(new SettingsActivity.ChangeBipParam(!bip));

                            } catch (IllegalAccessException iae) {
                                showMessage("Erro", iae.toString(), 2);
                            }
                        } catch (NoSuchFieldException e) {
                            showMessage("Erro", e.toString(), 2);
                        }
                    }
                    break;

                case "Potência do leitor":
                    if (SettingsActivity.currentDevice == null || pairedDevices.size() == 0){
                        showMessage("Atenção", "Leitor de dados não conectado", 3);
                    } else {
                        showSeekBarMessage("Potência");
                    }
                    break;

                case "Modelo Leitor RFID":
                    showEditModelo_Leitor_RFID();
                    break;

                case "IP do XR400":
                    showEditTextmessage();
                    break;

                default:
                    break;
            }
        }
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
                                ConfigUI.setConfigLastConnect(SettingsActivity.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(SettingsActivity.this, "Erro de Conexão" + currentDevice.getAddress());
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
                VH73Device vh75Device = new VH73Device(SettingsActivity.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {
                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(SettingsActivity.this, currentDevice.getAddress());

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

    void PreencherSettings(){
        settingsItem = new Settings();

        for (int i = 0; i < settingsItem.settings.length; i++){
            switch (settingsItem.settings[i].getTitle()){
                case "Versão":
                    settingsItem.settings[i].setSubtitle(getString(R.string.numero_versao_apk));
                    break;
                case "Número de Série":
                    settingsItem.settings[i].setSubtitle(Build.SERIAL);
                default:
                    break;
            }
            arrayListSobre.add(settingsItem.settings[i]);
        }

        for (int j = 0; j < settingsItem.sobreSettings.length; j++){
            switch (settingsItem.sobreSettings[j].getTitle()){
                case "Modelo Leitor RFID":
                    if (!pref.contains(key_modelo_leitor_rfid)){
                        editor.putString(key_modelo_leitor_rfid, modelo_leitor_rfid_Default);
                        editor.commit();

                        settingsItem.sobreSettings[j].setSubtitle(modelo_leitor_rfid_Default);
                    } else {
                        settingsItem.sobreSettings[j].setSubtitle(pref.getString(key_modelo_leitor_rfid, modelo_leitor_rfid_Default));
                    }
                    break;
                case "Potência do leitor":
                    settingsItem.sobreSettings[j].setSubtitle("Leitor não conectado");
                    break;
                case "Bip do leitor":
                    settingsItem.sobreSettings[j].setSubtitle("Leitor não conectado");
                    break;
                case "IP do XR400":
                    if (!pref.contains(key_ipxr400)){
                        editor.putString(key_ipxr400, (String)ipDefault);
                        editor.commit();

                        settingsItem.sobreSettings[j].setSubtitle(ipDefault);
                    } else {
                        settingsItem.sobreSettings[j].setSubtitle(pref.getString(key_ipxr400, ipDefault));
                    }
                    break;
                default:
                    break;
            }
            arrayListGerais.add(settingsItem.sobreSettings[j]);
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

    @TargetApi(21)
    public void showSeekBarMessage(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);

        LayoutInflater inflaterSb = getLayoutInflater();
        View seekBarLayout = inflaterSb.inflate(R.layout.seekbar_alertdialot_layout, null);
        builder.setView(seekBarLayout);



        seek_bar_ad_layout = (SeekBar) seekBarLayout.findViewById(R.id.seek_bar_ad_layout);
        seek_bar_ad_layout.setOnSeekBarChangeListener(new myListener());
        seek_bar_ad_layout.setProgress(potencia);

        builder.create().show();
    }

    public void showEditTextmessage(){
        etbuilder = new AlertDialog.Builder(this);
        etbuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View edittextLayout = inflaterEt.inflate(R.layout.editnumber_alertdialog_layout, null);
        etbuilder.setView(edittextLayout);

        et_et_ad_layout = (EditText) edittextLayout.findViewById(R.id.et_et_ad_layout);
        if (pref.contains(key_ipxr400)){
            et_et_ad_layout.setText(pref.getString(key_ipxr400, ipDefault));
        }

        btn_salvar_et_ad_layout = (Button) edittextLayout.findViewById(R.id.btn_salvar_et_ad_layout);
        btn_salvar_et_ad_layout.setOnClickListener(new myBtnSalvarListener());

        btn_conectar_et_ad_layout = (Button) edittextLayout.findViewById(R.id.btn_conectar_et_ad_layout);
        btn_conectar_et_ad_layout.setOnClickListener(new myBtnConectarListener());

        alert = etbuilder.create();
        alert.show();
    }

    public void showEditModelo_Leitor_RFID(){
        etbuilder = new AlertDialog.Builder(this);
        etbuilder.setCancelable(true);

        LayoutInflater inflaterEt = getLayoutInflater();
        View editspLayout = inflaterEt.inflate(R.layout.editspinner_alertdialog_layout, null);
        etbuilder.setView(editspLayout);

        arraySpinner = new String[] {
                "Vanch_VH75",
                "BluetoothLE",
                "XR_400"
        };

        sp_dialog = (Spinner) editspLayout.findViewById(R.id.sp_dialog);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
        sp_dialog.setAdapter(adapter);

/*        et_et_ad_layout = (EditText) editspLayout.findViewById(R.id.et_et_ad_layout);
        if (pref.contains(key_modelo_leitor_rfid)){
            sp_dialog.setSelection(((ArrayAdapter<String>)sp_dialog.getAdapter()).getPosition(pref.getString(key_modelo_leitor_rfid, modelo_leitor_rfid_Default)));
        }*/

        btn_salvar_et_ad_layout = (Button) editspLayout.findViewById(R.id.btn_salvar_et_ad_layout);
        btn_salvar_et_ad_layout.setOnClickListener(new myBtnSalvarModeloLeitorRfid());

        alert = etbuilder.create();
        alert.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventMainThread(GetHandsetParam ghp){

        try {
            param = SettingsActivity.currentDevice.parseReadDefaultParam();

            if (!pref.contains(key_bip)){
                editor.putInt(key_bip, (int)param.Alarm);
                editor.commit();

                bip = param.Alarm;
            } else {
                switch (pref.getInt(key_bip, 2)){
                    case 0:
                        bip = 0;
                        EventBus.getDefault().post(new SettingsActivity.ChangeBipParam(false));
                        break;
                    case 1:
                        bip = 1;
                        EventBus.getDefault().post(new SettingsActivity.ChangeBipParam(true));
                        break;
                    default:
                        break;
                }
            }

            if (!pref.contains(key_potencia)){
                editor.putInt(key_potencia, (int)param.Power);
                editor.commit();

                potencia = param.Power;
            } else {
                potencia = (byte)pref.getInt(key_potencia, 0);
            }

            for (int i = 0; i < arrayListGerais.size(); i++){
                switch (arrayListGerais.get(i).getTitle()){
                    case "Potência do leitor":
                        if (potencia != 0){
                            arrayListGerais.get(i).setSubtitle(Integer.toString((int)potencia) + "db");
                        }
                        break;
                    case "Bip do leitor":
                        if (bip == 1){
                            arrayListGerais.get(i).setChecked(true);
                        } else if (bip == 0){
                            arrayListGerais.get(i).setChecked(false);
                        }

                        arrayListGerais.get(i).setSubtitle("");
                        break;
                    default:
                        break;
                }
            }

            gerais_list_custom.notifyDataSetChanged();

        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventMainThread(ChangeBipParam cbp){
        if (!cbp.bip){
            param.Alarm = 0;
        } else {
            param.Alarm = 1;
        }

        try {
            SettingsActivity.currentDevice.writeParam(param);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventMainThread(ChangePowerParam cpp){
        param.Power = (byte)cpp.power;

        try {
            SettingsActivity.currentDevice.writeParam(param);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void onEventBackgroundThread(SettingsActivity.ConnectXR400 c){
        Message message;

        if (isXR400Alive(c.ipAddress)){
            message = sucessConnectHandle.obtainMessage();
        } else {
            message = faildConnectHandle.obtainMessage();
        }

        message.sendToTarget();
    }

    private boolean isXR400Alive(String ipAddress){
        boolean isAlive = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress(ipAddress, 80);
            Socket sock = new Socket();

            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            isAlive = true;
            sock.close();
        } catch(IOException e) {
            String err = e.getMessage();
        }

        return isAlive;
    }

    public static boolean ValidarIP (String ipAddress) {
        try {
            if ( ipAddress == null || ipAddress.isEmpty() ) {
                return false;
            }

            String[] parts = ipAddress.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ipAddress.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
