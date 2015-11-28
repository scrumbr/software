package br.web.scrumbr.util;

public class TitleTarefaTaskBoard {
	
	
	
	public static String text(String titulo){
		String aux;
		
		if(titulo.length() > 7){
			return aux = titulo.substring(0, 5) + "...";
		}else{
			return titulo;
		}
	}
	
}
