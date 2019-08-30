package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 15/01/2018.
 */

public class Query_UPWEBInventarioPlanejado {
    private SQLiteDatabase connect;

    public Query_UPWEBInventarioPlanejado(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor UPWEBInventarioPlanejadoIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBInventarioPlanejado WHERE Id_Original ='"+ Id_Original +"'"+ "LIMIT 1",null);

        return cursor;
    }

    public ArrayAdapter<String> UPWEBInventarioPlanejadoAllQuery(Context context){
        ArrayAdapter<String> inventarioPlanejadolist = new ArrayAdapter<String>(context, R.layout.spinner_item);

        Cursor cursor = connect.rawQuery("select * from UPWEBInventarioPlanejado", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                inventarioPlanejadolist.add(cursor.getString(cursor.getColumnIndex("Descricao")));
            } while (cursor.moveToNext());
        }

        return inventarioPlanejadolist;
    }

    public String UPWEBInventarioPlanejadoIdOriginal(Context context, String descricao){
        String id_original = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBInventarioPlanejado WHERE Descricao ='"+ descricao +"'"+ "LIMIT 1",null);

        if (cursor.moveToFirst()){
            id_original = cursor.getString(cursor.getColumnIndex("Id_Original"));
        }

        return id_original;
    }

}
