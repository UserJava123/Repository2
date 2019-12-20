package mycompany.invoice;

import DAO.UserDAO;
import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application
{
	public static void main( String[] args )
    {
		
        launch(args);
    }

	
	@Override
	public void start(Stage primarystage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("log.fxml"));

		AnchorPane box = loader.load(); 

		Scene scene = new Scene(box);
		primarystage.setScene(scene);
		primarystage.setTitle("Witaj JavaFX");
		primarystage.show();
		
	}
}
