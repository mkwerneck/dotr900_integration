package br.com.marcosmilitao.idativosandroid.Idativos02Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Script;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Entity.UPMOBListaTarefasEqReadinessTables;


/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Idativos02Data extends SQLiteOpenHelper {
    private SQLiteDatabase connect;
    private static final String DB_NAME = "Idativos02Data";
    private static final int DB_VERSION = 1;

    public Idativos02Data(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CRIANDO UPMOBS
        db.execSQL(Script_Idativos02Data.CreateUPMOBWorksheet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBWorksheetItens(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBHistoricoLocalizacao(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBListaTarefasEqReadinessTables(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBListaMateriaisListaTarefaEqReadnesses(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBLista_Serviços_AdicionaisSet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBUsuarios(0, DB_VERSION));

        //CRIANDO UPWEBS
        db.execSQL(Script_Idativos02Data.CreateUPWEBPosicao(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBMaterial_Type(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBUsuariosSet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBEquipment_Type(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBGrupos(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBTarefasEqReadinessTable(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaTarefasEqReadinessTables(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaMateriaisListaTarefaEqReadnesses(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBLista_Serviços_AdicionaisSet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBWorksheets(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBServicos_AdicionaisSet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBCadastrosEquipmentTable(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBWorksheetItens(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBProprietarios(0, DB_VERSION));

        //OUTRAS
        db.execSQL(Script_Idativos02Data.CreateParametros_Padrao(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBImagemModelo(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreatIDs_Sistema(0, DB_VERSION));

        /*db.execSQL(Script_Idativos02Data.CreateUPWEBListaMateriaisTarefaEqReadnesses());
        db.execSQL(Script_Idativos02Data.CreateUPMOBPosicao());
        db.execSQL(Script_Idativos02Data.CreateInventarioEquipamento(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateInventarioMaterial());
        db.execSQL(Script_Idativos02Data.CreateInventario_Planejado());
        db.execSQL(Script_Idativos02Data.CreateRegistros_Inventario_Planejado());
        db.execSQL(Script_Idativos02Data.CreateUPMOBCert_Class());
        db.execSQL(Script_Idativos02Data.CreateUPMOBContract_Name());
        db.execSQL(Script_Idativos02Data.CreateUPMOBDESAT_TAGID());
        db.execSQL(Script_Idativos02Data.CreateUPMOBEquipment_Type());
        db.execSQL(Script_Idativos02Data.CreateUPMOBLogError());
        db.execSQL(Script_Idativos02Data.CreateUPMOBQTDInventario());
        db.execSQL(Script_Idativos02Data.CreateUPProcessTime());
        db.execSQL(Script_Idativos02Data.CreateUPWEBLoginColetor());
        db.execSQL(Script_Idativos02Data.CreateUPWEBPurchase_Order());
        db.execSQL(Script_Idativos02Data.CreateUPWEBRequisicao());
        db.execSQL(Script_Idativos02Data.CreateUPWEBAlmoxarifado());
        db.execSQL(Script_Idativos02Data.CreateUPWEBColetores_Dados());
        db.execSQL(Script_Idativos02Data.CreateUPWEBAcoesColetores_Dados());

        db.execSQL(Script_Idativos02Data.CreateUPWEBInventarioPlanejado(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaMateriaisInvPlanejado(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBProcessoDescarte(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPMOBListaResultadosServicosSet(0, DB_VERSION));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaResultadosServicosSet(0, DB_VERSION));*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*db.execSQL(Script_Idativos02Data.CreateUPWEBListaTarefasEqReadinessTables(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBListaTarefasEqReadinessTables(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBWorksheetItens(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBWorksheetItens(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBInventarioPlanejado(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaMateriaisInvPlanejado(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBWorksheet(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBWorksheets(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateInventarioEquipamento(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.UpdateUPWEBWorksheets(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.UpdateUPMOBWorksheets(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBGrupos(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBUsuarios(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBProcessoDescarte(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBServicos_AdicionaisSet(oldVersion , newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPMOBListaResultadosServicosSet(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBListaResultadosServicosSet(oldVersion, newVersion));
        db.execSQL(Script_Idativos02Data.CreateUPWEBLista_Serviços_AdicionaisSet(oldVersion, newVersion));*/
    }
}
