package br.com.locadora.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocadoraDataSource {
	private static final String URL_TEMPLATE = "jdbc:postgresql://localhost:5433/%s?user=%s&password=%s&useTimezone=true&serverTimezone=UTC";
	
	private Connection getConnection(String dbName, String username, String password) throws SQLException {
		String url = String.format(URL_TEMPLATE, dbName, username, password);
		return DriverManager.getConnection(url);
	}

	public Connection getConnection() throws SQLException {
		return getConnection("Locadora", "postgres", "proot"); // retornando a conexao estabelecida com o banco( metodo publico)
	}
	
}

/*private Connection conn;
private String url;
private String senha;
private String usuario;

LocadoraDataSource(){
	url = "jdbc:postgresql://localhost:5433/Locadora";
	usuario = "postgres";
	senha = "proot";
	
	try {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(url, usuario, senha);
		System.out.println("conection on");
		
	} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	
	try {
		conn.close();
		System.out.println("connect realese");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void main(String[] args) {
	LocadoraDataSource dt = new LocadoraDataSource();

}
*/

