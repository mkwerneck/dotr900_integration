package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;

@Dao
public interface ListaMateriaisListaTarefasDAO {
    @Insert
    void Create(ListaMateriaisListaTarefas listaMateriaisListaTarefas);

    @Update
    void Update(ListaMateriaisListaTarefas listaMateriaisListaTarefas);

    @Delete
    void Delete(ListaMateriaisListaTarefas listaMateriaisListaTarefas);

    @Query("SELECT RowVersion FROM ListaMateriaisListaTarefas ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ListaMateriaisListaTarefas WHERE IdOriginal = :qryIdOriginal ")
    ListaMateriaisListaTarefas GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ListaMateriaisListaTarefas WHERE ListaTarefasItemIdOriginal = :qryIdOriginalListaTarefa")
    List<ListaMateriaisListaTarefas> GetByIdOriginalListaTarefa(int qryIdOriginalListaTarefa);
}
