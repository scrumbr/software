package br.web.scrumbr.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import br.web.scrumbr.constants.PerfilUser;
import br.web.scrumbr.util.TitleTarefaTaskBoard;

@NamedQueries({
		@NamedQuery(name = "findByLoginAndSenha", query = "select u from Usuario u where u.login = :login and u.senha = :senha and u.empresa = :empresa"),
})

@Entity
@Table(name = "table_usuario")
public class Usuario extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Type(type = "true_false")
	private Boolean status = true;

	private String nome;

	@Transient
	private String nomeAux;

	private String sobrenome;

	private String email;

	private String login;

	private String senha;

	private String empresa;

	private String cpf;

	private String celular;

	@Type(type = "true_false")
	private Boolean primeiroAcesso = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil_user")
	private PerfilUser perfilUser;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "foto")
	private byte[] fotoPro;

	@Transient
	private Boolean imagem = false;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public PerfilUser getPerfilUser() {
		return perfilUser;
	}

	public void setPerfilUser(PerfilUser perfilUser) {
		this.perfilUser = perfilUser;
	}

	public byte[] getFotoPro() {
		return fotoPro;
	}

	public void setFotoPro(byte[] fotoPro) {
		this.fotoPro = fotoPro;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public Boolean getImagem() {
		return imagem;
	}

	public void setImagem(Boolean imagem) {
		this.imagem = imagem;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNomeAux() {
		nomeAux = TitleTarefaTaskBoard.text(nome);
		return nomeAux;
	}

	public void setNomeAux(String nomeAux) {
		this.nomeAux = nomeAux;
	}

}
