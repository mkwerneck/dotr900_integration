package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 21/07/2018.
 */

public class CustomAdapterResultadoOS extends ArrayAdapter<String> {
    private ArrayList<String> lista;
    private Context context;
    private Listener listener;

    public static interface Listener {

        public void onClick(int position);

    }

    public CustomAdapterResultadoOS(Context context, ArrayList<String> lista){
        super (context, 0, lista);
        this.lista = lista;
        this.context = context;
    }

    private static class ViewHolder{
        TextView title;
        Button button;
    }

    public void SetListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        final String item = getItem(pos);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new CustomAdapterResultadoOS.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_resultado_item_os, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_lv_custom_resultadoos);
            viewHolder.button = (Button) convertView.findViewById(R.id.btn_lv_custom_resultadoos);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterResultadoOS.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item);
        viewHolder.button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if (listener != null){
                    listener.onClick(pos);
                }
            }
        });

        return convertView;
    }
}
