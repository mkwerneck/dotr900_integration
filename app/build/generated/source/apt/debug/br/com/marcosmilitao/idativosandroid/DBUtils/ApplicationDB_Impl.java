package br.com.marcosmilitao.idativosandroid.DBUtils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroEquipamentosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroEquipamentosDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisItensDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.GruposDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.GruposDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.InventarioPlanejadoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.InventarioPlanejadoDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaMateriaisListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaMateriaisListaTarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaServicosListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaServicosListaTarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaTarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloEquipamentosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloEquipamentosDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloMateriaisDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ParametrosPadraoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ParametrosPadraoDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.PosicoesDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.PosicoesDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ProprietariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ProprietariosDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ServicosAdicionaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ServicosAdicionaisDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.TarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.TarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisItensDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBDescartesDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBDescartesDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBHistoricoLocalizacaoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBHistoricoLocalizacaoDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaServicosListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaServicosListaTarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaTarefasDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBUsuariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBUsuariosDAO_Impl;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UsuariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UsuariosDAO_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class ApplicationDB_Impl extends ApplicationDB {
  private volatile CadastroEquipamentosDAO _cadastroEquipamentosDAO;

  private volatile ModeloEquipamentosDAO _modeloEquipamentosDAO;

  private volatile PosicoesDAO _posicoesDAO;

  private volatile ServicosAdicionaisDAO _servicosAdicionaisDAO;

  private volatile TarefasDAO _tarefasDAO;

  private volatile ProprietariosDAO _proprietariosDAO;

  private volatile ModeloMateriaisDAO _modeloMateriaisDAO;

  private volatile InventarioPlanejadoDAO _inventarioPlanejadoDAO;

  private volatile ListaTarefasDAO _listaTarefasDAO;

  private volatile ListaServicosListaTarefasDAO _listaServicosListaTarefasDAO;

  private volatile ListaMateriaisListaTarefasDAO _listaMateriaisListaTarefasDAO;

  private volatile GruposDAO _gruposDAO;

  private volatile CadastroMateriaisItensDAO _cadastroMateriaisItensDAO;

  private volatile CadastroMateriaisDAO _cadastroMateriaisDAO;

  private volatile UsuariosDAO _usuariosDAO;

  private volatile ParametrosPadraoDAO _parametrosPadraoDAO;

  private volatile UPMOBUsuariosDAO _uPMOBUsuariosDAO;

  private volatile UPMOBListaServicosListaTarefasDAO _uPMOBListaServicosListaTarefasDAO;

  private volatile UPMOBListaTarefasDAO _uPMOBListaTarefasDAO;

  private volatile UPMOBHistoricoLocalizacaoDAO _uPMOBHistoricoLocalizacaoDAO;

  private volatile UPMOBCadastroMateriaisItensDAO _uPMOBCadastroMateriaisItensDAO;

  private volatile UPMOBCadastroMateriaisDAO _uPMOBCadastroMateriaisDAO;

  private volatile UPMOBDescartesDAO _uPMOBDescartesDAO;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CadastroEquipamentos` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `TraceNumber` TEXT, `DataCadastro` TEXT, `DataFabricacao` TEXT, `Status` TEXT, `ModeloEquipamentoItemIdOriginal` INTEGER NOT NULL, `Localizacao` TEXT, `TAGID` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ModeloEquipamentos` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Modelo` TEXT, `DescricaoTecnica` TEXT, `PartNumber` TEXT, `Fabricante` TEXT, `Categoria` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Posicoes` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Codigo` TEXT, `Descricao` TEXT, `Almoxarifado` TEXT, `TAGID` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ServicosAdicionais` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Servico` TEXT, `Descricao` TEXT, `Modalidade` TEXT, `FlagObrigatorio` INTEGER, `FlagAtivo` INTEGER, `TarefaItemIdOriginal` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Tarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Codigo` TEXT, `Titulo` TEXT, `Descricao` TEXT, `FlagDependenciaServico` INTEGER, `FlagDependenciaMaterial` INTEGER, `CategoriaEquipamentos` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Proprietarios` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Descricao` TEXT, `Empresa` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ModeloMateriais` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Modelo` TEXT, `IDOmni` TEXT, `PartNumber` TEXT, `DescricaoTecnica` TEXT, `Familia` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `InventarioPlanejado` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Descricao` TEXT, `ApplicationUserItemIdOriginal` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ListaTarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Status` TEXT, `DataInicio` TEXT, `DataFimPrevisao` TEXT, `DataFimReal` TEXT, `DataCancelamento` TEXT, `Dominio` TEXT, `Processo` INTEGER NOT NULL, `TarefaItemIdOriginal` INTEGER NOT NULL, `CadsatroEquipamentosItemIdOriginal` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ListaServicosListaTarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Status` TEXT, `DataInicio` TEXT, `DataConclusao` TEXT, `Resultado` TEXT, `ListaTarefasItemIdOriginal` INTEGER NOT NULL, `ServicoAdicinalItemIdOriginal` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ListaMateriaisListaTarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `Status` TEXT, `DataInicio` TEXT, `DataConclusao` TEXT, `Observacao` TEXT, `ListaTarefasItemIdOriginal` INTEGER NOT NULL, `CadastroMateriaisItemIdOriginal` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Grupos` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` TEXT, `RowVersion` TEXT, `Titulo` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CadastroMateriaisItens` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `NumSerie` TEXT, `Patrimonio` TEXT, `Quantidade` INTEGER NOT NULL, `DataCadastro` TEXT, `DataFabricacao` TEXT, `DataValidade` TEXT, `CadastroMateriaisItemIdOriginal` INTEGER NOT NULL, `ModeloMateriaisItemIdOriginal` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CadastroMateriais` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `NumSerie` TEXT, `Patrimonio` TEXT, `Quantidade` INTEGER NOT NULL, `DataCadastro` TEXT, `DataFabricacao` TEXT, `DataValidade` TEXT, `ValorUnitario` REAL NOT NULL, `DadosTecnicos` TEXT, `NotaFiscal` TEXT, `DataEntradaNotaFiscal` TEXT, `Categoria` TEXT, `ModeloMateriaisItemIdOriginal` INTEGER NOT NULL, `PosicaoOriginalItemIdoriginal` INTEGER NOT NULL, `TAGID` TEXT, `EmUso` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Usuarios` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` TEXT, `RowVersion` TEXT, `UserName` TEXT, `NomeCompleto` TEXT, `Email` TEXT, `Permissao` TEXT, `TAGID` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ParametrosPadrao` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `RowVersion` TEXT, `CodAlmoxarifado` TEXT, `SetorProprietario` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBUsuarios` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` TEXT, `RoleIdOriginal` TEXT, `Username` TEXT, `Email` TEXT, `NomeCompleto` TEXT, `TAGID` TEXT, `EnviarSenhaEmail` INTEGER NOT NULL, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER, `CodColetor` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBListaServicosListaTarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `CodTarefa` TEXT, `Servico` TEXT, `Resultado` TEXT, `Status` TEXT, `DataHoraEvento` TEXT, `DataInicio` TEXT, `DataConclusao` TEXT, `DataCancelamento` TEXT, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBListaTarefas` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `CodTarefa` TEXT, `Status` TEXT, `TraceNumber` TEXT, `DataInicio` TEXT, `DataFimReal` TEXT, `DataHoraEvento` TEXT, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBHistoricoLocalizacao` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `TAGID` TEXT, `TAGIDPosicao` TEXT, `DataHoraEvento` TEXT, `Processo` TEXT, `Dominio` TEXT, `Quantidade` INTEGER NOT NULL, `Modalidade` TEXT, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBCadastroMateriaisItens` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `Patrimonio` TEXT, `NumSerie` TEXT, `Quantidade` INTEGER NOT NULL, `DataHoraEvento` TEXT, `DataValidade` TEXT, `DataFabricacao` TEXT, `CadastroMateriaisItemIdOriginal` INTEGER NOT NULL, `ModeloMateriaisItemIdOriginal` INTEGER NOT NULL, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBCadastroMateriais` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `IdOriginal` INTEGER NOT NULL, `Patrimonio` TEXT, `NumSerie` TEXT, `Quantidade` INTEGER NOT NULL, `DataFabricacao` TEXT, `DataValidade` TEXT, `DataHoraEvento` TEXT, `DadosTecnicos` TEXT, `TAGID` TEXT, `PosicaoOriginalItemId` INTEGER NOT NULL, `NotaFiscal` TEXT, `DataEntradaNotaFiscal` TEXT, `ValorUnitario` REAL NOT NULL, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER, `FlagAtualizar` INTEGER, `FlagProcess` INTEGER, `ModeloMateriaisItemId` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UPMOBDescartes` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CadastromateriaisId` INTEGER NOT NULL, `ApplicationUserId` TEXT, `Motivo` TEXT, `DataHoraEvento` TEXT, `CodColetor` TEXT, `DescricaoErro` TEXT, `FlagErro` INTEGER NOT NULL, `FlagProcess` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"452df62e63393c80bdc064583745f4f6\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `CadastroEquipamentos`");
        _db.execSQL("DROP TABLE IF EXISTS `ModeloEquipamentos`");
        _db.execSQL("DROP TABLE IF EXISTS `Posicoes`");
        _db.execSQL("DROP TABLE IF EXISTS `ServicosAdicionais`");
        _db.execSQL("DROP TABLE IF EXISTS `Tarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `Proprietarios`");
        _db.execSQL("DROP TABLE IF EXISTS `ModeloMateriais`");
        _db.execSQL("DROP TABLE IF EXISTS `InventarioPlanejado`");
        _db.execSQL("DROP TABLE IF EXISTS `ListaTarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `ListaServicosListaTarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `ListaMateriaisListaTarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `Grupos`");
        _db.execSQL("DROP TABLE IF EXISTS `CadastroMateriaisItens`");
        _db.execSQL("DROP TABLE IF EXISTS `CadastroMateriais`");
        _db.execSQL("DROP TABLE IF EXISTS `Usuarios`");
        _db.execSQL("DROP TABLE IF EXISTS `ParametrosPadrao`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBUsuarios`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBListaServicosListaTarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBListaTarefas`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBHistoricoLocalizacao`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBCadastroMateriaisItens`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBCadastroMateriais`");
        _db.execSQL("DROP TABLE IF EXISTS `UPMOBDescartes`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCadastroEquipamentos = new HashMap<String, TableInfo.Column>(10);
        _columnsCadastroEquipamentos.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsCadastroEquipamentos.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsCadastroEquipamentos.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("TraceNumber", new TableInfo.Column("TraceNumber", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("DataCadastro", new TableInfo.Column("DataCadastro", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("DataFabricacao", new TableInfo.Column("DataFabricacao", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("ModeloEquipamentoItemIdOriginal", new TableInfo.Column("ModeloEquipamentoItemIdOriginal", "INTEGER", true, 0));
        _columnsCadastroEquipamentos.put("Localizacao", new TableInfo.Column("Localizacao", "TEXT", false, 0));
        _columnsCadastroEquipamentos.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCadastroEquipamentos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCadastroEquipamentos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCadastroEquipamentos = new TableInfo("CadastroEquipamentos", _columnsCadastroEquipamentos, _foreignKeysCadastroEquipamentos, _indicesCadastroEquipamentos);
        final TableInfo _existingCadastroEquipamentos = TableInfo.read(_db, "CadastroEquipamentos");
        if (! _infoCadastroEquipamentos.equals(_existingCadastroEquipamentos)) {
          throw new IllegalStateException("Migration didn't properly handle CadastroEquipamentos(br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos).\n"
                  + " Expected:\n" + _infoCadastroEquipamentos + "\n"
                  + " Found:\n" + _existingCadastroEquipamentos);
        }
        final HashMap<String, TableInfo.Column> _columnsModeloEquipamentos = new HashMap<String, TableInfo.Column>(8);
        _columnsModeloEquipamentos.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsModeloEquipamentos.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsModeloEquipamentos.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsModeloEquipamentos.put("Modelo", new TableInfo.Column("Modelo", "TEXT", false, 0));
        _columnsModeloEquipamentos.put("DescricaoTecnica", new TableInfo.Column("DescricaoTecnica", "TEXT", false, 0));
        _columnsModeloEquipamentos.put("PartNumber", new TableInfo.Column("PartNumber", "TEXT", false, 0));
        _columnsModeloEquipamentos.put("Fabricante", new TableInfo.Column("Fabricante", "TEXT", false, 0));
        _columnsModeloEquipamentos.put("Categoria", new TableInfo.Column("Categoria", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysModeloEquipamentos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesModeloEquipamentos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoModeloEquipamentos = new TableInfo("ModeloEquipamentos", _columnsModeloEquipamentos, _foreignKeysModeloEquipamentos, _indicesModeloEquipamentos);
        final TableInfo _existingModeloEquipamentos = TableInfo.read(_db, "ModeloEquipamentos");
        if (! _infoModeloEquipamentos.equals(_existingModeloEquipamentos)) {
          throw new IllegalStateException("Migration didn't properly handle ModeloEquipamentos(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos).\n"
                  + " Expected:\n" + _infoModeloEquipamentos + "\n"
                  + " Found:\n" + _existingModeloEquipamentos);
        }
        final HashMap<String, TableInfo.Column> _columnsPosicoes = new HashMap<String, TableInfo.Column>(7);
        _columnsPosicoes.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsPosicoes.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsPosicoes.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsPosicoes.put("Codigo", new TableInfo.Column("Codigo", "TEXT", false, 0));
        _columnsPosicoes.put("Descricao", new TableInfo.Column("Descricao", "TEXT", false, 0));
        _columnsPosicoes.put("Almoxarifado", new TableInfo.Column("Almoxarifado", "TEXT", false, 0));
        _columnsPosicoes.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPosicoes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPosicoes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPosicoes = new TableInfo("Posicoes", _columnsPosicoes, _foreignKeysPosicoes, _indicesPosicoes);
        final TableInfo _existingPosicoes = TableInfo.read(_db, "Posicoes");
        if (! _infoPosicoes.equals(_existingPosicoes)) {
          throw new IllegalStateException("Migration didn't properly handle Posicoes(br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes).\n"
                  + " Expected:\n" + _infoPosicoes + "\n"
                  + " Found:\n" + _existingPosicoes);
        }
        final HashMap<String, TableInfo.Column> _columnsServicosAdicionais = new HashMap<String, TableInfo.Column>(9);
        _columnsServicosAdicionais.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsServicosAdicionais.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsServicosAdicionais.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsServicosAdicionais.put("Servico", new TableInfo.Column("Servico", "TEXT", false, 0));
        _columnsServicosAdicionais.put("Descricao", new TableInfo.Column("Descricao", "TEXT", false, 0));
        _columnsServicosAdicionais.put("Modalidade", new TableInfo.Column("Modalidade", "TEXT", false, 0));
        _columnsServicosAdicionais.put("FlagObrigatorio", new TableInfo.Column("FlagObrigatorio", "INTEGER", false, 0));
        _columnsServicosAdicionais.put("FlagAtivo", new TableInfo.Column("FlagAtivo", "INTEGER", false, 0));
        _columnsServicosAdicionais.put("TarefaItemIdOriginal", new TableInfo.Column("TarefaItemIdOriginal", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysServicosAdicionais = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesServicosAdicionais = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoServicosAdicionais = new TableInfo("ServicosAdicionais", _columnsServicosAdicionais, _foreignKeysServicosAdicionais, _indicesServicosAdicionais);
        final TableInfo _existingServicosAdicionais = TableInfo.read(_db, "ServicosAdicionais");
        if (! _infoServicosAdicionais.equals(_existingServicosAdicionais)) {
          throw new IllegalStateException("Migration didn't properly handle ServicosAdicionais(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais).\n"
                  + " Expected:\n" + _infoServicosAdicionais + "\n"
                  + " Found:\n" + _existingServicosAdicionais);
        }
        final HashMap<String, TableInfo.Column> _columnsTarefas = new HashMap<String, TableInfo.Column>(9);
        _columnsTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsTarefas.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsTarefas.put("Codigo", new TableInfo.Column("Codigo", "TEXT", false, 0));
        _columnsTarefas.put("Titulo", new TableInfo.Column("Titulo", "TEXT", false, 0));
        _columnsTarefas.put("Descricao", new TableInfo.Column("Descricao", "TEXT", false, 0));
        _columnsTarefas.put("FlagDependenciaServico", new TableInfo.Column("FlagDependenciaServico", "INTEGER", false, 0));
        _columnsTarefas.put("FlagDependenciaMaterial", new TableInfo.Column("FlagDependenciaMaterial", "INTEGER", false, 0));
        _columnsTarefas.put("CategoriaEquipamentos", new TableInfo.Column("CategoriaEquipamentos", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTarefas = new TableInfo("Tarefas", _columnsTarefas, _foreignKeysTarefas, _indicesTarefas);
        final TableInfo _existingTarefas = TableInfo.read(_db, "Tarefas");
        if (! _infoTarefas.equals(_existingTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle Tarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas).\n"
                  + " Expected:\n" + _infoTarefas + "\n"
                  + " Found:\n" + _existingTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsProprietarios = new HashMap<String, TableInfo.Column>(5);
        _columnsProprietarios.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsProprietarios.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsProprietarios.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsProprietarios.put("Descricao", new TableInfo.Column("Descricao", "TEXT", false, 0));
        _columnsProprietarios.put("Empresa", new TableInfo.Column("Empresa", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProprietarios = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProprietarios = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProprietarios = new TableInfo("Proprietarios", _columnsProprietarios, _foreignKeysProprietarios, _indicesProprietarios);
        final TableInfo _existingProprietarios = TableInfo.read(_db, "Proprietarios");
        if (! _infoProprietarios.equals(_existingProprietarios)) {
          throw new IllegalStateException("Migration didn't properly handle Proprietarios(br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios).\n"
                  + " Expected:\n" + _infoProprietarios + "\n"
                  + " Found:\n" + _existingProprietarios);
        }
        final HashMap<String, TableInfo.Column> _columnsModeloMateriais = new HashMap<String, TableInfo.Column>(8);
        _columnsModeloMateriais.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsModeloMateriais.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsModeloMateriais.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsModeloMateriais.put("Modelo", new TableInfo.Column("Modelo", "TEXT", false, 0));
        _columnsModeloMateriais.put("IDOmni", new TableInfo.Column("IDOmni", "TEXT", false, 0));
        _columnsModeloMateriais.put("PartNumber", new TableInfo.Column("PartNumber", "TEXT", false, 0));
        _columnsModeloMateriais.put("DescricaoTecnica", new TableInfo.Column("DescricaoTecnica", "TEXT", false, 0));
        _columnsModeloMateriais.put("Familia", new TableInfo.Column("Familia", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysModeloMateriais = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesModeloMateriais = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoModeloMateriais = new TableInfo("ModeloMateriais", _columnsModeloMateriais, _foreignKeysModeloMateriais, _indicesModeloMateriais);
        final TableInfo _existingModeloMateriais = TableInfo.read(_db, "ModeloMateriais");
        if (! _infoModeloMateriais.equals(_existingModeloMateriais)) {
          throw new IllegalStateException("Migration didn't properly handle ModeloMateriais(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais).\n"
                  + " Expected:\n" + _infoModeloMateriais + "\n"
                  + " Found:\n" + _existingModeloMateriais);
        }
        final HashMap<String, TableInfo.Column> _columnsInventarioPlanejado = new HashMap<String, TableInfo.Column>(5);
        _columnsInventarioPlanejado.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsInventarioPlanejado.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsInventarioPlanejado.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsInventarioPlanejado.put("Descricao", new TableInfo.Column("Descricao", "TEXT", false, 0));
        _columnsInventarioPlanejado.put("ApplicationUserItemIdOriginal", new TableInfo.Column("ApplicationUserItemIdOriginal", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInventarioPlanejado = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInventarioPlanejado = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInventarioPlanejado = new TableInfo("InventarioPlanejado", _columnsInventarioPlanejado, _foreignKeysInventarioPlanejado, _indicesInventarioPlanejado);
        final TableInfo _existingInventarioPlanejado = TableInfo.read(_db, "InventarioPlanejado");
        if (! _infoInventarioPlanejado.equals(_existingInventarioPlanejado)) {
          throw new IllegalStateException("Migration didn't properly handle InventarioPlanejado(br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado).\n"
                  + " Expected:\n" + _infoInventarioPlanejado + "\n"
                  + " Found:\n" + _existingInventarioPlanejado);
        }
        final HashMap<String, TableInfo.Column> _columnsListaTarefas = new HashMap<String, TableInfo.Column>(12);
        _columnsListaTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsListaTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsListaTarefas.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsListaTarefas.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsListaTarefas.put("DataInicio", new TableInfo.Column("DataInicio", "TEXT", false, 0));
        _columnsListaTarefas.put("DataFimPrevisao", new TableInfo.Column("DataFimPrevisao", "TEXT", false, 0));
        _columnsListaTarefas.put("DataFimReal", new TableInfo.Column("DataFimReal", "TEXT", false, 0));
        _columnsListaTarefas.put("DataCancelamento", new TableInfo.Column("DataCancelamento", "TEXT", false, 0));
        _columnsListaTarefas.put("Dominio", new TableInfo.Column("Dominio", "TEXT", false, 0));
        _columnsListaTarefas.put("Processo", new TableInfo.Column("Processo", "INTEGER", true, 0));
        _columnsListaTarefas.put("TarefaItemIdOriginal", new TableInfo.Column("TarefaItemIdOriginal", "INTEGER", true, 0));
        _columnsListaTarefas.put("CadsatroEquipamentosItemIdOriginal", new TableInfo.Column("CadsatroEquipamentosItemIdOriginal", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListaTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListaTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListaTarefas = new TableInfo("ListaTarefas", _columnsListaTarefas, _foreignKeysListaTarefas, _indicesListaTarefas);
        final TableInfo _existingListaTarefas = TableInfo.read(_db, "ListaTarefas");
        if (! _infoListaTarefas.equals(_existingListaTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle ListaTarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas).\n"
                  + " Expected:\n" + _infoListaTarefas + "\n"
                  + " Found:\n" + _existingListaTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsListaServicosListaTarefas = new HashMap<String, TableInfo.Column>(9);
        _columnsListaServicosListaTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsListaServicosListaTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsListaServicosListaTarefas.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsListaServicosListaTarefas.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsListaServicosListaTarefas.put("DataInicio", new TableInfo.Column("DataInicio", "TEXT", false, 0));
        _columnsListaServicosListaTarefas.put("DataConclusao", new TableInfo.Column("DataConclusao", "TEXT", false, 0));
        _columnsListaServicosListaTarefas.put("Resultado", new TableInfo.Column("Resultado", "TEXT", false, 0));
        _columnsListaServicosListaTarefas.put("ListaTarefasItemIdOriginal", new TableInfo.Column("ListaTarefasItemIdOriginal", "INTEGER", true, 0));
        _columnsListaServicosListaTarefas.put("ServicoAdicinalItemIdOriginal", new TableInfo.Column("ServicoAdicinalItemIdOriginal", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListaServicosListaTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListaServicosListaTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListaServicosListaTarefas = new TableInfo("ListaServicosListaTarefas", _columnsListaServicosListaTarefas, _foreignKeysListaServicosListaTarefas, _indicesListaServicosListaTarefas);
        final TableInfo _existingListaServicosListaTarefas = TableInfo.read(_db, "ListaServicosListaTarefas");
        if (! _infoListaServicosListaTarefas.equals(_existingListaServicosListaTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle ListaServicosListaTarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas).\n"
                  + " Expected:\n" + _infoListaServicosListaTarefas + "\n"
                  + " Found:\n" + _existingListaServicosListaTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsListaMateriaisListaTarefas = new HashMap<String, TableInfo.Column>(9);
        _columnsListaMateriaisListaTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsListaMateriaisListaTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsListaMateriaisListaTarefas.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsListaMateriaisListaTarefas.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsListaMateriaisListaTarefas.put("DataInicio", new TableInfo.Column("DataInicio", "TEXT", false, 0));
        _columnsListaMateriaisListaTarefas.put("DataConclusao", new TableInfo.Column("DataConclusao", "TEXT", false, 0));
        _columnsListaMateriaisListaTarefas.put("Observacao", new TableInfo.Column("Observacao", "TEXT", false, 0));
        _columnsListaMateriaisListaTarefas.put("ListaTarefasItemIdOriginal", new TableInfo.Column("ListaTarefasItemIdOriginal", "INTEGER", true, 0));
        _columnsListaMateriaisListaTarefas.put("CadastroMateriaisItemIdOriginal", new TableInfo.Column("CadastroMateriaisItemIdOriginal", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListaMateriaisListaTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListaMateriaisListaTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListaMateriaisListaTarefas = new TableInfo("ListaMateriaisListaTarefas", _columnsListaMateriaisListaTarefas, _foreignKeysListaMateriaisListaTarefas, _indicesListaMateriaisListaTarefas);
        final TableInfo _existingListaMateriaisListaTarefas = TableInfo.read(_db, "ListaMateriaisListaTarefas");
        if (! _infoListaMateriaisListaTarefas.equals(_existingListaMateriaisListaTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle ListaMateriaisListaTarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas).\n"
                  + " Expected:\n" + _infoListaMateriaisListaTarefas + "\n"
                  + " Found:\n" + _existingListaMateriaisListaTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsGrupos = new HashMap<String, TableInfo.Column>(4);
        _columnsGrupos.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsGrupos.put("IdOriginal", new TableInfo.Column("IdOriginal", "TEXT", false, 0));
        _columnsGrupos.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsGrupos.put("Titulo", new TableInfo.Column("Titulo", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGrupos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGrupos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGrupos = new TableInfo("Grupos", _columnsGrupos, _foreignKeysGrupos, _indicesGrupos);
        final TableInfo _existingGrupos = TableInfo.read(_db, "Grupos");
        if (! _infoGrupos.equals(_existingGrupos)) {
          throw new IllegalStateException("Migration didn't properly handle Grupos(br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos).\n"
                  + " Expected:\n" + _infoGrupos + "\n"
                  + " Found:\n" + _existingGrupos);
        }
        final HashMap<String, TableInfo.Column> _columnsCadastroMateriaisItens = new HashMap<String, TableInfo.Column>(11);
        _columnsCadastroMateriaisItens.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsCadastroMateriaisItens.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsCadastroMateriaisItens.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("NumSerie", new TableInfo.Column("NumSerie", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("Patrimonio", new TableInfo.Column("Patrimonio", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("Quantidade", new TableInfo.Column("Quantidade", "INTEGER", true, 0));
        _columnsCadastroMateriaisItens.put("DataCadastro", new TableInfo.Column("DataCadastro", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("DataFabricacao", new TableInfo.Column("DataFabricacao", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("DataValidade", new TableInfo.Column("DataValidade", "TEXT", false, 0));
        _columnsCadastroMateriaisItens.put("CadastroMateriaisItemIdOriginal", new TableInfo.Column("CadastroMateriaisItemIdOriginal", "INTEGER", true, 0));
        _columnsCadastroMateriaisItens.put("ModeloMateriaisItemIdOriginal", new TableInfo.Column("ModeloMateriaisItemIdOriginal", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCadastroMateriaisItens = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCadastroMateriaisItens = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCadastroMateriaisItens = new TableInfo("CadastroMateriaisItens", _columnsCadastroMateriaisItens, _foreignKeysCadastroMateriaisItens, _indicesCadastroMateriaisItens);
        final TableInfo _existingCadastroMateriaisItens = TableInfo.read(_db, "CadastroMateriaisItens");
        if (! _infoCadastroMateriaisItens.equals(_existingCadastroMateriaisItens)) {
          throw new IllegalStateException("Migration didn't properly handle CadastroMateriaisItens(br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens).\n"
                  + " Expected:\n" + _infoCadastroMateriaisItens + "\n"
                  + " Found:\n" + _existingCadastroMateriaisItens);
        }
        final HashMap<String, TableInfo.Column> _columnsCadastroMateriais = new HashMap<String, TableInfo.Column>(18);
        _columnsCadastroMateriais.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsCadastroMateriais.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsCadastroMateriais.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsCadastroMateriais.put("NumSerie", new TableInfo.Column("NumSerie", "TEXT", false, 0));
        _columnsCadastroMateriais.put("Patrimonio", new TableInfo.Column("Patrimonio", "TEXT", false, 0));
        _columnsCadastroMateriais.put("Quantidade", new TableInfo.Column("Quantidade", "INTEGER", true, 0));
        _columnsCadastroMateriais.put("DataCadastro", new TableInfo.Column("DataCadastro", "TEXT", false, 0));
        _columnsCadastroMateriais.put("DataFabricacao", new TableInfo.Column("DataFabricacao", "TEXT", false, 0));
        _columnsCadastroMateriais.put("DataValidade", new TableInfo.Column("DataValidade", "TEXT", false, 0));
        _columnsCadastroMateriais.put("ValorUnitario", new TableInfo.Column("ValorUnitario", "REAL", true, 0));
        _columnsCadastroMateriais.put("DadosTecnicos", new TableInfo.Column("DadosTecnicos", "TEXT", false, 0));
        _columnsCadastroMateriais.put("NotaFiscal", new TableInfo.Column("NotaFiscal", "TEXT", false, 0));
        _columnsCadastroMateriais.put("DataEntradaNotaFiscal", new TableInfo.Column("DataEntradaNotaFiscal", "TEXT", false, 0));
        _columnsCadastroMateriais.put("Categoria", new TableInfo.Column("Categoria", "TEXT", false, 0));
        _columnsCadastroMateriais.put("ModeloMateriaisItemIdOriginal", new TableInfo.Column("ModeloMateriaisItemIdOriginal", "INTEGER", true, 0));
        _columnsCadastroMateriais.put("PosicaoOriginalItemIdoriginal", new TableInfo.Column("PosicaoOriginalItemIdoriginal", "INTEGER", true, 0));
        _columnsCadastroMateriais.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        _columnsCadastroMateriais.put("EmUso", new TableInfo.Column("EmUso", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCadastroMateriais = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCadastroMateriais = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCadastroMateriais = new TableInfo("CadastroMateriais", _columnsCadastroMateriais, _foreignKeysCadastroMateriais, _indicesCadastroMateriais);
        final TableInfo _existingCadastroMateriais = TableInfo.read(_db, "CadastroMateriais");
        if (! _infoCadastroMateriais.equals(_existingCadastroMateriais)) {
          throw new IllegalStateException("Migration didn't properly handle CadastroMateriais(br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais).\n"
                  + " Expected:\n" + _infoCadastroMateriais + "\n"
                  + " Found:\n" + _existingCadastroMateriais);
        }
        final HashMap<String, TableInfo.Column> _columnsUsuarios = new HashMap<String, TableInfo.Column>(8);
        _columnsUsuarios.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUsuarios.put("IdOriginal", new TableInfo.Column("IdOriginal", "TEXT", false, 0));
        _columnsUsuarios.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsUsuarios.put("UserName", new TableInfo.Column("UserName", "TEXT", false, 0));
        _columnsUsuarios.put("NomeCompleto", new TableInfo.Column("NomeCompleto", "TEXT", false, 0));
        _columnsUsuarios.put("Email", new TableInfo.Column("Email", "TEXT", false, 0));
        _columnsUsuarios.put("Permissao", new TableInfo.Column("Permissao", "TEXT", false, 0));
        _columnsUsuarios.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsuarios = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsuarios = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsuarios = new TableInfo("Usuarios", _columnsUsuarios, _foreignKeysUsuarios, _indicesUsuarios);
        final TableInfo _existingUsuarios = TableInfo.read(_db, "Usuarios");
        if (! _infoUsuarios.equals(_existingUsuarios)) {
          throw new IllegalStateException("Migration didn't properly handle Usuarios(br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios).\n"
                  + " Expected:\n" + _infoUsuarios + "\n"
                  + " Found:\n" + _existingUsuarios);
        }
        final HashMap<String, TableInfo.Column> _columnsParametrosPadrao = new HashMap<String, TableInfo.Column>(5);
        _columnsParametrosPadrao.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsParametrosPadrao.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsParametrosPadrao.put("RowVersion", new TableInfo.Column("RowVersion", "TEXT", false, 0));
        _columnsParametrosPadrao.put("CodAlmoxarifado", new TableInfo.Column("CodAlmoxarifado", "TEXT", false, 0));
        _columnsParametrosPadrao.put("SetorProprietario", new TableInfo.Column("SetorProprietario", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysParametrosPadrao = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesParametrosPadrao = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoParametrosPadrao = new TableInfo("ParametrosPadrao", _columnsParametrosPadrao, _foreignKeysParametrosPadrao, _indicesParametrosPadrao);
        final TableInfo _existingParametrosPadrao = TableInfo.read(_db, "ParametrosPadrao");
        if (! _infoParametrosPadrao.equals(_existingParametrosPadrao)) {
          throw new IllegalStateException("Migration didn't properly handle ParametrosPadrao(br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao).\n"
                  + " Expected:\n" + _infoParametrosPadrao + "\n"
                  + " Found:\n" + _existingParametrosPadrao);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBUsuarios = new HashMap<String, TableInfo.Column>(13);
        _columnsUPMOBUsuarios.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBUsuarios.put("IdOriginal", new TableInfo.Column("IdOriginal", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("RoleIdOriginal", new TableInfo.Column("RoleIdOriginal", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("Username", new TableInfo.Column("Username", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("Email", new TableInfo.Column("Email", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("NomeCompleto", new TableInfo.Column("NomeCompleto", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("EnviarSenhaEmail", new TableInfo.Column("EnviarSenhaEmail", "INTEGER", true, 0));
        _columnsUPMOBUsuarios.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBUsuarios.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBUsuarios.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBUsuarios.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        _columnsUPMOBUsuarios.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBUsuarios = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBUsuarios = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBUsuarios = new TableInfo("UPMOBUsuarios", _columnsUPMOBUsuarios, _foreignKeysUPMOBUsuarios, _indicesUPMOBUsuarios);
        final TableInfo _existingUPMOBUsuarios = TableInfo.read(_db, "UPMOBUsuarios");
        if (! _infoUPMOBUsuarios.equals(_existingUPMOBUsuarios)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBUsuarios(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios).\n"
                  + " Expected:\n" + _infoUPMOBUsuarios + "\n"
                  + " Found:\n" + _existingUPMOBUsuarios);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBListaServicosListaTarefas = new HashMap<String, TableInfo.Column>(15);
        _columnsUPMOBListaServicosListaTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBListaServicosListaTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsUPMOBListaServicosListaTarefas.put("CodTarefa", new TableInfo.Column("CodTarefa", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("Servico", new TableInfo.Column("Servico", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("Resultado", new TableInfo.Column("Resultado", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("DataInicio", new TableInfo.Column("DataInicio", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("DataConclusao", new TableInfo.Column("DataConclusao", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("DataCancelamento", new TableInfo.Column("DataCancelamento", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBListaServicosListaTarefas.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBListaServicosListaTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBListaServicosListaTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBListaServicosListaTarefas = new TableInfo("UPMOBListaServicosListaTarefas", _columnsUPMOBListaServicosListaTarefas, _foreignKeysUPMOBListaServicosListaTarefas, _indicesUPMOBListaServicosListaTarefas);
        final TableInfo _existingUPMOBListaServicosListaTarefas = TableInfo.read(_db, "UPMOBListaServicosListaTarefas");
        if (! _infoUPMOBListaServicosListaTarefas.equals(_existingUPMOBListaServicosListaTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBListaServicosListaTarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas).\n"
                  + " Expected:\n" + _infoUPMOBListaServicosListaTarefas + "\n"
                  + " Found:\n" + _existingUPMOBListaServicosListaTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBListaTarefas = new HashMap<String, TableInfo.Column>(13);
        _columnsUPMOBListaTarefas.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBListaTarefas.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsUPMOBListaTarefas.put("CodTarefa", new TableInfo.Column("CodTarefa", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("Status", new TableInfo.Column("Status", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("TraceNumber", new TableInfo.Column("TraceNumber", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("DataInicio", new TableInfo.Column("DataInicio", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("DataFimReal", new TableInfo.Column("DataFimReal", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBListaTarefas.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBListaTarefas.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBListaTarefas.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBListaTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBListaTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBListaTarefas = new TableInfo("UPMOBListaTarefas", _columnsUPMOBListaTarefas, _foreignKeysUPMOBListaTarefas, _indicesUPMOBListaTarefas);
        final TableInfo _existingUPMOBListaTarefas = TableInfo.read(_db, "UPMOBListaTarefas");
        if (! _infoUPMOBListaTarefas.equals(_existingUPMOBListaTarefas)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBListaTarefas(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas).\n"
                  + " Expected:\n" + _infoUPMOBListaTarefas + "\n"
                  + " Found:\n" + _existingUPMOBListaTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBHistoricoLocalizacao = new HashMap<String, TableInfo.Column>(13);
        _columnsUPMOBHistoricoLocalizacao.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBHistoricoLocalizacao.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("TAGIDPosicao", new TableInfo.Column("TAGIDPosicao", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("Processo", new TableInfo.Column("Processo", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("Dominio", new TableInfo.Column("Dominio", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("Quantidade", new TableInfo.Column("Quantidade", "INTEGER", true, 0));
        _columnsUPMOBHistoricoLocalizacao.put("Modalidade", new TableInfo.Column("Modalidade", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBHistoricoLocalizacao.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBHistoricoLocalizacao = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBHistoricoLocalizacao = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBHistoricoLocalizacao = new TableInfo("UPMOBHistoricoLocalizacao", _columnsUPMOBHistoricoLocalizacao, _foreignKeysUPMOBHistoricoLocalizacao, _indicesUPMOBHistoricoLocalizacao);
        final TableInfo _existingUPMOBHistoricoLocalizacao = TableInfo.read(_db, "UPMOBHistoricoLocalizacao");
        if (! _infoUPMOBHistoricoLocalizacao.equals(_existingUPMOBHistoricoLocalizacao)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBHistoricoLocalizacao(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao).\n"
                  + " Expected:\n" + _infoUPMOBHistoricoLocalizacao + "\n"
                  + " Found:\n" + _existingUPMOBHistoricoLocalizacao);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBCadastroMateriaisItens = new HashMap<String, TableInfo.Column>(15);
        _columnsUPMOBCadastroMateriaisItens.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBCadastroMateriaisItens.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriaisItens.put("Patrimonio", new TableInfo.Column("Patrimonio", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("NumSerie", new TableInfo.Column("NumSerie", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("Quantidade", new TableInfo.Column("Quantidade", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriaisItens.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("DataValidade", new TableInfo.Column("DataValidade", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("DataFabricacao", new TableInfo.Column("DataFabricacao", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("CadastroMateriaisItemIdOriginal", new TableInfo.Column("CadastroMateriaisItemIdOriginal", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriaisItens.put("ModeloMateriaisItemIdOriginal", new TableInfo.Column("ModeloMateriaisItemIdOriginal", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriaisItens.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBCadastroMateriaisItens.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBCadastroMateriaisItens = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBCadastroMateriaisItens = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBCadastroMateriaisItens = new TableInfo("UPMOBCadastroMateriaisItens", _columnsUPMOBCadastroMateriaisItens, _foreignKeysUPMOBCadastroMateriaisItens, _indicesUPMOBCadastroMateriaisItens);
        final TableInfo _existingUPMOBCadastroMateriaisItens = TableInfo.read(_db, "UPMOBCadastroMateriaisItens");
        if (! _infoUPMOBCadastroMateriaisItens.equals(_existingUPMOBCadastroMateriaisItens)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBCadastroMateriaisItens(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens).\n"
                  + " Expected:\n" + _infoUPMOBCadastroMateriaisItens + "\n"
                  + " Found:\n" + _existingUPMOBCadastroMateriaisItens);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBCadastroMateriais = new HashMap<String, TableInfo.Column>(20);
        _columnsUPMOBCadastroMateriais.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBCadastroMateriais.put("IdOriginal", new TableInfo.Column("IdOriginal", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriais.put("Patrimonio", new TableInfo.Column("Patrimonio", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("NumSerie", new TableInfo.Column("NumSerie", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("Quantidade", new TableInfo.Column("Quantidade", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriais.put("DataFabricacao", new TableInfo.Column("DataFabricacao", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("DataValidade", new TableInfo.Column("DataValidade", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("DadosTecnicos", new TableInfo.Column("DadosTecnicos", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("TAGID", new TableInfo.Column("TAGID", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("PosicaoOriginalItemId", new TableInfo.Column("PosicaoOriginalItemId", "INTEGER", true, 0));
        _columnsUPMOBCadastroMateriais.put("NotaFiscal", new TableInfo.Column("NotaFiscal", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("DataEntradaNotaFiscal", new TableInfo.Column("DataEntradaNotaFiscal", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("ValorUnitario", new TableInfo.Column("ValorUnitario", "REAL", true, 0));
        _columnsUPMOBCadastroMateriais.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBCadastroMateriais.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", false, 0));
        _columnsUPMOBCadastroMateriais.put("FlagAtualizar", new TableInfo.Column("FlagAtualizar", "INTEGER", false, 0));
        _columnsUPMOBCadastroMateriais.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", false, 0));
        _columnsUPMOBCadastroMateriais.put("ModeloMateriaisItemId", new TableInfo.Column("ModeloMateriaisItemId", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBCadastroMateriais = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBCadastroMateriais = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBCadastroMateriais = new TableInfo("UPMOBCadastroMateriais", _columnsUPMOBCadastroMateriais, _foreignKeysUPMOBCadastroMateriais, _indicesUPMOBCadastroMateriais);
        final TableInfo _existingUPMOBCadastroMateriais = TableInfo.read(_db, "UPMOBCadastroMateriais");
        if (! _infoUPMOBCadastroMateriais.equals(_existingUPMOBCadastroMateriais)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBCadastroMateriais(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais).\n"
                  + " Expected:\n" + _infoUPMOBCadastroMateriais + "\n"
                  + " Found:\n" + _existingUPMOBCadastroMateriais);
        }
        final HashMap<String, TableInfo.Column> _columnsUPMOBDescartes = new HashMap<String, TableInfo.Column>(9);
        _columnsUPMOBDescartes.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsUPMOBDescartes.put("CadastromateriaisId", new TableInfo.Column("CadastromateriaisId", "INTEGER", true, 0));
        _columnsUPMOBDescartes.put("ApplicationUserId", new TableInfo.Column("ApplicationUserId", "TEXT", false, 0));
        _columnsUPMOBDescartes.put("Motivo", new TableInfo.Column("Motivo", "TEXT", false, 0));
        _columnsUPMOBDescartes.put("DataHoraEvento", new TableInfo.Column("DataHoraEvento", "TEXT", false, 0));
        _columnsUPMOBDescartes.put("CodColetor", new TableInfo.Column("CodColetor", "TEXT", false, 0));
        _columnsUPMOBDescartes.put("DescricaoErro", new TableInfo.Column("DescricaoErro", "TEXT", false, 0));
        _columnsUPMOBDescartes.put("FlagErro", new TableInfo.Column("FlagErro", "INTEGER", true, 0));
        _columnsUPMOBDescartes.put("FlagProcess", new TableInfo.Column("FlagProcess", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUPMOBDescartes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUPMOBDescartes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUPMOBDescartes = new TableInfo("UPMOBDescartes", _columnsUPMOBDescartes, _foreignKeysUPMOBDescartes, _indicesUPMOBDescartes);
        final TableInfo _existingUPMOBDescartes = TableInfo.read(_db, "UPMOBDescartes");
        if (! _infoUPMOBDescartes.equals(_existingUPMOBDescartes)) {
          throw new IllegalStateException("Migration didn't properly handle UPMOBDescartes(br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes).\n"
                  + " Expected:\n" + _infoUPMOBDescartes + "\n"
                  + " Found:\n" + _existingUPMOBDescartes);
        }
      }
    }, "452df62e63393c80bdc064583745f4f6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "CadastroEquipamentos","ModeloEquipamentos","Posicoes","ServicosAdicionais","Tarefas","Proprietarios","ModeloMateriais","InventarioPlanejado","ListaTarefas","ListaServicosListaTarefas","ListaMateriaisListaTarefas","Grupos","CadastroMateriaisItens","CadastroMateriais","Usuarios","ParametrosPadrao","UPMOBUsuarios","UPMOBListaServicosListaTarefas","UPMOBListaTarefas","UPMOBHistoricoLocalizacao","UPMOBCadastroMateriaisItens","UPMOBCadastroMateriais","UPMOBDescartes");
  }

  @Override
  public CadastroEquipamentosDAO cadastroEquipamentosDAO() {
    if (_cadastroEquipamentosDAO != null) {
      return _cadastroEquipamentosDAO;
    } else {
      synchronized(this) {
        if(_cadastroEquipamentosDAO == null) {
          _cadastroEquipamentosDAO = new CadastroEquipamentosDAO_Impl(this);
        }
        return _cadastroEquipamentosDAO;
      }
    }
  }

  @Override
  public ModeloEquipamentosDAO modeloEquipamentosDAO() {
    if (_modeloEquipamentosDAO != null) {
      return _modeloEquipamentosDAO;
    } else {
      synchronized(this) {
        if(_modeloEquipamentosDAO == null) {
          _modeloEquipamentosDAO = new ModeloEquipamentosDAO_Impl(this);
        }
        return _modeloEquipamentosDAO;
      }
    }
  }

  @Override
  public PosicoesDAO posicoesDAO() {
    if (_posicoesDAO != null) {
      return _posicoesDAO;
    } else {
      synchronized(this) {
        if(_posicoesDAO == null) {
          _posicoesDAO = new PosicoesDAO_Impl(this);
        }
        return _posicoesDAO;
      }
    }
  }

  @Override
  public ServicosAdicionaisDAO servicosAdicionaisDAO() {
    if (_servicosAdicionaisDAO != null) {
      return _servicosAdicionaisDAO;
    } else {
      synchronized(this) {
        if(_servicosAdicionaisDAO == null) {
          _servicosAdicionaisDAO = new ServicosAdicionaisDAO_Impl(this);
        }
        return _servicosAdicionaisDAO;
      }
    }
  }

  @Override
  public TarefasDAO tarefasDAO() {
    if (_tarefasDAO != null) {
      return _tarefasDAO;
    } else {
      synchronized(this) {
        if(_tarefasDAO == null) {
          _tarefasDAO = new TarefasDAO_Impl(this);
        }
        return _tarefasDAO;
      }
    }
  }

  @Override
  public ProprietariosDAO proprietariosDAO() {
    if (_proprietariosDAO != null) {
      return _proprietariosDAO;
    } else {
      synchronized(this) {
        if(_proprietariosDAO == null) {
          _proprietariosDAO = new ProprietariosDAO_Impl(this);
        }
        return _proprietariosDAO;
      }
    }
  }

  @Override
  public ModeloMateriaisDAO modeloMateriaisDAO() {
    if (_modeloMateriaisDAO != null) {
      return _modeloMateriaisDAO;
    } else {
      synchronized(this) {
        if(_modeloMateriaisDAO == null) {
          _modeloMateriaisDAO = new ModeloMateriaisDAO_Impl(this);
        }
        return _modeloMateriaisDAO;
      }
    }
  }

  @Override
  public InventarioPlanejadoDAO inventarioPlanejadoDAO() {
    if (_inventarioPlanejadoDAO != null) {
      return _inventarioPlanejadoDAO;
    } else {
      synchronized(this) {
        if(_inventarioPlanejadoDAO == null) {
          _inventarioPlanejadoDAO = new InventarioPlanejadoDAO_Impl(this);
        }
        return _inventarioPlanejadoDAO;
      }
    }
  }

  @Override
  public ListaTarefasDAO listaTarefasDAO() {
    if (_listaTarefasDAO != null) {
      return _listaTarefasDAO;
    } else {
      synchronized(this) {
        if(_listaTarefasDAO == null) {
          _listaTarefasDAO = new ListaTarefasDAO_Impl(this);
        }
        return _listaTarefasDAO;
      }
    }
  }

  @Override
  public ListaServicosListaTarefasDAO listaServicosListaTarefasDAO() {
    if (_listaServicosListaTarefasDAO != null) {
      return _listaServicosListaTarefasDAO;
    } else {
      synchronized(this) {
        if(_listaServicosListaTarefasDAO == null) {
          _listaServicosListaTarefasDAO = new ListaServicosListaTarefasDAO_Impl(this);
        }
        return _listaServicosListaTarefasDAO;
      }
    }
  }

  @Override
  public ListaMateriaisListaTarefasDAO listaMateriaisListaTarefasDAO() {
    if (_listaMateriaisListaTarefasDAO != null) {
      return _listaMateriaisListaTarefasDAO;
    } else {
      synchronized(this) {
        if(_listaMateriaisListaTarefasDAO == null) {
          _listaMateriaisListaTarefasDAO = new ListaMateriaisListaTarefasDAO_Impl(this);
        }
        return _listaMateriaisListaTarefasDAO;
      }
    }
  }

  @Override
  public GruposDAO gruposDAO() {
    if (_gruposDAO != null) {
      return _gruposDAO;
    } else {
      synchronized(this) {
        if(_gruposDAO == null) {
          _gruposDAO = new GruposDAO_Impl(this);
        }
        return _gruposDAO;
      }
    }
  }

  @Override
  public CadastroMateriaisItensDAO cadastroMateriaisItensDAO() {
    if (_cadastroMateriaisItensDAO != null) {
      return _cadastroMateriaisItensDAO;
    } else {
      synchronized(this) {
        if(_cadastroMateriaisItensDAO == null) {
          _cadastroMateriaisItensDAO = new CadastroMateriaisItensDAO_Impl(this);
        }
        return _cadastroMateriaisItensDAO;
      }
    }
  }

  @Override
  public CadastroMateriaisDAO cadastroMateriaisDAO() {
    if (_cadastroMateriaisDAO != null) {
      return _cadastroMateriaisDAO;
    } else {
      synchronized(this) {
        if(_cadastroMateriaisDAO == null) {
          _cadastroMateriaisDAO = new CadastroMateriaisDAO_Impl(this);
        }
        return _cadastroMateriaisDAO;
      }
    }
  }

  @Override
  public UsuariosDAO usuariosDAO() {
    if (_usuariosDAO != null) {
      return _usuariosDAO;
    } else {
      synchronized(this) {
        if(_usuariosDAO == null) {
          _usuariosDAO = new UsuariosDAO_Impl(this);
        }
        return _usuariosDAO;
      }
    }
  }

  @Override
  public ParametrosPadraoDAO parametrosPadraoDAO() {
    if (_parametrosPadraoDAO != null) {
      return _parametrosPadraoDAO;
    } else {
      synchronized(this) {
        if(_parametrosPadraoDAO == null) {
          _parametrosPadraoDAO = new ParametrosPadraoDAO_Impl(this);
        }
        return _parametrosPadraoDAO;
      }
    }
  }

  @Override
  public UPMOBUsuariosDAO upmobUsuariosDAO() {
    if (_uPMOBUsuariosDAO != null) {
      return _uPMOBUsuariosDAO;
    } else {
      synchronized(this) {
        if(_uPMOBUsuariosDAO == null) {
          _uPMOBUsuariosDAO = new UPMOBUsuariosDAO_Impl(this);
        }
        return _uPMOBUsuariosDAO;
      }
    }
  }

  @Override
  public UPMOBListaServicosListaTarefasDAO upmobListaServicosListaTarefasDAO() {
    if (_uPMOBListaServicosListaTarefasDAO != null) {
      return _uPMOBListaServicosListaTarefasDAO;
    } else {
      synchronized(this) {
        if(_uPMOBListaServicosListaTarefasDAO == null) {
          _uPMOBListaServicosListaTarefasDAO = new UPMOBListaServicosListaTarefasDAO_Impl(this);
        }
        return _uPMOBListaServicosListaTarefasDAO;
      }
    }
  }

  @Override
  public UPMOBListaTarefasDAO upmobListaTarefasDAO() {
    if (_uPMOBListaTarefasDAO != null) {
      return _uPMOBListaTarefasDAO;
    } else {
      synchronized(this) {
        if(_uPMOBListaTarefasDAO == null) {
          _uPMOBListaTarefasDAO = new UPMOBListaTarefasDAO_Impl(this);
        }
        return _uPMOBListaTarefasDAO;
      }
    }
  }

  @Override
  public UPMOBHistoricoLocalizacaoDAO upmobHistoricoLocalizacaoDAO() {
    if (_uPMOBHistoricoLocalizacaoDAO != null) {
      return _uPMOBHistoricoLocalizacaoDAO;
    } else {
      synchronized(this) {
        if(_uPMOBHistoricoLocalizacaoDAO == null) {
          _uPMOBHistoricoLocalizacaoDAO = new UPMOBHistoricoLocalizacaoDAO_Impl(this);
        }
        return _uPMOBHistoricoLocalizacaoDAO;
      }
    }
  }

  @Override
  public UPMOBCadastroMateriaisItensDAO upmobCadastroMateriaisItensDAO() {
    if (_uPMOBCadastroMateriaisItensDAO != null) {
      return _uPMOBCadastroMateriaisItensDAO;
    } else {
      synchronized(this) {
        if(_uPMOBCadastroMateriaisItensDAO == null) {
          _uPMOBCadastroMateriaisItensDAO = new UPMOBCadastroMateriaisItensDAO_Impl(this);
        }
        return _uPMOBCadastroMateriaisItensDAO;
      }
    }
  }

  @Override
  public UPMOBCadastroMateriaisDAO upmobCadastroMateriaisDAO() {
    if (_uPMOBCadastroMateriaisDAO != null) {
      return _uPMOBCadastroMateriaisDAO;
    } else {
      synchronized(this) {
        if(_uPMOBCadastroMateriaisDAO == null) {
          _uPMOBCadastroMateriaisDAO = new UPMOBCadastroMateriaisDAO_Impl(this);
        }
        return _uPMOBCadastroMateriaisDAO;
      }
    }
  }

  @Override
  public UPMOBDescartesDAO upmobDescartesDAO() {
    if (_uPMOBDescartesDAO != null) {
      return _uPMOBDescartesDAO;
    } else {
      synchronized(this) {
        if(_uPMOBDescartesDAO == null) {
          _uPMOBDescartesDAO = new UPMOBDescartesDAO_Impl(this);
        }
        return _uPMOBDescartesDAO;
      }
    }
  }
}
