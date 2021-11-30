package org.openjfx.ppe;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class ArriveeTest {
	
	@Test
	void testArrivee() {
		Complexe newComplexe = new Complexe(4, 4, "CFUN");
		
		Arrivee arrivee65 = new Arrivee(newComplexe, 'F');
		arrivee65.AddTime(65);
		assertEquals("Erreur d'arrivée 65 minutes", arrivee65.getMontant(), 1.5, 0.0);
		
		Arrivee arrivee12 = new Arrivee(newComplexe, 'F');
		arrivee12.AddTime(12);
		assertEquals("Erreur d'arrivée 12 minutes", arrivee12.getMontant(), 0.0, 0.0);
		
		Arrivee arrivee35 = new Arrivee(newComplexe, 'F');
		arrivee35.AddTime(35);
		assertEquals("Erreur d'arrivée 35 minutes", arrivee35.getMontant(), 1.0, 0.0);
	}

}
