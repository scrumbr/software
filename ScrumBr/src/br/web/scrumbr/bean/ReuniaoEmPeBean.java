package br.web.scrumbr.bean;

import static br.web.scrumbr.util.Message.addErrorMessage;
import static br.web.scrumbr.util.Message.addInfoMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.acesso.EscopoSessaoBean;
import br.web.scrumbr.bo.ReuniaoEmPeBo;
import br.web.scrumbr.bo.SprintBo;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.ReuniaoEmPe;
import br.web.scrumbr.entity.Usuario;
import br.web.scrumbr.report.GenericReport;
import br.web.scrumbr.util.BuscaNoWebContent;
import br.web.scrumbr.util.DataUtil;
import br.web.scrumbr.util.ManagedBeanHelper;

@ManagedBean
@RequestScoped
public class ReuniaoEmPeBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private ReuniaoEmPe reuniaoEmPe;
	private ReuniaoEmPe reuniaoEmPeFilter;
	private LazyEntityDataModel<ReuniaoEmPe> lazy;
	private List<ReuniaoEmPe> reuniaoEmPes;
	private Usuario usuarioLogado;
	private UrlBean url;
	private Projeto projetoSelecionado;

	@PostConstruct
	public void init() {
		url = new UrlBean();
		reuniaoEmPe = new ReuniaoEmPe();
		reuniaoEmPeFilter = new ReuniaoEmPe();
		lazy = new LazyEntityDataModel<ReuniaoEmPe>(ReuniaoEmPe.class);
		reuniaoEmPes = new ArrayList<ReuniaoEmPe>();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean",
				EscopoSessaoBean.class).getUsuarioLogado();
		
		projetoSelecionado = ManagedBeanHelper.recuperaBean("projetoSessaoBean",
				ProjetoSessaoBean.class).getProjetoSelecionado();
	}

	public void save() {
		try {
			reuniaoEmPe.setDataReuniao(new Date());
			reuniaoEmPe.setUsuario(usuarioLogado);
			reuniaoEmPe.setProjeto(projetoSelecionado);
			ReuniaoEmPeBo.getInstance().save(reuniaoEmPe);
			addInfoMessage("","Cadastrado com sucesso!");
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		reuniaoEmPe = new ReuniaoEmPe();
	}

	public String update() {
		try {
			reuniaoEmPe.setUsuario(usuarioLogado);
			reuniaoEmPe.setProjeto(projetoSelecionado);
			ReuniaoEmPeBo.getInstance().update(reuniaoEmPe);
			addInfoMessage("",
					"Editado com sucesso!");
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente", "");
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
		}
		reuniaoEmPe = new ReuniaoEmPe();
		return url.getLIST_REUNIAO_PE();
	}
	
	public void gerarPDF(ActionEvent ev){
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();
			
			
			List<ReuniaoEmPe> reuniaoEmPe = ReuniaoEmPeBo.getInstance().reuniaoEmPeListReport(reuniaoEmPeFilter);
			
			
			reuniaoEmPeFilter = new ReuniaoEmPe();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/template/imagens/ScrumBR.png"));
			mapa.put("rodape", BuscaNoWebContent.buscaArquivo("/template/imagens/rodape_paisagem.png"));
			mapa.put("filtro",SprintBo.getInstance().dadosFiltro());
			
			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, reuniaoEmPe, "reuniaoEmPe", "Relatório de Reunião "  + DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String list() {
		lazy = ReuniaoEmPeBo.getInstance().reuniaoEmPesLazy(reuniaoEmPeFilter);
		return null;
	}

	public String goToList() {
		return url.getLIST_REUNIAO_PE();
	}

	public String prepareUpdate() {
		return url.getEDIT_REUNIAO_PE();
	}

	public String prepareSave() {
		reuniaoEmPe = new ReuniaoEmPe();
		return url.getCAD_REUNIAO_PE();
	}

	public ReuniaoEmPe getreuniaoEmPe() {
		return reuniaoEmPe;
	}

	public void setreuniaoEmPe(ReuniaoEmPe reuniaoEmPe) {
		this.reuniaoEmPe = reuniaoEmPe;
	}

	public LazyEntityDataModel<ReuniaoEmPe> getLazy() {
		lazy = ReuniaoEmPeBo.getInstance().reuniaoEmPeLazyProProjeto(projetoSelecionado);
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<ReuniaoEmPe> lazy) {
		this.lazy = lazy;
	}

	public void setReuniaoEmPes(List<ReuniaoEmPe> reuniaoEmPes) {
		this.reuniaoEmPes = reuniaoEmPes;
	}

	public ReuniaoEmPe getReuniaoEmPeFilter() {
		return reuniaoEmPeFilter;
	}


	public void setReuniaoEmPe(ReuniaoEmPe reuniaoEmPe) {
		this.reuniaoEmPe = reuniaoEmPe;
	}

	public void setReuniaoEmPeFilter(ReuniaoEmPe reuniaoEmPeFilter) {
		this.reuniaoEmPeFilter = reuniaoEmPeFilter;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UrlBean getUrl() {
		return url;
	}

	public void setUrl(UrlBean url) {
		this.url = url;
	}

	public ReuniaoEmPe getReuniaoEmPe() {
		return reuniaoEmPe;
	}

	public List<ReuniaoEmPe> getReuniaoEmPes() {
		return reuniaoEmPes;
	}

}
