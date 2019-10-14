package zoo.zoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

	@Override
	public void start(Stage primarystage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("../../stage.fxml"));

		AnchorPane box = loader.load(); 
		
		Scene scene = new Scene(box);
		primarystage.setScene(scene);
		primarystage.setTitle("Witaj JavaFX");
		primarystage.setResizable(true);
		primarystage.show();
		
	}
}
