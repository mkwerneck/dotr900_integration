package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;

@Dao
public interface ListaTarefasDAO {
    @Insert
    void Create(ListaTarefas listaTarefas);

    @Update
    void Update(ListaTarefas listaTarefas);

    @Delete
    void Delete(ListaTarefas listaTarefas);

    @Query("SELECT RowVersion FROM ListaTarefas ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ListaTarefas WHERE IdOriginal = :qryIdOriginal ")
    ListaTarefas GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ListaTarefas as lt INNER JOIN CadastroEquipamentos as ce ON lt.CadsatroEquipamentosItemIdOriginal = ce.IdOriginal WHERE (lt.Status <> 'Concluido' AND lt.Status <> 'Cancelado') AND (lt.Processo like :qryFilter || ce.TraceNumber like :qryFilter)")
    List<ListaTarefas> GetListaTarefasFilter(String qryFilter);

    @Query("SELECT * FROM ListaTarefas WHERE (Status != 'Concluido' AND Status != 'Cancelado')")
    List<ListaTarefas> GetListaTarefas();
}
