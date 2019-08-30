package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 20/08/19.
 */

public class CustomAdapterSpinnerFuncoes extends ArrayAdapter<FuncoesCU> {
    private List<FuncoesCU> _funcoes;
    private Context _context;

    public CustomAdapterSpinnerFuncoes(Context context, ArrayList<FuncoesCU> funcoes)
    {
        super(context, 0, funcoes);
        _funcoes = funcoes;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_role;
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
        final FuncoesCU item = _funcoes.get(position);
        CustomAdapterSpinnerFuncoes.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterSpinnerFuncoes.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            viewHolder.text_role = (TextView) convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterSpinnerFuncoes.ViewHolder) convertView.getTag();
        }

        viewHolder.text_role.setText(item.getRole());

        return convertView;
    }
}
