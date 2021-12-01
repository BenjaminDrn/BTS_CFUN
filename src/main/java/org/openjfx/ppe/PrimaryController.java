package org.openjfx.ppe;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PrimaryController {
	
	//private Button submit;
	@FXML
	private ComboBox<String> choix;
	ObservableList<String> choixList = FXCollections.observableArrayList("Musculation", "Fitnesse");
	

    @FXML
    private void switchToSecondary() throws IOException {
        //App.setRoot("secondary");
    }
    
    @FXML
    private void initialize() {
    	choix.setItems(choixList);
    }

}
