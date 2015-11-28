package br.web.scrumbr.bo;

import java.util.List;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.dao.ProductBacklogDao;
import br.web.scrumbr.dao.UsuarioDao;
import br.web.scrumbr.entity.ProductBacklog;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.exceptions.ObjetoExistenteException;

public class UsuarioBo {

	private static UsuarioBo singleton;

	public static synchronized UsuarioBo getInstance() {
		if (singleton == null) {
			singleton = new UsuarioBo();
		}
		return singleton;
	}

	public void save(Usuario Usuario) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Usuario);
		GenericDao.save(Usuario);
	}

	public void update(Usuario Usuario) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Usuario);
	}

	public void updateSenha(Usuario Usuario)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Usuario);
	}

	public Usuario find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Usuario.class, id);
	}

	public LazyEntityDataModel<Usuario> usuariosLazy(Usuario Usuario) {
		return UsuarioDao.getInstance().usuariosLazy(Usuario);
	}

	public List<Usuario> usuariosComplete(String UsuarioName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UsuarioDao.getInstance().usuarios(UsuarioName);
	}

	public List<Usuario> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UsuarioDao.getInstance().usuarios();
	}

	public LazyEntityDataModel<Usuario> usuariosLazy(List<Usuario> participantes) {
		return UsuarioDao.getInstance().usuariosLazy(participantes);
	}
	
	public StringBuilder dadosFiltro(){
		return UsuarioDao.getInstance().getDadosFiltro();
	}

	public List<Usuario>usuarioListReport(Usuario usuario) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return UsuarioDao.getInstance().usuarioListReport(usuario);
	}

	public void validation(Usuario usuario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {

		List<Usuario> usuarios = UsuarioDao.getInstance().usuariosValidation(
				usuario);

		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getLogin().equals(usuario.getLogin())
					&& usuarios.get(i).getEmpresa()
							.equals(usuario.getEmpresa())) {
				throw new ObjetoExistenteException("Usuário: "
						+ usuario.getNome()
						+ " já possui cadastro na empresa: "
						+ usuario.getEmpresa());
			}
		}
	}
}
