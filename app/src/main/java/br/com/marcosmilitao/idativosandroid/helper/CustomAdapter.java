package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marcos Paulo on 18/08/2017.
 */

public class CustomAdapter extends ArrayAdapter<TagIDInventario> {
    public CustomAdapter(Context context, ArrayList<TagIDInventario> tags){
        super (context, 0, tags);
    }

    private static class ViewHolder {
        TextView tag;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        TagIDInventario tagInventario = getItem(pos);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.tag = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tag.setText(tagInventario.tagid);

        if (tagInventario.isTaged == true){
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
