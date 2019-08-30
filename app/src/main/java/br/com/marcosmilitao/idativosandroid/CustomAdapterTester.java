package br.com.marcosmilitao.idativosandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.helper.CustomAdapterResultados;
import br.com.marcosmilitao.idativosandroid.helper.ListaResultados;

/**
 * Created by marcoswerneck on 24/07/2018.
 */

public class CustomAdapterTester extends ArrayAdapter<String> {

    private Context mcontext;
    private ArrayList<String> mlist;

    public CustomAdapterTester(Context context, ArrayList<String> list) {
        super (context, 0, list);
        mcontext = context;
        mlist = list;
    }

    private static class ViewHolder{
        private TextView title;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        final String item = getItem(pos);
        ViewHolder viewHolder = null;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item);
        return convertView;
    }
}
