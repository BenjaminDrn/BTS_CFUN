package org.openjfx.ppe;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entree {
	
	
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
	private AnchorPane Anchor_displayCodebarres;
	
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
	private Button button_exitDisplayCodebarres;
	
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
	
	@FXML
	private Circle circle_etatMuscu;
	
	@FXML
	private Circle circle_etatFitness;
	
	@FXML
	private ImageView img_loginGestionnaire;
	
	@FXML
	private ImageView img_codebarres;
	
	
	/*
	 * 
	 * Getters 
	 *
	 */
	
	public String getLastname() {
		return fields_lastname.getText();
	}
	
	public String getFirstname() {
		return fields_firstname.getText();
	}
	
	public String getCodebarres() {
		return fields_codebarres.getText();
	}
	
	/*
	 * 
	 * Initialisation de complexe 
	 *
	 */
	
	private static int nbPlaceTotalFitness = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='musculation';");
	private static int nbPlaceTotalMuscu = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='fitness';");
	private static final String nomComplexe = "CFUN";
	Complexe leComplexe = new Complexe(nbPlaceTotalMuscu, nbPlaceTotalFitness, nomComplexe);
	
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
		if(getLastname().isEmpty() || getFirstname().isEmpty()) {
			label_errorLogin.setText("Entrez votre nom et prénom");	
		} else {
			if(ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM users WHERE firstname='"+getFirstname()+"' AND lastname='"+getLastname()+"' AND datesortie IS NULL ;") == 0) {
				
				Anchor_login.setVisible(false);
				
				ChoixCouleur choixCouleurMuscu = new ChoixCouleur(leComplexe.etatMuscu());
				circle_etatMuscu.setFill(choixCouleurMuscu.getCouleurInterface());
				
				ChoixCouleur choixCouleurFit = new ChoixCouleur(leComplexe.etatFit());
				circle_etatFitness.setFill(choixCouleurFit.getCouleurInterface());
				
				//Affichage de l'interface
				Anchor_choixSalle.setVisible(true);
				label_bienvenue.setText("Bonjour " + getFirstname());
				
				// Niveau d'occupation des salles
				label_nbPlacesFitness.setText("places : " + leComplexe.getNbPlacesRestantesFit() + "/" + nbPlaceTotalFitness);
				label_nbPlacesMuscu.setText("places : " + leComplexe.getNbPlacesRestantesMuscu() + "/" + nbPlaceTotalMuscu);
				
			} else {
				label_errorLogin.setText("Vous êtes déjà entrée");
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
		label_errorCodebarres.setText("");
	}
	
	/*
	 * 
	 * action button to valid codebarres
	 *
	 */
	
	@FXML 
	private void exitComplex() throws IOException{
		if(getCodebarres().isEmpty() && getCodebarres().length() != 12) {
			label_errorCodebarres.setText("Entrez un code-barres de 12 chiffres");
		} else {
			// vérification dans la bdd si le numéro du code barre existe 
			int numUsersExit = ReqSql.setSendDataSpecificRow(getCodebarres());
			
			ReqSql.setUpdateDataUser(datetime(), leComplexe.sortieUsager(numUsersExit).getMontant(), getCodebarres());
			
		}
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
		label_errorLogin.setText("");
	}
	
	/*
	 * 
	 * action button to choose complex
	 *
	 */
	
	@FXML
	private void fitnessComplex() throws IOException{
		
		if(leComplexe.getNbPlacesRestantesFit() > 0) {
			// Changement d'interface
			Anchor_choixSalle.setVisible(false);
			
			// Génération du code barre
			String codebarres = CodeBarres.SetCodeBarre();
			File Img = CodeBarres.SetcreateImage(codebarres);
			
			// Initialisation d'une nouvelle arrivée en fitness
			Arrivee arriveFitness = new Arrivee(leComplexe, "fitness");
			
			if (leComplexe.entreeUsager(arriveFitness)) {
				 System.out.println(arriveFitness.afficheBillet()); 
				 System.out.println(leComplexe.lesInfos());
				 } 
			
			//Enregistrement en base de donnée d'une nouvelle arrivée
			ReqSql.setInsertEntree(getLastname(), getFirstname(), "fitness", codebarres, datetime(), arriveFitness.afficheBillet());
			
			Anchor_displayCodebarres.setVisible(true);
			Image image = new Image(Img.toURI().toString());
			img_codebarres.setImage(image);
			
		} else {
			
			System.out.println("Plus de place");
			
		}
		
	}
	
	@FXML
	private void muscuComplex() throws IOException{
		
		if(leComplexe.getNbPlacesRestantesMuscu() > 0) {
			// Changement d'interface
			Anchor_choixSalle.setVisible(false);
			
			// Génération du code barre
			String codebarres = CodeBarres.SetCodeBarre();
			File Img = CodeBarres.SetcreateImage(codebarres);
			
			// Initialisation d'une nouvelle arrivée en musculation
			Arrivee arriveMusculation = new Arrivee(leComplexe, "musculation");
			
			if (leComplexe.entreeUsager(arriveMusculation)) {
				 System.out.println(arriveMusculation.afficheBillet());
				 System.out.println(leComplexe.lesInfos());
				 } 
			
			//Enregistrement en base de donnée d'une nouvelle arrivée
			ReqSql.setInsertEntree(getLastname(), getFirstname(), "musculation", codebarres, datetime(), arriveMusculation.afficheBillet());
			
			Anchor_displayCodebarres.setVisible(true);
			Image image = new Image(Img.toURI().toString());
			img_codebarres.setImage(image);
			
		} else {
			
			System.out.println("Plus de place");
			
		}
	}
	
	@FXML 
	private void exitPopUpCodebarres() throws IOException{
		Anchor_displayCodebarres.setVisible(false);
		Anchor_login.setVisible(true);
		label_errorLogin.setText("");
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
	
}
