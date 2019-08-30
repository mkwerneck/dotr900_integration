package br.com.marcosmilitao.idativosandroid.DAO;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.marcosmilitao.idativosandroid.ViewModel.UPMOBUsuario;

/**
 * Created by marcoswerneck on 12/11/2018.
 */

public class UPMOBUsuarioDAO {
    private SQLiteDatabase _connect;

    public UPMOBUsuarioDAO(SQLiteDatabase connect)
    {
        _connect = connect;
    }

    public void Create(UPMOBUsuario upmobUsuario)
    {
        try
        {
            _connect.execSQL("INSERT INTO UPMOBUsuariosSets VALUES('" + upmobUsuario.IdOriginal + "'" +
                    ", '" + upmobUsuario.Permissao + "'" +
                    ", '" + upmobUsuario.Usuario + "'" +
                    ", '" + upmobUsuario.Email + "'" +
                    ", '" + upmobUsuario.NomeCompleto + "'" +
                    ", '" + upmobUsuario.TAGID + "'" +
                    ", '" + upmobUsuario.Grupo + "'" +
                    ", '" + upmobUsuario.DescricaoErro + "'" +
                    ", '" + upmobUsuario.FlagErro + "'" +
                    ", '" + upmobUsuario.FlagAtualizar + "'" +
                    ", '" + upmobUsuario.FlagProcess + "');");
        } catch (SQLException e) {
        }
        catch (Exception e) {
        }

    }
}
