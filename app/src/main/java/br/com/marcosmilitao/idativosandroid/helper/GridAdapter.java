package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Idutto07 on 10/05/2017.
 */

public class GridAdapter extends BaseAdapter {

    private String Materiais[];

    private Context context;

    public GridAdapter(Context context, String Materiais[]){

        this.context=context;
        this.Materiais=Materiais;
    }
    @Override
    public int getCount() {
        return Materiais.length;
    }

    @Override
    public Object getItem(int position) {
        return Materiais[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View gridView = view;





        return null;
    }
}
