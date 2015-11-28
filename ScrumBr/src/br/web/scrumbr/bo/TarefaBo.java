package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.constants.StatusTarefa;
import br.web.scrumbr.dao.TarefaDao;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;
import br.web.scrumbr.entity.Tarefa;

public class TarefaBo {

	private static TarefaBo singleton;

	public static synchronized TarefaBo getInstance() {
		if (singleton == null) {
			singleton = new TarefaBo();
		}
		return singleton;
	}

	public void save(Tarefa tarefa) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(tarefa);
		GenericDao.save(tarefa);
	}

	public void update(Tarefa tarefa) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(tarefa);
		GenericDao.update(tarefa);
	}

	public Tarefa find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Tarefa.class, id);
	}

	public LazyEntityDataModel<Tarefa> tarefasLazy(Tarefa tarefa) {
		return TarefaDao.getInstance().tarefasLazy(tarefa);
	}
	
	public List<Tarefa> tarefasAfazer(StatusTarefa status) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return TarefaDao.getInstance().tarefasAfazer(status);
	}

	public List<Tarefa> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return TarefaDao.getInstance().tarefas();
	}
	
	public List<Tarefa> tarefasPorProductBacklog(ProductBacklog productBacklog) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return TarefaDao.getInstance().tarefasPorProductBacklog(productBacklog);
	}
	
	public List<Tarefa> tarefasPorSprint(String namedQuery, Sprint sprintFilter) throws SessaoNaoEncontradaParaEntidadeFornecidaException, NumeroAtributosDiferenteNumeroValoresException{
		return TarefaDao.getInstance().tarefasBySprint(namedQuery, sprintFilter);
	}
	
	
	public void mergeAll(List<Tarefa> tarefas) throws ViolacaoChaveHibernateException, ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(tarefas);
		
	}
	
	public void delete(Tarefa tarefa) throws IntegridadeObjetoHibernateException, ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		GenericDao.delete(tarefa);
	}
	
	public List<Tarefa> total(Projeto projetoSelecionada) throws NumeroAtributosDiferenteNumeroValoresException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return TarefaDao.getInstance().total(projetoSelecionada);
	}

	public void validation(Tarefa tarefa) {

	}
}
