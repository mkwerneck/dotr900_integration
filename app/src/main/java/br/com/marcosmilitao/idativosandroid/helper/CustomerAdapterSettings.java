package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Marcos Paulo on 05/10/2017.
 */

public class CustomerAdapterSettings extends ArrayAdapter<Settings>{

    public CustomerAdapterSettings(Context context, ArrayList<Settings> line){
        super (context, 0, line);
    }

    private static class ViewHolder{
        TextView title;
        TextView subtitle;
        Switch checkbox;

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        final Settings settings = getItem(pos);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_custom_stgs2, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_custom_lv2);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle_custom_lv2);
            viewHolder.checkbox = (Switch) convertView.findViewById(R.id.ckb_settings_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(settings.getTitle());
        viewHolder.subtitle.setText(settings.getSubtitle());

        if (!settings.getHasCheckbox()){
            viewHolder.checkbox.setVisibility(View.GONE);
        } else {
            viewHolder.checkbox.setVisibility(View.VISIBLE);

            if (settings.getChecked()){
                viewHolder.checkbox.setChecked(true);
            } else {
                viewHolder.checkbox.setChecked(false);
            }

        }

        if (!settings.getIsClickable()){
            convertView.setEnabled(false);
            convertView.setOnClickListener(null);
        }

        return convertView;

    }

}
