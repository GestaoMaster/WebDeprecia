package br.com.gestaomaster.web;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import br.com.gestaomaster.model.Departamento;
import br.com.gestaomaster.model.DepartamentoRN;

@ManagedBean (name = "departamentoMB")
@ViewScoped
public class DepartamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private Departamento departamento = new Departamento();
	private List<Departamento> listDepartamentos = null;

	public String actionNovo() {
		this.departamento = new Departamento();
		return "departamento_cadastro.xhtml?faces-redict=true";
	}
		
	public String actionExcluir() {
		new DepartamentoRN().excluir(departamento);
			this.listDepartamentos = null;
			return "departamento_lista.xhtml?faces-redict=true";
	}

	public void actionGravar() {
		FacesMessage msg = null;;
		try {
			new DepartamentoRN().gravar(departamento);
			this.listDepartamentos = null;
			if (departamento.getId() == null) {
				departamento = new Departamento();
  				msg =  new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", null);
  				addMessage(msg);
			}
			else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("departamento_lista.xhtml?faces-redict=true");
			}
			
		} catch(Exception err) {
			msg =  new FacesMessage(FacesMessage.SEVERITY_ERROR, err.getMessage(), err.getMessage());
			addMessage(msg);
		}
	}
	
	private void addMessage(FacesMessage msg) {
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
			
	public List<Departamento> getSelectAll(){
		if (this.listDepartamentos == null) {
		    this.listDepartamentos =  new DepartamentoRN().selectAll();
		}
		return this.listDepartamentos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
}
