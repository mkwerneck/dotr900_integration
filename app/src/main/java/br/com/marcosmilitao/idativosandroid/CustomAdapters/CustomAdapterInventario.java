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

import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.Inventario;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 03/09/19.
 */

public class CustomAdapterInventario extends ArrayAdapter<Inventario> {

    private List<Inventario> _inventarios;
    private Context _context;

    public CustomAdapterInventario(Context context, ArrayList<Inventario> inventarios)
    {
        super(context, 0, inventarios);
        _inventarios = inventarios;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_modelo;
        TextView text_tagid;
        TextView text_partnumber;
        //TextView text_posicao;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Inventario inventario = getItem(position);
        CustomAdapterInventario.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterInventario.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_inventario, parent, false);

            viewHolder.text_modelo = (TextView) convertView.findViewById(R.id.text_modelo);
            viewHolder.text_tagid = (TextView) convertView.findViewById(R.id.text_tagid);
            viewHolder.text_partnumber = (TextView) convertView.findViewById(R.id.text_partnumber);
            //viewHolder.text_posicao = (TextView) convertView.findViewById(R.id.text_posicao);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterInventario.ViewHolder) convertView.getTag();
        }

        viewHolder.text_modelo.setText(inventario.getModelo());
        viewHolder.text_tagid.setText(inventario.getTagid());
        viewHolder.text_partnumber.setText(inventario.getPartNumber());
        //viewHolder.text_posicao.setText(inventario.getPosicao());

        //Cor do background altera quando selecionado
        if (inventario.getFound())
        {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

}

