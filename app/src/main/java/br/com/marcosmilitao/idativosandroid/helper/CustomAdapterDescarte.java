package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 09/07/2018.
 */

public class CustomAdapterDescarte extends ArrayAdapter<Descarte> {
    private ArrayList<Descarte> lista;
    private Context context;
    private Listener listener;

    public static interface Listener {

        public void onClick(int position);

    }

    public CustomAdapterDescarte(Context context, ArrayList<Descarte> lista){
        super(context, 0, lista);
        this.lista = lista;
        this.context = context;
    }

    private static class ViewHolder{
        TextView title;
        TextView subtitle;
        Button button;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        final Descarte descarte = getItem(pos);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new CustomAdapterDescarte.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_custom_descarte, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_lv_custom_descarte);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle_lv_custom_descarte);
            viewHolder.button = (Button) convertView.findViewById(R.id.btn_lv_custom_descarte);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterDescarte.ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(descarte.getTitle());
        viewHolder.subtitle.setText(descarte.getSubtitle());
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
