package br.web.scrumbr.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import br.web.scrumbr.entity.Projeto;

@ManagedBean
@SessionScoped
public class ProjetoSessaoBean implements Serializable {

	private static final long serialVersionUID = -1361342408456177486L;

	@ManagedProperty(value = "#{projetoSelecionado}")
	private Projeto projetoSelecionado;

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
	
}