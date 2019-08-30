package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import java.lang.Override;
import java.lang.String;

public class ModeloEquipamentosDAO_Impl implements ModeloEquipamentosDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfModeloEquipamentos;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfModeloEquipamentos;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfModeloEquipamentos;

  public ModeloEquipamentosDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfModeloEquipamentos = new EntityInsertionAdapter<ModeloEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ModeloEquipamentos`(`Id`,`IdOriginal`,`RowVersion`,`Modelo`,`DescricaoTecnica`,`PartNumber`,`Fabricante`,`Categoria`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloEquipamentos value) {
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
        if (value.getDescricaoTecnica() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricaoTecnica());
        }
        if (value.getPartNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPartNumber());
        }
        if (value.getFabricante() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getFabricante());
        }
        if (value.getCategoria() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCategoria());
        }
      }
    };
    this.__deletionAdapterOfModeloEquipamentos = new EntityDeletionOrUpdateAdapter<ModeloEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ModeloEquipamentos` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloEquipamentos value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfModeloEquipamentos = new EntityDeletionOrUpdateAdapter<ModeloEquipamentos>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ModeloEquipamentos` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Modelo` = ?,`DescricaoTecnica` = ?,`PartNumber` = ?,`Fabricante` = ?,`Categoria` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ModeloEquipamentos value) {
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
        if (value.getDescricaoTecnica() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricaoTecnica());
        }
        if (value.getPartNumber() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPartNumber());
        }
        if (value.getFabricante() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getFabricante());
        }
        if (value.getCategoria() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCategoria());
        }
        stmt.bindLong(9, value.getId());
      }
    };
  }

  @Override
  public void Create(ModeloEquipamentos modeloEquipamentos) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfModeloEquipamentos.insert(modeloEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ModeloEquipamentos modeloEquipamentos) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfModeloEquipamentos.handle(modeloEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ModeloEquipamentos modeloEquipamentos) {
    __db.beginTransaction();
    try {
      __updateAdapterOfModeloEquipamentos.handle(modeloEquipamentos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ModeloEquipamentos ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ModeloEquipamentos GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ModeloEquipamentos WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfModelo = _cursor.getColumnIndexOrThrow("Modelo");
      final int _cursorIndexOfDescricaoTecnica = _cursor.getColumnIndexOrThrow("DescricaoTecnica");
      final int _cursorIndexOfPartNumber = _cursor.getColumnIndexOrThrow("PartNumber");
      final int _cursorIndexOfFabricante = _cursor.getColumnIndexOrThrow("Fabricante");
      final int _cursorIndexOfCategoria = _cursor.getColumnIndexOrThrow("Categoria");
      final ModeloEquipamentos _result;
      if(_cursor.moveToFirst()) {
        _result = new ModeloEquipamentos();
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
        final String _tmpDescricaoTecnica;
        _tmpDescricaoTecnica = _cursor.getString(_cursorIndexOfDescricaoTecnica);
        _result.setDescricaoTecnica(_tmpDescricaoTecnica);
        final String _tmpPartNumber;
        _tmpPartNumber = _cursor.getString(_cursorIndexOfPartNumber);
        _result.setPartNumber(_tmpPartNumber);
        final String _tmpFabricante;
        _tmpFabricante = _cursor.getString(_cursorIndexOfFabricante);
        _result.setFabricante(_tmpFabricante);
        final String _tmpCategoria;
        _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
        _result.setCategoria(_tmpCategoria);
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
