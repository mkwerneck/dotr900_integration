package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM UPMOBListaServicosListaTarefas WHERE DataHoraEvento like :qryDataHoraEvento")
    List<UPMOBListaServicosListaTarefas> GetAllRecords(String qryDataHoraEvento);
}
