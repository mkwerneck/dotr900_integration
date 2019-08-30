package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;

@Dao
public interface UPMOBCadastroMateriaisDAO {
    @Insert
    void Create(UPMOBCadastroMateriais upmobCadastroMateriais);

    @Update
    void Update(UPMOBCadastroMateriais upmobCadastroMateriais);

    @Delete
    void Delete(UPMOBCadastroMateriais upmobCadastroMateriais);

    @Query("SELECT * FROM UPMOBCadastroMateriais")
    List<UPMOBCadastroMateriais> GetAllRecords();
}
