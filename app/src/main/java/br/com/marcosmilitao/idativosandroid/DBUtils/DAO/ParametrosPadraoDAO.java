package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT SetorProprietarioId FROM ParametrosPadrao LIMIT 1")
    int GetBaseId();

    /*@Query("SELECT CodAlmoxarifado FROM ParametrosPadrao LIMIT 1")
    String GetCodigoAlmoxarifado();*/

    /*@Query("SELECT SetorProprietario FROM PARAMETROSPADRAO LIMIT 1")
    String GetProprietarioPadrao();*/
}
