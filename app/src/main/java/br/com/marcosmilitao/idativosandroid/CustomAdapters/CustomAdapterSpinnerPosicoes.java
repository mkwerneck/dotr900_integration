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
import br.com.marcosmilitao.idativosandroid.POJO.PosicoesInv;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 02/09/19.
 */

public class CustomAdapterSpinnerPosicoes extends ArrayAdapter<PosicoesInv> {
    private List<PosicoesInv> _posicoes;
    private Context _context;

    public CustomAdapterSpinnerPosicoes(Context context, ArrayList<PosicoesInv> posicoes)
    {
        super(context, 0, posicoes);
        _posicoes = posicoes;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_posicao;
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
        final PosicoesInv item = _posicoes.get(position);
        CustomAdapterSpinnerPosicoes.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterSpinnerPosicoes.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            viewHolder.text_posicao = (TextView) convertView.findViewById(R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterSpinnerPosicoes.ViewHolder) convertView.getTag();
        }

        viewHolder.text_posicao.setText(item.getDescricao());

        return convertView;
    }
}
