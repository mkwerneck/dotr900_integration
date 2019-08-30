package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 06/12/17.
 */

public class CustomWorksheetItensAdapter extends ArrayAdapter<WorksheetItemKit> {
    public CustomWorksheetItensAdapter(Context context, ArrayList<WorksheetItemKit> worksheetItem){
        super(context, 0, worksheetItem);
    }

    private static class ViewHolder {
        TextView title;
        TextView subtitle;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        WorksheetItemKit worksheetitem = getItem(pos);
        CustomWorksheetItensAdapter.ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new CustomWorksheetItensAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_custom_settings, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_custom_lv);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle_custom_lv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomWorksheetItensAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(worksheetitem.getModeloMaterial());
        viewHolder.subtitle.setText(worksheetitem.toString());

        return convertView;
    }
}

