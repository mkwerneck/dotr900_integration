package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.POJO.InventarioPlanejado_Materiais;

@Dao
public interface CadastroMateriaisDAO {
    @Insert
    void Create(CadastroMateriais cadastroMateriais);

    @Update
    void Update(CadastroMateriais cadastroMateriais);

    @Delete
    void Delete(CadastroMateriais cadastroMateriais);

    @Query("SELECT RowVersion FROM CadastroMateriais ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM CadastroMateriais WHERE IdOriginal = :qryIdOriginal ")
    CadastroMateriais GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM CadastroMateriais WHERE TAGID = :qryTAGID AND EmUso = :qryEmUso" )
    CadastroMateriais GetByTAGID(String qryTAGID, boolean qryEmUso);

    @Query("SELECT TAGID FROM CadastroMateriais as cm " +
            "INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal " +
            "WHERE cm.TAGID like :qryFilter OR mm.IDOmni like :qryFilter")
    List<String> GetByFilter(String qryFilter);

    @Query("SELECT mm.Modelo, mm.IDOmni, cm.NumSerie, cm.TAGID FROM CadastroMateriais as cm " +
            "INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal" +
            " JOIN Posicoes as po ON cm.PosicaoOriginalItemIdoriginal = po.IdOriginal WHERE po.Codigo like :qryCodigo")
    List<InventarioPlanejado_Materiais> GetByPosicaoOriginal(String qryCodigo);

    @Query("SELECT mm.Modelo, mm.IDOmni, cm.NumSerie, cm.TAGID FROM CadastroMateriais as cm " +
            "INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal" +
            " WHERE cm.IdOriginal like :qryIdOriginal " +
            "LIMIT 1")
    InventarioPlanejado_Materiais GetInventarioPlanejadoByIdOriginal(int qryIdOriginal);

    @Query("SELECT IdOriginal FROM CadastroMateriais WHERE TAGID like :qryTAGID")
    Integer GetIdOriginalByTAGID(String qryTAGID);

    @Query("SELECT TAGID FROM CadastroMateriais LIMIT 25")
    List<String> TesteTelaCadastroFerramentas ();
}
