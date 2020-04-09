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

import br.com.marcosmilitao.idativosandroid.ExecutarProcessosActivity;
import br.com.marcosmilitao.idativosandroid.POJO.Inventario;
import br.com.marcosmilitao.idativosandroid.POJO.ListaServicosExecucaoTarefas;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 03/10/19.
 */

public class CustomAdapterListaServicos extends ArrayAdapter<ListaServicosExecucaoTarefas> {

    private List<ListaServicosExecucaoTarefas> _listaServicos;
    private Context _context;

    public CustomAdapterListaServicos(Context context, ArrayList<ListaServicosExecucaoTarefas> listaServicos)
    {
        super(context, 0, listaServicos);
        _listaServicos = listaServicos;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_servico;
        TextView text_resultado;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ListaServicosExecucaoTarefas servico = getItem(position);
        CustomAdapterListaServicos.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterListaServicos.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_servicos, parent, false);

            viewHolder.text_servico = (TextView) convertView.findViewById(R.id.text_servico);
            viewHolder.text_resultado = (TextView) convertView.findViewById(R.id.text_resultado);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterListaServicos.ViewHolder) convertView.getTag();
        }

        viewHolder.text_servico.setText(servico.getServico());
        viewHolder.text_resultado.setText(servico.getResultado());

        //Cor do background altera quando selecionado
        if (servico.getResultado() != null)
        {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

}
