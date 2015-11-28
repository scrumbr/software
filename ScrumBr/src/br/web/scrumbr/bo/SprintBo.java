package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.SprintDao;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Sprint;

public class SprintBo {

	private static SprintBo singleton;

	public static synchronized SprintBo getInstance() {
		if (singleton == null) {
			singleton = new SprintBo();
		}
		return singleton;
	}

	public void save(Sprint sprint) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(sprint);
		GenericDao.save(sprint);
	}

	public void update(Sprint sprint) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(sprint);
		GenericDao.update(sprint);
	}

	public Sprint find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Sprint.class, id);
	}

	public LazyEntityDataModel<Sprint> sprintsLazy(Sprint sprint) {
		return SprintDao.getInstance().sprintsLazy(sprint);
	}

	public LazyEntityDataModel<Sprint> sprintsLazyProProjeto(Projeto projetoSelecionado) {
		return SprintDao.getInstance().sprintLazyProProjeto(projetoSelecionado);
	}

	
	public List<Sprint> sprintByProjetoSelecionado(String namedQuery,
			Sprint sprintFilter, Projeto projetoSelecionado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {
		return SprintDao.getInstance().sprintsByProjetoSelecionado(namedQuery,
				sprintFilter, projetoSelecionado);
	}

	public List<Sprint> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SprintDao.getInstance().sprints();
	}
	
	public StringBuilder dadosFiltro(){
		return SprintDao.getInstance().getDadosFiltro();
	}

	public List<Sprint>sprintListReport(Sprint sprint) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return SprintDao.getInstance().sprintListReport(sprint);
	}

	public void validation(Sprint sprint) {

	}
}
