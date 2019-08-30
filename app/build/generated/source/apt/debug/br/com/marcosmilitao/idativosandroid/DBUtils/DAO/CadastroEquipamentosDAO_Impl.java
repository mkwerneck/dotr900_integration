package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroEquipamentosDAO_Impl implements CadastroEquipamentosDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCadastroEquipamentos;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCadastroEquipamentos;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCadastroEquipamentos;

  public CadastroEquipamentosDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCadastroEquipamentos = new EntityInsertionAdapter<CadastroEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CadastroEquipamentos`(`Id`,`IdOriginal`,`RowVersion`,`TraceNumber`,`DataCadastro`,`DataFabricacao`,`Status`,`ModeloEquipamentoItemIdOriginal`,`Localizacao`,`TAGID`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroEquipamentos value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getTraceNumber() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTraceNumber());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataCadastro());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        if (value.getStatus() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getStatus());
        }
        stmt.bindLong(8, value.getModeloEquipamentoItemIdOriginal());
        if (value.getLocalizacao() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLocalizacao());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTAGID());
        }
      }
    };
    this.__deletionAdapterOfCadastroEquipamentos = new EntityDeletionOrUpdateAdapter<CadastroEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CadastroEquipamentos` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroEquipamentos value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCadastroEquipamentos = new EntityDeletionOrUpdateAdapter<CadastroEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CadastroEquipamentos` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`TraceNumber` = ?,`DataCadastro` = ?,`DataFabricacao` = ?,`Status` = ?,`ModeloEquipamentoItemIdOriginal` = ?,`Localizacao` = ?,`TAGID` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroEquipamentos value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getTraceNumber() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTraceNumber());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataCadastro());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        if (value.getStatus() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getStatus());
        }
        stmt.bindLong(8, value.getModeloEquipamentoItemIdOriginal());
        if (value.getLocalizacao() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLocalizacao());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTAGID());
        }
        stmt.bindLong(11, value.getId());
      }
    };
  }

  @Override
  public void Create(CadastroEquipamentos cadastroEquipamentos) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCadastroEquipamentos.insert(cadastroEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(CadastroEquipamentos cadastroEquipamentos) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCadastroEquipamentos.handle(cadastroEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(CadastroEquipamentos cadastroEquipamentos) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCadastroEquipamentos.handle(cadastroEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM CadastroEquipamentos ORDER BY RowVersion DESC LIMIT 1 ";
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
  public CadastroEquipamentos GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM CadastroEquipamentos WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfTraceNumber = _cursor.getColumnIndexOrThrow("TraceNumber");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfModeloEquipamentoItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloEquipamentoItemIdOriginal");
      final int _cursorIndexOfLocalizacao = _cursor.getColumnIndexOrThrow("Localizacao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final CadastroEquipamentos _result;
      if(_cursor.moveToFirst()) {
        _result = new CadastroEquipamentos();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpTraceNumber;
        _tmpTraceNumber = _cursor.getString(_cursorIndexOfTraceNumber);
        _result.setTraceNumber(_tmpTraceNumber);
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
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final int _tmpModeloEquipamentoItemIdOriginal;
        _tmpModeloEquipamentoItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloEquipamentoItemIdOriginal);
        _result.setModeloEquipamentoItemIdOriginal(_tmpModeloEquipamentoItemIdOriginal);
        final String _tmpLocalizacao;
        _tmpLocalizacao = _cursor.getString(_cursorIndexOfLocalizacao);
        _result.setLocalizacao(_tmpLocalizacao);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _result.setTAGID(_tmpTAGID);
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
  public CadastroEquipamentos GetByTAGID(String qryTAGID) {
    final String _sql = "SELECT * FROM CadastroEquipamentos WHERE TAGID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryTAGID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryTAGID);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfTraceNumber = _cursor.getColumnIndexOrThrow("TraceNumber");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfModeloEquipamentoItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloEquipamentoItemIdOriginal");
      final int _cursorIndexOfLocalizacao = _cursor.getColumnIndexOrThrow("Localizacao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final CadastroEquipamentos _result;
      if(_cursor.moveToFirst()) {
        _result = new CadastroEquipamentos();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpTraceNumber;
        _tmpTraceNumber = _cursor.getString(_cursorIndexOfTraceNumber);
        _result.setTraceNumber(_tmpTraceNumber);
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
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final int _tmpModeloEquipamentoItemIdOriginal;
        _tmpModeloEquipamentoItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloEquipamentoItemIdOriginal);
        _result.setModeloEquipamentoItemIdOriginal(_tmpModeloEquipamentoItemIdOriginal);
        final String _tmpLocalizacao;
        _tmpLocalizacao = _cursor.getString(_cursorIndexOfLocalizacao);
        _result.setLocalizacao(_tmpLocalizacao);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _result.setTAGID(_tmpTAGID);
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
  public List<CadastroEquipamentos> GetCadastroEquipamentos() {
    final String _sql = "SELECT * FROM CadastroEquipamentos WHERE Status != 'Inativo'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfTraceNumber = _cursor.getColumnIndexOrThrow("TraceNumber");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfModeloEquipamentoItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloEquipamentoItemIdOriginal");
      final int _cursorIndexOfLocalizacao = _cursor.getColumnIndexOrThrow("Localizacao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final List<CadastroEquipamentos> _result = new ArrayList<CadastroEquipamentos>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CadastroEquipamentos _item;
        _item = new CadastroEquipamentos();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _item.setRowVersion(_tmpRowVersion);
        final String _tmpTraceNumber;
        _tmpTraceNumber = _cursor.getString(_cursorIndexOfTraceNumber);
        _item.setTraceNumber(_tmpTraceNumber);
        final Date _tmpDataCadastro;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataCadastro);
        _tmpDataCadastro = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataCadastro(_tmpDataCadastro);
        final Date _tmpDataFabricacao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataFabricacao(_tmpDataFabricacao);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final int _tmpModeloEquipamentoItemIdOriginal;
        _tmpModeloEquipamentoItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloEquipamentoItemIdOriginal);
        _item.setModeloEquipamentoItemIdOriginal(_tmpModeloEquipamentoItemIdOriginal);
        final String _tmpLocalizacao;
        _tmpLocalizacao = _cursor.getString(_cursorIndexOfLocalizacao);
        _item.setLocalizacao(_tmpLocalizacao);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _item.setTAGID(_tmpTAGID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CadastroEquipamentos> GetCadastroEquipamentosFilter(String qryFilter) {
    final String _sql = "SELECT * FROM CadastroEquipamentos as ce INNER JOIN ModeloEquipamentos as me ON ce.ModeloEquipamentoItemIdOriginal = me.IdOriginal WHERE Status != 'Inativo' AND (me.Modelo like ? || ce.TraceNumber like ?)";
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
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfTraceNumber = _cursor.getColumnIndexOrThrow("TraceNumber");
      final int _cursorIndexOfDataCadastro = _cursor.getColumnIndexOrThrow("DataCadastro");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfModeloEquipamentoItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloEquipamentoItemIdOriginal");
      final int _cursorIndexOfLocalizacao = _cursor.getColumnIndexOrThrow("Localizacao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfId_1 = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal_1 = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion_1 = _cursor.getColumnIndexOrThrow("RowVersion");
      final List<CadastroEquipamentos> _result = new ArrayList<CadastroEquipamentos>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CadastroEquipamentos _item;
        _item = new CadastroEquipamentos();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _item.setRowVersion(_tmpRowVersion);
        final String _tmpTraceNumber;
        _tmpTraceNumber = _cursor.getString(_cursorIndexOfTraceNumber);
        _item.setTraceNumber(_tmpTraceNumber);
        final Date _tmpDataCadastro;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataCadastro);
        _tmpDataCadastro = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataCadastro(_tmpDataCadastro);
        final Date _tmpDataFabricacao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataFabricacao(_tmpDataFabricacao);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final int _tmpModeloEquipamentoItemIdOriginal;
        _tmpModeloEquipamentoItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloEquipamentoItemIdOriginal);
        _item.setModeloEquipamentoItemIdOriginal(_tmpModeloEquipamentoItemIdOriginal);
        final String _tmpLocalizacao;
        _tmpLocalizacao = _cursor.getString(_cursorIndexOfLocalizacao);
        _item.setLocalizacao(_tmpLocalizacao);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _item.setTAGID(_tmpTAGID);
        final int _tmpId_1;
        _tmpId_1 = _cursor.getInt(_cursorIndexOfId_1);
        _item.setId(_tmpId_1);
        final int _tmpIdOriginal_1;
        _tmpIdOriginal_1 = _cursor.getInt(_cursorIndexOfIdOriginal_1);
        _item.setIdOriginal(_tmpIdOriginal_1);
        final String _tmpRowVersion_1;
        _tmpRowVersion_1 = _cursor.getString(_cursorIndexOfRowVersion_1);
        _item.setRowVersion(_tmpRowVersion_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
