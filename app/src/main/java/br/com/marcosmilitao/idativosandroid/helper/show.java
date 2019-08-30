package br.com.marcosmilitao.idativosandroid.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by Idutto07 on 30/11/2016.
 */

public class show {

    public void Message(String title, String message, Context context)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
