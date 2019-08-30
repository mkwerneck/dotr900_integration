package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.POJO.ModeloMateriaisCF;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class ModeloMateriaisDAO_Impl implements ModeloMateriaisDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfModeloMateriais;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfModeloMateriais;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfModeloMateriais;

  public ModeloMateriaisDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfModeloMateriais = new EntityInsertionAdapter<ModeloMateriais>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ModeloMateriais`(`Id`,`IdOriginal`,`RowVersion`,`Modelo`,`IDOmni`,`PartNumber`,`DescricaoTecnica`,`Familia`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloMateriais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getModelo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getModelo());
        }
        if (value.getIDOmni() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIDOmni());
        }
        if (value.getPartNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPartNumber());
        }
        if (value.getDescricaoTecnica() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescricaoTecnica());
        }
        if (value.getFamilia() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getFamilia());
        }
      }
    };
    this.__deletionAdapterOfModeloMateriais = new EntityDeletionOrUpdateAdapter<ModeloMateriais>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ModeloMateriais` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloMateriais value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfModeloMateriais = new EntityDeletionOrUpdateAdapter<ModeloMateriais>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ModeloMateriais` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Modelo` = ?,`IDOmni` = ?,`PartNumber` = ?,`DescricaoTecnica` = ?,`Familia` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloMateriais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getModelo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getModelo());
        }
        if (value.getIDOmni() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIDOmni());
        }
        if (value.getPartNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPartNumber());
        }
        if (value.getDescricaoTecnica() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescricaoTecnica());
        }
        if (value.getFamilia() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getFamilia());
        }
        stmt.bindLong(9, value.getId());
      }
    };
  }

  @Override
  public void Create(ModeloMateriais modeloMateriais) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfModeloMateriais.insert(modeloMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ModeloMateriais modeloMateriais) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfModeloMateriais.handle(modeloMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ModeloMateriais modeloMateriais) {
    __db.beginTransaction();
    try {
      __updateAdapterOfModeloMateriais.handle(modeloMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ModeloMateriais ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ModeloMateriais GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ModeloMateriais WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("Modelo");
      final int _cursorIndexOfIDOmni = _cursor.getColumnIndexOrThrow("IDOmni");
      final int _cursorIndexOfPartNumber = _cursor.getColumnIndexOrThrow("PartNumber");
      final int _cursorIndexOfDescricaoTecnica = _cursor.getColumnIndexOrThrow("DescricaoTecnica");
      final int _cursorIndexOfFamilia = _cursor.getColumnIndexOrThrow("Familia");
      final ModeloMateriais _result;
      if(_cursor.moveToFirst()) {
        _result = new ModeloMateriais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpModelo;
        _tmpModelo = _cursor.getString(_cursorIndexOfModelo);
        _result.setModelo(_tmpModelo);
        final String _tmpIDOmni;
        _tmpIDOmni = _cursor.getString(_cursorIndexOfIDOmni);
        _result.setIDOmni(_tmpIDOmni);
        final String _tmpPartNumber;
        _tmpPartNumber = _cursor.getString(_cursorIndexOfPartNumber);
        _result.setPartNumber(_tmpPartNumber);
        final String _tmpDescricaoTecnica;
        _tmpDescricaoTecnica = _cursor.getString(_cursorIndexOfDescricaoTecnica);
        _result.setDescricaoTecnica(_tmpDescricaoTecnica);
        final String _tmpFamilia;
        _tmpFamilia = _cursor.getString(_cursorIndexOfFamilia);
        _result.setFamilia(_tmpFamilia);
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
  public ModeloMateriais GetByNumProduto(String qryNumProduto) {
    final String _sql = "SELECT * FROM ModeloMateriais WHERE IDOmni like ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryNumProduto == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryNumProduto);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("Modelo");
      final int _cursorIndexOfIDOmni = _cursor.getColumnIndexOrThrow("IDOmni");
      final int _cursorIndexOfPartNumber = _cursor.getColumnIndexOrThrow("PartNumber");
      final int _cursorIndexOfDescricaoTecnica = _cursor.getColumnIndexOrThrow("DescricaoTecnica");
      final int _cursorIndexOfFamilia = _cursor.getColumnIndexOrThrow("Familia");
      final ModeloMateriais _result;
      if(_cursor.moveToFirst()) {
        _result = new ModeloMateriais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpModelo;
        _tmpModelo = _cursor.getString(_cursorIndexOfModelo);
        _result.setModelo(_tmpModelo);
        final String _tmpIDOmni;
        _tmpIDOmni = _cursor.getString(_cursorIndexOfIDOmni);
        _result.setIDOmni(_tmpIDOmni);
        final String _tmpPartNumber;
        _tmpPartNumber = _cursor.getString(_cursorIndexOfPartNumber);
        _result.setPartNumber(_tmpPartNumber);
        final String _tmpDescricaoTecnica;
        _tmpDescricaoTecnica = _cursor.getString(_cursorIndexOfDescricaoTecnica);
        _result.setDescricaoTecnica(_tmpDescricaoTecnica);
        final String _tmpFamilia;
        _tmpFamilia = _cursor.getString(_cursorIndexOfFamilia);
        _result.setFamilia(_tmpFamilia);
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
  public List<String> GetAllNomeModelo() {
    final String _sql = "SELECT Modelo FROM ModeloMateriais";
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
  public List<ModeloMateriaisCF> GetAllModelosCustomAdapter() {
    final String _sql = "SELECT IdOriginal as idOriginal, Modelo as modelo, IDOmni as numProduto FROM ModeloMateriais";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("idOriginal");
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("modelo");
      final int _cursorIndexOfNumProduto = _cursor.getColumnIndexOrThrow("numProduto");
      final List<ModeloMateriaisCF> _result = new ArrayList<ModeloMateriaisCF>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ModeloMateriaisCF _item;
        final String _tmpModelo;
        _tmpModelo = _cursor.getString(_cursorIndexOfModelo);
        final String _tmpNumProduto;
        _tmpNumProduto = _cursor.getString(_cursorIndexOfNumProduto);
        _item = new ModeloMateriaisCF(_tmpModelo,_tmpNumProduto);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String GetNumProdutoByModelo(String qryModelo) {
    final String _sql = "SELECT IDOmni FROM ModeloMateriais WHERE Modelo like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryModelo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryModelo);
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
  public String GetModeloByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT Modelo FROM ModeloMateriais WHERE IdOriginal like ?";
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
}
