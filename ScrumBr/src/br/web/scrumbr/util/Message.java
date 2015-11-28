package br.web.scrumbr.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;

public class Message implements Serializable{
 
	private static final long serialVersionUID = -1917252814727199200L;
	
	/**
	 * Retorna o ResourceBundle da aplicação.
	 * 
	 * @return ResourceBundle de acordo com o Locale da aplicação
	 */
	private static ResourceBundle getBundle() {
		return FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msgs");
	}

	/**
	 * Cria uma mensagem a partir do arquivo de resources e dos parametros
	 * passados.
	 * 
	 * @param messageKey
	 *            chave de mensagem
	 * @param parametros
	 *            Parametros
	 * @return mensagem formatada
	 */
	public static String getMessage(String messageKey, Object... parametros) {
		return MessageFormat.format(getBundle().getString(messageKey),
				parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos.
	 * 
	 * @param severity
	 *            Severidade do erro descrita em {@link FacesMessage}
	 * @param summaryKey
	 *            Chave da mensagem de erro
	 * @param parametros
	 *            Parâmetros da mensagem
	 * @return mensagem criada
	 */
	public static FacesMessage createMessage(Severity severity,
			String summaryKey, Object... parametros) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(severity);
		if (summaryKey != null && !summaryKey.equals("")) {
			message.setSummary(getMessage(summaryKey, parametros));
		}
		return message;
	}

	/**
	 * Cria uma memsagem sem chave do arquivo de recursos e adiciona ao escopo
	 * da requisição atual
	 * 
	 * @param severity
	 *            Severidade do erro descrita em {@link FacesMessage}
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summary
	 *            Mensagem de erro
	 */
	private static void createMessage(Severity severity, String componetId,
			String summary) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(severity);
		message.setSummary(summary);
		FacesContext.getCurrentInstance().addMessage(componetId, message);
	}

	/**
	 * Cria uma memsagem a partir de uma chave do arquivo de recursos e adiciona
	 * ao escopo da requisição atual
	 * 
	 * @param severity
	 *            Severidade do erro descrita em {@link FacesMessage}
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summaryKey
	 *            Chave de mensagem de erro
	 * @param parametros
	 *            parâmetros para a mensagem
	 */
	public static void addMessage(Severity severity, String componetId,
			String summaryKey, Object... parametros) {
		FacesMessage message = createMessage(severity, summaryKey, parametros);
		FacesContext.getCurrentInstance().addMessage(componetId, message);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de erro predefinida
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summaryKey
	 *            Chave do detalhe da mensagem de erro
	 * @param parametros
	 *            parâmetros para mensagem de erro
	 */
	public static void addErrorMessage(String componetId, String summaryKey,
			Object... parametros) {
		addMessage(FacesMessage.SEVERITY_ERROR, componetId, summaryKey,
				parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de informação predefinida
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summaryKey
	 *            Chave do detalhe da mensagem de erro
	 * @param parametros
	 *            parâmetros para mensagem de erro
	 */
	public static void addInfoMessage(String componetId, String summaryKey, Object... parametros) {
		addMessage(FacesMessage.SEVERITY_INFO, componetId, summaryKey,
				parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de aviso predefinida
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summaryKey
	 *            Chave do detalhe da mensagem de erro
	 * @param parametros
	 *            parâmetros para mensagem de erro
	 */
	public static void addWarnMessage(String componetId, String summaryKey,
			Object... parametros) {
		addMessage(FacesMessage.SEVERITY_WARN, componetId, summaryKey,
				parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de erro fatal predefinida
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem serâ associada
	 * @param summaryKey
	 *            Chave do detalhe da mensagem de erro
	 * @param parametros
	 *            parâmetros para mensagem de erro
	 */
	public static void addFatalMessage(String componetId, String summaryKey,
			Object... parametros) {
		addMessage(FacesMessage.SEVERITY_FATAL, componetId, summaryKey,
				parametros);
	}

	public static void addErrorMessage(String componetId,
			Set<ConstraintViolation<?>> violations) {
		for (ConstraintViolation<?> violation : violations) {
			createMessage(FacesMessage.SEVERITY_ERROR, componetId,
					violation.getMessage());
		}
	}

	public static void addWarnMessage(String summary, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                (FacesMessage.SEVERITY_WARN, summary, detail));
	}
	
	public static void addInfoMessage(String summary, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                (FacesMessage.SEVERITY_INFO, summary, detail));
	}
	
	public static void addErrorMessage(String componetId, String[] messages) {
		for (String message : messages) {
			addErrorMessage(componetId, message);
		}
	}

	public static void addErrorMessage(String componetId, String message) {
		createMessage(FacesMessage.SEVERITY_ERROR, componetId, message);
	}

	public static void setFatalMessage(String sumary, String details) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, sumary,
				details);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public static void setErrorMessage(String sumary, String details) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, sumary,
				details);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public static void setInfoMessage(String sumary, String details) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, sumary,
				details);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}

	public static void setWarnMessage(String sumary, String details) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, sumary,
				details);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
}
