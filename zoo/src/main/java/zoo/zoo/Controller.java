package zoo.zoo;
import java.awt.Event;
import java.net.URL;
import java.util.ResourceBundle;

import animals.Animal;
import animals.Cat;
import animals.Cow;
import animals.Dog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;

public class Controller implements Initializable {
	
	@FXML 
	Button btnCat, btnDog, btnCow;
	
	Animal animal;
	
	public void initialize(URL location, ResourceBundle resources) {
		EventHandler<ActionEvent> btnHandler =  new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				if(e.getSource() == btnCat) {
					animal = new Cat();
				} 
				else if(e.getSource() == btnDog) {
					animal = new Dog();
				}
				else if(e.getSource() == btnCow) {
					animal = new Cow();
				}		
				animal.voice();
			}
		};
		
		btnCat.addEventHandler(ActionEvent.ACTION, btnHandler);
		btnCow.addEventHandler(ActionEvent.ACTION, btnHandler);
		btnDog.addEventHandler(ActionEvent.ACTION, btnHandler);
		
	}
}
