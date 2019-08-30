package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcoswerneck on 15/01/2018.
 */

public class Query_UPWEBListaMateriaisInvPlanejado {
    private SQLiteDatabase connect;

    public Query_UPWEBListaMateriaisInvPlanejado(SQLiteDatabase connect){

        this.connect = connect;
    }

    public Cursor UPWEBListaMateriaisInvPlanejado(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisInvPlanejado WHERE Id_Original ='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPWEBListaMateriaisInvPlanDesc(Context context, String id_inventarioplanejado){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBListaMateriaisInvPlanejado WHERE InventarioPlanejadoTable ='"+ id_inventarioplanejado +"'",null);
        return cursor;
    }

}
