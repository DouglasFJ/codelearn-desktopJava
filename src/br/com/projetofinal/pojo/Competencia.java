package br.com.projetofinal.pojo;

public class Competencia {
	
	private int id;
	private String descricao;
	private int minutos;
	private static int contador;
	
	public Competencia() {
		contador++;
	}
	public Competencia(int id, String descricao, int minutos){
		super();
		this.id = id;
		this.descricao = descricao;
		this.minutos = minutos;
		contador++;
	}
	
	public static int contagemObjetos() {
		return contador;
	}
}
