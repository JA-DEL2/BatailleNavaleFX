package fr.univartois.butinfo.ihm;

import java.util.Arrays;

public class GrilleJeu {
	private int largeur,hauteur;
	private PartieBateau[][]  grille;
	
	public GrilleJeu (int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		grille = new PartieBateau[hauteur][largeur];
	}
	
	public int getLargeur() {
		return largeur;
	}


	public int getHauteur() {
		return hauteur;
	}
	
	public boolean placerBateauAlea(Bateau bateau, int[] pos, boolean sens) {
		if (sens) return placerHorizontal(bateau, pos);
		else return placerVertical(bateau, pos);
	}


	public PartieBateau[][] getGrille() {
		return grille;
	}
	
	public void createVoidCase () {
		for (int i=0; i<10;i++) {
			for (int j=0; j<10;j++) {
				if (grille[i][j] == null) grille[i][j] = new PartieBateau();
			}
		}
	}

	public PartieBateau accesCase (int[] xy) {
		return grille[xy[0]][xy[1]];
	}
	
	public boolean verifHori(int taille, int[] pos) {
		if (largeur-pos[0] < taille) return false;
		for (int i= pos[0]; i<largeur && i<pos[0]+taille;i++) {
			if(grille[pos[1]][i] != null)return false; 
		}
		return true;
	}
	
	public boolean placerHorizontal (Bateau bateau, int[] pos) {
		if (verifHori(bateau.getTaille(),pos)) {
			for (int i=0; i<bateau.getTaille();i++) {
				grille[pos[1]][pos[0]+i] = new PartieBateau(bateau,i);
			}
			return true;
		}
		return false;
	}
	
	public boolean verifVert (int taille, int[] pos) {
		if (hauteur-pos[1] < taille) return false;
		for (int i= pos[1]; i<hauteur && i<pos[1]+taille;i++) {
			if(grille[i][pos[0]] != null)return false; 
		}
		return true;
	}
	
	public boolean placerVertical (Bateau bateau, int[] pos) {
		if (verifVert(bateau.getTaille(),pos)) {
			for (int i=0; i<bateau.getTaille();i++) {
				grille[pos[1]+i][pos[0]] = new PartieBateau(bateau,i);
			}
			return true;
		}
		return false;
	}
	
	public boolean tirer (int[] pos) {
		return grille[pos[0]][pos[1]].tirer();
	}

	
	public boolean verifAllCoule () {
		int nbCoules = 0;
		for (int i=0; i<hauteur; i++) {
			for (int j=0; j<largeur; j++) {
				if ((grille[i][j].getPosition() != -1) && grille[i][j].verifCoule()) {
					nbCoules ++;
				}
			}
		}
		return nbCoules == 30;
	}
	
	public void delBateaux () {
		for (int l = 0; l < hauteur;l++) {
			for (int c=0; c < largeur; c++) {
				if (grille[l][c].getPosition() != -1) grille[l][c] = new PartieBateau();
			}
		}
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i=0; i<largeur; i++) {
			for (int j=0; i<hauteur;i++) {
				ret += grille[i][j].toString() + " ";
			}
			ret += "| ";
		}
		return ret;
	}
	
	
}