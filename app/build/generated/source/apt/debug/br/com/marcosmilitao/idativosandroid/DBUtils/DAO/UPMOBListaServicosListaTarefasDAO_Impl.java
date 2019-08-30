package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBListaServicosListaTarefasDAO_Impl implements UPMOBListaServicosListaTarefasDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBListaServicosListaTarefas;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBListaServicosListaTarefas;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBListaServicosListaTarefas;

  public UPMOBListaServicosListaTarefasDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBListaServicosListaTarefas = new EntityInsertionAdapter<UPMOBListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBListaServicosListaTarefas`(`Id`,`IdOriginal`,`CodTarefa`,`Servico`,`Resultado`,`Status`,`DataHoraEvento`,`DataInicio`,`DataConclusao`,`DataCancelamento`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaServicosListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getCodTarefa() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCodTarefa());
        }
        if (value.getServico() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getServico());
        }
        if (value.getResultado() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getResultado());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataConclusao());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataCancelamento());
        if (_tmp_3 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_3);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDescricaoErro());
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_5);
        }
        final Integer _tmp_6;
        _tmp_6 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_6 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_6);
        }
      }
    };
    this.__deletionAdapterOfUPMOBListaServicosListaTarefas = new EntityDeletionOrUpdateAdapter<UPMOBListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBListaServicosListaTarefas` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaServicosListaTarefas value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBListaServicosListaTarefas = new EntityDeletionOrUpdateAdapter<UPMOBListaServicosListaTarefas>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBListaServicosListaTarefas` SET `Id` = ?,`IdOriginal` = ?,`CodTarefa` = ?,`Servico` = ?,`Resultado` = ?,`Status` = ?,`DataHoraEvento` = ?,`DataInicio` = ?,`DataConclusao` = ?,`DataCancelamento` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBListaServicosListaTarefas value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getCodTarefa() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCodTarefa());
        }
        if (value.getServico() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getServico());
        }
        if (value.getResultado() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getResultado());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataInicio());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataConclusao());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataCancelamento());
        if (_tmp_3 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_3);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDescricaoErro());
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_5);
        }
        final Integer _tmp_6;
        _tmp_6 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_6 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_6);
        }
        stmt.bindLong(16, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBListaServicosListaTarefas.insert(upmobListaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBListaServicosListaTarefas.handle(upmobListaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBListaServicosListaTarefas.handle(upmobListaServicosListaTarefas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBListaServicosListaTarefas> GetAllRecords(String qryDataHoraEvento) {
    final String _sql = "SELECT * FROM UPMOBListaServicosListaTarefas WHERE DataHoraEvento like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (qryDataHoraEvento == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, qryDataHoraEvento);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfCodTarefa = _cursor.getColumnIndexOrThrow("CodTarefa");
      final int _cursorIndexOfServico = _cursor.getColumnIndexOrThrow("Servico");
      final int _cursorIndexOfResultado = _cursor.getColumnIndexOrThrow("Resultado");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfDataInicio = _cursor.getColumnIndexOrThrow("DataInicio");
      final int _cursorIndexOfDataConclusao = _cursor.getColumnIndexOrThrow("DataConclusao");
      final int _cursorIndexOfDataCancelamento = _cursor.getColumnIndexOrThrow("DataCancelamento");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final List<UPMOBListaServicosListaTarefas> _result = new ArrayList<UPMOBListaServicosListaTarefas>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBListaServicosListaTarefas _item;
        _item = new UPMOBListaServicosListaTarefas();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpCodTarefa;
        _tmpCodTarefa = _cursor.getString(_cursorIndexOfCodTarefa);
        _item.setCodTarefa(_tmpCodTarefa);
        final String _tmpServico;
        _tmpServico = _cursor.getString(_cursorIndexOfServico);
        _item.setServico(_tmpServico);
        final String _tmpResultado;
        _tmpResultado = _cursor.getString(_cursorIndexOfResultado);
        _item.setResultado(_tmpResultado);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final Date _tmpDataHoraEvento;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final Date _tmpDataInicio;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataInicio);
        _tmpDataInicio = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataInicio(_tmpDataInicio);
        final Date _tmpDataConclusao;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataConclusao);
        _tmpDataConclusao = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataConclusao(_tmpDataConclusao);
        final Date _tmpDataCancelamento;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataCancelamento);
        _tmpDataCancelamento = TimeStampConverter.fromTimestamp(_tmp_3);
        _item.setDataCancelamento(_tmpDataCancelamento);
        final String _tmpCodColetor;
        _tmpCodColetor = _cursor.getString(_cursorIndexOfCodColetor);
        _item.setCodColetor(_tmpCodColetor);
        final String _tmpDescricaoErro;
        _tmpDescricaoErro = _cursor.getString(_cursorIndexOfDescricaoErro);
        _item.setDescricaoErro(_tmpDescricaoErro);
        final Boolean _tmpFlagErro;
        final Integer _tmp_4;
        if (_cursor.isNull(_cursorIndexOfFlagErro)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getInt(_cursorIndexOfFlagErro);
        }
        _tmpFlagErro = _tmp_4 == null ? null : _tmp_4 != 0;
        _item.setFlagErro(_tmpFlagErro);
        final Boolean _tmpFlagAtualizar;
        final Integer _tmp_5;
        if (_cursor.isNull(_cursorIndexOfFlagAtualizar)) {
          _tmp_5 = null;
        } else {
          _tmp_5 = _cursor.getInt(_cursorIndexOfFlagAtualizar);
        }
        _tmpFlagAtualizar = _tmp_5 == null ? null : _tmp_5 != 0;
        _item.setFlagAtualizar(_tmpFlagAtualizar);
        final Boolean _tmpFlagProcess;
        final Integer _tmp_6;
        if (_cursor.isNull(_cursorIndexOfFlagProcess)) {
          _tmp_6 = null;
        } else {
          _tmp_6 = _cursor.getInt(_cursorIndexOfFlagProcess);
        }
        _tmpFlagProcess = _tmp_6 == null ? null : _tmp_6 != 0;
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
