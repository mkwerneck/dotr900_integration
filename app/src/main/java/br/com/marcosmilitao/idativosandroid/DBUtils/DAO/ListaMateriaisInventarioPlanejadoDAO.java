package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisInventarioPlanejado;

/**
 * Created by marcoswerneck on 04/09/19.
 */

@Dao
public interface ListaMateriaisInventarioPlanejadoDAO {
    @Insert
    void Create(ListaMateriaisInventarioPlanejado listaMateriaisInventarioPlanejado);

    @Update
    void Update(ListaMateriaisInventarioPlanejado listaMateriaisInventarioPlanejado);

    @Delete
    void Delete(ListaMateriaisInventarioPlanejado listaMateriaisInventarioPlanejado);

    @Query("SELECT RowVersion FROM ListaMateriaisInventarioPlanejado ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ListaMateriaisInventarioPlanejado WHERE IdOriginal = :qryIdOriginal ")
    ListaMateriaisInventarioPlanejado GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ListaMateriaisInventarioPlanejado WHERE InventarioPlanejadoId = :qryInvPlanId ")
    List<ListaMateriaisInventarioPlanejado> GetByInventarioPlanejadoId(int qryInvPlanId);
}
