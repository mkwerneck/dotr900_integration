package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 01/02/2017.
 */

public class Query_UPMOBListaTarefasEqReadinessTables {

    private SQLiteDatabase connect;

    public Query_UPMOBListaTarefasEqReadinessTables(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> ListaTarefasAllQuery(Context context){
        ArrayAdapter<String> listaTarefas1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPMOBListaTarefasEqReadinessTables", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String titulo_tarefa = cursor.getString(2);
                listaTarefas1.add(titulo_tarefa);
            }while (cursor.moveToNext());

        }

        return listaTarefas1;
    }

    public ArrayAdapter<Integer> ListaTarefasIdQuery(Context context){
        ArrayAdapter<Integer> listaTarefas1 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBListaTarefasEqReadinessTables LIMIT 1 ORDER BY Id DESC", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                Integer id_Tarefa = cursor.getInt(cursor.getColumnIndex("Id"));
                listaTarefas1.add(id_Tarefa);
            }while (cursor.moveToNext());

        }

        return listaTarefas1;
    }
    public ArrayAdapter<String> ListaTarefasAtivosQuery(Context context, String Trace_number){
        ArrayAdapter<String> listaTarefas1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBListaTarefasEqReadinessTables WHERE  Trace_Number = '"+ Trace_number +"'", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String Trace_Number = cursor.getString(cursor.getColumnIndex("Trace_Number")).toString();
                listaTarefas1.add(Trace_Number);
            }while (cursor.moveToNext());

        }

        return listaTarefas1;
    }
}
