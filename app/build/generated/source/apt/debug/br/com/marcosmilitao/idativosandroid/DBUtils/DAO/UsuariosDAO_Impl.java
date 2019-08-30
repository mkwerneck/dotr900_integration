package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO_Impl implements UsuariosDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUsuarios;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUsuarios;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUsuarios;

  public UsuariosDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuarios = new EntityInsertionAdapter<Usuarios>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Usuarios`(`Id`,`IdOriginal`,`RowVersion`,`UserName`,`NomeCompleto`,`Email`,`Permissao`,`TAGID`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuarios value) {
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
        if (value.getUserName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUserName());
        }
        if (value.getNomeCompleto() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNomeCompleto());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEmail());
        }
        if (value.getPermissao() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPermissao());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTAGID());
        }
      }
    };
    this.__deletionAdapterOfUsuarios = new EntityDeletionOrUpdateAdapter<Usuarios>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Usuarios` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuarios value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUsuarios = new EntityDeletionOrUpdateAdapter<Usuarios>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Usuarios` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`UserName` = ?,`NomeCompleto` = ?,`Email` = ?,`Permissao` = ?,`TAGID` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuarios value) {
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
        if (value.getUserName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUserName());
        }
        if (value.getNomeCompleto() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNomeCompleto());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEmail());
        }
        if (value.getPermissao() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPermissao());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTAGID());
        }
        stmt.bindLong(9, value.getId());
      }
    };
  }

  @Override
  public void Create(Usuarios usuarios) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUsuarios.insert(usuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(Usuarios usuarios) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUsuarios.handle(usuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(Usuarios usuarios) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUsuarios.handle(usuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM Usuarios ORDER BY RowVersion DESC LIMIT 1 ";
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
  public Usuarios GetByIdOriginal(String qryIdOriginal) {
    final String _sql = "SELECT * FROM Usuarios WHERE IdOriginal = ? ";
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
      final int _cursorIndexOfUserName = _cursor.getColumnIndexOrThrow("UserName");
      final int _cursorIndexOfNomeCompleto = _cursor.getColumnIndexOrThrow("NomeCompleto");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("Email");
      final int _cursorIndexOfPermissao = _cursor.getColumnIndexOrThrow("Permissao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Usuarios _result;
      if(_cursor.moveToFirst()) {
        _result = new Usuarios();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpUserName;
        _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        _result.setUserName(_tmpUserName);
        final String _tmpNomeCompleto;
        _tmpNomeCompleto = _cursor.getString(_cursorIndexOfNomeCompleto);
        _result.setNomeCompleto(_tmpNomeCompleto);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _result.setEmail(_tmpEmail);
        final String _tmpPermissao;
        _tmpPermissao = _cursor.getString(_cursorIndexOfPermissao);
        _result.setPermissao(_tmpPermissao);
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
  public Usuarios GetByTAGID(String qryTAGID) {
    final String _sql = "SELECT * FROM Usuarios WHERE TAGID = ?";
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
      final int _cursorIndexOfUserName = _cursor.getColumnIndexOrThrow("UserName");
      final int _cursorIndexOfNomeCompleto = _cursor.getColumnIndexOrThrow("NomeCompleto");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("Email");
      final int _cursorIndexOfPermissao = _cursor.getColumnIndexOrThrow("Permissao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Usuarios _result;
      if(_cursor.moveToFirst()) {
        _result = new Usuarios();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpUserName;
        _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        _result.setUserName(_tmpUserName);
        final String _tmpNomeCompleto;
        _tmpNomeCompleto = _cursor.getString(_cursorIndexOfNomeCompleto);
        _result.setNomeCompleto(_tmpNomeCompleto);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _result.setEmail(_tmpEmail);
        final String _tmpPermissao;
        _tmpPermissao = _cursor.getString(_cursorIndexOfPermissao);
        _result.setPermissao(_tmpPermissao);
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
  public List<String> GetAllRecords() {
    final String _sql = "SELECT NomeCompleto FROM Usuarios";
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
  public Usuarios GetByUsername(String qryUsername) {
    final String _sql = "SELECT * FROM Usuarios WHERE UserName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryUsername == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryUsername);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfUserName = _cursor.getColumnIndexOrThrow("UserName");
      final int _cursorIndexOfNomeCompleto = _cursor.getColumnIndexOrThrow("NomeCompleto");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("Email");
      final int _cursorIndexOfPermissao = _cursor.getColumnIndexOrThrow("Permissao");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Usuarios _result;
      if(_cursor.moveToFirst()) {
        _result = new Usuarios();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpUserName;
        _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        _result.setUserName(_tmpUserName);
        final String _tmpNomeCompleto;
        _tmpNomeCompleto = _cursor.getString(_cursorIndexOfNomeCompleto);
        _result.setNomeCompleto(_tmpNomeCompleto);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _result.setEmail(_tmpEmail);
        final String _tmpPermissao;
        _tmpPermissao = _cursor.getString(_cursorIndexOfPermissao);
        _result.setPermissao(_tmpPermissao);
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
}
