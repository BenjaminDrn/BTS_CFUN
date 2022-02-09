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
import javafx.scene.shape.Rectangle;
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
	private AnchorPane Anchor_logout;
	
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
	private Rectangle rect_etatMuscu;
	
	@FXML
	private Rectangle rect_etatFitness;
	
	@FXML
	private ImageView img_loginGestionnaire;
	
	@FXML
	private ImageView img_codebarres;
	
	
	/*
     * 
     *  ============================== generate datetime ============================== 
     *
     */
    
    private String datetime() {
   	 String pattern = "yyyy-MM-dd HH:mm";
   	 SimpleDateFormat dateTimeFormat = new SimpleDateFormat(pattern);
   	 String datetime = dateTimeFormat.format(new Date());
   	 
   	 return datetime;
    }
	
	
	/*
	 * 
	 * ============================== new complex ============================== 
	 *
	 */
	
	private static int nbPlaceTotalFitness = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='musculation';");
	private static int nbPlaceTotalMuscu = ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM equipement WHERE salle='fitness';");
	private static final String nomComplexe = "CFUN";
	Complexe leComplexe = new Complexe(nbPlaceTotalMuscu, nbPlaceTotalFitness, nomComplexe);
	
	/*
	 * 
	 * ============================== Page Login ============================== 
	 * 
	 */
	
	// return Lastname
	public String getLastname() {
		return fields_lastname.getText();
	}
	
	// return Firstname
	public String getFirstname() {
		return fields_firstname.getText();
	}
	
	// Event when click on button "Entrer". This event control if user is already enter and if he is no enter, app show Complex 
	@FXML
    private void enterComplexPage() throws IOException {
		if(getLastname().isEmpty() || getFirstname().isEmpty()) {
			label_errorLogin.setText("Entrez votre nom et prénom");	
		} else {
			if(ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM users WHERE firstname='"+getFirstname()+"' AND lastname='"+getLastname()+"' AND datesortie IS NULL ;") == 0) {
				
				// ===== Hide Login page ===== //
				Anchor_login.setVisible(false);
				
				// ===== Show Complex page ===== //
				Anchor_choixSalle.setVisible(true);
				label_bienvenue.setText("Bonjour " + getFirstname());
				
				// ===== Data show in Complex page ===== //
				ChoixCouleur choixCouleurMuscu = new ChoixCouleur(leComplexe.etatMuscu());
				rect_etatMuscu.setFill(choixCouleurMuscu.getCouleurInterface());
				ChoixCouleur choixCouleurFit = new ChoixCouleur(leComplexe.etatFit());
				rect_etatFitness.setFill(choixCouleurFit.getCouleurInterface());
				label_nbPlacesFitness.setText("places : " + leComplexe.getNbPlacesRestantesFit() + "/" + nbPlaceTotalFitness);
				label_nbPlacesMuscu.setText("places : " + leComplexe.getNbPlacesRestantesMuscu() + "/" + nbPlaceTotalMuscu);
				
			} else {
				label_errorLogin.setText("Vous êtes déjà entrée");
			}
		}
    }
	
	// Event when click on button "Sortir de la salle".
	@FXML
	private void enterLogoutPage() throws IOException{
		// ===== Hide login page ===== //
		Anchor_login.setVisible(false);
		
		// ===== Show logout page ===== //
		Anchor_logout.setVisible(true);
		label_errorCodebarres.setText("");
	}
	
	// Event when click on icon button user. Redirect to "gestionnaire" interface.
	@FXML
	private void loginGestionnaire() throws IOException{
		App.setRoot("gestionnaire");
	}
	
	/*
	 * 
	 * ============================== Page Complex ============================== 
	 * 
	 */
	
	// Event when click on button "Fitness".
	@FXML
	private void fitnessComplex() throws IOException{
		if(leComplexe.getNbPlacesRestantesFit() > 0) {
			
			// ===== Set new arrived ===== //
			Arrivee arrivee = new Arrivee(leComplexe, "fitness");
			
			// ===== Set new arrived in database ===== //
			String codebarres = CodeBarres.SetCodeBarre();
			File Img = CodeBarres.SetcreateImage(codebarres);
			if (leComplexe.entreeUsager(arrivee)) {
				System.out.println(arrivee.afficheBillet());
				ReqSql.setInsertEntree(getLastname(), getFirstname(), "fitness", codebarres, datetime(), arrivee.afficheBillet());//arriveFitness.afficheBillet()
			}
			
			// ===== Hide complex page ===== //
			Anchor_choixSalle.setVisible(false);
			
			// ===== Show code barres page ===== //
			Anchor_displayCodebarres.setVisible(true);
			Image image = new Image(Img.toURI().toString());
			img_codebarres.setImage(image);
			
		} else {
			System.out.println("Plus de place");	
		}
	}
	
	// Event when click on button "Musculation".
	@FXML
	private void muscuComplex() throws IOException{
		if(leComplexe.getNbPlacesRestantesMuscu() > 0) {
			
			// ===== Set new arrived ===== //
			Arrivee arrivee = new Arrivee(leComplexe, "musculation");
			
			// ===== Set new arrived in database ===== //
			String codebarres = CodeBarres.SetCodeBarre();
			File Img = CodeBarres.SetcreateImage(codebarres);
			if (leComplexe.entreeUsager(arrivee)) {
				System.out.println(arrivee.afficheBillet());
				ReqSql.setInsertEntree(getLastname(), getFirstname(), "musculation", codebarres, datetime(), arrivee.afficheBillet());//arriveMusculation.afficheBillet()
			} 
			
			// ===== Reset value of fields in login page ===== //
			fields_lastname.setText("");
			fields_firstname.setText("");
			
			// ===== Hide complex page ===== //
			Anchor_choixSalle.setVisible(false);
			
			// ===== Show code barres page ===== //
			Anchor_displayCodebarres.setVisible(true);
			Image image = new Image(Img.toURI().toString());
			img_codebarres.setImage(image);
			
		} else {
			System.out.println("Plus de place");
		}
	}
	
	
	
	/*
	 * 
	 * ============================== Page Code barres ============================== 
	 * 
	 */
	
	@FXML 
	private void exitCodebarresPage() throws IOException{
		// ===== Hide code barres page ===== //
		Anchor_displayCodebarres.setVisible(false);
		
		// ===== show login page ===== //
		Anchor_login.setVisible(true);
		label_errorLogin.setText("");
	}
	
	
	/*
	 * 
	 * ============================== Page logout ============================== 
	 * 
	 */
	
	// return code barres
	public String getCodebarres() {
		return fields_codebarres.getText();
	}
	
	// Event when click on button "Valider"
	@FXML 
	private void exitComplex() throws IOException{
		if(getCodebarres().isEmpty() && getCodebarres().length() != 12) {
			label_errorCodebarres.setText("Entrez un code-barres de 12 chiffres");
		} else {
			// verify if the code-barres is find in database
			int numUsersExit = ReqSql.setSendDataSpecificRow(getCodebarres());
			ReqSql.setUpdateDataUser(datetime(), leComplexe.sortieUsager(numUsersExit).afficheTicket(), getCodebarres());// leComplexe.sortieUsager(numUsersExit).getMontant()
			//System.out.println(leComplexe.sortieUsager(numUsersExit).afficheTicket());
			
			exitLogoutPage();
		}
	}
	
	// Event when click on button "Retour"
	@FXML 
	private void exitLogoutPage() throws IOException {
		// ===== hide logout page ===== //
		Anchor_logout.setVisible(false);
		
		// ===== show login page ===== //
		Anchor_login.setVisible(true);
		label_errorLogin.setText("");
	}
	
}
