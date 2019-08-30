package br.com.marcosmilitao.idativosandroid.Sync;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import javax.xml.transform.Result;

import br.com.marcosmilitao.idativosandroid.DBUtils.ApplicationDB;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Posicoes;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioEquipamento;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_InventarioMaterial;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPMOBListaMateriaisListaTarefaEqReadnesses;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPMOBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBAcoesColetores_Dados;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBCadastrosEquipmentTable;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBEquipment_Type;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBGrupos;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBInventarioPlanejado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaMateriaisInvPlanejado;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaMateriaisListaTarefaEqReadnesses;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaMateriaisTarefaEqReadnesses;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaResultados;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBListaTarefasEqReadinessTables;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBMaterial_Type;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBPosicao;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBProprietario;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBServicos_AdicionaisSet;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBTarefasEqReadinessTable;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBUsuariosSet;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheets;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPWEBWorksheetItens;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Script_Idativos02Data;
import br.com.marcosmilitao.idativosandroid.R;
import br.com.marcosmilitao.idativosandroid.RoomImplementation;
import br.com.marcosmilitao.idativosandroid.SettingsActivity;


/**
 * Created by Idutto07 on 15/12/2016.
 */

public class Sync extends SQLiteOpenHelper {
    private Idativos02Data idativos02Data;
    private Context context1;
    private Query_UPWEBAcoesColetores_Dados query_upwebAcoesColetores_Dados;
    private Query_UPWEBPosicao query_UPWEBPosicao;
    private Query_UPMOBPosicao query_UPMOBPosicao;
    private Query_UPWEBEquipment_Type query_UPWEBEquipment_Type;
    private Query_InventarioMaterial query_InventarioMaterial;
    private Query_InventarioEquipamento query_InventarioEquipamento;
    private Query_UPWEBMaterial_Type query_UPWEBMaterial_Type;
    private Query_UPWEBProprietario query_UPWEBProprietario;
    private Query_UPWEBWorksheets query_UPWEBWorksheets;
    private Query_UPWEBListaTarefasEqReadinessTables query_UPWEBListaTarefasEqReadinessTables;
    private Query_UPMOBListaMateriaisListaTarefaEqReadnesses.Query_UPWEBLista_Serviços_AdicionaisSet query_UPWEBLista_Serviços_AdicionaisSet;
    private Query_UPWEBListaMateriaisListaTarefaEqReadnesses query_UPWEBListaMateriaisListaTarefaEqReadnesses;
    private Query_UPWEBListaResultados query_UPWEBListaResultados;
    private Query_UPWEBListaMateriaisTarefaEqReadnesses query_UPWEBListaMateriaisTarefaEqReadnesses;
    private Query_UPWEBTarefasEqReadinessTable query_UPWEBTarefasEqReadinessTable;
    private Query_UPWEBServicos_AdicionaisSet query_UPWEBServicos_AdicionaisSet;
    private Query_UPWEBUsuariosSet query_UPWEBUsuariosSet;
    private Query_UPWEBWorksheetItens query_upwebWorksheetItens;
    private Query_UPWEBInventarioPlanejado query_upwebInventarioPlanejado;
    private Query_UPWEBListaMateriaisInvPlanejado query_upweblistaMateriaisInvPlanejado;
    private Query_UPWEBGrupos query_UPWEBGrupos;
    private Query_UPWEBCadastrosEquipmentTable query_upwebCadastrosEquipmentTable;



    private Query_InventarioEquipamento.Query_IDs_Sistema query_IDs_Sistema;
    ProgressDialog progress;
    private int num_NF1;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private String versao_IDAtivos;

    public Sync(Context context, SQLiteDatabase db){
        super(context, "Sync", null, 100);

        versao_IDAtivos = context.getResources().getString(R.string.numero_versao_apk);
        idativos02Data = new Idativos02Data(context);
        context1 = context;
        onCreate(db);
    }

    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Verificando conectividade com a internet
        if (!isOnline()){
            showMessage("AVISO","Sem conexão com a internet. O Wi-Fi ou os dados da rede devem estar ativos. Tente novamente!", 3);
            return;
        }

        //Verificando Conexão com Banco de Dados
        final Connection DbConn = DbConnect();

        //Verificando Versão ID-Ativos
        if (!Verificar_Versao(db, DbConn)){
            showMessage("AVISO","ATUALIZE A VERSÃO DO APLICATIVO ANTES DE SINCRONIZAR. Versão Atual: " + versao_IDAtivos, 3);
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this.context1);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Aguarde!");
        pd.setMessage("SINCRONIZANDO ..." + "\n\nDispositivo N° Série: " + Build.SERIAL);
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.setProgress(0);
        pd.show();

        final SQLiteDatabase db2 = db;

        if (DbConn != null){
            final Thread mThread = new Thread() {
                @Override
                public void run() {
                    SyncDatabase(db2, pd, DbConn);
                }
            };
            mThread.start();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int CountUPWEBTables(SQLiteDatabase db, ProgressDialog pd, Connection DbConn, String device) throws SQLException{
        int qtdRegistros = 0;
        Statement countStmt = null;
        Statement tarefaStmt = null;
        Statement materialStmt = null;
        Statement servicoStmt = null;
        Statement resultStmt = null;
        Cursor cursor = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                String queries [] = {
                        "SELECT COUNT(*) as count FROM ModeloEquipamentos WHERE RowVersion > '"+ dbInstance.modeloEquipamentosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.modeloEquipamentosDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM ModelosMateriais WHERE RowVersion > '"+ dbInstance.modeloMateriaisDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.modeloMateriaisDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM CadastroEquipamentos WHERE RowVersion > '"+ dbInstance.cadastroEquipamentosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.cadastroEquipamentosDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM CadastroMateriais WHERE RowVersion > '"+ dbInstance.cadastroMateriaisDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.cadastroMateriaisDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM CadastroMateriaisItens WHERE RowVersion > '"+ dbInstance.cadastroMateriaisItensDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.cadastroMateriaisItensDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM Posicao WHERE RowVersion > '"+ dbInstance.posicoesDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.posicoesDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM SetorProprietario WHERE RowVersion > '"+ dbInstance.proprietariosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.proprietariosDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM Grupo WHERE RowVersion > '"+ dbInstance.gruposDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.gruposDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM ParametrosPadroes WHERE RowVersion > '"+ dbInstance.parametrosPadraoDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.parametrosPadraoDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM Tarefas WHERE RowVersion > '"+ dbInstance.tarefasDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.tarefasDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM ServicosAdicionais WHERE RowVersion > '"+ dbInstance.servicosAdicionaisDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.servicosAdicionaisDAO().GetLastRowVersion()) : 0 +"'",
                        "SELECT COUNT(*) as count FROM AspNetUsers WHERE RowVersion > '"+ dbInstance.usuariosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.usuariosDAO().GetLastRowVersion()) : 0 +"'",

                };

            }
        }).start();

        String queriesUPMOB [] = {
                "SELECT COUNT (*) as count FROM UPMOBUsuariosSets",
                "SELECT COUNT (*) as count FROM UPMOBListaTarefasEqReadinessTables",
                "SELECT COUNT (*) as count FROM UPMOBListaMateriaisListaTarefaEqReadnesses",
                "SELECT COUNT (*) as count FROM UPMOBLista_Servicos_AdicionaisSet",
                "SELECT COUNT (*) as count FROM UPMOBWorksheet",
                "SELECT COUNT (*) as count FROM UPMOBHistoricoLocalizacao",
                "SELECT COUNT (*) as count FROM UPMOBWorksheetItens",
                //"SELECT COUNT (*) as count FROM UPMOBProcessoDescarte",
                //"SELECT COUNT (*) as count FROM UPMOBListaResultadosServicosSet"
        };

        /*//ATUALIZANDO QTDREGISTROS COM REGISTROS DO SQL SERVER
        try {
            countStmt = DbConn.createStatement();

            for (int i = 0; i < queries.length; i ++){
                ResultSet result = countStmt.executeQuery(queries[i]);
                while (result.next()){
                    qtdRegistros = qtdRegistros + result.getInt(result.findColumn("count"));
                }
            }

        }catch(SQLException e){

        } finally {
            if (countStmt != null){
                countStmt.close();
            }
        }*/

        //ATUALIZANDO QTDREGISTROS COM REGISTROS DO SQLLITE
        try{

            for (int i = 0; i < queriesUPMOB.length; i ++){
                cursor = db.rawQuery(queriesUPMOB[i], null);
                while (cursor.moveToNext()){
                    qtdRegistros = qtdRegistros + cursor.getInt(cursor.getColumnIndex("count"));
                }
            }

        }catch(Exception e){

        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        //ATUALIZANDO QTDREGISTROS DE LISTA TAREFA, MATERIAIS E SERVIÇOS
        try {
            tarefaStmt = DbConn.createStatement();
            ResultSet tarefaResult = tarefaStmt.executeQuery("SELECT * FROM UPWEBListaTarefasEqReadinessTablesSet WHERE FlagMobileInsert = 1 and Cod_Coletor='" + device +"' AND Status <> 'Concluída' And Status <> 'Cancelada'");
            while (tarefaResult.next()){
                qtdRegistros ++;

                try {
                    materialStmt = DbConn.createStatement();
                    ResultSet materialResult = materialStmt.executeQuery("SELECT COUNT (*) as count FROM UPWEBListaMateriaisListaTarefaEqReadnessesSet WHERE ListaTarefasEqReadinessTables = " + tarefaResult.getInt("Id_Original") + " And Cod_Coletor='" + device +"'");
                    while (materialResult.next()){
                        qtdRegistros = qtdRegistros + materialResult.getInt(materialResult.findColumn("count"));

                        try {
                            resultStmt = DbConn.createStatement();
                            ResultSet resultadoResult = resultStmt.executeQuery("SELECT COUNT (*) as count FROM UPWEBListaResultadosServicosSet WHERE ListaTarefasEqReadinessTable = " + tarefaResult.getInt("Id_Original") +
                                    " And ListaMateriaisListaTarefasEqReadinessTable = " + materialResult.getInt("Id_Original") +
                                    " And Cod_Coletor = '" + device + "'");
                            while (resultadoResult.next()){
                                qtdRegistros = qtdRegistros + resultadoResult.getInt(resultadoResult.findColumn("count"));
                            }

                        } catch (Exception e){
                        } finally {
                            if (resultStmt != null){
                                resultStmt.close();
                            }
                        }
                    }
                } catch (Exception e){
                } finally {
                    if (materialStmt != null){
                        materialStmt.close();
                    }
                }

                try {
                    servicoStmt = DbConn.createStatement();
                    ResultSet servicoResult = servicoStmt.executeQuery("SELECT COUNT (*) as count FROM UPWEBLista_Servicos_AdicionaisSet WHERE ListaTarefasEqReadinessTable = " + tarefaResult.getInt("Id_Original") + " And Cod_Coletor='" + device +"'");
                    while (servicoResult.next()){
                        qtdRegistros = qtdRegistros + servicoResult.getInt(servicoResult.findColumn("count"));
                    }
                } catch (Exception e){
                } finally {
                    if (servicoStmt != null){
                        servicoStmt.close();
                    }
                }

            }
        } catch (Exception e){

        } finally {
            if (tarefaStmt != null){
                tarefaStmt.close();
            }
        }

        return qtdRegistros;
    }

    public void SyncDatabase(SQLiteDatabase db,ProgressDialog pd, Connection DbConn){

        //Verificando Dispositivo Conectado
        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA == null) {
            showMessage("AVISO","SELECIONE UM LEITOR RFID ANTES DE SINCRONIZAR", 3);
            return;
        }

        pairedDevices = BA.getBondedDevices();
        if (BA == null) {
            showMessage("AVISO","SELECIONE UM LEITOR RFID ANTES DE SINCRONIZAR", 3);
            return;
        }

        String device = Build.SERIAL;
        //String device = "FNM88XV6K0";

        if (device == null) {
            showMessage("AVISO","NÃO FOI POSSÍVEL IDENTIFICAR O NÚMERO DE SÉRIE DO DISPOSITIVO.", 2);
            return;
        }

        int qtdRegistrosTotal = 0;
        int qtdRegistrosAtual = 0;

        //chamando quantidade de registros para gerar progress bar
        /*try{
            qtdRegistrosTotal = CountUPWEBTables(db, pd, DbConn, device);
            pd.setMax(qtdRegistrosTotal);
        } catch (SQLException e){
            pd.dismiss();
            Handler mHandler1 = new Handler(Looper.getMainLooper());
            mHandler1.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (e.toString().contains("code 14")) {
                            showMessage("AVISO!","Não foi possível conectar com Banco de Dados remoto. Verifique sua conexão de dados e/ou sua conexão de rede.", 3);
                        } else {
                            showMessage("AVISO!",e.toString(), 3);
                        }
                    } catch (final Exception e) {
                        showMessage("AVISO","Não foi possível concluir a Sincronização. Tente novamente em alguns minutos, se o problema insistir entre em contato com o Suporte.", 3);
                    }

                }
            });
        }*/

        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Cursor c = null;

            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationDB dbInstance = RoomImplementation.getmInstance().getDbInstance();

                    try
                    {
                        //region POSICOES
                        Statement smtp_PosicaoTables = DbConn.createStatement();
                        ResultSet r_PosicaoTables = smtp_PosicaoTables.executeQuery("SELECT p.Codigo as pcodigo, a.Cod_Almoxarifado as acodigo, p.Descricao as pdescricao, p.Id as pid, p.RowVersion as prowversion, t.TAGID_Posicao as ttagid FROM Posicao as p INNER JOIN AlmoxarifadoTables as a ON p.AlmoxarifadoItemId = a.Id INNER JOIN TAGIDPosicaoTables as t ON p.TAGIDPosicaoItemId = t.Id WHERE p.RowVersion > '"+ dbInstance.posicoesDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.posicoesDAO().GetLastRowVersion()) : 0 +"'");

                        while(r_PosicaoTables.next()){
                            Posicoes posicao = dbInstance.posicoesDAO().GetByIdOriginal(r_PosicaoTables.getInt("Id"));

                            if (posicao == null)
                            {
                                posicao = new Posicoes();
                                posicao.setCodigo(r_PosicaoTables.getString("pcodigo"));
                                posicao.setAlmoxarifado(r_PosicaoTables.getString("acodigo"));
                                posicao.setDescricao(r_PosicaoTables.getString("pdescricao"));
                                posicao.setIdOriginal(r_PosicaoTables.getInt("pid"));
                                posicao.setRowVersion(r_PosicaoTables.getString("prowversion"));
                                posicao.setTAGID(r_PosicaoTables.getString("ttagid"));

                                dbInstance.posicoesDAO().Create(posicao);
                            }
                            else
                            {
                                posicao.setCodigo(r_PosicaoTables.getString("pcodigo"));
                                posicao.setAlmoxarifado(r_PosicaoTables.getString("acodigo"));
                                posicao.setDescricao(r_PosicaoTables.getString("pdescricao"));
                                posicao.setIdOriginal(r_PosicaoTables.getInt("pid"));
                                posicao.setRowVersion(r_PosicaoTables.getString("prowversion"));
                                posicao.setTAGID(r_PosicaoTables.getString("ttagid"));

                                dbInstance.posicoesDAO().Update(posicao);
                            }
                        }
                        //endregion

                        //region CADASTRO MATERIAIS
                        Statement smtp_CadastroMateriais = DbConn.createStatement();
                        ResultSet r_CadastroMateriais = smtp_CadastroMateriais.executeQuery("SELECT ct.Categoria as ctcategoria, c.DadosTecnicos as cdadostecnicos, c.DataCadastro as cdatacadastro, c.DataEntradaNotaFiscal as cdatanotafiscal, c.DataFabricacao as cdatafabricacao, c.DataValidade as cdatavalidade, c.FlagContentor as cflagcontentor, c.Id as cid, c.ModeloMateriaisItemId as cmodeloid, c.NotaFiscal as cnotafiscal, c.NumSerie as cnumserie, c.Patrimonio as cpatrimonio, c.PosicaoOriginalItemId as cposicaoid, c.Quantidade as cquantidade, c.RowVersion as crowversion, c.SetorProprietarioItemId as cproprieetarioid, t.TAGID_Material as ttagid, c.ValorUnitario as cvalorunitario FROM CadastroMateriais as c INNER JOIN Categoria_MateriaisSet as ct ON c.CategoriaMateriaisItemId = ct.Id INNER JOIN TAGID_MateriaisTables as t ON c.TAGIDMaterialItemId = t.Id WHERE c.RowVersion > '"+ dbInstance.cadastroMateriaisDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.cadastroMateriaisDAO().GetLastRowVersion()) : 0 +"'");

                        while(r_CadastroMateriais.next()){
                            CadastroMateriais cadastroMateriais = dbInstance.cadastroMateriaisDAO().GetByIdOriginal(r_CadastroMateriais.getInt("Id"));

                            if (cadastroMateriais == null)
                            {
                                cadastroMateriais = new CadastroMateriais();
                                cadastroMateriais.setCategoria(r_CadastroMateriais.getString("ctcategoria"));
                                cadastroMateriais.setDadosTecnicos(r_CadastroMateriais.getString("cdadostecnicos"));
                                cadastroMateriais.setDataCadastro(r_CadastroMateriais.getDate("cdatacadastro"));
                                cadastroMateriais.setDataEntradaNotaFiscal(r_CadastroMateriais.getDate("cdatanotafiscal"));
                                cadastroMateriais.setDataFabricacao(r_CadastroMateriais.getDate("cdatafabricacao"));
                                cadastroMateriais.setDataValidade(r_CadastroMateriais.getDate("cdatavalidade"));
                                cadastroMateriais.setFlagContentor(r_CadastroMateriais.getBoolean("cflagcontentor"));
                                cadastroMateriais.setIdOriginal(r_CadastroMateriais.getInt("cid"));
                                cadastroMateriais.setModeloMateriaisItemIdOriginal(r_CadastroMateriais.getInt("cmodeloid"));
                                cadastroMateriais.setNotaFiscal(r_CadastroMateriais.getString("cnotafiscal"));
                                cadastroMateriais.setNumSerie(r_CadastroMateriais.getString("cnotafiscal"));
                                cadastroMateriais.setPatrimonio(r_CadastroMateriais.getString("cpatrimonio"));
                                cadastroMateriais.setPosicaoOriginalItemIdoriginal(r_CadastroMateriais.getInt("cposicaoid"));
                                cadastroMateriais.setQuantidade(r_CadastroMateriais.getInt("cquantidade"));
                                cadastroMateriais.setRowVersion(r_CadastroMateriais.getLong("crowversion"));
                                cadastroMateriais.setSetorProprietarioItemIdOriginal(r_CadastroMateriais.getInt("cproprietarioid"));
                                cadastroMateriais.setTAGID(r_CadastroMateriais.getString("ttagid"));
                                cadastroMateriais.setValorUnitario(r_CadastroMateriais.getFloat("cvalorunitario"));

                                dbInstance.cadastroMateriaisDAO().Create(cadastroMateriais);
                            }
                            else
                            {
                                cadastroMateriais.setCategoria(r_CadastroMateriais.getString("ctcategoria"));
                                cadastroMateriais.setDadosTecnicos(r_CadastroMateriais.getString("cdadostecnicos"));
                                cadastroMateriais.setDataCadastro(r_CadastroMateriais.getDate("cdatacadastro"));
                                cadastroMateriais.setDataEntradaNotaFiscal(r_CadastroMateriais.getDate("cdatanotafiscal"));
                                cadastroMateriais.setDataFabricacao(r_CadastroMateriais.getDate("cdatafabricacao"));
                                cadastroMateriais.setDataValidade(r_CadastroMateriais.getDate("cdatavalidade"));
                                cadastroMateriais.setFlagContentor(r_CadastroMateriais.getBoolean("cflagcontentor"));
                                cadastroMateriais.setIdOriginal(r_CadastroMateriais.getInt("cid"));
                                cadastroMateriais.setModeloMateriaisItemIdOriginal(r_CadastroMateriais.getInt("cmodeloid"));
                                cadastroMateriais.setNotaFiscal(r_CadastroMateriais.getString("cnotafiscal"));
                                cadastroMateriais.setNumSerie(r_CadastroMateriais.getString("cnotafiscal"));
                                cadastroMateriais.setPatrimonio(r_CadastroMateriais.getString("cpatrimonio"));
                                cadastroMateriais.setPosicaoOriginalItemIdoriginal(r_CadastroMateriais.getInt("cposicaoid"));
                                cadastroMateriais.setQuantidade(r_CadastroMateriais.getInt("cquantidade"));
                                cadastroMateriais.setRowVersion(r_CadastroMateriais.getLong("crowversion"));
                                cadastroMateriais.setSetorProprietarioItemIdOriginal(r_CadastroMateriais.getInt("cproprietarioid"));
                                cadastroMateriais.setTAGID(r_CadastroMateriais.getString("ttagid"));
                                cadastroMateriais.setValorUnitario(r_CadastroMateriais.getFloat("cvalorunitario"));

                                dbInstance.cadastroMateriaisDAO().Update(cadastroMateriais);
                            }
                        }
                        //endregion

                        //region USUARIOS
                        Statement smtp_Usuarios = DbConn.createStatement();
                        ResultSet r_Usuarios = smtp_Usuarios.executeQuery("SELECT u.Email as email, u.Id as uid, u.NomeCompleto as nomecompleto, u.RowVersion as rowversion, t.TAGID as tagid, u.UserName as username from AspNetUsers as u INNER JOIN TAGIDUsuario as t ON u.TAGIDUsuarioItemId = t.Id WHERE u.RowVersion > '"+ dbInstance.usuariosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.usuariosDAO().GetLastRowVersion()) : 0 +"'");

                        while(r_Usuarios.next()){
                            Usuarios usuarios = dbInstance.usuariosDAO().GetByIdOriginal(r_PosicaoTables.getInt("Id"));

                            if (usuarios == null)
                            {
                                usuarios = new Usuarios();
                                usuarios.setEmail(r_Usuarios.getString("email"));
                                usuarios.setIdOriginal(r_Usuarios.getInt("uid"));
                                usuarios.setNomeCompleto(r_Usuarios.getString("nomecompleto"));
                                usuarios.setRowVersion(r_Usuarios.getInt("rowversion"));
                                usuarios.setTAGID(r_Usuarios.getString("tagid"));
                                usuarios.setUserName(r_Usuarios.getString("username"));

                                dbInstance.usuariosDAO().Create(usuarios);
                            }
                            else
                            {
                                usuarios.setEmail(r_Usuarios.getString("email"));
                                usuarios.setIdOriginal(r_Usuarios.getInt("uid"));
                                usuarios.setNomeCompleto(r_Usuarios.getString("nomecompleto"));
                                usuarios.setRowVersion(r_Usuarios.getInt("rowversion"));
                                usuarios.setTAGID(r_Usuarios.getString("tagid"));
                                usuarios.setUserName(r_Usuarios.getString("username"));

                                dbInstance.usuariosDAO().Update(usuarios);
                            }
                        }
                        //endregion

                        //region MODELO EQUIPAMENTOS
                        Statement smtp_ModeloEquipamentos = DbConn.createStatement();
                        ResultSet r_ModeloEquipamentos = smtp_ModeloEquipamentos.executeQuery("SELECT cat.Categoria as categoria,, me.Descricao_Tecnica as mdescricao, me.Id as mid, me.Modelo as mmodelo, me.Part_Number as mpartnumber, me.RowVersion as mrowversion FROM ModeloEquipamentos as me INNER JOIN Categoria_MateriaisSet as cat ON me.CategoriaEquipamentosId = cat.Id WHERE me.RowVersion > '"+ dbInstance.modeloEquipamentosDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.modeloEquipamentosDAO().GetLastRowVersion()) : 0 +"'");

                        while(r_ModeloEquipamentos.next()){
                            ModeloEquipamentos modeloEquipamentos = dbInstance.modeloEquipamentosDAO().GetByIdOriginal(r_ModeloEquipamentos.getInt("Id"));

                            if (modeloEquipamentos == null)
                            {
                                modeloEquipamentos = new ModeloEquipamentos();
                                modeloEquipamentos.setCategoria(r_ModeloEquipamentos.getString("categoria"));
                                modeloEquipamentos.setDescricaoTecnica(r_ModeloEquipamentos.getString("mdescricao"));
                                modeloEquipamentos.setIdOriginal(r_ModeloEquipamentos.getInt("mid"));
                                modeloEquipamentos.setModelo(r_ModeloEquipamentos.getString("mmodelo"));
                                modeloEquipamentos.setPartNumber(r_ModeloEquipamentos.getString("mpartnumber"));
                                modeloEquipamentos.setRowVersion(r_ModeloEquipamentos.getLong("mrowversion"));

                                dbInstance.modeloEquipamentosDAO().Create(modeloEquipamentos);
                            }
                            else
                            {
                                modeloEquipamentos.setCategoria(r_ModeloEquipamentos.getString("categoria"));
                                modeloEquipamentos.setDescricaoTecnica(r_ModeloEquipamentos.getString("mdescricao"));
                                modeloEquipamentos.setIdOriginal(r_ModeloEquipamentos.getInt("mid"));
                                modeloEquipamentos.setModelo(r_ModeloEquipamentos.getString("mmodelo"));
                                modeloEquipamentos.setPartNumber(r_ModeloEquipamentos.getString("mpartnumber"));
                                modeloEquipamentos.setRowVersion(r_ModeloEquipamentos.getLong("mrowversion"));

                                dbInstance.modeloEquipamentosDAO().Update(modeloEquipamentos);
                            }
                        }
                        //endregion

                        //region GRUPOS
                        Statement smtp_Grupos = DbConn.createStatement();
                        ResultSet r_Grupos = smtp_Grupos.executeQuery("SELECT g.Id as gid, p.NomePermissao as ppermissao, g.RowVersion as growversion, g.Titulo as gtitulo FROM Grupo as g INNER JOIN Permissao as p ON g.PermissaoItemId = p.Id WHERE g.RowVersion > '"+ dbInstance.gruposDAO().GetLastRowVersion() != null ? String.valueOf(dbInstance.gruposDAO().GetLastRowVersion()) : 0 +"'");

                        while(r_Grupos.next()){
                            Grupos grupos = dbInstance.gruposDAO().GetByIdOriginal(r_Grupos.getInt("Id"));

                            if (grupos == null) {
                                grupos = new Grupos();
                                grupos.setIdOriginal(r_Grupos.getInt("gid"));
                                grupos.setPermissao(r_Grupos.getString("ppermissao"));
                                grupos.setRowVersion(r_Grupos.getLong("growversion"));
                                grupos.setTitulo(r_Grupos.getString("gtitulo"));

                                dbInstance.gruposDAO().Create(grupos);
                            }
                            else
                            {
                                grupos.setIdOriginal(r_Grupos.getInt("gid"));
                                grupos.setPermissao(r_Grupos.getString("ppermissao"));
                                grupos.setRowVersion(r_Grupos.getLong("growversion"));
                                grupos.setTitulo(r_Grupos.getString("gtitulo"));

                                dbInstance.gruposDAO().Update(grupos);
                            }
                        }
                        //endregion

                    } catch (SQLException e){

                    }


                }
            }).start();*/

            //UPMOB SYNC

            //region UPMOBHISTORICOLOCALIZACAO
            //***HISTORICO DE LOCALIZAÇÃO***
            try {
                c =db.rawQuery("SELECT * FROM UPMOBHistoricoLocalizacao",null);
                while (c.moveToNext()){

                    PreparedStatement stmt_UPMOBHistoricoLocalizacaos = DbConn.prepareStatement("INSERT UPMOBHistoricoLocalizacao ( TAGID, TAGIDPosicao, DataHoraEvento, Processo, Dominio, Quantidade, Modalidade, DescricaoErro, FlagErro, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?)");

                    stmt_UPMOBHistoricoLocalizacaos.setString(1,c.getString(c.getColumnIndex("TAGID")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(2,c.getString(c.getColumnIndex("TAGIDPosicao")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(3,c.getString(c.getColumnIndex("DataHoraEvento")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(4,c.getString(c.getColumnIndex("Processo")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(5,c.getString(c.getColumnIndex("Dominio")));
                    stmt_UPMOBHistoricoLocalizacaos.setInt(6,c.getInt(c.getColumnIndex("Quantidade")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(7,c.getString(c.getColumnIndex("Modalidade")));
                    stmt_UPMOBHistoricoLocalizacaos.setString(8,c.getString(c.getColumnIndex("DescricaoErro")));
                    stmt_UPMOBHistoricoLocalizacaos.setBoolean(9,c.getInt(c.getColumnIndex("FlagErro")) != 0 );
                    stmt_UPMOBHistoricoLocalizacaos.setBoolean(10,c.getInt(c.getColumnIndex("FlagProcess")) != 0);
                    stmt_UPMOBHistoricoLocalizacaos.executeUpdate();

                    //c.getString(c.getColumnIndex("TAGID_Equipment"));
                    db.execSQL("DELETE FROM UPMOBHistoricoLocalizacao WHERE TAGID ='"+ c.getString(c.getColumnIndex("TAGID"))+"'");
                    updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                }
            }
            catch (final Exception e)
            {
                pd.dismiss();
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Your UI updates here
                        showMessage("AVISO! TABELA UPMOBHistoricoLocalizacaos", "Erro 020" + e.toString(), 2);
                    }
                });
            }
            //endregion

            //region UPMOBWORKSHEET
            //***UPMOBWORKSHEET***
            c = db.rawQuery("SELECT * FROM UPMOBWorksheet",null);
            while (c.moveToNext()) {
                PreparedStatement stmt_UPMOBWorksheet = DbConn.prepareStatement("INSERT UPMOBCadastro ( IdOriginal, TraceNumber, NumProduto, Patrimonio, NumSerie, Quantidade, Modalidade, DataFabricacao, DataValidade, DataHoraEvento, DadosTecnicos, TAGID, TAGIDPosicao, Categoria, NF, DataEntradaNF, ValorUnitario, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UPMOBWorksheet.setInt(1,c.getInt(c.getColumnIndex("IdOriginal")));
                stmt_UPMOBWorksheet.setString(2,c.getString(c.getColumnIndex("TraceNumber")));
                stmt_UPMOBWorksheet.setString(3,c.getString(c.getColumnIndex("NumProduto")));
                stmt_UPMOBWorksheet.setString(4,c.getString(c.getColumnIndex("Patrimonio")));
                stmt_UPMOBWorksheet.setString(5,c.getString(c.getColumnIndex("NumSerie")));
                stmt_UPMOBWorksheet.setInt(6,c.getInt(c.getColumnIndex("Quantidade")));
                stmt_UPMOBWorksheet.setString(7,c.getString(c.getColumnIndex("Modalidade")));
                stmt_UPMOBWorksheet.setString(8,c.getString(c.getColumnIndex("DataFabricacao")));
                stmt_UPMOBWorksheet.setString(9,c.getString(c.getColumnIndex("DataValidade")));
                stmt_UPMOBWorksheet.setString(10,c.getString(c.getColumnIndex("DataHoraEvento")));
                stmt_UPMOBWorksheet.setString(11,c.getString(c.getColumnIndex("DadosTecnicos")));
                stmt_UPMOBWorksheet.setString(12,c.getString(c.getColumnIndex("TAGID")));
                stmt_UPMOBWorksheet.setString(13,c.getString(c.getColumnIndex("TAGIDPosicao")));
                stmt_UPMOBWorksheet.setString(14,c.getString(c.getColumnIndex("Categoria")));
                stmt_UPMOBWorksheet.setString(15,c.getString(c.getColumnIndex("NF")));
                stmt_UPMOBWorksheet.setString(16,c.getString(c.getColumnIndex("DataEntradaNF")));
                stmt_UPMOBWorksheet.setString(17,c.getString(c.getColumnIndex("ValorUnitario")));
                stmt_UPMOBWorksheet.setString(18,c.getString(c.getColumnIndex("DescricaoErro")));
                stmt_UPMOBWorksheet.setBoolean(19,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UPMOBWorksheet.setBoolean(20,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UPMOBWorksheet.setBoolean(21,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UPMOBWorksheet.executeUpdate();

                db.execSQL("DELETE FROM UPMOBWorksheet WHERE TAGID='"+c.getString(c.getColumnIndex("TAGID"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion

            //region UPMOBUSUARIOS
            //***UPMOBUsuariosSet
            c = db.rawQuery("SELECT * FROM UPMOBUsuariosSets", null);
            while (c.moveToNext()){
                PreparedStatement stmt_UsuariosSet = DbConn.prepareStatement("INSERT UPMOBUsuario ( IdOriginal, Permissao, Usuario, Email, NomeCompleto, TAGID, Grupo, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UsuariosSet.setString(1, c.getString(c.getColumnIndex("IdOriginal")));
                stmt_UsuariosSet.setString(2, c.getString(c.getColumnIndex("Permissao")));
                stmt_UsuariosSet.setString(3, c.getString(c.getColumnIndex("Usuario")));
                stmt_UsuariosSet.setString(4, c.getString(c.getColumnIndex("Email")));
                stmt_UsuariosSet.setString(5, c.getString(c.getColumnIndex("NomeCompleto")));
                stmt_UsuariosSet.setString(6, c.getString(c.getColumnIndex("TAGID")));
                stmt_UsuariosSet.setString(7, c.getString(c.getColumnIndex("Grupo")));
                stmt_UsuariosSet.setString(8,c.getString(c.getColumnIndex("DescricaoErro")));
                stmt_UsuariosSet.setBoolean(9,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UsuariosSet.setBoolean(10,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UsuariosSet.setBoolean(11,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UsuariosSet.executeUpdate();
                db.execSQL("DELETE FROM UPMOBUsuariosSets WHERE TAGID ='"+c.getString(c.getColumnIndex("TAGID"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion

            //region UPMOBWORKSHEETITENS
            //***UPMOBWorksheetItens
            c = db.rawQuery("SELECT * FROM UPMOBWorksheetItens",null);
            while (c.moveToNext()) {
                PreparedStatement stmt_UPMOBWorksheetItens = DbConn.prepareStatement("INSERT UPWEBCadastroMateriaisItens ( IdOriginal, Patrimonio, NumSerie, NumProduto, Quantidade, DataHoraEvento, DataValidade, DataFabricacao, TAGIDContentor, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UPMOBWorksheetItens.setInt(1,c.getInt(c.getColumnIndex("IdOriginal")));
                stmt_UPMOBWorksheetItens.setString(2,c.getString(c.getColumnIndex("Patrimonio")));
                stmt_UPMOBWorksheetItens.setString(3,c.getString(c.getColumnIndex("NumSerie")));
                stmt_UPMOBWorksheetItens.setString(4,c.getString(c.getColumnIndex("NumProduto")));
                stmt_UPMOBWorksheetItens.setInt(5,c.getInt(c.getColumnIndex("Quantidade")));
                stmt_UPMOBWorksheetItens.setString(6,c.getString(c.getColumnIndex("DataHoraEvento")));
                stmt_UPMOBWorksheetItens.setString(7,c.getString(c.getColumnIndex("DataValidade")));
                stmt_UPMOBWorksheetItens.setString(8,c.getString(c.getColumnIndex("DataFabricacao")));
                stmt_UPMOBWorksheetItens.setString(9,c.getString(c.getColumnIndex("TAGIDContentor")));
                stmt_UPMOBWorksheetItens.setString(10,c.getString(c.getColumnIndex("DescricaoErro")));
                stmt_UPMOBWorksheetItens.setBoolean(11,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UPMOBWorksheetItens.setBoolean(12,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UPMOBWorksheetItens.setBoolean(13,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UPMOBWorksheetItens.executeUpdate();
                db.execSQL("DELETE FROM UPMOBWorksheetItens WHERE DataHoraEvento='"+c.getString(c.getColumnIndex("DataHoraEvento"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion

            //region UPMOBLISTAMATERIAIS
            //***UPMOBListaMateriaisListaTarefaEqReadnesses***
            c = db.rawQuery("SELECT * FROM UPMOBListaMateriaisListaTarefaEqReadnesses",null);
            while (c.moveToNext()) {

                PreparedStatement stmt_UPMOBListaMateriaisListaTarefaEqReadnesses = DbConn.prepareStatement("INSERT UPMOBListaTarefas ( IdOriginal, CodTarefa, Status, TraceNumber, DataInicio, DataFimReal, DataHoraEvento, CodColetor, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setInt(1,c.getInt(c.getColumnIndex("IdOriginal")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(2,c.getString(c.getColumnIndex("CodTarefa")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(3,c.getString(c.getColumnIndex("Status")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(4,c.getString(c.getColumnIndex("TraceNumber")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setInt(1,c.getInt(c.getColumnIndex("DataInicio")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(2,c.getString(c.getColumnIndex("DataFimReal")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(3,c.getString(c.getColumnIndex("DataHoraEvento")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setString(4,c.getString(c.getColumnIndex("CodColetor")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setInt(1,c.getInt(c.getColumnIndex("DescricaoErro")));
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setBoolean(2,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setBoolean(3,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.setBoolean(4,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UPMOBListaMateriaisListaTarefaEqReadnesses.executeUpdate();

                db.execSQL("DELETE FROM UPMOBListaMateriaisListaTarefaEqReadnesses WHERE _Id='"+c.getString(c.getColumnIndex("_Id"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion

            //region UPMOBLISTASERVICOS
            //***UPMOBLista_Serviços_AdicionaisSet***
            c = db.rawQuery("SELECT * FROM UPMOBLista_Servicos_AdicionaisSet",null);
            while (c.moveToNext()) {

                PreparedStatement stmt_UPMOBLista_Serviços_AdicionaisSet = DbConn.prepareStatement("INSERT UPMOBListaServicosAdicionais ( IdOriginal, CodTarefa, Servico, Resultado, Quantidade, Status, DataHoraEvento, DataInicio, DataConclusao, DataCancelamento, ListaTarefas, CodColetor, DescricaoErro, FlagErro, FlagAtualizar, FlagPocess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UPMOBLista_Serviços_AdicionaisSet.setInt(1, c.getInt(c.getColumnIndex("IdOriginal")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(2,c.getString(c.getColumnIndex("CodTarefa")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(3,c.getString(c.getColumnIndex("Servico")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(4,c.getString(c.getColumnIndex("Resultado")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setInt(5,c.getInt(c.getColumnIndex("Quantidade")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(6,c.getString(c.getColumnIndex("Status")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(7,c.getString(c.getColumnIndex("DataHoraEvento")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(8,c.getString(c.getColumnIndex("DataInicio")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(9,c.getString(c.getColumnIndex("DataConclusao")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(10,c.getString(c.getColumnIndex("DataCancelamento")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setInt(11,c.getInt(c.getColumnIndex("ListaTarefas")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(12,c.getString(c.getColumnIndex("CodColetor")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setString(13,c.getString(c.getColumnIndex("DescricaoErro")));
                stmt_UPMOBLista_Serviços_AdicionaisSet.setBoolean(14,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UPMOBLista_Serviços_AdicionaisSet.setBoolean(15,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UPMOBLista_Serviços_AdicionaisSet.setBoolean(16,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UPMOBLista_Serviços_AdicionaisSet.executeUpdate();

                db.execSQL("DELETE FROM UPMOBLista_Servicos_AdicionaisSet WHERE _Id='"+c.getString(c.getColumnIndex("_Id"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion

            //region UPMOBLISTATAREFAS
            //***UPMOBListaTarefasEqReadinessTables***
            c = db.rawQuery("SELECT * FROM UPMOBListaTarefasEqReadinessTables",null);
            while (c.moveToNext()) {
                PreparedStatement stmt_UPMOBListaTarefasEqReadinessTables = DbConn.prepareStatement("INSERT UPMOBListaMateriais ( IdOriginal, Status, TAGID, Quantidade, Observacao, CodColetor, CodTarefa, DataHoraEvento, ListaTarefas, DescricaoErro, FlagErro, FlagAtualizar, FlagProcess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt_UPMOBListaTarefasEqReadinessTables.setInt(1, c.getInt(c.getColumnIndex("IdOriginal")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(2,c.getString(c.getColumnIndex("Status")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(3,c.getString(c.getColumnIndex("TAGID")));
                stmt_UPMOBListaTarefasEqReadinessTables.setInt(4,c.getInt(c.getColumnIndex("Quantidade")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(5,c.getString(c.getColumnIndex("Observacao")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(6,c.getString(c.getColumnIndex("CodColetor")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(7,c.getString(c.getColumnIndex("CodTarefa")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(8,c.getString(c.getColumnIndex("DataHoraEvento")));
                stmt_UPMOBListaTarefasEqReadinessTables.setInt(9,c.getInt(c.getColumnIndex("ListaTarefas")));
                stmt_UPMOBListaTarefasEqReadinessTables.setString(10,c.getString(c.getColumnIndex("DescricaoErro")));
                stmt_UPMOBListaTarefasEqReadinessTables.setBoolean(10,c.getInt(c.getColumnIndex("FlagErro")) != 0);
                stmt_UPMOBListaTarefasEqReadinessTables.setBoolean(10,c.getInt(c.getColumnIndex("FlagAtualizar")) != 0);
                stmt_UPMOBListaTarefasEqReadinessTables.setBoolean(10,c.getInt(c.getColumnIndex("FlagProcess")) != 0);

                stmt_UPMOBListaTarefasEqReadinessTables.executeUpdate();

                db.execSQL("DELETE FROM UPMOBListaTarefasEqReadinessTables WHERE _Id='"+c.getString(c.getColumnIndex("_Id"))+"'");
                updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion


            //UPWEB SYNC

            //region UPWEBPOSICAO

            //***INSERT UPWEBPosicao***
            Statement smtp_PosicaoTables = DbConn.createStatement();
            Statement smtp_PosicaoTables_update = DbConn.createStatement();
            ResultSet r_PosicaoTables = smtp_PosicaoTables.executeQuery("SELECT * FROM UPWEBPosicoes WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(r_PosicaoTables.next()){
                query_UPWEBPosicao = new Query_UPWEBPosicao(db);
                Cursor posicao1 = query_UPWEBPosicao.UPWEBPosicaoId_OrigQuery(r_PosicaoTables.getString("IdOriginal"));
                if (posicao1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBPosicao VALUES('"+ r_PosicaoTables.getInt("IdOriginal") +"', '"+ r_PosicaoTables.getString("Codigo") +"', '"+ r_PosicaoTables.getString("Descricao")  +"', '"+ r_PosicaoTables.getString("Almoxarifado") +"', '"+ r_PosicaoTables.getString("TAGID")+"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_PosicaoTables_update.executeUpdate("update UPWEBPosicoes set FlagMobileInsert = 0  WHERE Id="+ r_PosicaoTables.getInt("Id"));
                } else {
                    smtp_PosicaoTables_update.executeUpdate("update UPWEBPosicoes set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id="+ r_PosicaoTables.getInt("Id"));
                }
            }

            //***UPDATE UPWEBPosicao***
            Statement smtp_PosicaoTables2 = DbConn.createStatement();
            Statement smtp_PosicaoTables_update2 = DbConn.createStatement();
            ResultSet r_PosicaoTables2 = smtp_PosicaoTables2.executeQuery("SELECT * FROM UPWEBPosicoes WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(r_PosicaoTables2.next()){
                db.execSQL("UPDATE UPWEBPosicao SET IdOriginal = " + r_PosicaoTables2.getString("IdOriginal") +" , Codigo = '" + r_PosicaoTables2.getString("Codigo") +"' , Descricao = '"+ r_PosicaoTables2.getString("Descricao") +"' ,Almoxarifado = '"+ r_PosicaoTables2.getString("Almoxarifado") +"' ,TAGID = '" + r_PosicaoTables2.getString("TAGID") +"' WHERE IdOriginal="+ r_PosicaoTables2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_PosicaoTables_update2.executeUpdate("update UPWEBPosicoes set FlagMobileUpdate = 0  WHERE Id="+ r_PosicaoTables2.getInt("Id"));
            }

            //endregion

            //region UPWEBWORKSHEET

            //***INSERT UPWEBWORSHEET***
            Statement smtp_upwebworksheet = DbConn.createStatement();
            Statement smtp_upwebworksheet_update = DbConn.createStatement();
            ResultSet upwebworksheet1 = smtp_upwebworksheet.executeQuery("SELECT * FROM UPWEBCadastroMateriais WHERE FlagMobileInsert = 1 and CodColetor='" + device +"'");

            while(upwebworksheet1.next()){
                query_UPWEBWorksheets = new Query_UPWEBWorksheets(db);
                Cursor worksheet1 = query_UPWEBWorksheets.UPWEBWorksheetIdQuery(upwebworksheet1.getString("IdOriginal"));
                if (worksheet1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBWorksheet VALUES('" + upwebworksheet1.getInt("IdOriginal") +"','"+ upwebworksheet1.getString("NumSerie") +"','"+ upwebworksheet1.getString("Patrimonio") +"','"+ upwebworksheet1.getInt("Quantidade") +"','"+ upwebworksheet1.getString("DataCadastro") +"','"+ upwebworksheet1.getString("DataFabricacao") +"','"+ upwebworksheet1.getString("DataValidade") +"','"+ upwebworksheet1.getString("ValorUnitario") +"','"+ upwebworksheet1.getString("DadosTecnicos") +"','"+ upwebworksheet1.getString("NotaFiscal") +"','"+ upwebworksheet1.getString("DataEntradaNotaFiscal") +"','"+ upwebworksheet1.getString("Categoria") +"','"+ (upwebworksheet1.getInt("FlagContentor") != 0) +"','"+ upwebworksheet1.getInt("ModeloMateriaisItemIdOriginal") +"','"+ upwebworksheet1.getInt("SetorProprietarioItemIdOriginal") +"','"+ upwebworksheet1.getInt("PosicaoOriginalItemIdoriginal") +"','"+ upwebworksheet1.getString("TAGID") + "');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_upwebworksheet_update.executeUpdate("update UPWEBCadastroMateriais set FlagMobileInsert = 0 WHERE Id=" + upwebworksheet1.getInt("Id"));
                } else {
                    smtp_upwebworksheet_update.executeUpdate("update UPWEBCadastroMateriais set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id=" + upwebworksheet1.getInt("Id"));
                }
            }

            //***UPDATE UPWEBWORSHEET***
            Statement smtp_upwebworksheet2 = DbConn.createStatement();
            Statement smtp_upwebworksheet2_update = DbConn.createStatement();
            ResultSet upwebworksheet2 = smtp_upwebworksheet2.executeQuery("SELECT * FROM UPWEBCadastroMateriais WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(upwebworksheet2.next()){
                db.execSQL("UPDATE UPWEBWorksheet SET IdOriginal = " + upwebworksheet2.getInt("IdOriginal") +" , NumSerie = '"+ upwebworksheet2.getString("NumSerie") +"' ,Patrimonio = '"+ upwebworksheet2.getString("Patrimonio") +"' ,Quantidade = '"+ upwebworksheet2.getInt("Quantidade") +"' ,DataCadastro = '"+ upwebworksheet2.getString("DataCadastro") +"' ,DataFabricacao = '" + upwebworksheet2.getString("DataFabricacao") +"' ,DataValidade = '" + upwebworksheet2.getString("DataValidade") +"' ,ValorUnitario = '" + upwebworksheet2.getString("ValorUnitario") +"' ,DadosTecnicos = '"  + upwebworksheet2.getString("DadosTecnicos") +"' ,NotaFiscal = '"  + upwebworksheet2.getString("NotaFiscal") +"' ,DataEntradaNotaFiscal = '"  + upwebworksheet2.getString("DataEntradaNotaFiscal") +"' ,Categoria = '"  + upwebworksheet2.getString("Categoria") +"' ,FlagContentor = '"  + (upwebworksheet2.getInt("FlagContentor") != 0)+"' ,ModeloMateriaisItemIdOriginal = '"  + upwebworksheet2.getInt("ModeloMateriaisItemIdOriginal")+"' ,SetorProprietarioItemIdOriginal = '"  + (upwebworksheet2.getInt("SetorProprietarioItemIdOriginal") != 0)+"' ,PosicaoOriginalItemIdoriginal = '"  + (upwebworksheet2.getInt("PosicaoOriginalItemIdoriginal") != 0)+"' ,TAGID = '"  + upwebworksheet2.getString("TAGID") + "' WHERE IdOriginal="+ upwebworksheet2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_upwebworksheet2_update.executeUpdate("update UPWEBCadastroMateriais set FlagMobileUpdate = 0  WHERE Id="+ upwebworksheet2.getInt("Id"));
            }

            //endregion

            //region UPWEBUSUARIOS

            //***INSERT UPWEBUsuariosSet***
            Statement smtp_UPWEBUsuariosSet1 = DbConn.createStatement();
            Statement smtp_UPWEBUsuariosSetSet1_update = DbConn.createStatement();
            ResultSet UPWEBUsuariosSetSet1 = smtp_UPWEBUsuariosSet1.executeQuery("SELECT * FROM UPWEBApplicationUsers WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(UPWEBUsuariosSetSet1.next()){
                query_UPWEBUsuariosSet = new Query_UPWEBUsuariosSet(db);
                Cursor usuarios1 = query_UPWEBUsuariosSet.UsuariosIdQuery(UPWEBUsuariosSetSet1.getString("IdOriginal"));
                if (usuarios1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBUsuariosSet VALUES('" + UPWEBUsuariosSetSet1.getString("IdOriginal") +"', '"+ UPWEBUsuariosSetSet1.getString("UserName") +"', '"+ UPWEBUsuariosSetSet1.getString("NomeCompleto") +"', '"+ UPWEBUsuariosSetSet1.getString("Email") +"', '"+ UPWEBUsuariosSetSet1.getString("Permissao") +"', '" + UPWEBUsuariosSetSet1.getString("TAGID")+"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBUsuariosSetSet1_update.executeUpdate("update UPWEBApplicationUsers set FlagMobileInsert = 0  WHERE Id="+ UPWEBUsuariosSetSet1.getInt("Id"));
                } else {
                    smtp_UPWEBUsuariosSetSet1_update.executeUpdate("update UPWEBApplicationUsers set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBUsuariosSetSet1.getInt("Id"));
                }
            }

            //***UPDATE UPWEBUsuariosSet***
            Statement smtp_UPWEBUsuariosSet2 = DbConn.createStatement();
            Statement smtp_UPWEBUsuariosSet2_update = DbConn.createStatement();
            ResultSet UPWEBUsuariosSet2 = smtp_UPWEBUsuariosSet2.executeQuery("SELECT * FROM UPWEBApplicationUsers WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(UPWEBUsuariosSet2.next()){
                db.execSQL("UPDATE UPWEBUsuariosSet SET IdOriginal = " + UPWEBUsuariosSet2.getString("IdOriginal") +" ,UserName = '"+ UPWEBUsuariosSet2.getString("UserName") +"' ,NomeCompleto = '"+ UPWEBUsuariosSet2.getString("NomeCompleto") +"' ,Email = '"+ UPWEBUsuariosSet2.getString("Email") +"' ,Permissao = '"+ UPWEBUsuariosSet2.getString("Permissao") +"' ,TAGID = '" + UPWEBUsuariosSet2.getString("TAGID") +"' ,Cod_Coletor = '" + UPWEBUsuariosSet2.getString("Cod_Coletor") +"' WHERE IdOriginal="+ UPWEBUsuariosSet2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBUsuariosSet2_update.executeUpdate("update UPWEBApplicationUsers set FlagMobileUpdate = 0  WHERE Id="+ UPWEBUsuariosSet2.getInt("Id"));
            }

            //endregion

            //region UPWEBEQUIPMENTTYPE

            //***INSERT UPWEBEquipment_Type***
            Statement smtp_EquipmentTypeTables = DbConn.createStatement();
            Statement smtp_EquipmentTypeTables_update = DbConn.createStatement();
            ResultSet r_EquipmentTypeTables = smtp_EquipmentTypeTables.executeQuery("SELECT * FROM UPWEBModeloEquipamentos WHERE FlagMobileInsert = 1  and CodColetor='"+ device+"'");

            while(r_EquipmentTypeTables.next()){
                query_UPWEBEquipment_Type = new Query_UPWEBEquipment_Type(db);
                Cursor equipment_type1 = query_UPWEBEquipment_Type.UPWEBEquipment_TypeIdQuery(r_EquipmentTypeTables.getString("IdOriginal"));
                if (equipment_type1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBEquipment_Type VALUES('"+ r_EquipmentTypeTables.getString("IdOriginal") +"', '"+ r_EquipmentTypeTables.getString("Modelo") +"', '"+ r_EquipmentTypeTables.getString("DescricaoTecnica")  + "', '" + r_EquipmentTypeTables.getString("PartNumber") + "', '" + r_EquipmentTypeTables.getString("PartNumber") + "', '" + r_EquipmentTypeTables.getString("PartNumber") + "');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_EquipmentTypeTables_update.executeUpdate("update UPWEBModeloEquipamentos set FlagMobileInsert = 0 WHERE Id=" + r_EquipmentTypeTables.getInt("Id"));
                } else {
                    smtp_EquipmentTypeTables_update.executeUpdate("update UPWEBModeloEquipamentos set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id=" + r_EquipmentTypeTables.getInt("Id"));
                }

            }

            //***UPDATE UPWEBEquipment_Type***
            Statement smtp_EquipmentTypeTables2 = DbConn.createStatement();
            Statement smtp_EquipmentTypeTables_update2 = DbConn.createStatement();
            ResultSet r_EquipmentTypeTables2 = smtp_EquipmentTypeTables2.executeQuery("SELECT * FROM UPWEBModeloEquipamentos WHERE FlagMobileUpdate = 1  and CodColetor='"+ device+"'");

            while(r_EquipmentTypeTables2.next()){
                db.execSQL("UPDATE UPWEBEquipment_Type SET IdOriginal = " + r_EquipmentTypeTables2.getString("IdOriginal") +" , Modelo = '"+ r_EquipmentTypeTables2.getString("Modelo") +"' ,DescricaoTecnica = '"+ r_EquipmentTypeTables2.getString("DescricaoTecnica") +"' ,PartNumber = '"+ r_EquipmentTypeTables2.getString("PartNumber") +"' ,Fabricante = '"+ r_EquipmentTypeTables2.getString("Fabricante") +"' ,Categoria = '"+ r_EquipmentTypeTables2.getString("Categoria") +"' WHERE IdOriginal="+ r_EquipmentTypeTables2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_EquipmentTypeTables_update2.executeUpdate("update UPWEBModeloEquipamentos set FlagMobileUpdate = 0  WHERE Id="+ r_EquipmentTypeTables2.getInt("Id"));
            }

            //endregion

            //region UPWEBGRUPOS

            //INSERT UPWEBGrupos
            Statement smtp_UPWEBGruposSet1 = DbConn.createStatement();
            Statement smtp_UPWEBGruposSet1_update = DbConn.createStatement();
            ResultSet UPWEBGruposSet1 = smtp_UPWEBGruposSet1.executeQuery("SELECT * FROM UPWEBGrupos WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(UPWEBGruposSet1.next()){
                query_UPWEBGrupos = new Query_UPWEBGrupos(db);
                Cursor grupos1 = query_UPWEBGrupos.GruposIdQuery(UPWEBGruposSet1.getString("IdOriginal"));
                if (grupos1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBGrupos VALUES('" + UPWEBGruposSet1.getInt("IdOriginal") +"', '"+ UPWEBGruposSet1.getString("Titulo").toString() +"', '" + UPWEBGruposSet1.getString("Permissao") + "');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBGruposSet1_update.executeUpdate("update UPWEBGrupos set FlagMobileInsert = 0  WHERE Id="+ UPWEBGruposSet1.getInt("Id"));
                } else {
                    smtp_UPWEBGruposSet1_update.executeUpdate("update UPWEBGrupos set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBGruposSet1.getInt("Id"));
                }
            }

            //UPDATE UPWEBGrupos
            Statement smtp_UPWEBGruposSet2 = DbConn.createStatement();
            Statement smtp_UPWEBGruposSet2_update = DbConn.createStatement();
            ResultSet UPWEBGruposSet2 = smtp_UPWEBGruposSet2.executeQuery("SELECT * FROM UPWEBGrupos WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(UPWEBGruposSet2.next()){
                db.execSQL("UPDATE UPWEBGrupos SET IdOriginal = " + UPWEBGruposSet2.getInt("IdOriginal") +" ,Titulo = '"+ UPWEBGruposSet2.getString("Titulo").toString() + "' ,Permissao = '" + UPWEBGruposSet2.getString("Permissao") +"' WHERE IdOriginal="+ UPWEBGruposSet2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBGruposSet2_update.executeUpdate("update UPWEBGrupos set FlagMobileUpdate = 0  WHERE Id="+ UPWEBGruposSet2.getInt("Id"));
            }

            //endregion

            //region UPWEBTAREFAS

            //***INSERT UPWEBTarefasEqReadinessTable***
            Statement smtp_UPWEBTarefasEqReadinessTable1 = DbConn.createStatement();
            Statement smtp_UPWEBTarefasEqReadinessTable1_update = DbConn.createStatement();
            ResultSet UPWEBTarefasEqReadinessTable1 = smtp_UPWEBTarefasEqReadinessTable1.executeQuery("SELECT * FROM UPWEBTarefas WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(UPWEBTarefasEqReadinessTable1.next()){
                query_UPWEBTarefasEqReadinessTable = new Query_UPWEBTarefasEqReadinessTable(db);
                Cursor tarefas1 = query_UPWEBTarefasEqReadinessTable.UPWEBTarefasIdQuery(UPWEBTarefasEqReadinessTable1.getString("IdOriginal"));
                if (tarefas1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBTarefasEqReadinessTable VALUES('" + UPWEBTarefasEqReadinessTable1.getInt("IdOriginal") +"', '"+ UPWEBTarefasEqReadinessTable1.getString("Codigo") +"', '"+ UPWEBTarefasEqReadinessTable1.getString("Titulo") +"', '"+ UPWEBTarefasEqReadinessTable1.getString("Tipo") +"', '" + UPWEBTarefasEqReadinessTable1.getString("Descricao") +"', '" + (UPWEBTarefasEqReadinessTable1.getInt("FlagDependenciaServico") != 0) +"', '"  + (UPWEBTarefasEqReadinessTable1.getInt("FlagDependenciaMaterial") != 0) +"', '"  + UPWEBTarefasEqReadinessTable1.getInt("GrupoItemIdOriginal") +"', '" + UPWEBTarefasEqReadinessTable1.getString("CategoriaEquipamentos") +"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBTarefasEqReadinessTable1_update.executeUpdate("update UPWEBTarefas set FlagMobileInsert = 0  WHERE Id="+ UPWEBTarefasEqReadinessTable1.getInt("Id"));
                } else {
                    smtp_UPWEBTarefasEqReadinessTable1_update.executeUpdate("update UPWEBTarefas set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBTarefasEqReadinessTable1.getInt("Id"));
                }
            }

            //***UPDATE UPWEBTarefasEqReadinessTable***
            Statement smtp_UPWEBTarefasEqReadinessTable2 = DbConn.createStatement();
            Statement smtp_UPWEBTarefasEqReadinessTable2_update = DbConn.createStatement();
            ResultSet UPWEBTarefasEqReadinessTable2 = smtp_UPWEBTarefasEqReadinessTable2.executeQuery("SELECT * FROM UPWEBTarefas WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(UPWEBTarefasEqReadinessTable2.next()){
                db.execSQL("UPDATE UPWEBTarefasEqReadinessTable SET IdOriginal = " + UPWEBTarefasEqReadinessTable2.getInt("IdOriginal") +" , Codigo = '"+ UPWEBTarefasEqReadinessTable2.getString("Codigo") +"' ,Titulo = '"+ UPWEBTarefasEqReadinessTable2.getString("Titulo") +"' ,Tipo = '"+ UPWEBTarefasEqReadinessTable2.getString("Tipo") +"' ,Descricao = " + UPWEBTarefasEqReadinessTable2.getString("Descricao") +" ,FlagDependenciaServico = '" + (UPWEBTarefasEqReadinessTable2.getInt("FlagDependenciaServico") != 0) +"' ,FlagDependenciaMaterial = '"  + (UPWEBTarefasEqReadinessTable2.getInt("FlagDependenciaMaterial") != 0) +"' ,GrupoItemIdOriginal = '"  + UPWEBTarefasEqReadinessTable2.getInt("GrupoItemIdOriginal") +"' ,CategoriaEquipamentos = " + UPWEBTarefasEqReadinessTable2.getString("CategoriaEquipamentos") +" WHERE IdOriginal="+ UPWEBTarefasEqReadinessTable2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBTarefasEqReadinessTable2_update.executeUpdate("update UPWEBTarefas set FlagMobileUpdate = 0  WHERE Id="+ UPWEBTarefasEqReadinessTable2.getInt("Id"));
            }

            //endregion

            //region UPWEBMATERIALTYPE

            //***INSERT UPWEBMaterial_Type***
            Statement smtp_Modelo_MateriaisSet = DbConn.createStatement();
            Statement smtp_Modelo_MateriaisSet_update = DbConn.createStatement();
            ResultSet r_Modelo_MateriaisSet = smtp_Modelo_MateriaisSet.executeQuery("SELECT * FROM UPWEBModeloMateriais WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(r_Modelo_MateriaisSet.next()){
                query_UPWEBMaterial_Type = new Query_UPWEBMaterial_Type(db);
                Cursor material1 = query_UPWEBMaterial_Type.UPWEBMaterialIdQuery(r_Modelo_MateriaisSet.getString("IdOriginal"));
                if (material1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBMaterial_Type VALUES('"+ r_Modelo_MateriaisSet.getString("IdOriginal")+"', '" +  r_Modelo_MateriaisSet.getString("Modelo") +"', '"+ r_Modelo_MateriaisSet.getString("IDOmni") +"', '"+ r_Modelo_MateriaisSet.getString("PartNumber") +"', '"+ r_Modelo_MateriaisSet.getString("DescricaoTecnica")+"', '"+ r_Modelo_MateriaisSet.getString("ValorUnitario")+"', '"+ r_Modelo_MateriaisSet.getString("Fabricante")+"', '" + r_Modelo_MateriaisSet.getString("Familia") +"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_Modelo_MateriaisSet_update.executeUpdate("update UPWEBModeloMateriais set FlagMobileInsert = 0 WHERE Id = '"+ r_Modelo_MateriaisSet.getInt("Id")+"'");
                } else {
                    smtp_Modelo_MateriaisSet_update.executeUpdate("update UPWEBModeloMateriais set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id = '"+ r_Modelo_MateriaisSet.getInt("Id")+"'");
                }

            }

            //***UPDATE UPWEBMaterial_Type***
            Statement smtp_Modelo_MateriaisSet2 = DbConn.createStatement();
            Statement smtp_Modelo_MateriaisSet_update2 = DbConn.createStatement();
            ResultSet r_Modelo_MateriaisSet2 = smtp_Modelo_MateriaisSet2.executeQuery("SELECT * FROM UPWEBModeloMateriais WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(r_Modelo_MateriaisSet2.next()){
                db.execSQL("UPDATE UPWEBMaterial_Type SET IdOriginal = " + r_Modelo_MateriaisSet2.getString("IdOriginal") +" , Modelo = '" +  r_Modelo_MateriaisSet2.getString("Modelo") +"' , IDOmni = '"+  r_Modelo_MateriaisSet2.getString("IDOmni") +"' , PartNumber = '"+ r_Modelo_MateriaisSet2.getString("PartNumber") +"' , DescricaoTecnica = '" + r_Modelo_MateriaisSet2.getString("DescricaoTecnica") +"' , ValorUnitario = '" + r_Modelo_MateriaisSet2.getString("ValorUnitario") +"' , Fabricante = '" + r_Modelo_MateriaisSet2.getString("Fabricante") +"' , Familia = '"  + r_Modelo_MateriaisSet2.getString("Familia") +"' WHERE IdOriginal="+ r_Modelo_MateriaisSet2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_Modelo_MateriaisSet_update2.executeUpdate("update UPWEBModeloMateriais set FlagMobileUpdate = 0  WHERE Id="+ r_Modelo_MateriaisSet2.getInt("Id"));
            }

            //endregion

            //region UPWEBSERVICOSADICIONAIS

            //***INSERT UPWEBServicos_AdicionaisSet***
            Statement smtp_UPWEBServicos_AdicionaisSet1 = DbConn.createStatement();
            Statement smtp_UPWEBServicos_AdicionaisSet1_update = DbConn.createStatement();
            ResultSet UPWEBServicos_AdicionaisSet1 = smtp_UPWEBServicos_AdicionaisSet1.executeQuery("SELECT * FROM UPWEBServicoAdicionais WHERE FlagMobileInsert = 1  and CodColetor='" + device +"'");

            while(UPWEBServicos_AdicionaisSet1.next()){
                query_UPWEBServicos_AdicionaisSet = new Query_UPWEBServicos_AdicionaisSet(db);
                Cursor servicos1 = query_UPWEBServicos_AdicionaisSet.ServicosIdQuery(UPWEBServicos_AdicionaisSet1.getString("IdOriginal"));
                if (servicos1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBServicos_AdicionaisSet VALUES('" + UPWEBServicos_AdicionaisSet1.getInt("IdOriginal") +"', '"+ UPWEBServicos_AdicionaisSet1.getString("Servico") +"', '"+ UPWEBServicos_AdicionaisSet1.getString("Descricao") +"', '" + UPWEBServicos_AdicionaisSet1.getString("Modalidade") +"', '" + (UPWEBServicos_AdicionaisSet1.getInt("FlagObrigatorio") != 0) +"', '"  + (UPWEBServicos_AdicionaisSet1.getInt("FlagAtivo") != 0) +"', '"  + UPWEBServicos_AdicionaisSet1.getInt("TarefaItemIdOriginal") +"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBServicos_AdicionaisSet1_update.executeUpdate("update UPWEBServicoAdicionais set FlagMobileInsert = 0  WHERE Id="+ UPWEBServicos_AdicionaisSet1.getInt("Id"));
                } else {
                    smtp_UPWEBServicos_AdicionaisSet1_update.executeUpdate("update UPWEBServicoAdicionais set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBServicos_AdicionaisSet1.getInt("Id"));
                }
            }

            //***UPDATE UPWEBServicos_AdicionaisSet***
            Statement smtp_UPWEBServicos_AdicionaisSet2 = DbConn.createStatement();
            Statement smtp_UPWEBServicos_AdicionaisSet2_update = DbConn.createStatement();
            ResultSet UPWEBServicos_AdicionaisSet2 = smtp_UPWEBServicos_AdicionaisSet2.executeQuery("SELECT * FROM UPWEBServicoAdicionais WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(UPWEBServicos_AdicionaisSet2.next()){
                db.execSQL("UPDATE UPWEBServicos_AdicionaisSet SET Id_Original = " + UPWEBServicos_AdicionaisSet2.getInt("Id_Original") +" , Servico = '"+ UPWEBServicos_AdicionaisSet2.getString("Servico") +"' ,Descricao = '"+ UPWEBServicos_AdicionaisSet2.getString("Descricao") +"' ,Modalidade = '" + UPWEBServicos_AdicionaisSet2.getString("Modalidade") +"' ,FlagObrigatorio = '" + (UPWEBServicos_AdicionaisSet2.getInt("FlagObrigatorio") != 0) +"' ,FlagAtivo = '" + (UPWEBServicos_AdicionaisSet2.getInt("FlagAtivo") != 0) +"' ,TarefaItemIdOriginal = '"  + UPWEBServicos_AdicionaisSet2.getInt("TarefaItemIdOriginal") +"' WHERE IdOriginal="+ UPWEBServicos_AdicionaisSet2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBServicos_AdicionaisSet2_update.executeUpdate("update UPWEBServicoAdicionais set FlagMobileUpdate = 0  WHERE Id="+ UPWEBServicos_AdicionaisSet2.getInt("Id"));
            }

            //endregion

            //region UPWEBCADASTROEQUIPAMENTOS

            //***INSERT UPWEBInventario_Equipamento***
            /*Statement smtp_UPWEBInventario_Equipamentoes = DbConn.createStatement();
            Statement smtp_UPWEBInventario_Equipamentoes_update = DbConn.createStatement();
            ResultSet r_InventarioTables = smtp_UPWEBInventario_Equipamentoes.executeQuery("SELECT * FROM UPWEBCadastroEquipmentos WHERE FlagMobileInsert = 1 and CodColetor='" + device +"'");

            while(r_InventarioTables.next()){
                query_upwebCadastrosEquipmentTable = new Query_UPWEBCadastrosEquipmentTable(db);
                //Cursor inventario1 = query_upwebCadastrosEquipmentTable.CadastroEquipamentoIdQuery(r_InventarioTables.getString("IdOriginal"));
                Cursor inventario1 = query_upwebCadastrosEquipmentTable.CadastroEquipamentoIdQuery(r_InventarioTables.getString("IdOriginal"));
                if (inventario1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBCadastrosEquipmentTable VALUES('"+ r_InventarioTables.getInt("IdOriginal") + "', '" +  r_InventarioTables.getString("TraceNumber") + "', '" + r_InventarioTables.getString("DataCadastro") + "', '" +  r_InventarioTables.getString("DataFabricacao") + "', '" +  r_InventarioTables.getString("Status") + "', '" + r_InventarioTables.getInt("ModeloEquipamentosItemIdOriginal") + "', '" + r_InventarioTables.getString("Fabricante") + "', '" + r_InventarioTables.getString("Localizacao") + "', '" + r_InventarioTables.getString("TAGID")+ "');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBInventario_Equipamentoes_update.executeUpdate("update UPWEBCadastroEquipmentos " + "set FlagMobileInsert = 0" + " WHERE Id="+ r_InventarioTables.getString("Id"));
                } else {
                    smtp_UPWEBInventario_Equipamentoes_update.executeUpdate("update UPWEBCadastroEquipmentos set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id="+ r_InventarioTables.getString("Id"));
                }
            }*/

            //***UPDATE UPWEBInventario_Equipamento***
            Statement smtp_UPWEBInventario_Equipamentoes2 = DbConn.createStatement();
            Statement smtp_UPWEBInventario_Equipamentoes_update2 = DbConn.createStatement();
            ResultSet r_InventarioTables2 = smtp_UPWEBInventario_Equipamentoes2.executeQuery("SELECT * FROM UPWEBCadastroEquipmentos WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(r_InventarioTables2.next()){
                db.execSQL("UPDATE InventarioEquipamento SET IdOriginal = " + r_InventarioTables2.getString("IdOriginal") +" , TraceNumber = '"+  r_InventarioTables2.getString("TraceNumber") +"' ,DataCadastro = '"+ r_InventarioTables2.getString("DataCadastro") +"' ,DataFabricacao = '" + r_InventarioTables2.getString("DataFabricacao") +"' ,Status = '" + r_InventarioTables2.getString("Status") +"' ,ModeloEquipamentoItemIdOriginal = '" + r_InventarioTables2.getInt("ModeloEquipamentoItemIdOriginal") +"' ,Fabricante = '"  + r_InventarioTables2.getString("Fabricante") +"' ,Localizacao = '"  + r_InventarioTables2.getString("Localizacao") +"' ,TAGID = '"  + r_InventarioTables2.getString("TAGID") + "' WHERE IdOriginal="+ r_InventarioTables2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBInventario_Equipamentoes_update2.executeUpdate("update UPWEBCadastroEquipmentos set FlagMobileUpdate = 0  WHERE Id="+ r_InventarioTables2.getInt("Id"));
            }

            //endregion

            //region UPWEBWORKSHEETITENS

            //***INSERT UPWEBWorksheetItens***
            Statement smtp_WorksheetItensTables = DbConn.createStatement();
            Statement smtp_WorksheetItensTables_update = DbConn.createStatement();
            ResultSet r_WorksheetItens = smtp_WorksheetItensTables.executeQuery("SELECT * FROM UPWEBCadastroMateriaisItens WHERE FlagMobileInsert = 1 and CodColetor='" + device + "'");

            while (r_WorksheetItens.next()){
                query_upwebWorksheetItens = new Query_UPWEBWorksheetItens(db);
                Cursor worksheet_itens1 = query_upwebWorksheetItens.UPWEBWorksheetItensIdQuery(r_WorksheetItens.getString("IdOriginal"));
                if (worksheet_itens1.getCount() == 0){
                    db.execSQL("INSERT INTO UPWEBWorksheetItens VALUES('" + r_WorksheetItens.getInt("IdOriginal") +"' , '"+ r_WorksheetItens.getString("NumSerie") +"' , '" + r_WorksheetItens.getString("Patrimonio") +"' , '" + r_WorksheetItens.getInt("Quantidade") +"' , '" + r_WorksheetItens.getString("DataCadastro") +"' , '" + r_WorksheetItens.getString("DataFabricacao") + "' , '" + r_WorksheetItens.getString("DataValidade") + "' , '" + r_WorksheetItens.getInt("CadastroMateriaisItemIdOriginal") + "' , '" + r_WorksheetItens.getInt("ModeloMateriaisItemIdOriginal")+ "');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_WorksheetItensTables_update.executeUpdate("update UPWEBCadastroMateriaisItens set FlagMobileInsert = 0 WHERE Id=" + r_WorksheetItens.getInt("Id"));
                } else {
                    smtp_WorksheetItensTables_update.executeUpdate("update UPWEBCadastroMateriaisItens set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id =" + r_WorksheetItens.getInt("Id"));
                }
            }

            //***UPDATE UPWEBWorksheetItens***
            Statement smtp_WorksheetItensTables2 = DbConn.createStatement();
            Statement smtp_WorksheetItensTables_update2 = DbConn.createStatement();
            ResultSet r_WorksheetItens2 = smtp_WorksheetItensTables2.executeQuery("SELECT * FROM UPWEBCadastroMateriaisItens WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(r_WorksheetItens2.next()){
                db.execSQL("UPDATE UPWEBWorksheetItens SET IdOriginal = " + r_WorksheetItens2.getInt("IdOriginal") +" , NumSerie = '"+ r_WorksheetItens2.getString("NumSerie") +"' ,Patrimonio = '"+ r_WorksheetItens2.getString("Patrimonio") +"' ,Quantidade = '"+ r_WorksheetItens2.getInt("Quantidade") +"' ,DataCadastro = '"+ r_WorksheetItens2.getString("DataCadastro") +"' ,DataFabricacao = " + r_WorksheetItens2.getString("DataFabricacao") +" ,DataValidade = '" + r_WorksheetItens2.getString("DataValidade") +"' ,CadastroMateriaisItemIdOriginal = '" + r_WorksheetItens2.getInt("CadastroMateriaisItemIdOriginal") +"' ,ModeloMateriaisItemIdOriginal = '"  + r_WorksheetItens2.getInt("ModeloMateriaisItemIdOriginal")+ " WHERE IdOriginal="+ r_WorksheetItens2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_WorksheetItensTables_update2.executeUpdate("update UPWEBCadastroMateriaisItens set FlagMobileUpdate = 0  WHERE Id="+ r_WorksheetItens2.getInt("Id"));
            }

            //endregion

            //region UPWEBPROPRIETARIOS

            //***INSERT UPWEBProprietario***
            Statement smtp_SETOR_Proprietarios = DbConn.createStatement();
            Statement smtp_SETOR_Proprietarios_update = DbConn.createStatement();
            ResultSet r_Proprietarios = smtp_SETOR_Proprietarios.executeQuery("SELECT * FROM UPWEBSetorProprietarios WHERE FlagMobileInsert = 1 and CodColetor = '" + device +"'");

            while(r_Proprietarios.next()){
                query_UPWEBProprietario = new Query_UPWEBProprietario(db);
                Cursor proprietario1 = query_UPWEBProprietario.UPWEBSETOR_ProprietarioIdQuery(r_Proprietarios.getString("IdOriginal"));
                if (proprietario1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBProprietarios VALUES("+ r_Proprietarios.getInt("IdOriginal") +", '"+ r_Proprietarios.getString("Descricao") +"', '"+ r_Proprietarios.getString("ApplicationUserIdOriginal") +"', '"+ r_Proprietarios.getString("Empresa") +"');");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_SETOR_Proprietarios_update.executeUpdate("update UPWEBSetorProprietarios set FlagMobileInsert = 0 WHERE Id = "+ r_Proprietarios.getInt("Id"));
                } else {
                    smtp_SETOR_Proprietarios_update.executeUpdate("update UPWEBSetorProprietarios set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id = "+ r_Proprietarios.getInt("Id"));
                }

            }

            //***UPDATE UPWEBProprietario***
            Statement smtp_SETOR_Proprietarios2 = DbConn.createStatement();
            Statement smtp_SETOR_Proprietarios_update2 = DbConn.createStatement();
            ResultSet r_Proprietarios2 = smtp_SETOR_Proprietarios2.executeQuery("SELECT * FROM UPWEBSetorProprietarios WHERE FlagMobileUpdate = 1 and CodColetor = '" + device +"'");

            while(r_Proprietarios2.next()){
                db.execSQL("UPDATE UPWEBProprietarios SET IdOriginal = " + r_Proprietarios2.getInt("IdOriginal") +" , Descricao = '" + r_Proprietarios2.getString("Descricao") + "' , ApplicationUserIdOriginal = '" + r_Proprietarios2.getString("ApplicationUserIdOriginal") + "' , Empresa = '" + r_Proprietarios2.getString("Empresa") + "' WHERE IdOriginal="+ r_Proprietarios2.getInt("IdOriginal"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_SETOR_Proprietarios_update2.executeUpdate("update UPWEBSetorProprietarios set FlagMobileUpdate = 0  WHERE Id="+ r_Proprietarios2.getInt("Id"));
            }

            //endregion

            //region UPWEBLISTATAREFAS
            //***INSERT UPWEBListaTarefasEqReadinessTables***
            Statement smtp_ListaTarefasEqReadinessTables = DbConn.createStatement();
            Statement smtp_ListaTarefasEqReadinessTables_update = DbConn.createStatement();
            ResultSet ListaTarefasEqReadinessTables = smtp_ListaTarefasEqReadinessTables.executeQuery("SELECT * FROM UPWEBListaTarefas WHERE FlagMobileInsert = 1 and CodColetor='" + device +"' AND Status <> 'Concluída' And Status <> 'Cancelada'");
            Insert_Lista_Tarefa(db, DbConn, smtp_ListaTarefasEqReadinessTables_update, ListaTarefasEqReadinessTables, device);

            //***UPDATE UPWEBListaTarefasEqReadinessTables***
            Statement smtp_ListaTarefasEqReadinessTables2 = DbConn.createStatement();
            Statement smtp_ListaTarefasEqReadinessTables2_update = DbConn.createStatement();
            ResultSet ListaTarefasEqReadinessTables2 = smtp_ListaTarefasEqReadinessTables2.executeQuery("SELECT * FROM UPWEBListaTarefas WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(ListaTarefasEqReadinessTables2.next()){
                db.execSQL("UPDATE UPWEBListaTarefasEqReadinessTables SET IdOriginal = "+ ListaTarefasEqReadinessTables2.getString("IdOriginal") +" , "+ "Status = '" + ListaTarefasEqReadinessTables2.getString("Status") +"' , "+ "DataInicio = '" + ListaTarefasEqReadinessTables2.getString("DataInicio") +"' , "+ "DataFimPrevisao = '" + ListaTarefasEqReadinessTables2.getString("DataFimPrevisao") +"' , "+ "DataFimReal = '" + ListaTarefasEqReadinessTables2.getString("DataFimReal") +"' , "+ "DataCancelamento = '" + ListaTarefasEqReadinessTables2.getString("DataCancelamento") +"' , "+ "Dominio = '" + ListaTarefasEqReadinessTables2.getString("Dominio") +"' , "+ "Processo = '" + ListaTarefasEqReadinessTables2.getString("Processo") +"' , "+ "TarefaItemIdOriginal = '" + ListaTarefasEqReadinessTables2.getInt("TarefaItemIdOriginal") +"'");
                smtp_ListaTarefasEqReadinessTables2_update.executeUpdate("update UPWEBListaTarefas set FlagMobileUpdate = 0  WHERE Id="+ ListaTarefasEqReadinessTables2.getInt("Id"));
            }
            //endregion

            //region UPWEBLISTAMATERIAIS
            //***UPDATE UPWEBListaMateriaisListaTarefaEqReadnessesSet***
            Statement smtp_UPWEBListaMateriaisListaTarefa2 = DbConn.createStatement();
            Statement smtp_UPWEBListaMateriaisListaTarefa2_update = DbConn.createStatement();
            ResultSet UPWEBListaMateriaisListaTarefa2 = smtp_UPWEBListaMateriaisListaTarefa2.executeQuery("SELECT * FROM UPWEBListaMateriaisListaTarefas WHERE FlagMobileUpdate = 1 and CodColetor='" + device +"'");

            while(UPWEBListaMateriaisListaTarefa2.next()){
                db.execSQL("UPDATE UPWEBListaMateriaisListaTarefaEqReadnesses SET IdOriginal = "+ UPWEBListaMateriaisListaTarefa2.getString("IdOriginal") +" , Status = '" + UPWEBListaMateriaisListaTarefa2.getString("Status") +"' , DataInicio = '" + UPWEBListaMateriaisListaTarefa2.getString("DataInicio") +"' , DataConclusao = '"+ UPWEBListaMateriaisListaTarefa2.getString("DataConclusao") +"' ,Observacao = "+ UPWEBListaMateriaisListaTarefa2.getString("Observacao") +" ,ListaTarefasItemIdOriginal = '"+ UPWEBListaMateriaisListaTarefa2.getInt("ListaTarefasItemIdOriginal") +"' ,CadastroMateriaisItemIdOriginal = '"+ UPWEBListaMateriaisListaTarefa2.getInt("CadastroMateriaisItemIdOriginal") +"' WHERE IdOriginal="+ UPWEBListaMateriaisListaTarefa2.getInt("Id_Original"));
                smtp_UPWEBListaMateriaisListaTarefa2_update.executeUpdate("update UPWEBListaMateriaisListaTarefas set FlagMobileUpdate = 0  WHERE Id="+ UPWEBListaMateriaisListaTarefa2.getInt("Id"));
            }
            //endregion

            //region UPWEBLISTASERVICOS
            //***UPDATE UPWEBLista_Serviços_AdicionaisSet***
            Statement smtp_UPWEBLista_Serviços_AdicionaisSet2 = DbConn.createStatement();
            Statement smtp_UPWEBLista_Serviços_AdicionaisSet2_update = DbConn.createStatement();
            ResultSet UPWEBLista_Serviços_AdicionaisSet2 = smtp_UPWEBLista_Serviços_AdicionaisSet2.executeQuery("SELECT * FROM UPWEBListaServicosListaTarefas WHERE FlagMobileUpdate = 1  and CodColetor='" + device +"'");

            while(UPWEBLista_Serviços_AdicionaisSet2.next()){
                db.execSQL("UPDATE UPWEBLista_Serviços_AdicionaisSet SET IdOriginal = '" + UPWEBLista_Serviços_AdicionaisSet2.getInt("IdOriginal") +"' , Status = '"+ UPWEBLista_Serviços_AdicionaisSet2.getString("Status") +"' ,DataInicio = '"+ UPWEBLista_Serviços_AdicionaisSet2.getString("DataInicio") +"' ,DataConclusao = '"+ UPWEBLista_Serviços_AdicionaisSet2.getString("DataConclusao") +"' ,Resultado = '" + UPWEBLista_Serviços_AdicionaisSet2.getString("Resultado") +"' ,ListaTarefasItemIdOriginal = '" + UPWEBLista_Serviços_AdicionaisSet2.getInt("ListaTarefasItemIdOriginal") +"' ,ServicoAdicinalItemIdOriginal = '"  + UPWEBLista_Serviços_AdicionaisSet2.getInt("ServicoAdicinalItemIdOriginal") +"' WHERE IdOriginal= '"+ UPWEBLista_Serviços_AdicionaisSet2.getInt("IdOriginal")+"'");
                smtp_UPWEBLista_Serviços_AdicionaisSet2_update.executeUpdate("update UPWEBListaServicosListaTarefas set FlagMobileUpdate = 0  WHERE Id="+ UPWEBLista_Serviços_AdicionaisSet2.getInt("Id"));
            }
            //endregion


            //OUTRAS SYNC (PRECISA DE REVISÃO)

            //region PARAMETROSPADRAO

            //***PARAMETROS PADRAO***
            Statement smtp_Parametros_Padrao = DbConn.createStatement();
            ResultSet Parametros_Padrao = smtp_Parametros_Padrao.executeQuery("SELECT * FROM ParametrosPadroes as p INNER JOIN ColetoresDadosSet as c ON p.ColetorItemId = c.Id INNER JOIN AlmoxarifadoTables as a ON p.AlmoxarifadoItemId = a.Id WHERE c.Codigo = '" + device +"'");
            db.execSQL("DELETE from Parametros_Padrao");

            while(Parametros_Padrao.next()){
                db.execSQL("INSERT INTO Parametros_Padrao VALUES('"+ Parametros_Padrao.getString("Id")+"','" + Parametros_Padrao.getString("Codigo") +"', '"+ Parametros_Padrao.getString("Cod_Almoxarifado") +"','" + Parametros_Padrao.getString("SetorProprietarioItemId") +"');");
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }

            //endregion

            //region IMAGEM

            //***IMAGEM MODELO MATERIAL***
            String updateTableSQL = "UPDATE Modelo_MateriaisSet SET Imagem_Material = ?, FlagAtualizarcadastroMateriais = 1 WHERE Modelo_Material = ?";
            c = db.rawQuery("SELECT * FROM UPMOBImagemModelo WHERE FlagProcess = 1",null);
            while (c.moveToNext()) {
                PreparedStatement  smtp_UPWEBLista_UPMOBImagemModelo_update = DbConn.prepareStatement(updateTableSQL);
                String arquivo = c.getString(c.getColumnIndex("Imagem_Material"));
                File imagem = new File(arquivo);
                FileInputStream img = null;

                try {
                    img = new FileInputStream(imagem);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Convertendo a imagem para byte
                Bitmap bm = BitmapFactory.decodeStream(img);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byteArray = stream.toByteArray();

                smtp_UPWEBLista_UPMOBImagemModelo_update.setBytes(1, byteArray);
                smtp_UPWEBLista_UPMOBImagemModelo_update.setString(2, c.getString(c.getColumnIndex("Modelo_Material")));
                smtp_UPWEBLista_UPMOBImagemModelo_update .executeUpdate();

                db.execSQL("UPDATE UPMOBImagemModelo SET FlagProcess = 0 where Modelo_Material = '"+c.getString(c.getColumnIndex("Modelo_Material"))+"'");

            }

            //endregion


            //FORA DE USO
/*
            //region UPWEBCOLETORESDADOS
            //***UPWEBColetoresDadosSet ***
            Statement smtp_UPWEBColetoresDadosSet = DbConn.createStatement();
            Statement smtp_UPWEBColetoresDadosSet_update = DbConn.createStatement();
            ResultSet UPWEBColetoresDadosSet = smtp_UPWEBColetoresDadosSet.executeQuery("SELECT * FROM ColetoresDadosSet WHERE FlagMobile = 1 and CodColetor='" + device +"'");

            while(UPWEBColetoresDadosSet.next()){
                db.execSQL("DELETE from UPWEBColetoresDadosSet WHERE CodColetor='" + device +"'");
                db.execSQL("INSERT INTO UPWEBColetoresDadosSet VALUES('"+ UPWEBColetoresDadosSet.getString("CodColetor") +"', '"+ UPWEBColetoresDadosSet.getString("Cod_Posicao") +"');");
                smtp_UPWEBColetoresDadosSet_update.executeUpdate("update ColetoresDadosSet " + "set FlagMobile = 0" + " WHERE Id="+ UPWEBColetoresDadosSet.getString("Id"));
            }
            //endregion

            //region UPWEBACOESCOLETORES
            //***UPWEBAcoesColetores_Dados ***
            Statement smtp_UPWEBAcoesColetores_Dados = DbConn.createStatement();
            Statement smtp_UPWEBAcoesColetores_Dados_update = DbConn.createStatement();
            ResultSet UPWEBAcoesColetores_Dados = smtp_UPWEBAcoesColetores_Dados.executeQuery("SELECT * FROM AcoesColetores_DadosSet WHERE FlagProcess = 0 and CodColetor='" + device +"'");

            while(UPWEBAcoesColetores_Dados.next()){
                query_upwebAcoesColetores_Dados = new Query_UPWEBAcoesColetores_Dados(db);
                Cursor acao1 = query_upwebAcoesColetores_Dados.UPWEBAcoesColetores_Dados_Id(UPWEBAcoesColetores_Dados.getString("Id"));
                if (acao1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBAcoesColetores_Dados VALUES('"+ UPWEBAcoesColetores_Dados.getString("Id") + "', '"+ UPWEBAcoesColetores_Dados.getString("CodColetor") +"', '"+ UPWEBAcoesColetores_Dados.getString("Acao") +"', '"+ UPWEBAcoesColetores_Dados.getString("DataHoraEvento") +"', '"+ UPWEBAcoesColetores_Dados.getString("FlagProcess") +"');");
                }
                smtp_UPWEBAcoesColetores_Dados_update.executeUpdate("update AcoesColetores_DadosSet " + "set FlagProcess = 1" + " WHERE Id="+ UPWEBAcoesColetores_Dados.getString("Id"));
            }

            // Verificando Fila de Execução de Ações para o Dispositivo
            ExecutarUPWEBAcoesColetores_Dados(db, DbConn, device);
            //endregion

            //region UPWEBINVENTARIOMATERIAIS
            //***INSERT UPWEBInventarioMateriais***
            Statement smtp_UPWEBInventarioMateriaisTables = DbConn.createStatement();
            Statement smtp_UPWEBInventarioMateriaisTables_update = DbConn.createStatement();
            ResultSet r_InventarioMateriaisTables = smtp_UPWEBInventarioMateriaisTables.executeQuery("SELECT * FROM UPWEBInventario_Materials WHERE FlagMobileInsert = 1 and Cod_Coletor = '" + device +"'");

            while(r_InventarioMateriaisTables.next()){
                query_InventarioMaterial = new Query_InventarioMaterial(db);
                Cursor inventario1 = query_InventarioMaterial.InventarioMaterialIdQuery(r_InventarioMateriaisTables.getString("Id_Original"));
                if (inventario1.getCount() == 0) {
                    db.execSQL("INSERT INTO InventarioMaterial VALUES('" + r_InventarioMateriaisTables.getString("Id_Original")+"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Material_Type")) +"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("lote_material")) +"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Categoria")) +"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Modalidade")) +"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Proprietario_SETOR"))+"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Desc_Local"))+"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Nome_Almoxarifado"))+"', '"+ r_InventarioMateriaisTables.getString("Quantidade")+"', '"+  Replace_AS(r_InventarioMateriaisTables.getString("Desc_Posicao"))+"', '"+ r_InventarioMateriaisTables.getString("DataHoraLocalizacao")+"', '"+ r_InventarioMateriaisTables.getString("Condition") +"', '"+ r_InventarioMateriaisTables.getString("Processo") +"', '"+ r_InventarioMateriaisTables.getString("Dominio_Atualizacao")+"', '"+ r_InventarioMateriaisTables.getString("NF_Empresa")+"', '"+ r_InventarioMateriaisTables.getString("num_NF")+"', '"+ r_InventarioMateriaisTables.getString("TAGID_Material")+"', '"+ r_InventarioMateriaisTables.getString("TAGID_Posicao")+"', '"+ r_InventarioMateriaisTables.getString("Cod_Posicao") +"', '"+ r_InventarioMateriaisTables.getString("ID_Omni")+ "', '" + r_InventarioMateriaisTables.getString("Posicao_Original") + "', '" + r_InventarioMateriaisTables.getString("Nome_Almoxarifado_Original") + "', " + r_InventarioMateriaisTables.getString("FlagMobileUpdate") + "," + r_InventarioMateriaisTables.getString("FlagMobileInsert") +");");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBInventarioMateriaisTables_update.executeUpdate("update UPWEBInventario_Materials set FlagMobileInsert = 0 WHERE Id=" + r_InventarioMateriaisTables.getInt("Id"));
                } else {
                    smtp_UPWEBInventarioMateriaisTables_update.executeUpdate("update UPWEBInventario_Materials set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id=" + r_InventarioMateriaisTables.getInt("Id"));
                }

            }

            //***UPDATE UPWEBInventarioMateriais***
            Statement smtp_UPWEBInventarioMateriaisTables2 = DbConn.createStatement();
            Statement smtp_UPWEBInventarioMateriaisTables_update2 = DbConn.createStatement();
            ResultSet r_InventarioMateriaisTables2 = smtp_UPWEBInventarioMateriaisTables2.executeQuery("SELECT * FROM UPWEBInventario_Materials WHERE FlagMobileUpdate = 1  and Cod_Coletor='" + device +"'");

            while(r_InventarioMateriaisTables2.next()){
                db.execSQL("UPDATE InventarioMaterial SET Id_Original = " + r_InventarioMateriaisTables2.getString("Id_Original") +" , Material_Type = '"+ Replace_AS(r_InventarioMateriaisTables2.getString("Material_Type")) +"' ,lote_material = '"+ Replace_AS(r_InventarioMateriaisTables2.getString("lote_material")) +"' ,Categoria = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Categoria")) +"' ,Modalidade = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Modalidade")) +"' ,Proprietario_SETOR = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Proprietario_SETOR")) +"' ,Desc_Local = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Desc_Local")) +"' ,Nome_Almoxarifado = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Nome_Almoxarifado")) +"' ,Quantidade = '"  + r_InventarioMateriaisTables2.getString("Quantidade") +"' ,Desc_Posicao = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Desc_Posicao")) +"' ,DataHoraLocalizacao = '"  + r_InventarioMateriaisTables2.getString("DataHoraLocalizacao")+"' ,Condition = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Condition")) +"' ,Processo = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Processo"))+ "' ,Dominio_Atualizacao = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("Dominio_Atualizacao")) +"' ,NF_Empresa = '"  + Replace_AS(r_InventarioMateriaisTables2.getString("NF_Empresa"))+"' ,num_NF = '"  + r_InventarioMateriaisTables2.getString("num_NF")+"' ,TAGID_Material = '"  + r_InventarioMateriaisTables2.getString("TAGID_Material")+"' ,TAGID_Posicao = '"  + r_InventarioMateriaisTables2.getString("TAGID_Posicao") +"' ,Cod_Posicao = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Cod_Posicao")) +"' ,ID_Omni = '" + Replace_AS(r_InventarioMateriaisTables2.getString("ID_Omni")) +"' ,Posicao_Original = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Posicao_Original")) +"' ,Nome_Almoxarifado_Original = '" + Replace_AS(r_InventarioMateriaisTables2.getString("Nome_Almoxarifado_Original")) +"' ,FlagMobileUpdate = 1 ,FlagMobileInsert = 0 WHERE Id_Original="+ r_InventarioMateriaisTables2.getInt("Id_Original"));
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                smtp_UPWEBInventarioMateriaisTables_update2.executeUpdate("update UPWEBInventario_Materials set FlagMobileUpdate = 0  WHERE Id="+ r_InventarioMateriaisTables2.getInt("Id"));
            }

            //***DELETE UPWEBInventarioMateriais e UPWEBWorksheet***
            Statement smtp_UPWEBInventarioMateriaisTables3 = DbConn.createStatement();
            Statement smtp_UPWEBInventarioMateriaisTables_delete = DbConn.createStatement();
            Statement smtp_UPWEBWorksheetTables_delete = DbConn.createStatement();
            ResultSet r_InventarioMateriaisTables3 = smtp_UPWEBInventarioMateriaisTables3.executeQuery("SELECT * FROM UPWEBInventario_Materials WHERE FlagMobileDelete = 1 and Cod_Coletor='" + device +"'");
            while (r_InventarioMateriaisTables3.next()){
                db.execSQL("DELETE FROM InventarioMaterial WHERE TAGID_Material='"+ r_InventarioMateriaisTables3.getString("TAGID_Material") +"'");
                db.execSQL("DELETE FROM UPWEBWorksheet WHERE Tagid = '" + r_InventarioMateriaisTables3.getString("TAGID_Material") +"'");

                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);

                smtp_UPWEBInventarioMateriaisTables_delete.executeUpdate("delete from UPWEBInventario_Materials where TAGID_Material = '"+ r_InventarioMateriaisTables3.getString("TAGID_Material") + "'");
                smtp_UPWEBWorksheetTables_delete.executeUpdate("delete from UPWEBWorksheetsSet where TAGID = '"+ r_InventarioMateriaisTables3.getString("TAGID_Material") + "'");
            }
            //endregion

            //region UPWEBINVENTARIOPLANEJADO

            //***INSERT UPWEBInventarioPlanejado***
            Statement smtp_UPWEBInventarioPlanejado = DbConn.createStatement();
            Statement smtp_UPWEBInventarioPlanejado_update = DbConn.createStatement();
            ResultSet UPWEBInventarioPlanejado1 = smtp_UPWEBInventarioPlanejado.executeQuery("SELECT * FROM UPWEBInventarioPlanejadoes WHERE FlagMobileInsert = 1 and Cod_Coletor='" + device + "'");
            while (UPWEBInventarioPlanejado1.next()){
                query_upwebInventarioPlanejado = new Query_UPWEBInventarioPlanejado(db);
                Cursor inventarioPlanejado1 = query_upwebInventarioPlanejado.UPWEBInventarioPlanejadoIdQuery(UPWEBInventarioPlanejado1.getString("Id_Original"));
                if (inventarioPlanejado1.getCount() == 0){
                    db.execSQL("INSERT INTO UPWEBInventarioPlanejado VALUES('" + UPWEBInventarioPlanejado1.getString("Id_Original") +"', '"+ UPWEBInventarioPlanejado1.getString("Descricao").toString().replace("'","''")  +"', '" + UPWEBInventarioPlanejado1.getString("Cod_Coletor") +"', "  + UPWEBInventarioPlanejado1.getString("FlagMobileUpdate") + "," + UPWEBInventarioPlanejado1.getString("FlagMobileInsert") +");");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBInventarioPlanejado_update.executeUpdate("update UPWEBInventarioPlanejadoes set FlagMobileInsert = 0  WHERE Id="+ UPWEBInventarioPlanejado1.getInt("Id"));
                } else {
                    smtp_UPWEBInventarioPlanejado_update.executeUpdate("update UPWEBInventarioPlanejadoes set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBInventarioPlanejado1.getInt("Id"));
                }
            }

            //endregion

            //region UPWEBLISTAMATERIALINVPLANEJADO
            //**INSERT UPWEBListaMateriaisInvPlanejado
            Statement smtp_UPWEBListaMatInvPlanejado = DbConn.createStatement();
            Statement smtp_UPWEBListaMatInvPlanejado_update = DbConn.createStatement();
            ResultSet R_UPWEBListaMatInvPlanejado = smtp_UPWEBListaMatInvPlanejado.executeQuery("SELECT * FROM UPWEBListaMateriaisInventarioPlanejadoes WHERE FlagMobileInsert = 1  and Cod_Coletor='" + device +"'");

            while(R_UPWEBListaMatInvPlanejado.next()){
                query_upweblistaMateriaisInvPlanejado = new Query_UPWEBListaMateriaisInvPlanejado(db);
                Cursor listaMateriaisInvPlanejado = query_upweblistaMateriaisInvPlanejado.UPWEBListaMateriaisInvPlanejado(R_UPWEBListaMatInvPlanejado.getString("Id_Original"));
                if (listaMateriaisInvPlanejado.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBListaMateriaisInvPlanejado VALUES('" + R_UPWEBListaMatInvPlanejado.getString("Id_Original") +"', '" + R_UPWEBListaMatInvPlanejado.getString("TAGID_Material")  +"', '" + R_UPWEBListaMatInvPlanejado.getString("InventarioPlanejadoTable") +"', '" + R_UPWEBListaMatInvPlanejado.getString("Cod_Coletor") +"', "  + R_UPWEBListaMatInvPlanejado.getString("FlagMobileUpdate") + "," + R_UPWEBListaMatInvPlanejado.getString("FlagMobileInsert") +");");
                    qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                    smtp_UPWEBListaMatInvPlanejado_update.executeUpdate("update UPWEBListaMateriaisInventarioPlanejadoes set FlagMobileInsert = 0  WHERE Id="+ R_UPWEBListaMatInvPlanejado.getInt("Id"));
                } else {
                    smtp_UPWEBListaMatInvPlanejado_update.executeUpdate("update UPWEBListaMateriaisInventarioPlanejadoes set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ R_UPWEBListaMatInvPlanejado.getInt("Id"));
                }
            }
            //endregion

            //region UPWEBLISTAMATERIALTAREFA
            //***INSERT UPWEBListaMateriaisTarefaEqReadnesses***
            Statement smtp_UPWEBListaMateriaisTarefaEqReadnesses1 = DbConn.createStatement();
            Statement smtp_UPWEBListaMateriaisTarefaEqReadnesses1_update = DbConn.createStatement();
            ResultSet UPWEBListaMateriaisTarefaEqReadnesses1 = smtp_UPWEBListaMateriaisTarefaEqReadnesses1.executeQuery("SELECT * FROM UPWEBListaMateriaisTarefaEqReadnessesSet WHERE FlagMobileInsert = 1  and Cod_Coletor='" + device +"'");

            while(UPWEBListaMateriaisTarefaEqReadnesses1.next()){
                query_UPWEBListaMateriaisTarefaEqReadnesses = new Query_UPWEBListaMateriaisTarefaEqReadnesses(db);
                Cursor listarefasMateriais1 = query_UPWEBListaMateriaisTarefaEqReadnesses.ListaMateriaisIdOriginal(UPWEBListaMateriaisTarefaEqReadnesses1.getString("Id_Original"));
                if (listarefasMateriais1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBListaMateriaisTarefaEqReadnesses VALUES('" + UPWEBListaMateriaisTarefaEqReadnesses1.getString("Id_Original") +"', '"+ UPWEBListaMateriaisTarefaEqReadnesses1.getString("Part_Number") +"', '"+ UPWEBListaMateriaisTarefaEqReadnesses1.getString("Modelo_Material").toString().replace("'","''") +"', '"+ UPWEBListaMateriaisTarefaEqReadnesses1.getString("Quantidade") +"', '"+ UPWEBListaMateriaisTarefaEqReadnesses1.getString("Cod_Coletor") + "', '"+ UPWEBListaMateriaisTarefaEqReadnesses1.getString("TarefasEqReadinessTable") + "', '" + UPWEBListaMateriaisTarefaEqReadnesses1.getString("FlagMobileUpdate") + "'," + UPWEBListaMateriaisTarefaEqReadnesses1.getString("FlagMobileInsert") +");");
                    smtp_UPWEBListaMateriaisTarefaEqReadnesses1_update.executeUpdate("update UPWEBListaMateriaisTarefaEqReadnessesSet set FlagMobileInsert = 0  WHERE Id="+ UPWEBListaMateriaisTarefaEqReadnesses1.getInt("Id"));
                } else {
                    smtp_UPWEBListaMateriaisTarefaEqReadnesses1_update.executeUpdate("update UPWEBListaMateriaisTarefaEqReadnessesSet set FlagMobileInsert = 0, FlagMobileUpdate = 1   WHERE Id="+ UPWEBListaMateriaisTarefaEqReadnesses1.getInt("Id"));
                }
            }

            //***UPDATE UPWEBListaMateriaisTarefaEqReadnesses***
            Statement smtp_UPWEBListaMateriaisTarefaEqReadnesses2 = DbConn.createStatement();
            Statement smtp_UPWEBListaMateriaisTarefaEqReadnesses2_update = DbConn.createStatement();
            ResultSet UPWEBListaMateriaisTarefaEqReadnesses2 = smtp_UPWEBListaMateriaisTarefaEqReadnesses2.executeQuery("SELECT * FROM UPWEBListaMateriaisTarefaEqReadnessesSet WHERE FlagMobileUpdate = 1  and Cod_Coletor='" + device +"'");

            while(UPWEBListaMateriaisTarefaEqReadnesses2.next()){
                db.execSQL("UPDATE UPWEBListaMateriaisTarefaEqReadnesses SET Id_Original = " + UPWEBListaMateriaisTarefaEqReadnesses2.getString("Id_Original") + " ,Part_Number = '"+ UPWEBListaMateriaisTarefaEqReadnesses2.getString("Part_Number") +"' ,Modelo_Material = '"+ UPWEBListaMateriaisTarefaEqReadnesses2.getString("Modelo_Material").replace("'","''") +"' ,Quantidade = "+ UPWEBListaMateriaisTarefaEqReadnesses2.getString("Quantidade") +" ,Cod_Coletor = '"+ UPWEBListaMateriaisTarefaEqReadnesses2.getString("Cod_Coletor") +"' ,FlagMobileUpdate = 1 ,FlagMobileInsert = 0 WHERE Id_Original="+ UPWEBListaMateriaisTarefaEqReadnesses2.getInt("Id_Original"));
                smtp_UPWEBListaMateriaisTarefaEqReadnesses2_update.executeUpdate("update UPWEBListaMateriaisTarefaEqReadnessesSet set FlagMobileUpdate = 0  WHERE Id="+ UPWEBListaMateriaisTarefaEqReadnesses2.getInt("Id"));
            }
            //endregion

            //region UPWMOBProcessoDescartes
            //UPMOBProcessosDescartes
            try {
                c = db.rawQuery("SELECT * FROM UPMOBProcessoDescarte", null);
                while (c.moveToNext()){
                    PreparedStatement stmt_UPMOBProcessoDescartes = DbConn.prepareStatement("INSERT UPMOBProcessoDescartes(TAGID, DataHoraSolicitacao, UsuarioSolicitacao, Motivo, Modalidade, FlagMobile) VALUES (?, ?, ?, ?, ?, ?)");

                    stmt_UPMOBProcessoDescartes.setString(1, c.getString(c.getColumnIndex("TAGID")));
                    stmt_UPMOBProcessoDescartes.setString(2, c.getString(c.getColumnIndex("DataHoraSolicitacao")));
                    stmt_UPMOBProcessoDescartes.setString(3, c.getString(c.getColumnIndex("UsuarioSolicitacao")));
                    stmt_UPMOBProcessoDescartes.setString(4, c.getString(c.getColumnIndex("Motivo")));
                    stmt_UPMOBProcessoDescartes.setString(5, c.getString(c.getColumnIndex("Modalidade")));
                    stmt_UPMOBProcessoDescartes.setBoolean(6, true);

                    stmt_UPMOBProcessoDescartes.executeUpdate();

                    db.execSQL("DELETE FROM UPMOBProcessoDescarte WHERE TAGID='"+ c.getString(c.getColumnIndex("TAGID"))+"'");
                    updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                }
            } catch (final Exception e) {
                pd.dismiss();
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("AVISO! TABELA UPMOBProcessoDescarte", "Erro 020" + e.toString(), 2);
                    }
                });
            }
            //endregion

            //region UPMOBLISTARESULTADOS

            //***UPMOBListaResultadosServicosSet***
            try {
                c = db.rawQuery("SELECT * FROM UPMOBListaResultadosServicosSet", null);
                while (c.moveToNext()){
                    PreparedStatement stmt_UPMOBListaResultServicos = DbConn.prepareStatement("INSERT UPMOBListaResultadosServicosSet(Resultado, DataHoraEvento, IdServicoAdicional, TAGID_Material, Cod_Coletor,FlagMobileUpdate, FlagMobileInsert) VALUES (?, ?, ?, ?, ?, ?, ?)");

                    stmt_UPMOBListaResultServicos.setString(1, c.getString(c.getColumnIndex("Resultado")));
                    stmt_UPMOBListaResultServicos.setString(2, c.getString(c.getColumnIndex("DataHoraEvento")));
                    stmt_UPMOBListaResultServicos.setString(3, c.getString(c.getColumnIndex("IdServicoAdicional")));
                    stmt_UPMOBListaResultServicos.setString(4, c.getString(c.getColumnIndex("TAGID_Material")));
                    stmt_UPMOBListaResultServicos.setString(5, c.getString(c.getColumnIndex("Cod_Coletor")));
                    stmt_UPMOBListaResultServicos.setString(6, c.getString(c.getColumnIndex("FlagMobileUpdate")));
                    stmt_UPMOBListaResultServicos.setString(7, c.getString(c.getColumnIndex("FlagMobileInsert")));

                    stmt_UPMOBListaResultServicos.executeUpdate();

                    db.execSQL("DELETE FROM UPMOBListaResultadosServicosSet WHERE _Id='"+ c.getInt(c.getColumnIndex("_Id"))+"'");
                    updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
                }

            } catch (final Exception e) {
                pd.dismiss();
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("AVISO! TABELA UPMOBListaResultadosServicosSet", "Erro 020" + e.toString(), 2);
                    }
                });
            }

            //endregion

            //region ALMOXARIFADO
            //***ALMOXARIFADO***
            Statement smtp_AlmoxarifadoTables = DbConn.createStatement();
            //Statement smtp_AlmoxarifadoTables_update = DbConn.createStatement();
            ResultSet AlmoxarifadoTables = smtp_AlmoxarifadoTables.executeQuery("SELECT * FROM AlmoxarifadoTables");
            db.execSQL("DELETE from UPWEBAlmoxarifado");
            while(AlmoxarifadoTables.next()){
                db.execSQL("INSERT INTO UPWEBAlmoxarifado VALUES('"+ AlmoxarifadoTables.getString("Cod_Almoxarifado") +"', '"+ AlmoxarifadoTables.getString("Nome_Almoxarifado") +"');");
                qtdRegistrosAtual = updateProgressDialog(qtdRegistrosAtual, qtdRegistrosTotal, pd);
            }
            //endregion
*/

            pd.dismiss();
            final String device2 = device;
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    showMessage("AVISO","SINCRONIZADO COM SUCESSO!!!!" + "\n\nDispositivo N° Série: " + device2, 1);
                }
            });
            DbConn.close();
        } catch (final SQLException e)
        {
            pd.dismiss();
            Handler mHandler1 = new Handler(Looper.getMainLooper());
            mHandler1.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (e.toString().contains("code 14")) {
                            showMessage("AVISO!","Não foi possível conectar com Banco de Dados remoto. Verifique sua conexão de dados e/ou sua conexão de rede.", 3);
                        } else {
                            showMessage("AVISO!",e.toString(), 3);
                        }
                    } catch (final Exception e) {
                        showMessage("AVISO","Não foi possível concluir a Sincronização. Tente novamente em alguns minutos, se o problema insistir entre em contato com o Suporte.", 3);
                    }

                }
            });

        }catch (final Exception e)
        {
            pd.dismiss();
            Handler mHandler2 = new Handler(Looper.getMainLooper());
            mHandler2.post(new Runnable() {
                @Override
                public void run() {
                    // Your UI updates here
                    try {
                        if (e.toString().contains("code 14")) {
                            showMessage("AVISO","Não foi possível conectar com Banco de Dados remoto. Verifique sua conexão de dados e/ou sua conexão de rede.", 3);
                        } else {
                            showMessage("AVISO",e.toString(), 3);
                        }
                    }catch (final Exception e) {
                        showMessage("AVISO","Não foi possível concluir a Sincronização. Tente novamente em alguns minutos, se o problema insistir entre em contato com o Suporte.", 3);
                    }

                }
            });

        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    protected Boolean ExecutarUPWEBAcoesColetores_Dados(SQLiteDatabase db, Connection DbConn, String device)
    {
        boolean flagReturn = true;
        query_upwebAcoesColetores_Dados = new Query_UPWEBAcoesColetores_Dados(db);
        Cursor listaAcoes1 = query_upwebAcoesColetores_Dados.ListaAcoesPendentes();

        if (listaAcoes1.getCount() > 0)
        {
            listaAcoes1.moveToFirst();
            do{

                if(listaAcoes1.getString(listaAcoes1.getColumnIndex("Acao")).equals("Reiniciar_Lista_Tarefa")){
                    flagReturn = Reiniciar_Lista_Tarefa(db, DbConn, device);
                    db.execSQL("UPDATE UPWEBAcoesColetores_Dados SET FlagProcess = 1 WHERE Id_Original="+ listaAcoes1.getString(listaAcoes1.getColumnIndex("Id_Original")));
                }

            }while (listaAcoes1.moveToNext());
        }
        return flagReturn;
    }

    protected Boolean Reiniciar_Lista_Tarefa(SQLiteDatabase db, Connection DbConn, String device)
    {
        //DROP TABLES
        db.execSQL(Script_Idativos02Data.DropTableUPWEBListaTarefasEqReadinessTables());
        db.execSQL(Script_Idativos02Data.DropTableUPWEBListaMateriaisListaTarefaEqReadnesses());
        db.execSQL(Script_Idativos02Data.DropTableUPWEBLista_Serviços_AdicionaisSet());

        try{
            //RE-CRIANDO TABELAS
            db.execSQL(Script_Idativos02Data.CreateUPWEBListaTarefasEqReadinessTables(0, 0));
            db.execSQL(Script_Idativos02Data.CreateUPWEBListaMateriaisListaTarefaEqReadnesses(0, 0));
            db.execSQL(Script_Idativos02Data.CreateUPWEBLista_Serviços_AdicionaisSet(0, 0));

            //***INSERT UPWEBListaTarefasEqReadinessTables***
            Statement smtp_ListaTarefasEqReadinessTables = DbConn.createStatement();
            Statement smtp_ListaTarefasEqReadinessTables_update = DbConn.createStatement();
            ResultSet ListaTarefasEqReadinessTables = smtp_ListaTarefasEqReadinessTables.executeQuery("SELECT * FROM UPWEBListaTarefasEqReadinessTablesSet WHERE Cod_Coletor='" + device +"' AND Status <> 'Concluída' And Status <> 'Cancelada'");
            Insert_Lista_Tarefa(db, DbConn, smtp_ListaTarefasEqReadinessTables_update, ListaTarefasEqReadinessTables, device);

        }catch (final Exception e){
            return false;
        }
        return true;
    }

    private void Insert_Lista_Tarefa(SQLiteDatabase db, Connection DbConn, Statement smtp_ListaTarefasEqReadinessTables_update, ResultSet ListaTarefasEqReadinessTables, String device)
    {
        try{
            while(ListaTarefasEqReadinessTables.next()){
                query_UPWEBListaTarefasEqReadinessTables = new Query_UPWEBListaTarefasEqReadinessTables(db);
                Cursor listarefas1 = query_UPWEBListaTarefasEqReadinessTables.ListaTarefasIdOriginal(ListaTarefasEqReadinessTables.getString("IdOriginal"));
                if (listarefas1.getCount() == 0) {
                    db.execSQL("INSERT INTO UPWEBListaTarefasEqReadinessTables VALUES('" + ListaTarefasEqReadinessTables.getInt("IdOriginal") +"', '"+ ListaTarefasEqReadinessTables.getString("Status") +"', '"+ ListaTarefasEqReadinessTables.getString("DataInicio") +"', '"+ ListaTarefasEqReadinessTables.getString("DataFimPrevisao") +"', '"+ ListaTarefasEqReadinessTables.getString("DataFimReal") +"', '"+ ListaTarefasEqReadinessTables.getString("DataCancelamento") +"', '"+ ListaTarefasEqReadinessTables.getString("Dominio") +"', '"+ ListaTarefasEqReadinessTables.getString("Processo") +"', '"+ ListaTarefasEqReadinessTables.getInt("TarefaItemIdOriginal") +"');");
                    smtp_ListaTarefasEqReadinessTables_update.executeUpdate("update UPWEBListaTarefas set FlagMobileInsert = 0  WHERE Id="+ ListaTarefasEqReadinessTables.getInt("Id"));
                } else {
                    smtp_ListaTarefasEqReadinessTables_update.executeUpdate("update UPWEBListaTarefas set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ ListaTarefasEqReadinessTables.getInt("Id"));
                }

                //***INSERT UPWEBLISTAMATERIAISLISTATAREFAEQREADNESSES***
                Statement smtp_UPWEBListaMateriaisListaTarefa1 = DbConn.createStatement();
                Statement smtp_UPWEBListaMateriaisListaTarefa1_update = DbConn.createStatement();
                ResultSet UPWEBListaMateriaisListaTarefa1 = smtp_UPWEBListaMateriaisListaTarefa1.executeQuery("SELECT * FROM UPWEBListaMateriaisListaTarefas WHERE ListaTarefasItemIdOriginal = " + ListaTarefasEqReadinessTables.getInt("IdOriginal") + " And CodColetor='" + device +"'");

                while(UPWEBListaMateriaisListaTarefa1.next()){
                    query_UPWEBListaMateriaisListaTarefaEqReadnesses = new Query_UPWEBListaMateriaisListaTarefaEqReadnesses(db);
                    Cursor listarefasMateriais1 = query_UPWEBListaMateriaisListaTarefaEqReadnesses.ListaMateriaisIdOriginal(UPWEBListaMateriaisListaTarefa1.getString("IdOriginal"));
                    if (listarefasMateriais1.getCount() == 0) {
                        db.execSQL("INSERT INTO UPWEBListaMateriaisListaTarefaEqReadnesses VALUES('" + UPWEBListaMateriaisListaTarefa1.getInt("IdOriginal") +"', '"+ UPWEBListaMateriaisListaTarefa1.getString("Status") +"', '"+ UPWEBListaMateriaisListaTarefa1.getString("DataInicio") +"', '"+ UPWEBListaMateriaisListaTarefa1.getString("DataConclusao") +"', '"+ UPWEBListaMateriaisListaTarefa1.getString("Observacao") +"', '"+ UPWEBListaMateriaisListaTarefa1.getInt("ListaTarefasItemIdOriginal") +"', '"+ UPWEBListaMateriaisListaTarefa1.getInt("CadastroMateriaisItemIdOriginal")+"');");
                        smtp_UPWEBListaMateriaisListaTarefa1_update.executeUpdate("update UPWEBListaMateriaisListaTarefas set FlagMobileInsert = 0  WHERE Id="+ UPWEBListaMateriaisListaTarefa1.getInt("Id"));
                    } else {
                        smtp_UPWEBListaMateriaisListaTarefa1_update.executeUpdate("update UPWEBListaMateriaisListaTarefas set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id="+ UPWEBListaMateriaisListaTarefa1.getInt("Id"));
                    }

                    /*//***INSERT UPWEBLISTARESULTADOS***
                    Statement smtp_UPWEBListaResultados = DbConn.createStatement();
                    Statement smtp_UPWEBListaResultados_update = DbConn.createStatement();
                    ResultSet UPWEBListaResultados = smtp_UPWEBListaResultados.executeQuery("SELECT * FROM UPWEBListaResultadosServicosSet WHERE ListaTarefasEqReadinessTable = " + ListaTarefasEqReadinessTables.getInt("Id_Original") +
                            " And ListaMateriaisListaTarefasEqReadinessTable = " + UPWEBListaMateriaisListaTarefa1.getInt("Id_Original") +
                            " And Cod_Coletor = '" + device + "'");

                    while(UPWEBListaResultados.next()){
                        query_UPWEBListaResultados = new Query_UPWEBListaResultados(db);
                        Cursor listaresultados1 = query_UPWEBListaResultados.GetResultsByIdOriginal(UPWEBListaResultados.getString("Id_Original"));
                        if (listaresultados1.getCount() == 0){
                            db.execSQL("INSERT INTO UPWEBListaResultadosServicosSet VALUES('" + UPWEBListaResultados.getString("Id_Original") +
                                    "','" + UPWEBListaResultados.getString("Resultado") +
                                    "','" + UPWEBListaResultados.getString("TAGID_Material") +
                                    "','" + UPWEBListaResultados.getString("ListaMateriaisListaTarefasEqReadinessTable") +
                                    "','" + UPWEBListaResultados.getString("ListaTarefasEqReadinessTable") +
                                    "','" + UPWEBListaResultados.getString("Cod_Coletor") +
                                    "','" + UPWEBListaResultados.getString("FlagMobileUpdate") +
                                    "','" + UPWEBListaResultados.getString("FlagMobileInsert") + "')");
                            smtp_UPWEBListaResultados_update.executeUpdate("update UPWEBListaResultadosServicosSet set FlagMobileInsert = 0  WHERE Id="+ UPWEBListaResultados.getInt("Id"));
                        } else {
                            smtp_UPWEBListaResultados_update.executeUpdate("update UPWEBListaResultadosServicosSet set FlagMobileInsert = 0, FlagMobileUpdate = 1 WHERE Id="+ UPWEBListaResultados.getInt("Id"));
                        }
                    }*/
                }

                //***INSERT UPWEBLista_Serviços_AdicionaisSet***
                Statement smtp_UPWEBLista_Serviços_AdicionaisSet1 = DbConn.createStatement();
                Statement smtp_UPWEBLista_Serviços_AdicionaisSet1_update = DbConn.createStatement();
                ResultSet UPWEBLista_Serviços_AdicionaisSet1 = smtp_UPWEBLista_Serviços_AdicionaisSet1.executeQuery("SELECT * FROM UPWEBListaServicosListaTarefas WHERE ListaTarefasItemIdOriginal = " + ListaTarefasEqReadinessTables.getInt("IdOriginal") + " And CodColetor='" + device +"'");

                while(UPWEBLista_Serviços_AdicionaisSet1.next()){
                    query_UPWEBLista_Serviços_AdicionaisSet = new Query_UPMOBListaMateriaisListaTarefaEqReadnesses.Query_UPWEBLista_Serviços_AdicionaisSet(db);
                    Cursor listarefasMateriais1 = query_UPWEBLista_Serviços_AdicionaisSet.ListaServicoIdOriginal(UPWEBLista_Serviços_AdicionaisSet1.getString("IdOriginal"));
                    if (listarefasMateriais1.getCount() == 0) {
                        db.execSQL("INSERT INTO UPWEBLista_Serviços_AdicionaisSet VALUES('" + UPWEBLista_Serviços_AdicionaisSet1.getInt("IdOriginal") +"', '"+ UPWEBLista_Serviços_AdicionaisSet1.getString("Status") +"', '"+ UPWEBLista_Serviços_AdicionaisSet1.getString("DataInicio") +"', '"+ UPWEBLista_Serviços_AdicionaisSet1.getString("DataConclusao") +"', '" + UPWEBLista_Serviços_AdicionaisSet1.getString("Resultado") +"', '" + UPWEBLista_Serviços_AdicionaisSet1.getInt("ListaTarefasItemIdOriginal") +"', '"  + UPWEBLista_Serviços_AdicionaisSet1.getInt("ServicoAdicinalItemIdOriginal")+"');");
                        smtp_UPWEBLista_Serviços_AdicionaisSet1_update.executeUpdate("update UPWEBListaServicosListaTarefas set FlagMobileInsert = 0  WHERE Id="+ UPWEBLista_Serviços_AdicionaisSet1.getInt("Id"));
                    } else {
                        smtp_UPWEBLista_Serviços_AdicionaisSet1_update.executeUpdate("update UPWEBListaServicosListaTarefas set FlagMobileInsert = 0, FlagMobileUpdate = 1  WHERE Id="+ UPWEBLista_Serviços_AdicionaisSet1.getInt("Id"));
                    }
                }
            }

        }catch (final SQLException e){
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Your UI updates here
                    showMessage("AVISO","Erro 008: " + e.toString(), 2);
                }
            });}
    }

    public void showMessage(String title,String message,int type)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context1);

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

    protected boolean Verificar_Versao(SQLiteDatabase db, Connection DbConn)
    {
        String versao_app_local = null;
        String versao_app_remote = null;

        try{
            Statement smtp_IDs_Sistemas = DbConn.createStatement();
            ResultSet r_IDs_Sistemas = smtp_IDs_Sistemas.executeQuery("SELECT * FROM IDs_Sistemas LIMIT 1");
            while(r_IDs_Sistemas.next()){
                versao_app_remote = r_IDs_Sistemas.getString("Versao_App");

                query_IDs_Sistema = new Query_InventarioEquipamento.Query_IDs_Sistema(db);
                versao_app_local = query_IDs_Sistema.VersaoIDAtivosQuery();

                if (!versao_app_local.equals(null) && !versao_app_local.equals("")  && !versao_app_remote.isEmpty()){
                    db.execSQL("UPDATE IDs_Sistema SET VersaoApp = '" + versao_app_remote + "' WHERE IdOriginal = 1");
                    versao_app_local = versao_app_remote;

                } else if (!versao_app_remote.isEmpty()) {
                    db.execSQL("INSERT INTO IDs_Sistema VALUES('" + r_IDs_Sistemas.getString("Id") +"', '"+ versao_app_remote +"');");
                    versao_app_local = versao_app_remote;
                }
            }

        } catch (final SQLException e){
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Your UI updates here
                    showMessage("AVISO","Erro 010: " + e.toString(), 2);
                }
            });}

        if (!versao_app_local.equals(versao_IDAtivos)){
            return false;
        }

        return true;
    }

    protected Connection DbConnect()
    {

        //CONEXÃOSERVIDORSQL   --   DESENVOLVIMENTO ***
        String driver="net.sourceforge.jtds.jdbc.Driver";
        String Server_Adress="179.127.193.166";
        String portaSQL="1433";
        String dataBaseName="idativos2019_omni";
        String username="sa";
        String password="idutto@04d";


        //CONEXÃOSERVIDORSQL   --   HOMOLOGAÇÃO***

/*        String driver="net.sourceforge.jtds.jdbc.Driver";
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
            showMessage("AVISO","Não foi possivel conectar ao banco de dados !" + e.toString(), 3);
            return null;
        } catch (final Exception e)  {
            showMessage("AVISO","Não foi possivel conectar ao banco de dados !" + e.toString(), 3);
            return null;
        }

        return DbConn;
    }

    protected String Replace_AS(String str1){
        try {
            str1 = str1.replace("'","''");
        } catch (Exception e) { }

        return str1;
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) context1.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    private int updateProgressDialog(int qtdRegistrosAtual, int qtdRegistrosTotal, ProgressDialog pd){
        qtdRegistrosAtual ++;
        float actualPercentage = ((qtdRegistrosAtual * 100)/qtdRegistrosTotal);
        //int progress = Math.round(actualPercentage);
        int progress = qtdRegistrosAtual;

        final Handler myHandler = new Handler(Looper.getMainLooper());
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                pd.setProgress(progress);
            }
        });
        return qtdRegistrosAtual;
    }
}