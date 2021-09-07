import java.util.Random;

public class Grana<E> {
	
	private E grana;
	private boolean znak;

	//konstruktor klase
	public Grana(E grana, boolean znak) {
		this.grana=grana;
		this.znak=znak;
	}
	//geteri i seteri
	public E getGrana() {
		return grana;
	}
	
	public void setGrana(E grana) {
		this.grana = grana;
	}
	
	public void setZnak(boolean znak) {
		this.znak = znak;
	}
	
	public String toString() { 
		if(!znak) { 
			return "-";
		}
		else {
			return "+";
		}
	}
	public boolean getZnak() { 
		return znak;
	}
}
