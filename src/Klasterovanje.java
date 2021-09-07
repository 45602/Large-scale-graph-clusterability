import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class Klasterovanje<V, E> {
	
	private Set<UndirectedSparseGraph<V, Grana<E>>> klasteri = new HashSet<UndirectedSparseGraph<V, Grana<E>>>();
	private ArrayList<UndirectedSparseGraph<V, Grana<E>>> antikoalicije = new ArrayList<UndirectedSparseGraph<V, Grana<E>>>();
	private ArrayList<UndirectedSparseGraph<V, Grana<E>>> koalicije = new ArrayList<UndirectedSparseGraph<V, Grana<E>>>();
	private Set<Set<V>> components = new HashSet<Set<V>>();
	private ArrayList<Grana<E>> izbacene; 
	
	public ArrayList<String> ispisiListu() { 
		ArrayList<String> ispis = new ArrayList<String>();
		for(int i=0; i<izbacene.size(); i++) { 
			ispis.add(" " + izbacene.get(i) + " " + graph.getEndpoints(izbacene.get(i)));
		}
		return ispis;
	}
	//geteri
	public Set<UndirectedSparseGraph<V, Grana<E>>> getClusters() { 
		return klasteri;
	}
	public Set<Set<V>> getComps() { 
		return components;
	}
	public ArrayList<UndirectedSparseGraph<V, Grana<E>>> getAntikoalicije() { 
		return antikoalicije;
	}
	public ArrayList<UndirectedSparseGraph<V, Grana<E>>> getKoalicije() { 
		return koalicije;
	}
	public ArrayList<Grana<E>> getIzbacene() { 
		return izbacene;
	}
	
	public UndirectedSparseGraph<V,Grana<E>> getGraf() { 
		return graph;
	}
	public UndirectedSparseGraph<V,Grana<E>> graph;
	//konstruktor koji poziva sve metode
	public Klasterovanje(UndirectedSparseGraph<V,Grana<E>> graph) {
		this.graph=graph;
		identifyComponents();
		napraviPodgrafove();
		iterirajKrozKlastere();
		izbacene = iterirajKrozAntiKoalicije();
	}
	//detektovanje skupova cvorova, izostavljamo negativne grane
	private void identifyComponents() {
		Set<V> poseceni = new HashSet<>();
		components = new HashSet<Set<V>>();
		for (V x : graph.getVertices()) {
			if (!poseceni.contains(x)) {
				components.add(bfs(x,poseceni));
			}
		}
	}
	
	private Set<V> bfs(V v, Set<V> poseceni) {
		Set<V> komponenta = new HashSet<V>();
		Queue<V> red = new LinkedList<V>();
		
		komponenta.add(v);
		red.add(v);
		poseceni.add(v);
		
		while(!red.isEmpty()) { 
			V pom = red.poll();
			Iterator<V> it = graph.getNeighbors(v).iterator();
			while(it.hasNext()) {
				V komsija = it.next();
				if(graph.findEdge(pom, komsija) != null)  {
					if(!poseceni.contains(komsija) && graph.findEdge(pom, komsija).getZnak()) {
						komponenta.add(komsija);
						poseceni.add(komsija);
						red.add(komsija);
					}
				}
			}
		}
		return komponenta;
	}
	//prolazimo kroz grane pocetnog grafa i ako postoji grana koja spaja dva cvora iz skupa cvorova, dodamo je
	//u sustini, od skupa cvorova sada pravimo graf
	
	public void napraviPodgrafove() { 
		Iterator<Set<V>> it = components.iterator();
		while(it.hasNext()) { 
			Set<V> set = it.next();
			UndirectedSparseGraph<V, Grana<E>> graf = new UndirectedSparseGraph<V, Grana<E>>();
			Iterator<V> cvorovi = set.iterator();
			while(cvorovi.hasNext()) { 
				graf.addVertex(cvorovi.next());
			}
			
			ArrayList<Grana<E>> skupGrana = new ArrayList<Grana<E>>(graph.getEdges());
			
			Iterator<Grana<E>> grane = skupGrana.iterator();
			while(grane.hasNext()) { 
				Grana<E> pom = grane.next();
				Pair<V> par = graph.getEndpoints(pom);
				V src = par.getFirst();
				V tgt = par.getSecond();
				if(set.contains(src) && set.contains(tgt)) {
					graf.addEdge(pom, src, tgt);
				}
			}
			klasteri.add(graf);
		}
	}
	//prolazimo kroz sve podgrafove i gledamo sadrze li negativnu granu, ako sadrze dodas ceo podgraf u 
	//antikoalicije, u suprotnom dodas u koalicije
	
	public void iterirajKrozKlastere() {
		
		Iterator<UndirectedSparseGraph<V, Grana<E>>> itKlasteri = klasteri.iterator();
		while(itKlasteri.hasNext()) { 
			boolean nadjena = false;
			UndirectedSparseGraph<V, Grana<E>> set = itKlasteri.next();
			Iterator<Grana<E>> grane = set.getEdges().iterator();
			while(grane.hasNext()) { 
				Grana<E> trenutnaGrana = grane.next();
				if(!trenutnaGrana.getZnak()) { 
					nadjena = true;
					break;
				}
			}
			if(nadjena) {
				antikoalicije.add(set);
			}
			else {
				koalicije.add(set);
			}
		}
	}
	//izbaci negativne
	public ArrayList<Grana<E>> iterirajKrozAntiKoalicije() { 
		ArrayList<Grana<E>> negativne = new ArrayList<Grana<E>>();
		
		Iterator<UndirectedSparseGraph<V, Grana<E>>> antik = antikoalicije.iterator();
		while(antik.hasNext()) {
			UndirectedSparseGraph<V, Grana<E>> graf = antik.next();
			
			Iterator<Grana<E>> grane = graf.getEdges().iterator();
			while(grane.hasNext()) { 
				Grana<E> grana = grane.next();
				if(!grana.getZnak()) { 
					negativne.add(grana);
				}
			}
		}
		
		return negativne;
	}
	
	//metrike
	
	//prosecan stepen cvora u koaliciji/antikoaliciji/grafu
	
	public double avgStepen() { 
		int zbir = 0;
		int brCvorova = 0;
		for(V v : graph.getVertices()) { 
				brCvorova++;
				zbir += graph.degree(v);
		}
		return zbir/brCvorova;
	}
	public double avgKoalicije() { 
		int zbir = 0;
		int brCvorova = 0;
		Iterator<UndirectedSparseGraph<V, Grana<E>>> it = koalicije.iterator();
		while(it.hasNext()) {
			UndirectedSparseGraph<V, Grana<E>> graf = it.next();
			for(V v : graf.getVertices()) { 
				brCvorova++;
				zbir += graf.degree(v);
			}
		}
		return zbir/brCvorova;
	}
	public double avgAntikoalicije() { 
		int zbir = 0;
		int brCvorova = 0;
		Iterator<UndirectedSparseGraph<V, Grana<E>>> it = antikoalicije.iterator();
		while(it.hasNext()) {
			UndirectedSparseGraph<V, Grana<E>> graf = it.next();
			for(V v : graf.getVertices()) { 
				brCvorova++;
				zbir += graf.degree(v);
			}
		}
		if(brCvorova == 0) { 
			return 0;
		}
		else { 
			return (double) zbir/brCvorova;
		}
	}	
	//gustina mreze
	
	public double gustina()  {
		int maxBrGrana = graph.getVertexCount()*(graph.getVertexCount()-1)/2;
		int trenutniBrGrana = graph.getEdgeCount();
		return (double) trenutniBrGrana/maxBrGrana;
	}
}
