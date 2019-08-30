package br.com.marcosmilitao.idativosandroid.CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.marcosmilitao.idativosandroid.POJO.CadastrarTagid;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by marcoswerneck on 15/08/19.
 */

public class CustomAdapterCadastrarTagid extends ArrayAdapter<CadastrarTagid> {

    private ArrayList<CadastrarTagid> _tagids;
    private Context _context;

    public CustomAdapterCadastrarTagid(Context context, ArrayList<CadastrarTagid> tagids)
    {
        super(context, 0, tagids);
        _tagids = tagids;
        _context = context;
    }

    private static class ViewHolder{
        TextView text_tagid;
        TextView text_status;
        TextView text_mensagem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final CadastrarTagid tagid = getItem(position);
        CustomAdapterCadastrarTagid.ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new CustomAdapterCadastrarTagid.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item_1, parent, false);
            viewHolder.text_tagid = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.text_status = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.text_mensagem = (TextView) convertView.findViewById(R.id.text3);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomAdapterCadastrarTagid.ViewHolder) convertView.getTag();
        }

        viewHolder.text_tagid.setText(tagid.getTagid());

        //Label e mensagem mudam de acordo com a propriedade isCadastrado
        if (!tagid.getIsCadastrado())
        {
            viewHolder.text_status.setText("Não Cadastrado");
            viewHolder.text_status.setBackgroundColor(_context.getResources().getColor(R.color.tagnaocadastrado));
            viewHolder.text_status.setTextColor(_context.getResources().getColor(R.color.white));
            viewHolder.text_mensagem.setText("Clique para cadastrar");
        } else {
            viewHolder.text_status.setText("Cadastrado");
            viewHolder.text_status.setBackgroundColor(_context.getResources().getColor(R.color.tagcadastrado));
            viewHolder.text_status.setTextColor(_context.getResources().getColor(R.color.black));
            viewHolder.text_mensagem.setText("Clique para editar");
        }

        //Cor do background altera quando selecionado
        if (tagid.isSelected)
        {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    @Override
    public CadastrarTagid getItem(int position)
    {
        return _tagids.get(position);
    }

    @Override
    public int getCount()
    {
        return _tagids.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void setSelected(int position){}
}
