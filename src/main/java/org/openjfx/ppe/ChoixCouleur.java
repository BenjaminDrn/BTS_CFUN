package org.openjfx.ppe;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ChoixCouleur {
	private Couleur couleur;

	public ChoixCouleur(double etat) {
		
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
	
	public Paint getCouleurInterface() {
		
		if(couleur == Couleur.vert) {
			return Color.GREEN;
		} else if (couleur == Couleur.orange) {
			return Color.ORANGE;
		} else if (couleur == Couleur.rouge) {
			return Color.RED;
		}
		
		return Color.RED;
		
	}


}