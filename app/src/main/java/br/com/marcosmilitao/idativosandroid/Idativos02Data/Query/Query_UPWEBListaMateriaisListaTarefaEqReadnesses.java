package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 05/02/2017.
 */

public class Query_UPWEBListaMateriaisListaTarefaEqReadnesses {
    private SQLiteDatabase connect;

    public Query_UPWEBListaMateriaisListaTarefaEqReadnesses(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBListaMateriaisTarefaEqReadnessesQuery(Context context){
        ArrayAdapter<String> listaMateriais1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBListaMateriaisListaTarefaEqReadnesses", null, null, null, null, null, null);

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

    public Cursor UPWEBListaMateriaisTarefaEqReadnessesQuery2(Context context){
         Cursor cursor = connect.query("UPWEBListaMateriaisListaTarefaEqReadnesses", null, null, null, null, null, null);

        return cursor;
    }

    public Cursor ListaMateriaisItens(int ListaTarefasEqReadinessTables){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisListaTarefaEqReadnesses WHERE ListaTarefasEqReadinessTable = '" + ListaTarefasEqReadinessTables + "' And Status <> 'Concluido' And Status <> 'Cancelado'", null);
        return cursor;
    }

    public Cursor ListaMateriaisIdOriginal(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisListaTarefaEqReadnesses WHERE Id_Original = " + Id_Original + " LIMIT 1", null);
        return cursor;
    }
}
