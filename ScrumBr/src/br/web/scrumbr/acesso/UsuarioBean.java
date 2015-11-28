package br.web.scrumbr.acesso;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;
import static br.web.scrumbr.util.Message.addWarnMessage;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.bean.UrlBean;
import br.web.scrumbr.bo.ProductBacklogBo;
import br.web.scrumbr.bo.UsuarioBo;
import br.web.scrumbr.constants.PerfilUser;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.exceptions.ObjetoExistenteException;
import br.web.scrumbr.report.GenericReport;
import br.web.scrumbr.util.BuscaNoWebContent;
import br.web.scrumbr.util.DataUtil;
import br.web.scrumbr.util.DispararEmail;
import br.web.scrumbr.util.GeradorDeSenha;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class UsuarioBean implements Serializable {
	  
	private static final long serialVersionUID = 8197346996386546555L;
		private final String CAD = "/private/cadastro/cadastrarusuario.xhtml";
		private final String EDIT = "/pages/private/cadastro/cadastrarParticipante.xhtml";
		private final String LIST= "/pages/private/lista/listarParticipante.xhtml";
		
		private Usuario usuario;
		private Usuario usuarioFilter;
		private Usuario usuarioLogado;
		private LazyEntityDataModel<Usuario> lazy;
		private List<Usuario> usuarios;
		private UrlBean url;
		
		@PostConstruct
		public void init(){
			usuario = new Usuario();
			usuarioFilter = new Usuario();
			lazy = new LazyEntityDataModel<Usuario>(Usuario.class);
			usuarios = new ArrayList<Usuario>();
			url = new UrlBean();
			usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();
		}
		
		public String assinar(){
			try {
				usuario.setSenha(GeradorDeSenha.gerarSenha());
				usuario.setPerfilUser(PerfilUser.SCRUM_MASTER);
				UsuarioBo.getInstance().save(usuario);
				DispararEmail.dispararEmailHtmlAssinar(usuario);
				addInfoMessage("","Cadastrado com sucesso! Usuario " + usuario.getNome());
			} catch (ViolacaoChaveHibernateException e) {
				addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente", "");
				e.printStackTrace();
			} catch (ValidacaoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (ObjetoExistenteException e) {
				addWarnMessage("Erro!", "Erro: " + e.getMessage());
				e.printStackTrace();
			} catch (EmailException e) {
				addWarnMessage("Erro!", "Erro ao enviar e-mail: " + e.getMessage());
				e.printStackTrace();
			}
			usuario = new Usuario();
			return CAD;
		}
		
		public void save(){
			try {
				usuario.setLogin(usuario.getNome() + "_ScrumBR");
				usuario.setSenha(GeradorDeSenha.gerarSenha());
				usuario.setEmpresa(usuarioLogado.getEmpresa());
				DispararEmail.dispararEmailHtmlCadastro(usuario);
				UsuarioBo.getInstance().save(usuario);
				addInfoMessage("","Cadastrado com sucesso! Usuario " + usuario.getNome());
			} catch (ViolacaoChaveHibernateException e) {
				addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
				e.printStackTrace();
			} catch (ValidacaoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			}catch (ObjetoExistenteException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (EmailException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			}
			usuario = new Usuario();
		}
		
		public String update(){
			try {
				if(usuario.getPrimeiroAcesso() == true){
					usuario.setPrimeiroAcesso(false);
				}
				UsuarioBo.getInstance().update(usuario);
				addInfoMessage("","Editado com sucesso! Usuario " + usuario.getNome());
			} catch (ViolacaoChaveHibernateException e) {
				addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente", "");
				e.printStackTrace();
			} catch (ObjetoNaoEncontradoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (ValidacaoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			}catch (ObjetoExistenteException e) {
				addWarnMessage("Erro!", "Erro: " + e.getMessage());
				e.printStackTrace();
			}
			usuario = new Usuario();
			return LIST;
		}
		
	public String updateSenha(){
			try {
				usuario.setPrimeiroAcesso(false);
				UsuarioBo.getInstance().updateSenha(usuario);
				usuarioLogado = usuario;
				addInfoMessage("","Senha Alterada com sucesso! Usuario " + usuario.getNome());
			} catch (ViolacaoChaveHibernateException e) {
				addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente", "");
				e.printStackTrace();
			} catch (ObjetoNaoEncontradoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (ValidacaoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			}catch (ObjetoExistenteException e) {
				addWarnMessage("Erro!", "Erro: " + e.getMessage());
				e.printStackTrace();
			}
			usuario = new Usuario();
			return url.getPAG_INICIO();
		}
		
		public PerfilUser[] getPerfis(){
			return PerfilUser.values();
		}
		
		public String list(){
			
			lazy = UsuarioBo.getInstance().usuariosLazy(usuarioFilter);
			
			return null;
		}
		
		public String goToList(){
			return LIST;
		}
		
		public String prepareUpdate(){
			return EDIT;
		}
		
		public String prepareSave(){
			usuario = new Usuario();
			return CAD;	
		}
		
		public String updateStatus() {
			try {
				
				if (usuario.getStatus()) {
					usuario.setStatus(false);
					UsuarioBo.getInstance().update(usuario);
				} else {
					usuario.setStatus(true);
					UsuarioBo.getInstance().update(usuario);
				}
				
			} catch (ViolacaoChaveHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (ObjetoNaoEncontradoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (ValidacaoHibernateException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				addErrorMessage("Erro!", e.getMessage());
				e.printStackTrace();
			}catch (ObjetoExistenteException e) {
				addWarnMessage("Erro!", "Erro: " + e.getMessage());
				e.printStackTrace();
			}
			
			String status = usuario.getStatus() ? "Ativo" : "Inativo";
			
			addInfoMessage("Status alterado com sucesso!", "O usuario está " + status);

			return null;
		}
		
		public void enviarImagem(FileUploadEvent event) {
			try {
				usuario.setFotoPro(event.getFile().getContents());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void gerarPDF(ActionEvent ev){
			try {
				Map<String, Object> mapa = new HashMap<String, Object>();
				
				
				List<Usuario> usuario = UsuarioBo.getInstance().usuarioListReport(usuarioFilter);
				
				
				usuarioFilter= new Usuario();
				mapa.put("logo", BuscaNoWebContent.buscaArquivo("/template/imagens/ScrumBR.png"));
				mapa.put("rodape", BuscaNoWebContent.buscaArquivo("/template/imagens/rodape_paisagem.png"));
				mapa.put("filtro",ProductBacklogBo.getInstance().dadosFiltro());
				
				GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, usuario, "usuario", "Relatório de Usuarios "  + DataUtil.formatarData(new Date()), true);

			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
		
		public StreamedContent getImage() {
			FacesContext fc = FacesContext.getCurrentInstance();
			if (fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				return new DefaultStreamedContent();
			}

			String id = fc.getExternalContext().getRequestParameterMap().get("img");
			byte[] photoData = null;
			try {
				photoData = UsuarioBo.getInstance().find(Integer.parseInt(id))
						.getFotoPro();
				
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ObjetoNaoEncontradoHibernateException e) {
				e.printStackTrace();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
			return new DefaultStreamedContent(new ByteArrayInputStream(photoData));
		}


		public Usuario getusuario() {
			return usuario;
		}


		public void setusuario(Usuario usuario) {
			this.usuario = usuario;
		}


		public LazyEntityDataModel<Usuario> getLazy() {
			return lazy;
		}


		public void setLazy(LazyEntityDataModel<Usuario> lazy) {
			this.lazy = lazy;
		}


		public List<Usuario> getUsuarios() {
			try {
				usuarios = UsuarioBo.getInstance().list();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
			return usuarios;
		}


		public void setUsuarios(List<Usuario> usuarios) {
			this.usuarios = usuarios;
		}


		public Usuario getusuarioFilter() {
			return usuarioFilter;
		}


		public void setusuarioFilter(Usuario usuarioFilter) {
			this.usuarioFilter = usuarioFilter;
		}


		public String getCAD() {
			return CAD;
		}


		public String getEDIT() {
			return EDIT;
		}


		public String getLIST() {
			return LIST;
		}


		public Usuario getUsuarioLogado() {
			return usuarioLogado;
		}


		public void setUsuarioLogado(Usuario usuarioLogado) {
			this.usuarioLogado = usuarioLogado;
		}


		public Usuario getUsuario() {
			return usuario;
		}


		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}


		public Usuario getUsuarioFilter() {
			return usuarioFilter;
		}


		public void setUsuarioFilter(Usuario usuarioFilter) {
			this.usuarioFilter = usuarioFilter;
		}

		public UrlBean getUrl() {
			return url;
		}

		public void setUrl(UrlBean url) {
			this.url = url;
		}
		
		

}
