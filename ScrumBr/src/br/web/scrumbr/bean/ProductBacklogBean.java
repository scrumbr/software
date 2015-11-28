package br.web.scrumbr.bean;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;
import static br.web.scrumbr.util.Message.setWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.bo.ProductBacklogBo;
import br.web.scrumbr.bo.TarefaBo;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Tarefa;
import br.web.scrumbr.report.GenericReport;
import br.web.scrumbr.util.BuscaNoWebContent;
import br.web.scrumbr.util.DataUtil;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class ProductBacklogBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private ProductBacklog productBacklog;
	private ProductBacklog productBacklogFilter;
	private LazyEntityDataModel<ProductBacklog> lazy;
	private List<ProductBacklog> productBacklogs;
	private List<ProductBacklog> listProductBacklogs;
	private List<Tarefa> tarefas;
	private Tarefa tarefa;
	private Projeto projetoSelecionado;
	private UrlBean url;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		productBacklog = new ProductBacklog();
		productBacklogFilter = new ProductBacklog();
		tarefa = new Tarefa();
		lazy = new LazyEntityDataModel<ProductBacklog>(ProductBacklog.class);
		productBacklogs = new ArrayList<ProductBacklog>();
		listProductBacklogs = new ArrayList<ProductBacklog>();
		tarefas = new ArrayList<Tarefa>();
		projetoSelecionado = ManagedBeanHelper.recuperaBean("projetoSessaoBean",
				ProjetoSessaoBean.class).getProjetoSelecionado();
		listandoProductBacklogs();
	}

	public String save() {
		try {
			productBacklog.setProjeto(projetoSelecionado);
			productBacklog.setTarefas(tarefas);
				
			ProductBacklogBo.getInstance().save(productBacklog);

			for (int i = 0; i < tarefas.size(); i++) {
				tarefas.get(i).setProductBacklog(productBacklog);
			}

			if (!tarefas.isEmpty()) {
				TarefaBo.getInstance().mergeAll(tarefas);
			}

			addInfoMessage("", "Cadastrada com sucesso! ProductBacklog "
					+ productBacklog.getNome());
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
			productBacklog.setTarefas(tarefas);
			ProductBacklogBo.getInstance().update(productBacklog);

			for (int i = 0; i < tarefas.size(); i++) {
				tarefas.get(i).setProductBacklog(productBacklog);
			}

			if (!tarefas.isEmpty()) {
				TarefaBo.getInstance().mergeAll(tarefas);
			}

			addInfoMessage("", "Editada com sucesso! ProductBacklog "
					+ productBacklog.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente", "");
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
		
		return url.getLIST_PRODUCT_BACKLOG();
	}
	
	public void gerarPDF(ActionEvent ev){
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();
			
			
			List<ProductBacklog> procutoBacklog = ProductBacklogBo.getInstance().productBacklogListReport(productBacklogFilter);
			
			
			productBacklogFilter= new ProductBacklog();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/template/imagens/ScrumBR.png"));
			mapa.put("rodape", BuscaNoWebContent.buscaArquivo("/template/imagens/rodape_paisagem.png"));
			mapa.put("filtro",ProductBacklogBo.getInstance().dadosFiltro());
			
			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, procutoBacklog, "productBacklog", "Relatório de eventos "  + DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String list() {
		lazy = ProductBacklogBo.getInstance().productBacklogsLazy(
				productBacklogFilter);
		return null;
	}

	public String goToList() {
		return url.getLIST_PRODUCT_BACKLOG();
	}

	public String prepareUpdate() {
		prepareData();
		return url.getEDIT_PRODUCT_BACKLOG();
	}

	public String prepareSave() {
		productBacklog = new ProductBacklog();
		return url.getCAD_PRODUCT_BACKLOG();
	}

	public String updateStatus() {
		try {
			if (productBacklog.getStatus()) {
				productBacklog.setStatus(false);
				ProductBacklogBo.getInstance().update(productBacklog);
			} else {
				productBacklog.setStatus(true);
				ProductBacklogBo.getInstance().update(productBacklog);
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

		String status = productBacklog.getStatus() ? "Ativo" : "Inativo";

		addInfoMessage("Status alterado com sucesso!", "O productBacklog está "
				+ status);

		return null;
	}

	public String removerTarefa() {
		for (int i = 0; i < tarefas.size(); i++) {
			if (tarefas.get(i) == tarefa) {
				tarefas.remove(i);
				setWarnMessage("", "Metodologia removida com sucesso!");
				break;
			}
		}
		tarefa = new Tarefa();
		return null;
	}

	public String deleteTarefa() {
		try {
			TarefaBo.getInstance().delete(tarefa);
			tarefas = TarefaBo.getInstance().tarefasPorProductBacklog(
					productBacklog);
			addInfoMessage("", "Deletado com sucesso!");
		} catch (IntegridadeObjetoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("", "Erro!" + e.getMessage());
			e.printStackTrace();
		}

		return url.getCAD_PRODUCT_BACKLOG();
	}

	public String saveTarefa() {
		try {
			tarefas.add(tarefa);
			addInfoMessage("", "Tarefa adicionada com sucesso!");
			tarefa = new Tarefa();
		} catch (Exception e) {
			setWarnMessage("", "Atenção" + e.getMessage());
		}
		return null;
	}

	public String salvarTarefaNoBanco() {
		try {
			tarefa.setProductBacklog(productBacklog);
			new TarefaBo().save(tarefa);
			tarefas.add(tarefa);
			addInfoMessage("", "Cadastrado com sucesso!");
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String prepareData() {

		try {
			tarefas = TarefaBo.getInstance().tarefasPorProductBacklog(productBacklog);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			setWarnMessage("", "Atenção!" + e.getMessage());
		}
		return null;
	}


	public ProductBacklog getProductBacklog() {
		return productBacklog;
	}

	public void setProductBacklog(ProductBacklog productBacklog) {
		this.productBacklog = productBacklog;
	}

	public LazyEntityDataModel<ProductBacklog> getLazy() {
//		lazy = ProductBacklogBo.getInstance().productBacklogLazyPorProjeto(projetoSelecionado);
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<ProductBacklog> lazy) {
		this.lazy = lazy;
	}

	public List<ProductBacklog> getProductBacklogs() {
		try {
			productBacklogs = ProductBacklogBo.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return productBacklogs;
	}

	public String listandoProductBacklogs() {
		try {
			listProductBacklogs = ProductBacklogBo.getInstance().productBacklogByProjetoSelecionado(
					"list_product_backlog", productBacklogFilter, projetoSelecionado);
		} catch (Exception e) {
			addErrorMessage("", "Erro ao listar " + e.getMessage());
		}
		return null;
	}
	
	public void setProductBacklogs(List<ProductBacklog> productBacklogs) {
		this.productBacklogs = productBacklogs;
	}

	public ProductBacklog getProductBacklogFilter() {
		return productBacklogFilter;
	}

	public void setProductBacklogFilter(ProductBacklog productBacklogFilter) {
		this.productBacklogFilter = productBacklogFilter;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public List<ProductBacklog> getListProductBacklogs() {
		return listProductBacklogs;
	}

	public void setListProductBacklogs(List<ProductBacklog> listProductBacklogs) {
		this.listProductBacklogs = listProductBacklogs;
	}
	
}
