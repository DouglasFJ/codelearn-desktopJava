package br.com.projetofinal;

import br.com.projetofinal.gui.CursosGUI;
import br.com.projetofinal.pojo.Competencia;

public class Main {

	public static void main(String[] args) {
	
		Competencia c1 = new Competencia();
		
		System.out.println("total de objetos: "+ Competencia.contagemObjetos());
		
		Object[] competencia = {"conhecimento", "habilidade", "atitude", "IA"};
		
		new CursosGUI().setVisible(true);
		
	}

}
