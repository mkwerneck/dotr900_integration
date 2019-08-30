package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 20/07/2017.
 */

public class Query_UPWEBEquipment_Type {
    private SQLiteDatabase connect;
    public Query_UPWEBEquipment_Type(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBEquipment_Type(Context context){
        ArrayAdapter<String> equipment_Type1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("UPWEBEquipment_Type", null, null, null, null, null, "Part_Number");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String part_Number1 = cursor.getString(cursor.getColumnIndex("Part_Number"));
                equipment_Type1.add(part_Number1);
            }while (cursor.moveToNext());

        }

        return equipment_Type1;
    }

    public Cursor UPWEBEquipment_TypePNQuery(String part_Number1){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBEquipment_Type WHERE Part_Number='"+ part_Number1 +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPWEBEquipment_TypeIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBEquipment_Type WHERE Id_Original ='"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
