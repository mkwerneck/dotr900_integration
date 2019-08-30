package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroMateriaisItensDAO_Impl implements CadastroMateriaisItensDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCadastroMateriaisItens;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCadastroMateriaisItens;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCadastroMateriaisItens;

  public CadastroMateriaisItensDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCadastroMateriaisItens = new EntityInsertionAdapter<CadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CadastroMateriaisItens`(`Id`,`IdOriginal`,`RowVersion`,`NumSerie`,`Patrimonio`,`Quantidade`,`DataCadastro`,`DataFabricacao`,`DataValidade`,`CadastroMateriaisItemIdOriginal`,`ModeloMateriaisItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriaisItens value) {
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
        stmt.bindLong(10, value.getCadastroMateriaisItemIdOriginal());
        stmt.bindLong(11, value.getModeloMateriaisItemIdOriginal());
      }
    };
    this.__deletionAdapterOfCadastroMateriaisItens = new EntityDeletionOrUpdateAdapter<CadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CadastroMateriaisItens` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriaisItens value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCadastroMateriaisItens = new EntityDeletionOrUpdateAdapter<CadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CadastroMateriaisItens` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`NumSerie` = ?,`Patrimonio` = ?,`Quantidade` = ?,`DataCadastro` = ?,`DataFabricacao` = ?,`DataValidade` = ?,`CadastroMateriaisItemIdOriginal` = ?,`ModeloMateriaisItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CadastroMateriaisItens value) {
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
        stmt.bindLong(10, value.getCadastroMateriaisItemIdOriginal());
        stmt.bindLong(11, value.getModeloMateriaisItemIdOriginal());
        stmt.bindLong(12, value.getId());
      }
    };
  }

  @Override
  public void Create(CadastroMateriaisItens cadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCadastroMateriaisItens.insert(cadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(CadastroMateriaisItens cadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCadastroMateriaisItens.handle(cadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(CadastroMateriaisItens cadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCadastroMateriaisItens.handle(cadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM CadastroMateriaisItens ORDER BY RowVersion DESC LIMIT 1 ";
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
  public CadastroMateriaisItens GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM CadastroMateriaisItens WHERE IdOriginal = ? ";
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
      final int _cursorIndexOfCadastroMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("CadastroMateriaisItemIdOriginal");
      final int _cursorIndexOfModeloMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemIdOriginal");
      final CadastroMateriaisItens _result;
      if(_cursor.moveToFirst()) {
        _result = new CadastroMateriaisItens();
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
        final int _tmpCadastroMateriaisItemIdOriginal;
        _tmpCadastroMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfCadastroMateriaisItemIdOriginal);
        _result.setCadastroMateriaisItemIdOriginal(_tmpCadastroMateriaisItemIdOriginal);
        final int _tmpModeloMateriaisItemIdOriginal;
        _tmpModeloMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloMateriaisItemIdOriginal);
        _result.setModeloMateriaisItemIdOriginal(_tmpModeloMateriaisItemIdOriginal);
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
  public List<CadastroMateriaisItens> GetByCadastroMaterias(Integer qryCadastroMateriaisIdOriginal) {
    final String _sql = "SELECT * FROM CadastroMateriaisItens WHERE CadastroMateriaisItemIdOriginal like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCadastroMateriaisIdOriginal == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, qryCadastroMateriaisIdOriginal);
    }
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
      final int _cursorIndexOfCadastroMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("CadastroMateriaisItemIdOriginal");
      final int _cursorIndexOfModeloMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemIdOriginal");
      final List<CadastroMateriaisItens> _result = new ArrayList<CadastroMateriaisItens>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CadastroMateriaisItens _item;
        _item = new CadastroMateriaisItens();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _item.setRowVersion(_tmpRowVersion);
        final String _tmpNumSerie;
        _tmpNumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _item.setNumSerie(_tmpNumSerie);
        final String _tmpPatrimonio;
        _tmpPatrimonio = _cursor.getString(_cursorIndexOfPatrimonio);
        _item.setPatrimonio(_tmpPatrimonio);
        final int _tmpQuantidade;
        _tmpQuantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _item.setQuantidade(_tmpQuantidade);
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
        final Date _tmpDataValidade;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataValidade);
        _tmpDataValidade = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataValidade(_tmpDataValidade);
        final int _tmpCadastroMateriaisItemIdOriginal;
        _tmpCadastroMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfCadastroMateriaisItemIdOriginal);
        _item.setCadastroMateriaisItemIdOriginal(_tmpCadastroMateriaisItemIdOriginal);
        final int _tmpModeloMateriaisItemIdOriginal;
        _tmpModeloMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloMateriaisItemIdOriginal);
        _item.setModeloMateriaisItemIdOriginal(_tmpModeloMateriaisItemIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
