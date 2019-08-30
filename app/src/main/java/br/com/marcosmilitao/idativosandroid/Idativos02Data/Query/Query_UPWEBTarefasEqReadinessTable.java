package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPWEBTarefasEqReadinessTable {
    private SQLiteDatabase connect;

    public Query_UPWEBTarefasEqReadinessTable(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> TarefasAndamentoQuery(Context context){
        ArrayAdapter<String> tarefa1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBTarefasEqReadinessTable", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String trace_number = cursor.getString(1);
                tarefa1.add(trace_number);
            }while (cursor.moveToNext());

        }

        return tarefa1;
    }

    public Cursor TarefaCategoriaItem(String categoria_Equipamento1) {
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBTarefasEqReadinessTable WHERE Categoria_Equipamento = '" + categoria_Equipamento1 + "' order by Cod_Tarefa asc", null);
        return cursor;
    }

    public int TarefaAtivoQuery(Context context, int categoria_Equipamento1) {
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBTarefasEqReadinessTable WHERE Equipment_Type = '" + categoria_Equipamento1 + "'", null);

        int id_Tarefa = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id_Tarefa = cursor.getInt(cursor.getColumnIndex("_Id"));
        }
        return id_Tarefa;
    }

    public Cursor UPWEBTarefasIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBTarefasEqReadinessTable WHERE Id_Original='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
