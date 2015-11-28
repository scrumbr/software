package br.web.scrumbr.bean;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.acesso.EscopoSessaoBean;
import br.web.scrumbr.bo.HistoricoAlteracaoTarefaBo;
import br.web.scrumbr.bo.TarefaBo;
import br.web.scrumbr.constants.StatusTarefa;
import br.web.scrumbr.constants.StatusTarefaAlteracao;
import br.web.scrumbr.entity.HistoricoAlteracaoTarefa;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;
import br.web.scrumbr.entity.Tarefa;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class TarefaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private final String CAD = "/private/cadastro/cadastrarTarefa.xhtml";
	private final String EDIT = "/pages/private/cadastro/cadastrarTarefa.xhtml";
	private final String LIST = "/pages/private/lista/listarTarefa.xhtml";

	private Tarefa tarefa;
	private Tarefa tarefaFilter;
	private LazyEntityDataModel<Tarefa> lazy;
	private List<Tarefa> tarefas;
	private List<Tarefa> tarefasAfazer;
	private List<Tarefa> tarefasAndamento;
	private List<Tarefa> tarefasConcluida;
	private Sprint sprintFilter;
	private UrlBean url;
	private Projeto projetoSelecionado;
	private HistoricoAlteracaoTarefa historicoTarefa;
	private Usuario usuarioLogado;
	private PieChartModel pieModel1;
	private int qtdAfazer;
	private int qtdConcluida;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		pieModel1 = new PieChartModel();
		sprintFilter = new Sprint();
		tarefa = new Tarefa();
		tarefaFilter = new Tarefa();
		lazy = new LazyEntityDataModel<Tarefa>(Tarefa.class);
		tarefas = new ArrayList<Tarefa>();
		tarefasAfazer = new ArrayList<Tarefa>();
		tarefasAndamento = new ArrayList<Tarefa>();
		tarefasConcluida = new ArrayList<Tarefa>();
		historicoTarefa = new HistoricoAlteracaoTarefa();
		projetoSelecionado = ManagedBeanHelper.recuperaBean(
				"projetoSessaoBean", ProjetoSessaoBean.class)
				.getProjetoSelecionado();
		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean",
				EscopoSessaoBean.class).getUsuarioLogado();
		createPieModel1();
	}

	public void save() {
		try {
			TarefaBo.getInstance().save(tarefa);
			addInfoMessage("",
					"Cadastrada com sucesso! Tarefa " + tarefa.getNome());
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
		tarefa = new Tarefa();
	}

	public String update() {
		try {
			TarefaBo.getInstance().update(tarefa);
			setarHistorico();
			HistoricoAlteracaoTarefaBo.getInstance().save(historicoTarefa);
			addInfoMessage("",
					"Editada com sucesso! Tarefa " + tarefa.getNome());
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
		tarefa = new Tarefa();
		listandoTarefas();
		return null;
	}

	public String list() {
		lazy = TarefaBo.getInstance().tarefasLazy(tarefaFilter);
		return null;
	}

	public String listandoTarefas() {
		try {
			tarefasAfazer = new ArrayList<Tarefa>();
			tarefasAndamento = new ArrayList<Tarefa>();
			tarefasConcluida = new ArrayList<Tarefa>();
			
			tarefas = TarefaBo.getInstance().tarefasPorSprint(
					"list_tarefas_sprint", sprintFilter);

			for (int i = 0; i < tarefas.size(); i++) {
				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.A_FAZER) {
					tarefasAfazer.add(tarefas.get(i));
				}
				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.ANDAMENTO) {
					tarefasAndamento.add(tarefas.get(i));
				}
				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.CONCLUIDA) {
					tarefasConcluida.add(tarefas.get(i));
				}
			}
			
			qtdAfazer = tarefas.size();
			qtdConcluida = tarefasConcluida.size();
			
		} catch (Exception e) {
			addErrorMessage("", "Erro ao listar " + e.getMessage());
		}
		return null;
	}

	public String createPieModel1() {

//		try {
//			tarefas = TarefaBo.getInstance().tarefasPorSprint(
//					"list_tarefas_sprint", sprintFilter);
//			for (int i = 0; i < tarefas.size(); i++) {
//				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.A_FAZER) {
//					tarefasAfazer.add(tarefas.get(i));
//					System.out.println();
//				}
//				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.ANDAMENTO) {
//					tarefasAndamento.add(tarefas.get(i));
//				}
//				if (tarefas.get(i).getStatusTarefa() == StatusTarefa.CONCLUIDA) {
//					tarefasConcluida.add(tarefas.get(i));
//				}
//			}
//			for (int i = 0; i < tarefasAfazer.size(); i++) {
//				System.out.println(tarefasAfazer.get(i).getNome());
//			}
//			for (int i = 0; i < tarefasAndamento.size(); i++) {
//				System.out.println(tarefasAndamento.get(i).getNome());
//			}
//			for (int i = 0; i < tarefasConcluida.size(); i++) {
//				System.out.println(tarefasConcluida.get(i).getNome());
//			}
//
//		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
//			e.printStackTrace();
//		} catch (NumeroAtributosDiferenteNumeroValoresException e) {
//			e.printStackTrace();
//		}
		

		pieModel1.set("Á fazer", 60);
		pieModel1.set("Andamento", 30);
		pieModel1.set("Concluída", 10);

		pieModel1.setTitle("Sprint 001");
		pieModel1.setLegendPosition("w");
		pieModel1.setShowDataLabels(true);

		return null;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public StatusTarefa[] getStatusTarefas() {
		return StatusTarefa.values();
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return EDIT;
	}

	public String prepareSave() {
		tarefa = new Tarefa();
		return CAD;
	}

	public String updateStatus() {
		try {

			if (tarefa.getStatus()) {
				tarefa.setStatus(false);
				TarefaBo.getInstance().update(tarefa);
			} else {
				tarefa.setStatus(true);
				TarefaBo.getInstance().update(tarefa);
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

		String status = tarefa.getStatus() ? "Ativo" : "Inativo";

		addInfoMessage("Status alterado com sucesso!", "O tarefa está "
				+ status);

		return null;
	}

	public Double total() {
		Double horaTotal = 0.0;
		Double horaConcluida = 0.0;
		Double resultado = 0.0;

		try {
			List<Tarefa> listTarefas = TarefaBo.getInstance().total(
					projetoSelecionado);

			for (int i = 0; i < listTarefas.size(); i++) {
				if (listTarefas.get(i).getStatusTarefa()
						.equals(StatusTarefa.CONCLUIDA)) {
					horaConcluida += listTarefas.get(i).getTempoAtividade();
				}
				horaTotal += listTarefas.get(i).getTempoAtividade();
			}
			resultado = (horaConcluida * 100) / horaTotal;
			return resultado;
		} catch (NumeroAtributosDiferenteNumeroValoresException e) {
			e.printStackTrace();
			return 0.0;
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	public void setarHistorico() {
		historicoTarefa.setDataAlteracao(new Date());
		historicoTarefa.setUsuario(usuarioLogado);
		historicoTarefa.setTarefa(tarefa);
		historicoTarefa.setProjeto(projetoSelecionado);
		if (tarefa.getStatusTarefa().equals(StatusTarefa.ANDAMENTO)) {
			historicoTarefa
					.setStatusTarefaAlteracao(StatusTarefaAlteracao.AFAZER_ANDAMENTO);
		} else if (tarefa.getStatusTarefa().equals(StatusTarefa.CONCLUIDA)) {
			historicoTarefa
					.setStatusTarefaAlteracao(StatusTarefaAlteracao.ANDAMENTO_CONCLUIDA);
		}
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public LazyEntityDataModel<Tarefa> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Tarefa> lazy) {
		this.lazy = lazy;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public List<Tarefa> getTarefasAfazer() {
		return tarefasAfazer;
	}

	public void setTarefasAfazer(List<Tarefa> tarefasAfazer) {
		this.tarefasAfazer = tarefasAfazer;
	}

	public List<Tarefa> getTarefasAndamento() {
		return tarefasAndamento;
	}

	public void setTarefasAndamento(List<Tarefa> tarefasAndamento) {
		this.tarefasAndamento = tarefasAndamento;
	}

	public List<Tarefa> getTarefasConcluida() {
		return tarefasConcluida;
	}

	public void setTarefasConcluida(List<Tarefa> tarefasConcluida) {
		this.tarefasConcluida = tarefasConcluida;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Tarefa getTarefaFilter() {
		return tarefaFilter;
	}

	public void setTarefaFilter(Tarefa tarefaFilter) {
		this.tarefaFilter = tarefaFilter;
	}

	public String getCAD() {
		return CAD;
	}

	public String getEDIT() {
		return EDIT;
	}

	public String getLIST() {
		return LIST;
	}

	public Sprint getSprintFilter() {
		return sprintFilter;
	}

	public void setSprintFilter(Sprint sprintFilter) {
		this.sprintFilter = sprintFilter;
	}

	public UrlBean getUrl() {
		return url;
	}

	public void setUrl(UrlBean url) {
		this.url = url;
	}

	public int getQtdAfazer() {
		return qtdAfazer;
	}

	public void setQtdAfazer(int qtdAfazer) {
		this.qtdAfazer = qtdAfazer;
	}

	public int getQtdConcluida() {
		return qtdConcluida;
	}

	public void setQtdConcluida(int qtdConcluida) {
		this.qtdConcluida = qtdConcluida;
	}
	
	

}
