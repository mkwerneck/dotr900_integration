package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 26/12/2016.
 */

public class Query_UPWEBCadastrosEquipmentTable {
    private SQLiteDatabase connect;

    public Query_UPWEBCadastrosEquipmentTable(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBCadastrosEquipmentTableQuery(Context context){
        ArrayAdapter<String> cadastro_equipment = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBCadastrosEquipmentTable", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String trace_number = cursor.getString(cursor.getColumnIndex("Trace_Number"));
                cadastro_equipment.add(trace_number);
            }while (cursor.moveToNext());

        }

        return cadastro_equipment;
    }

    public int Categoria_EquipamentoItem(Context context, String trace_number) {
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBCadastrosEquipmentTable WHERE Trace_Number = '" + trace_number + "'", null);

        int categoria = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            categoria = cursor.getInt(cursor.getColumnIndex("Categoria_Equipamentos_FK"));
        }
        return categoria;
    }
}
