package br.web.scrumbr.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.entity.Usuario;

public class UsuarioDao {

	private static UsuarioDao singleton;

	public static synchronized UsuarioDao getInstance() {
		if (singleton == null) {
			singleton = new UsuarioDao();
		}
		return singleton;
	}

	public LazyEntityDataModel<Usuario> usuariosLazy(Usuario Usuario) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (Usuario.getNome() != null && !Usuario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", Usuario.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Usuario>(Usuario.class, null, null, restrictions, null);

	}

	public List<Usuario> usuarios(String Usuario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (Usuario != null && !Usuario.isEmpty()) {
			restrictions.add(Restrictions.like("nome", Usuario));
		}
		return GenericDao.findAllByAttributesRestrictions(Usuario.class, "nome", true, restrictions);
	}

	public List<Usuario> usuarios() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Usuario.class, "nome", true, restricions);
	}
	
	public List<Usuario> usuariosValidation(Usuario usuario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		restricions.add(Restrictions.eq("status", true));
		
		return GenericDao.findAllByAttributesRestrictions(Usuario.class, "nome", true, restricions);
	}
	
	private StringBuilder dadosFiltro = new StringBuilder();
	
	public List<Usuario> usuarioListReport(Usuario usuario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", usuario.getNome(), MatchMode.ANYWHERE));
			
			dadosFiltro.append("NOME: "+usuario.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Usuario.class, "nome", true, restrictions);
	}
	
	
	public LazyEntityDataModel<Usuario> usuariosLazy(List<Usuario> participantes) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (!participantes.isEmpty()) {
			for (int i = 0; i < participantes.size(); i++) {
				restrictions.add(Restrictions.ne("id", participantes.get(i)
						.getId()));
			}
		}

		return new LazyEntityDataModel<Usuario>(Usuario.class, null, null,
				restrictions, null);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}
	
}
