package br.com.projetofinal.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import br.com.projetofinal.dao.Conexao;
import br.com.projetofinal.dao.CursoDAO;
import br.com.projetofinal.pojo.Curso;

public class TabelaCursos extends JFrame implements ActionListener {

	private JPanel painel1;
	private JPanel painel2;
	private JButton btnExcluir;
	private JButton btnAlterar;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtCargaHoraria;

	private JTable table;
	private DefaultTableModel modelo;

	private int id = 0;
	private String nome;
	private int cargaHoraria;

	ResultSet result;

	public TabelaCursos(ResultSet result) {
		this.result = result;

		painel1 = new JPanel();
		painel2 = new JPanel();
		btnExcluir = new JButton("Excluir");
		btnAlterar = new JButton("Alterar");
		txtId = new JTextField(10);
		txtNome = new JTextField("Digite o nome", 20);
		txtCargaHoraria = new JTextField("Digite a carga horária", 20);
		txtId.setEnabled(false);
		painel1.add(txtId);
		painel1.add(txtNome);
		painel1.add(txtCargaHoraria);
		painel1.add(btnExcluir);
		painel1.add(btnAlterar);

		painel1.setLayout(new FlowLayout());

		BorderLayout border = new BorderLayout();
		setLayout(border);
		this.preencherTabela();

		add(painel1, BorderLayout.NORTH);
		add(painel2, BorderLayout.WEST);
		pack();
		setVisible(true);

		btnExcluir.addActionListener(this);
		btnAlterar.addActionListener(this);

	}

	public void preencherTabela() {

		String[] colunas = { "ID", "Nome do curso", "Carga horaria" };
		modelo = new DefaultTableModel(colunas, 0);

		try {
			while (result.next()) {
				int id = result.getInt("idcurso");
				String nome = result.getString("nome_curso");
				int carga = result.getInt("carga_horaria");
				Object[] row = { id, nome, carga };
				modelo.addRow(row);
			}
		} catch (SQLException e) {
			System.err.println("Ocorreu um erro: ");
			e.printStackTrace();
		}

		table = new JTable(modelo);

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);

		// DESABILITANDO REDIMENSIONAMENTO AUTOMATICO DAS COLUNAS
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//EVENTO DE SELECIONAR

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// verifica se o evento foi gerado pela seleção de uma linha
				if (!event.getValueIsAdjusting()) {
					// obtém o índice da linha selecionada
					int rowIndex = table.getSelectedRow();

					// obtém o valor da célula da coluna 0 da linha selecionada
					Object cellValue = table.getValueAt(rowIndex, 0);
					Object cellValue1 = table.getValueAt(rowIndex, 1);
					Object cellValue2 = table.getValueAt(rowIndex, 2);
					
					id = Integer.parseInt(cellValue.toString());
					nome = String.valueOf(cellValue1.toString());
					cargaHoraria = Integer.parseInt(cellValue2.toString());
					txtId.setText(String.valueOf(id));
					txtNome.setText(nome);
					txtCargaHoraria.setText(String.valueOf(cargaHoraria));
					// exibe o valor da célula em um JOptionPane
					// JOptionPane.showMessageDialog(null, "Valor selecionado: " + cellValue);
				}
			}
		});
		table.revalidate();
		table.repaint();

		JScrollPane scrollPane = new JScrollPane(table);
		painel2.add(scrollPane);

	}
	


	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnExcluir) {
			new CursoDAO().excluir(id);
			JOptionPane.showMessageDialog(null, "Dados Excluídos com Sucesso");
			
			//VERIFICA QUAL LINHA FOI EXCLUIDA NO BANCO PARA A MESMA SER EXCLUIDA NO MODELO DA TABELA
			for(int i=0 ; i<modelo.getRowCount(); i++) {
				if(Integer.parseInt( modelo.getValueAt(i, 0).toString()) == id) {

					//VERIFICA QUAL LINHA ESTÁ SELECIONADA PARA SELECIONAR OUTRA LINHA ANTES DE APAGAR -- APAGAR UMA LINHA SELECIONADA GERA UM ERRO
					if(table.getSelectedRow() != 0) {
						table.setRowSelectionInterval(0, 0);
					}else {
						table.setRowSelectionInterval(1, 1);
					}
					modelo.removeRow(i);
				}
			}
			
			

		} else if (e.getSource() == btnAlterar) {
			Curso curso = new Curso();
			curso.setNome(txtNome.getText());
			curso.setCargaHoraria(Integer.parseInt(txtCargaHoraria.getText()));
			curso.setId(Integer.parseInt(txtId.getText()));
			
			new CursoDAO().alterar(curso.getId(), curso);
			
			for(int i=0 ; i<modelo.getRowCount(); i++) {
				if(Integer.parseInt( modelo.getValueAt(i, 0).toString()) == Integer.parseInt(txtId.getText())) {
					System.out.println("Alterando linha: "+ i);
					System.out.println("Total de Linhas: " + modelo.getRowCount());
					//VERIFICA QUAL LINHA ESTÁ SELECIONADA PARA SELECIONAR OUTRA LINHA ANTES DE APAGAR -- APAGAR UMA LINHA SELECIONADA GERA UM ERRO
					
					modelo.setValueAt(txtNome.getText(), i, 1);
					modelo.setValueAt(Integer.parseInt(txtCargaHoraria.getText()), i, 2);
					
				}
			}

		}

	}

}
