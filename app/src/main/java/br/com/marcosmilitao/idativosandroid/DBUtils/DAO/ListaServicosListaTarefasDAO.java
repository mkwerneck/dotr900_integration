package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM ListaServicosListaTarefas WHERE ListaTarefasItemIdOriginal like :qrylistaTarefaIdOriginal")
    List<ListaServicosListaTarefas> GetByListaTarefaIdOriginal(int qrylistaTarefaIdOriginal);
}
