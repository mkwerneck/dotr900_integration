package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
