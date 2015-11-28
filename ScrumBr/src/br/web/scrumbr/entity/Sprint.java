package br.web.scrumbr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

@NamedQueries({ @NamedQuery(name = "list_sprint", query = "select s from Sprint s "
		+ "JOIN s.projeto p " + "where  p = :projetoSelecionado"), })
@Entity
@Table(name = "table_sprint")
public class Sprint extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Type(type = "true_false")
	private Boolean status = true;

	private String nome;

	private String descricao;
	
	@Column(name="data_inicio")
	private Date dataInicio = new Date();
	
	@Column(name="data_fim")
	private Date dataFim;
	
	@Column(name="qtd_dias")
	private Integer qtdDias;
	

	@OneToMany(mappedBy = "sprint")
	private List<ProductBacklog> productBacklogs = new ArrayList<ProductBacklog>();

	@ManyToOne
	@JoinColumn(name = "projeto_id")
	private Projeto projeto;

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

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

	public List<ProductBacklog> getProductBacklogs() {
		return productBacklogs;
	}

	public void setProductBacklogs(List<ProductBacklog> productBacklogs) {
		this.productBacklogs = productBacklogs;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public Date somarDiasParaFimSprint(int numDias) {
		Date dataFinal = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dataFinal);
		c.add(Calendar.DATE, numDias);
		dataFinal = c.getTime();
		return dataFinal;
	}

}
