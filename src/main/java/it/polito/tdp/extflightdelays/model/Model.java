package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	Graph<Airport, DefaultWeightedEdge> graph;
	Map<Integer, Airport> airportIdMap;
	List<Adiacenza> adiacenze;
	ExtFlightDelaysDAO fdao;
	public Model() {
		
		fdao = new ExtFlightDelaysDAO();
		//creo grafo e mappaIdentita
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		airportIdMap = new HashMap<>();
		
		//per caricare i vertici posso estrarre una lista di aereoporti dal dao e poi caricarla qua con un ciclo
		//nella soluzione uso una tecnica piu rapida caricando la map nel dao
		fdao.loadAllAirports(airportIdMap);
		
		//carico i vertici
		Graphs.addAllVertices(graph, airportIdMap.values());
		
		
		
		
	}
	
	public Graph<Airport, DefaultWeightedEdge> getGraphByPeso(int peso) {
		
		adiacenze = fdao.getAdiacenze(peso);
		//carico solo gli archi di peso >= a quello di input
		for(Adiacenza tempA: adiacenze) {
			Graphs.addEdge(this.graph, airportIdMap.get(tempA.getA1Id()), airportIdMap.get(tempA.getA2Id()), tempA.getPeso());
		}
		return this.graph;
	}
	
	public String getArchiConPeso() {
		
		String risultato="";
		for(Adiacenza tempA: adiacenze) {
			
			risultato+="\n{"+airportIdMap.get(tempA.getA1Id()).getAirportName() + ", "+airportIdMap.get(tempA.getA2Id()).getAirportName()  + "}, peso: "+ tempA.getPeso();
		}
		return risultato;
	}
		
}
