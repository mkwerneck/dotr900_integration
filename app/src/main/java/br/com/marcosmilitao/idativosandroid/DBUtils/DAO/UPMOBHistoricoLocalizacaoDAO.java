package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
