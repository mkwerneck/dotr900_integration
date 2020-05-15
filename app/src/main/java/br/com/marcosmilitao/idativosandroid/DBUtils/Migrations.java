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
}
