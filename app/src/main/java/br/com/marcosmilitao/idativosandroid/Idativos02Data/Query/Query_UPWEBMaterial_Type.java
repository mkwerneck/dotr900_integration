package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Idutto07 on 06/01/2017.
 */

public class Query_UPWEBMaterial_Type {
    private SQLiteDatabase connect;

    public Query_UPWEBMaterial_Type(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> UPWEBMaterial_TypeQuery(Context context){
        ArrayAdapter<String> Material_Type1 = new ArrayAdapter<String>(context, R.layout.spinner_item);
        Cursor cursor = connect.query("UPWEBMaterial_Type", null, null, null, null, null, "Modelo");

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String descricao_proprietario = cursor.getString(cursor.getColumnIndex("Modelo"));
                Material_Type1.add(descricao_proprietario);
            }while (cursor.moveToNext());

        }
        return Material_Type1;
    }

    public Cursor UPWEBMaterial_TypeQuery(String modelo_material){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE Modelo = '"+ modelo_material +"'", null);
        return cursor;
    }

    public String UPWEBMaterial_Type_PNQuery(String Part_Number){
        String material_type1 = null;
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE IDOmni = '"+ Part_Number +"'"+ "LIMIT 1",null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                material_type1 = cursor.getString(cursor.getColumnIndex("Modelo"));
            }while (cursor.moveToNext());

        }
        return material_type1;
    }

    public Cursor UPWEBMaterialPNQuery(String Part_Number){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE PartNumber = '"+ Part_Number +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPWEBMaterialIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE IdOriginal = '"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
