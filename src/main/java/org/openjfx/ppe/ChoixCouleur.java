package org.openjfx.ppe;

public class ChoixCouleur {
	private Couleur couleur;

	public ChoixCouleur(final double etat) {
		
		if(etat < 0.7) {
			couleur = Couleur.vert;
		} else if (etat >= 0.7 && etat < 1){
			couleur = Couleur.orange;
		} else if (etat == 1){
			couleur = Couleur.rouge;
		}
		
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
}