package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.POJO.AtivosProc;
import br.com.marcosmilitao.idativosandroid.POJO.ProcessosProc;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 25/09/19.
 */

public class CustomAdapterAtivos extends ArrayAdapter<AtivosProc> {
    private List<AtivosProc> _ativos;
    private Context _context;

    public CustomAdapterAtivos(Context context, ArrayList<AtivosProc> ativos)
    {
        super(context, 0, ativos);
        _ativos = ativos;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_ativo;
        TextView text_modelo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final AtivosProc ativo = getItem(position);
        CustomAdapterAtivos.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterAtivos.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_ativos, parent, false);

            viewHolder.text_modelo = (TextView) convertView.findViewById(R.id.text_modelo);
            viewHolder.text_ativo = (TextView) convertView.findViewById(R.id.text_ativo);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterAtivos.ViewHolder) convertView.getTag();
        }

        viewHolder.text_modelo.setText(ativo.getModelo());
        viewHolder.text_ativo.setText(ativo.getAtivo());

        return convertView;
    }
}
