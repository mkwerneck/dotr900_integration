package br.com.marcosmilitao.idativosandroid.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.POJO.NovoProcesso_Servicos;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 23/08/19.
 */

public class CustomAdapterServicosAdicionais extends ArrayAdapter<NovoProcesso_Servicos> {

    private ArrayList<NovoProcesso_Servicos> lista;
    private Context context;
    private CustomAdapterResultados.Listener listener;

    public CustomAdapterServicosAdicionais(Context context, ArrayList<NovoProcesso_Servicos> lista){
        super(context, R.layout.list_view_custom_choicelist, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        return convertView;
    }

}
