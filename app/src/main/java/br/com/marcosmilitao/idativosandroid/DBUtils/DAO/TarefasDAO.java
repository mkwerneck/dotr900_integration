package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;

@Dao
public interface TarefasDAO {
    @Insert
    void Create(Tarefas tarefas);

    @Update
    void Update(Tarefas tarefas);

    @Delete
    void Delete(Tarefas tarefas);

    @Query("SELECT RowVersion FROM Tarefas ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Tarefas WHERE IdOriginal = :qryIdOriginal ")
    Tarefas GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM Tarefas WHERE CategoriaEquipamentos like :qryCategoria ORDER BY Codigo LIMIT 1")
    Tarefas GetFirstByCategoria(String qryCategoria);
}
