package br.com.marcosmilitao.idativosandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import br.com.marcosmilitao.idativosandroid.DAO.UPMOBWorksheetDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_Parametros_Padrao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBMaterial_Type;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBProprietario;
import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBWorksheet;
import br.com.marcosmilitao.idativosandroid.helper.AccessUI;
import br.com.marcosmilitao.idativosandroid.helper.ConfigUI;
import br.com.marcosmilitao.idativosandroid.helper.Epc;
import br.com.marcosmilitao.idativosandroid.helper.HandsetParam;
import br.com.marcosmilitao.idativosandroid.helper.ImageHelper;
import br.com.marcosmilitao.idativosandroid.helper.TimeoutException;
import br.com.marcosmilitao.idativosandroid.helper.Utility;
import br.com.marcosmilitao.idativosandroid.helper.VH73Device;
import de.greenrobot.event.EventBus;

import static br.com.marcosmilitao.idativosandroid.MainActivity.getConfigLastConnected;

public class Act_Cadastro_dados extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    private CurrencyEditText et_valor;
    private EditText novo_tagid, PK_Serie, PK_Lote, Dados_Tecnicos, num_NF;
    private TextView Tagid, lbl_posicao, tvsp_data_nf, tvsp_dtvalidade;
    private Spinner sp_cadastro_proprietario;
    private DatePicker dp_validade;
    private SpinnerAdapter Proprietario1;
    private SpinnerAdapter Material_TypeSP;
    private SQLiteDatabase connect;
    private Query_UPWEBProprietario query_UPWEBProprietario;
    private Query_UPWEBMaterial_Type query_UPWEBMaterial_Type;
    private Query_Parametros_Padrao query_parametros_padrao;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Spinner sp_cadastro_Material_Type, sp_cadastro_condition, sp_cadastro_posicao_original;
    private ArrayList<String> arraySpinner_processo;
    private ArrayList<String> arraySpinner_proprietario;
    private ArrayList<String> arraySpinner_modeloMaterial;
    private ArrayList<String> arraySpinner_posicao;
    private ArrayAdapter<String> adapter_posicao;
    private ArrayAdapter<String> adapter_modeloMaterial;
    private ArrayAdapter<String> adapter_proprietario;
    private ArrayAdapter<String> adapter_condition;
    private String Part_Number, Modalidade, Categoria_Equipamento;
    private Date data_validade, data_entradanf, data_fabricacao;
    private BigDecimal valor_unitario;
    private int Quantidade;
    private String Material_Type;
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;
    private boolean flagEditarCadastro1, FlagMobileUpdate, FlagMobileInsert;
    private ListView lv_cadastro_dados;
    private ImageView imagemModelo;
    private ImageHelper ImageHelper;
    private HandsetParam param;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SlideDateTimeListener slidelistenernf = new SlideDateTimeListener(){
        @Override
        public void onDateTimeSet(Date date){
            tvsp_data_nf.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel(){

        }
    };
    private SlideDateTimeListener slidelistenerval = new SlideDateTimeListener(){
        @Override
        public void onDateTimeSet(Date date){
            tvsp_dtvalidade.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel(){

        }
    };
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage) {
            Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
        }
    };
    private Intent intent;
    String Posicao_Original;
    String Posicao_Atual;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
    private UPMOBWorksheetDAO upmobworksheetDAO;


    List<Epc> epcs = new ArrayList<Epc>();
    Map<String, Integer> epc2num = new ConcurrentHashMap<String, Integer>();

    private ListAdapter adapter;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    public static VH73Device currentDevice;

    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();


    private SpinnerAdapter posicao1;

    //Teste ListView
    CheckBox cbx_editar_cadastro;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private Idativos02Data idativos02Data;


    private Query_UPWEBPosicao query_UPWEBPosicao;


    ProgressDialog progressDialog;
    boolean stoop = false;
    int readCount = 0;
    boolean inventoring = false;
    public static final String TAG = "inventory";

    public static final String EXTRA_MESSAGE = "tagid";
    public static final String EXTRA_MESSAGE2 = "idOriginal";
    public static final String EXTRA_MESSAGE3 = "flagEditar";
    public static final String EXTRA_MESSAGE4 = "categoria";
    public static final String EXTRA_MESSAGE5 = "posicaoOriginal";
    public static final String EXTRA_MESSAGE6 = "quantidade";
    public static final String EXTRA_MESSAGE7 = "numProduto";

    public String intentTagid;
    public int intentIdOriginal;
    public boolean intentFlagEditar;
    public String intentCategoria;
    public String intentPosicaoOriginal;
    public int intentQuantidade;
    public String intentNumProduto;

    private static final String cor = "";

    private GoogleApiClient client;

    public Act_Cadastro_dados() {
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Act_Cadastro Page") // TODO: Define a title for the content shown.
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

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        String tag = lv_cadastro_dados.getItemAtPosition(position).toString();
        novo_tagid.setText(tag);
    }

    public static class InventarioTerminal {

    }
    public static class InventoryEvent {
    }
    public static class InventoryTerminal {
    }
    public static class EpcInventoryEvent {
    }
    public static class TimeoutEvent {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__cadastro_dados);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();
            }
        });

        this.ImageHelper = new ImageHelper(this);
        imagemModelo = (ImageView) findViewById(R.id.imagemModelo2);
        imagemModelo.setImageResource(R.drawable.ic_add_a_photo_black_48dp);
        imagemModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaSourceImagem();
            }
        });

        BA = BluetoothAdapter.getDefaultAdapter();
        BA.enable();

        intent = getIntent();
        intentTagid = intent.getStringExtra(EXTRA_MESSAGE);
        intentIdOriginal = intent.getIntExtra(EXTRA_MESSAGE2, 0);
        intentFlagEditar = intent.getBooleanExtra(EXTRA_MESSAGE3, false);
        intentCategoria = intent.getStringExtra(EXTRA_MESSAGE4);
        intentPosicaoOriginal = intent.getStringExtra(EXTRA_MESSAGE5);
        intentQuantidade = intent.getIntExtra(EXTRA_MESSAGE6, 1);
        intentNumProduto = intent.getStringExtra(EXTRA_MESSAGE7);

        dp_validade = (DatePicker) findViewById(R.id.dp_validade);
        lv_cadastro_dados = (ListView) findViewById(R.id.lv_cadastro_dados);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_lertag_cadastro_dados);
        lv_cadastro_dados.setOnItemClickListener(this);

        //Spiner de Condicoes
        arraySpinner_processo = new ArrayList<String>();
        adapter_condition = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner_processo);
        sp_cadastro_condition = (Spinner) findViewById(R.id.sp_cadastro_condition);
        sp_cadastro_condition.setAdapter(adapter_condition);
        FillSpinerCondicoes();

        //Spiner de Proprieetarios
        arraySpinner_proprietario = new ArrayList<String>();
        adapter_proprietario = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner_proprietario);
        sp_cadastro_proprietario = (Spinner) findViewById(R.id.sp_cadastro_proprietario);
        sp_cadastro_proprietario.setAdapter(adapter_proprietario);
        FillSpinerProprietario();

        //Spinner de Modelos
        arraySpinner_modeloMaterial = new ArrayList<String>();
        adapter_modeloMaterial = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner_modeloMaterial);
        sp_cadastro_Material_Type = (Spinner) findViewById(R.id.sp_cadastro_Material_Type);
        sp_cadastro_Material_Type.setAdapter(adapter_modeloMaterial);
        FillSpinerModeloMateriais();

        //Spinner de Posicoes
        arraySpinner_posicao = new ArrayList<String>();
        adapter_posicao = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner_posicao);
        sp_cadastro_posicao_original = (Spinner) findViewById(R.id.sp_cadastro_posicao_original);
        sp_cadastro_posicao_original.setAdapter(adapter_posicao);
        FillSpinerPosicoes();

        novo_tagid = (EditText)findViewById(R.id.novo_tagid);
        Dados_Tecnicos = (EditText)findViewById(R.id.Dados_Tecnicos);
        num_NF = (EditText)findViewById(R.id.num_NF);
        PK_Serie = (EditText)findViewById(R.id.PK_Serie);
        PK_Lote = (EditText)findViewById(R.id.PK_Lote);
        et_valor = (CurrencyEditText)findViewById(R.id.et_valor);
        Tagid = (TextView) findViewById(R.id.Tagid);
        lbl_posicao = (TextView) findViewById(R.id.lbl_posicao);
        tvsp_data_nf = (TextView) findViewById(R.id.tvsp_data_nf);
        tvsp_dtvalidade = (TextView) findViewById(R.id.tvsp_dtvalidade);

        tvsp_data_nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slidelistenernf);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        tvsp_dtvalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slidelistenerval);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        //Recebendo o TAGID;
        TextView myTextView = (TextView) findViewById(R.id.Tagid);
        myTextView.setText(intentTagid);

        try {
             Quantidade = intentQuantidade;
        } catch(Exception ex){
             Quantidade = 1;
             android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(this);
             dlg.setMessage("Erro no cadastro do Valor da Quantidade. Quantidade foi automaticamente cadastrada como 1.0.");
             dlg.setNeutralButton("OK", null);
             dlg.show();
        }

        Categoria_Equipamento = intentCategoria;

        if (!intentFlagEditar) {
            novo_tagid.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            lv_cadastro_dados.setVisibility(View.GONE);

        } else {
            novo_tagid.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            lv_cadastro_dados.setVisibility(View.GONE);

            PreencherCadastroEditar();
        }

        switch (intentCategoria) {
            case "Material Almoxarifado":

                Modalidade = "Materiais";
                break;
            case "Produto Químico":

                Modalidade = "Materiais";
                break;
            case "Cadastro Equipamento":

                Modalidade = "Equipamentos";
                break;
            case "Cadastro Eslinga":

                Modalidade = "Equipamentos";
                break;
            case "Fabricação Eslinga":

                Modalidade = "Equipamentos";
                break;
            case "Material Fabricação":

                Modalidade = "Equipamentos";
                break;
            default:
                break;

        }

        fab.setOnClickListener(new View.OnClickListener() {
            String color;
            @Override
            public void onClick(View view) {
                if(Act_Cadastro_dados.currentDevice != null){
                    list();
                    if(!inventoring){
                        inventoring = !inventoring;
                        readCount=0;
                        clearList();
                        EventBus.getDefault().post(new Act_Cadastro_dados.InventoryEvent());
                        color = "#F30808";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }else {
                        inventoring = !inventoring;


                        color = "#41C05A";
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                    }
                }else{
                    Utility.WarningAlertDialg(Act_Cadastro_dados.this,"!","Leitor RFID Desconectado!").show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        updateLang();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void Cadastrar(){

        if(sp_cadastro_posicao_original.getSelectedItem().toString().isEmpty() || sp_cadastro_posicao_original.getSelectedItem().toString() == "Selecione uma posição") {
            showMessage("AVISO", "Entre com a Posição", 3);
        } else if(num_NF.getText().toString() == null || num_NF.getText().toString().equals("0") || num_NF.getText().toString().equals("")){
            showMessage("AVISO", "Entre com o número de Nota Fiscal válido para prosseguir", 3);
        } else if(tvsp_data_nf.getText().toString().equals("AAAA-MM-DD")){
            showMessage("AVISO", "Informe a Data de Entrada da nota fiscal para prosseguir", 3);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                    Date dataHoraEvento = Calendar.getInstance().getTime();
                    String NumProduto = dbInstance.modeloMateriaisDAO().GetNumProdutoByModelo(sp_cadastro_Material_Type.getSelectedItem().toString());
                    String TAGIDPosicao = dbInstance.posicoesDAO().GetTAGIDByCodPosicao(sp_cadastro_posicao_original.getSelectedItem().toString());

                    //Formatando as datas Validade e Entrada NF
                    if (ValidarData(tvsp_dtvalidade.getText().toString()) != null){
                        data_validade = ValidarData(tvsp_dtvalidade.getText().toString());
                    } else {
                        data_validade = null;
                    }

                    if (ValidarData(tvsp_data_nf.getText().toString()) != null){
                        data_entradanf = ValidarData(tvsp_data_nf.getText().toString());
                    } else {
                        data_entradanf = null;
                    }

                    //Formatando Valor Unitario
                    valor_unitario = FormatarValor(String.valueOf(et_valor.getRawValue()));
                    if (valor_unitario == null || valor_unitario.equals("null")){
                        valor_unitario = null;
                    }

                    //Verificando se há troca de TAGID
                    if(!novo_tagid.getText().toString().isEmpty() && flagEditarCadastro1 == true){
                        CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(novo_tagid.getText().toString(), true);

                        //Verificando se o TAGID já se encontra cadastrado
                        if (cadastroMateriais != null)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage("AVISO", "Novo TAG já está sendo utilizado", 3);
                                    return;
                                }
                            });
                        }
                    }

                    UPMOBCadastroMateriais upmobCadastroMateriais = new UPMOBCadastroMateriais();
                    upmobCadastroMateriais.setIdOriginal(intentIdOriginal);
                    upmobCadastroMateriais.setPatrimonio(PK_Lote.getText().toString());
                    upmobCadastroMateriais.setNumSerie(PK_Serie.getText().toString());
                    upmobCadastroMateriais.setQuantidade(Quantidade);
                    upmobCadastroMateriais.setDadosTecnicos(Dados_Tecnicos.getText().toString());
                    upmobCadastroMateriais.setTAGID(intentTagid);
                    upmobCadastroMateriais.setValorUnitario(valor_unitario.doubleValue());
                    upmobCadastroMateriais.setCodColetor(Build.SERIAL);
                    upmobCadastroMateriais.setDescricaoErro(null);
                    upmobCadastroMateriais.setFlagErro(false);
                    upmobCadastroMateriais.setFlagAtualizar(intentFlagEditar);
                    upmobCadastroMateriais.setFlagProcess(false);
                    upmobCadastroMateriais.setDataHoraEvento(dataHoraEvento);
                    upmobCadastroMateriais.setDataFabricacao(null);
                    upmobCadastroMateriais.setDataValidade(data_validade);

                    dbInstance.upmobCadastroMateriaisDAO().Create(upmobCadastroMateriais);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!intentFlagEditar)
                            {
                                showMessage("Cadastro", "Cadastro Realizado com Sucesso", 1 );
                                closeWindowTimer();
                            } else {
                                showMessage("Cadastro", "Cadastro Editado com Sucesso", 1 );
                                closeWindowTimer();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cadastro_dados,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cadastro_dados_reconectar:
                list();
                return true;
            case R.id.action_cadastro_dados_cadastrar:
                Cadastrar();
                Cadastrar_imagem_modelo ();
                return true;
            case R.id.action_sync_ci:
                ESync.GetSyncInstance().SyncDatabase(Act_Cadastro_dados.this);
                return true;
            default:
                return false;
        }
    }

    public void Cadastrar_imagem_modelo (){
        if(imagemModelo.getTag() != null) {
            try {
                connect.execSQL("INSERT INTO UPMOBImagemModelo VALUES('" + sp_cadastro_Material_Type.getSelectedItem().toString() + "', '" + imagemModelo.getTag().toString() + "'," + 1 + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void alertaSourceImagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name).setMessage("Selecione a fonte da Imagem:");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clicaTirarFoto();
            }
        });
        builder.setNegativeButton("Biblioteca", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clicaCarregarImagem();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clicaTirarFoto(){
        fotoResource = true;
        localArquivoFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis()+".jpg";

        Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localArquivoFoto)));
        startActivityForResult(irParaCamera, 123);
    }

    public void clicaCarregarImagem(){
        fotoResource=false;
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagem de contato"), 1);*/

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!fotoResource) {
            if (resultCode == RESULT_OK
                    && null != data) {

                Uri imagemSel = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(imagemSel,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String caminhoFoto = cursor.getString(columnIndex);
                cursor.close();

                ImageHelper.carregaImagem(caminhoFoto);
            }

        }else{
            if (requestCode == TIRA_FOTO) {
                if(resultCode == Activity.RESULT_OK) {
                    ImageHelper.carregaImagem(this.localArquivoFoto);
                } else {
                    this.localArquivoFoto = null;
                }
            }
        }
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        if(myString == null){
            return 0;
        }else {
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
    }

    public static java.util.Date getDateFromDatePicket(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public void showMessage(String title,String message,int type) {
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
        //Toast.makeText(getApplicationContext(), "Conectado !", Toast.LENGTH_SHORT).show();
    }

    private void connect(final BluetoothDevice device) {

        new Thread() {

            public void run() {
                Intent intent = null;
                VH73Device vh75Device = new VH73Device(Act_Cadastro_dados.this, device);
                boolean succ = vh75Device.connect();
                if (succ) {

                    currentDevice = vh75Device;
                    EventBus.getDefault().post(new MainActivity.DoReadParam());
                    ConfigUI.setConfigLastConnect(Act_Cadastro_dados.this, currentDevice.getAddress());

                    Message message = mhandler.obtainMessage();
                    message.sendToTarget();
                } else {

                }
            }
        }.start();
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
                                ConfigUI.setConfigLastConnect(Act_Cadastro_dados.this, currentDevice.getAddress());
                            } else {
                                Utility.showTostInNonUIThread(Act_Cadastro_dados.this, "Erro de Conexão" + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new MainActivity.FreshList());
                        }
                    }.start();
                }


            }
        }

    }

    private void clearList() {
        if (epc2num != null && epc2num.size() > 0) {
            epc2num.clear();
            refreshList();
        }
    }

    public void onEventBackgroundThread(Act_Cadastro_dados.InventoryEvent e) {

        int i = 0;

        try {
            Act_Cadastro_dados.currentDevice.SetReaderMode((byte) 1);
            byte[] res = Act_Cadastro_dados.currentDevice.getCmdResultWithTimeout(3000);
            if (!VH73Device.checkSucc(res)) {
                // TODO show error message

                inventoring = false;
                EventBus.getDefault().post(new Act_Cadastro_dados.InventoryTerminal());
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

            long lnow = android.os.SystemClock.uptimeMillis(); // 起始时间
            doInventory();
            while (true) {
                long lnew = android.os.SystemClock.uptimeMillis(); // 结束时间
                if (lnew - lnow > 150) {
                    break;
                }
            }
        }
        EventBus.getDefault().post(new Act_Cadastro_dados.InventoryTerminal());

        try {
            Act_Cadastro_dados.currentDevice.SetReaderMode((byte) 1);
            byte[] ret = Act_Cadastro_dados.currentDevice.getCmdResultWithTimeout(3000);
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

    private void doInventory() {
        try {

            Act_Cadastro_dados.currentDevice.listTagID(1, 0, 0);
            byte[] ret = Act_Cadastro_dados.currentDevice.getCmdResult();
            if (!VH73Device.checkSucc(ret)) {
                // TODO show error message
                return;
            }
            VH73Device.ListTagIDResult listTagIDResult = VH73Device
                    .parseListTagIDResult(ret);
            addEpc(listTagIDResult);
            EventBus.getDefault().post(new Act_Cadastro_dados.EpcInventoryEvent());

            // read the left id
            int left = listTagIDResult.totalSize - 8;
            while (left > 0) {
                if (left >= 8) {
                    Act_Cadastro_dados.currentDevice.getListTagID(8, 8);
                    left -= 8;
                } else {
                    Act_Cadastro_dados.currentDevice.getListTagID(8, left);
                    left = 0;
                }
                byte[] retLeft = Act_Cadastro_dados.currentDevice
                        .getCmdResult();
                if (!VH73Device.checkSucc(retLeft)) {
//                    Utility.showTostInNonUIThread(getActivity(),
//                            Strings.getString(R.string.msg_command_fail));
                    continue;
                }
                VH73Device.ListTagIDResult listTagIDResultLeft = VH73Device
                        .parseGetListTagIDResult(retLeft);
                addEpc(listTagIDResultLeft);
                EventBus.getDefault().post(new Act_Cadastro_dados.EpcInventoryEvent());
            }
            // EventBus.getDefault().post(new InventoryTerminal());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onEventMainThread(Act_Cadastro_dados.InventoryTerminal e) {
        progressDialog.dismiss();
        inventoring = false;

//
//        btnInventory.setBackgroundResource(R.drawable.inventory_btn_press);
//        btnInventory.setText(Strings.getString(R.string.inventory));
    }

    private void addEpc(VH73Device.ListTagIDResult list) {
        ArrayList<byte[]> epcs = list.epcs;
        for (byte[] bs : epcs) {
            String string ="H" + Utility.bytes2HexString(bs);
            if (!ConfigUI.getConfigSkipsame(Act_Cadastro_dados.this)) {
                if (epc2num.containsKey(string)) {
                    epc2num.put(string, epc2num.get(string) + 1);
                } else {
                    epc2num.put(string, 1);
                }
            } else {
                epc2num.put(string, 1);
            }

            readCount = epc2num.size();



        }

    }

    private void addEpcTest(String strEpc) {
        if (epc2num.containsKey(strEpc)) {
            epc2num.put(strEpc, epc2num.get(strEpc) + 1);
        } else {
            epc2num.put(strEpc, 1);
        }
        readCount = epc2num.size();
    }

    public void onEventMainThread(Act_Cadastro_dados.EpcInventoryEvent e) {
        refreshList();
        //txtCount.setText("" + readCount);
        //


    }

    Act_Cadastro_dados.InventoryThread inventoryThread;
    class InventoryThread extends Thread {
        int len, addr, mem;


        public InventoryThread(int len, int addr, int mem) {
            this.len = len;
            this.addr = addr;
            this.mem = mem;

        }

        public void run() {
            try {
                Act_Cadastro_dados.currentDevice.listTagID(1, 0, 0);
                Log.i(TAG, "start read!!");
                Act_Cadastro_dados.currentDevice.getCmdResult();
                Log.i(TAG, "read ok!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void freshStatus() {

    }

    public void onEventMainThread(AccessUI.StatusChangeEvent e) {
        freshStatus();
    }
    public void onEventMainThread(ConfigUI.LangChanged e) {
        updateLang();
    }
    public void onEventMainThread(Act_Cadastro_dados.TimeoutEvent e) {
        progressDialog.dismiss();

        inventoring = false;

        EventBus.getDefault().post(new Act_Cadastro_dados.InventoryTerminal());
    }
    private void refreshList() {
        adapter = new Act_Cadastro_dados.IdListAdaptor();
        lv_cadastro_dados.setAdapter(adapter);
        // listView.scrollTo(0, adapter.getCount());

        lv_cadastro_dados.setSelection(lv_cadastro_dados.getAdapter().getCount() - 1);
    }

    private class IdListAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return epc2num.size();
        }

        @Override
        public Object getItem(int position) {
            String[] ids = new String[epc2num.size()];
            epc2num.keySet().toArray(ids);
            return ids[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = Act_Cadastro_dados.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.list_view_columns, null);
            TextView rfidTextView = (TextView) view.findViewById(R.id.txt_rfid);
            TextView countTextView = (TextView) view.findViewById(R.id.txt_count_item);

            String id = (String) getItem(position);
            int count = epc2num.get(id);
            rfidTextView.setText(id);
            //countTextView.setText("Quantidade:" + count);

            TextView textViewNoTitle = (TextView) view.findViewById(R.id.txt_teste);
            //textViewNoTitle.setText("test test teste");
            return view;
        }
    }
    private void updateLang() {
        //btnInventory.setText(Strings.getString(R.string.inventory));
        //btnSave.setText(Strings.getString(R.string.save));
        refreshList();
    }

    @Override
    public void onResume() {

        EventBus.getDefault().register(this);
        //refreshList();
        refreshList();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        //findEpcSound.stop();

        super.onDestroy();
    }
    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Context context = this;
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            BA.disable();
            BA.enable();




        }
        return super.onKeyDown(keyCode, event);
    }

    public Date ValidarData(String data_validar){
        Date date = null;

        if (data_validar == null){
            return date;
        }

        try {
            date = sdformat.parse(data_validar);
        } catch (Exception e){
            return date;
        }

        return date;
    }

    public BigDecimal FormatarValor(String valorRaw){
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

    private void FillSpinerCondicoes() {
        arraySpinner_processo.addAll(Arrays.asList(
                "Em Uso",
                "Em Reparo",
                "Em Manutenção",
                "Em Certificação",
                "Pronto p/ Uso",
                "Desconhecido",
                "N/A"));

        adapter_condition.notifyDataSetChanged();
    }

    private void FillSpinerProprietario() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                String proprietarioPadrao = dbInstance.parametrosPadraoDAO().GetProprietarioPadrao();
                List<String> proprietariosList = dbInstance.proprietariosDAO().GetAllDescricao();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arraySpinner_proprietario.addAll(proprietariosList);
                        adapter_proprietario.notifyDataSetChanged();
                        sp_cadastro_proprietario.setSelection(adapter_proprietario.getPosition(proprietarioPadrao));
                    }
                });

            }
        }).start();
    }

    private void FillSpinerModeloMateriais() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                ModeloMateriais modeloMateriais = intentNumProduto != null ? dbInstance.modeloMateriaisDAO().GetByNumProduto(intentNumProduto) : null;
                List<String> modeloMateriaisList = dbInstance.modeloMateriaisDAO().GetAllNomeModelo();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arraySpinner_modeloMaterial.addAll(modeloMateriaisList);
                        adapter_modeloMaterial.notifyDataSetChanged();

                        if (modeloMateriais != null)
                        {
                            sp_cadastro_Material_Type.setSelection(adapter_modeloMaterial.getPosition(modeloMateriais.getModelo()));
                        }
                    }
                });
            }
        }).start();
    }

    private void FillSpinerPosicoes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                String almoxarifadoPadrao = dbInstance.parametrosPadraoDAO().GetCodigoAlmoxarifado();
                Posicoes posicao = intentPosicaoOriginal != null ? dbInstance.posicoesDAO().GetByCodPosicao(intentPosicaoOriginal) : null;
                List<String> posicaoList = dbInstance.posicoesDAO().GetPosicoesByAlmoxarifado(almoxarifadoPadrao);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arraySpinner_posicao.addAll(posicaoList);
                        adapter_posicao.notifyDataSetChanged();

                        if (posicao != null)
                        {
                            sp_cadastro_posicao_original.setSelection(adapter_posicao.getPosition(posicao.getCodigo()));
                        }
                    }
                });
            }
        }).start();
    }

    private void PreencherCadastroEditar(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(intentIdOriginal);
                String posicao = dbInstance.posicoesDAO().GetCodigoByIdOriginal(cadastroMateriais.getPosicaoOriginalItemIdoriginal());
                String modelo = dbInstance.modeloMateriaisDAO().GetModeloByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());
                String numProduto = dbInstance.modeloMateriaisDAO().GetNumProdutoByModelo(modelo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        sp_cadastro_posicao_original.setSelection(adapter_posicao.getPosition(posicao));
                        sp_cadastro_Material_Type.setSelection(adapter_modeloMaterial.getPosition(modelo));
                        PK_Serie.setText(cadastroMateriais.getNumSerie());
                        PK_Lote.setText(cadastroMateriais.getPatrimonio());
                        Dados_Tecnicos.setText(cadastroMateriais.getDadosTecnicos());
                        num_NF.setText(cadastroMateriais.getNotaFiscal());
                        et_valor.setText(String.valueOf(cadastroMateriais.getValorUnitario()));

                        if (cadastroMateriais.getDataValidade() != null) tvsp_dtvalidade.setText(sdformat.format(cadastroMateriais.getDataValidade()));

                        if (cadastroMateriais.getDataEntradaNotaFiscal() != null) tvsp_data_nf.setText(sdformat.format(cadastroMateriais.getDataEntradaNotaFiscal()));
                    }
                });
            }
        }).start();
    }

    private void closeWindowTimer(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                close_at();
            }
        }, 1500);
    }

}
