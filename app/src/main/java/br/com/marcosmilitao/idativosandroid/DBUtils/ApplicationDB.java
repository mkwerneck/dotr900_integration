package br.com.marcosmilitao.idativosandroid.DBUtils;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.AlmoxarifadosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroEquipamentosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.CadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.GruposDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.InventarioPlanejadoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaMateriaisInventarioPlanejadoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaMateriaisListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaServicosListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloEquipamentosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ModeloMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ParametrosPadraoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.PosicoesDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ProcessosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ProprietariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.ServicosAdicionaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.TarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBCadastroMateriaisItensDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBDescartesDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBHistoricoLocalizacaoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaMateriaisDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaServicosListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBListaTarefasDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBProcessoDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UPMOBUsuariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.DAO.UsuariosDAO;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Almoxarifados;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroEquipamentos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Grupos;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.InventarioPlanejado;
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
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaServicosListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBListaTarefas;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBProcesso;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.UPMOBUsuarios;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.Usuarios;

@Database(entities = {CadastroEquipamentos.class,
        ModeloEquipamentos.class,
        Posicoes.class,
        Almoxarifados.class,
        ServicosAdicionais.class,
        Tarefas.class,
        Proprietarios.class,
        ModeloMateriais.class,
        InventarioPlanejado.class,
        ListaMateriaisInventarioPlanejado.class,
        ListaTarefas.class,
        ListaServicosListaTarefas.class,
        ListaMateriaisListaTarefas.class,
        Processos.class,
        Grupos.class,
        CadastroMateriaisItens.class,
        CadastroMateriais.class,
        Usuarios.class,
        ParametrosPadrao.class,
        UPMOBUsuarios.class,
        UPMOBProcesso.class,
        UPMOBListaServicosListaTarefas.class,
        UPMOBListaMateriais.class,
        UPMOBListaTarefas.class,
        UPMOBHistoricoLocalizacao.class,
        UPMOBCadastroMateriaisItens.class,
        UPMOBCadastroMateriais.class,
        UPMOBDescartes.class}, version = 2)

public abstract class ApplicationDB extends RoomDatabase {

    public abstract CadastroEquipamentosDAO cadastroEquipamentosDAO();
    public abstract ModeloEquipamentosDAO modeloEquipamentosDAO();
    public abstract PosicoesDAO posicoesDAO();
    public abstract AlmoxarifadosDAO almoxarifadosDAO();
    public abstract ServicosAdicionaisDAO servicosAdicionaisDAO();
    public abstract TarefasDAO tarefasDAO();
    public abstract ProprietariosDAO proprietariosDAO();
    public abstract ModeloMateriaisDAO modeloMateriaisDAO();
    public abstract InventarioPlanejadoDAO inventarioPlanejadoDAO();
    public abstract ProcessosDAO processosDAO();
    public abstract ListaMateriaisInventarioPlanejadoDAO listaMateriaisInventarioPlanejadoDAO();
    public abstract ListaTarefasDAO listaTarefasDAO();
    public abstract ListaServicosListaTarefasDAO listaServicosListaTarefasDAO();
    public abstract ListaMateriaisListaTarefasDAO listaMateriaisListaTarefasDAO();
    public abstract GruposDAO gruposDAO();
    public abstract CadastroMateriaisItensDAO cadastroMateriaisItensDAO();
    public abstract CadastroMateriaisDAO cadastroMateriaisDAO();
    public abstract UsuariosDAO usuariosDAO();
    public abstract ParametrosPadraoDAO parametrosPadraoDAO();
    public abstract UPMOBUsuariosDAO upmobUsuariosDAO();
    public abstract UPMOBListaServicosListaTarefasDAO upmobListaServicosListaTarefasDAO();
    public abstract UPMOBListaMateriaisDAO upmobListaMateriaisDAO();
    public abstract UPMOBListaTarefasDAO upmobListaTarefasDAO();
    public abstract UPMOBProcessoDAO upmobProcessoDAO();
    public abstract UPMOBHistoricoLocalizacaoDAO upmobHistoricoLocalizacaoDAO();
    public abstract UPMOBCadastroMateriaisItensDAO upmobCadastroMateriaisItensDAO();
    public abstract UPMOBCadastroMateriaisDAO upmobCadastroMateriaisDAO();
    public abstract UPMOBDescartesDAO upmobDescartesDAO();

}


