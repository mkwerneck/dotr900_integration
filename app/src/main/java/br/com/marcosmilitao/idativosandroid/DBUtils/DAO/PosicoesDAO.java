package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.ArrayAdapter;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;

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

    @Query("SELECT Codigo FROM Posicoes WHERE Almoxarifado = :qryCodigoAlmoxarifado")
    List<String> GetPosicoesByAlmoxarifado(String qryCodigoAlmoxarifado);

    @Query("SELECT * FROM Posicoes WHERE TAGID = :qryTAGID")
    Posicoes GetByTAGID(String qryTAGID);

    @Query("SELECT DISTINCT Almoxarifado FROM Posicoes")
    List<String> GetAlmoxarifados();

    @Query("SELECT * FROM Posicoes WHERE Codigo like :qryCodigo LIMIT 1")
    Posicoes GetByCodPosicao(String qryCodigo);

    @Query("SELECT Almoxarifado FROM Posicoes WHERE TAGID like :qryTAGIDPosicao")
    String GetAlmoxarifadoByPosicao(String qryTAGIDPosicao);

    @Query("SELECT TAGID FROM Posicoes WHERE Codigo like :qryCodigo")
    String GetTAGIDByCodPosicao(String qryCodigo);

    @Query("SELECT Codigo FROM Posicoes WHERE IdOriginal like :qryIdOriginal")
    String GetCodigoByIdOriginal(int qryIdOriginal);

    @Query("SELECT IdOriginal as idOriginal, Codigo as codPosicao, Descricao as descPosicao FROM Posicoes")
    List<PosicaoCF> GetAllPosicoesCustomAdapter();
}
