package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Almoxarifados;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosInv;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;

/**
 * Created by marcoswerneck on 02/09/19.
 */

@Dao
public interface AlmoxarifadosDAO {
    @Insert
    void Create(Almoxarifados almoxarifados);

    @Update
    void Update(Almoxarifados almoxarifados);

    @Delete
    void Delete(Almoxarifados almoxarifados);

    @Query("SELECT RowVersion FROM Almoxarifados ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Almoxarifados WHERE IdOriginal = :qryIdOriginal ")
    Almoxarifados GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT IdOriginal, Nome as Codigo FROM Almoxarifados WHERE SetorProprietarioId = :qryBaseId")
    List<AlmoxarifadosCP> GetSpinnerItems(int qryBaseId);
}
