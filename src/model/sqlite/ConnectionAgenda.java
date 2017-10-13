package model.sqlite;
import java.sql.*;

public class ConnectionAgenda {
	public Connection getConnection(){
		try {
			String banco = "jdbc:sqlite:agenda.db";
			return DriverManager.getConnection(banco);
		}catch(SQLException e) {
			throw new RuntimeException("Erro ao conectar com o banco de dados"+e);
		}
	}
}
