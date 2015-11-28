package br.web.scrumbr.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;

public class ProductBacklogDao {

	private static ProductBacklogDao singleton;

	public static synchronized ProductBacklogDao getInstance() {
		if (singleton == null) {
			singleton = new ProductBacklogDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<ProductBacklog> productBacklogsLazy(
			ProductBacklog productBacklog) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (productBacklog.getNome() != null
				&& !productBacklog.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome",
					productBacklog.getNome(), MatchMode.ANYWHERE));
		}
		
		return new LazyEntityDataModel<ProductBacklog>(ProductBacklog.class,
				null, null, restrictions, null);

	}

	public List<ProductBacklog> productBacklogs(String productBacklog)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (productBacklog != null && !productBacklog.isEmpty()) {
			restrictions.add(Restrictions.like("nome", productBacklog));
		}
		return GenericDao.findAllByAttributesRestrictions(ProductBacklog.class,
				"nome", true, restrictions);
	}

	public List<ProductBacklog> productBacklogs()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		
		return GenericDao.findAllByAttributesRestrictions(ProductBacklog.class,
				"nome", true, restricions);
	}
	
	public List<ProductBacklog> productBacklogByProjetoSelecionado(String namedQuery,
			ProductBacklog productBacklogFilter, Projeto projetoSelecionado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {
		
		return GenericDao.findAllByNamedQueryWithParameters(ProductBacklog.class,
				namedQuery, "projetoSelecionado", projetoSelecionado
				);
	}

	public LazyEntityDataModel<ProductBacklog> productBacklogLazy(
			List<ProductBacklog> productBacklog, Projeto projetoSelecionado) {
		
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (!productBacklog.isEmpty()) {
			for (int i = 0; i < productBacklog.size(); i++) {
				restrictions.add(Restrictions.ne("id", productBacklog.get(i)
						.getId()));
			}
		}
		
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		restrictions.add(Restrictions.isNull("sprint"));
		
		return new LazyEntityDataModel<ProductBacklog>(ProductBacklog.class,
				null, null, restrictions, null);
	}
	
	public LazyEntityDataModel<ProductBacklog> productBacklogLazyProProjeto(Projeto projetoSelecionado) {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		
		return new LazyEntityDataModel<ProductBacklog>(ProductBacklog.class,
				null, null, restrictions, null);
	}


	public LazyEntityDataModel<ProductBacklog> productBacklogsLazy(
			ProductBacklog productBacklogFilter,
			List<ProductBacklog> productBacklogs, Projeto projetoSelecionado) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (productBacklogFilter.getNome() != null
				&& !productBacklogFilter.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome",
					productBacklogFilter.getNome(), MatchMode.ANYWHERE));
		}
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		restrictions.add(Restrictions.isNull("sprint"));
		
		return new LazyEntityDataModel<ProductBacklog>(ProductBacklog.class,
				null, null, restrictions, null);
	}

	public List<ProductBacklog> productBacklogPorSprint(Sprint sprint)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sprint != null) {
			restrictions.add(Restrictions
					.like("sprint", sprint));
		}
		return GenericDao.findAllByAttributesRestrictions(ProductBacklog.class, null,
				true, restrictions);
	}
	//teste
	private StringBuilder dadosFiltro = new StringBuilder();
	
	public List<ProductBacklog> productBacklogListReport(ProductBacklog productBacklog) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (productBacklog.getNome() != null && !productBacklog.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", productBacklog.getNome(), MatchMode.ANYWHERE));
			
			dadosFiltro.append("NOME: "+productBacklog.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(ProductBacklog.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}