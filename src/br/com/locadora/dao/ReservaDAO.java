package br.com.locadora.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

import br.com.locadora.conn.LocadoraDataSource;
import br.com.locadora.model.Reserva;

public class ReservaDAO {
	private LocadoraDataSource dataSource = new LocadoraDataSource();
  	
	public void salvar(Reserva reserva) throws DataAccessException {
		
		try(Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			
			// chamando procedimento de reservas de carros, antes de ser feito a inserção dos dados
			try(PreparedStatement ps1 = conn.prepareStatement("select proc_reserva(?)")){
				ps1.setString(1, reserva.getCarro().getPlaca());
				ps1.execute();
			}catch (SQLException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Carro já está reservado!", "Não foi possível reservar" , JOptionPane.ERROR_MESSAGE);
				// TODO: handle exception
			}
			
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Reserva (data_prev_ret, data_prev_dev, "
					+ "cpf_cliente, placa) VALUES (?, ?, ?, ?);")){
				
				ps.setDate(1, new java.sql.Date (reserva.getDataRetirada().getTime()));
				ps.setDate(2, new java.sql.Date (reserva.getDataDevolucao().getTime()));
				ps.setString(3, reserva.getCliente().getCpf());
				ps.setString(4, reserva.getCarro().getPlaca());
		
				ps.executeUpdate();
				conn.commit();
				
			}catch (SQLException e) {
				conn.rollback();
				throw e;
				// TODO: handle exception
			}
		
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
}
	/*
	public boolean procedure(Reserva reserva) {
		
		try(Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement("select proc_reserva(?)")){
				ps.setString(1, reserva.getCarro().getPlaca());
				ps.execute();
			
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}*/

	
	/*try(CallableStatement call = conn.prepareCall("{ call proce_reserva(?)}")){
	call.registerOutParameter(1, Types.VARCHAR);
	call.setString(1, reserva.getCarro().getPlaca());
	call.execute();
	
	conn.commit();
}*/

