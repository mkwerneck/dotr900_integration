package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;

@Dao
public interface ListaServicosListaTarefasDAO {
    @Insert
    void Create(ListaServicosListaTarefas listaServicosListaTarefas);

    @Update
    void Update(ListaServicosListaTarefas listaServicosListaTarefas);

    @Delete
    void Delete(ListaServicosListaTarefas listaServicosListaTarefas);

    @Query("SELECT RowVersion FROM ListaServicosListaTarefas ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ListaServicosListaTarefas WHERE IdOriginal = :qryIdOriginal ")
    ListaServicosListaTarefas GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ListaServicosListaTarefas WHERE ListaTarefaIdOriginal like :qrylistaTarefaIdOriginal")
    List<ListaServicosListaTarefas> GetByListaTarefaIdOriginal(int qrylistaTarefaIdOriginal);
}
