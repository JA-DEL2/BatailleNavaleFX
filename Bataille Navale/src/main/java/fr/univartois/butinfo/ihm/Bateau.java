package fr.univartois.butinfo.ihm;

public class Bateau {
	private int taille,nbImpacts;
	private boolean[] impacts;

	public Bateau(int taille) {
		this.taille = taille;
		nbImpacts = 0;
		impacts = new boolean[taille];
		for (int i=0; i < taille; i++) {
			impacts[i] = false;
		}
	}

	public int getTaille() {
		return taille;
	}

	public int getNbImpacts() {
		return nbImpacts;
	}

	public boolean[] getImpacts() {
		return impacts;
	}
	
	public void tirer (int partie) {
		impacts[partie] = true;
		nbImpacts ++;
	}
	
	public boolean verifCoule () {
		return nbImpacts == taille;
	}
} 


