package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaServicosListaTarefasDAO_Impl implements ListaServicosListaTarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfListaServicosListaTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfListaServicosListaTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfListaServicosListaTarefas;

  public ListaServicosListaTarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfListaServicosListaTarefas = new EntityInsertionAdapter<ListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ListaServicosListaTarefas`(`Id`,`IdOriginal`,`RowVersion`,`Status`,`DataInicio`,`DataConclusao`,`Resultado`,`ListaTarefasItemIdOriginal`,`ServicoAdicinalItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaServicosListaTarefas value) {
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
        if (value.getResultado() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getResultado());
        }
        stmt.bindLong(8, value.getListaTarefasItemIdOriginal());
        stmt.bindLong(9, value.getServicoAdicinalItemIdOriginal());
      }
    };
    this.__deletionAdapterOfListaServicosListaTarefas = new EntityDeletionOrUpdateAdapter<ListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ListaServicosListaTarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaServicosListaTarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfListaServicosListaTarefas = new EntityDeletionOrUpdateAdapter<ListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ListaServicosListaTarefas` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Status` = ?,`DataInicio` = ?,`DataConclusao` = ?,`Resultado` = ?,`ListaTarefasItemIdOriginal` = ?,`ServicoAdicinalItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaServicosListaTarefas value) {
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
        if (value.getResultado() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getResultado());
        }
        stmt.bindLong(8, value.getListaTarefasItemIdOriginal());
        stmt.bindLong(9, value.getServicoAdicinalItemIdOriginal());
        stmt.bindLong(10, value.getId());
      }
    };
  }

  @Override
  public void Create(ListaServicosListaTarefas listaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfListaServicosListaTarefas.insert(listaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ListaServicosListaTarefas listaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfListaServicosListaTarefas.handle(listaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ListaServicosListaTarefas listaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfListaServicosListaTarefas.handle(listaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ListaServicosListaTarefas ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ListaServicosListaTarefas GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ListaServicosListaTarefas WHERE IdOriginal = ? ";
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
      final int _cursorIndexOfResultado = _cursor.getColumnIndexOrThrow("Resultado");
      final int _cursorIndexOfListaTarefasItemIdOriginal = _cursor.getColumnIndexOrThrow("ListaTarefasItemIdOriginal");
      final int _cursorIndexOfServicoAdicinalItemIdOriginal = _cursor.getColumnIndexOrThrow("ServicoAdicinalItemIdOriginal");
      final ListaServicosListaTarefas _result;
      if(_cursor.moveToFirst()) {
        _result = new ListaServicosListaTarefas();
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
        final String _tmpResultado;
        _tmpResultado = _cursor.getString(_cursorIndexOfResultado);
        _result.setResultado(_tmpResultado);
        final int _tmpListaTarefasItemIdOriginal;
        _tmpListaTarefasItemIdOriginal = _cursor.getInt(_cursorIndexOfListaTarefasItemIdOriginal);
        _result.setListaTarefasItemIdOriginal(_tmpListaTarefasItemIdOriginal);
        final int _tmpServicoAdicinalItemIdOriginal;
        _tmpServicoAdicinalItemIdOriginal = _cursor.getInt(_cursorIndexOfServicoAdicinalItemIdOriginal);
        _result.setServicoAdicinalItemIdOriginal(_tmpServicoAdicinalItemIdOriginal);
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
  public List<ListaServicosListaTarefas> GetByListaTarefaIdOriginal(int qrylistaTarefaIdOriginal) {
    final String _sql = "SELECT * FROM ListaServicosListaTarefas WHERE ListaTarefasItemIdOriginal like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, qrylistaTarefaIdOriginal);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataConclusao = _cursor.getColumnIndexOrThrow("DataConclusao");
      final int _cursorIndexOfResultado = _cursor.getColumnIndexOrThrow("Resultado");
      final int _cursorIndexOfListaTarefasItemIdOriginal = _cursor.getColumnIndexOrThrow("ListaTarefasItemIdOriginal");
      final int _cursorIndexOfServicoAdicinalItemIdOriginal = _cursor.getColumnIndexOrThrow("ServicoAdicinalItemIdOriginal");
      final List<ListaServicosListaTarefas> _result = new ArrayList<ListaServicosListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ListaServicosListaTarefas _item;
        _item = new ListaServicosListaTarefas();
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
        final String _tmpResultado;
        _tmpResultado = _cursor.getString(_cursorIndexOfResultado);
        _item.setResultado(_tmpResultado);
        final int _tmpListaTarefasItemIdOriginal;
        _tmpListaTarefasItemIdOriginal = _cursor.getInt(_cursorIndexOfListaTarefasItemIdOriginal);
        _item.setListaTarefasItemIdOriginal(_tmpListaTarefasItemIdOriginal);
        final int _tmpServicoAdicinalItemIdOriginal;
        _tmpServicoAdicinalItemIdOriginal = _cursor.getInt(_cursorIndexOfServicoAdicinalItemIdOriginal);
        _item.setServicoAdicinalItemIdOriginal(_tmpServicoAdicinalItemIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
