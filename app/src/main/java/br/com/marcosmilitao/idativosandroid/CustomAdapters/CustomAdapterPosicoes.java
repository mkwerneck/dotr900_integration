package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 15/08/19.
 */

public class CustomAdapterPosicoes extends ArrayAdapter<PosicaoCF> {
    private List<PosicaoCF> _posicoes, _tempItens, _suggestions;
    private Context _context;
    private ApplicationDB _dbInstance;

    public CustomAdapterPosicoes(Context context, ArrayList<PosicaoCF> posicoes, ApplicationDB dbInstance)
    {
        super(context, 0, posicoes);
        _posicoes = posicoes;
        _context = context;
        _dbInstance = dbInstance;

        _tempItens = new ArrayList<PosicaoCF>(_posicoes);
        _suggestions = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView text_codigo;
        TextView text_descricao;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final PosicaoCF item = getItem(position);
        CustomAdapterPosicoes.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterPosicoes.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_custom_item2, parent, false);
            viewHolder.text_codigo = (TextView) convertView.findViewById(R.id.text_titulo);
            viewHolder.text_descricao = (TextView) convertView.findViewById(R.id.text_subtitulo);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (CustomAdapterPosicoes.ViewHolder) convertView.getTag();
        }

        viewHolder.text_descricao.setText(item.getDescPosicao());
        viewHolder.text_codigo.setText(item.getCodPosicao());

        return convertView;
    }

    @Override
    public  PosicaoCF getItem(int position)
    {
        return _posicoes.get(position);
    }

    @Override
    public int getCount()
    {
        return _posicoes.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Filter getFilter() {
        return posicoesFilter;
    }

    private Filter posicoesFilter = new Filter()
    {
        @Override
        public CharSequence convertResultToString(Object resultValue)
        {
            PosicaoCF posicaoCF = (PosicaoCF) resultValue;
            return posicaoCF.getCodPosicao();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                _suggestions.clear();

                String filter = charSequence.toString().toLowerCase() + "%";

                _suggestions = _dbInstance.posicoesDAO().GetFilterPosicoesCustomAdapter(filter);

                FilterResults filterResults = new FilterResults();
                filterResults.values = _suggestions;
                filterResults.count = _suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<PosicaoCF> tempValues = (ArrayList<PosicaoCF>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (PosicaoCF posicaoCFObj : tempValues) {
                    add(posicaoCFObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
