package br.web.scrumbr.bo;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.web.scrumbr.entity.Usuario;

public class LoginBO {

	private static LoginBO singleton;

	/**
	 * M�todo que implementa o padr�o de projeto 'singleton', este � respons�vel
	 * por facilitar o acesso a uma �nica inst�ncia da classe LoginBO, este
	 * m�todo garante tamb�m um ponto global de acesso � uma inst�ncia dessa
	 * classe, que ser� obtida atrav�s da chamada desse m�todo.
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
	 * Construtor padr�o com modificador de acesso 'private', para que seja n�o
	 * permitida a cria��o de uma inst�ncia dessa classe.
	 * */
	private LoginBO() {

	}

	public Usuario login(String login, String senha, String empresa) throws NumeroAtributosDiferenteNumeroValoresException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return (Usuario) GenericDao.findByNamedQueryWithParameters(Usuario.class, "findByLoginAndSenha", "login", login,"senha", senha, "empresa", empresa);

	}
}
