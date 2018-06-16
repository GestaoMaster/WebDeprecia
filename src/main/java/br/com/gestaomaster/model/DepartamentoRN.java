package br.com.gestaomaster.model;

import java.util.List;

public class DepartamentoRN {

	public void excluir(Departamento departamento) {
		new DepartamentoDAO().excluir(departamento);
	}

	public void gravar(Departamento departamento) {
		if (departamento.getId() == null) {
			new DepartamentoDAO().incluir(departamento);
		} else {
			new DepartamentoDAO().alterar(departamento);
		}
	}

	public List<Departamento> selectAll() {
		return new DepartamentoDAO().selectAll();
	}
}
