package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBListaTarefasDAO_Impl implements UPMOBListaTarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBListaTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBListaTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBListaTarefas;

  public UPMOBListaTarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBListaTarefas = new EntityInsertionAdapter<UPMOBListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBListaTarefas`(`Id`,`IdOriginal`,`CodTarefa`,`Status`,`TraceNumber`,`DataInicio`,`DataFimReal`,`DataHoraEvento`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getCodTarefa() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCodTarefa());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
        if (value.getTraceNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTraceNumber());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFimReal());
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDescricaoErro());
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_3);
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_5);
        }
      }
    };
    this.__deletionAdapterOfUPMOBListaTarefas = new EntityDeletionOrUpdateAdapter<UPMOBListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBListaTarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaTarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBListaTarefas = new EntityDeletionOrUpdateAdapter<UPMOBListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBListaTarefas` SET `Id` = ?,`IdOriginal` = ?,`CodTarefa` = ?,`Status` = ?,`TraceNumber` = ?,`DataInicio` = ?,`DataFimReal` = ?,`DataHoraEvento` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getCodTarefa() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCodTarefa());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
        if (value.getTraceNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTraceNumber());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataFimReal());
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDescricaoErro());
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_3);
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_5);
        }
        stmt.bindLong(14, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBListaTarefas upmobListaTarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBListaTarefas.insert(upmobListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBListaTarefas upmobListaTarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBListaTarefas.handle(upmobListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBListaTarefas upmobListaTarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBListaTarefas.handle(upmobListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBListaTarefas> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBListaTarefas";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfCodTarefa = _cursor.getColumnIndexOrThrow("CodTarefa");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfTraceNumber = _cursor.getColumnIndexOrThrow("TraceNumber");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataFimReal = _cursor.getColumnIndexOrThrow("DataFimReal");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final List<UPMOBListaTarefas> _result = new ArrayList<UPMOBListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBListaTarefas _item;
        _item = new UPMOBListaTarefas();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpCodTarefa;
        _tmpCodTarefa = _cursor.getString(_cursorIndexOfCodTarefa);
        _item.setCodTarefa(_tmpCodTarefa);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpTraceNumber;
        _tmpTraceNumber = _cursor.getString(_cursorIndexOfTraceNumber);
        _item.setTraceNumber(_tmpTraceNumber);
        final Date _tmpDataInicio;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataInicio);
        _tmpDataInicio = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataInicio(_tmpDataInicio);
        final Date _tmpDataFimReal;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataFimReal);
        _tmpDataFimReal = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataFimReal(_tmpDataFimReal);
        final Date _tmpDataHoraEvento;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final String _tmpCodColetor;
        _tmpCodColetor = _cursor.getString(_cursorIndexOfCodColetor);
        _item.setCodColetor(_tmpCodColetor);
        final String _tmpDescricaoErro;
        _tmpDescricaoErro = _cursor.getString(_cursorIndexOfDescricaoErro);
        _item.setDescricaoErro(_tmpDescricaoErro);
        final Boolean _tmpFlagErro;
        final Integer _tmp_3;
        if (_cursor.isNull(_cursorIndexOfFlagErro)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getInt(_cursorIndexOfFlagErro);
        }
        _tmpFlagErro = _tmp_3 == null ? null : _tmp_3 != 0;
        _item.setFlagErro(_tmpFlagErro);
        final Boolean _tmpFlagAtualizar;
        final Integer _tmp_4;
        if (_cursor.isNull(_cursorIndexOfFlagAtualizar)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getInt(_cursorIndexOfFlagAtualizar);
        }
        _tmpFlagAtualizar = _tmp_4 == null ? null : _tmp_4 != 0;
        _item.setFlagAtualizar(_tmpFlagAtualizar);
        final Boolean _tmpFlagProcess;
        final Integer _tmp_5;
        if (_cursor.isNull(_cursorIndexOfFlagProcess)) {
          _tmp_5 = null;
        } else {
          _tmp_5 = _cursor.getInt(_cursorIndexOfFlagProcess);
        }
        _tmpFlagProcess = _tmp_5 == null ? null : _tmp_5 != 0;
        _item.setFlagProcess(_tmpFlagProcess);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
