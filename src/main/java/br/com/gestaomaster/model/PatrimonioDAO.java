package br.com.gestaomaster.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.gestaomaster.enums.StatusPatrimonio;
import br.com.gestaomaster.util.ConnectionFactory;

public class PatrimonioDAO extends ConnectionFactory implements Serializable{

	private static final long serialVersionUID = 1L;

	private BigDecimal baseDivisao = new BigDecimal("100.00"); 
	
	public void alterar(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE BemPatrimonial SET descricao          = ?, marca              = ?, preco_compra       = ?,"
				                             + "data_compra        = ?, vida_util          = ?, situacao_do_bem    = ?,"
			                                 + "tempo_de_uso       = ?, turnos_de_trabalho = ?, tipo_do_bem        = ?,"
			                                 + "perc_residual      = ?, dep_sigla          = ?"
			                                 + " WHERE numero = ?";
		try { 
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, patrimonio.getDescricao());
			ps.setString(2, patrimonio.getMarca());
			ps.setBigDecimal(3, patrimonio.getPreco_compra());
			ps.setDate(4, new Date(patrimonio.getData_compra().getTime()));
			ps.setLong(5, patrimonio.getVida_util());
			ps.setString(6, patrimonio.getSituacao_do_bem());
			ps.setLong(7, patrimonio.getTempo_de_uso());
			ps.setLong(8, patrimonio.getTurnos_de_trabalho());
			ps.setString(9, patrimonio.getTipo_do_bem());
			ps.setBigDecimal(10, patrimonio.getPerc_residual());
			ps.setString(11, patrimonio.getDep_sigla());
			ps.setLong(12, patrimonio.getNumero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao alterar: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}

	public void excluir(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM BemPatrimonial WHERE numero = ?";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setLong(1, patrimonio.getNumero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao excluir: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}

	public void reabrir(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE BemPatrimonial SET baixa_tipo  = null, baixa_data_ou_venda = null, baixa_valor_da_venda = null"
 			                                 + " WHERE numero = ?";
		try { 
			con = open();
			ps = con.prepareStatement(sql);
			ps.setLong(1, patrimonio.getNumero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao reabrir: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}

	public void baixar(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE BemPatrimonial SET baixa_tipo  = ?, baixa_data_ou_venda = ?, baixa_valor_da_venda = ?"
			                                 + " WHERE numero = ?";
		try { 
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, patrimonio.getBaixa_tipo());
			ps.setDate(2, new Date(patrimonio.getBaixa_data_ou_venda().getTime()));
			ps.setBigDecimal(3, patrimonio.getBaixa_valor_da_venda());
			ps.setLong(4, patrimonio.getNumero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao baixar: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}
	
	public void incluir(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO BemPatrimonial(descricao    , marca             , preco_compra   ,"
				                              + "data_compra  , vida_util         , situacao_do_bem,"
				                              + "tempo_de_uso , turnos_de_trabalho, tipo_do_bem    ,"
				                              + "perc_residual, dep_sigla         )"
				                              + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString( 1, patrimonio.getDescricao());
			ps.setString( 2, patrimonio.getMarca());
			ps.setBigDecimal ( 3, patrimonio.getPreco_compra());
			ps.setDate(4, new Date(patrimonio.getData_compra().getTime()));
			ps.setLong  ( 5, patrimonio.getVida_util());
			ps.setString( 6, patrimonio.getSituacao_do_bem());
			ps.setLong  ( 7, patrimonio.getTempo_de_uso());
			ps.setLong  ( 8, patrimonio.getTurnos_de_trabalho());
			ps.setString( 9, patrimonio.getTipo_do_bem());
			ps.setBigDecimal (10, patrimonio.getPerc_residual());
			ps.setString(11, patrimonio.getDep_sigla());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao inserir: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}
	
	public List<Patrimonio> selectAll(StatusPatrimonio status){
		List<Patrimonio> lsPatrimonios = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT numero         , descricao          , marca               ,"
				          + "preco_compra   , data_compra        , vida_util           ,"
				          + "situacao_do_bem, tempo_de_uso       , turnos_de_trabalho  ,"
				          + "tipo_do_bem    , perc_residual      , dep_sigla           ,"
				          + "baixa_tipo     , baixa_data_ou_venda, baixa_valor_da_venda"
                          + " FROM BemPatrimonial";
		
		// Verifica o status para filtrar os patrimonios
		// Where Abertos
		if(StatusPatrimonio.ATIVO.equals(status)) {
			sql += " where baixa_tipo is null and baixa_data_ou_venda is null and baixa_valor_da_venda is null";
		}
		// Where Fechados
		else if(StatusPatrimonio.BAIXADO.equals(status)) {
			sql += " where baixa_tipo is not null and baixa_data_ou_venda is not null and baixa_valor_da_venda is not null";
		}
		// Ordena
		sql += " Order by descricao";
		
		try {
			con = open();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			lsPatrimonios = new ArrayList<Patrimonio>();
			while (rs.next()) {
				Patrimonio e = new Patrimonio();
				e.setNumero(rs.getLong("numero"));
				e.setDescricao(rs.getString("descricao"));
				e.setMarca(rs.getString("marca"));
				e.setPreco_compra(rs.getBigDecimal("preco_compra"));
	        	e.setData_compra(rs.getDate("data_compra"));
				e.setVida_util(rs.getLong("vida_util"));
				e.setSituacao_do_bem(rs.getString("situacao_do_bem"));
				e.setTempo_de_uso(rs.getLong("tempo_de_uso"));
				e.setTurnos_de_trabalho(rs.getLong("turnos_de_trabalho"));
				e.setTipo_do_bem(rs.getString("tipo_do_bem"));
				e.setPerc_residual(rs.getBigDecimal("perc_residual"));
				e.setDep_sigla(rs.getString("dep_sigla"));
				e.setBaixa_tipo(rs.getString("baixa_tipo"));
	        	e.setBaixa_data_ou_venda(rs.getDate("baixa_data_ou_venda"));
				e.setBaixa_valor_da_venda(rs.getBigDecimal("baixa_valor_da_venda"));
				lsPatrimonios.add(e);
			}
		} catch (Exception e) {
			System.err.println("Erro ao selecionar: " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(con, ps, rs);
		}
		return lsPatrimonios;
	}


	public void depreciar(Patrimonio patrimonio) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE BemPatrimonial SET baixa_tipo  = ?, baixa_data_ou_venda = ?, baixa_valor_da_venda = ?"
			                                 + " WHERE numero = ?";
		try { 
			con = open();
			ps = con.prepareStatement(sql);
			ps.setString(1, patrimonio.getBaixa_tipo());
			ps.setDate(2, new Date(patrimonio.getBaixa_data_ou_venda().getTime()));
			ps.setBigDecimal(3, patrimonio.getBaixa_valor_da_venda());
			ps.setLong(4, patrimonio.getNumero());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Erro ao baixar: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
	}
	
	
	public void deprecia(Patrimonio patrimonio){

		// Calcular quantidade de meses a depreciar
		// Selecioando dia, mes e ano da compra/aquisição
	
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(patrimonio.getData_compra());

		int diaCompra = calendario.get(GregorianCalendar.DAY_OF_MONTH);
		int mesCompra = calendario.get(GregorianCalendar.MONTH);		
		int anoCompra = calendario.get(GregorianCalendar.YEAR);		

		// Selecioando dia, mes e ano da baixa
//		if (patrimonio.getBaixa_data_ou_venda() == null) {
		
		calendario.setTime(patrimonio.getBaixa_data_ou_venda());
		//esle{
//			calendario.setTime(patrimonio.getBaixa_data_ou_venda());
		int diaBaixa = calendario.get(GregorianCalendar.DAY_OF_MONTH);
		int mesBaixa = calendario.get(GregorianCalendar.MONTH);		
		int anoBaixa = calendario.get(GregorianCalendar.YEAR);		
		int qtdMeses = 0;
		qtdMeses = ((anoBaixa - anoCompra)-1) * 12;
		if(diaCompra>15){
			qtdMeses = (qtdMeses + 12) - mesCompra;
		}else {
			qtdMeses = (qtdMeses + 13) - mesCompra;
		}
		if(diaBaixa> 15){
			qtdMeses = qtdMeses + mesBaixa;
		}else{
			qtdMeses = (qtdMeses + mesBaixa)-1;
		}
System.out.println(qtdMeses);
		
		//Calculo da taxa de depreciacao
		Long  tempoUso  = patrimonio.getTempo_de_uso();
		Long  vidaUtil  = patrimonio.getVida_util();
		BigDecimal taxaAnual = baseDivisao.divide(new BigDecimal(vidaUtil), RoundingMode.HALF_EVEN);

System.out.println(taxaAnual);
		
		
		vidaUtil  = vidaUtil * 12; // Vida util em meses, porque tempo de uso está em meses
System.out.println(vidaUtil);
		// "Bem" usado
		if (tempoUso>0){
			BigDecimal vidaUtil_meio, vidaUtil_sobra;
			vidaUtil_meio  = new BigDecimal(vidaUtil).divide(new BigDecimal("2.0"), RoundingMode.HALF_EVEN);
System.out.println(vidaUtil_meio);

			vidaUtil_sobra = new BigDecimal(vidaUtil).subtract(new BigDecimal(tempoUso));
System.out.println(vidaUtil_sobra);

			// Calculando nova taxa anual por tempo de uso
			if  (vidaUtil_meio.compareTo(vidaUtil_sobra) > 0 ){
				taxaAnual = baseDivisao.divide(vidaUtil_meio.divide(new BigDecimal("12.0"), RoundingMode.HALF_EVEN), RoundingMode.HALF_EVEN);
			}else if (vidaUtil_meio.compareTo(vidaUtil_sobra) < 0 ){			
				taxaAnual = baseDivisao.divide(vidaUtil_sobra.divide(new BigDecimal("12.0"), RoundingMode.HALF_EVEN), RoundingMode.HALF_EVEN);

System.out.println(taxaAnual);
				
			}
		}

		// Verificando turnos de trabalhos
		Long  turno8Hrs = patrimonio.getTurnos_de_trabalho();

System.out.println(turno8Hrs);
						
		if (turno8Hrs == 3){
			taxaAnual = taxaAnual.multiply(new BigDecimal("2.0"));
		}else if (turno8Hrs == 2){
			taxaAnual = taxaAnual.multiply(new BigDecimal("1.5"));
		}

System.out.println(taxaAnual);
		
		//Calculo de Valor a Depreciar
		BigDecimal vl_Compra, tx_resid, calc_VD;
		vl_Compra = patrimonio.getPreco_compra();
		tx_resid  = patrimonio.getPerc_residual();
		calc_VD = vl_Compra.subtract((vl_Compra.divide(baseDivisao, RoundingMode.HALF_EVEN).multiply(tx_resid)));
		calc_VD = patrimonio.arredondar(calc_VD, 2);
		//Calculo de Depreciacao Acumulada
		BigDecimal calc_DA;

		calc_DA = taxaAnual.divide(baseDivisao).setScale(8);
System.out.println(calc_DA);
System.out.println("aqui..1");
		calc_DA = calc_DA.divide(new BigDecimal("12.0"), RoundingMode.HALF_EVEN).setScale(8);				
System.out.println(calc_DA);
System.out.println("aqui..2");
		calc_DA = calc_VD.multiply(calc_DA);				
System.out.println(calc_DA);
System.out.println("aqui..3");
		calc_DA = calc_DA.multiply(new BigDecimal(qtdMeses));				
System.out.println(calc_DA);
System.out.println("aqui..4");
		 
		
		
//		calc_DA = ((calc_VD * (taxaAnual/100))/12) * qtdMeses;
		
		

System.out.println(calc_DA);
System.out.println("aqui..");
		
		calc_DA = patrimonio.arredondar(calc_DA, 2);
		if (calc_DA.compareTo(calc_VD) > 0){
		    calc_DA = calc_VD;
		}
		//Calculo de Valor Contabil
		BigDecimal calc_VC;
		calc_VC = vl_Compra.subtract(calc_DA);
		calc_VC = patrimonio.arredondar(calc_VC, 2);
		//Calculo de GANHO ou PERDA
		BigDecimal calc_GP;
		calc_GP = patrimonio.getBaixa_valor_da_venda().subtract(calc_VC);
		calc_GP = patrimonio.arredondar(calc_GP, 2);
		
		//grava resultado
		patrimonio.setCalc_DA(calc_DA);
		patrimonio.setCalc_VC(calc_VC);
		patrimonio.setCalc_GP(calc_GP);
		
		

System.out.println("calc_VD");
System.out.println(calc_VD);
System.out.println("calc_DA");
System.out.println(calc_DA);
System.out.println("calc_VC");
System.out.println(calc_VC);
System.out.println("calc_GP");
System.out.println(calc_GP);
		
	}

	
}
