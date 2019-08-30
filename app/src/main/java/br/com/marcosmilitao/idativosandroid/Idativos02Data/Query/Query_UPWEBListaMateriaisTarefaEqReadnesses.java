package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPWEBListaMateriaisTarefaEqReadnesses {
    private SQLiteDatabase connect;

    public Query_UPWEBListaMateriaisTarefaEqReadnesses(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBListaMateriaisTarefaEqReadnessesQuery(Context context){
        ArrayAdapter<String> listaMateriais1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBListaMateriaisTarefaEqReadnessesQuery", null, null, null, null, null, null);

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

    public ArrayAdapter<String> ListaMateriaisQuery(Context context, int Tarefa_FK){
        ArrayAdapter<String> listaMateriais1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisTarefaEqReadnessesQuery WHERE TarefasEqReadinessTable = '" + Tarefa_FK + "'", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String material = cursor.getString(0);
                listaMateriais1.add(material);
            }while (cursor.moveToNext());

        }

        return listaMateriais1;
    }

    public Cursor ListaMateriaisIdOriginal(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisTarefaEqReadnessesQuery WHERE Id_Original = " + Id_Original + " LIMIT 1", null);
        return cursor;
    }
}
