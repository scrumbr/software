package br.web.scrumbr.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.entity.HistoricoAlteracaoTarefa;
import br.web.scrumbr.entity.Projeto;

public class HistoricoAlteracaoTarefaDao {

	private static HistoricoAlteracaoTarefaDao singleton;

	public static synchronized HistoricoAlteracaoTarefaDao getInstance() {
		if (singleton == null) {
			singleton = new HistoricoAlteracaoTarefaDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<HistoricoAlteracaoTarefa> HistoricoAlteracaoTarefasLazy(HistoricoAlteracaoTarefa historicoAlteracaoTarefaFilter, Projeto projetoSelecionado) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (historicoAlteracaoTarefaFilter.getDataAlteracaoInicio() != null
				&& historicoAlteracaoTarefaFilter.getDataAlteracaoFim() != null) {
			restrictions.add(Restrictions.between("dataReuniao",
					historicoAlteracaoTarefaFilter.getDataAlteracaoInicio(),
					historicoAlteracaoTarefaFilter.getDataAlteracaoFim()));
		}

		if (historicoAlteracaoTarefaFilter.getDataAlteracaoInicio() != null
				&& historicoAlteracaoTarefaFilter.getDataAlteracaoFim() == null) {
			historicoAlteracaoTarefaFilter.setDataAlteracaoFim(historicoAlteracaoTarefaFilter
					.getDataAlteracaoInicio());
			restrictions.add(Restrictions.between("dataReuniao",
					historicoAlteracaoTarefaFilter.getDataAlteracaoInicio(),
					historicoAlteracaoTarefaFilter.getDataAlteracaoFim()));
		}

		if (historicoAlteracaoTarefaFilter.getDataAlteracaoInicio() == null
				&& historicoAlteracaoTarefaFilter.getDataAlteracaoFim() != null) {
			historicoAlteracaoTarefaFilter.setDataAlteracaoInicio(historicoAlteracaoTarefaFilter
					.getDataAlteracaoFim());
			restrictions.add(Restrictions.between("dataReuniao",
					historicoAlteracaoTarefaFilter.getDataAlteracaoInicio(),
					historicoAlteracaoTarefaFilter.getDataAlteracaoFim()));
		}

		if (historicoAlteracaoTarefaFilter.getUsuario() != null) {
			restrictions.add(Restrictions.eq("usuario", historicoAlteracaoTarefaFilter.getUsuario()));
		}
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		
		return new LazyEntityDataModel<HistoricoAlteracaoTarefa>(HistoricoAlteracaoTarefa.class, null, null, restrictions, null);

	}
	
	public LazyEntityDataModel<HistoricoAlteracaoTarefa> historicotarefaLazyProProjeto(Projeto projetoSelecionado) {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		
		return new LazyEntityDataModel<HistoricoAlteracaoTarefa>(HistoricoAlteracaoTarefa.class,
				null, null, restrictions, null);
	}

	public List<HistoricoAlteracaoTarefa> HistoricoAlteracaoTarefas(String HistoricoAlteracaoTarefa) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		return GenericDao.findAll(HistoricoAlteracaoTarefa.class);
	}
	
	
}
