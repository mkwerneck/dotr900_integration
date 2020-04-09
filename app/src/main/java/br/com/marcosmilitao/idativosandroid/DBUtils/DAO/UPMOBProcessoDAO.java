package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBProcesso;

/**
 * Created by marcoswerneck on 03/10/19.
 */

@Dao
public interface UPMOBProcessoDAO {
    @Insert
    void Create(UPMOBProcesso upmobProcesso);

    @Update
    void Update(UPMOBProcesso upmobProcesso);

    @Delete
    void Delete(UPMOBProcesso upmobProcesso);

    @Query("SELECT * FROM UPMOBProcesso")
    List<UPMOBProcesso> GetAllRecords();

    @Query("SELECT COUNT(IdOriginal) FROM UPMOBPROCESSO WHERE IdOriginal = :qryProcessoId")
    int ExistsByProcessoId(int qryProcessoId);
}
