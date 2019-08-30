package br.com.marcosmilitao.idativosandroid;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.facebook.stetho.Stetho;
import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;

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
