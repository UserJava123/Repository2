package mycompany.invoice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		loader.setLocation(this.getClass().getResource("indexView.fxml"));

		BorderPane box = loader.load(); 
		
		Scene scene = new Scene(box);
		primarystage.setScene(scene);
		primarystage.setTitle("Witaj JavaFX");
		primarystage.setResizable(false);
		primarystage.setMaximized(true);
		primarystage.show();
		
	}
}
