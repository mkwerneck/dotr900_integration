package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
