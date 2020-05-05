package it.polito.tdp.extflightdelays.model;

public class Adiacenza {

	private Integer a1Id;
	private Integer a2Id;
	private int peso;
	/**
	 * @param a1
	 * @param a2
	 * @param peso
	 */
	public Adiacenza(Integer a1, Integer a2, int peso) {
		
		this.a1Id = a1;
		this.a2Id = a2;
		this.peso = peso;
	}
	public Integer getA1Id() {
		return a1Id;
	}
	public Integer getA2Id() {
		return a2Id;
	}
	public int getPeso() {
		return peso;
	}
	
	
	
	
	
	
}
