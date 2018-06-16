package br.com.gestaomaster.model;

import java.util.List;

import br.com.gestaomaster.enums.StatusPatrimonio;

public class PatrimonioRN {

	
	
	public void excluir(Patrimonio patrimonio) {
		new PatrimonioDAO().excluir(patrimonio);
	}

	public void reabrir(Patrimonio patrimonio) {
		new PatrimonioDAO().reabrir(patrimonio);
	}

	public void gravar(Patrimonio patrimonio) {
		if (patrimonio.getNumero() == null) {
			new PatrimonioDAO().incluir(patrimonio);
		} else {
			new PatrimonioDAO().alterar(patrimonio);
		}
	}

	public void baixar(Patrimonio patrimonio) {
		new PatrimonioDAO().baixar(patrimonio);
	}

	public List<Patrimonio> selectAll() {
		return new PatrimonioDAO().selectAll(StatusPatrimonio.TODOS);
	}
	
	public List<Patrimonio> selectAll(StatusPatrimonio status) {
		return new PatrimonioDAO().selectAll(status);
	}

	public void depreciar(Patrimonio patrimonio){
		 new PatrimonioDAO().deprecia(patrimonio);
	}
	

}
