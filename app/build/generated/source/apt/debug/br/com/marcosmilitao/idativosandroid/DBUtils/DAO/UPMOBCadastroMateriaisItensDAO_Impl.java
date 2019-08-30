package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBCadastroMateriaisItensDAO_Impl implements UPMOBCadastroMateriaisItensDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBCadastroMateriaisItens;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBCadastroMateriaisItens;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBCadastroMateriaisItens;

  public UPMOBCadastroMateriaisItensDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBCadastroMateriaisItens = new EntityInsertionAdapter<UPMOBCadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBCadastroMateriaisItens`(`Id`,`IdOriginal`,`Patrimonio`,`NumSerie`,`Quantidade`,`DataHoraEvento`,`DataValidade`,`DataFabricacao`,`CadastroMateriaisItemIdOriginal`,`ModeloMateriaisItemIdOriginal`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriaisItens value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getPatrimonio() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPatrimonio());
        }
        if (value.getNumSerie() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNumSerie());
        }
        stmt.bindLong(5, value.getQuantidade());
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataValidade());
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        stmt.bindLong(9, value.getCadastroMateriaisItemIdOriginal());
        stmt.bindLong(10, value.getModeloMateriaisItemIdOriginal());
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
        final Integer _tmp_3;
        _tmp_3 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_3);
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_5);
        }
      }
    };
    this.__deletionAdapterOfUPMOBCadastroMateriaisItens = new EntityDeletionOrUpdateAdapter<UPMOBCadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBCadastroMateriaisItens` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriaisItens value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBCadastroMateriaisItens = new EntityDeletionOrUpdateAdapter<UPMOBCadastroMateriaisItens>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBCadastroMateriaisItens` SET `Id` = ?,`IdOriginal` = ?,`Patrimonio` = ?,`NumSerie` = ?,`Quantidade` = ?,`DataHoraEvento` = ?,`DataValidade` = ?,`DataFabricacao` = ?,`CadastroMateriaisItemIdOriginal` = ?,`ModeloMateriaisItemIdOriginal` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriaisItens value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdOriginal());
        if (value.getPatrimonio() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPatrimonio());
        }
        if (value.getNumSerie() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNumSerie());
        }
        stmt.bindLong(5, value.getQuantidade());
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = TimeStampConverter.dateToTimestamp(value.getDataValidade());
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        stmt.bindLong(9, value.getCadastroMateriaisItemIdOriginal());
        stmt.bindLong(10, value.getModeloMateriaisItemIdOriginal());
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
        final Integer _tmp_3;
        _tmp_3 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_3);
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_5);
        }
        stmt.bindLong(16, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBCadastroMateriaisItens.insert(upmobCadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBCadastroMateriaisItens.handle(upmobCadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBCadastroMateriaisItens.handle(upmobCadastroMateriaisItens);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBCadastroMateriaisItens> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBCadastroMateriaisItens";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfPatrimonio = _cursor.getColumnIndexOrThrow("Patrimonio");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfQuantidade = _cursor.getColumnIndexOrThrow("Quantidade");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfDataValidade = _cursor.getColumnIndexOrThrow("DataValidade");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfCadastroMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("CadastroMateriaisItemIdOriginal");
      final int _cursorIndexOfModeloMateriaisItemIdOriginal = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemIdOriginal");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final List<UPMOBCadastroMateriaisItens> _result = new ArrayList<UPMOBCadastroMateriaisItens>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBCadastroMateriaisItens _item;
        _item = new UPMOBCadastroMateriaisItens();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getInt(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpPatrimonio;
        _tmpPatrimonio = _cursor.getString(_cursorIndexOfPatrimonio);
        _item.setPatrimonio(_tmpPatrimonio);
        final String _tmpNumSerie;
        _tmpNumSerie = _cursor.getString(_cursorIndexOfNumSerie);
        _item.setNumSerie(_tmpNumSerie);
        final int _tmpQuantidade;
        _tmpQuantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _item.setQuantidade(_tmpQuantidade);
        final Date _tmpDataHoraEvento;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final Date _tmpDataValidade;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataValidade);
        _tmpDataValidade = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataValidade(_tmpDataValidade);
        final Date _tmpDataFabricacao;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataFabricacao(_tmpDataFabricacao);
        final int _tmpCadastroMateriaisItemIdOriginal;
        _tmpCadastroMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfCadastroMateriaisItemIdOriginal);
        _item.setCadastroMateriaisItemIdOriginal(_tmpCadastroMateriaisItemIdOriginal);
        final int _tmpModeloMateriaisItemIdOriginal;
        _tmpModeloMateriaisItemIdOriginal = _cursor.getInt(_cursorIndexOfModeloMateriaisItemIdOriginal);
        _item.setModeloMateriaisItemIdOriginal(_tmpModeloMateriaisItemIdOriginal);
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
