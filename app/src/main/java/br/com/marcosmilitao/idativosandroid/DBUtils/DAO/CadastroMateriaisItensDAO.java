package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.helper.WorksheetItemKit;

@Dao
public interface CadastroMateriaisItensDAO {
    @Insert
    void Create(CadastroMateriaisItens cadastroMateriaisItens);

    @Update
    void Update(CadastroMateriaisItens cadastroMateriaisItens);

    @Delete
    void Delete(CadastroMateriaisItens cadastroMateriaisItens);

    @Query("SELECT RowVersion FROM CadastroMateriaisItens ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM CadastroMateriaisItens WHERE IdOriginal = :qryIdOriginal ")
    CadastroMateriaisItens GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM CadastroMateriaisItens WHERE CadastroMateriaisItemIdOriginal like :qryCadastroMateriaisIdOriginal")
    List<CadastroMateriaisItens> GetByCadastroMaterias(Integer qryCadastroMateriaisIdOriginal);
}
