import java.util.ArrayList;
import java.util.Random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RandomNetwork {
	
	UndirectedSparseGraph<Integer, Grana<String>> graf = new UndirectedSparseGraph<Integer, Grana<String>>();
	public UndirectedSparseGraph<Integer, Grana<String>> napraviKlasterabilanGraf(int brojCvorova) { 
		for(int i=0; i<brojCvorova; i++)  {
			graf.addVertex(i);
		}
		
		Random r = new Random();
		int brGrana = r.nextInt(brojCvorova * (brojCvorova-1)/2);
		int brojac = 0;
		while(brojac != brGrana) { 
			int randomPrvi = r.nextInt(brojCvorova);
			int randomDrugi = r.nextInt(brojCvorova);
			
			if(randomPrvi != randomDrugi && graf.findEdge(randomPrvi, randomDrugi) == null) { 
				Grana<String> grana = new Grana<String>(randomPrvi+ " " + randomDrugi, true);
				graf.addEdge(grana, randomPrvi, randomDrugi);
			}
			brojac++;
		}
		return graf;
	} 
	public UndirectedSparseGraph<Integer, Grana<String>> napraviNeklasterabilanGraf(int brojCvorova) { 
		for(int i=0; i<brojCvorova; i++)  {
			graf.addVertex(i);
		}
		
		Random r = new Random();
		int brGrana = r.nextInt(brojCvorova * (brojCvorova-1)/2);
		int brojac = 0;
		while(brojac != brGrana) { 
			int randomPrvi = r.nextInt(brojCvorova);
			int randomDrugi = r.nextInt(brojCvorova);
			
			if(randomPrvi != randomDrugi && graf.findEdge(randomPrvi, randomDrugi) == null) {
				int p = r.nextInt(9);
				boolean znak = true;
				if(p>=0 && p<=7) { 
					znak = false;
				}
				Grana<String> grana = new Grana<String>(randomPrvi+ " " + randomDrugi, znak);
				graf.addEdge(grana, randomPrvi, randomDrugi);
			}
			brojac++;
		}
		return graf;
	} 
	public <E> UndirectedSparseGraph<Integer, Grana<E>> izbaciGrane(UndirectedSparseGraph<Integer, Grana<E>> graf) { 
		Klasterovanje k = new Klasterovanje(graf);
		ArrayList<Grana<E>> grane = k.getIzbacene();
		for(Grana<E> s : grane) { 
			graf.removeEdge(s);
		}
		return graf;
	}
}
