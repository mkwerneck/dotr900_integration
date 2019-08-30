package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 03/03/2017.
 */

public class Query_UPWEBAcoesColetores_Dados {
    private SQLiteDatabase connect;

    public Query_UPWEBAcoesColetores_Dados(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBAcoesColetores_Dados(Context context){
        ArrayAdapter<String> listaAcoes = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBAcoesColetores_Dados", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String acao1 = cursor.getString(0);
                listaAcoes.add(acao1);
            }while (cursor.moveToNext());

        }

        return listaAcoes;
    }

    public Cursor ListaAcoesPendentes(){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBAcoesColetores_Dados WHERE FlagProcess <> 1 ", null);

        return cursor;
    }

    public Cursor UPWEBAcoesColetores_Dados_Id(String Id){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBAcoesColetores_Dados WHERE Id = '" + Id + "'" + " LIMIT 1", null);

        return cursor;
    }
}
