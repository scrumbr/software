package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.ProductBacklogDao;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;

public class ProductBacklogBo {

	private static ProductBacklogBo singleton;

	public static synchronized ProductBacklogBo getInstance() {
		if (singleton == null) {
			singleton = new ProductBacklogBo();
		}
		return singleton;
	}

	public void save(ProductBacklog productBacklog) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(productBacklog);
		GenericDao.save(productBacklog);
	}

	public void update(ProductBacklog productBacklog) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(productBacklog);
		GenericDao.update(productBacklog);
	}

	public ProductBacklog find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ProductBacklog.class, id);
	}

	public LazyEntityDataModel<ProductBacklog> productBacklogsLazy(ProductBacklog productBacklog) {
		return ProductBacklogDao.getInstance().productBacklogsLazy(productBacklog);
	}
	
	public LazyEntityDataModel<ProductBacklog> productBacklogLazy(ProductBacklog productBacklogFilter, List<ProductBacklog> productBacklogs, Projeto projetoSelecionado) {
		return ProductBacklogDao.getInstance().productBacklogsLazy(productBacklogFilter, productBacklogs, projetoSelecionado);
	}
	
	public List<ProductBacklog> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProductBacklogDao.getInstance().productBacklogs();
	}
	
	public LazyEntityDataModel<ProductBacklog> productBacklogLazy(List<ProductBacklog> productBacklogs, Projeto projetoSelecionado) {
		return ProductBacklogDao.getInstance().productBacklogLazy(productBacklogs, projetoSelecionado);
	}
	
	public LazyEntityDataModel<ProductBacklog> productBacklogLazyPorProjeto(Projeto projetoSelecionado) {
		return ProductBacklogDao.getInstance().productBacklogLazyProProjeto(projetoSelecionado);
	}
	
	public List<ProductBacklog> productBacklogByProjetoSelecionado(String namedQuery,
			ProductBacklog productBacklogFilter, Projeto projetoSelecionado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {
		return ProductBacklogDao.getInstance().productBacklogByProjetoSelecionado(namedQuery,
				productBacklogFilter, projetoSelecionado);
	}
	
	public void mergeAll(List<ProductBacklog> productBacklogs) throws ViolacaoChaveHibernateException, ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(productBacklogs);
	}
	
	public List<ProductBacklog> productBacklogPorSprint(Sprint sprint) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ProductBacklogDao.getInstance().productBacklogPorSprint(sprint);
	}
	
	public StringBuilder dadosFiltro(){
		return ProductBacklogDao.getInstance().getDadosFiltro();
	}

	public List<ProductBacklog>productBacklogListReport(ProductBacklog productBacklog) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ProductBacklogDao.getInstance().productBacklogListReport(productBacklog);
	}
	
	public void validation(ProductBacklog productBacklog) {

	}
}
