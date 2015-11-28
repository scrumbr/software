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
import br.web.scrumbr.constants.StatusTarefa;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;
import br.web.scrumbr.entity.Tarefa;

public class TarefaDao {

	private static TarefaDao singleton;

	public static synchronized TarefaDao getInstance() {
		if (singleton == null) {
			singleton = new TarefaDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<Tarefa> tarefasLazy(Tarefa tarefa) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (tarefa.getNome() != null && !tarefa.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", tarefa.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Tarefa>(Tarefa.class, null, null,
				restrictions, null);

	}

	public List<Tarefa> tarefas(String tarefa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (tarefa != null && !tarefa.isEmpty()) {
			restrictions.add(Restrictions.like("nome", tarefa));
		}
		return GenericDao.findAllByAttributesRestrictions(Tarefa.class, "nome",
				true, restrictions);
	}

	public List<Tarefa> tarefasAfazer(StatusTarefa status)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.like("statusTarefa", status));
		restrictions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Tarefa.class, "nome",
				true, restrictions);
	}

	public List<Tarefa> tarefas()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Tarefa.class, "nome",
				true, restricions);
	}

	public List<Tarefa> tarefasPorProductBacklog(ProductBacklog productBacklog)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (productBacklog != null) {
			restrictions.add(Restrictions
					.like("productBacklog", productBacklog));
		}
		return GenericDao.findAllByAttributesRestrictions(Tarefa.class, null,
				true, restrictions);
	}

	public List<Tarefa> tarefasBySprint(String namedQuery, Sprint sprintFilter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {

		return GenericDao.findAllByNamedQueryWithParameters(Tarefa.class,
				namedQuery, "sprintFilter", sprintFilter);
	}

	public List<Tarefa> total(Projeto projetoSelecionado)
			throws NumeroAtributosDiferenteNumeroValoresException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findAllByNamedQueryWithParameters(Tarefa.class,
				"soma_tarefas", "projetoSelecionado", projetoSelecionado);
	}

}
