package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaTarefasDAO_Impl implements ListaTarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfListaTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfListaTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfListaTarefas;

  public ListaTarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfListaTarefas = new EntityInsertionAdapter<ListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ListaTarefas`(`Id`,`IdOriginal`,`RowVersion`,`Status`,`DataInicio`,`DataFimPrevisao`,`DataFimReal`,`DataCancelamento`,`Dominio`,`Processo`,`TarefaItemIdOriginal`,`CadsatroEquipamentosItemIdOriginal`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaTarefas value) {
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
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFimPrevisao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataFimReal());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataCancelamento());
        if (_tmp_3 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_3);
        }
        if (value.getDominio() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDominio());
        }
        stmt.bindLong(10, value.getProcesso());
        stmt.bindLong(11, value.getTarefaItemIdOriginal());
        stmt.bindLong(12, value.getCadsatroEquipamentosItemIdOriginal());
      }
    };
    this.__deletionAdapterOfListaTarefas = new EntityDeletionOrUpdateAdapter<ListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ListaTarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaTarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfListaTarefas = new EntityDeletionOrUpdateAdapter<ListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ListaTarefas` SET `Id` = ?,`IdOriginal` = ?,`RowVersion` = ?,`Status` = ?,`DataInicio` = ?,`DataFimPrevisao` = ?,`DataFimReal` = ?,`DataCancelamento` = ?,`Dominio` = ?,`Processo` = ?,`TarefaItemIdOriginal` = ?,`CadsatroEquipamentosItemIdOriginal` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListaTarefas value) {
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
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFimPrevisao());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataFimReal());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataCancelamento());
        if (_tmp_3 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_3);
        }
        if (value.getDominio() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDominio());
        }
        stmt.bindLong(10, value.getProcesso());
        stmt.bindLong(11, value.getTarefaItemIdOriginal());
        stmt.bindLong(12, value.getCadsatroEquipamentosItemIdOriginal());
        stmt.bindLong(13, value.getId());
      }
    };
  }

  @Override
  public void Create(ListaTarefas listaTarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfListaTarefas.insert(listaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(ListaTarefas listaTarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfListaTarefas.handle(listaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(ListaTarefas listaTarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfListaTarefas.handle(listaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public String GetLastRowVersion() {
    final String _sql = "SELECT RowVersion FROM ListaTarefas ORDER BY RowVersion DESC LIMIT 1 ";
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
  public ListaTarefas GetByIdOriginal(int qryIdOriginal) {
    final String _sql = "SELECT * FROM ListaTarefas WHERE IdOriginal = ? ";
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
      final int _cursorIndexOfDataFimPrevisao = _cursor.getColumnIndexOrThrow("DataFimPrevisao");
      final int _cursorIndexOfDataFimReal = _cursor.getColumnIndexOrThrow("DataFimReal");
      final int _cursorIndexOfDataCancelamento = _cursor.getColumnIndexOrThrow("DataCancelamento");
      final int _cursorIndexOfDominio = _cursor.getColumnIndexOrThrow("Dominio");
      final int _cursorIndexOfProcesso = _cursor.getColumnIndexOrThrow("Processo");
      final int _cursorIndexOfTarefaItemIdOriginal = _cursor.getColumnIndexOrThrow("TarefaItemIdOriginal");
      final int _cursorIndexOfCadsatroEquipamentosItemIdOriginal = _cursor.getColumnIndexOrThrow("CadsatroEquipamentosItemIdOriginal");
      final ListaTarefas _result;
      if(_cursor.moveToFirst()) {
        _result = new ListaTarefas();
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
        final Date _tmpDataFimPrevisao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFimPrevisao);
        _tmpDataFimPrevisao = TimeStampConverter.fromTimestamp(_tmp_1);
        _result.setDataFimPrevisao(_tmpDataFimPrevisao);
        final Date _tmpDataFimReal;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataFimReal);
        _tmpDataFimReal = TimeStampConverter.fromTimestamp(_tmp_2);
        _result.setDataFimReal(_tmpDataFimReal);
        final Date _tmpDataCancelamento;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataCancelamento);
        _tmpDataCancelamento = TimeStampConverter.fromTimestamp(_tmp_3);
        _result.setDataCancelamento(_tmpDataCancelamento);
        final String _tmpDominio;
        _tmpDominio = _cursor.getString(_cursorIndexOfDominio);
        _result.setDominio(_tmpDominio);
        final int _tmpProcesso;
        _tmpProcesso = _cursor.getInt(_cursorIndexOfProcesso);
        _result.setProcesso(_tmpProcesso);
        final int _tmpTarefaItemIdOriginal;
        _tmpTarefaItemIdOriginal = _cursor.getInt(_cursorIndexOfTarefaItemIdOriginal);
        _result.setTarefaItemIdOriginal(_tmpTarefaItemIdOriginal);
        final int _tmpCadsatroEquipamentosItemIdOriginal;
        _tmpCadsatroEquipamentosItemIdOriginal = _cursor.getInt(_cursorIndexOfCadsatroEquipamentosItemIdOriginal);
        _result.setCadsatroEquipamentosItemIdOriginal(_tmpCadsatroEquipamentosItemIdOriginal);
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
  public List<ListaTarefas> GetListaTarefasFilter(String qryFilter) {
    final String _sql = "SELECT * FROM ListaTarefas as lt INNER JOIN CadastroEquipamentos as ce ON lt.CadsatroEquipamentosItemIdOriginal = ce.IdOriginal WHERE (lt.Status <> 'Concluido' AND lt.Status <> 'Cancelado') AND (lt.Processo like ? || ce.TraceNumber like ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (qryFilter == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryFilter);
    }
    _argIndex = 2;
    if (qryFilter == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryFilter);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataFimPrevisao = _cursor.getColumnIndexOrThrow("DataFimPrevisao");
      final int _cursorIndexOfDataFimReal = _cursor.getColumnIndexOrThrow("DataFimReal");
      final int _cursorIndexOfDataCancelamento = _cursor.getColumnIndexOrThrow("DataCancelamento");
      final int _cursorIndexOfDominio = _cursor.getColumnIndexOrThrow("Dominio");
      final int _cursorIndexOfProcesso = _cursor.getColumnIndexOrThrow("Processo");
      final int _cursorIndexOfTarefaItemIdOriginal = _cursor.getColumnIndexOrThrow("TarefaItemIdOriginal");
      final int _cursorIndexOfCadsatroEquipamentosItemIdOriginal = _cursor.getColumnIndexOrThrow("CadsatroEquipamentosItemIdOriginal");
      final int _cursorIndexOfId_1 = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal_1 = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion_1 = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus_1 = _cursor.getColumnIndexOrThrow("Status");
      final List<ListaTarefas> _result = new ArrayList<ListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ListaTarefas _item;
        _item = new ListaTarefas();
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
        final Date _tmpDataFimPrevisao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFimPrevisao);
        _tmpDataFimPrevisao = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataFimPrevisao(_tmpDataFimPrevisao);
        final Date _tmpDataFimReal;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataFimReal);
        _tmpDataFimReal = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataFimReal(_tmpDataFimReal);
        final Date _tmpDataCancelamento;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataCancelamento);
        _tmpDataCancelamento = TimeStampConverter.fromTimestamp(_tmp_3);
        _item.setDataCancelamento(_tmpDataCancelamento);
        final String _tmpDominio;
        _tmpDominio = _cursor.getString(_cursorIndexOfDominio);
        _item.setDominio(_tmpDominio);
        final int _tmpProcesso;
        _tmpProcesso = _cursor.getInt(_cursorIndexOfProcesso);
        _item.setProcesso(_tmpProcesso);
        final int _tmpTarefaItemIdOriginal;
        _tmpTarefaItemIdOriginal = _cursor.getInt(_cursorIndexOfTarefaItemIdOriginal);
        _item.setTarefaItemIdOriginal(_tmpTarefaItemIdOriginal);
        final int _tmpCadsatroEquipamentosItemIdOriginal;
        _tmpCadsatroEquipamentosItemIdOriginal = _cursor.getInt(_cursorIndexOfCadsatroEquipamentosItemIdOriginal);
        _item.setCadsatroEquipamentosItemIdOriginal(_tmpCadsatroEquipamentosItemIdOriginal);
        final int _tmpId_1;
        _tmpId_1 = _cursor.getInt(_cursorIndexOfId_1);
        _item.setId(_tmpId_1);
        final int _tmpIdOriginal_1;
        _tmpIdOriginal_1 = _cursor.getInt(_cursorIndexOfIdOriginal_1);
        _item.setIdOriginal(_tmpIdOriginal_1);
        final String _tmpRowVersion_1;
        _tmpRowVersion_1 = _cursor.getString(_cursorIndexOfRowVersion_1);
        _item.setRowVersion(_tmpRowVersion_1);
        final String _tmpStatus_1;
        _tmpStatus_1 = _cursor.getString(_cursorIndexOfStatus_1);
        _item.setStatus(_tmpStatus_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ListaTarefas> GetListaTarefas() {
    final String _sql = "SELECT * FROM ListaTarefas WHERE (Status != 'Concluido' AND Status != 'Cancelado')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRowVersion = _cursor.getColumnIndexOrThrow("RowVersion");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataFimPrevisao = _cursor.getColumnIndexOrThrow("DataFimPrevisao");
      final int _cursorIndexOfDataFimReal = _cursor.getColumnIndexOrThrow("DataFimReal");
      final int _cursorIndexOfDataCancelamento = _cursor.getColumnIndexOrThrow("DataCancelamento");
      final int _cursorIndexOfDominio = _cursor.getColumnIndexOrThrow("Dominio");
      final int _cursorIndexOfProcesso = _cursor.getColumnIndexOrThrow("Processo");
      final int _cursorIndexOfTarefaItemIdOriginal = _cursor.getColumnIndexOrThrow("TarefaItemIdOriginal");
      final int _cursorIndexOfCadsatroEquipamentosItemIdOriginal = _cursor.getColumnIndexOrThrow("CadsatroEquipamentosItemIdOriginal");
      final List<ListaTarefas> _result = new ArrayList<ListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ListaTarefas _item;
        _item = new ListaTarefas();
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
        final Date _tmpDataFimPrevisao;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFimPrevisao);
        _tmpDataFimPrevisao = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataFimPrevisao(_tmpDataFimPrevisao);
        final Date _tmpDataFimReal;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataFimReal);
        _tmpDataFimReal = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataFimReal(_tmpDataFimReal);
        final Date _tmpDataCancelamento;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataCancelamento);
        _tmpDataCancelamento = TimeStampConverter.fromTimestamp(_tmp_3);
        _item.setDataCancelamento(_tmpDataCancelamento);
        final String _tmpDominio;
        _tmpDominio = _cursor.getString(_cursorIndexOfDominio);
        _item.setDominio(_tmpDominio);
        final int _tmpProcesso;
        _tmpProcesso = _cursor.getInt(_cursorIndexOfProcesso);
        _item.setProcesso(_tmpProcesso);
        final int _tmpTarefaItemIdOriginal;
        _tmpTarefaItemIdOriginal = _cursor.getInt(_cursorIndexOfTarefaItemIdOriginal);
        _item.setTarefaItemIdOriginal(_tmpTarefaItemIdOriginal);
        final int _tmpCadsatroEquipamentosItemIdOriginal;
        _tmpCadsatroEquipamentosItemIdOriginal = _cursor.getInt(_cursorIndexOfCadsatroEquipamentosItemIdOriginal);
        _item.setCadsatroEquipamentosItemIdOriginal(_tmpCadsatroEquipamentosItemIdOriginal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
