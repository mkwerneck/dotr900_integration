package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UPMOBCadastroMateriaisDAO_Impl implements UPMOBCadastroMateriaisDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBCadastroMateriais;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBCadastroMateriais;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBCadastroMateriais;

  public UPMOBCadastroMateriaisDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBCadastroMateriais = new EntityInsertionAdapter<UPMOBCadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBCadastroMateriais`(`Id`,`IdOriginal`,`Patrimonio`,`NumSerie`,`Quantidade`,`DataFabricacao`,`DataValidade`,`DataHoraEvento`,`DadosTecnicos`,`TAGID`,`PosicaoOriginalItemId`,`NotaFiscal`,`DataEntradaNotaFiscal`,`ValorUnitario`,`CodColetor`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`,`ModeloMateriaisItemId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriais value) {
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
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
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
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        if (value.getDadosTecnicos() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDadosTecnicos());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTAGID());
        }
        stmt.bindLong(11, value.getPosicaoOriginalItemId());
        if (value.getNotaFiscal() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotaFiscal());
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataEntradaNotaFiscal());
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_3);
        }
        stmt.bindDouble(14, value.getValorUnitario());
        if (value.getCodColetor() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDescricaoErro());
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindLong(18, _tmp_5);
        }
        final Integer _tmp_6;
        _tmp_6 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_6 == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, _tmp_6);
        }
        stmt.bindLong(20, value.getModeloMateriaisItemId());
      }
    };
    this.__deletionAdapterOfUPMOBCadastroMateriais = new EntityDeletionOrUpdateAdapter<UPMOBCadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBCadastroMateriais` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriais value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBCadastroMateriais = new EntityDeletionOrUpdateAdapter<UPMOBCadastroMateriais>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBCadastroMateriais` SET `Id` = ?,`IdOriginal` = ?,`Patrimonio` = ?,`NumSerie` = ?,`Quantidade` = ?,`DataFabricacao` = ?,`DataValidade` = ?,`DataHoraEvento` = ?,`DadosTecnicos` = ?,`TAGID` = ?,`PosicaoOriginalItemId` = ?,`NotaFiscal` = ?,`DataEntradaNotaFiscal` = ?,`ValorUnitario` = ?,`CodColetor` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ?,`ModeloMateriaisItemId` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBCadastroMateriais value) {
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
        _tmp = TimeStampConverter.dateToTimestamp(value.getDataFabricacao());
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
        _tmp_2 = TimeStampConverter.dateToTimestamp(value.getDataHoraEvento());
        if (_tmp_2 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_2);
        }
        if (value.getDadosTecnicos() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDadosTecnicos());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTAGID());
        }
        stmt.bindLong(11, value.getPosicaoOriginalItemId());
        if (value.getNotaFiscal() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotaFiscal());
        }
        final String _tmp_3;
        _tmp_3 = TimeStampConverter.dateToTimestamp(value.getDataEntradaNotaFiscal());
        if (_tmp_3 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_3);
        }
        stmt.bindDouble(14, value.getValorUnitario());
        if (value.getCodColetor() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getCodColetor());
        }
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getDescricaoErro());
        }
        final Integer _tmp_4;
        _tmp_4 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_4 == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindLong(17, _tmp_4);
        }
        final Integer _tmp_5;
        _tmp_5 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_5 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindLong(18, _tmp_5);
        }
        final Integer _tmp_6;
        _tmp_6 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_6 == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, _tmp_6);
        }
        stmt.bindLong(20, value.getModeloMateriaisItemId());
        stmt.bindLong(21, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBCadastroMateriais upmobCadastroMateriais) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBCadastroMateriais.insert(upmobCadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBCadastroMateriais upmobCadastroMateriais) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBCadastroMateriais.handle(upmobCadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBCadastroMateriais upmobCadastroMateriais) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBCadastroMateriais.handle(upmobCadastroMateriais);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBCadastroMateriais> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBCadastroMateriais";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfPatrimonio = _cursor.getColumnIndexOrThrow("Patrimonio");
      final int _cursorIndexOfNumSerie = _cursor.getColumnIndexOrThrow("NumSerie");
      final int _cursorIndexOfQuantidade = _cursor.getColumnIndexOrThrow("Quantidade");
      final int _cursorIndexOfDataFabricacao = _cursor.getColumnIndexOrThrow("DataFabricacao");
      final int _cursorIndexOfDataValidade = _cursor.getColumnIndexOrThrow("DataValidade");
      final int _cursorIndexOfDataHoraEvento = _cursor.getColumnIndexOrThrow("DataHoraEvento");
      final int _cursorIndexOfDadosTecnicos = _cursor.getColumnIndexOrThrow("DadosTecnicos");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfPosicaoOriginalItemId = _cursor.getColumnIndexOrThrow("PosicaoOriginalItemId");
      final int _cursorIndexOfNotaFiscal = _cursor.getColumnIndexOrThrow("NotaFiscal");
      final int _cursorIndexOfDataEntradaNotaFiscal = _cursor.getColumnIndexOrThrow("DataEntradaNotaFiscal");
      final int _cursorIndexOfValorUnitario = _cursor.getColumnIndexOrThrow("ValorUnitario");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final int _cursorIndexOfModeloMateriaisItemId = _cursor.getColumnIndexOrThrow("ModeloMateriaisItemId");
      final List<UPMOBCadastroMateriais> _result = new ArrayList<UPMOBCadastroMateriais>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBCadastroMateriais _item;
        _item = new UPMOBCadastroMateriais();
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
        final Date _tmpDataFabricacao;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDataFabricacao);
        _tmpDataFabricacao = TimeStampConverter.fromTimestamp(_tmp);
        _item.setDataFabricacao(_tmpDataFabricacao);
        final Date _tmpDataValidade;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfDataValidade);
        _tmpDataValidade = TimeStampConverter.fromTimestamp(_tmp_1);
        _item.setDataValidade(_tmpDataValidade);
        final Date _tmpDataHoraEvento;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfDataHoraEvento);
        _tmpDataHoraEvento = TimeStampConverter.fromTimestamp(_tmp_2);
        _item.setDataHoraEvento(_tmpDataHoraEvento);
        final String _tmpDadosTecnicos;
        _tmpDadosTecnicos = _cursor.getString(_cursorIndexOfDadosTecnicos);
        _item.setDadosTecnicos(_tmpDadosTecnicos);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _item.setTAGID(_tmpTAGID);
        final int _tmpPosicaoOriginalItemId;
        _tmpPosicaoOriginalItemId = _cursor.getInt(_cursorIndexOfPosicaoOriginalItemId);
        _item.setPosicaoOriginalItemId(_tmpPosicaoOriginalItemId);
        final String _tmpNotaFiscal;
        _tmpNotaFiscal = _cursor.getString(_cursorIndexOfNotaFiscal);
        _item.setNotaFiscal(_tmpNotaFiscal);
        final Date _tmpDataEntradaNotaFiscal;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfDataEntradaNotaFiscal);
        _tmpDataEntradaNotaFiscal = TimeStampConverter.fromTimestamp(_tmp_3);
        _item.setDataEntradaNotaFiscal(_tmpDataEntradaNotaFiscal);
        final double _tmpValorUnitario;
        _tmpValorUnitario = _cursor.getDouble(_cursorIndexOfValorUnitario);
        _item.setValorUnitario(_tmpValorUnitario);
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
        final int _tmpModeloMateriaisItemId;
        _tmpModeloMateriaisItemId = _cursor.getInt(_cursorIndexOfModeloMateriaisItemId);
        _item.setModeloMateriaisItemId(_tmpModeloMateriaisItemId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
