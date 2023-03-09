package br.com.projetofinal.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.projetofinal.dao.Conexao;
import br.com.projetofinal.dao.CursoDAO;
import br.com.projetofinal.pojo.Curso;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursosGUI extends JFrame implements ActionListener {

	private JButton btnCadastrar;
	private JButton btnPesquisar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private JPanel pnlPesquisar;
	private JPanel pnlCadastrar;
	private JPanel pnlCadastrarLeft;
	private JPanel pnlCadastrarRight;
	private JPanel pnlAlterar;
	private JPanel pnlExcluir;
	
	private JTextField txtCurso;
	private JTextField txtTempoMin;
	private JTextField txtId;
	private JTextField txtPesquisar;
	private JTextField txtAlterar;
	
	private JLabel lblCurso;
	private JLabel lbltxtTempoMin;
	private JLabel lblId;
	private JLabel lblPesquisar;
	private JLabel lblAlterar;
	
	public CursosGUI() {
		setLayout(new GridLayout(4, 1));
		setSize(800,600);
		
		
		btnCadastrar = new JButton("Cadastrar");
		btnAlterar = new JButton("Alterar");
		btnExcluir = new JButton("Excluir");
		btnPesquisar = new JButton("Pesquisar");
		
		btnCadastrar.addActionListener(this);
		btnPesquisar.addActionListener(this);
		btnAlterar.addActionListener(this);
		btnExcluir.addActionListener(this);
		
		
		txtCurso = new JTextField(20);
		txtTempoMin = new JTextField(20);
		txtPesquisar = new JTextField(40);
		txtId = new JTextField(10);
		txtAlterar = new JTextField(10);
		
		lblCurso = new JLabel("Curso: ");
		lbltxtTempoMin = new JLabel("Carga horaria em min:");
		lblId = new JLabel("Para deletar digite o ID:");
		lblPesquisar = new JLabel("Pesquisar:");
		lblAlterar = new JLabel("Alterar curso de ID:");

		
		pnlPesquisar = new JPanel(new FlowLayout());
		pnlPesquisar.add(lblPesquisar);
		pnlPesquisar.add(txtPesquisar);
		pnlPesquisar.add(btnPesquisar);
		
		pnlCadastrarLeft = new JPanel( new GridLayout(2,1));
		pnlCadastrarLeft.add(lblCurso);
		pnlCadastrarLeft.add(txtCurso);
		pnlCadastrarRight = new JPanel(new GridLayout(2,1));
		pnlCadastrarRight.add(lbltxtTempoMin);
		pnlCadastrarRight.add(txtTempoMin);
		pnlCadastrar = new JPanel();;
		pnlCadastrar.add(pnlCadastrarLeft);
		pnlCadastrar.add(pnlCadastrarRight);
		pnlCadastrar.add(btnCadastrar);
		
		pnlExcluir = new JPanel();
		pnlExcluir.add(lblId);
		pnlExcluir.add(txtId);
		pnlExcluir.add(btnExcluir);
		
		pnlAlterar = new JPanel();
		pnlAlterar.add(lblAlterar);
		pnlAlterar.add(txtAlterar);
		pnlAlterar.add(btnAlterar);
		
		getContentPane().add(pnlPesquisar);
		getContentPane().add(pnlCadastrar);
		getContentPane().add(pnlExcluir);
		getContentPane().add(pnlAlterar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnCadastrar) {
			
			if (txtCurso.getText().equals("") || txtTempoMin.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Campos obrigatorios");
			}else {
				Curso curso = new Curso(txtCurso.getText(), Integer.parseInt(txtTempoMin.getText())); 
				CursoDAO add = new CursoDAO();
				add.cadastrar(curso);
				txtCurso.setText("");
				txtTempoMin.setText("");
				JOptionPane.showMessageDialog(null, "Cadastrou");
			}
		}else if(e.getSource() == btnPesquisar) {
			ResultSet pes;
			try {
				int num = Integer.parseInt(txtPesquisar.getText());
				pes = new CursoDAO().pesquisar(num);
			}catch (Exception e1) {
				pes = new CursoDAO().pesquisar(txtPesquisar.getText());
			}
			new TabelaCursos(pes);
			
		}else if(e.getSource() == btnExcluir) {
			
			new CursoDAO().excluir(Integer.parseInt(txtId.getText()));
			Conexao.fecharConexao();
			JOptionPane.showMessageDialog(null, "O curso do ID: "+txtId.getText()+" foi excluido com sucesso");
			txtId.setText("");
			
		} else {
			JOptionPane.showMessageDialog(null, "Alterou");
		}
	}
}
