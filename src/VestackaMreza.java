import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class VestackaMreza {

	
	//klasterabilan graf 
	
	public static UndirectedSparseGraph<Integer,Grana<String>> maliKlasterabilniGraf() {
			
		UndirectedSparseGraph<Integer,Grana<String>> graf = new UndirectedSparseGraph<Integer,Grana<String>>();
		for(int i=1;i<10;i++) {
			graf.addVertex(i);
		}
			
			graf.addEdge(new Grana<String>(1+ " " + 2, true), 1, 2);
			graf.addEdge(new Grana<String>(2+ " " + 3, true), 2, 3);
			graf.addEdge(new Grana<String>(3+ " " + 1, true), 3, 1);
			graf.addEdge(new Grana<String>(3+ " " + 4, false), 3, 4);
			graf.addEdge(new Grana<String>(5+ " " + 6, true), 5, 6);
			graf.addEdge(new Grana<String>(4+ " " + 6, true), 4, 6);
			graf.addEdge(new Grana<String>(4+ " " + 5, true), 4, 5);
			graf.addEdge(new Grana<String>(5+ " " + 7, false), 5, 7);
			graf.addEdge(new Grana<String>(7+ " " + 8, true), 7, 8);
			graf.addEdge(new Grana<String>(7+ " " + 9, true), 7, 9);
			graf.addEdge(new Grana<String>(8+ " " + 9, true), 8, 9);
			return graf;
			
		};
		//neklasterabilan graf
		public static UndirectedSparseGraph<Integer,Grana<String>> maliNeklasterabilniGraf() {
			
			UndirectedSparseGraph<Integer,Grana<String>> graf = new UndirectedSparseGraph<Integer,Grana<String>>();
			for(int i=1;i<10;i++) {
				graf.addVertex(i);
			}
				
				graf.addEdge(new Grana<String>(1+ " " + 2, true), 1, 2);
				graf.addEdge(new Grana<String>(2+ " " + 3, true), 2, 3);
				graf.addEdge(new Grana<String>(3+ " " + 1, true), 3, 1);
				graf.addEdge(new Grana<String>(3+ " " + 4, false), 3, 4);
				graf.addEdge(new Grana<String>(5+ " " + 6, true), 5, 6);
				graf.addEdge(new Grana<String>(4+ " " + 6, true), 4, 6);
				graf.addEdge(new Grana<String>(4+ " " + 5, true), 4, 5);
				graf.addEdge(new Grana<String>(5+ " " + 7, false), 5, 7);
				graf.addEdge(new Grana<String>(7+ " " + 8, true), 7, 8);
				graf.addEdge(new Grana<String>(7+ " " + 9, true), 7, 9);
				graf.addEdge(new Grana<String>(8+ " " + 9, false), 8, 9);
				return graf;
				
			};
}
