package br.web.scrumbr.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.acesso.EscopoSessaoBean;
import br.web.scrumbr.bo.HistoricoAlteracaoTarefaBo;
import br.web.scrumbr.entity.HistoricoAlteracaoTarefa;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class HistoricoAlteracaoTarefaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private HistoricoAlteracaoTarefa historicoAlteracaoTarefa;
	private HistoricoAlteracaoTarefa historicoAlteracaoTarefaFilter;
	private LazyEntityDataModel<HistoricoAlteracaoTarefa> lazy;
	private List<HistoricoAlteracaoTarefa> historicoAlteracaoTarefas;
	private Usuario usuarioLogado;
	private UrlBean url;
	private Projeto projetoSelecionado;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		historicoAlteracaoTarefa = new HistoricoAlteracaoTarefa();
		historicoAlteracaoTarefaFilter = new HistoricoAlteracaoTarefa();
		lazy = new LazyEntityDataModel<HistoricoAlteracaoTarefa>(HistoricoAlteracaoTarefa.class);
		historicoAlteracaoTarefas = new ArrayList<HistoricoAlteracaoTarefa>();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean",
				EscopoSessaoBean.class).getUsuarioLogado();
		
		projetoSelecionado = ManagedBeanHelper.recuperaBean("projetoSessaoBean",
				ProjetoSessaoBean.class).getProjetoSelecionado();
	}


	public String list() {
		lazy = HistoricoAlteracaoTarefaBo.getInstance().historicoAlteracaoTarefasLazy(historicoAlteracaoTarefaFilter, projetoSelecionado);
		return null;
	}

	public String goToList() {
		return url.getLIST_REUNIAO_PE();
	}

	public String prepareUpdate() {
		return url.getEDIT_REUNIAO_PE();
	}

	public String prepareSave() {
		historicoAlteracaoTarefa = new HistoricoAlteracaoTarefa();
		return url.getCAD_REUNIAO_PE();
	}

	public HistoricoAlteracaoTarefa gethistoricoAlteracaoTarefa() {
		return historicoAlteracaoTarefa;
	}

	public void sethistoricoAlteracaoTarefa(HistoricoAlteracaoTarefa historicoAlteracaoTarefa) {
		this.historicoAlteracaoTarefa = historicoAlteracaoTarefa;
	}

	public LazyEntityDataModel<HistoricoAlteracaoTarefa> getLazy() {
		lazy = HistoricoAlteracaoTarefaBo.getInstance().historicoTarefasLazy(projetoSelecionado);
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<HistoricoAlteracaoTarefa> lazy) {
		this.lazy = lazy;
	}

	public void setHistoricoAlteracaoTarefas(List<HistoricoAlteracaoTarefa> historicoAlteracaoTarefas) {
		this.historicoAlteracaoTarefas = historicoAlteracaoTarefas;
	}

	public HistoricoAlteracaoTarefa getHistoricoAlteracaoTarefaFilter() {
		return historicoAlteracaoTarefaFilter;
	}


	public void setHistoricoAlteracaoTarefa(HistoricoAlteracaoTarefa historicoAlteracaoTarefa) {
		this.historicoAlteracaoTarefa = historicoAlteracaoTarefa;
	}

	public void setHistoricoAlteracaoTarefaFilter(HistoricoAlteracaoTarefa historicoAlteracaoTarefaFilter) {
		this.historicoAlteracaoTarefaFilter = historicoAlteracaoTarefaFilter;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UrlBean getUrl() {
		return url;
	}

	public void setUrl(UrlBean url) {
		this.url = url;
	}

	public HistoricoAlteracaoTarefa getHistoricoAlteracaoTarefa() {
		return historicoAlteracaoTarefa;
	}

	public List<HistoricoAlteracaoTarefa> getHistoricoAlteracaoTarefas() {
		return historicoAlteracaoTarefas;
	}

}
