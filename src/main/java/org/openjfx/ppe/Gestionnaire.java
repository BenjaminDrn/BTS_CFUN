package org.openjfx.ppe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Gestionnaire {
	
	@FXML
	private TextField fields_user;
	
	@FXML
	private TextField fields_password;
	
	@FXML
	private AnchorPane Anchor_loginGestionnaire;
	
	@FXML
	private AnchorPane Anchor_interfaceGestionnaire;
	
	@FXML
	private Button button_backToLoginUsers;
	
	@FXML
	private Button button_loginGestionnaire;
	
	@FXML
	private Button btn_logout;
	
	@FXML
	private Label label_errorLoginGestionnaire;
	
	@FXML
	private GridPane grid_equipement;
	
	/*
	 * 
	 * getters 
	 * 
	 */
	
	public String getUsername() {
		return fields_user.getText();
	}
	
	public String getPassword() {
		return fields_password.getText();
	}
	
	
	/*
	 * 
	 * action button to login entree 
	 * 
	 */
	@FXML
	private void loginUsers() throws IOException{
		App.setRoot("entree");
	}
	
	/*
	 * 
	 * Verif username and password
	 * 
	 */
	
	@FXML
	private void loginInterfaceGestionnaire() throws IOException{
		
		if(getUsername().isEmpty() || getPassword().isEmpty()) {
			label_errorLoginGestionnaire.setText("Entrez votre nom d'utilisateur et le mot de passe");
		} else {
			
			// requette pour vérifier les identifiants
			if(ReqSql.setRowCount("SELECT COUNT(*) AS rowcount FROM gestionnaire WHERE username='"+ getUsername() +"' AND password='"+ getPassword() +"' ;") > 0) {
				
				Anchor_loginGestionnaire.setVisible(false);
				Anchor_interfaceGestionnaire.setVisible(true);
				
				// requette pour afficher les informations
				Connection con = SqlConnection.connect();
		    	PreparedStatement ps = null;
		    	ResultSet rs = null;
		    	try {
		    		String sql = "SELECT * FROM equipement";
		    		ps = con.prepareStatement(sql);
		    		rs = ps.executeQuery();
		    		
		    		int row = 0;
		    		
		    		while (rs.next()) {
		    			Label labelNameEquipement = new Label(rs.getString("name"));
		                Button btnDelete = new Button("Delete");
		                btnDelete.setId(rs.getInt("id") + "");
		                Button btnDisabled = new Button("Disabled");
		                btnDisabled.setId(rs.getInt("id") + "");
		                 	
		                grid_equipement.add(labelNameEquipement, 0, row, 1, 1);
		                grid_equipement.add(btnDelete, 1, row, 1, 1);
		                grid_equipement.add(btnDisabled, 2, row, 1, 1);
		                
		                grid_equipement.setHgap(10);
		                grid_equipement.setVgap(5);
		                grid_equipement.setPadding(new Insets(10, 10, 10, 10));
		                
		                row++;
		            }
		    		
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
			} else {
				label_errorLoginGestionnaire.setText("Identifiants invalides");
			}	
		}
	}

	
    @FXML 
    private void logout() throws IOException{ 
	    App.setRoot("entree"); 
    }
	 

}
