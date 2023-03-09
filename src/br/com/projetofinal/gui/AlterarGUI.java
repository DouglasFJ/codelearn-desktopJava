package br.com.projetofinal.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.projetofinal.dao.CursoDAO;
import br.com.projetofinal.pojo.Curso;

public class AlterarGUI extends JFrame implements ActionListener{
	
	int id;
	String nome;
	int carga;
	
	JPanel pnlNome;
	JPanel pnlCarga;
	
	JTextField txtNome;
	JTextField txtCarga;
	
	JLabel lblNome;
	JLabel lblCarga;
	
	JButton btnAlterar;
	
	public AlterarGUI(int id) {
		this.id = id;
		
		try {
			ResultSet rs = new CursoDAO().pesquisar(id);
			rs.next();
			nome = rs.getString("nome_curso");
			carga = rs.getInt("carga_horaria");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setLayout(new GridLayout(3, 1));
		setSize(500, 400);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(this);
		
		lblNome = new JLabel("Nome do Curso: "); 
		lblCarga = new JLabel("Carga horaria em min:");
		
		txtNome = new JTextField(20);
		txtCarga = new JTextField(20);
		txtNome.setText(nome);
		txtCarga.setText(Integer.toString(carga));
		
		pnlNome = new JPanel();
		pnlNome.add(lblNome);
		pnlNome.add(txtNome);
		
		pnlCarga = new JPanel();
		pnlCarga.add(lblCarga);
		pnlCarga.add(txtCarga);
		
		getContentPane().add(pnlNome);
		getContentPane().add(pnlCarga);
		getContentPane().add(btnAlterar);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnAlterar) {
			Curso curso = new Curso(txtNome.getText(), Integer.parseInt(txtCarga.getText()));
			
			new CursoDAO().alterar(this.id, curso);
			JOptionPane.showMessageDialog(null, "curso alterado com sucesso");
			
		}
		
	}
	
}
