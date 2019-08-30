package br.com.marcosmilitao.idativosandroid.DAO;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBCadastroItens;

/**
 * Created by marcoswerneck on 12/11/2018.
 */

public class UPMOBCadastroItensDAO {
    private SQLiteDatabase _connect;

    public UPMOBCadastroItensDAO(SQLiteDatabase connect)
    {
        _connect = connect;
    }

    public void Create(UPMOBCadastroItens upmobCadastroItens)
    {
        try {
            _connect.execSQL("INSERT INTO UPMOBWorksheetItens VALUES('" + upmobCadastroItens.IdOriginal + "'" +
                    ", '" + upmobCadastroItens.Patrimonio + "'" +
                    ", '" + upmobCadastroItens.NumSerie + "'" +
                    ", '" + upmobCadastroItens.NumProduto + "'" +
                    ", '" + upmobCadastroItens.Quantidade + "'" +
                    ", '" + upmobCadastroItens.DataHoraEvento + "'" +
                    ", '" + upmobCadastroItens.DataValidade + "'" +
                    ", '" + upmobCadastroItens.DataFabricacao + "'" +
                    ", '" + upmobCadastroItens.TAGIDContentor + "'" +
                    ", '" + upmobCadastroItens.DescricaoErro + "'" +
                    ", '" + upmobCadastroItens.FlagErro + "'" +
                    ", '" + upmobCadastroItens.FlagAtualizar + "'" +
                    ", '" + upmobCadastroItens.FlagProcess + "');");
        } catch (SQLException e) {
        }
        catch (Exception e) {
        }
    }
}
