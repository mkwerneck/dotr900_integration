package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;

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

    @Query("SELECT * FROM ListaMateriaisListaTarefas")
    List<ListaMateriaisListaTarefas> GetAll();

    @Query("SELECT * FROM ListaMateriaisListaTarefas WHERE ProcessoIdOriginal = :qryProcessoId ")
    List<ListaMateriaisListaTarefas> GetByProcessoId(int qryProcessoId);
}
