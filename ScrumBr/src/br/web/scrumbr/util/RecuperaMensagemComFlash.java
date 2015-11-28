package br.web.scrumbr.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class RecuperaMensagemComFlash {
	
   public static Flash flashScope (){
	return FacesContext.getCurrentInstance().getExternalContext().getFlash();
   }
}
