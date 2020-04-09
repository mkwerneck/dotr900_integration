package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;

@Dao
public interface ModeloMateriaisDAO {
    @Insert
    void Create(ModeloMateriais modeloMateriais);

    @Update
    void Update(ModeloMateriais modeloMateriais);

    @Delete
    void Delete(ModeloMateriais modeloMateriais);

    @Query("SELECT RowVersion FROM ModeloMateriais ORDER BY RowVersion DESC LIMIT 1 ")
    String GetLastRowVersion();

    @Query("SELECT * FROM ModeloMateriais WHERE IdOriginal = :qryIdOriginal ")
    ModeloMateriais GetByIdOriginal(int qryIdOriginal);

    @Query("SELECT * FROM ModeloMateriais WHERE IDOmni like :qryNumProduto LIMIT 1")
    ModeloMateriais GetByNumProduto(String qryNumProduto);

    @Query("SELECT Modelo FROM ModeloMateriais")
    List<String> GetAllNomeModelo();

    @Query("SELECT IdOriginal as idOriginal, Modelo as modelo, IDOmni as numProduto FROM ModeloMateriais")
    List<ModeloMateriaisCF> GetAllModelosCustomAdapter();

    @Query("SELECT IdOriginal as idOriginal, Modelo as modelo, IDOmni as numProduto FROM ModeloMateriais WHERE Modelo like :charSequence COLLATE NOCASE OR IDOmni like :charSequence LIMIT 10")
    List<ModeloMateriaisCF> GetFilterModelosCustomAdapter(String charSequence);

    @Query("SELECT IDOmni FROM ModeloMateriais WHERE Modelo like :qryModelo")
    String GetNumProdutoByModelo(String qryModelo);

    @Query("SELECT Modelo FROM ModeloMateriais WHERE IdOriginal like :qryIdOriginal")
    String GetModeloByIdOriginal(int qryIdOriginal);


}
