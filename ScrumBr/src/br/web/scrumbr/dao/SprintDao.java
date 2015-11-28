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
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;

public class SprintDao {

	private static SprintDao singleton;

	public static synchronized SprintDao getInstance() {
		if (singleton == null) {
			singleton = new SprintDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<Sprint> sprintsLazy(Sprint sprint) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sprint.getNome() != null && !sprint.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", sprint.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Sprint>(Sprint.class, null, null,
				restrictions, null);

	}

	public List<Sprint> sprints(String sprint)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sprint != null && !sprint.isEmpty()) {
			restrictions.add(Restrictions.like("nome", sprint));
		}
		return GenericDao.findAllByAttributesRestrictions(Sprint.class, "nome",
				true, restrictions);
	}

	public List<Sprint> sprints()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Sprint.class, "nome",
				true, restricions);
	}
	
	public List<Sprint> sprintsByProjetoSelecionado(String namedQuery,
			Sprint sprintFilter, Projeto projetoSelecionado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {
		
		return GenericDao.findAllByNamedQueryWithParameters(Sprint.class,
				namedQuery, "projetoSelecionado", projetoSelecionado
				);
	}
	
	public LazyEntityDataModel<Sprint> sprintLazyProProjeto(Projeto projetoSelecionado) {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		
		return new LazyEntityDataModel<Sprint>(Sprint.class,
				null, null, restrictions, null);
	}
	
	private StringBuilder dadosFiltro = new StringBuilder();
	
	public List<Sprint> sprintListReport(Sprint sprint) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sprint.getNome() != null && !sprint.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", sprint.getNome(), MatchMode.ANYWHERE));
			
			dadosFiltro.append("NOME: "+sprint.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Sprint.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}	
}
