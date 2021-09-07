import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Main {
	
	public static void main(String[] args) throws IOException { 
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int unos;
		do {
			System.out.println("Unesite: \n 1 - Za graf iz fajla \n 2 - Za random graf \n 3 - Za vestacki napravljen graf");
			unos = Integer.parseInt(br.readLine());
			
		}while(unos<=0 || unos > 3);
		
		
		if(unos == 1) { 
			System.out.println("Unesite: \n 1 - Za wiki.rfa \n 2 - Za epinions \n 3 - slashdot");
			int unosG = Integer.parseInt(br.readLine());
			if(unosG == 1) { 
				UndirectedSparseGraph<String, Grana<String>> graf = ucitajGraf();
				poziv(graf);
			}
			else if(unosG == 2) { 
				UndirectedSparseGraph<Integer, Grana<String>> graf = ucitajGraf("epinions");
				poziv(graf);
			}
			else  {
				UndirectedSparseGraph<Integer, Grana<String>> graf = ucitajGraf("slashdot");
				poziv(graf);
			}
		}
		else if(unos == 2) { 
			System.out.println("Unesite broj cvorova: ");
			int brCvorova = Integer.parseInt(br.readLine());
			RandomNetwork rn = new RandomNetwork();
			System.out.println("Unesite 1 ako zelite klasterabilnu random mrezu, a 2 ukoliko zelite neklasterabilnu:");
			int unosRN = Integer.parseInt(br.readLine());
			if(unosRN == 1) { 
				UndirectedSparseGraph<Integer, Grana<String>> graf = rn.napraviKlasterabilanGraf(brCvorova);
				poziv(graf);
			}
			else { 
				UndirectedSparseGraph<Integer, Grana<String>> graf = rn.napraviNeklasterabilanGraf(brCvorova);
				poziv(graf);
			}
		}
		else {
			System.out.println("Unesite k za klasterabilnu mrezu, a n za neklasterabilnu mrezu:");
			String unosK = br.readLine();
			VestackaMreza vm = new VestackaMreza();
			if(unosK.equals("k")) { 
				UndirectedSparseGraph<Integer, Grana<String>> graf = vm.maliKlasterabilniGraf();
				poziv(graf);
			}
			else { 
				UndirectedSparseGraph<Integer, Grana<String>> graf = vm.maliNeklasterabilniGraf();	
				//ne radi 
				poziv(graf);
			}
		}
	}
	public static <V, E> void poziv(UndirectedSparseGraph<V , Grana<E>> graf) { 
		Klasterovanje k = new Klasterovanje(graf);
		System.out.println(graf);
		System.out.println("KLASTERI: \n" + k.getClusters());
		System.out.println("ANTIKOALICIJE: \n" + k.getAntikoalicije());
		System.out.println("KOALICIJE: \n" + k.getKoalicije());
		System.out.println("POTREBNO IZBACITI: \n" + k.ispisiListu());
		System.out.println("BROJ CVOROVA U GRAFU: " + k.getGraf().getVertexCount());
		System.out.println("BROJ GRANA U GRAFU: " + k.getGraf().getEdgeCount());
		System.out.println("METRIKE: ");
		System.out.println("PROSECAN STEPEN GRAFA: " + k.avgStepen());
		System.out.println("BROJ KOALICIJA: " + k.getKoalicije().size());
		System.out.println("PROSECAN STEPEN U KOALICIJAMA: " + k.avgKoalicije());
		System.out.println("BROJ ANTIKOALICIJA: " + k.getAntikoalicije().size());
		System.out.println("PROSECAN STEPEN U ANTIKOALICIJAMA: " + k.avgAntikoalicije());
		System.out.println("GUSTINA GRAFA: " + k.gustina());
		System.out.println(k.getAntikoalicije().size() == 0 ? "Mreza je klasterabilna." : "Mreza nije klasterabilna.");
	}
	//ucitavanje grafa wiki rfa
	public static UndirectedSparseGraph<String, Grana<String>> ucitajGraf() throws IOException { 
		UndirectedSparseGraph<String, Grana<String>> graf = new UndirectedSparseGraph<String, Grana<String>>();
		BufferedReader br = new BufferedReader(new FileReader("rfa"));
		
		String linija;
	    while ((linija = br.readLine()) != null) {
	    	
	    	if(linija.startsWith("SRC")) { 
	    		String source = linija.substring(4);
	    		String anotherLine = br.readLine();
	    		String dest = anotherLine.substring(4);
	    		br.readLine();
	    		String anotherAnotherLine = br.readLine();
	    		char c = anotherAnotherLine.charAt(4);
	    		if(graf.findEdge(source, dest) == null) {
		    		if(c == '1') { 
		    			graf.addEdge(new Grana(source + " " + dest, true), source, dest);
		    		}
		    		else {
		    			graf.addEdge(new Grana(source + " " + dest, false), source, dest);
		    		}
	    		}
	    	}
	    }
	    System.out.println(graf.getVertexCount());
	    return graf;
	}
	public static UndirectedSparseGraph<Integer, Grana<String>> ucitajGraf(String imeF) throws IOException { 
		UndirectedSparseGraph<Integer, Grana<String>> graf = new UndirectedSparseGraph<Integer, Grana<String>>();
		BufferedReader br = new BufferedReader(new FileReader(imeF));
		
		br.readLine();
		br.readLine();
		br.readLine();
		br.readLine();
		String linija;
	    while ((linija = br.readLine()) != null) {
	    	
	    	String[] red = linija.split("\\s+");
	    	int src = Integer.parseInt(red[0]);
	    	int tgt = Integer.parseInt(red[1]);
	    	if(graf.findEdge(src, tgt) == null) {
		    	if(Integer.parseInt(red[2]) == -1 || Integer.parseInt(red[2]) == 0) { 
		    		graf.addEdge(new Grana(src + " " + tgt, false), src, tgt);
		    	}
		    	else {
		    		graf.addEdge(new Grana(src + " " + tgt, true), src, tgt);
		    	}
	    	}
	    }
	    return graf;
	}
}
