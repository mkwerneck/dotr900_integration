package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DAO.UPMOBUsuarioDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.ESync;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBGrupos;
import br.com.marcosmilitao.idativosandroid.Sync.Sync;
import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBUsuario;
import br.com.marcosmilitao.idativosandroid.helper.RetrieveXMLResult;
import br.com.marcosmilitao.idativosandroid.helper.XR400ReadTags;
import de.greenrobot.event.EventBus;

public class Act_Cadastro_Usuarios extends AppCompatActivity {
    public SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    private ArrayList<String> arrayGrupos;
    private ArrayAdapter<String> adapterGrupos;
    private Query_UPWEBGrupos query_upwebGrupos;
    private UPMOBUsuarioDAO upmobUsuarioDAO;
    private UPMOBUsuario upmobUsuario;
    private Sync sync;
    private TextView tv_tagid_cu;
    private EditText et_usuario_cu, et_nomecompleto_cu, et_email_cu;
    private Spinner sp_grupo_cu;
    private String intentTagid, id_original, permissao, usuario, nomecompleto, email, grupo;
    private boolean flag_atualizar, flag_update, flag_insert, flag_delete;
    public static final String EXTRA_MESSAGE = "tagid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro_usuarios);

        //idativos02Data = new Idativos02Data(this);
        //db = idativos02Data.getReadableDatabase();

        upmobUsuarioDAO = new UPMOBUsuarioDAO(db);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);

        intentTagid = getIntent().getStringExtra(EXTRA_MESSAGE);

        //Preenchendo spiner de grupos
        arrayGrupos = new ArrayList<String>();
        adapterGrupos = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayGrupos);
        sp_grupo_cu = (Spinner) findViewById(R.id.sp_grupo_cu);
        sp_grupo_cu.setAdapter(adapterGrupos);
        FillSpinerGrupos();

        et_usuario_cu = (EditText) findViewById(R.id.et_usuario_cu);
        et_nomecompleto_cu = (EditText) findViewById(R.id.et_nomecompleto_cu);
        et_email_cu = (EditText) findViewById(R.id.et_email_cu);

        tv_tagid_cu = (TextView) findViewById(R.id.tv_tagid_cu);
        tv_tagid_cu.setText(intentTagid);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                ESync.GetSyncInstance().SyncDatabase(Act_Cadastro_Usuarios.this);
                return true;
            default:
                return false;
        }
    }

    public void Atualizar(){
        if (et_usuario_cu.getText().toString().isEmpty()){
            showMessage("AVISO", "Digite um id de usuário", 3);
            return;
        }

        if (et_nomecompleto_cu.getText().toString().isEmpty()){
            showMessage("AVISO", "Digite o nome do usuário", 3);
            return;
        }

        CriarUsuario();
    }

    private void CriarUsuario(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                UPMOBUsuarios upmobUsuarios = new UPMOBUsuarios();
                upmobUsuarios.setIdOriginal("0");
                //upmobUsuarios.setPermissao("Administrador");
                //upmobUsuarios.setUsuario(et_usuario_cu.getText().toString());
                upmobUsuarios.setEmail(et_email_cu.getText().toString());
                upmobUsuarios.setNomeCompleto(et_nomecompleto_cu.getText().toString());
                upmobUsuarios.setTAGID(intentTagid);
                //upmobUsuarios.setGrupo(sp_grupo_cu.getSelectedItem().toString());
                upmobUsuarios.setDescricaoErro(null);
                upmobUsuarios.setFlagErro(false);
                upmobUsuarios.setFlagAtualizar(false);
                upmobUsuarios.setFlagProcess(false);

                dbInstance.upmobUsuariosDAO().Create(upmobUsuarios);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Cadastro", "Cadastro realizado com sucesso", 1 );
                    }
                });

                runtimer();
            }
        }).start();

        //criando objeto UPMOBUsuario
        upmobUsuario = new UPMOBUsuario();
        upmobUsuario.IdOriginal = "0";
        upmobUsuario.Permissao = "Administrador";
        upmobUsuario.Usuario = et_usuario_cu.getText().toString();
        upmobUsuario.Email = (!et_email_cu.getText().toString().isEmpty() ? et_email_cu.getText().toString() : null);
        upmobUsuario.NomeCompleto = et_nomecompleto_cu.getText().toString();
        upmobUsuario.TAGID = intentTagid;
        upmobUsuario.Grupo = (!sp_grupo_cu.getSelectedItem().toString().isEmpty() ? sp_grupo_cu.getSelectedItem().toString() : null);
        upmobUsuario.DescricaoErro = null;
        upmobUsuario.FlagErro = false;
        upmobUsuario.FlagAtualizar = false;
        upmobUsuario.FlagProcess = false;
    }

    private void runtimer(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
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

    private void FillSpinerGrupos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                List<String> gruposList = dbInstance.gruposDAO().GetAllTitulos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayGrupos.addAll(gruposList);
                        adapterGrupos.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
