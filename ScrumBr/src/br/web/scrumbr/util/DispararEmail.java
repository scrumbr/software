package br.web.scrumbr.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.web.scrumbr.entity.Usuario;

public class DispararEmail {

	public static void dispararEmailHtmlAssinar(Usuario usuario)
			throws EmailException {

		HtmlEmail email = new HtmlEmail();
		System.out.println("alterando hostname...");
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);

		email.addTo(usuario.getEmail());
		email.setFrom("scrumbragil@gmail.com", "ScrumBR");
		email.setSubject("Bem-Vindo ao ScrumBR");

		email.setHtmlMsg("<HTML> <HEAD> <TITLE>Seu t�tulo aqui</TITLE> </HEAD>"
				+ "	<BODY BGCOLOR='FFFFFF'> 	"
				+ "<center><h2> ScrumBR - Facilitando no gerenciamento de seus projetos!</h2></center>"
				+ "<HR color='#479F03'> "
				+ "<center><h4><font color='#479F03'> BEM VINDO AO SCRUMBR</font></h4></center>"
				+ "	   <br/>   <br/> "
				+ "	Ol� <b>"
				+ usuario.getNome()
				+ "</b>,"
				+ "seja bem-vindo ao ScrumHalf! "
				+ "Agora voc� faz parte do grupo de usu�rios do ScrumBR. "
				+ "Esperamos que a nossa ferramenta facilite a sua experi�ncia "
				+ "com projetos �geis."
				+ " <br/> Segue informa��es para seu primeiro acesso!"
				+ "<ul style='list-style-type:square'> 	"
				+ "<li><b>ORGANIZA��O:</b> "
				+ usuario.getEmpresa()
				+ "</li>"
				+ "<li><b>LOGIN:</b> "
				+ usuario.getLogin()
				+ "</li>"
				+ "<li><b>SENHA:</b> "
				+ usuario.getSenha()
				+ "</li>	</ul>"

				+ "<HR color='#479F03'>"
				+ "<br/> <br/> <b>OBS: </b>Este � um e-mail autom�tico. N�o � necess�rio respond�-lo. "
				+ "	</BODY> </HTML> ");

		System.out.println("autenticando...");
		email.setSSL(true);
		email.setAuthentication("andreygoisfcrs@gmail.com", "ANDrey342210141407");
		System.out.println("enviando...");
		email.send();
		System.out.println("Email enviado!");
	}
	
	public static void dispararEmailHtmlCadastro(Usuario usuario)
			throws EmailException {

		HtmlEmail email = new HtmlEmail();
		System.out.println("alterando hostname...");
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);

		email.addTo(usuario.getEmail());
		email.setFrom("andreygoisfcrs@gmail.com", "ScrumBR");
		email.setSubject("Bem-Vindo ao ScrumBR");

		email.setHtmlMsg("<HTML> <HEAD> <TITLE>Seu t�tulo aqui</TITLE> </HEAD>"
				+ "	<BODY BGCOLOR='FFFFFF'> 	"
				+ "<center><h2> ScrumBR - Facilitando no gerenciamento de seus projetos!</h2></center>"
				+ "<HR color='#479F03'> "
				+ "<center><h4><font color='#479F03'> BEM VINDO AO SCRUMBR</font></h4></center>"
				+ "	   <br/>   <br/> "
				+ "	Ol� <b>"
				+ usuario.getNome()
				+ "</b>,"
				+ "seja bem-vindo ao ScrumHalf! "
				+ "Agora voc� faz parte do grupo de usu�rios do ScrumBR. "
				+ "Esperamos que a nossa ferramenta facilite a sua experi�ncia "
				+ "com projetos �geis."
				+ " <br/> Segue informa��es para seu primeiro acesso!"
				+ "<ul style='list-style-type:square'> 	"
				+ "<li><b>ORGANIZA��O:</b> "
				+ usuario.getEmpresa()
				+ "</li>"
				+ "<li><b>LOGIN:</b> "
				+ usuario.getLogin()
				+ "</li>"
				+ "<li><b>SENHA:</b> "
				+ usuario.getSenha()
				+ "</li>	</ul>"
				+ "<br/> Aguarde o ScrumMaster adicionar voc� aos projetos da <b>" + usuario.getEmpresa() +". <b/>"	
				+ "<HR color='#479F03'>"
				+ "<br/> <br/> <b>OBS: </b>Este � um e-mail autom�tico. N�o � necess�rio respond�-lo. "
				+ "	</BODY> </HTML> ");

		System.out.println("autenticando...");
		email.setSSL(true);
		email.setAuthentication("andreygoisfcrs@gmail.com", "ANDrey342210141407");
		System.out.println("enviando...");
		email.send();
		System.out.println("Email enviado!");
	}


}
