package br.web.scrumbr.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UrlBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5446391767704360509L;
	
	private  final String CAD_PRODUCT_BACKLOG = "/pages/private/cadastro/cadastrarProductBacklog.xhtml";
	private final String EDIT_PRODUCT_BACKLOG = "/pages/private/cadastro/cadastrarProductBacklog.xhtml";
	private final String LIST_PRODUCT_BACKLOG = "/pages/private/lista/listarProductBacklog.xhtml";
	private final String CAD_PROJETO = "/pages/private/cadastro/cadastrarProjeto.xhtml";
	private final String EDIT_PROJETO = "/pages/private/cadastro/cadastrarProjeto.xhtml";
	private final String LIST_PROJETO = "/pages/private/lista/listarProjeto.xhtml";
	private final String CAD_SPRINT = "/pages/private/cadastro/cadastrarSprint.xhtml";
	private final String EDIT_SPRINT = "/pages/private/cadastro/cadastrarSprint.xhtml";
	private final String LIST_SPRINT = "/pages/private/lista/listarSprint.xhtml";
	private  final String CAD_REUNIAO_PE = "/pages/private/cadastro/cadastrarReuniaoEmPe.xhtml";
	private final String EDIT_REUNIAO_PE = "/pages/private/cadastro/cadastrarReuniaoEmPe.xhtml";
	private final String LIST_REUNIAO_PE = "/pages/private/lista/listarReuniaoEmPe.xhtml";
	
	private final String PAG_INICIO = "/pages/private/inicio.xhtml";
	private final String PAG_INDEX = "/pages/public/index.xhtml";
	private final String PAG_QUADRO_TAREFAS = "/pages/private/lista/listarQuadroTarefas.xhtml";
	private final String PAG_MUDAR_SENHA = "/pages/private/cadastro/mudarSenha.xhtml";
	
	
	
	
	public String getCAD_PRODUCT_BACKLOG() {
		return CAD_PRODUCT_BACKLOG;
	}
	public String getEDIT_PRODUCT_BACKLOG() {
		return EDIT_PRODUCT_BACKLOG;
	}
	public String getLIST_PRODUCT_BACKLOG() {
		return LIST_PRODUCT_BACKLOG;
	}
	public String getCAD_PROJETO() {
		return CAD_PROJETO;
	}
	public String getEDIT_PROJETO() {
		return EDIT_PROJETO;
	}
	public String getLIST_PROJETO() {
		return LIST_PROJETO;
	}
	public String getPAG_INICIO() {
		return PAG_INICIO;
	}
	public String getCAD_SPRINT() {
		return CAD_SPRINT;
	}
	public String getEDIT_SPRINT() {
		return EDIT_SPRINT;
	}
	public String getLIST_SPRINT() {
		return LIST_SPRINT;
	}
	public String getPAG_INDEX() {
		return PAG_INDEX;
	}
	public String getPAG_QUADRO_TAREFAS() {
		return PAG_QUADRO_TAREFAS;
	}
	public String getCAD_REUNIAO_PE() {
		return CAD_REUNIAO_PE;
	}
	public String getEDIT_REUNIAO_PE() {
		return EDIT_REUNIAO_PE;
	}
	public String getLIST_REUNIAO_PE() {
		return LIST_REUNIAO_PE;
	}
	public String getPAG_MUDAR_SENHA() {
		return PAG_MUDAR_SENHA;
	}
	
}

