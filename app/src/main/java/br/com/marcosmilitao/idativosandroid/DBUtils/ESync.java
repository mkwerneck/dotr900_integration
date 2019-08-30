package br.com.marcosmilitao.idativosandroid.DBUtils;

//Versão melhorada da classe Sync

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Funcoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
//import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisInventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
//import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ESync {

    private static ESync _syncInstance;
    private Context _context;
    private final String _device = Build.SERIAL;
    private final Connection DbConn = DbConnect();
    private Handler _handler;
    private ProgressDialog _pd;
    private String _versaoApp;

    private ConnectionClassManager _mConnectionClassManager;
    private DeviceBandwidthSampler _mDeviceBandwidthSampler;
    private ConnectionQuality _mConnectionClass;
    private int _mTries;
    private String _TAG;

    private ESync(){}

    public static ESync GetSyncInstance(){
        if (_syncInstance == null) {
            synchronized (ESync.class){
                if (_syncInstance == null){
                    _syncInstance = new ESync();
                }
            }
        }

        return _syncInstance;
    }

    public void SyncDatabase(Context context)
    {
        _context = context;
        _handler = new Handler(Looper.getMainLooper());
        _versaoApp = _context.getResources().getString(R.string.numero_versao_apk);

        //MÉTODOS PARA VERIFICAR QUALIDADE DA CONEXÃO (AINDA PRECISA DE VERIFICAÇÃO)

        /*_mConnectionClassManager = ConnectionClassManager.getInstance();
        _mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
        _mConnectionClass = ConnectionQuality.UNKNOWN;
        _mTries = 0;
        _TAG = _context.getClass().getSimpleName();

        checkNetworkQuality();*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();
                SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //OPENING Statement AS TRY-WITH RESOURCE TO AVOID DISPOSING
                try (Statement stmt = DbConn.createStatement();)
                {
                    if (IsTabletAtivo(stmt))
                    {
                        _handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showProgressDialog();
                            }
                        });

                        //SINCRONIZAÇÃO SQLITE > SQL SERVER

                        //region UPMOBCADASTRO MATERIAIS
                        for (UPMOBCadastroMateriais upmobCadastroMateriais : dbInstance.upmobCadastroMateriaisDAO().GetAllRecords())
                        {
                            try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBCadastroMateriais VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");)
                            {
                                pstmt.setInt(1, upmobCadastroMateriais.getIdOriginal());
                                pstmt.setString(2, upmobCadastroMateriais.getNumSerie());
                                pstmt.setString(3, upmobCadastroMateriais.getPatrimonio());
                                pstmt.setInt(4, upmobCadastroMateriais.getQuantidade());
                                pstmt.setTimestamp(5, upmobCadastroMateriais.getDataFabricacao() != null ? new Timestamp(upmobCadastroMateriais.getDataFabricacao().getTime()) : null);
                                pstmt.setTimestamp(6, upmobCadastroMateriais.getDataValidade() != null ? new Timestamp(upmobCadastroMateriais.getDataValidade().getTime()) : null);
                                pstmt.setDouble(7, upmobCadastroMateriais.getValorUnitario());
                                pstmt.setString(8, upmobCadastroMateriais.getDadosTecnicos());
                                pstmt.setString(9, upmobCadastroMateriais.getNotaFiscal());
                                pstmt.setTimestamp(10, upmobCadastroMateriais.getDataEntradaNotaFiscal() != null ? new Timestamp(upmobCadastroMateriais.getDataEntradaNotaFiscal().getTime()) : null);
                                pstmt.setString(11, upmobCadastroMateriais.getTAGID());
                                pstmt.setTimestamp(12, upmobCadastroMateriais.getDataHoraEvento() != null ? new Timestamp(upmobCadastroMateriais.getDataHoraEvento().getTime()) : null);
                                pstmt.setInt(13, upmobCadastroMateriais.getModeloMateriaisItemId());
                                pstmt.setInt(14, upmobCadastroMateriais.getPosicaoOriginalItemId());
                                pstmt.setString(15, upmobCadastroMateriais.getDescricaoErro());
                                pstmt.setBoolean(16, upmobCadastroMateriais.getFlagErro());
                                pstmt.setBoolean(17, upmobCadastroMateriais.getFlagAtualizar());
                                pstmt.setBoolean(18, upmobCadastroMateriais.getFlagProcess());
                                pstmt.setString(19, upmobCadastroMateriais.getCodColetor());

                                pstmt.execute();

                                dbInstance.upmobCadastroMateriaisDAO().Delete(upmobCadastroMateriais);

                            } catch (Exception e)
                            {
                                _handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        _pd.dismiss();
                                        showMessage("ERRO - UPMOBCADASTRO MATERIAIS", e.getMessage(), 2);
                                    }
                                });
                            }
                        }
                        //endregion

                        //region UPMOBCADASTRO MATERIAIS ITENS
                        for (UPMOBCadastroMateriaisItens upmobCadastroMateriaisItens : dbInstance.upmobCadastroMateriaisItensDAO().GetAllRecords())
                        {
                            try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBCadastroItens VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"))
                            {
                                pstmt.setInt(1, upmobCadastroMateriaisItens.getIdOriginal());
                                pstmt.setString(2, upmobCadastroMateriaisItens.getPatrimonio());
                                pstmt.setString(3, upmobCadastroMateriaisItens.getNumSerie());
                                pstmt.setInt(4, upmobCadastroMateriaisItens.getQuantidade());
                                pstmt.setTimestamp(5, upmobCadastroMateriaisItens.getDataHoraEvento() != null ? new Timestamp(upmobCadastroMateriaisItens.getDataHoraEvento().getTime()) : null);
                                pstmt.setTimestamp(6, upmobCadastroMateriaisItens.getDataValidade() != null ? new Timestamp(upmobCadastroMateriaisItens.getDataValidade().getTime()) : null);
                                pstmt.setTimestamp(7, upmobCadastroMateriaisItens.getDataFabricacao() != null ? new Timestamp(upmobCadastroMateriaisItens.getDataFabricacao().getTime()) : null);
                                pstmt.setString(8, upmobCadastroMateriaisItens.getDescricaoErro());
                                pstmt.setBoolean(9, upmobCadastroMateriaisItens.getFlagErro());
                                pstmt.setBoolean(10, upmobCadastroMateriaisItens.getFlagAtualizar());
                                pstmt.setBoolean(11, upmobCadastroMateriaisItens.getFlagProcess());
                                pstmt.setInt(12, upmobCadastroMateriaisItens.getCadastroMateriaisItemIdOriginal());
                                pstmt.setInt(13, upmobCadastroMateriaisItens.getModeloMateriaisItemIdOriginal());
                                pstmt.setString(14, upmobCadastroMateriaisItens.getCodColetor());

                                pstmt.execute();

                                dbInstance.upmobCadastroMateriaisItensDAO().Delete(upmobCadastroMateriaisItens);

                            } catch (Exception e)
                            {
                                _handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        _pd.dismiss();
                                        showMessage("ERRO - UPMOBCADASTRO MATERIAIS ITENS", e.getMessage(), 2);
                                    }
                                });
                            }
                        }
                        //endregion

                        //region UPMOBHISTÓRICO LOCALIZAÇÃO
                        for (UPMOBHistoricoLocalizacao upmobHistoricoLocalizacao : dbInstance.upmobHistoricoLocalizacaoDAO().GetAllRecords())
                        {
                            try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBHistoricoLocalizacao VALUES (?,?,?,?,?,?,?,?,?,?)");)
                            {
                                pstmt.setString(1, upmobHistoricoLocalizacao.getTAGID());
                                pstmt.setString(2, upmobHistoricoLocalizacao.getTAGIDPosicao());
                                pstmt.setTimestamp(3, upmobHistoricoLocalizacao.getDataHoraEvento() != null ? new Timestamp(upmobHistoricoLocalizacao.getDataHoraEvento().getTime()) : null);
                                pstmt.setString(4, upmobHistoricoLocalizacao.getProcesso());
                                pstmt.setString(5, upmobHistoricoLocalizacao.getDominio());
                                pstmt.setInt(6, upmobHistoricoLocalizacao.getQuantidade());
                                pstmt.setString(7, upmobHistoricoLocalizacao.getModalidade());
                                pstmt.setString(8, upmobHistoricoLocalizacao.getDescricaoErro());
                                pstmt.setBoolean(9, upmobHistoricoLocalizacao.getFlagErro());
                                pstmt.setBoolean(10, upmobHistoricoLocalizacao.getFlagProcess());

                                pstmt.execute();

                                dbInstance.upmobHistoricoLocalizacaoDAO().Delete(upmobHistoricoLocalizacao);

                            } catch (Exception e)
                            {
                                _handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        _pd.dismiss();
                                        showMessage("ERRO - UPMOBHISTÓRICO LOCALIZAÇÃO", e.getMessage(), 2);
                                    }
                                });
                            }
                        }
                        //endregion

                        //region UPMOBUSUARIOS
                        for (UPMOBUsuarios upmobUsuarios : dbInstance.upmobUsuariosDAO().GetAllRecords())
                        {
                            try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBUsuario VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");)
                            {
                                pstmt.setString(1, upmobUsuarios.getIdOriginal());
                                pstmt.setString(2, upmobUsuarios.getRoleIdOriginal());
                                pstmt.setString(3, upmobUsuarios.getUsername());
                                pstmt.setString(4, upmobUsuarios.getEmail());
                                pstmt.setString(5, upmobUsuarios.getNomeCompleto());
                                pstmt.setString(6, upmobUsuarios.getTAGID());
                                pstmt.setString(7, upmobUsuarios.getCodColetor());
                                pstmt.setString(8, upmobUsuarios.getDescricaoErro());
                                pstmt.setBoolean(9, upmobUsuarios.getFlagErro());
                                pstmt.setBoolean(10, upmobUsuarios.getFlagAtualizar());
                                pstmt.setBoolean(11, upmobUsuarios.getFlagProcess());
                                pstmt.setBoolean(12, upmobUsuarios.getEnviarSenhaEmail());

                                pstmt.execute();

                                dbInstance.upmobUsuariosDAO().Delete(upmobUsuarios);

                            } catch (Exception e)
                            {
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - UPMOBUSUARIO", e.getMessage(), 2);
                                        }
                                    });
                                }
                            }
                        }
                        //endregion

                        //region UPMOBDESCARTES
                        for (UPMOBDescartes upmobDescartes : dbInstance.upmobDescartesDAO().GetAllRecords())
                        {
                            try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBDescartes VALUES (?,?,?,?,?,?,?,?)");)
                            {
                                pstmt.setInt(1, upmobDescartes.getCadastromateriaisId());
                                pstmt.setString(2, upmobDescartes.getApplicationUserId());
                                pstmt.setString(3, upmobDescartes.getMotivo());
                                pstmt.setTimestamp(4, upmobDescartes.getDataHoraEvento() != null ? new Timestamp(upmobDescartes.getDataHoraEvento().getTime()) : null);
                                pstmt.setString(5, upmobDescartes.getCodColetor());
                                pstmt.setString(6, upmobDescartes.getDescricaoErro());
                                pstmt.setBoolean(7, upmobDescartes.isFlagErro());
                                pstmt.setBoolean(8, upmobDescartes.isFlagErro());

                                pstmt.execute();

                                dbInstance.upmobDescartesDAO().Delete(upmobDescartes);

                            } catch (Exception e)
                            {
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - UPMOBDESCARTES", e.getMessage(), 2);
                                        }
                                    });
                                }
                            }
                        }
                        //endregion

                        //region UPMOBLISTA TAREFAS
                        for (UPMOBListaTarefas upmobListaTarefas : dbInstance.upmobListaTarefasDAO().GetAllRecords())
                        {

                            String dataHoraEventoStr = mFormatter.format(upmobListaTarefas.getDataHoraEvento());

                            //SALVANDO LISTA DE SERVIÇOS
                            for (UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas : dbInstance.upmobListaServicosListaTarefasDAO().GetAllRecords(dataHoraEventoStr))
                            {
                                try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaServicosAdicionais VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");){

                                    pstmt.setInt(1, upmobListaServicosListaTarefas.getIdOriginal());
                                    pstmt.setString(2, upmobListaServicosListaTarefas.getCodTarefa());
                                    pstmt.setString(3, upmobListaServicosListaTarefas.getServico());
                                    pstmt.setString(4, upmobListaServicosListaTarefas.getResultado());
                                    pstmt.setString(5, upmobListaServicosListaTarefas.getStatus());
                                    pstmt.setTimestamp(6, upmobListaServicosListaTarefas.getDataHoraEvento() != null ? new Timestamp(upmobListaServicosListaTarefas.getDataHoraEvento().getTime()): null);
                                    pstmt.setTimestamp(7, upmobListaServicosListaTarefas.getDataInicio() != null ? new Timestamp(upmobListaServicosListaTarefas.getDataInicio().getTime()) : null);
                                    pstmt.setTimestamp(8, upmobListaServicosListaTarefas.getDataConclusao() != null ? new Timestamp(upmobListaServicosListaTarefas.getDataConclusao().getTime()) : null);
                                    pstmt.setTimestamp(9, upmobListaServicosListaTarefas.getDataCancelamento() != null ? new Timestamp(upmobListaServicosListaTarefas.getDataCancelamento().getTime()) : null);
                                    pstmt.setString(10, upmobListaServicosListaTarefas.getCodColetor());
                                    pstmt.setString(11, upmobListaServicosListaTarefas.getDescricaoErro());
                                    pstmt.setBoolean(12, upmobListaServicosListaTarefas.getFlagErro());
                                    pstmt.setBoolean(13, upmobListaServicosListaTarefas.getFlagAtualizar());
                                    pstmt.setBoolean(14, upmobListaServicosListaTarefas.getFlagProcess());

                                    pstmt.execute();

                                    dbInstance.upmobListaServicosListaTarefasDAO().Delete(upmobListaServicosListaTarefas);
                                } catch (Exception e)
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - LISTA SERVIÇOS", e.getMessage(), 2);
                                        }
                                    });
                                }
                            }

                            //SALVANDO LISTA DE MATERIAIS
                            /*for (UPMOBListaMateriaisListaTarefas upmobListaMateriaisListaTarefas : dbInstance.upmobListaMateriaisListaTarefasDAO().GetAllRecords(dataHoraEventoStr))
                            {
                                try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaMateriais VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");)
                                {
                                    pstmt.setInt(1, upmobListaMateriaisListaTarefas.getIdOriginal());
                                    pstmt.setString(2, upmobListaMateriaisListaTarefas.getStatus());
                                    pstmt.setString(3, upmobListaMateriaisListaTarefas.getTAGID());
                                    pstmt.setInt(4, upmobListaMateriaisListaTarefas.getQuantidade());
                                    pstmt.setString(5, upmobListaMateriaisListaTarefas.getObservacao());
                                    pstmt.setString(6, upmobListaMateriaisListaTarefas.getCodColetor());
                                    pstmt.setString(7, upmobListaMateriaisListaTarefas.getCodTarefa());
                                    pstmt.setTimestamp(8, upmobListaMateriaisListaTarefas.getDataHoraEvento() != null ? new Timestamp(upmobListaMateriaisListaTarefas.getDataHoraEvento().getTime()) : null);
                                    pstmt.setString(9, upmobListaMateriaisListaTarefas.getDescricaoErro());
                                    pstmt.setBoolean(10, upmobListaMateriaisListaTarefas.getFlagErro());
                                    pstmt.setBoolean(11, upmobListaMateriaisListaTarefas.getFlagAtualizar());
                                    pstmt.setBoolean(12, upmobListaMateriaisListaTarefas.getFlagProcess());

                                    pstmt.execute();

                                    dbInstance.upmobListaMateriaisListaTarefasDAO().Delete(upmobListaMateriaisListaTarefas);

                                } catch (Exception e)
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - LISTA MATERIAIS", e.getMessage(), 2);
                                        }
                                    });
                                }
                            }*/

                            //SALVANDO A TAREFA
                            try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaTarefas VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"))
                            {
                                pstmt.setInt(1, upmobListaTarefas.getIdOriginal());
                                pstmt.setString(2, upmobListaTarefas.getCodTarefa());
                                pstmt.setString(3, upmobListaTarefas.getStatus());
                                pstmt.setString(4, upmobListaTarefas.getTraceNumber());
                                pstmt.setTimestamp(5, upmobListaTarefas.getDataInicio() != null ? new Timestamp(upmobListaTarefas.getDataInicio().getTime()) : null);
                                pstmt.setTimestamp(6, upmobListaTarefas.getDataFimReal() != null ? new Timestamp(upmobListaTarefas.getDataFimReal().getTime()) : null);
                                pstmt.setTimestamp(7, upmobListaTarefas.getDataHoraEvento() != null ? new Timestamp(upmobListaTarefas.getDataHoraEvento().getTime()) : null);
                                pstmt.setString(8, upmobListaTarefas.getCodColetor());
                                pstmt.setString(9, upmobListaTarefas.getDescricaoErro());
                                pstmt.setBoolean(10, upmobListaTarefas.getFlagErro());
                                pstmt.setBoolean(11, upmobListaTarefas.getFlagAtualizar());
                                pstmt.setBoolean(12, upmobListaTarefas.getFlagProcess());

                                pstmt.execute();

                                dbInstance.upmobListaTarefasDAO().Delete(upmobListaTarefas);

                            } catch (Exception e)
                            {
                                _handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        _pd.dismiss();
                                        showMessage("ERRO - LISTA TAREFAS", e.getMessage(), 2);
                                    }
                                });
                            }
                        }
                        //endregion

                        //SINCRONIZAÇÃO SQL SERVER > SQLITE

                        //region PARAMETROS PADRÕES
                        String rv_Parametros = dbInstance.parametrosPadraoDAO().GetLastRowVersion() != null ? dbInstance.parametrosPadraoDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT al.Codigo as alcodigo, cd.Id as cdid, cd.RowVersion as cdrowversion, ba.Codigo as bacodigo " +
                                                                    "FROM Coletores as cd INNER JOIN Almoxarifados as al ON cd.AlmoxarifadoItemId = al.Id INNER JOIN Bases as ba ON al.BaseId = ba.Id " +
                                                                        "WHERE cd.Codigo = '"+ _device + "' AND cd.RowVersion > " + rv_Parametros);)
                        {
                            while (rs.next())
                            {
                                ParametrosPadrao parametrosPadrao = dbInstance.parametrosPadraoDAO().GetByIdOriginal(rs.getInt("cdid"));

                                if (parametrosPadrao == null)
                                {
                                    parametrosPadrao = new ParametrosPadrao();
                                    parametrosPadrao.setCodAlmoxarifado(rs.getString("alcodigo"));
                                    parametrosPadrao.setIdOriginal(rs.getInt("cdid"));
                                    parametrosPadrao.setRowVersion("0x" + rs.getString("cdrowversion"));
                                    parametrosPadrao.setSetorProprietario(rs.getString("bacodigo"));

                                    dbInstance.parametrosPadraoDAO().Create(parametrosPadrao);

                                } else
                                {
                                    parametrosPadrao.setCodAlmoxarifado(rs.getString("alcodigo"));
                                    parametrosPadrao.setIdOriginal(rs.getInt("cdid"));
                                    parametrosPadrao.setRowVersion("0x" + rs.getString("cdrowversion"));
                                    parametrosPadrao.setSetorProprietario(rs.getString("bacodigo"));

                                    dbInstance.parametrosPadraoDAO().Update(parametrosPadrao);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - PARAMETROS PADRAO", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region POSICOES
                        String rv_Posicao = dbInstance.posicoesDAO().GetLastRowVersion() != null ? dbInstance.posicoesDAO().GetLastRowVersion() : "0";
                        try (ResultSet rs = stmt.executeQuery("SELECT p.Codigo as pcodigo, a.Codigo as acodigo, p.Descricao as pdescricao, p.Id as pid, p.RowVersion as prowversion, t.TAGID as ttagid " +
                                                                    "FROM Posicoes as p INNER JOIN Almoxarifados as a ON p.AlmoxarifadoItemId = a.Id INNER JOIN TAGIDPosicao as t ON p.TAGIDPosicaoItemId = t.Id " +
                                                                        "WHERE p.RowVersion > " + rv_Posicao);){
                            while(rs.next()){
                                Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(rs.getInt("pid"));

                                if (posicao == null)
                                {
                                    posicao = new Posicoes();
                                    posicao.setCodigo(rs.getString("pcodigo"));
                                    posicao.setAlmoxarifado(rs.getString("acodigo"));
                                    posicao.setDescricao(rs.getString("pdescricao"));
                                    posicao.setIdOriginal(rs.getInt("pid"));
                                    posicao.setRowVersion("0x" + rs.getString("prowversion"));
                                    posicao.setTAGID(rs.getString("ttagid"));

                                    dbInstance.posicoesDAO().Create(posicao);
                                }
                                else
                                {
                                    posicao.setCodigo(rs.getString("pcodigo"));
                                    posicao.setAlmoxarifado(rs.getString("acodigo"));
                                    posicao.setDescricao(rs.getString("pdescricao"));
                                    posicao.setIdOriginal(rs.getInt("pid"));
                                    posicao.setRowVersion("0x" + rs.getString("prowversion"));
                                    posicao.setTAGID(rs.getString("ttagid"));

                                    dbInstance.posicoesDAO().Update(posicao);
                                }
                            }
                        }catch(Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - POSIÇÕES", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region CADASTRO MATERIAIS
                        String rv_CadastroMateriais = dbInstance.cadastroMateriaisDAO().GetLastRowVersion() != null ? dbInstance.cadastroMateriaisDAO().GetLastRowVersion() : "0";
                        try (ResultSet rs = stmt.executeQuery("SELECT ct.Categoria as ctcategoria, c.DadosTecnicos as cdadostecnicos, c.DataCadastro as cdatacadastro, c.DataEntradaNotaFiscal as cdatanotafiscal, c.DataFabricacao as cdatafabricacao, c.DataValidade as cdatavalidade, c.Id as cid, c.ModeloMateriaisItemId as cmodeloid, c.NotaFiscal as cnotafiscal, c.NumSerie as cnumserie, c.Patrimonio as cpatrimonio, c.PosicaoOriginalItemId as cposicaoid, c.Quantidade as cquantidade, c.RowVersion as crowversion, t.TAGID as ttagid, c.ValorUnitario as cvalorunitario, c.EmUso as cemuso " +
                                                                    "FROM CadastroMateriais as c INNER JOIN CategoriasMateriais as ct ON c.CategoriaMateriaisItemId = ct.Id INNER JOIN TAGIDMaterial as t ON c.TAGIDMaterialItemId = t.Id " +
                                                                        "WHERE c.RowVersion > " + rv_CadastroMateriais);)
                        {
                            while(rs.next()){
                                CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(rs.getInt("cid"));

                                if (cadastroMateriais == null)
                                {
                                    cadastroMateriais = new CadastroMateriais();
                                    cadastroMateriais.setCategoria(rs.getString("ctcategoria"));
                                    cadastroMateriais.setDadosTecnicos(rs.getString("cdadostecnicos"));
                                    cadastroMateriais.setDataCadastro(rs.getTimestamp("cdatacadastro") != null ? new java.util.Date(rs.getTimestamp("cdatacadastro").getTime()) : null);
                                    cadastroMateriais.setDataEntradaNotaFiscal(rs.getTimestamp("cdatanotafiscal") != null ? new java.util.Date(rs.getTimestamp("cdatanotafiscal").getTime()) : null);
                                    cadastroMateriais.setDataFabricacao(rs.getTimestamp("cdatafabricacao") != null ? new java.util.Date(rs.getTimestamp("cdatafabricacao").getTime()) : null);
                                    cadastroMateriais.setDataValidade(rs.getTimestamp("cdatavalidade") != null ? new java.util.Date(rs.getTimestamp("cdatavalidade").getTime()) : null);
                                    cadastroMateriais.setIdOriginal(rs.getInt("cid"));
                                    cadastroMateriais.setModeloMateriaisItemIdOriginal(rs.getInt("cmodeloid"));
                                    cadastroMateriais.setNotaFiscal(rs.getString("cnotafiscal"));
                                    cadastroMateriais.setNumSerie(rs.getString("cnotafiscal"));
                                    cadastroMateriais.setPatrimonio(rs.getString("cpatrimonio"));
                                    cadastroMateriais.setPosicaoOriginalItemIdoriginal(rs.getInt("cposicaoid"));
                                    cadastroMateriais.setQuantidade(rs.getInt("cquantidade"));
                                    cadastroMateriais.setRowVersion("0x" + rs.getString("crowversion"));
                                    cadastroMateriais.setTAGID(rs.getString("ttagid"));
                                    cadastroMateriais.setValorUnitario(rs.getFloat("cvalorunitario"));
                                    cadastroMateriais.setEmUso(rs.getBoolean("cemuso"));

                                    dbInstance.cadastroMateriaisDAO().Create(cadastroMateriais);
                                }
                                else
                                {
                                    cadastroMateriais.setCategoria(rs.getString("ctcategoria"));
                                    cadastroMateriais.setDadosTecnicos(rs.getString("cdadostecnicos"));
                                    cadastroMateriais.setDataCadastro(rs.getTimestamp("cdatacadastro") != null ? new java.util.Date(rs.getTimestamp("cdatacadastro").getTime()) : null);
                                    cadastroMateriais.setDataEntradaNotaFiscal(rs.getTimestamp("cdatanotafiscal") != null ? new java.util.Date(rs.getTimestamp("cdatanotafiscal").getTime()) : null);
                                    cadastroMateriais.setDataFabricacao(rs.getTimestamp("cdatafabricacao") != null ? new java.util.Date(rs.getTimestamp("cdatafabricacao").getTime()) : null);
                                    cadastroMateriais.setDataValidade(rs.getTimestamp("cdatavalidade") != null ? new java.util.Date(rs.getTimestamp("cdatavalidade").getTime()) : null);
                                    cadastroMateriais.setIdOriginal(rs.getInt("cid"));
                                    cadastroMateriais.setModeloMateriaisItemIdOriginal(rs.getInt("cmodeloid"));
                                    cadastroMateriais.setNotaFiscal(rs.getString("cnotafiscal"));
                                    cadastroMateriais.setNumSerie(rs.getString("cnotafiscal"));
                                    cadastroMateriais.setPatrimonio(rs.getString("cpatrimonio"));
                                    cadastroMateriais.setPosicaoOriginalItemIdoriginal(rs.getInt("cposicaoid"));
                                    cadastroMateriais.setQuantidade(rs.getInt("cquantidade"));
                                    cadastroMateriais.setRowVersion("0x" + rs.getString("crowversion"));
                                    cadastroMateriais.setTAGID(rs.getString("ttagid"));
                                    cadastroMateriais.setValorUnitario(rs.getFloat("cvalorunitario"));

                                    dbInstance.cadastroMateriaisDAO().Update(cadastroMateriais);
                                }
                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - CADASTRO MATERIAIS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region USUARIOS
                        String rv_Usuarios = dbInstance.usuariosDAO().GetLastRowVersion() != null ? dbInstance.usuariosDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT u.Email as email, u.Id as uid, u.NomeCompleto as nomecompleto, u.RowVersion as rowversion, t.TAGID as tagid, u.UserName as username " +
                                                                    "from AspNetUsers as u LEFT OUTER JOIN TAGIDUsuario as t ON u.TAGIDUsuarioItemId = t.Id " +
                                                                        "WHERE u.RowVersion > " + rv_Usuarios);)
                        {
                            while(rs.next()){
                                Usuarios usuarios = dbInstance.usuariosDAO().GetByIdOriginal(rs.getString("uid"));

                                if (usuarios == null)
                                {
                                    usuarios = new Usuarios();
                                    usuarios.setEmail(rs.getString("email"));
                                    usuarios.setIdOriginal(rs.getString("uid"));
                                    usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                                    usuarios.setRowVersion("0x" + rs.getString("rowversion"));
                                    usuarios.setTAGID(rs.getString("tagid"));
                                    usuarios.setUserName(rs.getString("username"));

                                    dbInstance.usuariosDAO().Create(usuarios);
                                }
                                else
                                {
                                    usuarios.setEmail(rs.getString("email"));
                                    usuarios.setIdOriginal(rs.getString("uid"));
                                    usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                                    usuarios.setRowVersion("0x" + rs.getString("rowversion"));
                                    usuarios.setTAGID(rs.getString("tagid"));
                                    usuarios.setUserName(rs.getString("username"));

                                    dbInstance.usuariosDAO().Update(usuarios);
                                }
                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - USUARIOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region MODELO EQUIPAMENTOS
                        String rv_ModeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetLastRowVersion() != null ? dbInstance.modeloEquipamentosDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT cat.Categoria as categoria, me.DescricaoTecnica as mdescricao, me.Id as mid, me.Modelo as mmodelo, me.PartNumber as mpartnumber, me.RowVersion as mrowversion " +
                                                                    "FROM ModeloEquipamentos as me INNER JOIN CategoriaEquipamentos as cat ON me.CategoriaEquipamentosId = cat.Id " +
                                                                        "WHERE me.RowVersion > " + rv_ModeloEquipamentos);)
                        {
                            while(rs.next()){
                                ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(rs.getInt("mid"));

                                if (modeloEquipamentos == null)
                                {
                                    modeloEquipamentos = new ModeloEquipamentos();
                                    modeloEquipamentos.setCategoria(rs.getString("categoria"));
                                    modeloEquipamentos.setDescricaoTecnica(rs.getString("mdescricao"));
                                    modeloEquipamentos.setIdOriginal(rs.getInt("mid"));
                                    modeloEquipamentos.setModelo(rs.getString("mmodelo"));
                                    modeloEquipamentos.setPartNumber(rs.getString("mpartnumber"));
                                    modeloEquipamentos.setRowVersion("0x" + rs.getString("mrowversion"));

                                    dbInstance.modeloEquipamentosDAO().Create(modeloEquipamentos);
                                }
                                else
                                {
                                    modeloEquipamentos.setCategoria(rs.getString("categoria"));
                                    modeloEquipamentos.setDescricaoTecnica(rs.getString("mdescricao"));
                                    modeloEquipamentos.setIdOriginal(rs.getInt("mid"));
                                    modeloEquipamentos.setModelo(rs.getString("mmodelo"));
                                    modeloEquipamentos.setPartNumber(rs.getString("mpartnumber"));
                                    modeloEquipamentos.setRowVersion("0x" + rs.getString("mrowversion"));

                                    dbInstance.modeloEquipamentosDAO().Update(modeloEquipamentos);
                                }
                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - MODELO EQUIPAMENTOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region GRUPOS
                        String rv_Grupos = dbInstance.gruposDAO().GetLastRowVersion() != null ? dbInstance.gruposDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT r.Id as rid, r.Name as rname, r.RowVersion as rrowversion " +
                                                                    "FROM AspNetRoles as r " +
                                                                        "WHERE r.RowVersion > " + rv_Grupos);)
                        {
                            while(rs.next()){
                                Grupos grupos = dbInstance.gruposDAO().GetByIdOriginal(rs.getString("rid"));

                                if (grupos == null) {
                                    grupos = new Grupos();
                                    grupos.setIdOriginal(rs.getString("rid"));
                                    grupos.setRowVersion("0x" + rs.getString("rrowversion"));
                                    grupos.setTitulo(rs.getString("rname"));

                                    dbInstance.gruposDAO().Create(grupos);
                                }
                                else
                                {
                                    grupos.setIdOriginal(rs.getString("rid"));
                                    grupos.setRowVersion("0x" + rs.getString("rrowversion"));
                                    grupos.setTitulo(rs.getString("rname"));

                                    dbInstance.gruposDAO().Update(grupos);
                                }
                            }

                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - MODELO GRUPOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region TAREFAS
                        String rv_Tarefas = dbInstance.tarefasDAO().GetLastRowVersion() != null ? dbInstance.tarefasDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT ce.Categoria as ccategoria, t.Codigo as tcodigo, t.Descricao as tdescricao, t.FlagDependenciaMaterial as tdependenciam, t.FlagDependenciaServico as tdependencias, t.Id as tid, t.RowVersion as trowversion, t.Titulo as ttitulo " +
                                                                    "FROM Tarefas as t INNER JOIN CategoriaEquipamentos as ce on t.CategoriaEquipamentosItemId = ce.Id " +
                                                                        "WHERE t.RowVersion > " + rv_Tarefas);)
                        {
                            while (rs.next())
                            {
                                Tarefas tarefas = dbInstance.tarefasDAO().GetByIdOriginal(rs.getInt("tid"));

                                if (tarefas == null)
                                {
                                    tarefas = new Tarefas();
                                    tarefas.setCategoriaEquipamentos(rs.getString("ccategoria"));
                                    tarefas.setCodigo(rs.getString("tcodigo"));
                                    tarefas.setDescricao(rs.getString("tdescricao"));
                                    tarefas.setFlagDependenciaMaterial(rs.getBoolean("tdependenciam"));
                                    tarefas.setFlagDependenciaServico(rs.getBoolean("tdependencias"));
                                    tarefas.setIdOriginal(rs.getInt("tid"));
                                    tarefas.setRowVersion("0x" + rs.getString("trowversion"));
                                    tarefas.setTitulo(rs.getString("ttitulo"));

                                    dbInstance.tarefasDAO().Create(tarefas);
                                } else
                                {
                                    tarefas.setCategoriaEquipamentos(rs.getString("ccategoria"));
                                    tarefas.setCodigo(rs.getString("tcodigo"));
                                    tarefas.setDescricao(rs.getString("tdescricao"));
                                    tarefas.setFlagDependenciaMaterial(rs.getBoolean("tdependenciam"));
                                    tarefas.setFlagDependenciaServico(rs.getBoolean("tdependencias"));
                                    tarefas.setIdOriginal(rs.getInt("tid"));
                                    tarefas.setRowVersion("0x" + rs.getString("trowversion"));
                                    tarefas.setTitulo(rs.getString("ttitulo"));

                                    dbInstance.tarefasDAO().Update(tarefas);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - TAREFAS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region MODELO MATERIAIS
                        String rv_ModeloMateriais = dbInstance.modeloMateriaisDAO().GetLastRowVersion() != null ? dbInstance.modeloMateriaisDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT mm.DescricaoTecnica, fm.NomeFamilia, mm.IDOmni, mm.Id, mm.Modelo, mm.PartNumber, mm.RowVersion " +
                                                                    "FROM ModelosMateriais as mm LEFT OUTER JOIN Familia as fm ON mm.FamiliaId = fm.Id " +
                                                                        "WHERE mm.RowVersion > " + rv_ModeloMateriais);)
                        {
                            while (rs.next())
                            {
                                ModeloMateriais modeloMateriais = dbInstance.modeloMateriaisDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (modeloMateriais == null)
                                {
                                    modeloMateriais = new ModeloMateriais();
                                    modeloMateriais.setDescricaoTecnica(rs.getString("DescricaoTecnica"));
                                    modeloMateriais.setFamilia(rs.getString("NomeFamilia"));
                                    modeloMateriais.setIDOmni(rs.getString("IDOmni"));
                                    modeloMateriais.setIdOriginal(rs.getInt("Id"));
                                    modeloMateriais.setModelo(rs.getString("Modelo"));
                                    modeloMateriais.setPartNumber(rs.getString("PartNumber"));
                                    modeloMateriais.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.modeloMateriaisDAO().Create(modeloMateriais);

                                } else
                                {
                                    modeloMateriais.setDescricaoTecnica(rs.getString("DescricaoTecnica"));
                                    modeloMateriais.setFamilia(rs.getString("NomeFamilia"));
                                    modeloMateriais.setIDOmni(rs.getString("IDOmni"));
                                    modeloMateriais.setIdOriginal(rs.getInt("Id"));
                                    modeloMateriais.setModelo(rs.getString("Modelo"));
                                    modeloMateriais.setPartNumber(rs.getString("PartNumber"));
                                    modeloMateriais.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.modeloMateriaisDAO().Update(modeloMateriais);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - MODELO MATERIAIS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region SERVICOS ADICIONAIS
                        String rv_ServicosAdicionais = dbInstance.servicosAdicionaisDAO().GetLastRowVersion() != null ? dbInstance.servicosAdicionaisDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT Descricao, FlagAtivo, FlagObrigatorio, Id, Modalidade, RowVersion, Servico, TarefaItemId " +
                                                                    "FROM ServicosAdicionais " +
                                                                        "WHERE RowVersion > " + rv_ServicosAdicionais);)
                        {
                            while (rs.next())
                            {
                                ServicosAdicionais servicosAdicionais = dbInstance.servicosAdicionaisDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (servicosAdicionais == null)
                                {
                                    servicosAdicionais = new ServicosAdicionais();
                                    servicosAdicionais.setDescricao(rs.getString("Descricao"));
                                    servicosAdicionais.setFlagAtivo(rs.getBoolean("FlagAtivo"));
                                    servicosAdicionais.setFlagObrigatorio(rs.getBoolean("FlagObrigatorio"));
                                    servicosAdicionais.setIdOriginal(rs.getInt("Id"));
                                    servicosAdicionais.setModalidade(rs.getString("Modalidade"));
                                    servicosAdicionais.setRowVersion("0x" + rs.getString("RowVersion"));
                                    servicosAdicionais.setServico(rs.getString("Servico"));
                                    servicosAdicionais.setTarefaItemIdOriginal(rs.getInt("TarefaItemId"));

                                    dbInstance.servicosAdicionaisDAO().Create(servicosAdicionais);
                                } else
                                {
                                    servicosAdicionais.setDescricao(rs.getString("Descricao"));
                                    servicosAdicionais.setFlagAtivo(rs.getBoolean("FlagAtivo"));
                                    servicosAdicionais.setFlagObrigatorio(rs.getBoolean("FlagObrigatorio"));
                                    servicosAdicionais.setIdOriginal(rs.getInt("Id"));
                                    servicosAdicionais.setModalidade(rs.getString("Modalidade"));
                                    servicosAdicionais.setRowVersion("0x" + rs.getString("RowVersion"));
                                    servicosAdicionais.setServico(rs.getString("Servico"));
                                    servicosAdicionais.setTarefaItemIdOriginal(rs.getInt("TarefaItemId"));

                                    dbInstance.servicosAdicionaisDAO().Update(servicosAdicionais);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - SERVICOS ADICIONAIS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region CADASTRO EQUIPAMENTOS
                        String rv_CadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetLastRowVersion() != null ? dbInstance.cadastroEquipamentosDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT ce.TraceNumber, ce.Id, ce.DataCadastro, ce.DataFabricacao, ba.Descricao, ce.ModeloEquipamentosId, ce.RowVersion, ce.Status, te.TAGID " +
                                                                    "FROM CadastroEquipamentos as ce INNER JOIN Bases as ba ON ce.BaseId = ba.Id LEFT OUTER JOIN TAGIDEquipment AS te ON ce.TAGIDEquipmentId = te.Id " +
                                                                        "WHERE ce.RowVersion > " + rv_CadastroEquipamentos);)
                        {
                            while (rs.next())
                            {
                                CadastroEquipamentos cadastroEquipamentos = dbInstance.cadastroEquipamentosDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (cadastroEquipamentos == null)
                                {
                                    cadastroEquipamentos = new CadastroEquipamentos();
                                    cadastroEquipamentos.setTraceNumber(rs.getString("TraceNumber"));
                                    cadastroEquipamentos.setIdOriginal(rs.getInt("Id"));
                                    cadastroEquipamentos.setDataCadastro(rs.getTimestamp("DataCadastro") != null ? new java.util.Date(rs.getTimestamp("DataCadastro").getTime()) : null);
                                    cadastroEquipamentos.setDataFabricacao(rs.getDate("DataFabricacao"));
                                    cadastroEquipamentos.setLocalizacao(rs.getString("Descricao"));
                                    cadastroEquipamentos.setModeloEquipamentoItemIdOriginal(rs.getInt("ModeloEquipamentosId"));
                                    cadastroEquipamentos.setRowVersion("0x" + rs.getString("RowVersion"));
                                    cadastroEquipamentos.setStatus(rs.getString("Status"));
                                    cadastroEquipamentos.setTAGID(rs.getString("TAGID"));

                                    dbInstance.cadastroEquipamentosDAO().Create(cadastroEquipamentos);

                                } else
                                {
                                    cadastroEquipamentos.setTraceNumber(rs.getString("TraceNumber"));
                                    cadastroEquipamentos.setIdOriginal(rs.getInt("Id"));
                                    cadastroEquipamentos.setDataCadastro(rs.getTimestamp("DataCadastro") != null ? new java.util.Date(rs.getTimestamp("DataCadastro").getTime()) : null);
                                    cadastroEquipamentos.setDataFabricacao(rs.getDate("DataFabricacao"));
                                    cadastroEquipamentos.setLocalizacao(rs.getString("Descricao"));
                                    cadastroEquipamentos.setModeloEquipamentoItemIdOriginal(rs.getInt("ModeloEquipamentosId"));
                                    cadastroEquipamentos.setRowVersion("0x" + rs.getString("RowVersion"));
                                    cadastroEquipamentos.setStatus(rs.getString("Status"));
                                    cadastroEquipamentos.setTAGID(rs.getString("TAGID"));

                                    dbInstance.cadastroEquipamentosDAO().Update(cadastroEquipamentos);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - CADASTRO EQUIPAMENTOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region CADASTRO MATERIAIS ITENS
                        String rv_CadastroMateriaisItens = dbInstance.cadastroMateriaisItensDAO().GetLastRowVersion() != null ? dbInstance.cadastroMateriaisItensDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT CadastroMateriaisItemId, DataCadastro, DataFabricacao, DataValidade, Id, ModeloMateriaisItemId, NumSerie, Patrimonio, Quantidade, RowVersion " +
                                                                    "FROM CadastroMateriaisItens " +
                                                                        "WHERE RowVersion >" + rv_CadastroMateriaisItens);)
                        {
                            while (rs.next())
                            {
                                CadastroMateriaisItens cadastroMateriaisItens = dbInstance.cadastroMateriaisItensDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (cadastroMateriaisItens == null)
                                {
                                    cadastroMateriaisItens = new CadastroMateriaisItens();
                                    cadastroMateriaisItens.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    cadastroMateriaisItens.setDataCadastro(rs.getDate("DataCadastro"));
                                    cadastroMateriaisItens.setDataFabricacao(rs.getDate("DataFabricacao"));
                                    cadastroMateriaisItens.setDataValidade(rs.getDate("DataValidade"));
                                    cadastroMateriaisItens.setIdOriginal(rs.getInt("Id"));
                                    cadastroMateriaisItens.setModeloMateriaisItemIdOriginal(rs.getInt("ModeloMateriaisItemId"));
                                    cadastroMateriaisItens.setNumSerie(rs.getString("NumSerie"));
                                    cadastroMateriaisItens.setPatrimonio(rs.getString("Patrimonio"));
                                    cadastroMateriaisItens.setQuantidade(rs.getInt("Quantidade"));
                                    cadastroMateriaisItens.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.cadastroMateriaisItensDAO().Create(cadastroMateriaisItens);
                                } else
                                {
                                    cadastroMateriaisItens.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    cadastroMateriaisItens.setDataCadastro(rs.getDate("DataCadastro"));
                                    cadastroMateriaisItens.setDataFabricacao(rs.getDate("DataFabricacao"));
                                    cadastroMateriaisItens.setDataValidade(rs.getDate("DataValidade"));
                                    cadastroMateriaisItens.setIdOriginal(rs.getInt("Id"));
                                    cadastroMateriaisItens.setModeloMateriaisItemIdOriginal(rs.getInt("ModeloMateriaisItemId"));
                                    cadastroMateriaisItens.setNumSerie(rs.getString("NumSerie"));
                                    cadastroMateriaisItens.setPatrimonio(rs.getString("Patrimonio"));
                                    cadastroMateriaisItens.setQuantidade(rs.getInt("Quantidade"));
                                    cadastroMateriaisItens.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.cadastroMateriaisItensDAO().Update(cadastroMateriaisItens);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - CADASTRO MATERISI ITENS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region PROPRIETARIOS
                        String rv_Proprietarios = dbInstance.proprietariosDAO().GetLastRowVersion() != null ? dbInstance.proprietariosDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT sp.Descricao, em.NomeFantasia, sp.Id, sp.RowVersion " +
                                                                    "FROM Bases as sp LEFT OUTER JOIN Empresa as em ON sp.EmpresaId = em.Id " +
                                                                        "WHERE sp.RowVersion > " + rv_Proprietarios);)
                        {
                            while (rs.next())
                            {
                                Proprietarios proprietarios = dbInstance.proprietariosDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (proprietarios == null)
                                {
                                    proprietarios = new Proprietarios();
                                    proprietarios.setDescricao(rs.getString("Descricao"));
                                    proprietarios.setEmpresa(rs.getString("NomeFantasia"));
                                    proprietarios.setIdOriginal(rs.getInt("Id"));
                                    proprietarios.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.proprietariosDAO().Create(proprietarios);
                                } else
                                {
                                    proprietarios.setDescricao(rs.getString("Descricao"));
                                    proprietarios.setEmpresa(rs.getString("NomeFantasia"));
                                    proprietarios.setIdOriginal(rs.getInt("Id"));
                                    proprietarios.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.proprietariosDAO().Update(proprietarios);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - PROPRIETARIOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        /*

                        //region INVENTARIO PLANEJADO
                        String rv_InventarioPlanejado = dbInstance.inventarioPlanejadoDAO().GetLastRowVersion() != null ? dbInstance.inventarioPlanejadoDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT * FROM InventarioPlanejados WHERE RowVersion > " + rv_InventarioPlanejado);)
                        {
                            while (rs.next())
                            {
                                InventarioPlanejado inventarioPlanejado = dbInstance.inventarioPlanejadoDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (inventarioPlanejado == null)
                                {
                                    inventarioPlanejado = new InventarioPlanejado();
                                    inventarioPlanejado.setApplicationUserItemIdOriginal(rs.getString("ApplicationUserItemId"));
                                    inventarioPlanejado.setDescricao(rs.getString("Descricao"));
                                    inventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                    inventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.inventarioPlanejadoDAO().Create(inventarioPlanejado);
                                } else
                                {
                                    inventarioPlanejado = new InventarioPlanejado();
                                    inventarioPlanejado.setApplicationUserItemIdOriginal(rs.getString("ApplicationUserItemId"));
                                    inventarioPlanejado.setDescricao(rs.getString("Descricao"));
                                    inventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                    inventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.inventarioPlanejadoDAO().Update(inventarioPlanejado);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - INVENTARIO PLANEJADO", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region LISTA MATERIAIS INVENTARIO PLANEJADO
                        String rv_ListaMateriaisInventarioPlanejado = dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT * FROM listaMateriaisInventarioPlanejados WHERE RowVersion > " + rv_ListaMateriaisInventarioPlanejado);)
                        {
                            while (rs.next())
                            {
                                ListaMateriaisInventarioPlanejado listaMateriaisInventarioPlanejado = dbInstance.listaMateriaisInventarioPlanejadoDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (listaMateriaisInventarioPlanejado == null)
                                {
                                    listaMateriaisInventarioPlanejado = new ListaMateriaisInventarioPlanejado();
                                    listaMateriaisInventarioPlanejado.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    listaMateriaisInventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                    listaMateriaisInventarioPlanejado.setInventarioPlanejadoItemId(rs.getInt("InventarioPlanejadoItemId"));
                                    listaMateriaisInventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.listaMateriaisInventarioPlanejadoDAO().Create(listaMateriaisInventarioPlanejado);

                                } else
                                {
                                    listaMateriaisInventarioPlanejado = new ListaMateriaisInventarioPlanejado();
                                    listaMateriaisInventarioPlanejado.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    listaMateriaisInventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                    listaMateriaisInventarioPlanejado.setInventarioPlanejadoItemId(rs.getInt("InventarioPlanejadoItemId"));
                                    listaMateriaisInventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));

                                    dbInstance.listaMateriaisInventarioPlanejadoDAO().Update(listaMateriaisInventarioPlanejado);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - LISTA MATERIAIS INVENTARIO PLANEJADO", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region LISTA SERVIÇOS
                        String rv_ListaServicos = dbInstance.listaServicosListaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaServicosListaTarefasDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT DataConclusao, DataInicio, Id, ListaTarefasItemId, Resultado, RowVersion, ServicoAdicinalItemId, Status FROM ListaServicosListaTarefas WHERE RowVersion > " + rv_ListaServicos);)
                        {
                            while (rs.next())
                            {
                                ListaServicosListaTarefas listaServicosListaTarefas = dbInstance.listaServicosListaTarefasDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (listaServicosListaTarefas == null)
                                {
                                    listaServicosListaTarefas = new ListaServicosListaTarefas();
                                    listaServicosListaTarefas.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                    listaServicosListaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaServicosListaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaServicosListaTarefas.setListaTarefasItemIdOriginal(rs.getInt("ListaTarefasItemId"));
                                    listaServicosListaTarefas.setResultado(rs.getString("Resultado"));
                                    listaServicosListaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaServicosListaTarefas.setServicoAdicinalItemIdOriginal(rs.getInt("ServicoAdicinalItemId"));
                                    listaServicosListaTarefas.setStatus(rs.getString("Status"));

                                    dbInstance.listaServicosListaTarefasDAO().Create(listaServicosListaTarefas);
                                } else
                                {
                                    listaServicosListaTarefas.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                    listaServicosListaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaServicosListaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaServicosListaTarefas.setListaTarefasItemIdOriginal(rs.getInt("ListaTarefasItemId"));
                                    listaServicosListaTarefas.setResultado(rs.getString("Resultado"));
                                    listaServicosListaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaServicosListaTarefas.setServicoAdicinalItemIdOriginal(rs.getInt("ServicoAdicinalItemId"));
                                    listaServicosListaTarefas.setStatus(rs.getString("Status"));

                                    dbInstance.listaServicosListaTarefasDAO().Update(listaServicosListaTarefas);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - LISTA SERVIÇOS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region LISTA MATERIAIS
                        String rv_ListaMateriais = dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT  CadastroMateriaisItemId, DataConclusao, DataInicio, Id, ListaTarefasItemId, Observacao, RowVersion, Status FROM ListaMateriaisListaTarefas WHERE RowVersion > " + rv_ListaMateriais);)
                        {
                            while (rs.next())
                            {
                                ListaMateriaisListaTarefas listaMateriaisListaTarefas = dbInstance.listaMateriaisListaTarefasDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (listaMateriaisListaTarefas == null)
                                {
                                    listaMateriaisListaTarefas = new ListaMateriaisListaTarefas();
                                    listaMateriaisListaTarefas.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    listaMateriaisListaTarefas.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                    listaMateriaisListaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaMateriaisListaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaMateriaisListaTarefas.setListaTarefasItemIdOriginal(rs.getInt("ListaTarefasItemId"));
                                    listaMateriaisListaTarefas.setObservacao(rs.getString("Observacao"));
                                    listaMateriaisListaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaMateriaisListaTarefas.setStatus(rs.getString("Status"));

                                    dbInstance.listaMateriaisListaTarefasDAO().Create(listaMateriaisListaTarefas);
                                } else
                                {
                                    listaMateriaisListaTarefas.setCadastroMateriaisItemIdOriginal(rs.getInt("CadastroMateriaisItemId"));
                                    listaMateriaisListaTarefas.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                    listaMateriaisListaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaMateriaisListaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaMateriaisListaTarefas.setListaTarefasItemIdOriginal(rs.getInt("ListaTarefasItemId"));
                                    listaMateriaisListaTarefas.setObservacao(rs.getString("Observacao"));
                                    listaMateriaisListaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaMateriaisListaTarefas.setStatus(rs.getString("Status"));

                                    dbInstance.listaMateriaisListaTarefasDAO().Update(listaMateriaisListaTarefas);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - LISTA MATERIAIS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        //region LISTA TAREFAS
                        String rv_ListaTarefas = dbInstance.listaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaTarefasDAO().GetLastRowVersion() : "0";
                        try(ResultSet rs = stmt.executeQuery("SELECT DataCancelamento, DataFimPrevisao, DataFimReal, lt.DataInicio, lt.Dominio, lt.Id, ProcessoItemId, RowVersion, lt.Status, TarefaItemId, pr.CadastroEquipamentosItemId from ListaTarefas as lt INNER JOIN ProcessoEqReadinessTables as pr ON lt.ProcessoItemId = pr.Id WHERE RowVersion > " + rv_ListaTarefas);)
                        {
                            while (rs.next())
                            {
                                ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetByIdOriginal(rs.getInt("Id"));

                                if (listaTarefas == null)
                                {
                                    listaTarefas = new ListaTarefas();
                                    listaTarefas.setDataCancelamento(rs.getTimestamp("DataCancelamento") != null ? new java.util.Date(rs.getTimestamp("DataCancelamento").getTime()) : null);
                                    listaTarefas.setDataFimPrevisao(rs.getTimestamp("DataFimPrevisao") != null ? new java.util.Date(rs.getTimestamp("DataFimPrevisao").getTime()) : null);
                                    listaTarefas.setDataFimReal(rs.getTimestamp("DataFimReal") != null ? new java.util.Date(rs.getTimestamp("DataFimReal").getTime()) : null);
                                    listaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaTarefas.setDominio(rs.getString("Dominio"));
                                    listaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaTarefas.setProcesso(rs.getInt("ProcessoItemId"));
                                    listaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaTarefas.setStatus(rs.getString("Status"));
                                    listaTarefas.setTarefaItemIdOriginal(rs.getInt("TarefaItemId"));
                                    listaTarefas.setCadsatroEquipamentosItemIdOriginal(rs.getInt("CadastroEquipamentosItemId"));

                                    dbInstance.listaTarefasDAO().Create(listaTarefas);
                                } else
                                {
                                    listaTarefas.setDataCancelamento(rs.getTimestamp("DataCancelamento") != null ? new java.util.Date(rs.getTimestamp("DataCancelamento").getTime()) : null);
                                    listaTarefas.setDataFimPrevisao(rs.getTimestamp("DataFimPrevisao") != null ? new java.util.Date(rs.getTimestamp("DataFimPrevisao").getTime()) : null);
                                    listaTarefas.setDataFimReal(rs.getTimestamp("DataFimReal") != null ? new java.util.Date(rs.getTimestamp("DataFimReal").getTime()) : null);
                                    listaTarefas.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                    listaTarefas.setDominio(rs.getString("Dominio"));
                                    listaTarefas.setIdOriginal(rs.getInt("Id"));
                                    listaTarefas.setProcesso(rs.getInt("ProcessoItemId"));
                                    listaTarefas.setRowVersion("0x" + rs.getString("RowVersion"));
                                    listaTarefas.setStatus(rs.getString("Status"));
                                    listaTarefas.setTarefaItemIdOriginal(rs.getInt("TarefaItemId"));
                                    listaTarefas.setCadsatroEquipamentosItemIdOriginal(rs.getInt("CadastroEquipamentosItemId"));

                                    dbInstance.listaTarefasDAO().Update(listaTarefas);
                                }

                            }
                        } catch (Exception e)
                        {
                            _handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    _pd.dismiss();
                                    showMessage("ERRO - TAREFAS", e.getMessage(), 2);
                                }
                            });
                        }
                        //endregion

                        */

                        _pd.dismiss();
                        _handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("AVISO", "SINCRONIZADO COM SUCESSO!", 1);
                            }
                        });

                    }
                } catch (SQLException e){
                    _handler.post(new Runnable() {
                        @Override
                        public void run() {
                            _pd.dismiss();
                            showMessage("Erro", e.getMessage(), 2);
                        }
                    });
                }
            }
        }).start();
    }

    protected Connection DbConnect()
    {

        //CONEXÃOSERVIDORSQL   --   DESENVOLVIMENTO ***
        String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="198.38.85.183";
        String portaSQL="1433";
        String dataBaseName="idativos2019_omni";
        String username="vrcarnei_omni2019";
        String password="idutto@04d";


        //CONEXÃOSERVIDORSQL   --   HOMOLOGAÇÃO***

/*      String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="162.144.63.165";
        String portaSQL="1433";
        String dataBaseName="idativos_omni02";
        String username="sa";
        String password="idutto@04d";*/


/*        //CONEXÃOSERVIDORSQL   --   PRODUÇÃO***

        String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="omni.idutto.com.br";
        String portaSQL="1433";
        String dataBaseName="idativos_omni02";
        String username="sa";
        String password="1dutto@04d";*/



        Connection DbConn = null;
        try {
            Class.forName(driver).newInstance();
            DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Server_Adress + ":" + portaSQL + ";databaseName=" + dataBaseName + ";user=" + username + ";password=" + password);

        } catch (final SQLException e)  {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _pd.dismiss();
                    showMessage("AVISO","Não foi possivel conectar ao banco de dados!" + e.toString(), 3);
                }
            });
            return null;
        } catch (final Exception e)  {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _pd.dismiss();
                    showMessage("AVISO","Não foi possivel conectar ao banco de dados!" + e.toString(), 3);
                }
            });
            return null;
        }

        return DbConn;
    }

    private void showMessage(String title, String message, int type)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(_context);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        switch (type){
            case 1:
                builder.setIcon(R.mipmap.ic_check_circle_green_36dp);
                break;
            case 2:
                builder.setIcon(R.mipmap.ic_error_red_36dp);
                break;
            case 3:
                builder.setIcon(R.mipmap.ic_warning_yellow_36dp);
                break;
            default:
        }
        builder.create().show();
    }

    private void showProgressDialog()
    {
        _pd = new ProgressDialog(_context);

        _pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _pd.setTitle("Aguarde!");
        _pd.setMessage("SINCRONIZANDO ..." + "\n\nDispositivo N° Série: " + _device);
        _pd.setIndeterminate(false);
        _pd.setCancelable(false);
        //_pd.setProgress(0);
        _pd.show();
    }

    private boolean IsTabletAtivo(Statement stmt)
    {
        //VERIFICANDO SE TABLET ENCONTRA-SE CADASTRADO E ATIVO
        try(ResultSet rs = stmt.executeQuery("SELECT * FROM Coletores WHERE Codigo = '" + _device+ "' AND FlagAtivo = 1");)
        {
            if (!rs.next())
            {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("ATENÇÃO", "TABLET NÃO CADASTRADO: SN: " + _device, 3);
                    }
                });
                return false;
            }
        }catch (Exception e)
        {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    showMessage("ERRO", "VERIFICAR TABLET ATIVO!", 2);
                }
            });
        }

        //VERIFICANDO A VERSÃO
        try(ResultSet rs = stmt.executeQuery("SELECT TOP 1 Versao FROM IdentificacaoSistema");)
        {
            while (rs.next())
            {
                String versaoServidor = rs.getString("Versao");
                if (versaoServidor.equals(_versaoApp))
                {
                    return true;
                }

                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("ATENÇÃO", "VERSÕES INCOMPATÍVEIS. VERSÃO SERVIDOR: " + versaoServidor + " / VERSÃO APP: " + _versaoApp, 3);
                    }
                });
                return false;
            }
        } catch (Exception e)
        {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    showMessage("ERRO", "COMPARAÇÃO DE VERSÕES!", 2);
                }
            });
        }

        return false;
    }

    public void checkNetworkQuality()
    {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://omni.idutto.com.br/omni_logo.png")
                .build();

        _mDeviceBandwidthSampler.startSampling();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                _mDeviceBandwidthSampler.stopSampling();
                // Retry for up to 10 times until we find a ConnectionClass.
                if (_mConnectionClass == ConnectionQuality.UNKNOWN && _mTries < 10) {
                    _mTries++;
                    checkNetworkQuality();
                }
                if (!_mDeviceBandwidthSampler.isSampling()) {
                    //mRunningBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d(_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.d(_TAG, response.body().string());
                Log.d(_TAG, _mConnectionClassManager.getCurrentBandwidthQuality().toString());

                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("ATENÇÃO", _mConnectionClassManager.getCurrentBandwidthQuality().toString(), 3);
                    }
                });

                _mDeviceBandwidthSampler.stopSampling();
            }
        });
    }

}
