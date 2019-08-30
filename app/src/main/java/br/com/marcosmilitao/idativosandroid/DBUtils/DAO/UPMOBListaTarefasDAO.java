package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
