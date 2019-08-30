package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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

    @Query("SELECT * FROM CadastroEquipamentos WHERE Status != 'Inativo'")
    List<CadastroEquipamentos> GetCadastroEquipamentos();

    @Query("SELECT * FROM CadastroEquipamentos as ce INNER JOIN ModeloEquipamentos as me ON ce.ModeloEquipamentoItemIdOriginal = me.IdOriginal WHERE Status != 'Inativo' AND (me.Modelo like :qryFilter || ce.TraceNumber like :qryFilter)")
    List<CadastroEquipamentos> GetCadastroEquipamentosFilter(String qryFilter);
}
