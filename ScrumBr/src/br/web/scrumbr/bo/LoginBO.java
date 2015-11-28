package br.web.scrumbr.bo;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.web.scrumbr.entity.Usuario;

public class LoginBO {

	private static LoginBO singleton;

	/**
	 * Método que implementa o padrão de projeto 'singleton', este é responsável
	 * por facilitar o acesso a uma única instância da classe LoginBO, este
	 * método garante também um ponto global de acesso à uma instância dessa
	 * classe, que será obtida através da chamada desse método.
	 * 
	 * @return AtendiomentoBO
	 */
	public synchronized static LoginBO getInstance() {
		if (singleton == null) {
			singleton = new LoginBO();
		}
		return singleton;
	}

	/**
	 * Construtor padrão com modificador de acesso 'private', para que seja não
	 * permitida a criação de uma instância dessa classe.
	 * */
	private LoginBO() {

	}

	public Usuario login(String login, String senha, String empresa) throws NumeroAtributosDiferenteNumeroValoresException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return (Usuario) GenericDao.findByNamedQueryWithParameters(Usuario.class, "findByLoginAndSenha", "login", login,"senha", senha, "empresa", empresa);

	}
}
