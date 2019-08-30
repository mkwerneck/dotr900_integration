package br.com.marcosmilitao.idativosandroid.Idativos02Data.Query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Created by Vinicius on 07/01/2017.
 */

public class Query_InventarioMaterial {
    private SQLiteDatabase connect;
    public Query_InventarioMaterial(SQLiteDatabase connect){
        this.connect = connect;
    }

    public ArrayAdapter<String> InventarioMaterialQuery(Context context){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.query("InventarioMaterial", null, null, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String material_Type = cursor.getString(cursor.getColumnIndex("Material_Type"));
                material1.add(material_Type);
            }while (cursor.moveToNext());

        }

        return material1;
    }

    public ArrayAdapter<String> InventarioMaterialTAGIDQuery(Context context, String tagid_Material1){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE TAGID_Material = '" + tagid_Material1 + "'" + " LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String Quantidade = cursor.getString(cursor.getColumnIndex("Quantidade"));
                material1.add(Quantidade);

                String material_Type = cursor.getString(cursor.getColumnIndex("Material_Type"));
                material1.add(material_Type);

                String lote_material = cursor.getString(cursor.getColumnIndex("lote_material"));
                material1.add(lote_material);

                String tagid_Material = cursor.getString(cursor.getColumnIndex("TAGID_Material"));
                material1.add(tagid_Material);

                String id_omni = cursor.getString(cursor.getColumnIndex("ID_Omni"));
                material1.add(id_omni);

            }while (cursor.moveToNext());
        }
        return material1;
    }

    public ArrayAdapter<String> InventarioMaterialTAGIDQuery2(Context context, String tagid_Material1){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE TAGID_Material = '" + tagid_Material1 + "'" + " LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String material_Type = cursor.getString(cursor.getColumnIndex("Material_Type"));
                material1.add(material_Type);

            }while (cursor.moveToNext());

        }

        return material1;
    }

    public Cursor InventarioMaterialTAGIDQuery3(String tagid_Material1){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE TAGID_Material = '" + tagid_Material1 + "'" + " LIMIT 1", null);
        return cursor;
    }

    public Cursor InventarioMaterialPosicaoQuery(String posicao1){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE Cod_Posicao = '" + posicao1 + "'", null);
        return cursor;
    }

    public Cursor InventarioMaterialPosicaoOrigQuery(String posicao1){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE Posicao_Original = '" + posicao1 + "'", null);
        return cursor;
    }

    public Cursor InventarioMaterialAlmoxarifadoQuery(String almoxarifado1){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE Nome_Almoxarifado = '" + almoxarifado1 + "'", null);
        return cursor;
    }

    public Cursor InventarioMaterialAlmoxarifadoOrigQuery(String almoxarifado1){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE Nome_Almoxarifado_Original = '" + almoxarifado1 + "'", null);
        return cursor;
    }
    public ArrayAdapter<String> InventarioMaterialPartNumber(Context context, String tagid_Material1, String Part_number){
        ArrayAdapter<String> material1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = connect.rawQuery("SELECT * FROM UPWEBMaterial_Type WHERE Material_Type = '" + tagid_Material1 + "' and Part_Number= '"+ Part_number+ "' LIMIT 1", null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do{
                String Quantidade = cursor.getString(cursor.getColumnIndex("PartNumber"));
                material1.add(Quantidade);

            }while (cursor.moveToNext());

        }

        return material1;
    }

    public Cursor InventarioMaterialIdQuery(String Id_Original){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE Id_Original = '" + Id_Original + "'", null);
        return cursor;
    }

    public Cursor InventarioMaterialID_OmniQuery(String ID_Omni){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial WHERE ID_Omni like '%" + ID_Omni + "%'", null);
        return cursor;
    }

    public Cursor InventarioMaterialID_OmniQueryFilter(){
        Cursor cursor = connect.rawQuery("SELECT * FROM InventarioMaterial", null);
        return cursor;
    }
}
