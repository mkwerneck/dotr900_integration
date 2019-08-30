package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;

@Dao
public interface ProprietariosDAO {
    @Insert
    void Create(Proprietarios proprietarios);

    @Update
    void Update(Proprietarios proprietarios);

    @Delete
    void Delete(Proprietarios proprietarios);

    @Query("SELECT RowVersion FROM Proprietarios ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Proprietarios WHERE IdOriginal = :qryIdOriginal ")
    Proprietarios GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT Descricao FROM Proprietarios")
    List<String> GetAllDescricao();
}
