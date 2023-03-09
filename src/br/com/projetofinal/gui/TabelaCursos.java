package br.com.projetofinal.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.projetofinal.dao.Conexao;

public class TabelaCursos extends JFrame{
	
	public TabelaCursos(ResultSet result){
		
		String[] colunas = {"ID", "Nome do curso", "Carga horaria"};
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

		try {
			while(result.next()) {
				int id = result.getInt("idcurso");
				String nome = result.getString("nome_curso");
				int carga = result.getInt("carga_horaria");
				Object[] row = {id, nome, carga};
				modelo.addRow(row);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro: ");
			e.printStackTrace();
		}
		Conexao.fecharConexao();
		
		JTable table = new JTable(modelo);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		pack();
		setVisible(true);
		
	}
	
}
