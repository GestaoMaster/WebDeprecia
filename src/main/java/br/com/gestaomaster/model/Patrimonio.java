package br.com.gestaomaster.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Patrimonio implements Serializable {


	private static final long serialVersionUID = 1L;


	private Long numero;
	private String descricao;
	private String marca;
	private BigDecimal preco_compra;
	private Date data_compra;
	private Long vida_util;
	private String situacao_do_bem;
	private Long tempo_de_uso;
	private Long turnos_de_trabalho;
	private String tipo_do_bem;
	private BigDecimal perc_residual;
	private String dep_sigla;
	private String baixa_tipo;
	private Date baixa_data_ou_venda;
	private BigDecimal baixa_valor_da_venda;
	private int calc_qtdmeses;
	private BigDecimal calc_DA;
	private BigDecimal calc_VC;
	private BigDecimal calc_GP;
	private String status;
	private int ativo;
	
	
	public Patrimonio() {
		super();
	}
	
	public Patrimonio(Long numero) {
		this.numero = numero;
	}

	public Patrimonio(Long    numero            , String  descricao          , String marca               , BigDecimal   preco_compra,
			          Date    data_compra       , Long    vida_util          , String situacao_do_bem     , Long    tempo_de_uso,
			          Long    turnos_de_trabalho, String  tipo_do_bem        , BigDecimal  perc_residual       , String  dep_sigla   ,
			          String  baixa_tipo        , Date    baixa_data_ou_venda, BigDecimal  baixa_valor_da_venda) {
    		super();
    		this.setNumero(numero);
    		this.setDescricao(descricao);
    		this.setMarca(marca);
    		this.setPreco_compra(preco_compra);
    		this.setData_compra(data_compra);
    		this.setVida_util(vida_util);
    		this.setSituacao_do_bem(situacao_do_bem);
    		this.setTempo_de_uso(tempo_de_uso);
    		this.setTurnos_de_trabalho(turnos_de_trabalho);
    		this.setTipo_do_bem(tipo_do_bem);
    		this.setPerc_residual(perc_residual);
    		this.setDep_sigla(dep_sigla);
    		this.setBaixa_tipo(baixa_tipo);
    		this.setBaixa_data_ou_venda(baixa_data_ou_venda);
    		this.setBaixa_valor_da_venda(baixa_valor_da_venda);
   	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public BigDecimal getPreco_compra() {
		return preco_compra;
	}

	public void setPreco_compra(BigDecimal preco_compra) {
		this.preco_compra = preco_compra;
	}

	public Date getData_compra() {
		return data_compra;
	}

	public void setData_compra(Date data_compra) {
		this.data_compra = data_compra;
	}

	public Long getVida_util() {
		return vida_util;
	}

	public void setVida_util(Long vida_util) {
		this.vida_util = vida_util;
	}

	public String getSituacao_do_bem() {
		return situacao_do_bem;
	}

	public void setSituacao_do_bem(String situacao_do_bem) {
		this.situacao_do_bem = situacao_do_bem;
	}

	public Long getTempo_de_uso() {
		return tempo_de_uso;
	}

	public void setTempo_de_uso(Long tempo_de_uso) {
		this.tempo_de_uso = tempo_de_uso;
	}

	public Long getTurnos_de_trabalho() {
		return turnos_de_trabalho;
	}

	public void setTurnos_de_trabalho(Long turnos_de_trabalho) {
		this.turnos_de_trabalho = turnos_de_trabalho;
	}

	public String getTipo_do_bem() {
		return tipo_do_bem;
	}

	public void setTipo_do_bem(String tipo_do_bem) {
		this.tipo_do_bem = tipo_do_bem;
	}

	public BigDecimal getPerc_residual() {
		return perc_residual;
	}

	public void setPerc_residual(BigDecimal perc_residual) {
		this.perc_residual = perc_residual;
	}

	public String getDep_sigla() {
		return dep_sigla;
	}

	public void setDep_sigla(String dep_sigla) {
		this.dep_sigla = dep_sigla;
	}

	public String getBaixa_tipo() {
		return baixa_tipo;
	}

	public void setBaixa_tipo(String baixa_tipo) {
		this.baixa_tipo = baixa_tipo;
	}

	public Date getBaixa_data_ou_venda() {
		return baixa_data_ou_venda;
	}

	public void setBaixa_data_ou_venda(Date baixa_data_ou_venda) {
		this.baixa_data_ou_venda = baixa_data_ou_venda;
	}

	public BigDecimal getBaixa_valor_da_venda() {
		return baixa_valor_da_venda;
	}

	public void setBaixa_valor_da_venda(BigDecimal baixa_valor_da_venda) {
		this.baixa_valor_da_venda = baixa_valor_da_venda;
	}

	public int getCalc_qtdmeses() {
		return calc_qtdmeses;
	}

	public void setCalc_qtdmeses(int calc_qtdmeses) {
		this.calc_qtdmeses = calc_qtdmeses;
	}

	public BigDecimal getCalc_DA() {
		return calc_DA;
	}

	public void setCalc_DA(BigDecimal calc_DA) {
		this.calc_DA = calc_DA;
	}

	public BigDecimal getCalc_VC() {
		return calc_VC;
	}

	public void setCalc_VC(BigDecimal calc_VC) {
		this.calc_VC = calc_VC;
	}

	public BigDecimal getCalc_GP() {
		return calc_GP;
	}

	public void setCalc_GP(BigDecimal calc_GP) {
		this.calc_GP = calc_GP;
	}

	public String getStatus() {
		if (getBaixa_data_ou_venda() == null) {
			status = "Ativo";
		} else {
			status = "Baixado";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal arredondar(BigDecimal valor, int casas) {
	    return valor.setScale(casas, RoundingMode.HALF_EVEN);
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	
}
