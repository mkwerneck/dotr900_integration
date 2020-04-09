package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;

/**
 * Created by marcoswerneck on 21/08/19.
 */

@Dao
public interface UPMOBDescartesDAO {
    @Insert
    void Create(UPMOBDescartes upmobDescartes);

    @Update
    void Update(UPMOBDescartes upmobDescartes);

    @Delete
    void Delete(UPMOBDescartes upmobDescartes);

    @Query("SELECT * FROM UPMOBDescartes")
    List<UPMOBDescartes> GetAllRecords();
}
