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
import br.web.scrumbr.entity.Usuario;

public class ProjetoDao {

	private static ProjetoDao singleton;

	public static synchronized ProjetoDao getInstance() {
		if (singleton == null) {
			singleton = new ProjetoDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<Projeto> projetosLazy(Projeto projeto) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (projeto.getNome() != null && !projeto.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", projeto.getNome(),
					MatchMode.ANYWHERE));
		}

		return new LazyEntityDataModel<Projeto>(Projeto.class, null, null,
				restrictions, null);

	}

	/**
	 * 
	 * @param projeto
	 * @return
	 * @throws SessaoNaoEncontradaParaEntidadeFornecidaException
	 */
	public List<Projeto> projetos(String projeto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (projeto != null && !projeto.isEmpty()) {
			restrictions.add(Restrictions.like("nome", projeto));
		}
		return GenericDao.findAllByAttributesRestrictions(Projeto.class,
				"nome", true, restrictions);
	}

	public List<Projeto> projetos(Usuario usuarioLogado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));

		return GenericDao.findAllByAttributesRestrictions(Projeto.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Projeto> projetoListReport(Projeto projeto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (projeto.getNome() != null && !projeto.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", projeto.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + projeto.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Projeto.class,
				"nome", true, restrictions);
	}

	public List<Projeto> projetosUserLogado(String namedQuery,
			Usuario usuarioLogado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumeroAtributosDiferenteNumeroValoresException {

		return GenericDao.findAllByNamedQueryWithParameters(Projeto.class,
				namedQuery, "usuarioLogado", usuarioLogado);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
