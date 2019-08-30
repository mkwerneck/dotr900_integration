package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.CustomAdapters.CustomAdapterSpinnerFuncoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;

public class EditarUsuariosActivity extends AppCompatActivity {

    private ApplicationDB dbInstance;
    private BluetoothAdapter BA;

    private CustomAdapterSpinnerFuncoes funcoesAdapter;

    private ArrayList<FuncoesCU> funcoesArrayList;

    private EditText et_edusu_username, et_edusu_nomecompleto, et_edusu_email;

    private CheckBox ckb_edusu_enviarsenha;

    private Spinner sp_edusu_funcao;

    private String funcaoSelected = null;
    private String tagid;

    private Handler preencherDadosHandler;
    private Handler salvarHandler;
    private Handler mainHandler;

    private HandlerThread preencherDadosThread;
    private HandlerThread salvarThread;

    private Intent intent;

    public static final String EXTRA_MESSAGE = "tagid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuarios);

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

        preencherDadosThread = new HandlerThread("preencherDadosThread");
        preencherDadosThread.start();
        preencherDadosHandler = new Handler(preencherDadosThread.getLooper())
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

        intent = getIntent();
        tagid = intent.getStringExtra(EXTRA_MESSAGE);

        //inicializando o spinner de funcoes
        FillSpinerRoles();

        et_edusu_nomecompleto = (EditText) findViewById(R.id.et_edusu_nomecompleto);
        et_edusu_email = (EditText) findViewById(R.id.et_edusu_email);
        et_edusu_username = (EditText) findViewById(R.id.et_edusu_username);

        ckb_edusu_enviarsenha = (CheckBox) findViewById(R.id.ckb_edusu_enviarsenha);

        PreencherDados();
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

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_editar_usuarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sync_eu:
                ESync.GetSyncInstance().SyncDatabase(EditarUsuariosActivity.this);
                return true;
            case R.id.action_atualizar_eu:
                Salvar();
                return true;
            default:

                return false;
        }
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
                funcoesAdapter = new CustomAdapterSpinnerFuncoes(EditarUsuariosActivity.this, funcoesArrayList);

                sp_edusu_funcao = (Spinner) findViewById(R.id.sp_edusu_funcao);
                sp_edusu_funcao.setAdapter(funcoesAdapter);
                sp_edusu_funcao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

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

    private void PreencherDados()
    {
        preencherDadosHandler.post(new Runnable() {
            @Override
            public void run() {
                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                if (usuario != null)
                {
                    et_edusu_nomecompleto.setText(usuario.getNomeCompleto());
                    et_edusu_email.setText(usuario.getEmail());
                    et_edusu_username.setText(usuario.getUserName());

                    ckb_edusu_enviarsenha.setChecked(false);
                }
            }
        });
    }

    private void Salvar(){
        salvarHandler.post(new Runnable() {
            @Override
            public void run() {
                 if(funcaoSelected == null) {
                    showMessage("AVISO", "Informe a Função do usuário", 3);
                    return;
                }else if(et_edusu_username.getText().toString() == null || et_edusu_username.getText().toString().equals("0") || et_edusu_username.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Username do usuário", 3);
                    return;
                }else if(et_edusu_email.getText().toString() == null || et_edusu_email.getText().toString().equals("0") || et_edusu_email.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Email do usuário", 3);
                    return;
                }else if(et_edusu_nomecompleto.getText().toString() == null || et_edusu_nomecompleto.getText().toString().equals("0") || et_edusu_nomecompleto.getText().toString().equals("")){
                    showMessage("AVISO", "Informe o Nome Completo do usuário", 3);
                    return;
                }

                Usuarios usuario = dbInstance.usuariosDAO().GetByTAGID(tagid);

                if (usuario != null)
                {
                    UPMOBUsuarios upmobUsuarios = new UPMOBUsuarios();
                    upmobUsuarios.setIdOriginal(usuario.getIdOriginal());
                    upmobUsuarios.setUsername(et_edusu_username.getText().toString());
                    upmobUsuarios.setRoleIdOriginal(funcaoSelected);
                    upmobUsuarios.setNomeCompleto(et_edusu_nomecompleto.getText().toString());
                    upmobUsuarios.setEmail(et_edusu_email.getText().toString());
                    upmobUsuarios.setTAGID(tagid);
                    upmobUsuarios.setEnviarSenhaEmail(ckb_edusu_enviarsenha.isChecked());
                    upmobUsuarios.setDescricaoErro(null);
                    upmobUsuarios.setFlagErro(false);
                    upmobUsuarios.setFlagAtualizar(true);
                    upmobUsuarios.setFlagProcess(false);
                    upmobUsuarios.setCodColetor(Build.SERIAL);

                    dbInstance.upmobUsuariosDAO().Create(upmobUsuarios);

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("Edição", "Edição realizada com sucesso", 1 );
                        }
                    });
                } else {
                    showMessage("Edição", "Usuário não encontrado na base de dados para edição", 3);
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
}
