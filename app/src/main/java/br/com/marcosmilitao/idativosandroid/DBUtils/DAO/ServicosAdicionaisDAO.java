package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;

@Dao
public interface ServicosAdicionaisDAO {
    @Insert
    void Create(ServicosAdicionais servicosAdicionais);

    @Update
    void Update(ServicosAdicionais servicosAdicionais);

    @Delete
    void Delete(ServicosAdicionais servicosAdicionais);

    @Query("SELECT RowVersion FROM ServicosAdicionais ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ServicosAdicionais WHERE IdOriginal = :qryIdOriginal ")
    ServicosAdicionais GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ServicosAdicionais WHERE TarefaItemIdOriginal like :qryIdOriginalTarefa AND FlagAtivo = 1")
    List<ServicosAdicionais> GetByIdOriginalTarefa(int qryIdOriginalTarefa);
}
