package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
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
