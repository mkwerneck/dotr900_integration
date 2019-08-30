package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.POJO.PosicaoCF;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class PosicoesDAO_Impl implements PosicoesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPosicoes;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPosicoes;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPosicoes;

  public PosicoesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPosicoes = new EntityInsertionAdapter<Posicoes>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Posicoes`(`Id`,`IdOriginal`,`RowVersion`,`Codigo`,`Descricao`,`Almoxarifado`,`TAGID`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Posicoes value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getCodigo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCodigo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricao());
        }
        if (value.getAlmoxarifado() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAlmoxarifado());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTAGID());
        }
      }
    };
    this.__deletionAdapterOfPosicoes = new EntityDeletionOrUpdateAdapter<Posicoes>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Posicoes` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Posicoes value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfPosicoes = new EntityDeletionOrUpdateAdapter<Posicoes>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Posicoes` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Codigo` = ?,`Descricao` = ?,`Almoxarifado` = ?,`TAGID` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Posicoes value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getCodigo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCodigo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricao());
        }
        if (value.getAlmoxarifado() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAlmoxarifado());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTAGID());
        }
        stmt.bindLong(8, value.getId());
      }
    };
  }

  @Override
  public void Create(Posicoes posicao) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPosicoes.insert(posicao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(Posicoes posicao) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPosicoes.handle(posicao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(Posicoes posicao) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPosicoes.handle(posicao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM Posicoes ORDER BY RowVersion DESC LIMIT 1 ";
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
  public Posicoes GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM Posicoes WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfCodigo = _cursor.getColumnIndexOrThrow("Codigo");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfAlmoxarifado = _cursor.getColumnIndexOrThrow("Almoxarifado");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Posicoes _result;
      if(_cursor.moveToFirst()) {
        _result = new Posicoes();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpCodigo;
        _tmpCodigo = _cursor.getString(_cursorIndexOfCodigo);
        _result.setCodigo(_tmpCodigo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final String _tmpAlmoxarifado;
        _tmpAlmoxarifado = _cursor.getString(_cursorIndexOfAlmoxarifado);
        _result.setAlmoxarifado(_tmpAlmoxarifado);
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
  public List<String> GetPosicoesByAlmoxarifado(String qryCodigoAlmoxarifado) {
    final String _sql = "SELECT Codigo FROM Posicoes WHERE Almoxarifado = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCodigoAlmoxarifado == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryCodigoAlmoxarifado);
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
  public Posicoes GetByTAGID(String qryTAGID) {
    final String _sql = "SELECT * FROM Posicoes WHERE TAGID = ?";
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
      final int _cursorIndexOfCodigo = _cursor.getColumnIndexOrThrow("Codigo");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfAlmoxarifado = _cursor.getColumnIndexOrThrow("Almoxarifado");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Posicoes _result;
      if(_cursor.moveToFirst()) {
        _result = new Posicoes();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpCodigo;
        _tmpCodigo = _cursor.getString(_cursorIndexOfCodigo);
        _result.setCodigo(_tmpCodigo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final String _tmpAlmoxarifado;
        _tmpAlmoxarifado = _cursor.getString(_cursorIndexOfAlmoxarifado);
        _result.setAlmoxarifado(_tmpAlmoxarifado);
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
  public List<String> GetAlmoxarifados() {
    final String _sql = "SELECT DISTINCT Almoxarifado FROM Posicoes";
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
  public Posicoes GetByCodPosicao(String qryCodigo) {
    final String _sql = "SELECT * FROM Posicoes WHERE Codigo like ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCodigo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryCodigo);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfCodigo = _cursor.getColumnIndexOrThrow("Codigo");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfAlmoxarifado = _cursor.getColumnIndexOrThrow("Almoxarifado");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final Posicoes _result;
      if(_cursor.moveToFirst()) {
        _result = new Posicoes();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpCodigo;
        _tmpCodigo = _cursor.getString(_cursorIndexOfCodigo);
        _result.setCodigo(_tmpCodigo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final String _tmpAlmoxarifado;
        _tmpAlmoxarifado = _cursor.getString(_cursorIndexOfAlmoxarifado);
        _result.setAlmoxarifado(_tmpAlmoxarifado);
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
  public String GetAlmoxarifadoByPosicao(String qryTAGIDPosicao) {
    final String _sql = "SELECT Almoxarifado FROM Posicoes WHERE TAGID like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryTAGIDPosicao == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryTAGIDPosicao);
    }
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
  public String GetTAGIDByCodPosicao(String qryCodigo) {
    final String _sql = "SELECT TAGID FROM Posicoes WHERE Codigo like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCodigo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryCodigo);
    }
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
  public String GetCodigoByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT Codigo FROM Posicoes WHERE IdOriginal like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
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
  public List<PosicaoCF> GetAllPosicoesCustomAdapter() {
    final String _sql = "SELECT IdOriginal as idOriginal, Codigo as codPosicao, Descricao as descPosicao FROM Posicoes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("idOriginal");
      final int _cursorIndexOfCodPosicao = _cursor.getColumnIndexOrThrow("codPosicao");
      final int _cursorIndexOfDescPosicao = _cursor.getColumnIndexOrThrow("descPosicao");
      final List<PosicaoCF> _result = new ArrayList<PosicaoCF>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PosicaoCF _item;
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        final String _tmpCodPosicao;
        _tmpCodPosicao = _cursor.getString(_cursorIndexOfCodPosicao);
        final String _tmpDescPosicao;
        _tmpDescPosicao = _cursor.getString(_cursorIndexOfDescPosicao);
        _item = new PosicaoCF(_tmpIdOriginal,_tmpCodPosicao,_tmpDescPosicao);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
