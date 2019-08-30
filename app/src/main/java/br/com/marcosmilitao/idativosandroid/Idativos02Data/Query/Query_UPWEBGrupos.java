package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 04/06/2018.
 */

public class Query_UPWEBGrupos {
    private SQLiteDatabase connect;

    public Query_UPWEBGrupos(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor GruposIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBGrupos WHERE IdOriginal='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public ArrayAdapter<String> ListAll(Context context){
        ArrayAdapter<String> grupos = new ArrayAdapter<String>(context, R.layout.spinner_item);

        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBGrupos", null);
        while (cursor.moveToNext()){
            String grupo = cursor.getString(cursor.getColumnIndex("Titulo"));
            grupos.add(grupo);
        }

        return grupos;
    }

}
