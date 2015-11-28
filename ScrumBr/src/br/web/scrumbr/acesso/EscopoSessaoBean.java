package br.web.scrumbr.acesso;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.web.scrumbr.entity.Usuario;


/**
 * Classe usada para retornar o usuário que está logado no sistema
 * 
 * @author LAPIS - FCRS
 * @version 1.0
 */

@ManagedBean
@SessionScoped
public class EscopoSessaoBean implements Serializable {

	private static final long serialVersionUID = -1361342408456177486L;

	@ManagedProperty(value = "#{usuarioLogado}")
	private Usuario usuarioLogado;

	/**
	 * Retorna um Usuario
	 * 
	 * @return Usuario
	 */
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * Seta um Usuario
	 * 
	 * @param Usuario
	 */
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}