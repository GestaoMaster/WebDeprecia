package br.com.gestaomaster.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.gestaomaster.util.ConnectionFactory;

public class DepartamentoDAO extends ConnectionFactory {

	private static final long serialVersionUID = 1L;
		
	public void alterar(Departamento departamento) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE departamento SET sigla = ?, nome = ?, responsavel = ? WHERE id = ?";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, departamento.getSigla());
			ps.setString(2, departamento.getNome());
			ps.setString(3, departamento.getResponsavel());
			ps.setLong(4, departamento.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao alterar: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}

	public void excluir(Departamento departamento) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM departamento WHERE id = ?";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setLong(1, departamento.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao excluir: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}

	
	public void incluir(Departamento departamento) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO departamento (sigla, nome, responsavel) VALUES (?, ?, ?)";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, departamento.getSigla());
			ps.setString(2, departamento.getNome());
			ps.setString(3, departamento.getResponsavel());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao inserir: " + e.getMessage());
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		} finally {
			close(con, ps);
		}
	}
	
	public List<Departamento> selectAll(){
		List<Departamento> lsDepartamentos = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id, sigla, nome, responsavel FROM departamento Order by nome";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			lsDepartamentos = new ArrayList<Departamento>();
			while (rs.next()) {
				Departamento e = new Departamento();
				e.setId(rs.getLong("id"));
				e.setSigla(rs.getString("sigla"));
				e.setNome(rs.getString("nome"));
				e.setResponsavel(rs.getString("responsavel"));
				lsDepartamentos.add(e);
			}
		} catch (Exception e) {
			System.err.println("Erro ao selecionar: " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(con, ps, rs);
		}
		return lsDepartamentos;
	}

	public void selecionaUM(Departamento departamento) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "SELECT sigla, nome FROM departamento WHERE sigla = ?";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, departamento.getSigla());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao selecionar: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}
	
}
