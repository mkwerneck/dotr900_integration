package br.com.marcosmilitao.idativosandroid.DBUtils;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by marcoswerneck on 12/11/19.
 */

public class Migrations {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Usuarios ADD COLUMN Matricula TEXT");
        }
    };

    //Adicionado em 15/05/2020
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CadastroMateriais ADD COLUMN Status TEXT");
            database.execSQL("ALTER TABLE UPMOBCadastroMateriais ADD COLUMN Status TEXT");
        }
    };

    //Adicionado em 19/05/2020
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE InventarioPlanejado ADD COLUMN EmUso INTEGER DEFAULT 0 NOT NULL");
        }
    };

    //Adicionado em 19/05/2020
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE CadastroMateriais_new (Id INTEGER PRIMARY KEY NOT NULL, IdOriginal INTEGER NOT NULL, RowVersion TEXT, NumSerie TEXT, Patrimonio TEXT, Quantidade INTEGER NOT NULL, DataCadastro TEXT, DataValidadeCalibracao TEXT, DataValidadeInspecao TEXT, ValorUnitario REAL NOT NULL, DadosTecnicos TEXT, NotaFiscal TEXT, DataEntradaNotaFiscal TEXT, Categoria TEXT, ModeloMateriaisItemIdOriginal INTEGER NOT NULL, PosicaoOriginalItemIdoriginal INTEGER NOT NULL, TAGID TEXT, EmUso INTEGER NOT NULL, Status TEXT)");
            database.execSQL("INSERT INTO CadastroMateriais_new (Id, IdOriginal, RowVersion, NumSerie, Patrimonio, Quantidade, DataCadastro, DataValidadeCalibracao, DataValidadeInspecao, ValorUnitario, DadosTecnicos, NotaFiscal, DataEntradaNotaFiscal, Categoria, ModeloMateriaisItemIdOriginal, PosicaoOriginalItemIdoriginal, TAGID, EmUso, Status) SELECT Id, IdOriginal, RowVersion, NumSerie, Patrimonio, Quantidade, DataCadastro, DataFabricacao, DataValidade, ValorUnitario, DadosTecnicos, NotaFiscal, DataEntradaNotaFiscal, Categoria, ModeloMateriaisItemIdOriginal, PosicaoOriginalItemIdoriginal, TAGID, EmUso, Status FROM CadastroMateriais");
            database.execSQL("DROP TABLE CadastroMateriais");
            database.execSQL("ALTER TABLE CadastroMateriais_new RENAME TO CadastroMateriais");

            database.execSQL("CREATE TABLE UPMOBCadastroMateriais_new (Id INTEGER PRIMARY KEY NOT NULL, IdOriginal INTEGER NOT NULL, Patrimonio TEXT, NumSerie TEXT, Quantidade INTEGER NOT NULL, DataValidadeCalibracao TEXT, DataValidadeInspecao TEXT, DataHoraEvento TEXT, DadosTecnicos TEXT, TAGID TEXT, PosicaoOriginalItemId INTEGER NOT NULL, NotaFiscal TEXT, DataEntradaNotaFiscal TEXT, ValorUnitario REAL NOT NULL, CodColetor TEXT, DescricaoErro TEXT, FlagErro INTEGER, FlagAtualizar INTEGER, FlagProcess INTEGER, ModeloMateriaisItemId INTEGER NOT NULL, Status TEXT)");
            database.execSQL("INSERT INTO UPMOBCadastroMateriais_new (Id, IdOriginal, Patrimonio, NumSerie, Quantidade, DataValidadeCalibracao, DataValidadeInspecao, DataHoraEvento, DadosTecnicos, TAGID, PosicaoOriginalItemId, NotaFiscal, DataEntradaNotaFiscal, ValorUnitario, CodColetor, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess, ModeloMateriaisItemId, Status) SELECT Id, IdOriginal, Patrimonio, NumSerie, Quantidade, DataFabricacao, DataValidade, DataHoraEvento, DadosTecnicos, TAGID, PosicaoOriginalItemId, NotaFiscal, DataEntradaNotaFiscal, ValorUnitario, CodColetor, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess, ModeloMateriaisItemId, Status FROM UPMOBCadastroMateriais");
            database.execSQL("DROP TABLE UPMOBCadastroMateriais");
            database.execSQL("ALTER TABLE UPMOBCadastroMateriais_new RENAME TO UPMOBCadastroMateriais");
        }
    };
}
