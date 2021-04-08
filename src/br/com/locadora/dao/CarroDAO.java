package br.com.locadora.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.locadora.conn.LocadoraDataSource;
import br.com.locadora.model.Carro;

public class CarroDAO {
private LocadoraDataSource dataSource = new LocadoraDataSource();
	
	public List<Carro> listaCarro(){
		
		List<Carro> lista = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Carro ORDER BY modelo");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Carro carro = new Carro();
				carro.setPlaca(rs.getString("placa"));
				carro.setMarca(rs.getString("marca"));
				carro.setAno(rs.getString("ano"));
				carro.setModelo(rs.getString("modelo"));
				carro.setSituacao(rs.getString("situacao"));
				lista.add(carro);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
		
	}
}
