package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 23/07/2018.
 */

public class CustomAdapterTagidResultado extends ArrayAdapter<TagidResultados> {

    private ArrayList<TagidResultados> lista;
    private Context context;

    public CustomAdapterTagidResultado(Context context, ArrayList<TagidResultados> lista){
        super(context, 0, lista);
        this.lista = lista;
        this.context = context;
    }

    private static class ViewHolder{
        TextView title;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final TagidResultados item = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new CustomAdapterTagidResultado.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterTagidResultado.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item.getLabel());

        if (item.isTaged == true){
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

}
