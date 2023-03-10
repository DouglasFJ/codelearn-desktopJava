package br.com.projetofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetofinal.pojo.Curso;

public class CursoDAO implements ICursoDAO {

	//METODO INSERIR NA TABELA
	public void cadastrar(Curso curso) {
		Connection conexao = ConexaoRemota.conectar();
		if (conexao != null) {
			try {
				PreparedStatement state = conexao.prepareStatement("INSERT INTO curso(nome_curso, carga_horaria) " + "VALUES (? , ?)");
				state.setString(1, curso.getNome());
				state.setInt(2, curso.getCargaHoraria());
				state.executeUpdate();
				conexao.close();
				state.close();
				System.out.println("Conexao fechada com sucesso");
			}catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("A conexao com o banco não existe então não é possivel fazer a query");
		}
	}

	//METODO PARA PESQUISAR PELO NOME
	public ResultSet pesquisar(String nomeCurso){
		Connection conexao = ConexaoRemota.conectar();
		if (conexao != null) {
			try {
				PreparedStatement state = conexao.prepareStatement("SELECT * FROM curso WHERE nome_curso LIKE CONCAT('%', ? , '%')");
				state.setString(1, nomeCurso);
				ResultSet result = state.executeQuery();
				return result;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else {
			System.err.println("A conexao com o banco não existe então não é possivel fazer a query");
			return null;
		}
	}

	//METODO PARA PESQUISAR POR ID
	public ResultSet pesquisar(int id) {
		Connection conexao = ConexaoRemota.conectar();
		if (conexao != null) {
			try {
				PreparedStatement state = conexao.prepareStatement("SELECT * FROM curso WHERE idcurso = ?");
				state.setInt(1, id);
				ResultSet result = state.executeQuery();
				return result;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else {
			System.err.println("A conexao com o banco não existe então não é possivel fazer a query");
			return null;
		}
	}

	//METODO PARA ALTERAR OS ATRIBUTOS DE UM CURSO
	public void alterar(int id, Curso curso) {
		Connection conexao = ConexaoRemota.conectar();
		if (conexao != null) {
			try {
				PreparedStatement state = conexao.prepareStatement("UPDATE curso SET nome_curso = ?, carga_horaria = ? WHERE idcurso = ?");
				state.setString(1, curso.getNome());
				state.setInt(2, curso.getCargaHoraria());
				state.setInt(3, id);
				state.executeUpdate();
				state.close();
				conexao.close();
				System.out.println("Conexao fechada com sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("A conexao com o banco não existe então não é possivel fazer a query");
		}
	}

	//METODO PARA EXCLUIR UM CURSO
	public void excluir(int id) {
		
		Connection conexao = ConexaoRemota.conectar();
		if (conexao != null) {
			try {
				PreparedStatement state = conexao.prepareStatement("DELETE FROM curso WHERE idcurso = ?");
				state.setInt(1, id);
				state.executeUpdate();
				conexao.close();
				state.close();
				System.out.println("Conexao fechada com sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("A conexao com o banco não existe então não é possivel fazer a query");
		}

	}
}
