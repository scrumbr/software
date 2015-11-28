package br.web.scrumbr.bean;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;
import static br.web.scrumbr.util.Message.setErrorMessage;
import static br.web.scrumbr.util.Message.setWarnMessage;

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
import javax.faces.event.ActionEvent;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.bo.ProductBacklogBo;
import br.web.scrumbr.bo.SprintBo;
import br.web.scrumbr.bo.TarefaBo;
import br.web.scrumbr.constants.StatusTarefa;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;
import br.web.scrumbr.entity.Tarefa;
import br.web.scrumbr.report.GenericReport;
import br.web.scrumbr.util.BuscaNoWebContent;
import br.web.scrumbr.util.DataUtil;
import br.web.scrumbr.util.DiasSprint;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class SprintBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Sprint sprint;
	private Sprint sprintFilter;
	private LazyEntityDataModel<Sprint> lazy;
	private List<Sprint> sprints;
	private ProductBacklog productBacklog;
	private ProductBacklog productBacklogFilter;
	private List<ProductBacklog> productBacklogs;
	private LazyEntityDataModel<ProductBacklog> productBacklogLazy;
	private Projeto projetoSelecionado;
	private List<Sprint> listSprints;
	private UrlBean url;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		sprint = new Sprint();
		sprintFilter = new Sprint();
		lazy = new LazyEntityDataModel<Sprint>(Sprint.class);
		sprints = new ArrayList<Sprint>();
		productBacklog = new ProductBacklog();
		productBacklogFilter = new ProductBacklog();
		productBacklogs = new ArrayList<ProductBacklog>();
		listSprints = new ArrayList<Sprint>();
		productBacklogLazy = new LazyEntityDataModel<ProductBacklog>(
				ProductBacklog.class);
		projetoSelecionado = ManagedBeanHelper.recuperaBean(
				"projetoSessaoBean", ProjetoSessaoBean.class)
				.getProjetoSelecionado();
		listandoSprints();
		createBarModels();
	}

	public String save() {
		try {
			sprint.setProjeto(projetoSelecionado);
			sprint.setProductBacklogs(productBacklogs);
			SprintBo.getInstance().save(sprint);
			for (int i = 0; i < productBacklogs.size(); i++) {
				productBacklogs.get(i).setSprint(sprint);
			}
			if (!productBacklogs.isEmpty()) {
				ProductBacklogBo.getInstance().mergeAll(productBacklogs);
			}
			addInfoMessage("",
					"Cadastrada com sucesso! Sprint " + sprint.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		}
		init();
		return null;
	}

	public String update() {
		try {

			sprint.setProductBacklogs(productBacklogs);
			SprintBo.getInstance().update(sprint);

			for (int i = 0; i < productBacklogs.size(); i++) {
				productBacklogs.get(i).setSprint(sprint);
			}

			if (!productBacklogs.isEmpty()) {
				ProductBacklogBo.getInstance().mergeAll(productBacklogs);
			}
			SprintBo.getInstance().update(sprint);
			addInfoMessage("",
					"Editada com sucesso! Sprint " + sprint.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		}
		init();
		return url.getLIST_SPRINT();
	}

	public String prepareData() {
		try {
			productBacklogs = ProductBacklogBo.getInstance()
					.productBacklogPorSprint(sprint);
			productBacklogLazy = ProductBacklogBo.getInstance()
					.productBacklogLazy(productBacklogs, projetoSelecionado);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			setWarnMessage("", "Atenção!" + e.getMessage());
		}
		return null;
	}

	public String list() {
		lazy = SprintBo.getInstance().sprintsLazy(sprintFilter);
		return null;
	}

	public String goToList() {
		listandoSprints();
		return url.getLIST_SPRINT();
	}

	public String prepareUpdate() {
		prepareData();
		return url.getEDIT_SPRINT();
	}

	public String prepareSave() {
		sprint = new Sprint();
		return url.getCAD_SPRINT();
	}

	public String updateStatus() {
		try {
			if (sprint.getStatus()) {
				sprint.setStatus(false);
				SprintBo.getInstance().update(sprint);
			} else {
				sprint.setStatus(true);
				SprintBo.getInstance().update(sprint);
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

		String status = sprint.getStatus() ? "Ativo" : "Inativo";

		addInfoMessage("Status alterado com sucesso!", "O sprint está "
				+ status);

		return null;
	}

	public String updateTableProductBacklog() {
		// try {
		// productBacklogs = ProductBacklogBo.getInstance()
		// .productBacklogPorSprint(sprint);
		// } catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
		// e.printStackTrace();
		// }
		productBacklogLazy = ProductBacklogBo.getInstance().productBacklogLazy(
				productBacklogs, projetoSelecionado);
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<Sprint> sprint = SprintBo.getInstance().sprintListReport(
					sprintFilter);

			sprintFilter = new Sprint();
			mapa.put("logo", BuscaNoWebContent
					.buscaArquivo("/template/imagens/ScrumBR.png"));
			mapa.put("rodape", BuscaNoWebContent
					.buscaArquivo("/template/imagens/rodape_paisagem.png"));
			mapa.put("filtro", SprintBo.getInstance().dadosFiltro());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							sprint,
							"sprint",
							"Relatório de Sprints "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String removerProductBacklog() {
		try {
			for (int i = 0; i < productBacklogs.size(); i++) {
				if (productBacklogs.get(i) == productBacklog) {
					productBacklogs.remove(i);
					setWarnMessage("", "Atividade removida com sucesso!");
					break;
				}
			}
			productBacklog = new ProductBacklog();
			productBacklogLazy = ProductBacklogBo.getInstance()
					.productBacklogLazy(productBacklogs, projetoSelecionado);
		} catch (Exception e) {
			setErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String filtrarProductBacklog() {
		try {
			productBacklogLazy = ProductBacklogBo.getInstance()
					.productBacklogLazy(productBacklogFilter, productBacklogs,
							projetoSelecionado);
		} catch (Exception e) {
			setErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String addProductBacklog() {
		try {
			productBacklogs.add(productBacklog);
			addInfoMessage("", "Atividade adicionada com sucesso!");
			productBacklog = new ProductBacklog();
			productBacklogLazy = ProductBacklogBo.getInstance()
					.productBacklogLazy(productBacklogs, projetoSelecionado);
		} catch (Exception e) {
			setErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String listandoSprints() {
		try {
			listSprints = SprintBo.getInstance().sprintByProjetoSelecionado(
					"list_sprint", sprintFilter, projetoSelecionado);
		} catch (Exception e) {
			addErrorMessage("", "Erro ao listar " + e.getMessage());
		}
		return null;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public LazyEntityDataModel<Sprint> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Sprint> lazy) {
		this.lazy = lazy;
	}

	public List<Sprint> getSprints() {
		try {
			sprints = SprintBo.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return sprints;
	}

	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}

	public Sprint getSprintFilter() {
		return sprintFilter;
	}

	public void setSprintFilter(Sprint sprintFilter) {
		this.sprintFilter = sprintFilter;
	}

	public ProductBacklog getProductBacklog() {
		return productBacklog;
	}

	public void setProductBacklog(ProductBacklog productBacklog) {
		this.productBacklog = productBacklog;
	}

	public List<ProductBacklog> getProductBacklogs() {
		return productBacklogs;
	}

	public void setProductBacklogs(List<ProductBacklog> productBacklogs) {
		this.productBacklogs = productBacklogs;
	}

	public LazyEntityDataModel<ProductBacklog> getProductBacklogLazy() {
		return productBacklogLazy;
	}

	public void setProductBacklogLazy(
			LazyEntityDataModel<ProductBacklog> productBacklogLazy) {
		this.productBacklogLazy = productBacklogLazy;
	}

	public ProductBacklog getProductBacklogFilter() {
		return productBacklogFilter;
	}

	public void setProductBacklogFilter(ProductBacklog productBacklogFilter) {
		this.productBacklogFilter = productBacklogFilter;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public List<Sprint> getListSprints() {
		return listSprints;
	}

	public void setListSprints(List<Sprint> listSprints) {
		this.listSprints = listSprints;
	}

	private BarChartModel barModel;

	public BarChartModel getBarModel() {
		return barModel;
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries boys = new ChartSeries();
		boys.setLabel("Status Sprint");
		Double horaTotal = 0.0;
		Double horaConcluida = 0.0;
		Double resultado = 0.0;
		List<Tarefa> listTarefas;

		try {
			for (int i = 0; i < listSprints.size(); i++) {
				listTarefas = TarefaBo.getInstance().tarefasPorSprint(
						"list_tarefas_sprint", listSprints.get(i));
				if (listTarefas.size() != 0) {
					for (int t = 0; t < listTarefas.size(); t++) {
						if (listTarefas.get(t).getStatusTarefa()
								.equals(StatusTarefa.CONCLUIDA)) {
							horaConcluida += listTarefas.get(t)
									.getTempoAtividade();
						}
						horaTotal += listTarefas.get(t).getTempoAtividade();
					}
					resultado = (horaConcluida * 100) / horaTotal;
				}
				boys.set(listSprints.get(i).getNome(), resultado);
				horaTotal = 0.0;
				horaConcluida = 0.0;
				resultado = 0.0;
			}
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (NumeroAtributosDiferenteNumeroValoresException e) {
			e.printStackTrace();
		}

		model.addSeries(boys);

		return model;
	}

	private void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Bar Chart");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Sprints");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Porcentagem");
		yAxis.setMin(0);
		yAxis.setMax(100);
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public void setarDataFinal() {

		Calendar c = Calendar.getInstance();
		c.setTime(sprint.getDataInicio());

		Date dataAux = new Date();
		dataAux = new Sprint().somarDiasParaFimSprint(sprint.getQtdDias());
		sprint.setDataFim(dataAux);

		DateTime dateInicio = new DateTime(sprint.getDataInicio());
		DateTime dateFim = new DateTime(sprint.getDataFim());
		DiasSprint.diasUteis(dateInicio, dateFim);
	}

	public List<LocalDate> retornaDatasNaoUteis() {
		DateTime dateInicio = new DateTime(sprint.getDataInicio());
		DateTime dateFim = new DateTime(sprint.getDataFim());
		return DiasSprint.diasUteis(dateInicio, dateFim);
	}

}
