package org.openjfx.ppe;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;



class ChoixCouleurTest {
	
	@Test
	
	public void testChoixCouleur() {
		//Test couleur vert
		ChoixCouleur vert = new ChoixCouleur(0.6);
		Couleur couleurVert = Couleur.vert;
		assertEquals("Erreur de couleur", vert.getCouleur(), couleurVert);
		
		//Test couleur orange
		ChoixCouleur orange = new ChoixCouleur(0.8);
		Couleur couleurOrange = Couleur.orange;
		assertEquals("Erreur de couleur", orange.getCouleur(), couleurOrange);
				
				
		//Test couleur rouge
		ChoixCouleur rouge = new ChoixCouleur(1);
		Couleur couleurRouge = Couleur.rouge;
		assertEquals("Erreur de couleur", rouge.getCouleur(), couleurRouge);
	}

}