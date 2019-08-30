package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPMOBListaMateriaisListaTarefaEqReadnesses {
    private SQLiteDatabase connect;

    public Query_UPMOBListaMateriaisListaTarefaEqReadnesses(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPMOBListaMateriaisTarefaEqReadnessesQuery(Context context){
        ArrayAdapter<String> listaMateriais1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPMOBListaMateriaisTarefaEqReadnessesQuery", null, null, null, null, null, null);

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

    public Cursor ListaMateriaisItem(Context context, int UPMOBListaTarefasEqReadinessTables){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBListaMateriaisListaTarefaEqReadnesses WHERE ListaTarefasEqReadinessTable = '" + UPMOBListaTarefasEqReadinessTables + "'", null);

        return cursor;
    }

    /**
     * Created by Vinicius on 05/02/2017.
     */

    public static class Query_UPWEBLista_Serviços_AdicionaisSet {
        private SQLiteDatabase connect;

        public Query_UPWEBLista_Serviços_AdicionaisSet(SQLiteDatabase connect){
            this.connect = connect;
        }

        public ArrayAdapter<String> UPWEBLista_Serviços_AdicionaisSet(Context context){
            ArrayAdapter<String> listaServiços1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
            Cursor cursor = connect.query("UPWEBLista_Serviços_AdicionaisSet", null, null, null, null, null, null);

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                do{
                    String Serviço = cursor.getString(0);
                    listaServiços1.add(Serviço);
                }while (cursor.moveToNext());

            }

            return listaServiços1;
        }

        public Cursor ListaServicoItens(int ListaTarefasEqReadinessTables){
            Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBLista_Serviços_AdicionaisSet WHERE ListaTarefasEqReadinessTables = '" + ListaTarefasEqReadinessTables + "'", null);
            return cursor;
        }

        public Cursor ListaServicoIdOriginal(String Id_Original){
            Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBLista_Serviços_AdicionaisSet WHERE Id_Original = " + Id_Original + " LIMIT 1", null);
            return cursor;
        }
    }
}
