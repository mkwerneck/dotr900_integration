package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPWEBServicos_AdicionaisSet {
    private SQLiteDatabase connect;

    public Query_UPWEBServicos_AdicionaisSet(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBServicos_AdicionaisQuery(Context context){
        ArrayAdapter<String> servicos1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBServicos_AdicionaisSet", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String trace_number = cursor.getString(1);
                servicos1.add(trace_number);
            }while (cursor.moveToNext());

        }

        return servicos1;
    }

    public Cursor ServicosTarefaQuery(int TarefasEqReadinessTable){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBServicos_AdicionaisSet WHERE TarefasEqReadinessTable = '" + TarefasEqReadinessTable + "'", null);
        return cursor;
    }

    public Cursor ServicosIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBServicos_AdicionaisSet WHERE Id_Original='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor GetServicoByName(String servico){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBServicos_AdicionaisSet WHERE Servico='"+ servico +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
