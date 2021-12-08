package org.openjfx.ppe;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Entree {
	
	private static int nbPlaceTotalFitness = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='musculation';");
	private static int nbPlaceTotalMuscu = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='fitness';");
	private static final String nomComplexe = "C Fun";
	
	/*
	 * 
	 * Interface components 
	 *
	 */
	
	@FXML
	private AnchorPane Anchor_login;
	
	@FXML
	private AnchorPane Anchor_codebarres;
	
	@FXML
	private AnchorPane Anchor_choixSalle;
	
	@FXML
	private TextField fields_lastname;
	
	@FXML
	private TextField fields_firstname;
	
	@FXML
	private TextField fields_codebarres;
	
	@FXML
	private Button button_login;
	
	@FXML
	private Button button_exit;
	
	@FXML
	private Button button_validExit;
	
	@FXML
	private Button button_fitnessComplex;
	
	@FXML
	private Button button_muscuComplex;
	
	@FXML
	private Button button_backCodeBarre;
	
	@FXML
	private Button button_loginGestionnaire;
	
	@FXML
	private Label label_errorLogin;
	
	@FXML
	private Label label_errorCodebarres;
	
	@FXML 
	private Label label_bienvenue;
	
	@FXML
	private Label label_nbPlacesFitness;
	
	@FXML
	private Label label_nbPlacesMuscu;
	
	
	/*
	 * 
	 * Getters 
	 *
	 */
	
	private String getLastname() {
		return fields_lastname.getText();
	}
	
	private String getFirtname() {
		return fields_firstname.getText();
	}
	
	private String getCodebarres() {
		return fields_codebarres.getText();
	}
	
	/*
	 * 
	 * Création du complexe
	 * 
	 */
	
	
	Complexe complexe = new Complexe(nbPlaceTotalFitness, nbPlaceTotalMuscu, "CFUN");
	
	/*
	 * 
	 * action button to login Gestionnaire 
	 * 
	 */
	
	@FXML
	private void loginGestionnaire() throws IOException{
		App.setRoot("gestionnaire");
	}
	
	/*
	 * 
	 * action button to enter complexe
	 *
	 */
	
	@FXML
    private void enterComplex() throws IOException {
		if(getLastname().isEmpty() || getFirtname().isEmpty()) {
			label_errorLogin.setText("Entrez votre nom et prénom");	
		} else {
			Anchor_login.setVisible(false);
			
			//Initialisation de la clasee complexe
			
			
			//Affichage de l'interface
			Anchor_choixSalle.setVisible(true);
			label_bienvenue.setText("Bonjour " + getFirtname());
			
			
			// Niveau d'occupation des salles
			
			
			label_nbPlacesFitness.setText("places : "  + "/" + nbPlaceTotalFitness);
			
			
			
			label_nbPlacesMuscu.setText("places : "  + "/" + nbPlaceTotalMuscu);
			
		}
    }
	
	/*
	 * 
	 * action button to valid codebarres
	 *
	 */
	
	@FXML 
	private void exitComplex() throws IOException{
		if(getCodebarres().isEmpty() && getCodebarres().length() < 12) {
			label_errorCodebarres.setText("Entrez un code-barres de 12 chiffres");
		} else {
			// vérification dans la bdd si le numéro du code barre existe 
			if(ReqSql.SetReadSpecificRow(getCodebarres()) == true) {
				
				// calculer la différence entre la date d'entrée et de sortie 
				
				// calculer le cout
				
				// si il existe, enregistrer le prix que doit l'employer 
				//ReqSql.setInsertCost(timeSpend, cost, codebarres);
				
			}
			
			
			
		}
	}
	
	

	/*
	 * 
	 * action button to enter in codeBarres interface
	 *
	 */

	@FXML
	private void enterCodebarres() throws IOException{
		Anchor_login.setVisible(false);
		Anchor_codebarres.setVisible(true);
	}
	
	/*
	 * 
	 * action button to leave codeBarres interface
	 *
	 */
	
	@FXML 
	private void exitCodebarres() throws IOException {
		Anchor_codebarres.setVisible(false);
		Anchor_login.setVisible(true);
	}
	
	/*
	 * 
	 * action button to choose complex
	 *
	 */
	
	@FXML
	private void fitnessComplex() throws IOException{
		//Anchor_choixSalle.setVisible(false);
		CodeBarre();
		
		String codebarres = CodeBarre();
		
		Arrivee arriveeFitness = new Arrivee(complexe, 'M');
		
		//Enregistrement en base de donnée
		//ReqSql.setInsertEntree(getLastname(), getFirtname(), "fitness", codebarres, datetime());

	}
	
	@FXML
	private void muscuComplex() throws IOException{
		//Anchor_choixSalle.setVisible(false);
		
		String codebarres = CodeBarre();
		
		//Enregistrement en base de donnée
		//ReqSql.setInsertEntree(getLastname(), getFirtname(), "musculation", codebarres, datetime());
		
	}
    
    /*
     * 
     * generate code-barres 
     *
     */
    
     private String CodeBarre() {
		String pattern = "ddMMyyHHmm";
		SimpleDateFormat dateTimeFormatCodebarres = new SimpleDateFormat(pattern);
		String dateEntree = dateTimeFormatCodebarres.format(new Date());
		
		int randomNb = (int) Math.floor( Math.random() * 99 ) + 10;
		
		String nbCodeBarres = randomNb + "" + dateEntree;
		
		//CodeBarres.createImage("codebarres.png", nbCodeBarres);
		
		return nbCodeBarres;
     }
     
     /*
      * 
      * generate datetime 
      *
      */
     
     private String datetime() {
    	 String pattern = "yyyy-MM-dd HH:mm";
    	 SimpleDateFormat dateTimeFormat = new SimpleDateFormat(pattern);
    	 String datetime = dateTimeFormat.format(new Date());
    	 
    	 return datetime;
     }
     
     
 	private static final String TYPE = "Type opération (E)ntrée ou (S)ortie : ";
 	private static final String SORTIE = "N° d'entrée à sortir : ";
 	private static final String CHOIX = "(M)usculation, (F)itness : ";
 	private static final String AUTRE = "Autre opération (O/N) : ";
 	
 	public static void main(String[] args) {
 		Complexe leComplexe = new Complexe(nbPlaceTotalMuscu, nbPlaceTotalFitness, nomComplexe);
 		
 		char repAutre = 'O';
 		char repType;
 		int repSortie;
 		char repChoix;
 		
 		while (repAutre == 'O') {
 			repType = Character.toUpperCase(javax.swing.JOptionPane.showInputDialog(TYPE).charAt(0));
 			//Si il y a une entrée E 
 			if (repType == 'E') {
 				//Ont demander le choix de la salle de sport
 				repChoix = Character.toUpperCase(javax.swing.JOptionPane.showInputDialog(CHOIX).charAt(0));
 				// Arrivee (le Complexe, la salle choisi)
 				Arrivee jArrive = new Arrivee(leComplexe, repChoix);
 				// Si il existe, affciher le ticket
 				if (leComplexe.entreeUsager(jArrive)) {
 					System.out.println(jArrive.afficheBillet());
 				}
 			}
 			//Si l'entrée est S
 			else{
 				
 				repSortie = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(SORTIE));
 				System.out.println(leComplexe.sortieUsager(repSortie).afficheTicket());
 			}
 			System.out.print(leComplexe.lesInfos());
 			repAutre = Character.toUpperCase(javax.swing.JOptionPane.showInputDialog(AUTRE).charAt(0));
 		}
 		System.exit(0);	
 	}
	
}
