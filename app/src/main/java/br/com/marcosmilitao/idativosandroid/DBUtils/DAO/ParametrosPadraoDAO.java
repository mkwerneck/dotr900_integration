package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao;

@Dao
public interface ParametrosPadraoDAO {
    @Insert
    void Create(ParametrosPadrao parametrosPadrao);

    @Update
    void Update(ParametrosPadrao parametrosPadrao);

    @Query("SELECT RowVersion FROM ParametrosPadrao ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ParametrosPadrao WHERE IdOriginal = :qryIdOriginal ")
    ParametrosPadrao GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT CodAlmoxarifado FROM ParametrosPadrao LIMIT 1")
    String GetCodigoAlmoxarifado();

    @Query("SELECT SetorProprietario FROM PARAMETROSPADRAO LIMIT 1")
    String GetProprietarioPadrao();
}
