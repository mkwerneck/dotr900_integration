package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPWEBListaTarefasEqReadinessTables {

    private SQLiteDatabase connect;

    public Query_UPWEBListaTarefasEqReadinessTables(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> ListaTarefasAndamentoQuery(Context context){
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

    public ArrayAdapter<String> ListaTarefasEmAndamentoQuery(Context context){
        ArrayAdapter<String> listaTarefas1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBListaTarefasEqReadinessTables WHERE Status <> 'Conluido' And Status <> 'Cancelado' order by Prioridade_Execucao", null);

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

    public Cursor ListaTarefasAtivosQuery(String resultado1){
        //Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaTarefasEqReadinessTables WHERE Status <> 'Concluída' And Status <> 'Cancelada' and Trace_Number like '%"+ resultado1 +"%'", null);

        Cursor cursor = connect.rawQuery("SELECT DISTINCT listatarefas1.* FROM UPWEBListaTarefasEqReadinessTables as listatarefas1 inner join UPWEBLista_Serviços_AdicionaisSet as listaservicos1 on listaservicos1.ListaTarefasEqReadinessTables = listatarefas1.Id_Original WHERE listatarefas1.Status <> 'Concluída' And listatarefas1.Status <> 'Cancelada' And (listatarefas1.Trace_Number like '%"+ resultado1 +"%' or listaservicos1.Resultado like '%"+ resultado1 +"%') order by Id_Original desc", null);
        return cursor;
    }

    public Cursor ListaTarefasAtivosQuerynoFilter(){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaTarefasEqReadinessTables WHERE Status <> 'Concluída' And Status <> 'Cancelada'", null);
        return cursor;
    }

    public Cursor ListaTarefasIdOriginal(String id_Original1){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaTarefasEqReadinessTables WHERE Id_Original = " + id_Original1 + " LIMIT 1", null);
        return cursor;
    }
}
