package br.web.scrumbr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	
	public static Date formatarData(String dateString){
		Date data = new Date();
		try {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			data = formatador.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static String formatarData(Date date){
		String data = null;
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		data = formatador.format(date);
		return data;
	}
	 
}
