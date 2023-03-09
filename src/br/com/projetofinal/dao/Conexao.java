package br.com.projetofinal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static final String URL = "jdbc:mysql://localhost:3306/projetofinaldb";
	private static final String USER = "root";
	private static final String PASSWORD = "Df_07330206";

	private static Connection conn = null;
	
	public static Connection conectar() {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Conexão feita com sucesso");
			
		} catch (SQLException e) {
			System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
		}
		return conn;
	}
	public static void fecharConexao() {
		try {
			conn.close();
			System.out.println("Conexao fechada com sucesso");
		} catch (SQLException e) {
			System.err.println("ERRO AO FECHAR CONEXAO:");
			e.printStackTrace();
		}
	}
}
