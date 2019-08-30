package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Vinicius on 24/12/2016.
 */

public class Query_UPWEBPosicao {
    private SQLiteDatabase connect;
    public Query_UPWEBPosicao(SQLiteDatabase connect){
        this.connect = connect;
    }

    public String UPWEBCod_PosicaoQuery(String cod_Posicao1){
        String tagid_posicao1 = new String();
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE Codigo='"+ cod_Posicao1 +"'"+ "LIMIT 1",null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                tagid_posicao1 = cursor.getString(cursor.getColumnIndex("TAGID"));
            }while (cursor.moveToNext());

        }
        return tagid_posicao1;
    }

    public Cursor UPWEBPosicaoId_OrigQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE IdOriginal='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public ArrayAdapter<String> UPWEBAlmoxarifado_PosicaoQuery1(Context context, String Cod_Almoxarifado){
        ArrayAdapter<String> cod_Almoxarifado1 = new ArrayAdapter<String>(context, R.layout.spinner_item);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE Almoxarifado = '" + Cod_Almoxarifado + "' ORDER BY Codigo ASC", null);
        if (cursor.getCount() > 0)
        {
            String posicao_Padrao = "Selecione uma posição";
            cod_Almoxarifado1.add(posicao_Padrao);
            cursor.moveToFirst();
            do{
                String cod_Posicao1 = cursor.getString(cursor.getColumnIndex("Codigo"));
                cod_Almoxarifado1.add(cod_Posicao1);
            }while (cursor.moveToNext());

        }

        return cod_Almoxarifado1;
    }

    public ArrayAdapter<String> UPWEBAlmoxarifado_PosicaoQuery2(Context context, String Cod_Almoxarifado){
        ArrayAdapter<String> cod_Almoxarifado1 = new ArrayAdapter<String>(context, R.layout.spinner_item);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE Almoxarifado = '" + Cod_Almoxarifado + "' ORDER BY Codigo ASC", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String cod_Posicao1 = cursor.getString(cursor.getColumnIndex("Codigo"));
                cod_Almoxarifado1.add(cod_Posicao1);
            }while (cursor.moveToNext());

        }

        return cod_Almoxarifado1;
    }

    public String UPWEBTAGID_Cod_Almoxarifadoquery(String tagid_Posicao1){
        String cod_almoxarifado1 = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE TAGID = '" + tagid_Posicao1 + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                cod_almoxarifado1 = cursor.getString(cursor.getColumnIndex("Almoxarifado"));
            }while (cursor.moveToNext());

        }
        return cod_almoxarifado1;
    }

    public String UPWEBCod_Posicao_Cod_Almoxarifadoquery(String cod_Posicao1){
        String cod_almoxarifado1 = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE Codigo = '" + cod_Posicao1 + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                cod_almoxarifado1 = cursor.getString(cursor.getColumnIndex("Almoxarifado"));
            }while (cursor.moveToNext());

        }
        return cod_almoxarifado1;
    }

    public String UPWEBTAGID_PosicaoQuery(String tagid_Posicao1){
        String cod_posicao1 = new String();
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE TAGID = '" + tagid_Posicao1 + "'" + " LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                cod_posicao1 = cursor.getString(cursor.getColumnIndex("Codigo"));
            }while (cursor.moveToNext());

        }
        return cod_posicao1;
    }

    public boolean UPWEBBPosicaoExists(String tagid_posicao){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBPosicao WHERE TAGID='"+ tagid_posicao +"'"+ "LIMIT 1",null);
        return cursor.moveToFirst();
    }
}
