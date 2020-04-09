package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import android.graphics.ColorSpace;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;

@Dao
public interface ModeloEquipamentosDAO {
    @Insert
    void Create(ModeloEquipamentos modeloEquipamentos);

    @Update
    void Update(ModeloEquipamentos modeloEquipamentos);

    @Delete
    void Delete(ModeloEquipamentos modeloEquipamentos);

    @Query("SELECT RowVersion FROM ModeloEquipamentos ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ModeloEquipamentos WHERE IdOriginal = :qryIdOriginal ")
    ModeloEquipamentos GetByIdOriginal(int qryIdOriginal);
}
