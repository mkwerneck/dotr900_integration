package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
