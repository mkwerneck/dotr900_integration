package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaMateriaisListaTarefasDAO_Impl implements ListaMateriaisListaTarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfListaMateriaisListaTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfListaMateriaisListaTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfListaMateriaisListaTarefas;

  public ListaMateriaisListaTarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfListaMateriaisListaTarefas = new EntityInsertionAdapter<ListaMateriaisListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ListaMateriaisListaTarefas`(`Id`,`IdOriginal`,`RowVersion`,`Status`,`DataInicio`,`DataConclusao`,`Observacao`,`ListaTarefasItemIdOriginal`,`CadastroMateriaisItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaMateriaisListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataConclusao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        if (value.getObservacao() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getObservacao());
        }
        stmt.bindLong(8, value.getListaTarefasItemIdOriginal());
        stmt.bindLong(9, value.getCadastroMateriaisItemIdOriginal());
      }
    };
    this.__deletionAdapterOfListaMateriaisListaTarefas = new EntityDeletionOrUpdateAdapter<ListaMateriaisListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ListaMateriaisListaTarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaMateriaisListaTarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfListaMateriaisListaTarefas = new EntityDeletionOrUpdateAdapter<ListaMateriaisListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ListaMateriaisListaTarefas` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Status` = ?,`DataInicio` = ?,`DataConclusao` = ?,`Observacao` = ?,`ListaTarefasItemIdOriginal` = ?,`CadastroMateriaisItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaMateriaisListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getRowVersion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRowVersion());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataConclusao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        if (value.getObservacao() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getObservacao());
        }
        stmt.bindLong(8, value.getListaTarefasItemIdOriginal());
        stmt.bindLong(9, value.getCadastroMateriaisItemIdOriginal());
        stmt.bindLong(10, value.getId());
      }
    };
  }

  @Override
  public void Create(ListaMateriaisListaTarefas listaMateriaisListaTarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfListaMateriaisListaTarefas.insert(listaMateriaisListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ListaMateriaisListaTarefas listaMateriaisListaTarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfListaMateriaisListaTarefas.handle(listaMateriaisListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ListaMateriaisListaTarefas listaMateriaisListaTarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfListaMateriaisListaTarefas.handle(listaMateriaisListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ListaMateriaisListaTarefas ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ListaMateriaisListaTarefas GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ListaMateriaisListaTarefas WHERE IdOriginal = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataConclusao = _cursor.getColumnIndexOrThrow("DataConclusao");
      final int _cursorIndexOfObservacao = _cursor.getColumnIndexOrThrow("Observacao");
      final int _cursorIndexOfListaTarefasItemIdOriginal = _cursor.getColumnIndexOrThrow("ListaTarefasItemIdOriginal");
      final int _cursorIndexOfCadastroMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("CadastroMateriaisItemIdOriginal");
      final ListaMateriaisListaTarefas _result;
      if(_cursor.moveToFirst()) {
        _result = new ListaMateriaisListaTarefas();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _result.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _result.setRowVersion(_tmpRowVersion);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final Date _tmpDataInicio;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataInicio);
        _tmpDataInicio = TimeStampConverter.fromTimestamp(_tmp);
        _result.setDataInicio(_tmpDataInicio);
        final Date _tmpDataConclusao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataConclusao);
        _tmpDataConclusao = TimeStampConverter.fromTimestamp(_tmp_1);
        _result.setDataConclusao(_tmpDataConclusao);
        final String _tmpObservacao;
        _tmpObservacao = _cursor.getString(_cursorIndexOfObservacao);
        _result.setObservacao(_tmpObservacao);
        final int _tmpListaTarefasItemIdOriginal;
        _tmpListaTarefasItemIdOriginal = _cursor.getInt(_cursorIndexOfListaTarefasItemIdOriginal);
        _result.setListaTarefasItemIdOriginal(_tmpListaTarefasItemIdOriginal);
        final int _tmpCadastroMateriaisItemIdOriginal;
        _tmpCadastroMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfCadastroMateriaisItemIdOriginal);
        _result.setCadastroMateriaisItemIdOriginal(_tmpCadastroMateriaisItemIdOriginal);
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
  public List<ListaMateriaisListaTarefas> GetByIdOriginalListaTarefa(int qryIdOriginalListaTarefa) {
    final String _sql = "SELECT * FROM ListaMateriaisListaTarefas WHERE ListaTarefasItemIdOriginal = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qryIdOriginalListaTarefa);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataConclusao = _cursor.getColumnIndexOrThrow("DataConclusao");
      final int _cursorIndexOfObservacao = _cursor.getColumnIndexOrThrow("Observacao");
      final int _cursorIndexOfListaTarefasItemIdOriginal = _cursor.getColumnIndexOrThrow("ListaTarefasItemIdOriginal");
      final int _cursorIndexOfCadastroMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("CadastroMateriaisItemIdOriginal");
      final List<ListaMateriaisListaTarefas> _result = new ArrayList<ListaMateriaisListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ListaMateriaisListaTarefas _item;
        _item = new ListaMateriaisListaTarefas();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRowVersion;
        _tmpRowVersion = _cursor.getString(_cursorIndexOfRowVersion);
        _item.setRowVersion(_tmpRowVersion);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final Date _tmpDataInicio;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataInicio);
        _tmpDataInicio = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataInicio(_tmpDataInicio);
        final Date _tmpDataConclusao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataConclusao);
        _tmpDataConclusao = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataConclusao(_tmpDataConclusao);
        final String _tmpObservacao;
        _tmpObservacao = _cursor.getString(_cursorIndexOfObservacao);
        _item.setObservacao(_tmpObservacao);
        final int _tmpListaTarefasItemIdOriginal;
        _tmpListaTarefasItemIdOriginal = _cursor.getInt(_cursorIndexOfListaTarefasItemIdOriginal);
        _item.setListaTarefasItemIdOriginal(_tmpListaTarefasItemIdOriginal);
        final int _tmpCadastroMateriaisItemIdOriginal;
        _tmpCadastroMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfCadastroMateriaisItemIdOriginal);
        _item.setCadastroMateriaisItemIdOriginal(_tmpCadastroMateriaisItemIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
