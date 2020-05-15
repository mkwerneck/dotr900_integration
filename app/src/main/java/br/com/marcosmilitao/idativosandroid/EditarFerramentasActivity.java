package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterCadastrarTagid;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterModeloMateriais;
import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterPosicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import de.greenrobot.event.EventBus;

public class EditarFerramentasActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;

    private CustomAdapterModeloMateriais modeloMateriaisAdapter;
    private CustomAdapterPosicoes posicoesAdapter;
    private CustomAdapterCadastrarTagid listViewAdapter;
    private ArrayAdapter<String> statusAdapter;

    private ArrayList<ModeloMateriaisCF> modeloMateriaisArrayList;
    private ArrayList<PosicaoCF> posicoesArrayList;
    private ArrayList<String> statusArrayList;

    private AppCompatAutoCompleteTextView et_edfer_modelo, et_edfer_posicao;

    private TextView et_edfer_numserie, et_edfer_patrimonio, et_edfer_quantidade, et_edfer_notafiscal;
    private TextView tvsp_edfer_dtnotafiscal, tvsp_edfer_dtvalidade, tvsp_edfer_dtfabricacao;

    private Spinner et_edfer_status;

    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    private SlideDateTimeListener slideListenernf, slideListenerdv, slideListenerdf;

    private CurrencyEditText et_edfer_valorunitario;

    private Integer posicaoSelected = null;
    private Integer modeloSelected = null;

    public static final String EXTRA_MESSAGE = "tagid";

    private Intent intent;

    private String tagid;

    private int idOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ferramentas);

        //iniciando a toolbar para a activity
        setupToolbar();

        dbInstance = RoomImplementation.getmInstance().getDbInstance();

        intent = getIntent();
        tagid = intent.getStringExtra(EXTRA_MESSAGE);

        et_edfer_posicao = (AppCompatAutoCompleteTextView) findViewById(R.id.et_edfer_posicao);
        et_edfer_modelo = (AppCompatAutoCompleteTextView) findViewById(R.id.et_edfer_modelo);

        FillAdapterModeloMateriais();
        FillAdapterPosicoes();
        FillAdapterStatus();

        //determinando os listeners dos datepickers
        slideListenernf = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_edfer_dtnotafiscal.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };
        slideListenerdv = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_edfer_dtvalidade.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };
        slideListenerdf = new SlideDateTimeListener(){
            @Override
            public void onDateTimeSet(Date date){
                tvsp_edfer_dtfabricacao.setText(formato.format(date));
            }

            @Override
            public void onDateTimeCancel(){

            }
        };

        tvsp_edfer_dtnotafiscal = (TextView) findViewById(R.id.tvsp_edfer_dtnotafiscal);
        tvsp_edfer_dtnotafiscal.setText("DD/MM/AAAA");
        tvsp_edfer_dtnotafiscal.setOnClickListener(new View.OnClickListener(){
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

        tvsp_edfer_dtvalidade = (TextView) findViewById(R.id.tvsp_edfer_dtvalidade);
        tvsp_edfer_dtvalidade.setText("DD/MM/AAAA");
        tvsp_edfer_dtvalidade.setOnClickListener(new View.OnClickListener(){
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

        tvsp_edfer_dtfabricacao = (TextView) findViewById(R.id.tvsp_edfer_dtfabricacao);
        tvsp_edfer_dtfabricacao.setText("DD/MM/AAAA");
        tvsp_edfer_dtfabricacao.setOnClickListener(new View.OnClickListener(){
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

        et_edfer_valorunitario = (CurrencyEditText) findViewById(R.id.et_edfer_valorunitario);

        et_edfer_numserie = (TextView) findViewById(R.id.et_edfer_numserie);
        et_edfer_patrimonio = (TextView) findViewById(R.id.et_edfer_patrimonio);
        et_edfer_quantidade = (TextView) findViewById(R.id.et_edfer_quantidade);
        et_edfer_notafiscal = (TextView) findViewById(R.id.et_edfer_notafiscal);

        PreencherDados();
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
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        CloseAct();
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

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_editar_ferramentas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_itens_ef:
                Intent intent = new Intent();
                intent.setClass(EditarFerramentasActivity.this, Act_Consulta_ItensKit.class);
                intent.putExtra(Act_Consulta_ItensKit.EXTRA_MESSAGE, tagid);
                startActivity(intent);
                return true;
            case R.id.action_salvar_ef:
                Salvar();
                return true;
            case R.id.action_sync_ef:
                ESync.GetSyncInstance().SyncDatabase(EditarFerramentasActivity.this);
                return true;
            default:
                return false;
        }
    }

    private void CloseAct()
    {
        this.finish();
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
                        modeloMateriaisAdapter = new CustomAdapterModeloMateriais(EditarFerramentasActivity.this, modeloMateriaisArrayList, dbInstance);

                        et_edfer_modelo.setAdapter(modeloMateriaisAdapter);
                        et_edfer_modelo.setThreshold(1);
                        et_edfer_modelo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        posicoesAdapter = new CustomAdapterPosicoes(EditarFerramentasActivity.this, posicoesArrayList, dbInstance);

                        et_edfer_posicao.setAdapter(posicoesAdapter);
                        et_edfer_posicao.setThreshold(1);
                        et_edfer_posicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void FillAdapterStatus()
    {
        List<String> status = Arrays.asList(new String[]{"Disponível", "Fora de Uso"});

        statusArrayList = new ArrayList<String>(status);
        statusAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, statusArrayList );

        et_edfer_status = (Spinner) findViewById(R.id.et_edfer_status);
        et_edfer_status.setAdapter(statusAdapter);
    }

    private void PreencherDados()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByTAGID(tagid, true);

                if (cadastroMateriais != null)
                {
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriais.getModeloMateriaisItemIdOriginal());
                    Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(cadastroMateriais.getPosicaoOriginalItemIdoriginal());

                    if (modeloMateriais != null && posicao != null)
                    {
                        idOriginal = cadastroMateriais.getIdOriginal();
                        modeloSelected = modeloMateriais.getIdOriginal();
                        posicaoSelected = posicao.getIdOriginal();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                et_edfer_numserie.setText(cadastroMateriais.getNumSerie());
                                et_edfer_patrimonio.setText(cadastroMateriais.getPatrimonio());
                                et_edfer_quantidade.setText(String.valueOf(cadastroMateriais.getQuantidade()));
                                et_edfer_valorunitario.setText(String.valueOf(cadastroMateriais.getValorUnitario()));
                                et_edfer_notafiscal.setText(cadastroMateriais.getNotaFiscal());

                                if (cadastroMateriais.getDataValidade() != null) tvsp_edfer_dtvalidade.setText(formato.format(cadastroMateriais.getDataValidade()));
                                if (cadastroMateriais.getDataEntradaNotaFiscal() != null) tvsp_edfer_dtnotafiscal.setText(formato.format(cadastroMateriais.getDataEntradaNotaFiscal()));
                                if (cadastroMateriais.getDataFabricacao() != null) tvsp_edfer_dtfabricacao.setText(formato.format(cadastroMateriais.getDataFabricacao()));

                                et_edfer_modelo.setText(modeloMateriais.getModelo());

                                et_edfer_posicao.setText(posicao.getCodigo());
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void Salvar()
    {
         if(posicaoSelected == null) {
            showMessage("AVISO", "Informe a Posição Original da Ferramenta", 3);

        } else if(modeloSelected == null) {
             showMessage("AVISO", "Informe o Modelo da Ferramenta", 3);

         }else if(et_edfer_status.getSelectedItem() == null) {
                 showMessage("AVISO", "Informe o Status da Ferramenta", 3);

        } else if(et_edfer_notafiscal.getText().toString() == null || et_edfer_notafiscal.getText().toString().equals("0") || et_edfer_notafiscal.getText().toString().equals("")){
            showMessage("AVISO", "Informe a Nota Fiscal da Ferramenta", 3);

        } else if(et_edfer_quantidade.getText().toString() == null || et_edfer_quantidade.getText().toString().equals("0") || et_edfer_quantidade.getText().toString().equals("")){
            showMessage("AVISO", "Informe a Quantidade da Ferramenta", 3);

        }else if(tvsp_edfer_dtnotafiscal.getText().toString().equals("DD/MM/AAAA")){
            showMessage("AVISO", "Informe a Data de Entrada da Nota Fiscal da Ferramenta", 3);

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Date dataHoraEvento = Calendar.getInstance().getTime();

                    Date dataValidade, dataEntradaNF, dataFabricacao;
                    BigDecimal valorUnitario;

                    //Formatando as datas Validade e Entrada NF
                    if (ValidarData(tvsp_edfer_dtvalidade.getText().toString()) != null){
                        dataValidade = ValidarData(tvsp_edfer_dtvalidade.getText().toString());
                    } else {
                        dataValidade = null;
                    }

                    if (ValidarData(tvsp_edfer_dtnotafiscal.getText().toString()) != null){
                        dataEntradaNF = ValidarData(tvsp_edfer_dtnotafiscal.getText().toString());
                    } else {
                        dataEntradaNF = null;
                    }

                    if (ValidarData(tvsp_edfer_dtfabricacao.getText().toString()) != null){
                        dataFabricacao = ValidarData(tvsp_edfer_dtfabricacao.getText().toString());
                    } else {
                        dataFabricacao = null;
                    }

                    //Formatando Valor Unitario
                    valorUnitario = FormatarValor(String.valueOf(et_edfer_valorunitario.getRawValue()));

                    UPMOBCadastroMateriais upmobCadastroMateriais = new UPMOBCadastroMateriais();
                    upmobCadastroMateriais.setIdOriginal(idOriginal);
                    upmobCadastroMateriais.setPatrimonio(et_edfer_patrimonio.getText().toString());
                    upmobCadastroMateriais.setNumSerie(et_edfer_numserie.getText().toString());
                    upmobCadastroMateriais.setQuantidade(Integer.parseInt(et_edfer_quantidade.getText().toString()));
                    upmobCadastroMateriais.setDadosTecnicos("");
                    upmobCadastroMateriais.setTAGID(tagid);
                    upmobCadastroMateriais.setPosicaoOriginalItemId(posicaoSelected);
                    upmobCadastroMateriais.setModeloMateriaisItemId(modeloSelected);
                    upmobCadastroMateriais.setNotaFiscal(et_edfer_notafiscal.getText().toString());
                    upmobCadastroMateriais.setValorUnitario(valorUnitario.doubleValue());
                    upmobCadastroMateriais.setCodColetor(Build.SERIAL);
                    upmobCadastroMateriais.setDescricaoErro(null);
                    upmobCadastroMateriais.setFlagErro(false);
                    upmobCadastroMateriais.setFlagAtualizar(true);
                    upmobCadastroMateriais.setFlagProcess(false);
                    upmobCadastroMateriais.setDataHoraEvento(dataHoraEvento);
                    upmobCadastroMateriais.setDataValidade(dataValidade);
                    upmobCadastroMateriais.setDataEntradaNotaFiscal(dataEntradaNF);
                    upmobCadastroMateriais.setDataFabricacao(dataFabricacao);
                    upmobCadastroMateriais.setStatus(et_edfer_status.getSelectedItem().toString());

                    dbInstance.upmobCadastroMateriaisDAO().Create(upmobCadastroMateriais);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Editar", "Cadastro editado com Sucesso", 1 );
                        }
                    });
                }
            }).start();
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

    public Date ValidarData(String data_validar)
    {
        Date date = null;

        if (data_validar == null){
            return date;
        }

        try {
            date = formato.parse(data_validar);
        } catch (Exception e){
            return date;
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
}
