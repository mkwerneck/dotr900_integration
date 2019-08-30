package br.com.marcosmilitao.idativosandroid.DBUtils.DAO;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class UPMOBUsuariosDAO_Impl implements UPMOBUsuariosDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUPMOBUsuarios;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUPMOBUsuarios;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUPMOBUsuarios;

  public UPMOBUsuariosDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUPMOBUsuarios = new EntityInsertionAdapter<UPMOBUsuarios>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UPMOBUsuarios`(`Id`,`IdOriginal`,`RoleIdOriginal`,`Username`,`Email`,`NomeCompleto`,`TAGID`,`EnviarSenhaEmail`,`DescricaoErro`,`FlagErro`,`FlagAtualizar`,`FlagProcess`,`CodColetor`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBUsuarios value) {
        stmt.bindLong(1, value.getId());
        if (value.getIdOriginal() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdOriginal());
        }
        if (value.getRoleIdOriginal() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRoleIdOriginal());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUsername());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getNomeCompleto() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getNomeCompleto());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTAGID());
        }
        final int _tmp;
        _tmp = value.getEnviarSenhaEmail() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDescricaoErro());
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp_1);
        }
        final Integer _tmp_2;
        _tmp_2 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_2);
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_3);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getCodColetor());
        }
      }
    };
    this.__deletionAdapterOfUPMOBUsuarios = new EntityDeletionOrUpdateAdapter<UPMOBUsuarios>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UPMOBUsuarios` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBUsuarios value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUPMOBUsuarios = new EntityDeletionOrUpdateAdapter<UPMOBUsuarios>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UPMOBUsuarios` SET `Id` = ?,`IdOriginal` = ?,`RoleIdOriginal` = ?,`Username` = ?,`Email` = ?,`NomeCompleto` = ?,`TAGID` = ?,`EnviarSenhaEmail` = ?,`DescricaoErro` = ?,`FlagErro` = ?,`FlagAtualizar` = ?,`FlagProcess` = ?,`CodColetor` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UPMOBUsuarios value) {
        stmt.bindLong(1, value.getId());
        if (value.getIdOriginal() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getIdOriginal());
        }
        if (value.getRoleIdOriginal() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRoleIdOriginal());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUsername());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getNomeCompleto() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getNomeCompleto());
        }
        if (value.getTAGID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTAGID());
        }
        final int _tmp;
        _tmp = value.getEnviarSenhaEmail() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getDescricaoErro() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getDescricaoErro());
        }
        final Integer _tmp_1;
        _tmp_1 = value.getFlagErro() == null ? null : (value.getFlagErro() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp_1);
        }
        final Integer _tmp_2;
        _tmp_2 = value.getFlagAtualizar() == null ? null : (value.getFlagAtualizar() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_2);
        }
        final Integer _tmp_3;
        _tmp_3 = value.getFlagProcess() == null ? null : (value.getFlagProcess() ? 1 : 0);
        if (_tmp_3 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, _tmp_3);
        }
        if (value.getCodColetor() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getCodColetor());
        }
        stmt.bindLong(14, value.getId());
      }
    };
  }

  @Override
  public void Create(UPMOBUsuarios upmobUsuarios) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUPMOBUsuarios.insert(upmobUsuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Delete(UPMOBUsuarios upmobUsuarios) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUPMOBUsuarios.handle(upmobUsuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(UPMOBUsuarios upmobUsuarios) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUPMOBUsuarios.handle(upmobUsuarios);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UPMOBUsuarios> GetAllRecords() {
    final String _sql = "SELECT * FROM UPMOBUsuarios";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
      final int _cursorIndexOfIdOriginal = _cursor.getColumnIndexOrThrow("IdOriginal");
      final int _cursorIndexOfRoleIdOriginal = _cursor.getColumnIndexOrThrow("RoleIdOriginal");
      final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("Username");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("Email");
      final int _cursorIndexOfNomeCompleto = _cursor.getColumnIndexOrThrow("NomeCompleto");
      final int _cursorIndexOfTAGID = _cursor.getColumnIndexOrThrow("TAGID");
      final int _cursorIndexOfEnviarSenhaEmail = _cursor.getColumnIndexOrThrow("EnviarSenhaEmail");
      final int _cursorIndexOfDescricaoErro = _cursor.getColumnIndexOrThrow("DescricaoErro");
      final int _cursorIndexOfFlagErro = _cursor.getColumnIndexOrThrow("FlagErro");
      final int _cursorIndexOfFlagAtualizar = _cursor.getColumnIndexOrThrow("FlagAtualizar");
      final int _cursorIndexOfFlagProcess = _cursor.getColumnIndexOrThrow("FlagProcess");
      final int _cursorIndexOfCodColetor = _cursor.getColumnIndexOrThrow("CodColetor");
      final List<UPMOBUsuarios> _result = new ArrayList<UPMOBUsuarios>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UPMOBUsuarios _item;
        _item = new UPMOBUsuarios();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpIdOriginal;
        _tmpIdOriginal = _cursor.getString(_cursorIndexOfIdOriginal);
        _item.setIdOriginal(_tmpIdOriginal);
        final String _tmpRoleIdOriginal;
        _tmpRoleIdOriginal = _cursor.getString(_cursorIndexOfRoleIdOriginal);
        _item.setRoleIdOriginal(_tmpRoleIdOriginal);
        final String _tmpUsername;
        _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        _item.setUsername(_tmpUsername);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _item.setEmail(_tmpEmail);
        final String _tmpNomeCompleto;
        _tmpNomeCompleto = _cursor.getString(_cursorIndexOfNomeCompleto);
        _item.setNomeCompleto(_tmpNomeCompleto);
        final String _tmpTAGID;
        _tmpTAGID = _cursor.getString(_cursorIndexOfTAGID);
        _item.setTAGID(_tmpTAGID);
        final boolean _tmpEnviarSenhaEmail;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfEnviarSenhaEmail);
        _tmpEnviarSenhaEmail = _tmp != 0;
        _item.setEnviarSenhaEmail(_tmpEnviarSenhaEmail);
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
        final String _tmpCodColetor;
        _tmpCodColetor = _cursor.getString(_cursorIndexOfCodColetor);
        _item.setCodColetor(_tmpCodColetor);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
