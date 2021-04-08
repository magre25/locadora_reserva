package br.com.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.locadora.conn.LocadoraDataSource;
import br.com.locadora.model.Cliente;

public class ClienteDAO {
	private LocadoraDataSource dataSource = new LocadoraDataSource();
	
	public List<Cliente> listaCliente(){
		
		List<Cliente> lista = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cliente ORDER BY nome");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCpf(rs.getString("cpf_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setTelefone(rs.getString("telefone"));
				lista.add(cliente);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
		
	}
}
