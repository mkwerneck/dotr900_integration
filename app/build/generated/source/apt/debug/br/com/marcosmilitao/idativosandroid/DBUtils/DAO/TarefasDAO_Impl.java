package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

public class TarefasDAO_Impl implements TarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTarefas;

  public TarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTarefas = new EntityInsertionAdapter<Tarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Tarefas`(`Id`,`IdOriginal`,`RowVersion`,`Codigo`,`Titulo`,`Descricao`,`FlagDependenciaServico`,`FlagDependenciaMaterial`,`CategoriaEquipamentos`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tarefas value) {
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
        if (value.getTitulo() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTitulo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDescricao());
        }
        final Integer _tmp;
        _tmp = value.getFlagDependenciaServico() == null ? null : (value.getFlagDependenciaServico() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagDependenciaMaterial() == null ? null : (value.getFlagDependenciaMaterial() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_1);
        }
        if (value.getCategoriaEquipamentos() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCategoriaEquipamentos());
        }
      }
    };
    this.__deletionAdapterOfTarefas = new EntityDeletionOrUpdateAdapter<Tarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Tarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTarefas = new EntityDeletionOrUpdateAdapter<Tarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Tarefas` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Codigo` = ?,`Titulo` = ?,`Descricao` = ?,`FlagDependenciaServico` = ?,`FlagDependenciaMaterial` = ?,`CategoriaEquipamentos` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tarefas value) {
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
        if (value.getTitulo() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTitulo());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDescricao());
        }
        final Integer _tmp;
        _tmp = value.getFlagDependenciaServico() == null ? null : (value.getFlagDependenciaServico() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagDependenciaMaterial() == null ? null : (value.getFlagDependenciaMaterial() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_1);
        }
        if (value.getCategoriaEquipamentos() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCategoriaEquipamentos());
        }
        stmt.bindLong(10, value.getId());
      }
    };
  }

  @Override
  public void Create(Tarefas tarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTarefas.insert(tarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(Tarefas tarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTarefas.handle(tarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(Tarefas tarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTarefas.handle(tarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM Tarefas ORDER BY RowVersion DESC LIMIT 1 ";
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
  public Tarefas GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM Tarefas WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfCodigo = _cursor.getColumnIndexOrThrow("Codigo");
      final int _cursorIndexOfTitulo = _cursor.getColumnIndexOrThrow("Titulo");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfFlagDependenciaServico = _cursor.getColumnIndexOrThrow("FlagDependenciaServico");
      final int _cursorIndexOfFlagDependenciaMaterial = _cursor.getColumnIndexOrThrow("FlagDependenciaMaterial");
      final int _cursorIndexOfCategoriaEquipamentos = _cursor.getColumnIndexOrThrow("CategoriaEquipamentos");
      final Tarefas _result;
      if(_cursor.moveToFirst()) {
        _result = new Tarefas();
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
        final String _tmpTitulo;
        _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
        _result.setTitulo(_tmpTitulo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final Boolean _tmpFlagDependenciaServico;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfFlagDependenciaServico)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfFlagDependenciaServico);
        }
        _tmpFlagDependenciaServico = _tmp == null ? null : _tmp != 0;
        _result.setFlagDependenciaServico(_tmpFlagDependenciaServico);
        final Boolean _tmpFlagDependenciaMaterial;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFlagDependenciaMaterial)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfFlagDependenciaMaterial);
        }
        _tmpFlagDependenciaMaterial = _tmp_1 == null ? null : _tmp_1 != 0;
        _result.setFlagDependenciaMaterial(_tmpFlagDependenciaMaterial);
        final String _tmpCategoriaEquipamentos;
        _tmpCategoriaEquipamentos = _cursor.getString(_cursorIndexOfCategoriaEquipamentos);
        _result.setCategoriaEquipamentos(_tmpCategoriaEquipamentos);
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
  public Tarefas GetFirstByCategoria(String qryCategoria) {
    final String _sql = "SELECT * FROM Tarefas WHERE CategoriaEquipamentos like ? ORDER BY Codigo LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryCategoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryCategoria);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfCodigo = _cursor.getColumnIndexOrThrow("Codigo");
      final int _cursorIndexOfTitulo = _cursor.getColumnIndexOrThrow("Titulo");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfFlagDependenciaServico = _cursor.getColumnIndexOrThrow("FlagDependenciaServico");
      final int _cursorIndexOfFlagDependenciaMaterial = _cursor.getColumnIndexOrThrow("FlagDependenciaMaterial");
      final int _cursorIndexOfCategoriaEquipamentos = _cursor.getColumnIndexOrThrow("CategoriaEquipamentos");
      final Tarefas _result;
      if(_cursor.moveToFirst()) {
        _result = new Tarefas();
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
        final String _tmpTitulo;
        _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
        _result.setTitulo(_tmpTitulo);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final Boolean _tmpFlagDependenciaServico;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfFlagDependenciaServico)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfFlagDependenciaServico);
        }
        _tmpFlagDependenciaServico = _tmp == null ? null : _tmp != 0;
        _result.setFlagDependenciaServico(_tmpFlagDependenciaServico);
        final Boolean _tmpFlagDependenciaMaterial;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFlagDependenciaMaterial)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfFlagDependenciaMaterial);
        }
        _tmpFlagDependenciaMaterial = _tmp_1 == null ? null : _tmp_1 != 0;
        _result.setFlagDependenciaMaterial(_tmpFlagDependenciaMaterial);
        final String _tmpCategoriaEquipamentos;
        _tmpCategoriaEquipamentos = _cursor.getString(_cursorIndexOfCategoriaEquipamentos);
        _result.setCategoriaEquipamentos(_tmpCategoriaEquipamentos);
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
