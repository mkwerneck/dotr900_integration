package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao;
import java.lang.Override;
import java.lang.String;

public class ParametrosPadraoDAO_Impl implements ParametrosPadraoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfParametrosPadrao;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfParametrosPadrao;

  public ParametrosPadraoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfParametrosPadrao = new EntityInsertionAdapter<ParametrosPadrao>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ParametrosPadrao`(`Id`,`IdOriginal`,`RowVersion`,`CodAlmoxarifado`,`SetorProprietario`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ParametrosPadrao value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getCodAlmoxarifado() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCodAlmoxarifado());
        }
        if (value.getSetorProprietario() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSetorProprietario());
        }
      }
    };
    this.__updateAdapterOfParametrosPadrao = new EntityDeletionOrUpdateAdapter<ParametrosPadrao>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ParametrosPadrao` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`CodAlmoxarifado` = ?,`SetorProprietario` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ParametrosPadrao value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getCodAlmoxarifado() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCodAlmoxarifado());
        }
        if (value.getSetorProprietario() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSetorProprietario());
        }
        stmt.bindLong(6, value.getId());
      }
    };
  }

  @Override
  public void Create(ParametrosPadrao parametrosPadrao) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfParametrosPadrao.insert(parametrosPadrao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ParametrosPadrao parametrosPadrao) {
    __db.beginTransaction();
    try {
      __updateAdapterOfParametrosPadrao.handle(parametrosPadrao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ParametrosPadrao ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ParametrosPadrao GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ParametrosPadrao WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfCodAlmoxarifado = _cursor.getColumnIndexOrThrow("CodAlmoxarifado");
      final int _cursorIndexOfSetorProprietario = _cursor.getColumnIndexOrThrow("SetorProprietario");
      final ParametrosPadrao _result;
      if(_cursor.moveToFirst()) {
        _result = new ParametrosPadrao();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpCodAlmoxarifado;
        _tmpCodAlmoxarifado = _cursor.getString(_cursorIndexOfCodAlmoxarifado);
        _result.setCodAlmoxarifado(_tmpCodAlmoxarifado);
        final String _tmpSetorProprietario;
        _tmpSetorProprietario = _cursor.getString(_cursorIndexOfSetorProprietario);
        _result.setSetorProprietario(_tmpSetorProprietario);
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
  public String GetCodigoAlmoxarifado() {
    final String _sql = "SELECT CodAlmoxarifado FROM ParametrosPadrao LIMIT 1";
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
  public String GetProprietarioPadrao() {
    final String _sql = "SELECT SetorProprietario FROM PARAMETROSPADRAO LIMIT 1";
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
}
