package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;

@Dao
public interface UsuariosDAO {
    @Insert
    void Create(Usuarios usuarios);

    @Update
    void Update(Usuarios usuarios);

    @Delete
    void Delete(Usuarios usuarios);

    @Query("SELECT RowVersion FROM Usuarios ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Usuarios WHERE IdOriginal = :qryIdOriginal ")
    Usuarios GetByIdOriginal(String qryIdOriginal);

    @Query("SELECT * FROM Usuarios WHERE TAGID = :qryTAGID")
    Usuarios GetByTAGID(String qryTAGID);

    @Query("SELECT NomeCompleto FROM Usuarios")
    List<String> GetAllRecords();

    @Query("SELECT * FROM Usuarios WHERE UserName = :qryUsername")
    Usuarios GetByUsername(String qryUsername);
}
