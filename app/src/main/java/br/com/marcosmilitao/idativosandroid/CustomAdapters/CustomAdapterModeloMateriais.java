package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.R;

public class CustomAdapterModeloMateriais extends ArrayAdapter<ModeloMateriaisCF> {
    private List<ModeloMateriaisCF> _modelos, _tempItens, _suggestions;
    private ApplicationDB _dbInstance;
    private Context _context;

    public CustomAdapterModeloMateriais(Context context, ArrayList<ModeloMateriaisCF> modelos, ApplicationDB dbInstance)
    {
        super(context, 0, modelos);
        _modelos = modelos;
        _context = context;
        _dbInstance = dbInstance;

        _tempItens = new ArrayList<ModeloMateriaisCF>(_modelos);
        _suggestions = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView text_modelo;
        TextView text_numproduto;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ModeloMateriaisCF item = getItem(position);
        CustomAdapterModeloMateriais.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterModeloMateriais.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_custom_item2, parent, false);
            viewHolder.text_modelo = (TextView) convertView.findViewById(R.id.text_titulo);
            viewHolder.text_numproduto = (TextView) convertView.findViewById(R.id.text_subtitulo);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (CustomAdapterModeloMateriais.ViewHolder) convertView.getTag();
        }

        viewHolder.text_modelo.setText(item.getModelo());
        viewHolder.text_numproduto.setText(item.getNumProduto());

        return convertView;
    }

    @Override
    public  ModeloMateriaisCF getItem(int position)
    {
        return _modelos.get(position);
    }

    @Override
    public int getCount()
    {
        return _modelos.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Filter getFilter() {
        return modelosFilter;
    }

    private Filter modelosFilter = new Filter()
    {
        @Override
        public CharSequence convertResultToString(Object resultValue)
        {
            ModeloMateriaisCF modeloMateriaisCF = (ModeloMateriaisCF) resultValue;
            return modeloMateriaisCF.getModelo();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                _suggestions.clear();

                String filter = charSequence.toString().toLowerCase() + "%";

                _suggestions = _dbInstance.modeloMateriaisDAO().GetFilterModelosCustomAdapter(filter);

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
            ArrayList<ModeloMateriaisCF> tempValues = (ArrayList<ModeloMateriaisCF>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (ModeloMateriaisCF modeloMateriaisCFObj : tempValues) {
                    add(modeloMateriaisCFObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
