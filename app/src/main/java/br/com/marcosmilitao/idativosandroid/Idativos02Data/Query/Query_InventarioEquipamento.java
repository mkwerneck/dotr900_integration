package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 07/01/2017.
 */

public class Query_InventarioEquipamento {
    private SQLiteDatabase connect;
    public Query_InventarioEquipamento(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor InventarioEquipamentoQuery(Context context, String Status){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioEquipamento WHERE Status <> '" + Status + "'", null);
        return cursor;
    }

    public ArrayAdapter<String> InventarioEquipamentoTAGIDQuery(Context context, String tagid_Equipment1){
        ArrayAdapter<String> equipamento1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioEquipamento WHERE TAGID_Equipment = '" + tagid_Equipment1 + "'" + " LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String equipment_Type = cursor.getString(cursor.getColumnIndex("Equipment_Type"));
                equipamento1.add(equipment_Type);

                String trace_Number = cursor.getString(cursor.getColumnIndex("Trace_Number"));
                equipamento1.add(trace_Number);

                String tagid_Equipment = cursor.getString(cursor.getColumnIndex("TAGID_Equipment"));
                equipamento1.add(tagid_Equipment);
            }while (cursor.moveToNext());

        }

        return equipamento1;
    }

    public Cursor InventarioEquipamentoTraceNumberQuery(String ativo, String Status){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioEquipamento WHERE Trace_Number like '%" + ativo + "%' AND Status <> '" + Status + "'", null);
        return cursor;
    }

    public Cursor InventarioEquipamentoIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioEquipamento WHERE Id_Original='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }

    /**
     * Created by Vinicius on 16/05/2017.
     */

    public static class Query_IDs_Sistema {
        private SQLiteDatabase connect;
        public Query_IDs_Sistema(SQLiteDatabase connect){
            this.connect = connect;
        }

        public Cursor IDs_SistemaQuery(Context context){
            Cursor cursor = connect.query("IDs_Sistema", null, null, null, null, null, null);
            return cursor;
        }

        public String VersaoIDAtivosQuery(){
            String versao1 = new String();
            Cursor cursor = connect.rawQuery("SELECT * FROM IDs_Sistema LIMIT 1", null);

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                do{
                    versao1 = cursor.getString(cursor.getColumnIndex("Versao_App"));
                }while (cursor.moveToNext());

            }

            return versao1;
        }
    }
}