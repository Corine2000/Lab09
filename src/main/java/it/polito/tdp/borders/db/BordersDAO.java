package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Connessione;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		//List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			   int codice = rs.getInt("ccode");
			   if(!idMap.containsKey(codice)) {
				   Country c = new Country(codice, rs.getString("StateAbb"), rs.getString("StateNme") );
				   idMap.put(codice, c);
			   }
			}
			
			conn.close();
			//return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}
	/*
	 * in questo metodo faccio due cose, popolo non solo la lista dei vertici ma ache gli archi
	 */
	public List<Connessione> getAllConfini(Map<Integer,Country> idMap, List<Country> Vertici, int anno){
		List<Connessione> result = new ArrayList<>();
		String sql = "SELECT state1no AS stato1, state2no AS stato2 "
				+ "FROM  contiguity "
				+ "WHERE conttype=1  AND year<=? AND state1no<state2no ";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Country ct1 = idMap.get(rs.getInt("stato1"));
				Country ct2 = idMap.get(rs.getInt("stato2"));
				
				if(!Vertici.contains(ct1))
					Vertici.add(ct1);
				if(!Vertici.contains(ct2))
					Vertici.add(ct2);
				
				Connessione c = new Connessione(ct1, ct2);
				result.add(c);
			}
			
			
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database metodo getAllConfini");
		}
		
	}
}
