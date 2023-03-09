package br.com.projetofinal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetofinal.pojo.Curso;

public interface ICursoDAO {
	
	public abstract void cadastrar(Curso curso);
	public abstract ResultSet pesquisar(String nomeCurso);
	public abstract ResultSet pesquisar(int id);
	public abstract ResultSet alterar(int id, Curso curso);
	public abstract void excluir(int id);
	
}