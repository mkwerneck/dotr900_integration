package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;

@Dao
public interface UPMOBHistoricoLocalizacaoDAO {
    @Insert
    void Create(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao);

    @Update
    void Update(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao);

    @Delete
    void Delete(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao);

    @Query("SELECT * FROM UPMOBHistoricoLocalizacao")
    List<UPMOBHistoricoLocalizacao> GetAllRecords();
}
