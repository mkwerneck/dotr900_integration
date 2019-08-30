package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Idutto07 on 23/01/2017.
 */

public class Query_UPWEBAlmoxarifado {

    private SQLiteDatabase connect;

    public Query_UPWEBAlmoxarifado(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBAlmoxarifadoQuery(Context context){
        ArrayAdapter<String> almoxarifado1 = new ArrayAdapter<String>(context, R.layout.spinner_item);
        Cursor cursor = connect.query("UPWEBAlmoxarifado", null, null, null, null, null, "Nome_Almoxarifado ASC");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String nome_almoxarifado1 = cursor.getString(cursor.getColumnIndex("Nome_Almoxarifado"));
                almoxarifado1.add(nome_almoxarifado1);
            }while (cursor.moveToNext());

        }

        return almoxarifado1;
    }

    public String UPWEBNome_AlmoxarifadoQuery(String Cod_Almoxarifado){
        String almoxarifado1 = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBAlmoxarifado WHERE Cod_Almoxarifado = '" + Cod_Almoxarifado + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                almoxarifado1 = cursor.getString(cursor.getColumnIndex("Nome_Almoxarifado"));
            }while (cursor.moveToNext());

        }

        return almoxarifado1;
    }

    public String UPWEBCod_AlmoxarifadoQuery(String Nome_Almoxarifado1){
        String almoxarifado1 = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBAlmoxarifado WHERE Nome_Almoxarifado = '" + Nome_Almoxarifado1 + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                almoxarifado1 = cursor.getString(cursor.getColumnIndex("Cod_Almoxarifado"));
            }while (cursor.moveToNext());

        }

        return almoxarifado1;
    }

}
