package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
	System.out.println("Creo il grafo relativo al 2000");
		model.createGraph(1900);
		
	List<Country> countries = model.getCountries();
		System.out.format("Trovate %d nazioni\n", countries.size());
		for(Country c: countries)
			System.out.println(c.toString());
		
		System.out.println("grafo creato: #vertici: "+countries.size()+", #archi: "+model.getNumArchi()+"\n");
		

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
	Map<Country, Integer> stats = model.getCountryCounts();
	 /* for (Country country : stats.keySet())
			System.out.format("%s %d\n", country, stats.get(country));	*/	
		
	Country c = countries.get(0);
	List<Country> vicini = model.NodiRaggiungibili(c);
	System.out.println("paesi raggiungibili a partire da "+c.getNomeStato());
	for(Country ci: vicini)
	   System.out.println(ci.toString());
	
	
	/*Map<Country, Set<Country>> result = model.getComponentiConnesse();
	for(Country ci: result.keySet()) {
		System.out.println(ci.toString()+" "+result.get(ci).size());
	}*/
	 
	
	}

}
