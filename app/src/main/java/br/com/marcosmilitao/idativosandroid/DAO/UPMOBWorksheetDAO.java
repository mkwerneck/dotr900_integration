package br.com.marcosmilitao.idativosandroid.DAO;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBWorksheet;


/**
 * Created by marcoswerneck on 09/11/2018.
 */

public class UPMOBWorksheetDAO {
    private SQLiteDatabase _connect;

    public UPMOBWorksheetDAO(SQLiteDatabase connect)
    {
        _connect = connect;
    }

    public void Create(UPMOBWorksheet upmobWorksheet)
    {
        try {
            _connect.execSQL("INSERT INTO UPMOBWorksheet VALUES('" + upmobWorksheet.IdOriginal + "'" +
                    ", '" + upmobWorksheet.TraceNumber + "'" +
                    ", '" + upmobWorksheet.NumProduto + "'" +
                    ", '" + upmobWorksheet.Patrimonio + "'" +
                    ", '" + upmobWorksheet.NumSerie + "'" +
                    ", '" + upmobWorksheet.Quantidade + "'" +
                    ", '" + upmobWorksheet.Modalidade + "'" +
                    ", '" + upmobWorksheet.DataFabricacao + "'" +
                    ", '" + upmobWorksheet.DataValidade + "'" +
                    ", '" + upmobWorksheet.DataHoraEvento + "'" +
                    ", '" + upmobWorksheet.DadosTecnicos + "'" +
                    ", '" + upmobWorksheet.TAGID + "'" +
                    ", '" + upmobWorksheet.TAGIDPosicao + "'" +
                    ", '" + upmobWorksheet.Categoria + "'" +
                    ", '" + upmobWorksheet.NF + "'" +
                    ", '" + upmobWorksheet.DataEntradaNF + "'" +
                    ", '" + upmobWorksheet.ValorUnitario + "'" +
                    ", '" + upmobWorksheet.DescricaoErro + "'" +
                    ", '" + upmobWorksheet.FlagErro + "'" +
                    ", '" + upmobWorksheet.FlagAtualizar + "'" +
                    ", '" + upmobWorksheet.FlagProcess +"');");
        } catch (SQLException e) {
        }
        catch (Exception e) {
        }
    }
}
