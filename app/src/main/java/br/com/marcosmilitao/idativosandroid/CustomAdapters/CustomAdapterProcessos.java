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

import br.com.marcosmilitao.idativosandroid.POJO.Inventario;
import br.com.marcosmilitao.idativosandroid.POJO.ProcessosProc;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 25/09/19.
 */

public class CustomAdapterProcessos extends ArrayAdapter<ProcessosProc> {
    private List<ProcessosProc> _processos;
    private Context _context;

    public CustomAdapterProcessos(Context context, ArrayList<ProcessosProc> processos)
    {
        super(context, 0, processos);
        _processos = processos;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_tarefa;
        TextView text_ativo;
        TextView text_codprocesso;
        TextView text_status;
        TextView text_datainicio;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ProcessosProc processo = getItem(position);
        CustomAdapterProcessos.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterProcessos.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_processos, parent, false);

            viewHolder.text_tarefa = (TextView) convertView.findViewById(R.id.text_tarefa);
            viewHolder.text_ativo = (TextView) convertView.findViewById(R.id.text_ativo);
            viewHolder.text_codprocesso = (TextView) convertView.findViewById(R.id.text_codprocesso);
            viewHolder.text_status = (TextView) convertView.findViewById(R.id.text_status);
            viewHolder.text_datainicio = (TextView) convertView.findViewById(R.id.text_datainicio);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterProcessos.ViewHolder) convertView.getTag();
        }

        viewHolder.text_tarefa.setText(processo.getTarefa());
        viewHolder.text_ativo.setText(processo.getAtivo());
        viewHolder.text_codprocesso.setText(processo.getCodProcesso());
        viewHolder.text_status.setText(processo.getStatus());
        viewHolder.text_datainicio.setText(processo.getDataInicio());

        //Cor da tarefa altera baseada no tipo dela
        if (processo.isEntradaAlmoxarifado())
        {
            viewHolder.text_tarefa.setTextColor(Color.RED);
        } else {
            viewHolder.text_tarefa.setTextColor(_context.getResources().getColor(R.color.green));
        }

        return convertView;
    }
}
