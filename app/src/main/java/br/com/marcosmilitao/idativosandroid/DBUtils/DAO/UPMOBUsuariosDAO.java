package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;

@Dao
public interface UPMOBUsuariosDAO {
    @Insert
    void Create(UPMOBUsuarios upmobUsuarios);

    @Update
    void Update(UPMOBUsuarios upmobUsuarios);

    @Delete
    void Delete(UPMOBUsuarios upmobUsuarios);

    @Query("SELECT * FROM UPMOBUsuarios")
    List<UPMOBUsuarios> GetAllRecords();
}
