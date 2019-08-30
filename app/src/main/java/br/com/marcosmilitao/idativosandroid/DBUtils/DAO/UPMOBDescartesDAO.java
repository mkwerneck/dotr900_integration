package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
