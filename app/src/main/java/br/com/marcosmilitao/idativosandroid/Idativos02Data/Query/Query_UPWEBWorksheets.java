package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Query_UPWEBWorksheets {
    private SQLiteDatabase connect;

    public Query_UPWEBWorksheets(SQLiteDatabase connect){
        this.connect = connect;
    }

    public Cursor UPWEBWorksheetTAGIDQuery(String tagid){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheet cm " +
                "INNER JOIN UPWEBMaterial_Type mm ON (cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal) " +
                "INNER JOIN  UPWEBPosicao po ON (cm.PosicaoOriginalItemIdoriginal = po.IdOriginal)" +
                "WHERE cm.TAGID = '"+ tagid +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public ArrayAdapter<String> UPWEBWorksheetTAGIDQuery2(Context context, String tagid){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheet cm " +
                "INNER JOIN UPWEBMaterial_Type mm ON (cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal) " +
                "WHERE cm.TAGID = '" + tagid + "'" + " LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String material_Type = cursor.getString(cursor.getColumnIndex("Modelo"));
                material1.add(material_Type);

            }while (cursor.moveToNext());

        }

        return material1;
    }

    public Cursor UPWEBWorksheetTAGIDQuery3(String tagid){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheet " +
                "WHERE TAGID = '"+ tagid +"'"+ "LIMIT 1",null);
        return cursor;
    }

    public Cursor UPWEBWorksheetIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBWorksheet WHERE IdOriginal = '"+ Id_Original +"'"+ "LIMIT 1",null);
        return cursor;
    }
}
