package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.cert.Certificate;
import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 23/07/2018.
 */

public class CustomAdapterResultados extends ArrayAdapter<ListaResultados> {

    private ArrayList<ListaResultados> lista;
    private Context context;
    private Listener listener;

    public CustomAdapterResultados(Context context, ArrayList<ListaResultados> lista){
        super(context, R.layout.list_view_custom_choicelist, lista);
        this.context = context;
        this.lista = lista;
    }

    public static interface Listener {

        public void onCheckedChanged(int position, boolean isChecked, CompoundButton button);

    }

    public void SetListener(Listener listener){
        this.listener = listener;
    }

    private static class ViewHolder{
        private TextView title;
        private CheckBox checkBox;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        final ListaResultados item = getItem(pos);
        ViewHolder viewHolder = null;

        if (convertView == null){
            viewHolder = new CustomAdapterResultados.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_custom_choicelist, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_cl_custom_resultadoos);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cl_custom_resultadoos);

            viewHolder.title.setText(item.getResultado());
            boolean value = item.getIsChecked();
            viewHolder.checkBox.setChecked(value);

            viewHolder.checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    if (listener != null){
                        listener.onCheckedChanged(pos, isChecked, buttonView);
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterResultados.ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
