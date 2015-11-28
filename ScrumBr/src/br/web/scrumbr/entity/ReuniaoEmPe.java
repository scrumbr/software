package br.web.scrumbr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "table_reuniao_diaria")
public class ReuniaoEmPe extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@ManyToOne
	private Usuario participante;
	
	@ManyToOne
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_reuniao")
	private Date dataReuniao;
	
	@ManyToOne
	@JoinColumn(name="projeto_id")
	private Projeto projeto;
	
	@Column(name="que_fez_hoje", columnDefinition="text")
	private String queFez;
	
	@Column(name="qual_dificuldade", columnDefinition="text")
	private String qualDificuldade;
	
	@Column(name="que_vai_fazer", columnDefinition="text")
	private String queVaiFazer;
	
	@Transient
	private Date dataReuniaoInicio;

	@Transient
	private Date dataReuniaoFim;

	public Usuario getParticipante() {
		return participante;
	}

	public void setParticipante(Usuario participante) {
		this.participante = participante;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataReuniao() {
		return dataReuniao;
	}

	public void setDataReuniao(Date dataReuniao) {
		this.dataReuniao = dataReuniao;
	}

	public String getQueFez() {
		return queFez;
	}

	public void setQueFez(String queFez) {
		this.queFez = queFez;
	}

	public String getQualDificuldade() {
		return qualDificuldade;
	}

	public void setQualDificuldade(String qualDificuldade) {
		this.qualDificuldade = qualDificuldade;
	}

	public String getQueVaiFazer() {
		return queVaiFazer;
	}

	public void setQueVaiFazer(String queVaiFazer) {
		this.queVaiFazer = queVaiFazer;
	}

	public Date getDataReuniaoInicio() {
		return dataReuniaoInicio;
	}

	public void setDataReuniaoInicio(Date dataReuniaoInicio) {
		this.dataReuniaoInicio = dataReuniaoInicio;
	}

	public Date getDataReuniaoFim() {
		return dataReuniaoFim;
	}

	public void setDataReuniaoFim(Date dataReuniaoFim) {
		this.dataReuniaoFim = dataReuniaoFim;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
}
