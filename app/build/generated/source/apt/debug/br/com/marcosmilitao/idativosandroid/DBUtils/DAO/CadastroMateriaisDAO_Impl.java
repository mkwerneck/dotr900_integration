package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.POJO.InventarioPlanejado_Materiais;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroMateriaisDAO_Impl implements CadastroMateriaisDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCadastroMateriais;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCadastroMateriais;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCadastroMateriais;

  public CadastroMateriaisDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCadastroMateriais = new EntityInsertionAdapter<CadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CadastroMateriais`(`Id`,`IdOriginal`,`RowVersion`,`NumSerie`,`Patrimonio`,`Quantidade`,`DataCadastro`,`DataFabricacao`,`DataValidade`,`ValorUnitario`,`DadosTecnicos`,`NotaFiscal`,`DataEntradaNotaFiscal`,`Categoria`,`ModeloMateriaisItemIdOriginal`,`PosicaoOriginalItemIdoriginal`,`TAGID`,`EmUso`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getNumSerie() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNumSerie());
        }
        if (value.getPatrimonio() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPatrimonio());
        }
        stmt.bindLong(6, value.getQuantidade());
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataCadastro());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataValidade());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        stmt.bindDouble(10, value.getValorUnitario());
        if (value.getDadosTecnicos() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDadosTecnicos());
        }
        if (value.getNotaFiscal() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotaFiscal());
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataEntradaNotaFiscal());
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_3);
        }
        if (value.getCategoria() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getCategoria());
        }
        stmt.bindLong(15, value.getModeloMateriaisItemIdOriginal());
        stmt.bindLong(16, value.getPosicaoOriginalItemIdoriginal());
        if (value.getTAGID() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getTAGID());
        }
        final int _tmp_4;
        _tmp_4 = value.isEmUso() ? 1 : 0;
        stmt.bindLong(18, _tmp_4);
      }
    };
    this.__deletionAdapterOfCadastroMateriais = new EntityDeletionOrUpdateAdapter<CadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CadastroMateriais` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriais value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCadastroMateriais = new EntityDeletionOrUpdateAdapter<CadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CadastroMateriais` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`NumSerie` = ?,`Patrimonio` = ?,`Quantidade` = ?,`DataCadastro` = ?,`DataFabricacao` = ?,`DataValidade` = ?,`ValorUnitario` = ?,`DadosTecnicos` = ?,`NotaFiscal` = ?,`DataEntradaNotaFiscal` = ?,`Categoria` = ?,`ModeloMateriaisItemIdOriginal` = ?,`PosicaoOriginalItemIdoriginal` = ?,`TAGID` = ?,`EmUso` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getNumSerie() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNumSerie());
        }
        if (value.getPatrimonio() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPatrimonio());
        }
        stmt.bindLong(6, value.getQuantidade());
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataCadastro());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataValidade());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        stmt.bindDouble(10, value.getValorUnitario());
        if (value.getDadosTecnicos() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getDadosTecnicos());
        }
        if (value.getNotaFiscal() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotaFiscal());
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataEntradaNotaFiscal());
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_3);
        }
        if (value.getCategoria() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getCategoria());
        }
        stmt.bindLong(15, value.getModeloMateriaisItemIdOriginal());
        stmt.bindLong(16, value.getPosicaoOriginalItemIdoriginal());
        if (value.getTAGID() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getTAGID());
        }
        final int _tmp_4;
        _tmp_4 = value.isEmUso() ? 1 : 0;
        stmt.bindLong(18, _tmp_4);
        stmt.bindLong(19, value.getId());
      }
    };
  }

  @Override
  public void Create(CadastroMateriais cadastroMateriais) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCadastroMateriais.insert(cadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(CadastroMateriais cadastroMateriais) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCadastroMateriais.handle(cadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(CadastroMateriais cadastroMateriais) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCadastroMateriais.handle(cadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM CadastroMateriais ORDER BY RowVersion DESC LIMIT 1 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public CadastroMateriais GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM CadastroMateriais WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfPatrimonio = _cursor.getColumnIndexOrThrow("Patrimonio");
      final int _cursorIndexOfQuantidade = _cursor.getColumnIndexOrThrow("Quantidade");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfDataValidade = _cursor.getColumnIndexOrThrow("DataValidade");
      final int _cursorIndexOfValorUnitario = _cursor.getColumnIndexOrThrow("ValorUnitario");
      final int _cursorIndexOfDadosTecnicos = _cursor.getColumnIndexOrThrow("DadosTecnicos");
      final int _cursorIndexOfNotaFiscal = _cursor.getColumnIndexOrThrow("NotaFiscal");
      final int _cursorIndexOfDataEntradaNotaFiscal = _cursor.getColumnIndexOrThrow("DataEntradaNotaFiscal");
      final int _cursorIndexOfCategoria = _cursor.getColumnIndexOrThrow("Categoria");
      final int _cursorIndexOfModeloMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemIdOriginal");
      final int _cursorIndexOfPosicaoOriginalItemIdoriginal = _cursor.getColumnIndexOrThrow("PosicaoOriginalItemIdoriginal");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfEmUso = _cursor.getColumnIndexOrThrow("EmUso");
      final CadastroMateriais _result;
      if(_cursor.moveToFirst()) {
        _result = new CadastroMateriais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpNumSerie;
        _tmpNumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _result.setNumSerie(_tmpNumSerie);
        final String _tmpPatrimonio;
        _tmpPatrimonio = _cursor.getString(_cursorIndexOfPatrimonio);
        _result.setPatrimonio(_tmpPatrimonio);
        final int _tmpQuantidade;
        _tmpQuantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _result.setQuantidade(_tmpQuantidade);
        final Date _tmpDataCadastro;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataCadastro);
        _tmpDataCadastro = TimeStampConverter.fromTimestamp(_tmp);
        _result.setDataCadastro(_tmpDataCadastro);
        final Date _tmpDataFabricacao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp_1);
        _result.setDataFabricacao(_tmpDataFabricacao);
        final Date _tmpDataValidade;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataValidade);
        _tmpDataValidade = TimeStampConverter.fromTimestamp(_tmp_2);
        _result.setDataValidade(_tmpDataValidade);
        final double _tmpValorUnitario;
        _tmpValorUnitario = _cursor.getDouble(_cursorIndexOfValorUnitario);
        _result.setValorUnitario(_tmpValorUnitario);
        final String _tmpDadosTecnicos;
        _tmpDadosTecnicos = _cursor.getString(_cursorIndexOfDadosTecnicos);
        _result.setDadosTecnicos(_tmpDadosTecnicos);
        final String _tmpNotaFiscal;
        _tmpNotaFiscal = _cursor.getString(_cursorIndexOfNotaFiscal);
        _result.setNotaFiscal(_tmpNotaFiscal);
        final Date _tmpDataEntradaNotaFiscal;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataEntradaNotaFiscal);
        _tmpDataEntradaNotaFiscal = TimeStampConverter.fromTimestamp(_tmp_3);
        _result.setDataEntradaNotaFiscal(_tmpDataEntradaNotaFiscal);
        final String _tmpCategoria;
        _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
        _result.setCategoria(_tmpCategoria);
        final int _tmpModeloMateriaisItemIdOriginal;
        _tmpModeloMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloMateriaisItemIdOriginal);
        _result.setModeloMateriaisItemIdOriginal(_tmpModeloMateriaisItemIdOriginal);
        final int _tmpPosicaoOriginalItemIdoriginal;
        _tmpPosicaoOriginalItemIdoriginal = _cursor.getInt(_cursorIndexOfPosicaoOriginalItemIdoriginal);
        _result.setPosicaoOriginalItemIdoriginal(_tmpPosicaoOriginalItemIdoriginal);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _result.setTAGID(_tmpTAGID);
        final boolean _tmpEmUso;
        final int _tmp_4;
        _tmp_4 = _cursor.getInt(_cursorIndexOfEmUso);
        _tmpEmUso = _tmp_4 != 0;
        _result.setEmUso(_tmpEmUso);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public CadastroMateriais GetByTAGID(String qryTAGID, boolean qryEmUso) {
    final String _sql = "SELECT * FROM CadastroMateriais WHERE TAGID = ? AND EmUso = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (qryTAGID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryTAGID);
    }
    _argIndex = 2;
    final int _tmp;
    _tmp = qryEmUso ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfPatrimonio = _cursor.getColumnIndexOrThrow("Patrimonio");
      final int _cursorIndexOfQuantidade = _cursor.getColumnIndexOrThrow("Quantidade");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfDataValidade = _cursor.getColumnIndexOrThrow("DataValidade");
      final int _cursorIndexOfValorUnitario = _cursor.getColumnIndexOrThrow("ValorUnitario");
      final int _cursorIndexOfDadosTecnicos = _cursor.getColumnIndexOrThrow("DadosTecnicos");
      final int _cursorIndexOfNotaFiscal = _cursor.getColumnIndexOrThrow("NotaFiscal");
      final int _cursorIndexOfDataEntradaNotaFiscal = _cursor.getColumnIndexOrThrow("DataEntradaNotaFiscal");
      final int _cursorIndexOfCategoria = _cursor.getColumnIndexOrThrow("Categoria");
      final int _cursorIndexOfModeloMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemIdOriginal");
      final int _cursorIndexOfPosicaoOriginalItemIdoriginal = _cursor.getColumnIndexOrThrow("PosicaoOriginalItemIdoriginal");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfEmUso = _cursor.getColumnIndexOrThrow("EmUso");
      final CadastroMateriais _result;
      if(_cursor.moveToFirst()) {
        _result = new CadastroMateriais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpNumSerie;
        _tmpNumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _result.setNumSerie(_tmpNumSerie);
        final String _tmpPatrimonio;
        _tmpPatrimonio = _cursor.getString(_cursorIndexOfPatrimonio);
        _result.setPatrimonio(_tmpPatrimonio);
        final int _tmpQuantidade;
        _tmpQuantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _result.setQuantidade(_tmpQuantidade);
        final Date _tmpDataCadastro;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataCadastro);
        _tmpDataCadastro = TimeStampConverter.fromTimestamp(_tmp_1);
        _result.setDataCadastro(_tmpDataCadastro);
        final Date _tmpDataFabricacao;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp_2);
        _result.setDataFabricacao(_tmpDataFabricacao);
        final Date _tmpDataValidade;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataValidade);
        _tmpDataValidade = TimeStampConverter.fromTimestamp(_tmp_3);
        _result.setDataValidade(_tmpDataValidade);
        final double _tmpValorUnitario;
        _tmpValorUnitario = _cursor.getDouble(_cursorIndexOfValorUnitario);
        _result.setValorUnitario(_tmpValorUnitario);
        final String _tmpDadosTecnicos;
        _tmpDadosTecnicos = _cursor.getString(_cursorIndexOfDadosTecnicos);
        _result.setDadosTecnicos(_tmpDadosTecnicos);
        final String _tmpNotaFiscal;
        _tmpNotaFiscal = _cursor.getString(_cursorIndexOfNotaFiscal);
        _result.setNotaFiscal(_tmpNotaFiscal);
        final Date _tmpDataEntradaNotaFiscal;
        final String _tmp_4;
        _tmp_4 = _cursor.getString(_cursorIndexOfDataEntradaNotaFiscal);
        _tmpDataEntradaNotaFiscal = TimeStampConverter.fromTimestamp(_tmp_4);
        _result.setDataEntradaNotaFiscal(_tmpDataEntradaNotaFiscal);
        final String _tmpCategoria;
        _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
        _result.setCategoria(_tmpCategoria);
        final int _tmpModeloMateriaisItemIdOriginal;
        _tmpModeloMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloMateriaisItemIdOriginal);
        _result.setModeloMateriaisItemIdOriginal(_tmpModeloMateriaisItemIdOriginal);
        final int _tmpPosicaoOriginalItemIdoriginal;
        _tmpPosicaoOriginalItemIdoriginal = _cursor.getInt(_cursorIndexOfPosicaoOriginalItemIdoriginal);
        _result.setPosicaoOriginalItemIdoriginal(_tmpPosicaoOriginalItemIdoriginal);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _result.setTAGID(_tmpTAGID);
        final boolean _tmpEmUso;
        final int _tmp_5;
        _tmp_5 = _cursor.getInt(_cursorIndexOfEmUso);
        _tmpEmUso = _tmp_5 != 0;
        _result.setEmUso(_tmpEmUso);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> GetByFilter(String qryFilter) {
    final String _sql = "SELECT TAGID FROM CadastroMateriais as cm INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal WHERE cm.TAGID like ? OR mm.IDOmni like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (qryFilter == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryFilter);
    }
    _argIndex = 2;
    if (qryFilter == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryFilter);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<InventarioPlanejado_Materiais> GetByPosicaoOriginal(String qryCodigo) {
    final String _sql = "SELECT mm.Modelo, mm.IDOmni, cm.NumSerie, cm.TAGID FROM CadastroMateriais as cm INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal JOIN Posicoes as po ON cm.PosicaoOriginalItemIdoriginal = po.IdOriginal WHERE po.Codigo like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCodigo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryCodigo);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("Modelo");
      final int _cursorIndexOfIDOmni = _cursor.getColumnIndexOrThrow("IDOmni");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final List<InventarioPlanejado_Materiais> _result = new ArrayList<InventarioPlanejado_Materiais>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final InventarioPlanejado_Materiais _item;
        _item = new InventarioPlanejado_Materiais();
        _item.Modelo = _cursor.getString(_cursorIndexOfModelo);
        _item.IDOmni = _cursor.getString(_cursorIndexOfIDOmni);
        _item.NumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _item.TAGID = _cursor.getString(_cursorIndexOfTAGID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public InventarioPlanejado_Materiais GetInventarioPlanejadoByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT mm.Modelo, mm.IDOmni, cm.NumSerie, cm.TAGID FROM CadastroMateriais as cm INNER JOIN ModeloMateriais as mm ON cm.ModeloMateriaisItemIdOriginal = mm.IdOriginal WHERE cm.IdOriginal like ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("Modelo");
      final int _cursorIndexOfIDOmni = _cursor.getColumnIndexOrThrow("IDOmni");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final InventarioPlanejado_Materiais _result;
      if(_cursor.moveToFirst()) {
        _result = new InventarioPlanejado_Materiais();
        _result.Modelo = _cursor.getString(_cursorIndexOfModelo);
        _result.IDOmni = _cursor.getString(_cursorIndexOfIDOmni);
        _result.NumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _result.TAGID = _cursor.getString(_cursorIndexOfTAGID);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Integer GetIdOriginalByTAGID(String qryTAGID) {
    final String _sql = "SELECT IdOriginal FROM CadastroMateriais WHERE TAGID like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryTAGID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryTAGID);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final Integer _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getInt(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> TesteTelaCadastroFerramentas() {
    final String _sql = "SELECT TAGID FROM CadastroMateriais LIMIT 25";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
