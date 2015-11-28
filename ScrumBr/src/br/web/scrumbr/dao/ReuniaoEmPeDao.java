package br.web.scrumbr.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.web.scrumbr.entity.Projeto;
import br.web.scrumbr.entity.ReuniaoEmPe;

public class ReuniaoEmPeDao {

	private static ReuniaoEmPeDao singleton;

	public static synchronized ReuniaoEmPeDao getInstance() {
		if (singleton == null) {
			singleton = new ReuniaoEmPeDao();
		}
		return singleton;
	}
	
/**
 * 
 * @param reuniaoEmPeFilter
 * @return
 */
	public LazyEntityDataModel<ReuniaoEmPe> reuniaoEmPesLazy(ReuniaoEmPe reuniaoEmPeFilter) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (reuniaoEmPeFilter.getDataReuniaoInicio() != null
				&& reuniaoEmPeFilter.getDataReuniaoFim() != null) {
			restrictions.add(Restrictions.between("dataReuniao",
					reuniaoEmPeFilter.getDataReuniaoInicio(),
					reuniaoEmPeFilter.getDataReuniaoFim()));
		}

		if (reuniaoEmPeFilter.getDataReuniaoInicio() != null
				&& reuniaoEmPeFilter.getDataReuniaoFim() == null) {
			reuniaoEmPeFilter.setDataReuniaoFim(reuniaoEmPeFilter
					.getDataReuniaoInicio());
			restrictions.add(Restrictions.between("dataReuniao",
					reuniaoEmPeFilter.getDataReuniaoInicio(),
					reuniaoEmPeFilter.getDataReuniaoFim()));
		}

		if (reuniaoEmPeFilter.getDataReuniaoInicio() == null
				&& reuniaoEmPeFilter.getDataReuniaoFim() != null) {
			reuniaoEmPeFilter.setDataReuniaoInicio(reuniaoEmPeFilter
					.getDataReuniaoFim());
			restrictions.add(Restrictions.between("dataReuniao",
					reuniaoEmPeFilter.getDataReuniaoInicio(),
					reuniaoEmPeFilter.getDataReuniaoFim()));
		}

		if (reuniaoEmPeFilter.getParticipante()!= null) {
			restrictions.add(Restrictions.eq("participante", reuniaoEmPeFilter.getParticipante()));
		}
		return new LazyEntityDataModel<ReuniaoEmPe>(ReuniaoEmPe.class, null, null, restrictions, null);

	}
	
	public LazyEntityDataModel<ReuniaoEmPe> reuniaoEmPeLazyProProjeto(Projeto projetoSelecionado) {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("projeto", projetoSelecionado));
		
		return new LazyEntityDataModel<ReuniaoEmPe>(ReuniaoEmPe.class,
				null, null, restrictions, null);
	}


	public List<ReuniaoEmPe> reuniaoEmPes(String reuniaoEmPe) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		return GenericDao.findAll(ReuniaoEmPe.class);
	}
	
	private StringBuilder dadosFiltro = new StringBuilder();
	
	public List<ReuniaoEmPe> reuniaoEmPeListReport(ReuniaoEmPe reuniaoEmPe) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (reuniaoEmPe.getUsuario().getNome() != null && !reuniaoEmPe.getUsuario().getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", reuniaoEmPe.getUsuario().getNome(), MatchMode.ANYWHERE));
			
			dadosFiltro.append("NOME: "+reuniaoEmPe.getUsuario());
		}
		return GenericDao.findAllByAttributesRestrictions(ReuniaoEmPe.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}	
	
	
}
