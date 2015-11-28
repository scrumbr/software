package br.web.scrumbr.bean;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;
import static br.web.scrumbr.util.Message.setErrorMessage;
import static br.web.scrumbr.util.Message.setWarnMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.acesso.EscopoSessaoBean;
import br.web.scrumbr.bo.ProjetoBo;
import br.web.scrumbr.bo.SprintBo;
import br.web.scrumbr.bo.UsuarioBo;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.report.GenericReport;
import br.web.scrumbr.util.BuscaNoWebContent;
import br.web.scrumbr.util.DataUtil;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Projeto projeto;
	private Projeto projetoFilter;
	private Projeto projetoMenu;
	private LazyEntityDataModel<Projeto> lazy;
	private List<Projeto> projetos;
	private List<Projeto> projetosMenu;
	private Usuario usuarioLogado;
	private List<Usuario> participantes;
	private LazyEntityDataModel<Usuario> participantesLazy;
	private Usuario participante;
	private Usuario participanteFilter;
	private UrlBean url;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		projeto = new Projeto();
		projetoFilter = new Projeto();
		lazy = new LazyEntityDataModel<Projeto>(Projeto.class);
		projetos = new ArrayList<Projeto>();
		projetosMenu = new ArrayList<Projeto>();

		participantes = new ArrayList<Usuario>();
		participantesLazy = new LazyEntityDataModel<Usuario>(Usuario.class);
		participante = new Usuario();
		participanteFilter = new Usuario();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean",
				EscopoSessaoBean.class).getUsuarioLogado();
	}

	public void save() {
		try {
			projeto.setParticipantes(participantes);
			projeto.setEmpresa(usuarioLogado.getEmpresa());
			ProjetoBo.getInstance().save(projeto);
			addInfoMessage("",
					"Cadastrado com sucesso! Projeto " + projeto.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		projeto = new Projeto();
	}

	public String update() {
		try {
			projeto.setParticipantes(participantes);
			ProjetoBo.getInstance().update(projeto);
			addInfoMessage("",
					"Editado com sucesso! Projeto " + projeto.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente", "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		projeto = new Projeto();
		return url.getLIST_PROJETO();
	}

	public String list() {
		lazy = ProjetoBo.getInstance().projetosLazy(projetoFilter);
		return null;
	}

	public String goToList() {
		return url.getLIST_PROJETO();
	}

	public String prepareUpdate() {
		participantes.addAll(projeto.getParticipantes());
		return url.getEDIT_PROJETO();
	}

	public String prepareSave() {
		projeto = new Projeto();
		return url.getCAD_PROJETO();
	}

	public String updateStatus() {
		try {

			if (projeto.getStatus()) {
				projeto.setStatus(false);
				ProjetoBo.getInstance().update(projeto);
			} else {
				projeto.setStatus(true);
				ProjetoBo.getInstance().update(projeto);
			}

		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}

		String status = projeto.getStatus() ? "Ativo" : "Inativo";

		addInfoMessage("Status alterado com sucesso!", "O projeto está "
				+ status);

		return null;
	}
	
	public String listandoProjetos() {
		try {
		projetos = ProjetoBo.getInstance().projetosUserLogado("list_projeto_user",usuarioLogado);
		} catch (Exception e) {
			addErrorMessage("", "Erro ao listar " + e.getMessage());
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev){
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();
			
			
			List<Projeto> projeto = ProjetoBo.getInstance().projetoListReport(projetoFilter);
			
			
			projetoFilter= new Projeto();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/template/imagens/ScrumBR.png"));
			mapa.put("rodape", BuscaNoWebContent.buscaArquivo("/template/imagens/rodape_paisagem.png"));
			mapa.put("filtro",SprintBo.getInstance().dadosFiltro());
			
			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, projeto, "projeto", "Relatório de Projetos "  + DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String updateTableParticipantes() {
		participantes = projeto.getParticipantes();
		participantesLazy = UsuarioBo.getInstance().usuariosLazy(participantes);
		return null;
	}

	public String removerParticipante() {
		try {

			for (int i = 0; i < participantes.size(); i++) {
				if (participantes.get(i) == participante) {
					participantes.remove(i);
					setWarnMessage("", "Participante removido com sucesso!");
					break;
				}
			}
			participante = new Usuario();
			participantesLazy = UsuarioBo.getInstance().usuariosLazy(
					participantes);
		} catch (Exception e) {
			setErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String addParticipante() {
		try {
			participantes.add(participante);
			addInfoMessage("Participante adicionado com sucesso!", "");
			participante = new Usuario();
			participantesLazy = UsuarioBo.getInstance().usuariosLazy(
					participantes);
		} catch (Exception e) {
			setErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String listFilter() {
		participantesLazy = UsuarioBo.getInstance().usuariosLazy(
				participanteFilter);
		return null;
	}
	
	public void verificaDatas() {

		Date dataTermino = projeto.getPrevisaoTermino();
		Calendar c = Calendar.getInstance();
		c.setTime(dataTermino);
		c.add(Calendar.DATE, 1);
		dataTermino = c.getTime();

		if (projeto.getDataInicio().after(dataTermino)) {
			setWarnMessage("Atenção!",
					"A data de previsão de término não pode ser menor que a data de inicio!");
		}
	}

	public String setarProjetoSessao() {
			
			try {
				ManagedBeanHelper.recuperaBean("projetoSessaoBean",
						ProjetoSessaoBean.class).setProjetoSelecionado(projetoMenu);
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ScrumBr"+url.getPAG_INICIO());
			} catch (IOException e) {
				addErrorMessage("", "Erro ao direcionar para a pagina!" + e.getMessage());
				e.printStackTrace();
			}
		return url.getPAG_INICIO();
	}

	public Projeto getprojeto() {
		return projeto;
	}

	public void setprojeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public LazyEntityDataModel<Projeto> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Projeto> lazy) {
		this.lazy = lazy;
	}

	public List<Projeto> getProjetos() {
		try {
			projetos = ProjetoBo.getInstance().list(usuarioLogado);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Projeto getProjetoFilter() {
		return projetoFilter;
	}


	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setProjetoFilter(Projeto projetoFilter) {
		this.projetoFilter = projetoFilter;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
		this.participantes = participantes;
	}

	public LazyEntityDataModel<Usuario> getParticipantesLazy() {
		return participantesLazy;
	}

	public void setParticipantesLazy(
			LazyEntityDataModel<Usuario> participantesLazy) {
		this.participantesLazy = participantesLazy;
	}

	public Usuario getParticipante() {
		return participante;
	}

	public void setParticipante(Usuario participante) {
		this.participante = participante;
	}

	public Usuario getParticipanteFilter() {
		return participanteFilter;
	}

	public void setParticipanteFilter(Usuario participanteFilter) {
		this.participanteFilter = participanteFilter;
	}

	public List<Projeto> getProjetosMenu() {
		try {
			projetosMenu = ProjetoBo.getInstance().projetosUserLogado("list_projeto_user",usuarioLogado);
			} catch (Exception e) {
				addErrorMessage("", "Erro ao listar " + e.getMessage());
			}
		return projetosMenu;
	}

	public void setProjetosMenu(List<Projeto> projetosMenu) {
		this.projetosMenu = projetosMenu;
	}

	public Projeto getProjetoMenu() {
		return projetoMenu;
	}

	public void setProjetoMenu(Projeto projetoMenu) {
		this.projetoMenu = projetoMenu;
	}

}
