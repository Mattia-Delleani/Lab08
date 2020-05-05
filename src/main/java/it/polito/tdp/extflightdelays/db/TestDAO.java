package it.polito.tdp.extflightdelays.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();

		Map<Integer, Airport> airportIdMap = new HashMap();
		
		System.out.println(dao.loadAllAirlines().size());
		dao.loadAllAirports(airportIdMap);
		System.out.println("CARICAMENTO AVVENUTO, dimesione mappaId: "+airportIdMap.size());
		System.out.println(dao.loadAllFlights().size());
		
		System.out.println("Numero adiacenze: "+dao.getAdiacenze(500).size());
		System.out.println("Peso prima adiacenza: "+dao.getAdiacenze(500).get(0).getPeso());
	}

}
