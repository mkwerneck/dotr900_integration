package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.POJO.Cadastro_Tags;
import br.com.marcosmilitao.idativosandroid.R;

public class CustomAdapterCadastrarTags extends ArrayAdapter<Cadastro_Tags> {

    private ArrayList<Cadastro_Tags> _tags;
    private Context _context;

    public CustomAdapterCadastrarTags(Context context, ArrayList<Cadastro_Tags> tags)
    {
        super(context, 0, tags);
        _tags = tags;
        _context = context;
    }

    private static class ViewHolder{
        TextView title;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final Cadastro_Tags item = getItem(position);
        CustomAdapterCadastrarTags.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterCadastrarTags.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_simple_list_item_1, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.custom_text1);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (CustomAdapterCadastrarTags.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item.getTagid());

        return convertView;
    }
}
