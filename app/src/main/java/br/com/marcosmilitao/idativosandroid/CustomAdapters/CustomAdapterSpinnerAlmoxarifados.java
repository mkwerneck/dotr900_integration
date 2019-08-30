package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 23/08/19.
 */

public class CustomAdapterSpinnerAlmoxarifados extends ArrayAdapter<AlmoxarifadosCP> {

    private List<AlmoxarifadosCP> _almoxarifados;
    private Context _context;

    public CustomAdapterSpinnerAlmoxarifados(Context context, ArrayList<AlmoxarifadosCP> almoxarifados)
    {
        super(context, 0, almoxarifados);
        _almoxarifados = almoxarifados;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_codigo;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final AlmoxarifadosCP item = _almoxarifados.get(position);
        CustomAdapterSpinnerAlmoxarifados.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterSpinnerAlmoxarifados.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            viewHolder.text_codigo = (TextView) convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterSpinnerAlmoxarifados.ViewHolder) convertView.getTag();
        }

        viewHolder.text_codigo.setText(item.getCodigo());

        return convertView;
    }

}
