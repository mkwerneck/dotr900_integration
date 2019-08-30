package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM ServicosAdicionais WHERE TarefaItemIdOriginal like :qryIdOriginalTarefa")
    List<ServicosAdicionais> GetByIdOriginalTarefa(int qryIdOriginalTarefa);
}
