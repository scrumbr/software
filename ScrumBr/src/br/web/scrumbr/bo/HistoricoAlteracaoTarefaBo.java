package br.web.scrumbr.bo;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.HistoricoAlteracaoTarefaDao;
import br.web.scrumbr.entity.HistoricoAlteracaoTarefa;
import br.web.scrumbr.entity.Projeto;

public class HistoricoAlteracaoTarefaBo {

	private static HistoricoAlteracaoTarefaBo singleton;

	public static synchronized HistoricoAlteracaoTarefaBo getInstance() {
		if (singleton == null) {
			singleton = new HistoricoAlteracaoTarefaBo();
		}
		return singleton;
	}

	public void save(HistoricoAlteracaoTarefa historicoAlteracaoTarefa) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(historicoAlteracaoTarefa);
		GenericDao.save(historicoAlteracaoTarefa);
	}

	public void update(HistoricoAlteracaoTarefa HistoricoAlteracaoTarefa) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(HistoricoAlteracaoTarefa);
		GenericDao.update(HistoricoAlteracaoTarefa);
	}

	public HistoricoAlteracaoTarefa find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoAlteracaoTarefa.class, id);
	}

	public LazyEntityDataModel<HistoricoAlteracaoTarefa> historicoAlteracaoTarefasLazy(HistoricoAlteracaoTarefa HistoricoAlteracaoTarefa, Projeto projetoSelecionado) {
		return HistoricoAlteracaoTarefaDao.getInstance().HistoricoAlteracaoTarefasLazy(HistoricoAlteracaoTarefa, projetoSelecionado);
	}
	
	public LazyEntityDataModel<HistoricoAlteracaoTarefa> historicoTarefasLazy(Projeto projetoSelecionado) {
		return HistoricoAlteracaoTarefaDao.getInstance().historicotarefaLazyProProjeto(projetoSelecionado);
	}

	public void validation(HistoricoAlteracaoTarefa HistoricoAlteracaoTarefa) {

	}
}
