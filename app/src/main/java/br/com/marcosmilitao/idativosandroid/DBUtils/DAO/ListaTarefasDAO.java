package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM ListaTarefas")
    List<ListaTarefas> GetAll();

    @Query("SELECT TarefaIdOriginal FROM ListaTarefas WHERE ProcessoIdOriginal = :codProcesso AND Status = 'Em Andamento' ORDER BY IdOriginal DESC LIMIT 1")
    int GetUltimaTarefaByCodProcesso(int codProcesso);

    @Query("SELECT * FROM ListaTarefas WHERE ProcessoIdOriginal = :codProcesso AND Status = 'Em Andamento' ORDER BY IdOriginal DESC LIMIT 1")
    ListaTarefas GetUltimaListaTarefaByCodProcesso(int codProcesso);
}
