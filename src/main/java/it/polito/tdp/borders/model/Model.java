package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	BordersDAO dao ;
    Map<Integer,Country> idMap;
    Graph<Country, DefaultEdge> grafo;
    List<Country> tuttiVertici;
    
    private int NumArchi;
    
	public Model() {
		idMap = new HashMap<>();
		dao = new BordersDAO();
		dao.loadAllCountries(idMap);
		
	}
	public void createGraph(int annoMax) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		tuttiVertici = new ArrayList<>(); 
		NumArchi = 0;
		
		List<Connessione> archi = dao.getAllConfini(idMap, tuttiVertici, annoMax);
		Graphs.addAllVertices(grafo, tuttiVertici);

		for(Connessione c: archi) {
			grafo.addEdge(c.getStato1(), c.getStato2());
		}
		NumArchi = grafo.edgeSet().size();
		
		//voglio trovare il numero di componenti connesse del grafo
		ConnectivityInspector<Country, DefaultEdge> conn = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		System.out.println("grafo connesso ? --"+conn.isConnected()); //mi restituisce true se grafo connesso, false alotrimenti
		
		/*
		 * questa lista mi restituisce dei gruppi di set di cui ogni set ha all'interno
		 * dei vertici connessi. il numero di set sara il numero di componenti connesse
		 */
		List<Set<Country>> sottoInsiemiConn = conn.connectedSets();
		//conn.connectedSetOf(vertice);
		System.out.println("il numero di componenti connesse è: "+sottoInsiemiConn.size());
	}

	public Map<Country, Integer> getCountryCounts(){ //prendo tutti gli archi con il loro grado
		Map<Country, Integer> result = new HashMap<>();
		
		for(Country c: this.grafo.vertexSet()) {
			int grado = grafo.degreeOf(c);
			result.put(c, grado);
		}
		
		return result;
	}
	
	public List<Country> getCountries(){ //prendo tutti i paesi del grafo
		List<Country> result = new ArrayList<>();
		
		if(grafo!=null) {
			for(Country c: grafo.vertexSet()) {
				 result.add(c);
			  }
		}
		Collections.sort(result);
		return result;
	}
	
	public int getNumArchi() {
		return NumArchi;
	}
	
	/*
	 * questo metodo mi restituisce tutti i nodo del grafo raggiungibili 
	 * a partire da un nodo di partenza che verrà scelto da un utente
	 * nell'interfaccia grafica
	 */
	//metodo iterativo
	public List<Country> NodiRaggiungibili(Country nodoPartenza){
		List<Country> result= new ArrayList<>();
		
		//ci sono 3 metodi diversi per fare questa visita del grafo
		//metodo iterativo
		List<Country> visitati = new ArrayList<>();
		List<Country> daVisitare = new ArrayList<>();
		daVisitare.add(nodoPartenza);
		
		//List<Country> vicini = Graphs.neighborListOf(this.grafo, nodoPartenza);
		/*daVisitare.addAll(Graphs.neighborListOf(this.grafo, nodoPartenza));
		while(!daVisitare.isEmpty()) {
			//visitati.add(nodoPartenza);
			if(daVisitare.contains(nodoPartenza))
			    daVisitare.remove(nodoPartenza); //cosi dopo la prima iterazione non entriamo qui
			
			for(int i=0; i<daVisitare.size(); i++) {
				Country c = daVisitare.get(i);
				daVisitare.addAll(Graphs.neighborListOf(this.grafo, c));
				
				daVisitare.remove(c);
				if(!visitati.contains(c))
				   visitati.add(c);
			}
		}*/
		
		while(daVisitare!=null) {
			
			for(int i=0; i<daVisitare.size(); i++) {
				Country temp = daVisitare.get(i);
				List<Country> vicini = Graphs.neighborListOf(grafo, temp);
				
				
				 //popolo la lista daVisitare
					for(Country c: vicini) {
						if(!visitati.contains(c)) { //se non è ancora stato visitato, lo metto comme nodo da visitare
							daVisitare.add(c);
						}
					}
				
				if(!visitati.contains(temp)) //aggiungo il nodo corrente come gia visitato
				     visitati.add(temp);
				
				daVisitare.remove(temp);
			}
			
		}
		
		//componenti connesse
		
		return visitati;
	}
	
	//ricerca con le componenti connesse
	public  Set<Country> getComponentiConnesse(Country nodoPartenza){
		Map<Country, Set<Country>> componentiConnesse= new HashMap<>();
		ConnectivityInspector<Country, DefaultEdge> conn = new ConnectivityInspector<Country, DefaultEdge>(grafo);

		/*for(Country co : grafo.vertexSet()) {
			if(!componentiConnesse.containsKey(co)) {
				
				Set<Country> list = conn.connectedSetOf(co);
				componentiConnesse.put(co, list);
				//componentiConnesse.get(co).addAll(list);
			}
		}*/
		Set<Country> list = conn.connectedSetOf(nodoPartenza);
		
		return list;
	}
	
	//questo è il metodo ricorsivo
	public List<Country> cercaNodiRaggiungibili3(Country nodoPartenza){
		List<Country> soluzione = new ArrayList<>();
		List<Country> parziale = new ArrayList<>();
		
		parziale.add(nodoPartenza);
		cerca(soluzione, parziale, 0);
		
		return soluzione;
	}
	private void cerca(List<Country> soluzione, List<Country> parziale, int livello) {
		
		if(parziale.size()==0) { //caso termianle quando la lista che contiene i nodi da visitare è vuota
			return;
		}
		
		for(int i=0; i<parziale.size(); i++) {
			List<Country> vicini = Graphs.neighborListOf(grafo, parziale.get(i));
			
			parziale.addAll(vicini);
			if(!soluzione.contains(parziale.get(i)))
			      soluzione.add(parziale.get(i));
			
			cerca(soluzione, parziale, livello+1);
			parziale.remove(parziale.get(i));
		}
	}
}
