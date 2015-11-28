package br.web.scrumbr.util;

import javax.faces.context.FacesContext;

public class BuscaNoWebContent {

	public static String buscaArquivo(String path) {
		String logo = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
		return logo;
	}
}
