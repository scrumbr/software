package br.web.scrumbr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

@NamedQueries({ @NamedQuery(name = "list_product_backlog", query = "select s from ProductBacklog s "
		+ "JOIN s.projeto p " + "where  p = :projetoSelecionado"), })

@Entity
@Table(name="table_product_backlog")
public class ProductBacklog extends EntidadeGenerica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2424868780944840594L;
	
	private String nome;
	
	@Column(columnDefinition="text")
	private String descricao;
	
	@Type(type = "true_false")
	private Boolean status = true;
	
	@OneToMany(mappedBy = "productBacklog")
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	
	@ManyToOne
	@JoinColumn(name="sprint_id")
	private Sprint sprint;
	
	@ManyToOne
	@JoinColumn(name="projeto_id")
	private Projeto projeto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
}
