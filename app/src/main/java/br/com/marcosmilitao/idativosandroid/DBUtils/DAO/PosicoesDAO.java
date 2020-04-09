package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import android.widget.ArrayAdapter;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.POJO.AlmoxarifadosCP;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicoesInv;

@Dao
public interface PosicoesDAO {
    @Insert
    void Create(Posicoes posicao);

    @Update
    void Update(Posicoes posicao);

    @Delete
    void Delete(Posicoes posicao);

    @Query("SELECT RowVersion FROM Posicoes ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM Posicoes WHERE IdOriginal = :qryIdOriginal ")
    Posicoes GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT Codigo FROM Posicoes WHERE AlmoxarifadoId = :qryCodigoAlmoxarifado")
    List<String> GetPosicoesByAlmoxarifado(int qryCodigoAlmoxarifado);

    @Query("SELECT * FROM Posicoes WHERE TAGID = :qryTAGID")
    Posicoes GetByTAGID(String qryTAGID);

    /*@Query("SELECT DISTINCT AlmoxarifadoId FROM Posicoes")
    List<String> GetAlmoxarifados();*/

    @Query("SELECT * FROM Posicoes WHERE Codigo like :qryCodigo LIMIT 1")
    Posicoes GetByCodPosicao(String qryCodigo);

    /*@Query("SELECT Almoxarifado FROM Posicoes WHERE TAGID like :qryTAGIDPosicao")
    String GetAlmoxarifadoByPosicao(String qryTAGIDPosicao);*/

    @Query("SELECT TAGID FROM Posicoes WHERE Codigo like :qryCodigo")
    String GetTAGIDByCodPosicao(String qryCodigo);

    @Query("SELECT Codigo FROM Posicoes WHERE IdOriginal like :qryIdOriginal")
    String GetCodigoByIdOriginal(int qryIdOriginal);

    @Query("SELECT IdOriginal as idOriginal, Codigo as codPosicao, Descricao as descPosicao FROM Posicoes")
    List<PosicaoCF> GetAllPosicoesCustomAdapter();

    @Query("SELECT IdOriginal as idOriginal, Codigo as codPosicao, Descricao as descPosicao FROM Posicoes WHERE Descricao like :charSequence COLLATE NOCASE OR Codigo like :charSequence LIMIT 10")
    List<PosicaoCF> GetFilterPosicoesCustomAdapter(String charSequence);

    @Query("SELECT IdOriginal, Descricao FROM Posicoes WHERE AlmoxarifadoId like :qryAlmoxarifadoId")
    List<PosicoesInv> GetSpinnerItems(int qryAlmoxarifadoId);
}
