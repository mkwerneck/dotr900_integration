package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class InventarioPlanejadoDAO_Impl implements InventarioPlanejadoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfInventarioPlanejado;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfInventarioPlanejado;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfInventarioPlanejado;

  public InventarioPlanejadoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInventarioPlanejado = new EntityInsertionAdapter<InventarioPlanejado>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `InventarioPlanejado`(`Id`,`IdOriginal`,`RowVersion`,`Descricao`,`ApplicationUserItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InventarioPlanejado value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescricao());
        }
        if (value.getApplicationUserItemIdOriginal() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getApplicationUserItemIdOriginal());
        }
      }
    };
    this.__deletionAdapterOfInventarioPlanejado = new EntityDeletionOrUpdateAdapter<InventarioPlanejado>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `InventarioPlanejado` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InventarioPlanejado value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfInventarioPlanejado = new EntityDeletionOrUpdateAdapter<InventarioPlanejado>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `InventarioPlanejado` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Descricao` = ?,`ApplicationUserItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InventarioPlanejado value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescricao());
        }
        if (value.getApplicationUserItemIdOriginal() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getApplicationUserItemIdOriginal());
        }
        stmt.bindLong(6, value.getId());
      }
    };
  }

  @Override
  public void Create(InventarioPlanejado inventarioPlanejado) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfInventarioPlanejado.insert(inventarioPlanejado);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(InventarioPlanejado inventarioPlanejado) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfInventarioPlanejado.handle(inventarioPlanejado);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(InventarioPlanejado inventarioPlanejado) {
    __db.beginTransaction();
    try {
      __updateAdapterOfInventarioPlanejado.handle(inventarioPlanejado);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM InventarioPlanejado ORDER BY RowVersion DESC LIMIT 1 ";
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
  public InventarioPlanejado GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM InventarioPlanejado WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfApplicationUserItemIdOriginal = _cursor.getColumnIndexOrThrow("ApplicationUserItemIdOriginal");
      final InventarioPlanejado _result;
      if(_cursor.moveToFirst()) {
        _result = new InventarioPlanejado();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final String _tmpApplicationUserItemIdOriginal;
        _tmpApplicationUserItemIdOriginal = _cursor.getString(_cursorIndexOfApplicationUserItemIdOriginal);
        _result.setApplicationUserItemIdOriginal(_tmpApplicationUserItemIdOriginal);
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
  public List<String> GetAllDescricoes() {
    final String _sql = "SELECT Descricao FROM InventarioPlanejado";
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

  @Override
  public Integer GetIdOriginalByDescricao(String qryDescricao) {
    final String _sql = "SELECT IdOriginal FROM InventarioPlanejado WHERE Descricao like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryDescricao == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryDescricao);
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
}
