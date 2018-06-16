package br.com.gestaomaster.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.gestaomaster.enums.StatusPatrimonio;
import br.com.gestaomaster.enums.TipoExporter;
import br.com.gestaomaster.model.Patrimonio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class ReportUtils {

	public static void downloadReport(String caminhoReport, List<?> dataSource, 
			Map<String, Object> params, TipoExporter typeExporter) throws JRException, IOException {
		
		// Cria o Response da resposta para renderizar
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		// Cria o dataSource
		JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
		// Cria o JasperPrint
		JasperPrint print = JasperFillManager.fillReport(caminhoReport, params, jrDataSource);
		
		// Cria o nome do arquivo que vai aparecer no downloads do navegador
		String nomeArquivo = "bensPatrimoniais";
		
		// Cria o exporter generico
		JRExporter exporter = null;
		
		// Verifica o tipo a ser exportado
		if(TipoExporter.PDF.equals(typeExporter)) {
			exporter = new JRPdfExporter();
			response.setContentType("application/pdf"); // Define o contentType da response
			nomeArquivo = nomeArquivo.concat(".pdf"); // Define a extensão do arquivo de relatorio
		} else if(TipoExporter.XLSX.equals(typeExporter)) {
			exporter = new JRXlsxExporter();
			response.setContentType("application/xlsx"); // Define o contentType da response
			nomeArquivo = nomeArquivo.concat(".xlsx"); // Define a extensão do arquivo de relatorio
		} else if(TipoExporter.HTML.equals(typeExporter)) {
			exporter = new JRHtmlExporter();
			response.setContentType("application/html"); // Define o contentType da response
			nomeArquivo = nomeArquivo.concat(".html"); // Define a extensão do arquivo de relatorio
		} else {
			throw new IllegalArgumentException("Tipo de arquivo não suportado!");
		}
		// Define o header da response
		response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);
		
		// Seta os parametros
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.exportReport();
		
		// Forca o JSF renderizar a resposta
		context.responseComplete();
	}
	
	public static void downloadRelatorioBensPatrimoniais(List<Patrimonio> dataSource,
			StatusPatrimonio status, TipoExporter typeExporter) throws JRException, IOException {
		// Cria o titulo do relatorio dinamicamente
		String tituloReport = "Bens patrimoniais - ";
		if(StatusPatrimonio.ATIVO.equals(status)) {
			tituloReport = tituloReport.concat("Ativos");
		} else if(StatusPatrimonio.BAIXADO.equals(status)) {
			tituloReport = tituloReport.concat("Baixados");			
		} else {
			throw new IllegalArgumentException("Status de patrimônio não suportado!");
		}
		
		// Pega o caminho absoluto do relatorio.jasper
		final String caimhoReport = getRealPathAplicacao(ConstantesUtils.PATH_REPORT_BENS_PATRIMONISIAS);
		
		// Pega o caminho absoluto da logo do report
		final String caminhoLogomarca = getRealPathAplicacao(ConstantesUtils.PATH_LOGO_REPORT);
		
		Map<String, Object> params = new HashMap<>();
		params.put(ConstantesUtils.NAME_PARAM_LOGO_REPORT, caminhoLogomarca);
		params.put(ConstantesUtils.NAME_PARAM_TITLE_REPORT, tituloReport);
		
		downloadReport(caimhoReport, dataSource, params, typeExporter);
	}
	
	public static String getRealPathAplicacao(String relativePath) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);
	}
	
}
