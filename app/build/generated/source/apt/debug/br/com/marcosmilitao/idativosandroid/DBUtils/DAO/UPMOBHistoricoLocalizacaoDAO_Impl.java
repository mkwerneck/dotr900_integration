package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBHistoricoLocalizacaoDAO_Impl implements UPMOBHistoricoLocalizacaoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBHistoricoLocalizacao;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBHistoricoLocalizacao;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBHistoricoLocalizacao;

  public UPMOBHistoricoLocalizacaoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBHistoricoLocalizacao = new EntityInsertionAdapter<UPMOBHistoricoLocalizacao>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBHistoricoLocalizacao`(`Id`,`TAGID`,`TAGIDPosicao`,`DataHoraEvento`,`Processo`,`Dominio`,`Quantidade`,`Modalidade`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBHistoricoLocalizacao value) {
        stmt.bindLong(1, value.getId());
        if (value.getTAGID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTAGID());
        }
        if (value.getTAGIDPosicao() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTAGIDPosicao());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getProcesso() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProcesso());
        }
        if (value.getDominio() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDominio());
        }
        stmt.bindLong(7, value.getQuantidade());
        if (value.getModalidade() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getModalidade());
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
        final Integer _tmp_1;
        _tmp_1 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_1);
        }
        final Integer _tmp_2;
        _tmp_2 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_2);
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_3);
        }
      }
    };
    this.__deletionAdapterOfUPMOBHistoricoLocalizacao = new EntityDeletionOrUpdateAdapter<UPMOBHistoricoLocalizacao>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBHistoricoLocalizacao` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBHistoricoLocalizacao value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBHistoricoLocalizacao = new EntityDeletionOrUpdateAdapter<UPMOBHistoricoLocalizacao>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBHistoricoLocalizacao` SET `Id` = ?,`TAGID` = ?,`TAGIDPosicao` = ?,`DataHoraEvento` = ?,`Processo` = ?,`Dominio` = ?,`Quantidade` = ?,`Modalidade` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBHistoricoLocalizacao value) {
        stmt.bindLong(1, value.getId());
        if (value.getTAGID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTAGID());
        }
        if (value.getTAGIDPosicao() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTAGIDPosicao());
        }
        final String _tmp;
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getProcesso() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProcesso());
        }
        if (value.getDominio() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDominio());
        }
        stmt.bindLong(7, value.getQuantidade());
        if (value.getModalidade() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getModalidade());
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
        final Integer _tmp_1;
        _tmp_1 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_1);
        }
        final Integer _tmp_2;
        _tmp_2 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_2);
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindLong(13, _tmp_3);
        }
        stmt.bindLong(14, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBHistoricoLocalizacao.insert(upmobHistoricoLocalizacao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBHistoricoLocalizacao.handle(upmobHistoricoLocalizacao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBHistoricoLocalizacao.handle(upmobHistoricoLocalizacao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBHistoricoLocalizacao> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBHistoricoLocalizacao";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfTAGIDPosicao = _cursor.getColumnIndexOrThrow("TAGIDPosicao");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfProcesso = _cursor.getColumnIndexOrThrow("Processo");
      final int _cursorIndexOfDominio = _cursor.getColumnIndexOrThrow("Dominio");
      final int _cursorIndexOfQuantidade = _cursor.getColumnIndexOrThrow("Quantidade");
      final int _cursorIndexOfModalidade = _cursor.getColumnIndexOrThrow("Modalidade");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final List<UPMOBHistoricoLocalizacao> _result = new ArrayList<UPMOBHistoricoLocalizacao>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBHistoricoLocalizacao _item;
        _item = new UPMOBHistoricoLocalizacao();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _item.setTAGID(_tmpTAGID);
        final String _tmpTAGIDPosicao;
        _tmpTAGIDPosicao = _cursor.getString(_cursorIndexOfTAGIDPosicao);
        _item.setTAGIDPosicao(_tmpTAGIDPosicao);
        final Date _tmpDataHoraEvento;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final String _tmpProcesso;
        _tmpProcesso = _cursor.getString(_cursorIndexOfProcesso);
        _item.setProcesso(_tmpProcesso);
        final String _tmpDominio;
        _tmpDominio = _cursor.getString(_cursorIndexOfDominio);
        _item.setDominio(_tmpDominio);
        final int _tmpQuantidade;
        _tmpQuantidade = _cursor.getInt(_cursorIndexOfQuantidade);
        _item.setQuantidade(_tmpQuantidade);
        final String _tmpModalidade;
        _tmpModalidade = _cursor.getString(_cursorIndexOfModalidade);
        _item.setModalidade(_tmpModalidade);
        final String _tmpCodColetor;
        _tmpCodColetor = _cursor.getString(_cursorIndexOfCodColetor);
        _item.setCodColetor(_tmpCodColetor);
        final String _tmpDescricaoErro;
        _tmpDescricaoErro = _cursor.getString(_cursorIndexOfDescricaoErro);
        _item.setDescricaoErro(_tmpDescricaoErro);
        final Boolean _tmpFlagErro;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFlagErro)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfFlagErro);
        }
        _tmpFlagErro = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setFlagErro(_tmpFlagErro);
        final Boolean _tmpFlagAtualizar;
        final Integer _tmp_2;
        if (_cursor.isNull(_cursorIndexOfFlagAtualizar)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getInt(_cursorIndexOfFlagAtualizar);
        }
        _tmpFlagAtualizar = _tmp_2 == null ? null : _tmp_2 != 0;
        _item.setFlagAtualizar(_tmpFlagAtualizar);
        final Boolean _tmpFlagProcess;
        final Integer _tmp_3;
        if (_cursor.isNull(_cursorIndexOfFlagProcess)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getInt(_cursorIndexOfFlagProcess);
        }
        _tmpFlagProcess = _tmp_3 == null ? null : _tmp_3 != 0;
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
