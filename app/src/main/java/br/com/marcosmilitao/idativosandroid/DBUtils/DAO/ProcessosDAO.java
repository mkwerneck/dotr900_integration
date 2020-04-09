package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import javax.annotation.Nullable;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Processos;

/**
 * Created by marcoswerneck on 24/09/19.
 */
@Dao
public interface ProcessosDAO {
    @Insert
    void Create(Processos processos);

    @Update
    void Update(Processos processos);

    @Delete
    void Delete(Processos processos);

    @Query("SELECT RowVersion FROM Processos ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Processos WHERE IdOriginal = :qryIdOriginal ")
    Processos GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT p.Id, p.IdOriginal, p.RowVersion, p.Status, p.DataInicio, p.DataConclusao, p.DataCancelado, p.CadsatroEquipamentosItemIdOriginal FROM Processos as p INNER JOIN CadastroEquipamentos as c ON p.CadsatroEquipamentosItemIdOriginal = c.IdOriginal INNER JOIN ModeloEquipamentos as m ON c.ModeloEquipamentoItemIdOriginal = m.IdOriginal WHERE p.Status = :qryStatus AND (m.Categoria <> 'Aeronave' OR c.Localizacao = :qryBase) ORDER BY p.IdOriginal DESC ")
    List<Processos> GetByStatus(String qryStatus, String qryBase);

    @Query("SELECT p.Id, p.IdOriginal, p.RowVersion, p.Status, p.DataInicio, p.DataConclusao, p.DataCancelado, p.CadsatroEquipamentosItemIdOriginal FROM Processos as p INNER JOIN CadastroEquipamentos as c ON p.CadsatroEquipamentosItemIdOriginal = c.IdOriginal INNER JOIN ModeloEquipamentos as m ON c.ModeloEquipamentoItemIdOriginal = m.IdOriginal WHERE p.Status = :qryStatus AND (m.Categoria <> 'Aeronave' OR c.Localizacao = :qryBase) AND (:qrySearch IS NULL OR (c.TraceNumber LIKE :qrySearch OR p.IdOriginal = :qrySearchInt)) ORDER BY p.IdOriginal DESC ")
    List<Processos> GetSearchResult(@Nullable String qrySearch, @Nullable Integer qrySearchInt, String qryStatus, String qryBase);

}
