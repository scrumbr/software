package br.web.scrumbr.acesso;


import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.web.scrumbr.bo.LoginBO;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.util.ManagedBeanHelper;
import br.web.scrumbr.util.RecuperaMensagemComFlash;

/**
 * Classe que se comunica com a p�gina xhtml respons�vel por cadastrar e listar
 * os atendimentos
 * 
 * @author LAPIS - FCRS
 * @version 1.0
 */

@ManagedBean
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -8351262285681938218L;

	private String login;
	private String senha;
	private String empresa;

	/**
	 * M�todo usado para validar o usu�rio no momento do login
	 * 
	 * @return String
	 */
	public String loginSistema() {

		try {
			invalidaSessaoAtiva();

			Usuario usuario = LoginBO.getInstance().login(getLogin(), getSenha(), getEmpresa());

			if (usuario == null) {
				addErrorMessage("Erro", "Usu�rio n�o encontrado!");
				return "/paginas/paginaLogin";
			}
			if (usuario.getStatus() == false) {
				addErrorMessage("Erro", "Usu�rio inativo, contate o administrador do sistema!");
				return "/paginas/paginaLogin";
			}
		
			ManagedBeanHelper.recuperaBean("escopoSessaoBean",
					EscopoSessaoBean.class).setUsuarioLogado(usuario);
			addInfoMessage("","Bem vindo! " + usuario.getNome());
			RecuperaMensagemComFlash.flashScope().setKeepMessages(true);
			if(usuario.getPrimeiroAcesso() == true){
				return "/pages/private/cadastro/mudarSenha.xhtml?faces-redirect=true";
			}
			return "/pages/private/inicio.xhtml?faces-redirect=true";

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro", e.getMessage());
			return "/public/login.xhtml";
		} catch (NumeroAtributosDiferenteNumeroValoresException e) {
			addErrorMessage("Erro", e.getMessage());
			e.printStackTrace();
			return "/public/login.xhtml";
		}
	}

	/**
	 * M�todo usado para que o usu�rio saia do sistema
	 * 
	 * @return String
	 */
	public String logout() {

		ManagedBeanHelper.destroiBean("escopoSessaoBean",
				EscopoSessaoBean.class);

		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).invalidate();
		
		RecuperaMensagemComFlash.flashScope().setKeepMessages(true);
		addInfoMessage( "", "Voc� acabou se sair do ScrumBR!");

		return "/pages/public/index.xhtml?faces-redirect=true";
	}

	/** M�todo usado para invalidar a sess�o e for�ar o usu�rio logar novamente */
	private void invalidaSessaoAtiva() {
		if (((HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false)) != null) {
			((HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false)).invalidate();
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	

}