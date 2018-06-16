package br.com.gestaomaster.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import br.com.gestaomaster.enums.StatusPatrimonio;
import br.com.gestaomaster.enums.TipoExporter;
import br.com.gestaomaster.model.Patrimonio;
import br.com.gestaomaster.model.PatrimonioRN;
import br.com.gestaomaster.util.ReportUtils;

@ManagedBean(name = "patrimonioMB")
@ViewScoped

public class PatrimonioMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Models
	private Patrimonio patrimonio = new Patrimonio();
	private TipoExporter tipoExporterSelecionado;
	private List<Patrimonio> listPatrimonios = null;
	
	private Date dataBaixa;
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	public PatrimonioMB() {
		getSelectAll();
		tipoExporterSelecionado = TipoExporter.PDF;
	}

	public String actionNovo() {
		this.patrimonio = new Patrimonio();
		return "patrimonio_cadastro.xhtml?faces-redict=true";
	}

	public String actionExcluir() {
		new PatrimonioRN().excluir(patrimonio);
		//this.listPatrimonios = null;
		return "patrimonio_lista_cadastro.xhtml?faces-redict=true";
	}

	public String actionReabrir() {
		new PatrimonioRN().reabrir(patrimonio);
		//this.listPatrimonios = null;
		return "patrimonio_lista_baixas.xhtml?faces-redict=true";
	}

	public void actionGravar() {
		FacesMessage msg = null;;
		try {
			if (patrimonio.getPreco_compra().floatValue() <= 0) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preço de compra deve ser maior que 0(zero)!", null);
				addMessage(msg);
			}else if (patrimonio.getVida_util() <= 0) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vida útil deve ser maior que 0(zero)!", null);
				addMessage(msg);
			}else if (patrimonio.getTempo_de_uso() < 0) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tempo de uso não pode ser menmor que 0(zero)!", null);
				addMessage(msg);
			}else if ((patrimonio.getSituacao_do_bem().equals("Usado")) && (patrimonio.getTempo_de_uso() == 0)) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bem USADO, colocar tempo de uso em meses!", null);
				addMessage(msg);
			}else if ((patrimonio.getSituacao_do_bem().equals("Novo")) && (patrimonio.getTempo_de_uso() != 0)) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bem NOVO, não pode ter tempo de uso!", null);
			}else if (patrimonio.getPerc_residual().floatValue() < 0) {
				msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "% de resíduo não pode ser negativo!", null);
				addMessage(msg);
			}else {
				new PatrimonioRN().gravar(patrimonio);
				this.listPatrimonios = null;
				if (patrimonio.getNumero() == null) {
					patrimonio = new Patrimonio();
					msg =  new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", null);
					addMessage(msg);
				}
				else {
					FacesContext.getCurrentInstance().getExternalContext().redirect("patrimonio_lista_cadastro.xhtml?faces-redict=true");
				}
			}
		} catch(Exception err) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, err.getMessage(), err.getMessage());
			addMessage(msg);
		}
	}

	private void addMessage(FacesMessage msg) {
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String actionBaixar() {
		FacesMessage msg = null;;
		if (patrimonio.getBaixa_valor_da_venda().floatValue() < 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor da venda não pode ser negativo!", null);
			addMessage(msg);
			return "";
//		}else if (patrimonio.getBaixa_data_ou_venda().compareTo(patrimonio.getData_compra()) < 0) {
//			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data da baixa/venda não pode ser menor que data de aquisição!", null);
//			addMessage(msg);
//			return "";
		}else {
			new PatrimonioRN().baixar(patrimonio);
			//this.listPatrimonios = null;
			return "patrimonio_lista_baixas.xhtml?faces-redict=true";
		}
	}

	public List<Patrimonio> getSelectAll() {
		this.listPatrimonios = new PatrimonioRN().selectAll();
		return this.listPatrimonios;
	}

	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String actionDeprecia(){
		FacesMessage msg = null;
		if (patrimonio.getPreco_compra().floatValue() <= 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preço de compra deve ser maior que 0(zero)!", null);
			addMessage(msg);
			return "";
		}else if (patrimonio.getVida_util() <= 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vida útil deve ser maior que 0(zero)!", null);
			addMessage(msg);
			return "";
		}else if (patrimonio.getTempo_de_uso() < 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tempo de uso não pode ser menmor que 0(zero)!", null);
			addMessage(msg);
			return "";
		}else if ((patrimonio.getSituacao_do_bem().equals("Usado")) && (patrimonio.getTempo_de_uso() == 0)) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bem USADO, colocar tempo de uso em meses!", null);
			addMessage(msg);
			return "";
		}else if ((patrimonio.getSituacao_do_bem().equals("Novo")) && (patrimonio.getTempo_de_uso() != 0)) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bem NOVO, não pode ter tempo de uso!", null);
			addMessage(msg);
			return "";
		}else if (patrimonio.getPerc_residual().floatValue() < 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "% de resíduo não pode ser negativo!", null);
			addMessage(msg);
			return "";
		}else if (patrimonio.getBaixa_valor_da_venda().floatValue() < 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor da venda/doação/sinistro não pode ser negativo!", null);
			addMessage(msg);
			return "";
		}else if (patrimonio.getData_compra().compareTo(patrimonio.getBaixa_data_ou_venda()) > 0) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data de venda/doação/sinistro não pode ser menor que data de aquisição!", null);
			addMessage(msg);
			return "";
		}else {
			new PatrimonioRN().depreciar(patrimonio);
			this.listPatrimonios = null;
			return "patrimonio_deprecia_show.xhmtl?faces-redict=true";
		}
	}
	
	public String novoPatrimonio() {
		patrimonio = new Patrimonio();
		listPatrimonios = new ArrayList<>();
		return "patrimonio_cadastro.xhmtl?faces-redict=true";
	}
	
	/**
	 */
	public void downloadRelatorioBensPatrimoniais() {
		try {
			// Define o status de filtro de pesquisa
			StatusPatrimonio status = dataBaixa == null ? StatusPatrimonio.BAIXADO : StatusPatrimonio.ATIVO;
			List<Patrimonio> patrimonios = new PatrimonioRN().selectAll(status);
			if(patrimonios != null && patrimonios.size() > 0) {
				patrimonios.forEach(pat -> {
					// Verifica se e pra imprimir o relatorio de ativos
					if(StatusPatrimonio.ATIVO.equals(status)) {
						pat.setBaixa_data_ou_venda(dataBaixa);
						pat.setBaixa_valor_da_venda(valorVenda);
						pat.setAtivo(1);
					}
					new PatrimonioRN().depreciar(pat);
				});
				ReportUtils.downloadRelatorioBensPatrimoniais(patrimonios, status, tipoExporterSelecionado);		
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum registro encontrado", null));
			}
			dataBaixa = null;
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	public TipoExporter[] getTiposExporter() {
		return TipoExporter.values();
	}

	public TipoExporter getTipoExporterSelecionado() {
		return tipoExporterSelecionado;
	}

	public void setTipoExporterSelecionado(TipoExporter tipoExporterSelecionado) {
		this.tipoExporterSelecionado = tipoExporterSelecionado;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Date getDataMinimaBaixa() {
		return new Date();
	}
	
}
