package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcoswerneck on 26/07/2018.
 */

public class Query_UPWEBListaResultados {
    private SQLiteDatabase connect;

    public Query_UPWEBListaResultados(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor GetResultsByIdOriginal(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaResultadosServicosSet WHERE Id_Original = " + Id_Original + " LIMIT 1", null);
        return cursor;
    }

    public boolean ResultsByListaMaterialAny(int idListaMaterial, String result){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaResultadosServicosSet WHERE ListaMateriaisListaTarefasEqReadness = " + idListaMaterial + " And Resultado = '" + result + "' LIMIT 1", null);
        return cursor.moveToFirst();
    }

    public Cursor GetResultsByListaMaterial(int idListaMaterial, String result){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaResultadosServicosSet WHERE ListaMateriaisListaTarefasEqReadness = " + idListaMaterial + " And Resultado = '" + result + "' LIMIT 1", null);
        return cursor;
    }

}
