package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;

@Dao
public interface UPMOBListaTarefasDAO {
    @Insert
    void Create(UPMOBListaTarefas upmobListaTarefas);

    @Update
    void Update(UPMOBListaTarefas upmobListaTarefas);

    @Delete
    void Delete(UPMOBListaTarefas upmobListaTarefas);

    @Query("SELECT * FROM UPMOBListaTarefas")
    List<UPMOBListaTarefas> GetAllRecords();
}
