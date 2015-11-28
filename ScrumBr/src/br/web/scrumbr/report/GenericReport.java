package br.web.scrumbr.report;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GenericReport {
	 
	public static void gerarPdfComJRBeanCollectionDataSource(Map<String, Object> mapa,  List<?> lista, String nomeArquivoJasper, String nomeDoArquivoDeSaida, boolean download){
		reportFactory(mapa, lista, nomeArquivoJasper, nomeDoArquivoDeSaida, download, false);
	}

	public static void gerarPdfComConnection(Map<String, Object> mapa, String nomeArquivoJasper, String nomeDoArquivoDeSaida, boolean download){
		reportFactory(mapa, null, nomeArquivoJasper, nomeDoArquivoDeSaida, download, true);
	}
	
	private static void reportFactory(Map<String, Object> mapa, List<?> lista, String nomeArquivoJasper, String nomeDoArquivoDeSaida, boolean download, boolean connection) {
		try {
			Map<String, Object> map = mapa;

			File jasper = new File(FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/report/" + nomeArquivoJasper + ".jasper"));
			
			JasperPrint jasperPrint = null;
			
			if(connection){
				jasperPrint = JasperFillManager.fillReport(jasper.getPath(), map, getConnectioDataSource());
			}else{
				jasperPrint = JasperFillManager.fillReport(jasper.getPath(), map, new JRBeanCollectionDataSource(lista));
			}

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			
			if(download){
				 response.addHeader("content-disposition", "attachment; filename="+nomeDoArquivoDeSaida+".pdf");
			}
		
			ServletOutputStream stream;
			stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection getConnectioDataSource(){
		Connection con = null;
		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/scrumbr");
			con = ds.getConnection();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	return con;
	}
	
}