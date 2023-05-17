package fr.univartois.butinfo.ihm;

public class PartieBateau {
	private Bateau bateau;
	private int position;
	private boolean estTouchee;
	
	public PartieBateau (Bateau  bateau, int position) {
		if (position >= 0 && position < bateau.getTaille()) {
			this.bateau = bateau;
			this.position = position;
			estTouchee = false;
		}
	}
	
	public PartieBateau () {
		this.bateau = null;
		this.position = -1;
		estTouchee = false;
	}
	
	public Bateau getBateau() {
		return bateau;
	}

	public int getPosition() {
		return position;
	}
	
	public boolean getEstTouchee() {
		return estTouchee;
	}
 
	public boolean tirer () {
		if (estTouchee == true) return false;
		estTouchee = true;
		if (estVide())return false;
		bateau.tirer(position);
		return true;
	}
	
	public boolean estVide () {
		return (position == -1 || bateau == null);
	}

	public boolean verifCoule() {
		if (this.estVide()) return false;
 		return bateau.verifCoule();
	}
	
	public String toString() {
		return Integer.toString(position);
	}
}