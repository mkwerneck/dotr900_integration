package br.com.marcosmilitao.idativosandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.File;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBMaterial_Type;
import br.com.marcosmilitao.idativosandroid.helper.ImageHelper;

public class Act_Imagem extends AppCompatActivity {
    ImageView imagemModelo;
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;
    private ImageHelper ImageHelper2;
    private SQLiteDatabase connect;
    private Query_UPWEBMaterial_Type query_UPWEBMaterial_Type;
    private String Material_TypeSP;
    private SpinnerAdapter Material_Type;
    SQLiteDatabase db;
    Spinner sp_imagem_Material_Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__imagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("IMAGEM MODELO");
        Intent intent = getIntent();
        db = openOrCreateDatabase("Idativos02Data", Context.MODE_PRIVATE, null);

        try {
            String part = intent.getStringExtra("Part_Number").toString();
            Cursor c_Material_Type = db.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE PartNumber ='" + intent.getStringExtra("Part_Number").toString() +"'",null);
            while(c_Material_Type.moveToNext()) {

                Material_TypeSP = c_Material_Type.getString(c_Material_Type.getColumnIndex("Material_Type"));

            }

        }
        catch(SQLException ex){
            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Banco de Dados: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }


        try {
            connect = openOrCreateDatabase("Idativos02Data", Context.MODE_PRIVATE, null);
            query_UPWEBMaterial_Type = new Query_UPWEBMaterial_Type(connect);
            Material_Type = query_UPWEBMaterial_Type.UPWEBMaterial_TypeQuery(this);

            sp_imagem_Material_Type = (Spinner) findViewById(R.id.sp_imagem_Material_Type);
            sp_imagem_Material_Type.setAdapter(Material_Type);

            sp_imagem_Material_Type.setSelection(getIndex(sp_imagem_Material_Type,Material_TypeSP));
        }
        catch(SQLException ex){
            android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao Criar Banco de Dados: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        imagemModelo = (ImageView) findViewById(R.id.imagemModelo);

        this.ImageHelper2 = new ImageHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaSourceImagem();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    private int getIndex(Spinner spinner, String myString)
    {
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
    public void clicaCarregarImagem(){
        fotoResource=false;
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagem de contato"), 1);*/

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

                ImageHelper2.carregaImagem(caminhoFoto);
            }

        }else{
            if (requestCode == TIRA_FOTO) {
                if(resultCode == Activity.RESULT_OK) {
                    ImageHelper2.carregaImagem(this.localArquivoFoto);
                } else {
                    this.localArquivoFoto = null;
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_imagem, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_imagem_ok:
                Cadastrar_imagem_modelo();
                Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_SHORT).show();
                return true;


            default:
                return false;
        }
    }

    public void Cadastrar_imagem_modelo (){
        try {
            db.execSQL("INSERT INTO UPMOBImagemModelo VALUES('" + sp_imagem_Material_Type.getSelectedItem().toString()+"', '"+ imagemModelo.getTag().toString()  + "'," + 1 +");");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
