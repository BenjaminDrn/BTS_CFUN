package org.openjfx.ppe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;  

public class PrimaryController {
	
	 /*
     * 
     * 	Interface components
     *
     */
	
	@FXML
	private TextField inputFirstname;
	
	@FXML
	private ComboBox<String> choix;
	ObservableList<String> choixList = FXCollections.observableArrayList("Musculation", "Fitnesse");
	
    @FXML
    private void initialize() {
    	choix.setItems(choixList);
    }
    
    /*
     * 
     * 	Getters
     *
     */
    
    public String getinpuFirstname() {
        return inputFirstname.getText();
    }
	
	public String getchoix() {
        return choix.getValue();
    }
    
    
    /*
     * 
     * 	Generate date
     *
     */
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime now = LocalDateTime.now();  

    /*
     * 
     * 	Button action when clicked  
     * 
     */
    
    @FXML
    private void sendData() throws IOException {
    	System.out.println(getinpuFirstname() + "," + getchoix());
        insertData(getinpuFirstname(), getchoix(), dtf.format(now));
    }
    
    /*
     * 
     * 	send data to database
     *
     */
    
    private static void insertData(String firstname, String choix, String date_entree) {
    	Connection con = SqlConnection.connect();
    	PreparedStatement ps = null;
    	try {
    		String sql = "INSERT INTO users(firstname, choix, date_entree) VALUES(?,?,?)";
    		ps = con.prepareStatement(sql);
    		ps.setString(1, firstname);
    		ps.setString(2, choix);
    		ps.setString(3, date_entree);
    		ps.execute();
    		System.out.println("Data has been inserted !");
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    	
    }
    
    

}
