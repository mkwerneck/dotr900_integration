package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;

@Dao
public interface UPMOBListaServicosListaTarefasDAO {
    @Insert
    void Create(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas);

    @Update
    void Update(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas);

    @Delete
    void Delete(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas);

    @Query("SELECT * FROM UPMOBListaServicosListaTarefas")
    List<UPMOBListaServicosListaTarefas> GetAllRecords();
}
