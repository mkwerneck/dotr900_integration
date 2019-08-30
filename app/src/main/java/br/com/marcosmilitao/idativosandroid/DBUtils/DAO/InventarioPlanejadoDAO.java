package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;

@Dao
public interface InventarioPlanejadoDAO {
    @Insert
    void Create(InventarioPlanejado inventarioPlanejado);

    @Update
    void Update(InventarioPlanejado inventarioPlanejado);

    @Delete
    void Delete(InventarioPlanejado inventarioPlanejado);

    @Query("SELECT RowVersion FROM InventarioPlanejado ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM InventarioPlanejado WHERE IdOriginal = :qryIdOriginal ")
    InventarioPlanejado GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT Descricao FROM InventarioPlanejado")
    List<String> GetAllDescricoes();

    @Query("SELECT IdOriginal FROM InventarioPlanejado WHERE Descricao like :qryDescricao")
    Integer GetIdOriginalByDescricao(String qryDescricao);
}
