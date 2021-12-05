package org.openjfx.ppe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
	private AnchorPane Anchor_equipementMusculation;

	@FXML
	private AnchorPane Anchor_equipementFitness;
	
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
	private Button button_backMuscuComplex;
	
	@FXML
	private Button button_backFitnessComplex;
	
	@FXML
	private Button button_backCodeBarre;
	
	@FXML
	private Label label_errorLogin;
	
	@FXML
	private Label label_errorCodebarres;
	
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
	 * action button to enter
	 *
	 */
	
	@FXML
    private void enterComplex() throws IOException {
		if(getLastname().isEmpty() && getFirtname().isEmpty()) {
			label_errorLogin.setText("Entrez votre nom et prénom");	
		} else {
			Anchor_login.setVisible(false);
			Anchor_choixSalle.setVisible(true);
		}
    }
	
	@FXML 
	private void validExitComplex() throws IOException{
		if(getCodebarres().isEmpty() && getCodebarres().length() < 12) {
			label_errorCodebarres.setText("Entrez un code-barres de 12 chiffres");
		} else {
			// vérification dans la bdd si le numéro du code barre existe 
			
			// si il existe, enregistrer le prix que doit l'employer 
		}
	}
	
	/*
	 * 
	 * action button to exit
	 *
	 */

	@FXML
	private void enterCodebarres() throws IOException{
		Anchor_login.setVisible(false);
		Anchor_codebarres.setVisible(true);
	}
	
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
		Anchor_choixSalle.setVisible(false);
		Anchor_equipementFitness.setVisible(true);

	}
	
	@FXML
	private void muscuComplex() throws IOException{
		Anchor_choixSalle.setVisible(false);
		Anchor_equipementMusculation.setVisible(true);
	}
	
	/*
	 * 
	 * action button to come back on choose complex
	 *
	 */
	
	@FXML
	private void backChooseComplexMuscu(){		
		Anchor_equipementMusculation.setVisible(false);
		Anchor_choixSalle.setVisible(true);
	}

	@FXML 
	private void backChooseComplexFitness() {
		Anchor_equipementFitness.setVisible(false);
		Anchor_choixSalle.setVisible(true);	
	}
	
	
	/*
     * 
     * 	send data to database
     *
     */
    
    private static void insertData(String lastname, String firstname, String salle, String equipement, String codebar) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO users(lastname, firstname, salle, equipement, codebar) VALUES(?,?,?,?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, lastname);
    		ps.setString(2, firstname);
    		ps.setString(3, salle);
    		ps.setString(3, equipement);
    		ps.setString(3, codebar);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    /*
     * 
     * 	change value in database
     *
     */
    
    private static void changeValue(String occupation, String nameEquipement) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "UPDATE equipement SET occupation = ? WHERE name = ?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, occupation);
    		ps.setString(2, nameEquipement);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    }
	
    /*
     * 
     * 	read specific row on the database 
     *
     */
    
    private static void readSpecificRow(String codebarres) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
    		String sql = "SELECT codebarres FROM enter WHERE codebarres = ?";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, codebarres);
    		rs = ps.executeQuery();
    		
    		String result = rs.getString(1);
    		System.out.println(result);
    		
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	} finally {
    		try {
    			rs.close();
    			ps.close();
    			con.close();
    		} catch(SQLException e) {
    			System.out.println(e.toString());
    		}
    	}
    }
    
    
    /*
     * 
     * 	generate code-barres 
     *
     */
    
    double randomDouble = Math.random();
   
    
    
	
}
