package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.POJO.FuncoesCU;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class GruposDAO_Impl implements GruposDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGrupos;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfGrupos;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfGrupos;

  public GruposDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGrupos = new EntityInsertionAdapter<Grupos>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Grupos`(`Id`,`IdOriginal`,`RowVersion`,`Titulo`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Grupos value) {
        stmt.bindLong(1, value.getId());
        if (value.getIdOriginal() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdOriginal());
        }
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getTitulo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTitulo());
        }
      }
    };
    this.__deletionAdapterOfGrupos = new EntityDeletionOrUpdateAdapter<Grupos>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Grupos` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Grupos value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfGrupos = new EntityDeletionOrUpdateAdapter<Grupos>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Grupos` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Titulo` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Grupos value) {
        stmt.bindLong(1, value.getId());
        if (value.getIdOriginal() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdOriginal());
        }
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getTitulo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTitulo());
        }
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void Create(Grupos grupos) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGrupos.insert(grupos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(Grupos grupos) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfGrupos.handle(grupos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(Grupos grupos) {
    __db.beginTransaction();
    try {
      __updateAdapterOfGrupos.handle(grupos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM Grupos ORDER BY RowVersion DESC LIMIT 1 ";
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
  public Grupos GetByIdOriginal(String qryIdOriginal) {
    final String _sql = "SELECT * FROM Grupos WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryIdOriginal == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryIdOriginal);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfTitulo = _cursor.getColumnIndexOrThrow("Titulo");
      final Grupos _result;
      if(_cursor.moveToFirst()) {
        _result = new Grupos();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpTitulo;
        _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
        _result.setTitulo(_tmpTitulo);
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
  public List<String> GetAllTitulos() {
    final String _sql = "SELECT Titulo FROM Grupos";
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
  public List<FuncoesCU> GetSpinnerItems() {
    final String _sql = "SELECT IdOriginal, Titulo as Role FROM Grupos";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRole = _cursor.getColumnIndexOrThrow("Role");
      final List<FuncoesCU> _result = new ArrayList<FuncoesCU>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FuncoesCU _item;
        _item = new FuncoesCU();
        _item.IdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _item.Role = _cursor.getString(_cursorIndexOfRole);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FuncoesCU GetSpinnerItem(String qryIdOriginal) {
    final String _sql = "SELECT IdOriginal, Titulo as Role FROM Grupos WHERE IdOriginal = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryIdOriginal == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryIdOriginal);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRole = _cursor.getColumnIndexOrThrow("Role");
      final FuncoesCU _result;
      if(_cursor.moveToFirst()) {
        _result = new FuncoesCU();
        _result.IdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _result.Role = _cursor.getString(_cursorIndexOfRole);
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
