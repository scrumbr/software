package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.ReuniaoEmPeDao;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.ReuniaoEmPe;

public class ReuniaoEmPeBo {

	private static ReuniaoEmPeBo singleton;

	public static synchronized ReuniaoEmPeBo getInstance() {
		if (singleton == null) {
			singleton = new ReuniaoEmPeBo();
		}
		return singleton;
	}

	public void save(ReuniaoEmPe reuniaoEmPe) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(reuniaoEmPe);
		GenericDao.save(reuniaoEmPe);
	}

	public void update(ReuniaoEmPe reuniaoEmPe) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		validation(reuniaoEmPe);
		GenericDao.update(reuniaoEmPe);
	}

	public ReuniaoEmPe find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ReuniaoEmPe.class, id);
	}

	public LazyEntityDataModel<ReuniaoEmPe> reuniaoEmPesLazy(ReuniaoEmPe reuniaoEmPe) {
		return ReuniaoEmPeDao.getInstance().reuniaoEmPesLazy(reuniaoEmPe);
	}

	public LazyEntityDataModel<ReuniaoEmPe> reuniaoEmPeLazyProProjeto(Projeto projetoSelecionado) {
		return ReuniaoEmPeDao.getInstance().reuniaoEmPeLazyProProjeto(projetoSelecionado);
	}
	
	public void validation(ReuniaoEmPe reuniaoEmPe) {

	}
	
	public StringBuilder dadosFiltro(){
		return ReuniaoEmPeDao.getInstance().getDadosFiltro();
	}

	public List<ReuniaoEmPe>reuniaoEmPeListReport(ReuniaoEmPe reuniaoEmPe) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ReuniaoEmPeDao.getInstance().reuniaoEmPeListReport(reuniaoEmPe);
	}
}
