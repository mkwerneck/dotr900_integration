package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class ProprietariosDAO_Impl implements ProprietariosDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfProprietarios;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfProprietarios;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfProprietarios;

  public ProprietariosDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProprietarios = new EntityInsertionAdapter<Proprietarios>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Proprietarios`(`Id`,`IdOriginal`,`RowVersion`,`Descricao`,`Empresa`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Proprietarios value) {
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
        if (value.getEmpresa() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmpresa());
        }
      }
    };
    this.__deletionAdapterOfProprietarios = new EntityDeletionOrUpdateAdapter<Proprietarios>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Proprietarios` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Proprietarios value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfProprietarios = new EntityDeletionOrUpdateAdapter<Proprietarios>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Proprietarios` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Descricao` = ?,`Empresa` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Proprietarios value) {
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
        if (value.getEmpresa() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmpresa());
        }
        stmt.bindLong(6, value.getId());
      }
    };
  }

  @Override
  public void Create(Proprietarios proprietarios) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfProprietarios.insert(proprietarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(Proprietarios proprietarios) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfProprietarios.handle(proprietarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(Proprietarios proprietarios) {
    __db.beginTransaction();
    try {
      __updateAdapterOfProprietarios.handle(proprietarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM Proprietarios ORDER BY RowVersion DESC LIMIT 1 ";
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
  public Proprietarios GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM Proprietarios WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfEmpresa = _cursor.getColumnIndexOrThrow("Empresa");
      final Proprietarios _result;
      if(_cursor.moveToFirst()) {
        _result = new Proprietarios();
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
        final String _tmpEmpresa;
        _tmpEmpresa = _cursor.getString(_cursorIndexOfEmpresa);
        _result.setEmpresa(_tmpEmpresa);
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
  public List<String> GetAllDescricao() {
    final String _sql = "SELECT Descricao FROM Proprietarios";
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
