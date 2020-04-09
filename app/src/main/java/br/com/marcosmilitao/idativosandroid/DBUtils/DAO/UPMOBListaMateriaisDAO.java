package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriais;

/**
 * Created by marcoswerneck on 03/10/19.
 */

@Dao
public interface UPMOBListaMateriaisDAO {
    @Insert
    void Create(UPMOBListaMateriais upmobListaMateriais);

    @Update
    void Update(UPMOBListaMateriais upmobListaMateriais);

    @Delete
    void Delete(UPMOBListaMateriais upmobListaMateriais);

    @Query("SELECT * FROM UPMOBListaMateriais")
    List<UPMOBListaMateriais> GetAllRecords();
}
