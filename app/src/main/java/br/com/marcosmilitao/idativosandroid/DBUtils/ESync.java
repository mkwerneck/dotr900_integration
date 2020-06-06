package br.com.marcosmilitao.idativosandroid.DBUtils;

//Versão melhorada da classe Sync

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Almoxarifados;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Funcoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
//import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisInventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisInventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ParametrosPadrao;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Processos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Proprietarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Tarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBCadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBDescartes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBHistoricoLocalizacao;
//import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriaisListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBProcesso;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Events.SyncEvent;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import de.greenrobot.event.EventBus;
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
    private Connection DbConn;
    private Handler _handler;
    private ProgressDialog _pd;
    private String _versaoApp;
    private boolean sincronizando = false;
    private ApplicationDB dbInstance;

    private ConnectionClassManager _mConnectionClassManager;
    private DeviceBandwidthSampler _mDeviceBandwidthSampler;
    private ConnectionQuality _mConnectionClass;
    private int _mTries;
    private String _TAG;
    private EventBus bus;

    private ESync(){
        dbInstance = RoomImplementation.getmInstance().getDbInstance();
        bus = EventBus.getDefault();
    }

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
        _handler = new Handler(Looper.getMainLooper());
        _context = context;

        //Verificando se o tablet esta conectado a internet
        boolean connected = isConnectingToInternet();

        if (!sincronizando)
        {
            if (connected)
            {
                DbConn = DbConnect();

                sincronizando = true;
                _versaoApp = _context.getResources().getString(R.string.numero_versao_apk);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
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

                                //DATA DO CORTE DE PROCESSOS
                                Calendar calendar = Calendar.getInstance();
                                calendar.add(Calendar.DAY_OF_MONTH, -20);

                                String dataHoraEvento = mFormatter.format(calendar.getTime());

                                //SINCRONIZAÇÃO SQLITE > SQL SERVER

                                //region UPMOBCADASTRO MATERIAIS
                                for (UPMOBCadastroMateriais upmobCadastroMateriais : dbInstance.upmobCadastroMateriaisDAO().GetAllRecords())
                                {
                                    try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBCadastroMateriais VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");)
                                    {
                                        pstmt.setInt(1, upmobCadastroMateriais.getIdOriginal());
                                        pstmt.setString(2, upmobCadastroMateriais.getNumSerie());
                                        pstmt.setString(3, upmobCadastroMateriais.getPatrimonio());
                                        pstmt.setInt(4, upmobCadastroMateriais.getQuantidade());
                                        pstmt.setTimestamp(5, upmobCadastroMateriais.getDataValidadeCalibracao() != null ? new Timestamp(upmobCadastroMateriais.getDataValidadeCalibracao().getTime()) : null);
                                        pstmt.setTimestamp(6, upmobCadastroMateriais.getDataValidadeInspecao() != null ? new Timestamp(upmobCadastroMateriais.getDataValidadeInspecao().getTime()) : null);
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
                                        pstmt.setString(20, upmobCadastroMateriais.getStatus());

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
                                    try(PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBHistoricoLocalizacao VALUES (?,?,?,?,?,?,?,?)");)
                                    {
                                        pstmt.setTimestamp(1, upmobHistoricoLocalizacao.getDataHoraEvento() != null ? new Timestamp(upmobHistoricoLocalizacao.getDataHoraEvento().getTime()) : null);
                                        pstmt.setString(2, upmobHistoricoLocalizacao.getProcesso());
                                        pstmt.setString(3, upmobHistoricoLocalizacao.getCodColetor());
                                        pstmt.setString(4, upmobHistoricoLocalizacao.getDescricaoErro());
                                        pstmt.setBoolean(5, upmobHistoricoLocalizacao.getFlagErro());
                                        pstmt.setBoolean(6, upmobHistoricoLocalizacao.getFlagProcess());
                                        pstmt.setInt(7, upmobHistoricoLocalizacao.getCadastroMateriaisId());
                                        pstmt.setInt(8, upmobHistoricoLocalizacao.getPosicaoId());

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

                                //region UPMOBLISTATAREFAS
                                for (UPMOBListaTarefas upmobListaTarefas : dbInstance.upmobListaTarefasDAO().GetAllRecords())
                                {
                                    try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaTarefas VALUES (?,?,?,?,?)");)
                                    {
                                        pstmt.setInt(1, upmobListaTarefas.getIdOriginal());
                                        pstmt.setTimestamp(2, upmobListaTarefas.getDataHoraEvento() != null ? new Timestamp(upmobListaTarefas.getDataHoraEvento().getTime()) : null);
                                        pstmt.setInt(3, upmobListaTarefas.getProcessoId());
                                        pstmt.setInt(4, upmobListaTarefas.getTarefaId());
                                        pstmt.setString(5, upmobListaTarefas.getCodColetor());

                                        pstmt.execute();

                                        dbInstance.upmobListaTarefasDAO().Delete(upmobListaTarefas);

                                    } catch (Exception e)
                                    {
                                        {
                                            _handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    _pd.dismiss();
                                                    showMessage("ERRO - UPMOBListaTarefas", e.getMessage(), 2);
                                                }
                                            });
                                        }
                                    }
                                }
                                //endregion

                                //region UPMOBLISTASERVIÇOS
                                for (UPMOBListaServicosListaTarefas upmobListaServicosListaTarefas : dbInstance.upmobListaServicosListaTarefasDAO().GetAllRecords())
                                {
                                    try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaServicosAdicionais VALUES (?,?,?,?,?,?,?)");)
                                    {
                                        pstmt.setInt(1, upmobListaServicosListaTarefas.getIdOriginal());
                                        pstmt.setString(2, upmobListaServicosListaTarefas.getResultado());
                                        pstmt.setTimestamp(3, upmobListaServicosListaTarefas.getDataHoraEvento() != null ? new Timestamp(upmobListaServicosListaTarefas.getDataHoraEvento().getTime()) : null);
                                        pstmt.setTimestamp(4, upmobListaServicosListaTarefas.getUltimaAtualizacao() != null ? new Timestamp(upmobListaServicosListaTarefas.getUltimaAtualizacao().getTime()) : null);
                                        pstmt.setInt(5, upmobListaServicosListaTarefas.getListaTarefaId());
                                        pstmt.setInt(6, upmobListaServicosListaTarefas.getServicoId());
                                        pstmt.setString(7, upmobListaServicosListaTarefas.getCodColetor());

                                        pstmt.execute();

                                        dbInstance.upmobListaServicosListaTarefasDAO().Delete(upmobListaServicosListaTarefas);

                                    } catch (Exception e)
                                    {
                                        {
                                            _handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    _pd.dismiss();
                                                    showMessage("ERRO - UPMOBListaServicos", e.getMessage(), 2);
                                                }
                                            });
                                        }
                                    }
                                }
                                //endregion

                                //region UPMOBLISTAMATERIAIS
                                for (UPMOBListaMateriais upmobListaMateriais : dbInstance.upmobListaMateriaisDAO().GetAllRecords())
                                {
                                    try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBListaMateriais VALUES (?,?,?,?,?)");)
                                    {
                                        pstmt.setInt(1, upmobListaMateriais.getIdOriginal());
                                        pstmt.setInt(2, upmobListaMateriais.getProcessoId());
                                        pstmt.setTimestamp(3, upmobListaMateriais.getDataHoraEvento() != null ? new Timestamp(upmobListaMateriais.getDataHoraEvento().getTime()) : null);
                                        pstmt.setInt(4, upmobListaMateriais.getCadastroMateriaisId());
                                        pstmt.setString(5, upmobListaMateriais.getCodColetor());

                                        pstmt.execute();

                                        dbInstance.upmobListaMateriaisDAO().Delete(upmobListaMateriais);

                                    } catch (Exception e)
                                    {
                                        {
                                            _handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    _pd.dismiss();
                                                    showMessage("ERRO - UPMOBListaMateriais", e.getMessage(), 2);
                                                }
                                            });
                                        }
                                    }
                                }
                                //endregion

                                //region UPMOBPROCESSO
                                for (UPMOBProcesso upmobProcesso : dbInstance.upmobProcessoDAO().GetAllRecords())
                                {
                                    try (PreparedStatement pstmt = DbConn.prepareStatement("INSERT INTO UPMOBProcessos VALUES (?,?,?,?,?,?,?)");)
                                    {
                                        pstmt.setInt(1, upmobProcesso.getIdOriginal());
                                        pstmt.setTimestamp(2, upmobProcesso.getDataHoraEvento() != null ? new Timestamp(upmobProcesso.getDataHoraEvento().getTime()) : null);
                                        pstmt.setInt(3, upmobProcesso.getCadastroEquipamentoId());
                                        pstmt.setString(4, upmobProcesso.getDescricaoErro());
                                        pstmt.setBoolean(5, upmobProcesso.isFlagErro());
                                        pstmt.setBoolean(6, upmobProcesso.isFlagProcess());
                                        pstmt.setString(7, upmobProcesso.getCodColetor());

                                        pstmt.execute();

                                        dbInstance.upmobProcessoDAO().Delete(upmobProcesso);

                                    } catch (Exception e)
                                    {
                                        {
                                            _handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    _pd.dismiss();
                                                    showMessage("ERRO - UPMOBProcessos", e.getMessage(), 2);
                                                }
                                            });
                                        }
                                    }
                                }
                                //endregion

                                //SINCRONIZAÇÃO SQL SERVER > SQLITE

                                //region PARAMETROS PADRÕES
                                String rv_Parametros = dbInstance.parametrosPadraoDAO().GetLastRowVersion() != null ? dbInstance.parametrosPadraoDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT cd.BaseId as albase, cd.Id as cdid, cd.RowVersion as cdrowversion " +
                                        "FROM Coletores as cd " +
                                        "WHERE cd.Codigo = '"+ _device + "' AND cd.RowVersion > " + rv_Parametros);)
                                {
                                    while (rs.next())
                                    {
                                        ParametrosPadrao parametrosPadrao = dbInstance.parametrosPadraoDAO().GetByIdOriginal(rs.getInt("cdid"));

                                        if (parametrosPadrao == null)
                                        {
                                            parametrosPadrao = new ParametrosPadrao();
                                            parametrosPadrao.setSetorProprietarioId(rs.getInt("albase"));
                                            parametrosPadrao.setIdOriginal(rs.getInt("cdid"));
                                            parametrosPadrao.setRowVersion("0x" + rs.getString("cdrowversion"));

                                            dbInstance.parametrosPadraoDAO().Create(parametrosPadrao);

                                        } else
                                        {
                                            parametrosPadrao.setSetorProprietarioId(rs.getInt("albase"));
                                            parametrosPadrao.setIdOriginal(rs.getInt("cdid"));
                                            parametrosPadrao.setRowVersion("0x" + rs.getString("cdrowversion"));

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

                                //region ALMOXARIFADOS
                                String rv_Almoxarifado = dbInstance.almoxarifadosDAO().GetLastRowVersion() != null ? dbInstance.almoxarifadosDAO().GetLastRowVersion() : "0";
                                try (ResultSet rs = stmt.executeQuery("SELECT BaseId as abaseid, Codigo as acodigo, Nome as anome, Id as aid, RowVersion as arowversion " +
                                        "FROM Almoxarifados " +
                                        "WHERE RowVersion > " + rv_Almoxarifado);){
                                    while(rs.next()){
                                        Almoxarifados almoxarifado = dbInstance.almoxarifadosDAO().GetByIdOriginal(rs.getInt("aid"));

                                        if (almoxarifado == null)
                                        {
                                            almoxarifado = new Almoxarifados();
                                            almoxarifado.setCodigo(rs.getString("acodigo"));
                                            almoxarifado.setNome(rs.getString("anome"));
                                            almoxarifado.setIdOriginal(rs.getInt("aid"));
                                            almoxarifado.setSetorProprietarioId(rs.getInt("abaseid"));
                                            almoxarifado.setRowVersion("0x" + rs.getString("arowversion"));

                                            dbInstance.almoxarifadosDAO().Create(almoxarifado);
                                        }
                                        else
                                        {
                                            almoxarifado.setCodigo(rs.getString("acodigo"));
                                            almoxarifado.setNome(rs.getString("anome"));
                                            almoxarifado.setIdOriginal(rs.getInt("aid"));
                                            almoxarifado.setSetorProprietarioId(rs.getInt("abaseid"));
                                            almoxarifado.setRowVersion("0x" + rs.getString("arowversion"));

                                            dbInstance.almoxarifadosDAO().Update(almoxarifado);
                                        }
                                    }
                                }catch(Exception e)
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - ALMOXARIFADOS", e.getMessage(), 2);
                                        }
                                    });
                                }
                                //endregion

                                //region POSICOES
                                String rv_Posicao = dbInstance.posicoesDAO().GetLastRowVersion() != null ? dbInstance.posicoesDAO().GetLastRowVersion() : "0";
                                try (ResultSet rs = stmt.executeQuery("SELECT p.Codigo as pcodigo, p.AlmoxarifadoItemId as acodigo, p.Descricao as pdescricao, p.Id as pid, p.RowVersion as prowversion, t.TAGID as ttagid " +
                                        "FROM Posicoes as p INNER JOIN Almoxarifados as a ON p.AlmoxarifadoItemId = a.Id INNER JOIN TAGIDPosicao as t ON p.TAGIDPosicaoItemId = t.Id " +
                                        "WHERE p.RowVersion > " + rv_Posicao);){
                                    while(rs.next()){
                                        Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(rs.getInt("pid"));

                                        if (posicao == null)
                                        {
                                            posicao = new Posicoes();
                                            posicao.setCodigo(rs.getString("pcodigo"));
                                            posicao.setAlmoxarifadoId(rs.getInt("acodigo"));
                                            posicao.setDescricao(rs.getString("pdescricao"));
                                            posicao.setIdOriginal(rs.getInt("pid"));
                                            posicao.setRowVersion("0x" + rs.getString("prowversion"));
                                            posicao.setTAGID(rs.getString("ttagid"));

                                            dbInstance.posicoesDAO().Create(posicao);
                                        }
                                        else
                                        {
                                            posicao.setCodigo(rs.getString("pcodigo"));
                                            posicao.setAlmoxarifadoId(rs.getInt("acodigo"));
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
                                try (ResultSet rs = stmt.executeQuery("SELECT c.DadosTecnicos as cdadostecnicos, c.DataCadastro as cdatacadastro, c.DataEntradaNotaFiscal as cdatanotafiscal, c.DataValidadeCalibracao as cdatacalibracao, c.DataValidadeInspecao as cdatainspecao, c.Id as cid, c.ModeloMateriaisItemId as cmodeloid, c.NotaFiscal as cnotafiscal, c.NumSerie as cnumserie, c.Patrimonio as cpatrimonio, c.PosicaoOriginalItemId as cposicaoid, c.Quantidade as cquantidade, c.RowVersion as crowversion, t.TAGID as ttagid, c.ValorUnitario as cvalorunitario, c.EmUso as cemuso, c.Status as cstatus " +
                                        "FROM CadastroMateriais as c " +
                                        "INNER JOIN TAGIDMaterial as t ON c.TAGIDMaterialItemId = t.Id " +
                                        "WHERE c.RowVersion > " + rv_CadastroMateriais);)
                                {
                                    while(rs.next()){
                                        CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(rs.getInt("cid"));

                                        if (cadastroMateriais == null)
                                        {
                                            cadastroMateriais = new CadastroMateriais();
                                            cadastroMateriais.setCategoria("");
                                            cadastroMateriais.setDadosTecnicos(rs.getString("cdadostecnicos"));
                                            cadastroMateriais.setDataCadastro(rs.getTimestamp("cdatacadastro") != null ? new java.util.Date(rs.getTimestamp("cdatacadastro").getTime()) : null);
                                            cadastroMateriais.setDataEntradaNotaFiscal(rs.getTimestamp("cdatanotafiscal") != null ? new java.util.Date(rs.getTimestamp("cdatanotafiscal").getTime()) : null);
                                            cadastroMateriais.setDataValidadeCalibracao(rs.getTimestamp("cdatacalibracao") != null ? new java.util.Date(rs.getTimestamp("cdatacalibracao").getTime()) : null);
                                            cadastroMateriais.setDataValidadeInspecao(rs.getTimestamp("cdatainspecao") != null ? new java.util.Date(rs.getTimestamp("cdatainspecao").getTime()) : null);
                                            cadastroMateriais.setIdOriginal(rs.getInt("cid"));
                                            cadastroMateriais.setModeloMateriaisItemIdOriginal(rs.getInt("cmodeloid"));
                                            cadastroMateriais.setNotaFiscal(rs.getString("cnotafiscal"));
                                            cadastroMateriais.setNumSerie(rs.getString("cnumserie"));
                                            cadastroMateriais.setPatrimonio(rs.getString("cpatrimonio"));
                                            cadastroMateriais.setPosicaoOriginalItemIdoriginal(rs.getInt("cposicaoid"));
                                            cadastroMateriais.setQuantidade(rs.getInt("cquantidade"));
                                            cadastroMateriais.setRowVersion("0x" + rs.getString("crowversion"));
                                            cadastroMateriais.setTAGID(rs.getString("ttagid"));
                                            cadastroMateriais.setValorUnitario(rs.getFloat("cvalorunitario"));
                                            cadastroMateriais.setEmUso(rs.getBoolean("cemuso"));
                                            cadastroMateriais.setStatus(rs.getString("cstatus"));

                                            dbInstance.cadastroMateriaisDAO().Create(cadastroMateriais);
                                        }
                                        else
                                        {
                                            cadastroMateriais.setCategoria("");
                                            cadastroMateriais.setDadosTecnicos(rs.getString("cdadostecnicos"));
                                            cadastroMateriais.setDataCadastro(rs.getTimestamp("cdatacadastro") != null ? new java.util.Date(rs.getTimestamp("cdatacadastro").getTime()) : null);
                                            cadastroMateriais.setDataEntradaNotaFiscal(rs.getTimestamp("cdatanotafiscal") != null ? new java.util.Date(rs.getTimestamp("cdatanotafiscal").getTime()) : null);
                                            cadastroMateriais.setDataValidadeCalibracao(rs.getTimestamp("cdatacalibracao") != null ? new java.util.Date(rs.getTimestamp("cdatacalibracao").getTime()) : null);
                                            cadastroMateriais.setDataValidadeInspecao(rs.getTimestamp("cdatainspecao") != null ? new java.util.Date(rs.getTimestamp("cdatainspecao").getTime()) : null);
                                            cadastroMateriais.setIdOriginal(rs.getInt("cid"));
                                            cadastroMateriais.setModeloMateriaisItemIdOriginal(rs.getInt("cmodeloid"));
                                            cadastroMateriais.setNotaFiscal(rs.getString("cnotafiscal"));
                                            cadastroMateriais.setNumSerie(rs.getString("cnumserie"));
                                            cadastroMateriais.setPatrimonio(rs.getString("cpatrimonio"));
                                            cadastroMateriais.setPosicaoOriginalItemIdoriginal(rs.getInt("cposicaoid"));
                                            cadastroMateriais.setQuantidade(rs.getInt("cquantidade"));
                                            cadastroMateriais.setRowVersion("0x" + rs.getString("crowversion"));
                                            cadastroMateriais.setTAGID(rs.getString("ttagid"));
                                            cadastroMateriais.setValorUnitario(rs.getFloat("cvalorunitario"));
                                            cadastroMateriais.setStatus(rs.getString("cstatus"));

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
                                try(ResultSet rs = stmt.executeQuery("SELECT ce.Categoria as ccategoria, t.Codigo as tcodigo, t.Descricao as tdescricao, t.FlagDependenciaMaterial as tdependenciam, t.FlagDependenciaServico as tdependencias, t.FlagEntradaAlmoxarifado as tentrada, t.FlagSaidaAlmoxarifado as tsaida, t.Id as tid, t.RowVersion as trowversion, t.Titulo as ttitulo " +
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
                                            tarefas.setFlagEntradaAlmoxarifado(rs.getBoolean("tentrada"));
                                            tarefas.setFlagSaidaAlmoxarifado(rs.getBoolean("tsaida"));

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
                                            tarefas.setFlagEntradaAlmoxarifado(rs.getBoolean("tentrada"));
                                            tarefas.setFlagSaidaAlmoxarifado(rs.getBoolean("tsaida"));

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

                                //region INVENTARIO PLANEJADO
                                String rv_InvPlan = dbInstance.inventarioPlanejadoDAO().GetLastRowVersion() != null ? dbInstance.inventarioPlanejadoDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT Descricao, Id, RowVersion, ApplicationUserItemId, EmUso " +
                                        "FROM InventarioPlanejados " +
                                        "WHERE RowVersion >" + rv_InvPlan);)
                                {
                                    while (rs.next())
                                    {
                                        InventarioPlanejado inventarioPlanejado = dbInstance.inventarioPlanejadoDAO().GetByIdOriginal(rs.getInt("Id"));

                                        if (inventarioPlanejado == null)
                                        {
                                            inventarioPlanejado = new InventarioPlanejado();
                                            inventarioPlanejado.setDescricao(rs.getString("Descricao"));
                                            inventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                            inventarioPlanejado.setApplicationUserItemIdOriginal(rs.getString("ApplicationUserItemId"));
                                            inventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));
                                            inventarioPlanejado.setEmUso(rs.getBoolean("EmUso"));

                                            dbInstance.inventarioPlanejadoDAO().Create(inventarioPlanejado);
                                        } else
                                        {
                                            inventarioPlanejado.setDescricao(rs.getString("Descricao"));
                                            inventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                            inventarioPlanejado.setApplicationUserItemIdOriginal(rs.getString("ApplicationUserItemId"));
                                            inventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));
                                            inventarioPlanejado.setEmUso(rs.getBoolean("EmUso"));

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
                                String rv_ListMatInvPlan = dbInstance.listaMateriaisInventarioPlanejadoDAO().GetLastRowVersion() != null ? dbInstance.listaMateriaisInventarioPlanejadoDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT Id, RowVersion, CadastroMateriaisItemId, InventarioPlanejadoItemId " +
                                        "FROM listaMateriaisInventarioPlanejados " +
                                        "WHERE RowVersion >" + rv_ListMatInvPlan);)
                                {
                                    while (rs.next())
                                    {
                                        ListaMateriaisInventarioPlanejado listaMateriaisInventarioPlanejado = dbInstance.listaMateriaisInventarioPlanejadoDAO().GetByIdOriginal(rs.getInt("Id"));

                                        if (listaMateriaisInventarioPlanejado == null)
                                        {
                                            listaMateriaisInventarioPlanejado = new ListaMateriaisInventarioPlanejado();
                                            listaMateriaisInventarioPlanejado.setCadastroMateriaisId(rs.getInt("CadastroMateriaisItemId"));
                                            listaMateriaisInventarioPlanejado.setInventarioPlanejadoId(rs.getInt("InventarioPlanejadoItemId"));
                                            listaMateriaisInventarioPlanejado.setIdOriginal(rs.getInt("Id"));
                                            listaMateriaisInventarioPlanejado.setRowVersion("0x" + rs.getString("RowVersion"));

                                            dbInstance.listaMateriaisInventarioPlanejadoDAO().Create(listaMateriaisInventarioPlanejado);
                                        } else
                                        {
                                            listaMateriaisInventarioPlanejado.setCadastroMateriaisId(rs.getInt("CadastroMateriaisItemId"));
                                            listaMateriaisInventarioPlanejado.setInventarioPlanejadoId(rs.getInt("InventarioPlanejadoItemId"));
                                            listaMateriaisInventarioPlanejado.setIdOriginal(rs.getInt("Id"));
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

                                //region LISTA TAREFAS
                                String rv_ListaTarefas = dbInstance.listaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaTarefasDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT lt.Id as ltid, lt.RowVersion as ltrowversion, lt.Status as ltstatus, lt.DataInicio as ltdatainicio, lt.DataConclusao as ltdataconclusao, lt.DataCancelamento as ltdatacancelamento, lt.ProcessoItemId as ltprocessoitemid, lt.TarefaItemId as lttarefaitemid " +
                                        "FROM ListaTarefas as lt INNER JOIN Processos as pr ON pr.Id = lt.ProcessoItemId " +
                                        "WHERE lt.RowVersion >" + rv_ListaTarefas + " AND pr.DataInicio > '" + dataHoraEvento + "'");)
                                {
                                    while (rs.next())
                                    {
                                        ListaTarefas listaTarefas = dbInstance.listaTarefasDAO().GetByIdOriginal(rs.getInt("ltid"));

                                        if (listaTarefas == null)
                                        {
                                            listaTarefas = new ListaTarefas();
                                            listaTarefas.setStatus(rs.getString("ltstatus"));
                                            listaTarefas.setDataInicio(rs.getTimestamp("ltdatainicio") != null ? new java.util.Date(rs.getTimestamp("ltdatainicio").getTime()) : null);
                                            listaTarefas.setDataConclusao(rs.getTimestamp("ltdataconclusao") != null ? new java.util.Date(rs.getTimestamp("ltdataconclusao").getTime()) : null);
                                            listaTarefas.setDataCancelamento(rs.getTimestamp("ltdatacancelamento") != null ? new java.util.Date(rs.getTimestamp("ltdatacancelamento").getTime()) : null);
                                            listaTarefas.setProcessoIdOriginal(rs.getInt("ltprocessoitemid"));
                                            listaTarefas.setTarefaIdOriginal(rs.getInt("lttarefaitemid"));
                                            listaTarefas.setIdOriginal(rs.getInt("ltid"));
                                            listaTarefas.setRowVersion("0x" + rs.getString("ltrowversion"));

                                            dbInstance.listaTarefasDAO().Create(listaTarefas);
                                        } else
                                        {
                                            listaTarefas.setStatus(rs.getString("ltstatus"));
                                            listaTarefas.setDataInicio(rs.getTimestamp("ltdatainicio") != null ? new java.util.Date(rs.getTimestamp("ltdatainicio").getTime()) : null);
                                            listaTarefas.setDataConclusao(rs.getTimestamp("ltdataconclusao") != null ? new java.util.Date(rs.getTimestamp("ltdataconclusao").getTime()) : null);
                                            listaTarefas.setDataCancelamento(rs.getTimestamp("ltdatacancelamento") != null ? new java.util.Date(rs.getTimestamp("ltdatacancelamento").getTime()) : null);
                                            listaTarefas.setProcessoIdOriginal(rs.getInt("ltprocessoitemid"));
                                            listaTarefas.setTarefaIdOriginal(rs.getInt("lttarefaitemid"));
                                            listaTarefas.setIdOriginal(rs.getInt("ltid"));
                                            listaTarefas.setRowVersion("0x" + rs.getString("ltrowversion"));

                                            dbInstance.listaTarefasDAO().Update(listaTarefas);
                                        }

                                    }
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
                                //endregion

                                //region LISTA MATERIAIS
                                String rv_ListaMateriais = dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaMateriaisListaTarefasDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT lm.Id as lmid, lm.RowVersion as lmrowversion, lm.DataInicio as lmdatainicio, lm.DataConclusao as lmdataconclusao, lm.Observacao as lmobservacao, lm.ProcessoItemId as lmprocessoId, lm.CadastroMateriaisItemId as lmcadastromateriaisitemid " +
                                        "FROM ListaMateriaisListaTarefas as lm INNER JOIN Processos as pr ON pr.Id = lm.ProcessoItemId " +
                                        "WHERE lm.RowVersion >" + rv_ListaMateriais + " AND pr.DataInicio > '" + dataHoraEvento + "'");)
                                {
                                    while (rs.next())
                                    {
                                        ListaMateriaisListaTarefas listaMateriais = dbInstance.listaMateriaisListaTarefasDAO().GetByIdOriginal(rs.getInt("lmid"));

                                        if (listaMateriais == null)
                                        {
                                            listaMateriais = new ListaMateriaisListaTarefas();
                                            listaMateriais.setObservacao(rs.getString("lmobservacao"));
                                            listaMateriais.setDataInicio(rs.getTimestamp("lmdatainicio") != null ? new java.util.Date(rs.getTimestamp("lmdatainicio").getTime()) : null);
                                            listaMateriais.setDataConclusao(rs.getTimestamp("lmdataconclusao") != null ? new java.util.Date(rs.getTimestamp("lmdataconclusao").getTime()) : null);
                                            listaMateriais.setProcessoIdOriginal(rs.getInt("lmprocessoId"));
                                            listaMateriais.setCadastroMateriaisIdOriginal(rs.getInt("lmcadastromateriaisitemid"));
                                            listaMateriais.setIdOriginal(rs.getInt("lmid"));
                                            listaMateriais.setRowVersion("0x" + rs.getString("lmrowversion"));

                                            dbInstance.listaMateriaisListaTarefasDAO().Create(listaMateriais);
                                        } else
                                        {
                                            listaMateriais.setObservacao(rs.getString("lmobservacao"));
                                            listaMateriais.setDataInicio(rs.getTimestamp("lmdatainicio") != null ? new java.util.Date(rs.getTimestamp("lmdatainicio").getTime()) : null);
                                            listaMateriais.setDataConclusao(rs.getTimestamp("lmdataconclusao") != null ? new java.util.Date(rs.getTimestamp("lmdataconclusao").getTime()) : null);
                                            listaMateriais.setProcessoIdOriginal(rs.getInt("lmprocessoId"));
                                            listaMateriais.setCadastroMateriaisIdOriginal(rs.getInt("lmcadastromateriaisitemid"));
                                            listaMateriais.setIdOriginal(rs.getInt("lmid"));
                                            listaMateriais.setRowVersion("0x" + rs.getString("lmrowversion"));

                                            dbInstance.listaMateriaisListaTarefasDAO().Update(listaMateriais);
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

                                //region LISTA SERVICOS
                                String rv_ListaServicos = dbInstance.listaServicosListaTarefasDAO().GetLastRowVersion() != null ? dbInstance.listaServicosListaTarefasDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT ls.Id as lsid, ls.RowVersion as lsrowversion, ls.Resultado as lsresultado, ls.UltimaAtualizacao as lsultimaatualizacao, ls.ListaTarefasItemId as lslistatarefaitemid, ls.ServicoAdicinalItemId as lsservicoadicionalitemid " +
                                        "FROM ListaServicosListaTarefas as ls INNER JOIN ListaTarefas as lt ON lt.Id = ls.ListaTarefasItemId INNER JOIN Processos as pr ON pr.Id = lt.ProcessoItemId " +
                                        "WHERE ls.RowVersion >" + rv_ListaServicos + " AND pr.DataInicio > '" + dataHoraEvento + "'");)
                                {
                                    while (rs.next())
                                    {
                                        ListaServicosListaTarefas listaServicos = dbInstance.listaServicosListaTarefasDAO().GetByIdOriginal(rs.getInt("lsid"));

                                        if (listaServicos == null)
                                        {
                                            listaServicos = new ListaServicosListaTarefas();
                                            listaServicos.setResultado(rs.getString("lsresultado"));
                                            listaServicos.setUltimaAtualizacao(rs.getTimestamp("lsultimaatualizacao") != null ? new java.util.Date(rs.getTimestamp("lsultimaatualizacao").getTime()) : null);
                                            listaServicos.setListaTarefaIdOriginal(rs.getInt("lslistatarefaitemid"));
                                            listaServicos.setServicoAdicionalIdOriginal(rs.getInt("lsservicoadicionalitemid"));
                                            listaServicos.setIdOriginal(rs.getInt("lsid"));
                                            listaServicos.setRowVersion("0x" + rs.getString("lsrowversion"));

                                            dbInstance.listaServicosListaTarefasDAO().Create(listaServicos);
                                        } else
                                        {
                                            listaServicos.setResultado(rs.getString("lsresultado"));
                                            listaServicos.setUltimaAtualizacao(rs.getTimestamp("lsultimaatualizacao") != null ? new java.util.Date(rs.getTimestamp("lsultimaatualizacao").getTime()) : null);
                                            listaServicos.setListaTarefaIdOriginal(rs.getInt("lslistatarefaitemid"));
                                            listaServicos.setServicoAdicionalIdOriginal(rs.getInt("lsservicoadicionalitemid"));
                                            listaServicos.setIdOriginal(rs.getInt("lsid"));
                                            listaServicos.setRowVersion("0x" + rs.getString("lsrowversion"));

                                            dbInstance.listaServicosListaTarefasDAO().Update(listaServicos);
                                        }

                                    }
                                } catch (Exception e)
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - LISTA SERVICOS", e.getMessage(), 2);
                                        }
                                    });
                                }
                                //endregion

                                //region PROCESSOS
                                String rv_Processos = dbInstance.processosDAO().GetLastRowVersion() != null ? dbInstance.processosDAO().GetLastRowVersion() : "0";
                                try(ResultSet rs = stmt.executeQuery("SELECT Id, RowVersion, Status, DataInicio, DataConclusao, DataCancelado, CadastroEquipamentosItemId " +
                                        "FROM Processos " +
                                        "WHERE RowVersion >" + rv_Processos + " AND DataInicio > '" + dataHoraEvento + "'");)
                                {
                                    while (rs.next())
                                    {
                                        Processos processos = dbInstance.processosDAO().GetByIdOriginal(rs.getInt("Id"));

                                        if (processos == null)
                                        {
                                            processos = new Processos();
                                            processos.setStatus(rs.getString("Status"));
                                            processos.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                            processos.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                            processos.setDataCancelado(rs.getTimestamp("DataCancelado") != null ? new java.util.Date(rs.getTimestamp("DataCancelado").getTime()) : null);
                                            processos.setCadsatroEquipamentosItemIdOriginal(rs.getInt("CadastroEquipamentosItemId"));
                                            processos.setIdOriginal(rs.getInt("Id"));
                                            processos.setRowVersion("0x" + rs.getString("RowVersion"));

                                            dbInstance.processosDAO().Create(processos);
                                        } else
                                        {
                                            processos.setStatus(rs.getString("Status"));
                                            processos.setDataInicio(rs.getTimestamp("DataInicio") != null ? new java.util.Date(rs.getTimestamp("DataInicio").getTime()) : null);
                                            processos.setDataConclusao(rs.getTimestamp("DataConclusao") != null ? new java.util.Date(rs.getTimestamp("DataConclusao").getTime()) : null);
                                            processos.setDataCancelado(rs.getTimestamp("DataCancelado") != null ? new java.util.Date(rs.getTimestamp("DataCancelado").getTime()) : null);
                                            processos.setCadsatroEquipamentosItemIdOriginal(rs.getInt("CadastroEquipamentosItemId"));
                                            processos.setIdOriginal(rs.getInt("Id"));
                                            processos.setRowVersion("0x" + rs.getString("RowVersion"));

                                            dbInstance.processosDAO().Update(processos);
                                        }

                                    }
                                } catch (Exception e)
                                {
                                    _handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            _pd.dismiss();
                                            showMessage("ERRO - PROCESSOS", e.getMessage(), 2);
                                        }
                                    });
                                }
                                //endregion

                                _pd.dismiss();
                                _handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage("AVISO", "SINCRONIZADO COM SUCESSO!", 1);
                                    }
                                });

                                //Disparando evento de término do sincronismo
                                SyncEvent event = new SyncEvent(true);;
                                bus.post(event);

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

                        sincronizando = false;
                    }
                }).start();
            } else {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(_context, "Sem conexão com a internet", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        } else {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(_context, "Sincronismo em andamento", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    protected Connection DbConnect()
    {

        //CONEXÃOSERVIDORSQL   --   DESENVOLVIMENTO ***
        String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="198.38.85.183";
        String portaSQL="1433";
        String dataBaseName="idativos2019_omni";
        String username="sa";
        String password="idutto16";


        //CONEXÃOSERVIDORSQL   --   HOMOLOGAÇÃO***

/*      String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="162.144.63.165";
        String portaSQL="1433";
        String dataBaseName="idativos_omni02";
        String username="sa";
        String password="idutto@04d";*/


        //CONEXÃOSERVIDORSQL   --   PRODUÇÃO***
/*        String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="189.8.34.140";
        String portaSQL="1433";
        String dataBaseName="idativos2019_omni";
        String username="sa";
        String password="idutto@04d";*/




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
        //abaixando a flag de sincronizando
        sincronizando = false;

        //emitindo alerta
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

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)
            {
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
