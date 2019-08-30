package br.com.marcosmilitao.idativosandroid;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Created by marcoswerneck on 06/01/2018.
 */

//Usado para inicializar o Stetho apenas quando app é usado em modo Debug
public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
    }
}
