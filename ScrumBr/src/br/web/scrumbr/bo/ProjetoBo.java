package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.ProjetoDao;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.Usuario;

public class ProjetoBo {

	private static ProjetoBo singleton;

	public static synchronized ProjetoBo getInstance() {
		if (singleton == null) {
			singleton = new ProjetoBo();
		}
		return singleton;
	}

	public void save(Projeto projeto) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(projeto);
		GenericDao.save(projeto);
	}

	public void update(Projeto projeto) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(projeto);
		GenericDao.update(projeto);
	}

	public Projeto find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Projeto.class, id);
	}

	public LazyEntityDataModel<Projeto> projetosLazy(Projeto projeto) {
		return ProjetoDao.getInstance().projetosLazy(projeto);
	}

	public List<Projeto> list(Usuario usuarioLogado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProjetoDao.getInstance().projetos(usuarioLogado);
	}

	public void validation(Projeto projeto) {

	}
	
	public List<Projeto> projetosUserLogado(String namedQuery,Usuario usuarioLogado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {
		return ProjetoDao.getInstance().projetosUserLogado(namedQuery, usuarioLogado);
	}
	
	public StringBuilder dadosFiltro(){
		return ProjetoDao.getInstance().getDadosFiltro();
	}

	public List<Projeto>projetoListReport(Projeto projeto) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ProjetoDao.getInstance().projetoListReport(projeto);
	}
}
