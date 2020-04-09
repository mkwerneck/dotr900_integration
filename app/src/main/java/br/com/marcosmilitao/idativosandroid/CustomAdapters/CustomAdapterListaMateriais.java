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

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.POJO.ListaMateriaisExecucaoTarefas;
import br.com.marcosmilitao.idativosandroid.POJO.ListaServicosExecucaoTarefas;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 03/10/19.
 */

public class CustomAdapterListaMateriais extends ArrayAdapter<ListaMateriaisExecucaoTarefas> {
    private List<ListaMateriaisExecucaoTarefas> _listaMateriais;
    private Context _context;

    public CustomAdapterListaMateriais(Context context, ArrayList<ListaMateriaisExecucaoTarefas> listaMateriais)
    {
        super(context, 0, listaMateriais);
        _listaMateriais = listaMateriais;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_modelo;
        TextView text_partnumber;
        TextView text_tagid;
        TextView text_numserie;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ListaMateriaisExecucaoTarefas material = getItem(position);
        CustomAdapterListaMateriais.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterListaMateriais.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_materiais, parent, false);

            viewHolder.text_modelo = (TextView) convertView.findViewById(R.id.text_modelo);
            viewHolder.text_partnumber = (TextView) convertView.findViewById(R.id.text_partnumber);
            viewHolder.text_tagid = (TextView) convertView.findViewById(R.id.text_tagid);
            viewHolder.text_numserie = (TextView) convertView.findViewById(R.id.text_numserie);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterListaMateriais.ViewHolder) convertView.getTag();
        }

        viewHolder.text_modelo.setText(material.getModelo());
        viewHolder.text_partnumber.setText(material.getPartNumber());
        viewHolder.text_tagid.setText(material.getTagid());
        viewHolder.text_numserie.setText(material.getNumSerie());

        //Cor do background altera quando selecionado
        if (material.isFound())
        {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
