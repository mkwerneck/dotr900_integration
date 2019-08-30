package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBDescartesDAO_Impl implements UPMOBDescartesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBDescartes;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBDescartes;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBDescartes;

  public UPMOBDescartesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBDescartes = new EntityInsertionAdapter<UPMOBDescartes>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBDescartes`(`Id`,`CadastromateriaisId`,`ApplicationUserId`,`Motivo`,`DataHoraEvento`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagProcess`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBDescartes value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getCadastromateriaisId());
        if (value.getApplicationUserId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getApplicationUserId());
        }
        if (value.getMotivo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMotivo());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescricaoErro());
        }
        final int _tmp_1;
        _tmp_1 = value.isFlagErro() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.isFlagProcess() ? 1 : 0;
        stmt.bindLong(9, _tmp_2);
      }
    };
    this.__deletionAdapterOfUPMOBDescartes = new EntityDeletionOrUpdateAdapter<UPMOBDescartes>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBDescartes` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBDescartes value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBDescartes = new EntityDeletionOrUpdateAdapter<UPMOBDescartes>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBDescartes` SET `Id` = ?,`CadastromateriaisId` = ?,`ApplicationUserId` = ?,`Motivo` = ?,`DataHoraEvento` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagProcess` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBDescartes value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getCadastromateriaisId());
        if (value.getApplicationUserId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getApplicationUserId());
        }
        if (value.getMotivo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMotivo());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDescricaoErro());
        }
        final int _tmp_1;
        _tmp_1 = value.isFlagErro() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.isFlagProcess() ? 1 : 0;
        stmt.bindLong(9, _tmp_2);
        stmt.bindLong(10, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBDescartes upmobDescartes) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBDescartes.insert(upmobDescartes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBDescartes upmobDescartes) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBDescartes.handle(upmobDescartes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBDescartes upmobDescartes) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBDescartes.handle(upmobDescartes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBDescartes> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBDescartes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfCadastromateriaisId = _cursor.getColumnIndexOrThrow("CadastromateriaisId");
      final int _cursorIndexOfApplicationUserId = _cursor.getColumnIndexOrThrow("ApplicationUserId");
      final int _cursorIndexOfMotivo = _cursor.getColumnIndexOrThrow("Motivo");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final List<UPMOBDescartes> _result = new ArrayList<UPMOBDescartes>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBDescartes _item;
        _item = new UPMOBDescartes();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpCadastromateriaisId;
        _tmpCadastromateriaisId = _cursor.getInt(_cursorIndexOfCadastromateriaisId);
        _item.setCadastromateriaisId(_tmpCadastromateriaisId);
        final String _tmpApplicationUserId;
        _tmpApplicationUserId = _cursor.getString(_cursorIndexOfApplicationUserId);
        _item.setApplicationUserId(_tmpApplicationUserId);
        final String _tmpMotivo;
        _tmpMotivo = _cursor.getString(_cursorIndexOfMotivo);
        _item.setMotivo(_tmpMotivo);
        final Date _tmpDataHoraEvento;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final String _tmpCodColetor;
        _tmpCodColetor = _cursor.getString(_cursorIndexOfCodColetor);
        _item.setCodColetor(_tmpCodColetor);
        final String _tmpDescricaoErro;
        _tmpDescricaoErro = _cursor.getString(_cursorIndexOfDescricaoErro);
        _item.setDescricaoErro(_tmpDescricaoErro);
        final boolean _tmpFlagErro;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfFlagErro);
        _tmpFlagErro = _tmp_1 != 0;
        _item.setFlagErro(_tmpFlagErro);
        final boolean _tmpFlagProcess;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfFlagProcess);
        _tmpFlagProcess = _tmp_2 != 0;
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
