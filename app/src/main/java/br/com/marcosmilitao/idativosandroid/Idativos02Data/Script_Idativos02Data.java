package br.com.marcosmilitao.idativosandroid.Idativos02Data;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class Script_Idativos02Data {

    //UPMOBS
    public static String CreateUPMOBWorksheet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBWorksheet( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("TraceNumber VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("NumProduto VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Patrimonio VARCHAR (255), ");
            sqlBuilder.append("NumSerie VARCHAR (255), ");
            sqlBuilder.append("Quantidade INTEGER, ");
            sqlBuilder.append("Modalidade VARCHAR (255)NOT NULL, ");
            sqlBuilder.append("DataFabricacao DATETIME, ");
            sqlBuilder.append("DataValidade DATETIME, ");
            sqlBuilder.append("DataHoraEvento DATETIME, ");
            sqlBuilder.append("DadosTecnicos VARCHAR (255), ");
            sqlBuilder.append("TAGID VARCHAR, ");
            sqlBuilder.append("TAGIDPosicao VARCHAR (255), ");
            sqlBuilder.append("Categoria VARCHAR (255),");
            sqlBuilder.append("NF VARCHAR (255), ");
            sqlBuilder.append("DataEntradaNF DATETIME, ");
            sqlBuilder.append("ValorUnitario DECIMAL(18,2),");
            sqlBuilder.append("DescricaoErro VARCHAR (255), ");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN ");

            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }
        return sqlBuilder.toString();
    }

    public static String CreateUPMOBWorksheetItens(int oldversion, int newversion) {
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBWorksheetItens( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Patrimonio VARCHAR (255), ");
            sqlBuilder.append("NumSerie VARCHAR (255), ");
            sqlBuilder.append("NumProduto VARCHAR (255), ");
            sqlBuilder.append("Quantidade INTEGER, ");
            sqlBuilder.append("DataHoraEvento DATETIME NOT NULL, ");
            sqlBuilder.append("DataValidade DATETIME, ");
            sqlBuilder.append("DataFabricacao DATETIME, ");
            sqlBuilder.append("TAGIDContentor VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("DescricaoErro VARCHAR (255),");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN ");

            sqlBuilder.append(");");
            return sqlBuilder.toString();

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBHistoricoLocalizacao(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBHistoricoLocalizacao(TAGID VARCHAR,TAGIDPosicao VARCHAR,DataHoraEvento DATETIME,Processo VARCHAR,Dominio VARCHAR,Quantidade INTEGER,Modalidade VARCHAR,DescricaoErro VARCHAR,FlagErro BOOLEAN,FlagProcesso BOOLEAN);");
            return sqlBuilder.toString();

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBListaTarefasEqReadinessTables(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBListaTarefasEqReadinessTables( ");
            sqlBuilder.append("_Id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("CodTarefa VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Status VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TraceNumber VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("DataInicio DATETIME NOT NULL, ");
            sqlBuilder.append("DataFimReal DATETIME, ");
            sqlBuilder.append("DataHoraEvento DATETIME, ");
            sqlBuilder.append("CodColetor VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("DescricaoErro VARCHAR (255), ");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN");

            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBListaMateriaisListaTarefaEqReadnesses(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBListaMateriaisListaTarefaEqReadnesses( ");
            sqlBuilder.append("_Id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("IdOriginal INTEGER  NOT NULL, ");
            sqlBuilder.append("Status VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TAGID VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Quantidade FLOAT, ");
            sqlBuilder.append("Observacao VARCHAR (255), ");
            sqlBuilder.append("CodColetor VARCHAR (255) NOT NULL,");
            sqlBuilder.append("CodTarefa VARCHAR (255) NOT NULL,");
            sqlBuilder.append("DataHoraEvento DATETIME, ");
            sqlBuilder.append("ListaTarefas INTEGER NOT NULL, ");
            sqlBuilder.append("DescricaoErro VARCHAR (255), ");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN ");
            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBLista_Serviços_AdicionaisSet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBLista_Servicos_AdicionaisSet( ");
            sqlBuilder.append("_Id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("CodTarefa VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Servico VARCHAR (255), ");
            sqlBuilder.append("Resultado VARCHAR (255), ");
            sqlBuilder.append("Quantidade VARCHAR (255), ");
            sqlBuilder.append("Status VARCHAR (255), ");
            sqlBuilder.append("DataHoraEvento DATETIME NOT NULL, ");
            sqlBuilder.append("DataInicio DATETIME NOT NULL, ");
            sqlBuilder.append("DataConclusao DATETIME, ");
            sqlBuilder.append("DataCancelamento DATETIME, ");
            sqlBuilder.append("ListaTarefas INTEGER NOT NULL, ");
            sqlBuilder.append("CodColetor VARCHAR (255) NOT NULL,");
            sqlBuilder.append("DescricaoErro VARCHAR (255) NOT NULL,");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN ");
            sqlBuilder.append(");");

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBUsuarios(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBUsuariosSets( ");
            sqlBuilder.append("IdOriginal VARCHAR(255) NOT NULL, ");
            sqlBuilder.append("Permissao VARCHAR(255), ");
            sqlBuilder.append("Usuario VARCHAR(255), ");
            sqlBuilder.append("Email VARCHAR(255), ");
            sqlBuilder.append("NomeCompleto VARCHAR(255), ");
            sqlBuilder.append("TAGID VARCHAR(255), ");
            sqlBuilder.append("Grupo VARCHAR(255), ");
            sqlBuilder.append("DescricaoErro VARCHAR(255), ");
            sqlBuilder.append("FlagErro BOOLEAN, ");
            sqlBuilder.append("FlagAtualizar BOOLEAN, ");
            sqlBuilder.append("FlagProcess BOOLEAN ");

            sqlBuilder.append(");");
        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }


    //UPWEBS
    public static String CreateUPWEBCadastrosEquipmentTable(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBCadastrosEquipmentTable( ");
            sqlBuilder.append("IdOriginal INTEGER, ");
            sqlBuilder.append("TraceNumber VARCHAR (255), ");
            sqlBuilder.append("DataCadastro DATETIME, ");
            sqlBuilder.append("DataFabricacao DATETIME, ");
            sqlBuilder.append("Status VARCHAR (255), ");
            sqlBuilder.append("ModeloEquipamentoItemIdOriginal INTEGER, ");
            sqlBuilder.append("Fabricante VARCHAR (255), ");
            sqlBuilder.append("Localizacao VARCHAR (255), ");
            sqlBuilder.append("TAGID VARCHAR (255) ");
            sqlBuilder.append(");");
        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBUsuariosSet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBUsuariosSet( ");
            sqlBuilder.append("IdOriginal VARCHAR(255) NOT NULL, ");
            sqlBuilder.append("UserName VARCHAR (255) NOT NULL,");
            sqlBuilder.append("NomeCompleto VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Email VARCHAR (255), ");
            sqlBuilder.append("Permissao VARCHAR (255), ");
            sqlBuilder.append("TAGID VARCHAR (255) ");
            sqlBuilder.append(");");
        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBWorksheets(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBWorksheet( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("NumSerie VARCHAR (255), ");
            sqlBuilder.append("Patrimonio VARCHAR (255), ");
            sqlBuilder.append("Quantidade INTEGER, ");
            sqlBuilder.append("DataCadastro DATETIME, ");
            sqlBuilder.append("DataFabricacao DATETIME, ");
            sqlBuilder.append("DataValidade DATETIME, ");
            sqlBuilder.append("ValorUnitario DECIMAL(18,2), ");
            sqlBuilder.append("DadosTecnicos VARCHAR (255), ");
            sqlBuilder.append("NotaFiscal VARCHAR (255), ");
            sqlBuilder.append("DataEntradaNotaFiscal DATETIME, ");
            sqlBuilder.append("Categoria VARCHAR (255), ");
            sqlBuilder.append("FlagContentor BOOLEAN, ");
            sqlBuilder.append("ModeloMateriaisItemIdOriginal INTEGER, ");
            sqlBuilder.append("SetorProprietarioItemIdOriginal INTEGER,");
            sqlBuilder.append("PosicaoOriginalItemIdoriginal INTEGER, ");
            sqlBuilder.append("TAGID VARCHAR (255) ");

            sqlBuilder.append(");");

        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBWorksheetItens(int oldversion, int newversion) {
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBWorksheetItens( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("NumSerie VARCHAR (255), ");
            sqlBuilder.append("Patrimonio VARCHAR (255), ");
            sqlBuilder.append("Quantidade INTEGER, ");
            sqlBuilder.append("DataCadastro DATETIME, ");
            sqlBuilder.append("DataFabricacao DATETIME, ");
            sqlBuilder.append("DataValidade DATETIME, ");
            sqlBuilder.append("CadastroMateriaisItemIdOriginal INTEGER, ");
            sqlBuilder.append("ModeloMateriaisItemIdOriginal INTEGER ");

            sqlBuilder.append(");");
            return sqlBuilder.toString();

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBGrupos(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBGrupos( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Titulo VARCHAR(255), ");
            sqlBuilder.append("Permissao VARCHAR (255) ");

            sqlBuilder.append(");");
        } else {
            //Comando NOOP apenas para retornar valor.
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBListaMateriaisListaTarefaEqReadnesses(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBListaMateriaisListaTarefaEqReadnesses( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Status VARCHAR (255), ");
            sqlBuilder.append("DataInicio DATETIME, ");
            sqlBuilder.append("DataConclusao DATETIME, ");
            sqlBuilder.append("Observacao VARCHAR (255), ");
            sqlBuilder.append("ListaTarefasItemIdOriginal INTEGER, ");
            sqlBuilder.append("CadastroMateriaisItemIdOriginal INTEGER ");

            sqlBuilder.append(");");

        } else {
            //Comando NOOP apenas para retornar valor.
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBLista_Serviços_AdicionaisSet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBLista_Serviços_AdicionaisSet( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Status VARCHAR (255), ");
            sqlBuilder.append("DataInicio DATETIME, ");
            sqlBuilder.append("DataConclusao DATETIME, ");
            sqlBuilder.append("Resultado VARCHAR (255), ");
            sqlBuilder.append("ListaTarefasItemIdOriginal INTEGER, ");
            sqlBuilder.append("ServicoAdicinalItemIdOriginal INTEGER ");

            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }
        return sqlBuilder.toString();
    }

    public static String CreateUPWEBListaTarefasEqReadinessTables(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBListaTarefasEqReadinessTables( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Status VARCHAR (255), ");
            sqlBuilder.append("DataInicio DATETIME, ");
            sqlBuilder.append("DataFimPrevisao DATETIME, ");
            sqlBuilder.append("DataFimReal DATETIME, ");
            sqlBuilder.append("DataCancelamento DATETIME, ");
            sqlBuilder.append("Dominio VARCHAR (255), ");
            sqlBuilder.append("Processo VARCHAR (255), ");
            sqlBuilder.append("TarefaItemIdOriginal INTEGER ");

            sqlBuilder.append(");");

        } else {
            //Comando NOOP apenas para retornar valor.
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBMaterial_Type(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBMaterial_Type( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Modelo VARCHAR (255), ");
            sqlBuilder.append("IDOmni VARCHAR (255), ");
            sqlBuilder.append("PartNumber VARCHAR (255), ");
            sqlBuilder.append("DescricaoTecnica VARCHAR (255), ");
            sqlBuilder.append("ValorUnitario DECIMAL(18,2), ");
            sqlBuilder.append("Fabricante VARCHAR (255), ");
            sqlBuilder.append("Familia VARCHAR (255) ");

            sqlBuilder.append(");");

        } else {

            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBEquipment_Type(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBEquipment_Type( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Modelo VARCHAR (255), ");
            sqlBuilder.append("DescricaoTecnica VARCHAR (255), ");
            sqlBuilder.append("PartNumber VARCHAR (255), ");
            sqlBuilder.append("Fabricante VARCHAR (255), ");
            sqlBuilder.append("Categoria VARCHAR (255) ");

            sqlBuilder.append(");");
        } else {

            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBPosicao(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBPosicao( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Codigo VARCHAR (255), ");
            sqlBuilder.append("Descricao VARCHAR (255), ");
            sqlBuilder.append("Almoxarifado VARCHAR (255), ");
            sqlBuilder.append("TAGID VARCHAR (255) ");
            sqlBuilder.append(");");

        } else {

            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBServicos_AdicionaisSet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBServicos_AdicionaisSet( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Servico VARCHAR (255), ");
            sqlBuilder.append("Descricao VARCHAR (255), ");
            sqlBuilder.append("Modalidade VARCHAR (255), ");
            sqlBuilder.append("FlagObrigatorio BOOLEAN, ");
            sqlBuilder.append("FlagAtivo BOOLEAN, ");
            sqlBuilder.append("TarefaItemIdOriginal INTEGER");

            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }
        return sqlBuilder.toString();
    }

    public static String CreateUPWEBTarefasEqReadinessTable(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBTarefasEqReadinessTable( ");
            sqlBuilder.append("IdOriginal INTEGER  NOT NULL, ");
            sqlBuilder.append("Codigo VARCHAR (255), ");
            sqlBuilder.append("Titulo VARCHAR (255), ");
            sqlBuilder.append("Tipo VARCHAR (255), ");
            sqlBuilder.append("Descricao VARCHAR (255), ");
            sqlBuilder.append("FlagDependenciaServico BOOLEAN, ");
            sqlBuilder.append("FlagDependenciaMaterial BOOLEAN, ");
            sqlBuilder.append("GrupoItemIdOriginal INTEGER, ");
            sqlBuilder.append("CategoriaEquipamentos VARCHAR (255) ");

            sqlBuilder.append(");");

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBProprietarios(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBProprietarios( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("Descricao VARCHAR (255), ");
            sqlBuilder.append("ApplicationUserIdOriginal VARCHAR (255), ");
            sqlBuilder.append("Empresa VARCHAR (255) ");
            sqlBuilder.append(");");

        } else {

            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }


    //OUTRAS
    public static String CreatIDs_Sistema(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS IDs_Sistema( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("VersaoApp VARCHAR (255) NOT NULL ");
            sqlBuilder.append(");");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateParametros_Padrao(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS Parametros_Padrao( ");
            sqlBuilder.append("IdOriginal INTEGER NOT NULL, ");
            sqlBuilder.append("CodColetor VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("CodAlmoxarifado VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("SETORProprietario VARCHAR (255) ");
            sqlBuilder.append(");");

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    //PRECISA DE REVISAO
    public static String CreateUPMOBImagemModelo(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 1){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBImagemModelo( ");
            sqlBuilder.append("ModeloMaterialItemIdOriginal VARCHAR,");
            sqlBuilder.append("ImagemMaterial VARCHAR,");
            sqlBuilder.append("FlagProcess BOOLEAN");

            sqlBuilder.append(");");

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }


    //FORA DE USO TEMPORARIAMENTE
    public static String CreateUPWEBInventarioPlanejado(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 103){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBInventarioPlanejado( ");
            sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
            sqlBuilder.append("Descricao VARCHAR(255) NOT NULL, ");
            sqlBuilder.append("Cod_Coletor VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
            sqlBuilder.append("FlagMobileInsert BOOLEAN ");

            sqlBuilder.append(");");
        } else {
            //Comando NOOP apenas para retornar valor.
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBListaMateriaisInvPlanejado(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 103){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBListaMateriaisInvPlanejado( ");
            sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
            sqlBuilder.append("TAGID_Material VARCHAR(255) NOT NULL, ");
            sqlBuilder.append("InventarioPlanejadoTable INTEGER NOT NULL, ");
            sqlBuilder.append("Cod_Coletor VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
            sqlBuilder.append("FlagMobileInsert BOOLEAN ");

            sqlBuilder.append(");");
        } else {
            //Comando NOOP apenas para retornar valor.
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBListaMateriaisTarefaEqReadnesses(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBListaMateriaisTarefaEqReadnesses( ");
        //sqlBuilder.append("Id INTEGER PRIMARY KEY NOT NULL, ");
        sqlBuilder.append("Id_Original INTEGER  NOT NULL, ");
        sqlBuilder.append("Part_Number VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Modelo_Material VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Quantidade FLOAT, ");
        sqlBuilder.append("Cod_Coletor VARCHAR (255) NOT NULL,");
        sqlBuilder.append("TarefasEqReadinessTable INTEGER FOREIGH KEY NOT NULL, ");

        sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
        sqlBuilder.append("FlagMobileInsert BOOLEAN ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String CreateRegistros_Inventario_Planejado(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS Registros_Inventario_Planejado( ");
        sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
        sqlBuilder.append("Cod_Inv_Plan VARCHAR (255) NOT NULL,");
        sqlBuilder.append("TAGID_Material VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Modelo_Material VARCHAR (255), ");
        sqlBuilder.append("Cod_Posicao VARCHAR (255), ");
        sqlBuilder.append("ID_Omni VARCHAR (255), ");
        sqlBuilder.append("Part_Number VARCHAR (255) ");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String CreateInventario_Planejado(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS Inventario_Planejado( ");
        sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
        sqlBuilder.append("Cod_Inv_Plan VARCHAR (255) NOT NULL");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBPosicao(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBPosicao( ");
        sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
        sqlBuilder.append("Cod_Posicao VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Desc_Posicao VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Cod_Local VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Desc_Local VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Cod_Almoxarifado VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Nome_Almoxarifado VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("TAGID_Posicao VARCHAR (255) NOT NULL, ");

        sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
        sqlBuilder.append("FlagMobileInsert BOOLEAN ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String CreateInventarioEquipamento(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 100){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS InventarioEquipamento( ");
            sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
            sqlBuilder.append("Equipment_Type VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Trace_Number VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Categoria VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Modalidade VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Proprietario_SETOR VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TAGID_Equipment VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TAGID_Posicao VARCHAR (255), ");
            sqlBuilder.append("Cod_Posicao VARCHAR (255), ");
            sqlBuilder.append("Nome_Almoxarifado VARCHAR (255), ");
            sqlBuilder.append("Desc_Local VARCHAR (255),");
            sqlBuilder.append("Quantidade INTEGER, ");
            sqlBuilder.append("NF_Empresa VARCHAR (255), ");
            sqlBuilder.append("num_NF VARCHAR (255),");
            sqlBuilder.append("DataHoraLocalizacao DATETIME, ");
            sqlBuilder.append("Condition VARCHAR NOT NULL, ");
            sqlBuilder.append("Processo VARCHAR (255),");
            sqlBuilder.append("Dominio_Atualizacao VARCHAR (255),");
            sqlBuilder.append("MapData VARCHAR (255), ");
            sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
            sqlBuilder.append("FlagMobileInsert BOOLEAN, ");
            sqlBuilder.append("Status VARCHAR (255) ");

            sqlBuilder.append(");");
        } if (oldversion != 0 && oldversion < 105){
            sqlBuilder.append("ALTER TABLE InventarioEquipamento ADD COLUMN Status VARCHAR;");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateInventarioMaterial(){
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS InventarioMaterial( ");
        sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
        sqlBuilder.append("Material_Type VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("lote_material VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Categoria VARCHAR (255) NOT NULL, ");
        sqlBuilder.append("Modalidade VARCHAR (255)NOT NULL, ");
        sqlBuilder.append("Proprietario_SETOR VARCHAR (255), ");
        sqlBuilder.append("Desc_Local VARCHAR (255), ");
        sqlBuilder.append("Nome_Almoxarifado VARCHAR (255),");
        sqlBuilder.append("Quantidade INTEGER, ");
        sqlBuilder.append("Desc_Posicao VARCHAR (255), ");
        sqlBuilder.append("DataHoraLocalizacao DATETIME, ");
        sqlBuilder.append("Condition VARCHAR (255),");
        sqlBuilder.append("Processo VARCHAR (255),");
        sqlBuilder.append("Dominio_Atualizacao VARCHAR (255), ");
        sqlBuilder.append("NF_Empresa VARCHAR (255), ");
        sqlBuilder.append("num_NF VARCHAR (255), ");
        sqlBuilder.append("TAGID_Material VARCHAR NOT NULL, ");
        sqlBuilder.append("TAGID_Posicao VARCHAR (255),");
        sqlBuilder.append("Cod_Posicao VARCHAR (255),");
        sqlBuilder.append("ID_Omni VARCHAR (255), ");
        sqlBuilder.append("Posicao_Original VARCHAR (255), ");
        sqlBuilder.append("Nome_Almoxarifado_Original VARCHAR (255), ");

        sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
        sqlBuilder.append("FlagMobileInsert BOOLEAN ");

        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    public static String CreateUPMOBCert_Class(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBCert_Class(Id_Cert_Class VARCHAR,Nome_Fantasia VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBContract_Name(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBContract_Name(Cod_Cliente VARCHAR,Num_Contrato VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBDESAT_TAGID(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBDESAT_TAGID(TAGID VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBEquipment_Type(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBEquipment_Type(PartNumber VARCHAR,Equipment_Type VARCHAR,Capacity VARCHAR,Part_Number VARCHAR,Comprimento VARCHAR,Largura VARCHAR,Altura VARCHAR,Diametro VARCHAR,Peso VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBLogError(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBLogError(Desc_Error VARCHAR,param1 VARCHAR,param2 VARCHAR,param3 VARCHAR,param4 VARCHAR,param5 VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBQTDInventario(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBQTDInventario(TAG_ID_EQUIPAMENTO VARCHAR,TAG_ID_MATERIAL VARCHAR,TRACE_NUMBER_EQUIPAMENTO VARCHAR,TRACE_NUMBER_MATERIAL VARCHAR,Data_Hora VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPProcessTime(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPProcessTime(UPWEBTime VARCHAR,UPMOBTime VARCHAR,ColetorTime VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBLoginColetor(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBLoginColetor(Login VARCHAR, Pass VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBPurchase_Order(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBPurchase_Order(Id_PO VARCHAR,PO VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBRequisicao(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBRequisicao(Id_RQ VARCHAR,RQ VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBAlmoxarifado(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBAlmoxarifado(Cod_Almoxarifado VARCHAR, Nome_Almoxarifado VARCHAR);");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBColetores_Dados(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBColetoresDadosSet( ");
        sqlBuilder.append("CodColetor VARCHAR,");
        sqlBuilder.append("Cod_Posicao VARCHAR");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBAcoesColetores_Dados(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBAcoesColetores_Dados( ");
        sqlBuilder.append("Id_Original INTEGER NOT NULL, ");
        sqlBuilder.append("CodColetor VARCHAR NOT NULL,");
        sqlBuilder.append("Acao VARCHAR NOT NULL,");
        sqlBuilder.append("DataHoraEvento DATETIME NOT NULL, ");
        sqlBuilder.append("FlagProcess BOOLEAN");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }



    public static String DropTableUPWEBListaTarefasEqReadinessTables(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE IF EXISTS UPWEBListaTarefasEqReadinessTables");
        sqlBuilder.append(";");

        return sqlBuilder.toString();
    }

    public static String DropTableUPWEBListaMateriaisListaTarefaEqReadnesses(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE IF EXISTS UPWEBListaMateriaisListaTarefaEqReadnesses");
        sqlBuilder.append(";");

        return sqlBuilder.toString();
    }

    public static String DropTableUPWEBLista_Serviços_AdicionaisSet(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE IF EXISTS UPWEBLista_Serviços_AdicionaisSet");
        sqlBuilder.append(";");

        return sqlBuilder.toString();
    }

    public static String UpdateUPWEBWorksheets(int oldversion, int newVersion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion != 0 && oldversion < 104){
            sqlBuilder.append("ALTER TABLE UPWEBWorksheet ADD COLUMN Valor_Unitario DECIMAL(18,2);");

        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String UpdateUPMOBWorksheets(int oldversion, int newVersion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion != 0 && oldversion < 104){
            sqlBuilder.append("ALTER TABLE UPMOBWorksheet ADD COLUMN Valor_Unitario DECIMAL(18,2);");

        }else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBProcessoDescarte(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 108) {

            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBProcessoDescarte( ");
            sqlBuilder.append("TAGID VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("DataHoraSolicitacao DATETIME NOT NULL, ");
            sqlBuilder.append("UsuarioSolicitacao VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Motivo VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("Modalidade VARCHAR (255) NOT NULL ");

            sqlBuilder.append(");");
            return sqlBuilder.toString();

        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPMOBListaResultadosServicosSet(int oldversion, int newversion) {
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 109) {
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPMOBListaResultadosServicosSet( ");
            sqlBuilder.append("_Id INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("Id_Original INTEGER  NOT NULL, ");
            sqlBuilder.append("Resultado VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("DataHoraEvento DATETIME NOT NULL, ");
            sqlBuilder.append("IdServicoAdicional VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TAGID_Material VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
            sqlBuilder.append("FlagMobileInsert BOOLEAN, ");
            sqlBuilder.append("Cod_Coletor VARCHAR (255) ");

            sqlBuilder.append(");");
            return sqlBuilder.toString();
        } if (oldversion != 0 && oldversion < 110){
            sqlBuilder.append("ALTER TABLE UPMOBListaResultadosServicosSet ADD COLUMN Cod_Coletor VARCHAR (255);");
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }

    public static String CreateUPWEBListaResultadosServicosSet(int oldversion, int newversion){
        StringBuilder sqlBuilder = new StringBuilder();

        if (oldversion < 111){
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS UPWEBListaResultadosServicosSet( ");
            sqlBuilder.append("Id_Original INTEGER  NOT NULL, ");
            sqlBuilder.append("Resultado VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("TAGID_Material VARCHAR (255) NOT NULL, ");
            sqlBuilder.append("ListaMateriaisListaTarefasEqReadness INTEGER, ");
            sqlBuilder.append("ListaTarefasEqReadinessTableINTEGER INTEGER, ");
            sqlBuilder.append("Cod_Coletor VARCHAR (255), ");
            sqlBuilder.append("FlagMobileUpdate BOOLEAN, ");
            sqlBuilder.append("FlagMobileInsert BOOLEAN ");

            sqlBuilder.append(");");
            return sqlBuilder.toString();
        } else {
            sqlBuilder.append("SELECT 0 WHERE 0");
        }

        return sqlBuilder.toString();
    }
}