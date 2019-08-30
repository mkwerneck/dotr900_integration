package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 05/05/2017.
 */

public class Query_Parametros_Padrao {
    private SQLiteDatabase connect;
    public Query_Parametros_Padrao(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> Parametros_PadraoPosicaoQuery(Context context){
        //Selecionando o Almoxarifado
        String Cod_Almoxarifado = new String();
        Cursor Parametros_Padrao_Cursor = connect.rawQuery("SELECT * FROM Parametros_Padrao LIMIT 1", null);
        if (Parametros_Padrao_Cursor.getCount() > 0)
        {
            Parametros_Padrao_Cursor.moveToFirst();
            do{
                Cod_Almoxarifado = Parametros_Padrao_Cursor.getString(Parametros_Padrao_Cursor.getColumnIndex("CodAlmoxarifado"));
            }while (Parametros_Padrao_Cursor.moveToNext());
        }

        //Carregando as Posições do Almoxarifado
        Query_UPWEBPosicao query_UPWEBPosicao;
        query_UPWEBPosicao = new Query_UPWEBPosicao(connect);
        return query_UPWEBPosicao.UPWEBAlmoxarifado_PosicaoQuery1(context, Cod_Almoxarifado);
    }

    public String Parametros_PadraoAlmoxarifadoQuery(){
        //Selecionando o Almoxarifado
        String Cod_Almoxarifado = new String();
        Cursor Parametros_Padrao_Cursor = connect.rawQuery("SELECT * FROM Parametros_Padrao LIMIT 1", null);
        if (Parametros_Padrao_Cursor.getCount() > 0)
        {
            Parametros_Padrao_Cursor.moveToFirst();
            do{
                Cod_Almoxarifado = Parametros_Padrao_Cursor.getString(Parametros_Padrao_Cursor.getColumnIndex("Cod_Almoxarifado"));
            }while (Parametros_Padrao_Cursor.moveToNext());
        }
        return Cod_Almoxarifado;
    }

    public ArrayAdapter<String> Parametros_PadraoProprietarioQuery(Context context){
        //Selecionando o Proprietario
        String proprietario = new String();
        Cursor cursor = connect.rawQuery("SELECT * FROM Parametros_Padrao LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                proprietario = cursor.getString(cursor.getColumnIndex("SETORProprietario"));
            }while (cursor.moveToNext());

        }

        //Carregando o Proprietario Padrão
        Query_UPWEBProprietario query_UPWEBProprietario;
        query_UPWEBProprietario = new Query_UPWEBProprietario(connect);
        return query_UPWEBProprietario.UPWEBSETOR_ProprietarioQuery(context, proprietario);
    }

    public String Parametros_PadraoProprietarioQuery2(Context context){
        String proprietario = new String();
        Cursor cursor = connect.rawQuery("SELECT * FROM Parametros_Padrao LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                proprietario = cursor.getString(cursor.getColumnIndex("SETOR_Proprietario"));

            }while (cursor.moveToNext());

        }

        return proprietario;
    }

    /**
     * Created by Vinicius on 26/12/2016.
     */

    public static class Query_UPMOBLista_Serviços_AdicionaisSet {
        private SQLiteDatabase connect;

        public Query_UPMOBLista_Serviços_AdicionaisSet(SQLiteDatabase connect){
            this.connect = connect;
        }

        public ArrayAdapter<String> UPMOBLista_Serviços_AdicionaisSet(Context context){
            ArrayAdapter<String> listaServiços1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
            Cursor cursor = connect.query("UPMOBLista_Serviços_AdicionaisSet", null, null, null, null, null, null);

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

        public Cursor ListaServicoItem(Context context, int UPMOBListaTarefasEqReadinessTables){
            Cursor cursor = connect.rawQuery("SELECT * FROM UPMOBLista_Serviços_AdicionaisSet WHERE UPMOBListaTarefasEqReadinessTables = '" + UPMOBListaTarefasEqReadinessTables + "'", null);

            return cursor;
        }
    }
}
