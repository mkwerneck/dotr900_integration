package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheetItens;
import br.com.marcosmilitao.idativosandroid.helper.CustomWorksheetItensAdapter;
import br.com.marcosmilitao.idativosandroid.helper.WorksheetItemKit;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;

public class Act_Consulta_ItensKit extends AppCompatActivity {

    private ListView lv_consult_itens_kit;
    public SQLiteDatabase db;
    private Idativos02Data idativos02Data;
    public String[] itens;
    private Query_UPWEBWorksheets query_upwebworksheets;
    private Query_UPWEBWorksheetItens query_upwebworksheetsitens, query_upwebworksheetitens_editar;
    private WorksheetItemKit worksheetItemKit;
    private CustomWorksheetItensAdapter worksheetItensAdapter;
    private ArrayList<WorksheetItemKit> arrayAdapterWorksheetItens;
    private TextView tv_no_itens;
    private String intentTagid;

    public static final String EXTRA_MESSAGE = "tagidMaterial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_consulta_itens_kit);

        Intent intent = getIntent();
        intentTagid = intent.getStringExtra(EXTRA_MESSAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);

        tv_no_itens = (TextView) findViewById(R.id.tv_no_itens);

        //Lista de Itens do Contentor
        arrayAdapterWorksheetItens = new ArrayList<WorksheetItemKit>();
        worksheetItensAdapter = new CustomWorksheetItensAdapter(this, arrayAdapterWorksheetItens);
        lv_consult_itens_kit = (ListView) findViewById(R.id.lv_consult_itens_kit);
        lv_consult_itens_kit.setAdapter(worksheetItensAdapter);
        PreencherListaItens(intentTagid);

        registerForContextMenu(lv_consult_itens_kit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if (v.getId() == R.id.lv_consult_itens_kit){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            String[] menuItens = new String[]{"Editar"};
            for (int i = 0; i<menuItens.length; i++){
                menu.add(Menu.NONE, i, i, menuItens[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        WorksheetItemKit worksheetItemKit = worksheetItensAdapter.getItem(info.position);

        int idOriginal = worksheetItemKit.getId_original();

        switch(item.getItemId())
        {
            case 0:
                Intent intent = new Intent(this, Act_Cadastro_ItensKit.class);
                intent.putExtra(Act_Cadastro_ItensKit.EXTRA_MESSAGE, intentTagid);
                intent.putExtra(Act_Cadastro_ItensKit.EXTRA_MESSAGE2, idOriginal);
                intent.putExtra("FlagEdit", true);
                startActivity(intent);
                break;
            default:
                break;
        }

        /*Object worksheetItensObject = lv_consult_itens_kit.getAdapter().getItem(info.position);
        Field declaredField;
        String idOriginal;

        try{
            declaredField = worksheetItensObject.getClass().getDeclaredField("id_original");
            declaredField.setAccessible(true);

            try {
                idOriginal = (String)declaredField.get(worksheetItensObject);
            } catch (IllegalAccessException iae){
                showMessage("Erro", iae.toString(), 2);
                return false;
            }

        } catch (NoSuchFieldException e){
            showMessage("Erro", e.toString(), 2);
            return false;
        }

        String tagidExtra = lv_consult_itens_kit.getItemAtPosition(info.position).toString();
        int menuItemIndex = item.getItemId();
        switch (menuItemIndex){
            case 0:
                Intent intent = new Intent(this, Act_Cadastro_ItensKit.class);
                intent.putExtra(Act_Cadastro_ItensKit.EXTRA_MESSAGE, intentTagid);
                intent.putExtra(Act_Cadastro_ItensKit.EXTRA_MESSAGE2, 0);
                intent.putExtra("FlagEdit", true);
                startActivity(intent);
                break;
            default:
                break;
        }*/
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_consulta_itens_kit,menu);
        return true;
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        arrayAdapterWorksheetItens.clear();

        PreencherListaItens(intentTagid);
        worksheetItensAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_novo_item_kit:
                Intent intent = new Intent(this, Act_Cadastro_ItensKit.class);
                intent.putExtra(Act_Cadastro_ItensKit.EXTRA_MESSAGE, intentTagid);
                startActivity(intent);
                return false;
            default:
                return false;
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

    public void PreencherListaItens(String tagid){

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                Integer cadastroMateriaisIdOriginal = dbInstance.cadastroMateriaisDAO().GetIdOriginalByTAGID(intentTagid);
                List<CadastroMateriaisItens> cadastroMateriaisItensList = dbInstance.cadastroMateriaisItensDAO().GetByCadastroMaterias(cadastroMateriaisIdOriginal);

                for (CadastroMateriaisItens cadastroMateriaisItens : cadastroMateriaisItensList)
                {
                    ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(cadastroMateriaisItens.getModeloMateriaisItemIdOriginal());

                    worksheetItemKit = new WorksheetItemKit(cadastroMateriaisItens.getIdOriginal(), modeloMateriais.getModelo(), cadastroMateriaisItens.getNumSerie(), cadastroMateriaisItens.getPatrimonio(), cadastroMateriaisItens.getQuantidade(), cadastroMateriaisItens.getDataCadastro());
                    arrayAdapterWorksheetItens.add(worksheetItemKit);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        worksheetItensAdapter.notifyDataSetChanged();

                        if (arrayAdapterWorksheetItens.isEmpty())
                        {
                            tv_no_itens.setText("Não existem itens cadastrados");
                        } else
                        {
                            tv_no_itens.setText(null);
                        }
                    }
                });
            }
        }).start();
    }
}
