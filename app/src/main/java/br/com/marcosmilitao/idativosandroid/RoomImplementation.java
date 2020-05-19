package br.com.marcosmilitao.idativosandroid;

import android.app.Application;

import com.facebook.stetho.Stetho;

import androidx.room.Room;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Migrations;

public class RoomImplementation extends Application {

    private static RoomImplementation mInstance;
    private ApplicationDB dbInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //Iniciar o Stetho apenas em modo DEBUG
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }

        mInstance = this;
        dbInstance = Room.databaseBuilder(getApplicationContext(), ApplicationDB.class, "ApplicationDB")
                .addMigrations(Migrations.MIGRATION_1_2)
                .addMigrations(Migrations.MIGRATION_2_3)
                .addMigrations(Migrations.MIGRATION_3_4)
                .build();
    }

    public static RoomImplementation getmInstance()
    {
        return mInstance;
    }

    public ApplicationDB getDbInstance()
    {
        return dbInstance;
    }

}
