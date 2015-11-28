package br.web.scrumbr.util;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

public class ManagedBeanHelper {

	public static <T> T recuperaBean(String nomeDoBean, Class<T> targetClass) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context,
				"#{" + nomeDoBean + "}", targetClass);
		
		
	}

	public static <T> void destroiBean(String nomeDoBean, Class<T> targetClass) {
		ValueExpression valueExpression = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{" + nomeDoBean + "}", targetClass);

		valueExpression.setValue(FacesContext.getCurrentInstance()
				.getELContext(), null);

	}

}
