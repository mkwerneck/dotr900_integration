package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.POJO.InvPlanejado;
import br.com.marcosmilitao.idativosandroid.POJO.PosicoesInv;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 04/09/19.
 */

public class CustomAdapterSpinnerInvPlan extends ArrayAdapter<InvPlanejado> {
    private List<InvPlanejado> _inventario;
    private Context _context;

    public CustomAdapterSpinnerInvPlan(Context context, ArrayList<InvPlanejado> inventario)
    {
        super(context, 0, inventario);
        _inventario = inventario;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_descricao;
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
        final InvPlanejado item = _inventario.get(position);
        CustomAdapterSpinnerInvPlan.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterSpinnerInvPlan.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            viewHolder.text_descricao = (TextView) convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterSpinnerInvPlan.ViewHolder) convertView.getTag();
        }

        viewHolder.text_descricao.setText(item.getDescricao());

        return convertView;
    }
}
