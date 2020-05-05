package it.polito.tdp.extflightdelays.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {
	
	public static void main(String[] args) {
		
		Model model = new Model();
		
		Graph<Airport, DefaultWeightedEdge> grafo = model.getGraphByPeso(500);
		
		System.out.println("Numero di vertici "+ grafo.vertexSet().size()+"\nNumero di archi: "+grafo.edgeSet().size());

	}

}
