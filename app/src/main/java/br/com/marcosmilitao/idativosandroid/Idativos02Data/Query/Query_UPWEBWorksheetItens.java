package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcoswerneck on 06/12/17.
 */

public class Query_UPWEBWorksheetItens {
    private SQLiteDatabase connect;

    public Query_UPWEBWorksheetItens(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor UPWEBWorksheetItensIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheetItens WHERE Id_Original ='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPWEBWorksheetItensCadastroWorksheetIdQuery(int CadastroMateriaisTable){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheetItens WHERE CadastroMateriaisTable ='"+ CadastroMateriaisTable +"'",null);
        return cursor;
    }

    public Cursor UPWEBWorksheetItensDataCadastroQuery(String data_cadastro){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheetItens WHERE Data_Cadastro ='" + data_cadastro +"'"+ "LIMIT1",null);
        return cursor;
    }
}
