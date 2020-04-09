package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens;

@Dao
public interface UPMOBCadastroMateriaisItensDAO {
    @Insert
    void Create(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens);

    @Update
    void Update(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens);

    @Delete
    void Delete(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens);

    @Query("SELECT * FROM UPMOBCadastroMateriaisItens")
    List<UPMOBCadastroMateriaisItens> GetAllRecords();
}
