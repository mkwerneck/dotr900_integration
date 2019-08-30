package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import net.sourceforge.jtds.jdbc.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.jar.JarEntry;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DAO.UPMOBCadastroItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBMaterial_Type;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheetItens;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBCadastroItens;

public class Act_Cadastro_ItensKit extends AppCompatActivity {

    public SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    private String tagid, num_serie, patrimonio, almoxarifado, numero_lote, data_cadastro, data_fabricacao, data_validade, modelo_material, part_number, tagid_contentor, proprietario, id_omni;
    private int id_original, quantidade, id_cadastro_material, id_original_editar;
    private boolean flag_atualizar, flag_update, flag_insert, flag_delete, flag_edit;
    private String [] arraySpinner;
    private ArrayAdapter<String> arrayAdapter;
    private TextView tvsp_datafabricacao_ci, tvsp_validade_ci;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SlideDateTimeListener slidelistenerfab = new SlideDateTimeListener(){
        @Override
        public void onDateTimeSet(Date date){
            tvsp_datafabricacao_ci.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel(){

        }
    };
    private SlideDateTimeListener slidelistenerval = new SlideDateTimeListener(){
        @Override
        public void onDateTimeSet(Date date){
            tvsp_validade_ci.setText(mFormatter.format(date));
        }

        @Override
        public void onDateTimeCancel(){

        }
    };
    private Query_UPWEBMaterial_Type query_UPWEBMaterial_Type, query_upwebMaterial_type_edit;
    private Query_UPWEBWorksheets query_upwebworksheets;
    private Query_UPWEBWorksheetItens query_upwebWorksheetItens;
    private SpinnerAdapter Material_TypeSP;
    private Sync sync;
    private EditText et_quantidade_ci, et_numserie_ci, et_patrimonio_ci;
    private SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
    private UPMOBCadastroItensDAO upmobCadastroItensDAO;
    private UPMOBCadastroItens upmobCadastroItens;

    private Date dataFabricacao, dataValidade;
    private Intent intent;

    public static final String EXTRA_MESSAGE = "tagidContentor";
    public static final String EXTRA_MESSAGE2 = "idOriginal";

    private CustomAdapterModeloMateriais modeloMateriaisAdapter;
    private ArrayList<ModeloMateriaisCF> modeloMateriaisArrayList;
    private AppCompatAutoCompleteTextView et_modelo_ci;
    Integer selectedModelo = 0;

    private ApplicationDB dbInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__cadastro__itens_kit);

        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        intent = getIntent();

        upmobCadastroItensDAO = new UPMOBCadastroItensDAO(db);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_at();
            }
        });

        tagid = getIntent().getStringExtra(EXTRA_MESSAGE);
        id_original_editar = getIntent().getIntExtra(EXTRA_MESSAGE2, 0);
        flag_edit = getIntent().getBooleanExtra("FlagEdit", false);

        et_quantidade_ci = (EditText) findViewById(R.id.et_quantidade_ci);
        et_numserie_ci = (EditText) findViewById(R.id.et_numserie_ci);
        et_patrimonio_ci = (EditText) findViewById(R.id.et_patrimonio_ci);


        //Preenchendo Spiner de Modelos
        et_modelo_ci = (AppCompatAutoCompleteTextView) findViewById(R.id.et_modelo_ci);
        PreencherSpinerModelo();

        tvsp_datafabricacao_ci = (TextView) findViewById(R.id.tvsp_datafabricacao_ci);
        tvsp_datafabricacao_ci.setText("AAAA-MM-DD");
        tvsp_datafabricacao_ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideDateTimePicker.Builder timerBuilder = new SlideDateTimePicker.Builder(getSupportFragmentManager());
                timerBuilder.setListener(slidelistenerfab);
                timerBuilder.setInitialDate(new Date());
                timerBuilder.setIs24HourTime(true);
                timerBuilder.setIndicatorColor(Color.GREEN);
                timerBuilder.build().show();
            }
        });

        tvsp_validade_ci = (TextView) findViewById(R.id.tvsp_validade_ci);
        tvsp_datafabricacao_ci.setText("AAAA-MM-DD");
        tvsp_validade_ci.setOnClickListener(new View.OnClickListener() {
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

        if (flag_edit){
            getSupportActionBar().setTitle("EDITAR ITEM DO CONTENTOR");
            PreencherFormularioEditar();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cadastro_itens_kit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_atualizar_ci:
                Atualizar();
                return true;
            case R.id.action_sync_ci:
                //Nova Chamada para Sincronismo
                ESync.GetSyncInstance().SyncDatabase(Act_Cadastro_ItensKit.this);
                return true;
            default:
                return false;
        }
    }

    public void Atualizar(){
        if (selectedModelo == null){
            showMessage("AVISO", "Selecione um modelo da lista", 3);
            return;
        }

        if (!isInteger(et_quantidade_ci.getText().toString())){
            showMessage("AVISO", "Quantidade inválida", 3);
            return;
        } else if (et_quantidade_ci.getText().toString().equals("0")){
            showMessage("AVISO", "Quantidade inválida", 3);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Date dataHoraEvento = Calendar.getInstance().getTime();
                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);

                if (cadastroMateriais != null)
                {
                    if (ValidarData(tvsp_datafabricacao_ci.getText().toString()) != null){
                        dataFabricacao = ValidarData(tvsp_datafabricacao_ci.getText().toString());
                    } else {
                        dataFabricacao = null;
                    }

                    if (ValidarData(tvsp_validade_ci.getText().toString()) != null){
                        dataValidade = ValidarData(tvsp_validade_ci.getText().toString());
                    } else {
                        dataValidade = null;
                    }

                    UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens = new UPMOBCadastroMateriaisItens();
                    upmobCadastroMateriaisItens.setIdOriginal(id_original_editar);
                    upmobCadastroMateriaisItens.setPatrimonio(et_patrimonio_ci.getText().toString());
                    upmobCadastroMateriaisItens.setNumSerie(et_numserie_ci.getText().toString());
                    upmobCadastroMateriaisItens.setQuantidade(Integer.parseInt(et_quantidade_ci.getText().toString()));
                    upmobCadastroMateriaisItens.setDataHoraEvento(dataHoraEvento);
                    upmobCadastroMateriaisItens.setDataValidade(dataValidade);
                    upmobCadastroMateriaisItens.setDataFabricacao(dataFabricacao);
                    upmobCadastroMateriaisItens.setCodColetor(Build.SERIAL);
                    upmobCadastroMateriaisItens.setDescricaoErro(null);
                    upmobCadastroMateriaisItens.setFlagErro(false);
                    upmobCadastroMateriaisItens.setFlagAtualizar(flag_edit);
                    upmobCadastroMateriaisItens.setFlagProcess(false);
                    upmobCadastroMateriaisItens.setModeloMateriaisItemIdOriginal(selectedModelo);
                    upmobCadastroMateriaisItens.setCadastroMateriaisItemIdOriginal(cadastroMateriais.getIdOriginal());

                    dbInstance.upmobCadastroMateriaisItensDAO().Create(upmobCadastroMateriaisItens);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!flag_edit){
                                LimparDadosFormulario();
                                showMessage("Cadastro", "Cadastro realizado com sucesso", 1 );
                            } else {
                                showMessage("Edição", "Cadastro editado com sucesso", 1);
                            }
                        }
                    });
                } else {
                    showMessage("Atenção", "Cadastro de Ferramenta não encontrado", 3);
                }
            }
        }).start();
    }

    public void PreencherFormularioEditar(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                CadastroMateriaisItens cadastroMateriaisItens = dbInstance.cadastroMateriaisItensDAO().GetByIdOriginal(id_original_editar);

                if (cadastroMateriaisItens != null)
                {
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriaisItens.getModeloMateriaisItemIdOriginal());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_modelo_ci.setText(modeloMateriais.getModelo());
                            selectedModelo = modeloMateriais.getIdOriginal();

                            et_numserie_ci.setText(cadastroMateriaisItens.getNumSerie());
                            et_patrimonio_ci.setText(cadastroMateriaisItens.getPatrimonio());
                            et_quantidade_ci.setText(String.valueOf(cadastroMateriaisItens.getQuantidade()));

                            if (cadastroMateriaisItens.getDataFabricacao() != null) tvsp_datafabricacao_ci.setText(sdformat.format(cadastroMateriaisItens.getDataFabricacao()));

                            if (cadastroMateriaisItens.getDataValidade() != null) tvsp_validade_ci.setText(sdformat.format(cadastroMateriaisItens.getDataValidade()));
                        }
                    });
                }
            }
        }).start();
    }

    public void LimparDadosFormulario(){
        et_modelo_ci.setText(new String());
        selectedModelo = null;

        et_numserie_ci.setText(new String());
        et_patrimonio_ci.setText(new String());
        et_quantidade_ci.setText("1");
        tvsp_validade_ci.setText("AAAA-MM-DD");
        tvsp_datafabricacao_ci.setText("AAAA-MM-DD");
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

    private void close_at(){
        this.finish();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private int getIndex(Spinner spinner, String myString) {
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

    private void PreencherSpinerModelo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ModeloMateriaisCF> modeloMateriaisList = dbInstance.modeloMateriaisDAO().GetAllModelosCustomAdapter();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        modeloMateriaisArrayList = new ArrayList<ModeloMateriaisCF>(modeloMateriaisList);
                        modeloMateriaisAdapter = new CustomAdapterModeloMateriais(Act_Cadastro_ItensKit.this, modeloMateriaisArrayList);

                        et_modelo_ci = (AppCompatAutoCompleteTextView) findViewById(R.id.et_modelo_ci);
                        et_modelo_ci.setAdapter(modeloMateriaisAdapter);
                        et_modelo_ci.setThreshold(1);
                        et_modelo_ci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ModeloMateriaisCF modeloMateriaisCF = (ModeloMateriaisCF) adapterView.getItemAtPosition(i);
                                selectedModelo = modeloMateriaisCF.getIdOriginal();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void runtimer(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LimparDadosFormulario();
            }
        }, 2000);
    }
}
