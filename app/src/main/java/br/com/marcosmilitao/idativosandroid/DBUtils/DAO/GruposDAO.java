package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;

@Dao
public interface GruposDAO {
    @Insert
    void Create(Grupos grupos);

    @Update
    void Update(Grupos grupos);

    @Delete
    void Delete(Grupos grupos);

    @Query("SELECT RowVersion FROM Grupos ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Grupos WHERE IdOriginal = :qryIdOriginal ")
    Grupos GetByIdOriginal(String qryIdOriginal);

    @Query("SELECT Titulo FROM Grupos")
    List<String> GetAllTitulos();

    @Query("SELECT IdOriginal, Titulo as Role FROM Grupos")
    List<FuncoesCU> GetSpinnerItems();

    @Query("SELECT IdOriginal, Titulo as Role FROM Grupos WHERE IdOriginal = :qryIdOriginal")
    FuncoesCU GetSpinnerItem(String qryIdOriginal);
}
