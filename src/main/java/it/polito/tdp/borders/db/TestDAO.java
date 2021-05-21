package it.polito.tdp.borders.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni:");
		Map<Integer, Country> idMap = new HashMap<>();
		 dao.loadAllCountries(idMap);
		
		for(Country c: idMap.values()) {
			System.out.println(c.toString());
		 }
		System.out.println(idMap.size());
	}
}
