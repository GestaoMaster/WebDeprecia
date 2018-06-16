package br.com.gestaomaster.enums;

public enum TipoExporter {
	
	PDF("PDF"), XLSX("Excel"), HTML("HTML");
	
	private String descricao;

	private TipoExporter(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
