package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Idutto07 on 06/01/2017.
 */

public class Query_UPWEBProprietario {

    private SQLiteDatabase connect;

    public Query_UPWEBProprietario(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBPMOBProprietarioQuery(Context context){
        ArrayAdapter<String> proprietario1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBProprietarios", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String descricao_proprietario = cursor.getString(cursor.getColumnIndex("Descricao"));
                proprietario1.add(descricao_proprietario);
            }while (cursor.moveToNext());

        }

        return proprietario1;
    }

    public ArrayAdapter<String> UPWEBSETOR_ProprietarioQuery(Context context, String SETOR_Proprietario){
        ArrayAdapter<String> Proprietario1 = new ArrayAdapter<String>(context, R.layout.spinner_item);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBProprietarios WHERE Descricao = '" + SETOR_Proprietario + "'", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String proprietario = cursor.getString(cursor.getColumnIndex("Descricao"));
                Proprietario1.add(proprietario);
            }while (cursor.moveToNext());

        }

        return Proprietario1;
    }

    public Cursor UPWEBSETOR_ProprietarioIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBProprietarios WHERE IdOriginal = '" + Id_Original + "'", null);
        return cursor;
    }
}
