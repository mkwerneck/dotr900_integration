package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Idutto07 on 22/02/2017.
 */

public class Query_UPWEB_ColetoresDados {
    private SQLiteDatabase connect;

    public Query_UPWEB_ColetoresDados(SQLiteDatabase connect) {
        this.connect = connect;
    }

    public String UPWEB_ColetoresDado_PosicaoQuery(Context context, String CodColetor){
        String cod_posicao1 = new String();
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBColetoresDadosSet WHERE CodColetor = '" + CodColetor + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                cod_posicao1 = cursor.getString(cursor.getColumnIndex("Cod_Posicao"));
            }while (cursor.moveToNext());

        }
        return cod_posicao1;
    }

}