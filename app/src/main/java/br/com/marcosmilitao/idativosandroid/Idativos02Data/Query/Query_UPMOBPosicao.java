package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 19/07/2017.
 */

public class Query_UPMOBPosicao {
    private SQLiteDatabase connect;
    public Query_UPMOBPosicao(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPMOBPosicaoQuery(Context context){
        ArrayAdapter<String> posicao1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPMOBPosicao", null, null, null, null, null, "Desc_Posicao");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String cod_Posicao = cursor.getString(cursor.getColumnIndex("Cod_Posicao"));
                posicao1.add(cod_Posicao);
            }while (cursor.moveToNext());

        }

        return posicao1;
    }

    public Cursor UPMOBCod_PosicaoQuery2(String cod_Posicao1){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBPosicao WHERE Cod_Posicao='"+ cod_Posicao1 +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPMOBPosicaoId_OrigQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBPosicao WHERE Id_Original='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public boolean UPMOBPosicaoExists(String tagid_posicao){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBPosicao WHERE TAGID_Posicao='"+ tagid_posicao +"'"+ "LIMIT 1",null);
        return cursor.moveToFirst();
    }
}
