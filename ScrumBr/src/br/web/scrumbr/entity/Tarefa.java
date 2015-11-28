package br.web.scrumbr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import br.web.scrumbr.constants.DificuldadeTarefa;
import br.web.scrumbr.constants.StatusTarefa;
import br.web.scrumbr.util.TitleTarefaTaskBoard;

@NamedQueries({
		@NamedQuery(name = "list_tarefas_sprint", query = "select t from Tarefa t "
				+ "JOIN t.productBacklog b "
				+ "JOIN b.sprint s "
				+ "where s = :sprintFilter"),

		@NamedQuery(name = "soma_tarefas", query = "select t from Tarefa t "
				+ "JOIN t.productBacklog b "
				+ "JOIN b.sprint s "
				+ "JOIN s.projeto p " + "where p = :projetoSelecionado"), })
@Entity
@Table(name = "table_tarefa")
public class Tarefa extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Type(type = "true_false")
	private Boolean status = true;

	private String nome;

	private String descricao;

	@Transient
	private String nomeAux;

	@Temporal(TemporalType.TIME)
	private Date tempo;

	@Column(name = "tempo_atividade")
	private Double tempoAtividade;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_conclusao")
	private Date dataConclusao = new Date();

	@ManyToOne
	@JoinColumn(name = "responsavel")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_tarefa")
	private StatusTarefa statusTarefa;

	@Enumerated(EnumType.STRING)
	@Column(name = "dificuldade_tarefa")
	private DificuldadeTarefa dificuldadeTarefa;

	@ManyToOne
	@JoinColumn(name = "product_backlog_id")
	private ProductBacklog productBacklog;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public StatusTarefa getStatusTarefa() {
		return statusTarefa;
	}

	public void setStatusTarefa(StatusTarefa statusTarefa) {
		this.statusTarefa = statusTarefa;
	}

	public ProductBacklog getProductBacklog() {
		return productBacklog;
	}

	public void setProductBacklog(ProductBacklog productBacklog) {
		this.productBacklog = productBacklog;
	}

	public Date getTempo() {
		return tempo;
	}

	public void setTempo(Date tempo) {
		this.tempo = tempo;
	}

	public DificuldadeTarefa getDificuldadeTarefa() {
		return dificuldadeTarefa;
	}

	public void setDificuldadeTarefa(DificuldadeTarefa dificuldadeTarefa) {
		this.dificuldadeTarefa = dificuldadeTarefa;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public Double getTempoAtividade() {
		return tempoAtividade;
	}

	public void setTempoAtividade(Double tempoAtividade) {
		this.tempoAtividade = tempoAtividade;
	}

	public String getNomeAux() {
		nomeAux = TitleTarefaTaskBoard.text(nome);
		return nomeAux;
	}

	public void setNomeAux(String nomeAux) {
		this.nomeAux = nomeAux;
	}

}
