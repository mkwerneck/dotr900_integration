package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class ServicosAdicionaisDAO_Impl implements ServicosAdicionaisDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfServicosAdicionais;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfServicosAdicionais;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfServicosAdicionais;

  public ServicosAdicionaisDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServicosAdicionais = new EntityInsertionAdapter<ServicosAdicionais>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ServicosAdicionais`(`Id`,`IdOriginal`,`RowVersion`,`Servico`,`Descricao`,`Modalidade`,`FlagObrigatorio`,`FlagAtivo`,`TarefaItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServicosAdicionais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getServico() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getServico());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricao());
        }
        if (value.getModalidade() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getModalidade());
        }
        final Integer _tmp;
        _tmp = value.getFlagObrigatorio() == null ? null : (value.getFlagObrigatorio() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagAtivo() == null ? null : (value.getFlagAtivo() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_1);
        }
        stmt.bindLong(9, value.getTarefaItemIdOriginal());
      }
    };
    this.__deletionAdapterOfServicosAdicionais = new EntityDeletionOrUpdateAdapter<ServicosAdicionais>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ServicosAdicionais` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServicosAdicionais value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfServicosAdicionais = new EntityDeletionOrUpdateAdapter<ServicosAdicionais>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ServicosAdicionais` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Servico` = ?,`Descricao` = ?,`Modalidade` = ?,`FlagObrigatorio` = ?,`FlagAtivo` = ?,`TarefaItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServicosAdicionais value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getServico() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getServico());
        }
        if (value.getDescricao() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescricao());
        }
        if (value.getModalidade() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getModalidade());
        }
        final Integer _tmp;
        _tmp = value.getFlagObrigatorio() == null ? null : (value.getFlagObrigatorio() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagAtivo() == null ? null : (value.getFlagAtivo() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_1);
        }
        stmt.bindLong(9, value.getTarefaItemIdOriginal());
        stmt.bindLong(10, value.getId());
      }
    };
  }

  @Override
  public void Create(ServicosAdicionais servicosAdicionais) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfServicosAdicionais.insert(servicosAdicionais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ServicosAdicionais servicosAdicionais) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfServicosAdicionais.handle(servicosAdicionais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ServicosAdicionais servicosAdicionais) {
    __db.beginTransaction();
    try {
      __updateAdapterOfServicosAdicionais.handle(servicosAdicionais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ServicosAdicionais ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ServicosAdicionais GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ServicosAdicionais WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfServico = _cursor.getColumnIndexOrThrow("Servico");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfModalidade = _cursor.getColumnIndexOrThrow("Modalidade");
      final int _cursorIndexOfFlagObrigatorio = _cursor.getColumnIndexOrThrow("FlagObrigatorio");
      final int _cursorIndexOfFlagAtivo = _cursor.getColumnIndexOrThrow("FlagAtivo");
      final int _cursorIndexOfTarefaItemIdOriginal = _cursor.getColumnIndexOrThrow("TarefaItemIdOriginal");
      final ServicosAdicionais _result;
      if(_cursor.moveToFirst()) {
        _result = new ServicosAdicionais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpServico;
        _tmpServico = _cursor.getString(_cursorIndexOfServico);
        _result.setServico(_tmpServico);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _result.setDescricao(_tmpDescricao);
        final String _tmpModalidade;
        _tmpModalidade = _cursor.getString(_cursorIndexOfModalidade);
        _result.setModalidade(_tmpModalidade);
        final Boolean _tmpFlagObrigatorio;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfFlagObrigatorio)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfFlagObrigatorio);
        }
        _tmpFlagObrigatorio = _tmp == null ? null : _tmp != 0;
        _result.setFlagObrigatorio(_tmpFlagObrigatorio);
        final Boolean _tmpFlagAtivo;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFlagAtivo)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfFlagAtivo);
        }
        _tmpFlagAtivo = _tmp_1 == null ? null : _tmp_1 != 0;
        _result.setFlagAtivo(_tmpFlagAtivo);
        final int _tmpTarefaItemIdOriginal;
        _tmpTarefaItemIdOriginal = _cursor.getInt(_cursorIndexOfTarefaItemIdOriginal);
        _result.setTarefaItemIdOriginal(_tmpTarefaItemIdOriginal);
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
  public List<ServicosAdicionais> GetByIdOriginalTarefa(int qryIdOriginalTarefa) {
    final String _sql = "SELECT * FROM ServicosAdicionais WHERE TarefaItemIdOriginal like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginalTarefa);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfServico = _cursor.getColumnIndexOrThrow("Servico");
      final int _cursorIndexOfDescricao = _cursor.getColumnIndexOrThrow("Descricao");
      final int _cursorIndexOfModalidade = _cursor.getColumnIndexOrThrow("Modalidade");
      final int _cursorIndexOfFlagObrigatorio = _cursor.getColumnIndexOrThrow("FlagObrigatorio");
      final int _cursorIndexOfFlagAtivo = _cursor.getColumnIndexOrThrow("FlagAtivo");
      final int _cursorIndexOfTarefaItemIdOriginal = _cursor.getColumnIndexOrThrow("TarefaItemIdOriginal");
      final List<ServicosAdicionais> _result = new ArrayList<ServicosAdicionais>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ServicosAdicionais _item;
        _item = new ServicosAdicionais();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _item.setRowVersion(_tmpRowVersion);
        final String _tmpServico;
        _tmpServico = _cursor.getString(_cursorIndexOfServico);
        _item.setServico(_tmpServico);
        final String _tmpDescricao;
        _tmpDescricao = _cursor.getString(_cursorIndexOfDescricao);
        _item.setDescricao(_tmpDescricao);
        final String _tmpModalidade;
        _tmpModalidade = _cursor.getString(_cursorIndexOfModalidade);
        _item.setModalidade(_tmpModalidade);
        final Boolean _tmpFlagObrigatorio;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfFlagObrigatorio)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfFlagObrigatorio);
        }
        _tmpFlagObrigatorio = _tmp == null ? null : _tmp != 0;
        _item.setFlagObrigatorio(_tmpFlagObrigatorio);
        final Boolean _tmpFlagAtivo;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFlagAtivo)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfFlagAtivo);
        }
        _tmpFlagAtivo = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setFlagAtivo(_tmpFlagAtivo);
        final int _tmpTarefaItemIdOriginal;
        _tmpTarefaItemIdOriginal = _cursor.getInt(_cursorIndexOfTarefaItemIdOriginal);
        _item.setTarefaItemIdOriginal(_tmpTarefaItemIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
