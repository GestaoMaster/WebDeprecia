package br.com.gestaomaster.model;

import java.io.Serializable;

public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long   id;
	private String sigla;
	private String nome;
	private String responsavel;

	public Departamento() {
		super();
	}

	public Departamento(Long id, String sigla, String nome, String responsavel) {
		super();
		this.id          = id;
		this.sigla       = sigla;
		this.nome        = nome;
		this.responsavel = responsavel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

}
