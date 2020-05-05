package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Flight;

public class ExtFlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadAllAirports(Map<Integer, Airport> idMap) {
		String sql = "SELECT * FROM airports";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"),
						rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"),
						rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				idMap.put(airport.getId(), airport);
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT * FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("ID"), rs.getInt("AIRLINE_ID"), rs.getInt("FLIGHT_NUMBER"),
						rs.getString("TAIL_NUMBER"), rs.getInt("ORIGIN_AIRPORT_ID"),
						rs.getInt("DESTINATION_AIRPORT_ID"),
						rs.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), rs.getDouble("DEPARTURE_DELAY"),
						rs.getDouble("ELAPSED_TIME"), rs.getInt("DISTANCE"),
						rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(), rs.getDouble("ARRIVAL_DELAY"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Adiacenza> getAdiacenze(int inputPeso){
		
		List<Adiacenza> lista = new ArrayList<>();
		//cosi prendo A-B e B-A
		//
		String sql="SELECT  ORIGIN_AIRPORT_ID AS idO, DESTINATION_AIRPORT_ID AS idD, DISTANCE AS peso " + 
				"FROM flights f " + 
				"WHERE DISTANCE>= ? AND (f.ORIGIN_AIRPORT_ID < f.DESTINATION_AIRPORT_ID " + 
				"		OR (f.ORIGIN_AIRPORT_ID, f.DESTINATION_AIRPORT_ID) IN (SELECT f2.ORIGIN_AIRPORT_ID, f2.DESTINATION_AIRPORT_ID " + 
				"																				 FROM flights f2 " + 
				"																				 WHERE (f2.ORIGIN_AIRPORT_ID> f2.DESTINATION_AIRPORT_ID) " + 
				"																				 		AND ((f2.ORIGIN_AIRPORT_ID, f2.DESTINATION_AIRPORT_ID) NOT IN (SELECT  DESTINATION_AIRPORT_ID, ORIGIN_AIRPORT_ID " + 
				"																				 																							FROM flights f3 " + 
				"																				 																							WHERE f3.ORIGIN_AIRPORT_ID<f3.DESTINATION_AIRPORT_ID)))) " + 
				"GROUP BY ORIGIN_AIRPORT_ID,DESTINATION_AIRPORT_ID";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, inputPeso);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Adiacenza temporanea = new Adiacenza(rs.getInt("idO"), rs.getInt("idD"), rs.getInt("peso"));
				lista.add(temporanea);
				
			}
			conn.close();
						
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return lista;
	}
}

	