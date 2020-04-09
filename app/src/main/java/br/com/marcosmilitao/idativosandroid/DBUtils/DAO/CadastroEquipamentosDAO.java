package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import java.util.List;

import javax.annotation.Nullable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;

@Dao
public interface CadastroEquipamentosDAO {
    @Insert
    void Create(CadastroEquipamentos cadastroEquipamentos);

    @Update
    void Update(CadastroEquipamentos cadastroEquipamentos);

    @Delete
    void Delete(CadastroEquipamentos cadastroEquipamentos);

    @Query("SELECT RowVersion FROM CadastroEquipamentos ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM CadastroEquipamentos WHERE IdOriginal = :qryIdOriginal ")
    CadastroEquipamentos GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM CadastroEquipamentos WHERE TAGID = :qryTAGID")
    CadastroEquipamentos GetByTAGID(String qryTAGID);

    @Query("SELECT c.Id, c.IdOriginal, c.RowVersion, c.TraceNumber, c.DataCadastro, c.DataFabricacao, c.Status, c.ModeloEquipamentoItemIdOriginal, c.Localizacao, c.TAGID FROM CadastroEquipamentos as c INNER JOIN ModeloEquipamentos as m ON c.ModeloEquipamentoItemIdOriginal = m.IdOriginal WHERE c.Status != 'Inativo' AND (m.Categoria <> 'Aeronave' OR c.Localizacao = :qryBase) ")
    List<CadastroEquipamentos> GetCadastroEquipamentos(String qryBase);

    @Query("SELECT * FROM CadastroEquipamentos as ce INNER JOIN ModeloEquipamentos as me ON ce.ModeloEquipamentoItemIdOriginal = me.IdOriginal WHERE Status != 'Inativo' AND (me.Modelo like :qryFilter || ce.TraceNumber like :qryFilter)")
    List<CadastroEquipamentos> GetCadastroEquipamentosFilter(String qryFilter);

    @Query("SELECT c.Id, c.IdOriginal, c.RowVersion, c.TraceNumber, c.DataCadastro, c.DataFabricacao, c.Status, c.ModeloEquipamentoItemIdOriginal, c.Localizacao, c.TAGID FROM CadastroEquipamentos as c INNER JOIN ModeloEquipamentos as m ON c.ModeloEquipamentoItemIdOriginal = m.IdOriginal WHERE Status != 'Inativo' AND (m.Categoria <> 'Aeronave' OR c.Localizacao = :qryBase) AND (:qrySearch IS NULL OR (c.TraceNumber LIKE :qrySearch OR m.Modelo LIKE :qrySearch))")
    List<CadastroEquipamentos> GetSearchResult(@Nullable String qrySearch, String qryBase);
}
