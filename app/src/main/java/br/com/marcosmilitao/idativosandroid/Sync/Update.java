package br.com.marcosmilitao.idativosandroid.Sync;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

import br.com.marcosmilitao.idativosandroid.Idativos02Data.Idativos02Data;
import br.com.marcosmilitao.idativosandroid.Idativos02Data.Query.Query_UPMOBListaTarefasEqReadinessTables;

/**
 * Created by Vinicius on 01/02/2017.
 */

public class Update extends SQLiteOpenHelper {
    private Idativos02Data idativos02Data;
    private Context context1;
    ProgressDialog progress;
    private int num_NF1;
    private static BluetoothAdapter BA;
    private static Set<BluetoothDevice> pairedDevices;
    //private static String device1;

    private Query_UPMOBListaTarefasEqReadinessTables query_UPMOBListaTarefas;

    public Update(Context context, SQLiteDatabase db){
        super(context, "Sync", null, 100);

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
        final ProgressDialog pd = new ProgressDialog(this.context1);
        final SQLiteDatabase db2 = db;
        final Thread mThread = new Thread() {
            @Override
            public void run() {
                SyncDatabase(db2,pd);
            }
        };
        mThread.start();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Connection SyncDatabase(SQLiteDatabase db, ProgressDialog pd){
        //connectBA ();

        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            String Server_Adress="162.144.63.165";
            String portaSQL="1433";
            String dataBaseName="idativos_omni02_teste";
            String username="iduttosqladm";
            String password="1dutto@04d";

            Class.forName(driver).newInstance();
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + Server_Adress + ":" + portaSQL + ";databaseName=" + dataBaseName + ";user=" + username + ";password=" + password);

            return DbConn;
        } catch (final Exception e)
        {
            /*pd.dismiss();
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Your UI updates here
                    showMessage("AVISO!",e.toString());
                }
            });*/
        }
        return null;
    }

    public static boolean UpdateInsert_Tarefa(Context context, SQLiteDatabase db, Integer id_Original1, String cod_Processo1, String cod_Tarefa1, String trace_number1, String status1, String data_Inicio1, String data_Fim_Previsao1, String data_Fim_Real1, String cod_Coletor1, String data_hora_evento1, String prioridade_Execucao1, Boolean flagNovoProcesso1, Boolean flagMobileUpdate1, Boolean flagMobileInsert1){
        Integer Id1;
        //***Incluir Ou Editar Tarefa***
        if (cod_Processo1 == null && flagMobileUpdate1 == true){
            showMessage(context, "AVISO!","Erro Update 001: Error ao Atualizar Tarefa. Código do Processo Não Informado.");
            return false;
        }

        if (id_Original1 == 0 && flagMobileUpdate1 == true){
            showMessage(context, "AVISO!","Erro Update 002: Error ao Atualizar Tarefa. Id_Original da Tarefa Não Informado.");
            return false;
        }
        try {
            db.execSQL("INSERT INTO UPMOBListaTarefasEqReadinessTables VALUES(" + null + ",'" + id_Original1 +  "','" + cod_Processo1 + "','"  + cod_Tarefa1 + "','" + trace_number1 +"','" + status1 +"','" + data_Inicio1 +"','" + data_Fim_Previsao1 + "','" + data_Fim_Real1 + "','" + cod_Coletor1 + "','" + data_hora_evento1 + "','" + prioridade_Execucao1 + "','" + flagNovoProcesso1 +"','" + flagMobileUpdate1 +"','" + flagMobileInsert1 +"','" + false +"');");

        } catch (final Exception e) {
            showMessage(context, "AVISO","Erro Update 003: " + e.toString());
            return false;
        }
        return true;
    }

    public static boolean UpdateInsert_ListaMaterial (Context context, SQLiteDatabase db, Integer id_Original1, String Part_Number1, String ID_Omni1, String Modelo_Material1, Float Quantidade1, String TAGID_Material1, String Cod_Coletor1, String Cod_Tarefa1, String data_hora_evento1, Integer ListaTarefasEqReadinessTables1, Boolean flagMobileUpdate1, Boolean flagMobileInsert1, String Observacao_Material){
        //connectBA ();

        //***Incluir ou Editar Material***
        if (id_Original1 == 0 && flagMobileUpdate1 == true) {
            showMessage(context, "AVISO!","Erro Update 004: Error ao Atualizar Lista de Material. Id_Original da Lista de Materiais Não Informado.");
            return false;
        }

        String LocalizacaoOrigem = new String();
        String LocalizacaoDestino = new String();

        try {

            db.execSQL("INSERT INTO UPMOBListaMateriaisListaTarefaEqReadnesses VALUES(" + null + ",'" + id_Original1 + "','" +Part_Number1 + "','" + ID_Omni1 + "','" +  Modelo_Material1 +"','" + Quantidade1 +"','" + LocalizacaoOrigem +"','" + LocalizacaoDestino +"','" + TAGID_Material1 + "','" + Cod_Coletor1 + "','" + Cod_Tarefa1 + "','" + data_hora_evento1 + "','" + ListaTarefasEqReadinessTables1 + "','" + "0" + "','" + "0" +"','" + flagMobileUpdate1 + "','" + flagMobileInsert1  +"','"+ Observacao_Material +"');");

        } catch (final Exception e) {
            showMessage(context, "AVISO","Erro Update 005: " + e.toString() + " | " + id_Original1 + "','" +Part_Number1 + "','" + ID_Omni1 + "','" +  Modelo_Material1 +"','" + Quantidade1 +"','" + LocalizacaoOrigem +"','" + LocalizacaoDestino +"','" + TAGID_Material1 + "','" + Cod_Coletor1 + "','" + Cod_Tarefa1 + "','" + data_hora_evento1 + "','" + "0" +"','" + flagMobileUpdate1 + "','" + flagMobileInsert1);
            return false;
        }
        return true;
    }

    public static boolean UpdateInsert_ListaServicos (Context context, SQLiteDatabase db, Integer id_Original1, String Servico1, String Descricao1, String Resultado1, Float Quantidade1, String Status1, String Data_Inicio1, String Data_Conclusao1, String Data_Cancelado1, String Cod_Processo1, String Cod_Tarefa1, String Tipo_Tarefa1, String Modalidade_Servico1, String Cod_Coletor1, String data_hora_evento1, int ListaTarefasEqReadinessTables1, Boolean FlagUsuario_Execucao1, Boolean flagMobileUpdate1, Boolean flagMobileInsert1){
        //connectBA ();

        //***Incluir ou Editar Servico***
        if (id_Original1 == 0 && flagMobileUpdate1 == true) {
            showMessage(context, "AVISO!","Erro Update 006: Error ao Atualizar Lista de Serviços. Id_Original da Lista de Serviços Não Informado.");
            return false;
        }
        try {

            db.execSQL("INSERT INTO UPMOBLista_Servicos_AdicionaisSet VALUES(" + null + ",'" + id_Original1 + "','" + Servico1 + "','" + Descricao1 +"','" + Resultado1 +"','" + Quantidade1 +"','" + Status1 +"','" + Data_Inicio1 +"','" + Data_Conclusao1 + "','" + Data_Cancelado1 + "','" + Cod_Processo1 + "','" + Cod_Tarefa1 + "','" + Tipo_Tarefa1 +"','" + Modalidade_Servico1 +"','"+ Cod_Coletor1 + "','" + data_hora_evento1 + "','" + ListaTarefasEqReadinessTables1 +"','" + FlagUsuario_Execucao1 +"','" + flagMobileUpdate1 + "','" + flagMobileInsert1 +"');");

        } catch (final Exception e) {
            showMessage(context, "AVISO","Erro Update 007: " + e.toString());
            return false;
        }
        return true;
    }

    protected static void showMessage(Context context1, String title, String message)
    {
        //try {
        //    builder1.setCancelable(true);
        //} catch (Exception e) {}

        AlertDialog.Builder builder2 = new AlertDialog.Builder(context1);
        builder2.setCancelable(true);
        builder2.setTitle(title);
        builder2.setMessage(message);
        builder2.show();

        // builder1 = builder2;
    }

/*    protected static void connectBA()
    {
        BA = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = BA.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
            device1 = bt.getName();
        }

    }*/
}