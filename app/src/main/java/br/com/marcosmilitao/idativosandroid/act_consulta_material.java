package br.com.marcosmilitao.idativosandroid;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;

public class act_consulta_material extends AppCompatActivity {

    private Button btn_consulta_material_pesquisar;
    private EditText edt_consulta_material_pesquisar;
    private ListView list_Consulta_Material;
    private SQLiteDatabase connect;
    private Context context;
    private Query_InventarioMaterial query_inventarioMaterial;
    private Query_UPWEBWorksheets query_UPWEBWorksheets;
    private ArrayAdapter<String> list_Consulta_MaterialAdapter;
    private Cursor listaMateriais;
    private BluetoothAdapter BA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_consulta_material);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_white_24dp);

        connect = openOrCreateDatabase("Idativos02Data", Context.MODE_PRIVATE, null);
        BA = BluetoothAdapter.getDefaultAdapter();
        edt_consulta_material_pesquisar = (EditText)findViewById(R.id.edt_consulta_material_pesquisar);

        list_Consulta_Material = (ListView) findViewById(R.id.list_Consulta_Material);
        list_Consulta_Material.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tagid = adapterView.getItemAtPosition(i).toString().split(" ")[0];
                Intent intent = new Intent();
                intent.setClass(act_consulta_material.this, Act_Cadastro_dados.class);
                //intent.putExtra("position", position);
                // Or / And
                //showMessage("",tag);

                    Cursor c_tagid = null;
                    try {
                        query_UPWEBWorksheets = new Query_UPWEBWorksheets(connect);
                        c_tagid = query_UPWEBWorksheets.UPWEBWorksheetTAGIDQuery(tagid);
                    } catch (Exception e) {
                        showMessage("Editar Cadastro !", e.toString());
                    }
                    if (c_tagid.getCount() > 0) {
                        while (c_tagid.moveToNext()) {
                            String Trace_Number;
                            String categoria = c_tagid.getString(c_tagid.getColumnIndex("Categoria_Equipamento"));
                            if (!categoria.equals("Material Almoxarifado")) {
                                Trace_Number = c_tagid.getString(c_tagid.getColumnIndex("Trace_Number"));
                                intent.putExtra("Trace_Number", Trace_Number);
                            } else {
                                Trace_Number = c_tagid.getString(c_tagid.getColumnIndex("lote_material"));
                                intent.putExtra("trace_number_lote_material", Trace_Number);
                            }

                            intent.putExtra("cod_posicao_original",c_tagid.getString(c_tagid.getColumnIndex("Posicao_Original")));
                            intent.putExtra("tagid", tagid);
                            intent.putExtra("Quantidade", c_tagid.getString(c_tagid.getColumnIndex("Quantidade")));
                            intent.putExtra("categoria", c_tagid.getString(c_tagid.getColumnIndex("Categoria_Equipamento")));
                            intent.putExtra("Part_Number", c_tagid.getString(c_tagid.getColumnIndex("Part_Number")));
                            intent.putExtra("PK_Serie", c_tagid.getString(c_tagid.getColumnIndex("PK_Serie")));
                            intent.putExtra("PK_Lote", c_tagid.getString(c_tagid.getColumnIndex("PK_Lote")));
                            intent.putExtra("Dados_Tecnicos", c_tagid.getString(c_tagid.getColumnIndex("Dados_Tecnicos")));
                            intent.putExtra("num_NF", c_tagid.getString(c_tagid.getColumnIndex("num_NF")));
                            intent.putExtra("Data_Validade", c_tagid.getString(c_tagid.getColumnIndex("Data_Validade")));
                            intent.putExtra("flagEditarCadastro1", "true");
                            intent.putExtra("FlagMobileUpdate", "true");
                            intent.putExtra("FlagMobileInsert", "false");

                            startActivity(intent);

                        }
                    } else {
                        showMessage("AVISO!","O Material selecionado não foi carregado corretamente. Sincronize e tente novamente.");
                    }

            }
        });

        btn_consulta_material_pesquisar = (Button) findViewById(R.id.btn_consulta_material_pesquisar);
        btn_consulta_material_pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListarMateriais();
            }
        });
        ListarMateriais();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void ListarMateriais()
    {
        ArrayAdapter<String> item1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list_Consulta_MaterialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        try {
            query_inventarioMaterial = new Query_InventarioMaterial(connect);
            if (edt_consulta_material_pesquisar.getText() != null){
                listaMateriais = query_inventarioMaterial.InventarioMaterialID_OmniQuery(edt_consulta_material_pesquisar.getText().toString());
            }
           else {
                listaMateriais = query_inventarioMaterial.InventarioMaterialID_OmniQueryFilter();
            }

            if (listaMateriais.getCount() > 0)
            {
                listaMateriais.moveToFirst();
                do{
                    item1.add(listaMateriais.getString(listaMateriais.getColumnIndex("TAGID_Material")).toString() + " | " + listaMateriais.getString(listaMateriais.getColumnIndex("ID_Omni")).toString() + " | " + listaMateriais.getString(listaMateriais.getColumnIndex("Material_Type")).toString() +" | "+ listaMateriais.getString(listaMateriais.getColumnIndex("lote_material")).toString());
                    list_Consulta_MaterialAdapter.add(listaMateriais.getString(listaMateriais.getColumnIndex("ID_Omni")).toString());
                }while (listaMateriais.moveToNext());
            }
            list_Consulta_Material.setAdapter(item1);


            /*  if (item1.getCount() > 0){
                list_Consulta_Material.setAdapter(item1);
            }*/
        }
        catch(SQLException ex){
        }
    }


    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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

}
