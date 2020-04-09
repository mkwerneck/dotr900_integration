package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.POJO.InvPlanejado;

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

    @Query("SELECT IdOriginal, Descricao FROM InventarioPlanejado")
    List<InvPlanejado> GetSpinnerItens();
}
