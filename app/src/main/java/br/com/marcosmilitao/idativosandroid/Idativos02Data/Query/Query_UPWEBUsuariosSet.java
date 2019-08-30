package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 05/02/2017.
 */

public class Query_UPWEBUsuariosSet {
    private SQLiteDatabase connect;

    public Query_UPWEBUsuariosSet(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBUsuariosSet(Context context){
        ArrayAdapter<String> listaMateriais1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBUsuariosSet", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String titulo_tarefa = cursor.getString(2);
                listaMateriais1.add(titulo_tarefa);
            }while (cursor.moveToNext());

        }

        return listaMateriais1;
    }

    public Cursor ListaUsuariosItens(String TAGID_Usuario){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBUsuariosSet WHERE TAGID = '" + TAGID_Usuario + "'", null);
        return cursor;
    }

    public ArrayAdapter<String> UsuarioTAGIDQuery(Context context, String TAGID_Usuario){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBUsuariosSet WHERE TAGID = '" + TAGID_Usuario + "'" + " LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String Quantidade = cursor.getString(cursor.getColumnIndex("NomeCompleto"));
                material1.add(Quantidade);

            }while (cursor.moveToNext());

        }
        return material1;
    }

    public Cursor UsuariosIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBUsuariosSet WHERE IdOriginal='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
