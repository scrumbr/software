package br.web.scrumbr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.web.scrumbr.constants.StatusTarefaAlteracao;

@Entity
@Table(name = "table_historico_tarefa")
public class HistoricoAlteracaoTarefa extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Projeto projeto;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_alteracao")
	private Date dataAlteracao;
	
	@ManyToOne
	private Tarefa tarefa;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status_tarefa_alteracao")
	private StatusTarefaAlteracao statusTarefaAlteracao;

	@Transient
	private Date dataAlteracaoInicio;

	@Transient
	private Date dataAlteracaoFim;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public StatusTarefaAlteracao getStatusTarefaAlteracao() {
		return statusTarefaAlteracao;
	}

	public void setStatusTarefaAlteracao(StatusTarefaAlteracao statusTarefaAlteracao) {
		this.statusTarefaAlteracao = statusTarefaAlteracao;
	}

	public Date getDataAlteracaoInicio() {
		return dataAlteracaoInicio;
	}

	public void setDataAlteracaoInicio(Date dataAlteracaoInicio) {
		this.dataAlteracaoInicio = dataAlteracaoInicio;
	}

	public Date getDataAlteracaoFim() {
		return dataAlteracaoFim;
	}

	public void setDataAlteracaoFim(Date dataAlteracaoFim) {
		this.dataAlteracaoFim = dataAlteracaoFim;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
}
