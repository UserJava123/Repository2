package mycompany.invoice;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.UserDAO;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class AddUserController implements Initializable{
	@FXML
	private AnchorPane an;
	@FXML
	private TextField tfLogin;
	@FXML
	private TextField tfPassword;
	@FXML
	private ChoiceBox tfType;

	public void addUser(ActionEvent e)
	{
		//TODO sprawdzic długoś hasła
		//TODO zrobic okienko do zmiany hasła
		if(tfLogin.getText() == null || tfPassword.getText() == null || tfType.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR, "Wszystkie pola muszą zosta uzupełnione", ButtonType.CANCEL);
			alert.showAndWait();
		}
		User u = new User();
		u.setLogin(tfLogin.getText());
		u.setPassword(tfPassword.getText());
		u.setType(tfType.getValue().toString());
		UserDAO.getInstance().addUser(u);
		
		Stage stage = (Stage) an.getScene().getWindow();
		stage.close();
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tfType.getItems().add("worker");
		tfType.getItems().add("accountant");
		tfType.getItems().add("boss");
	}
}
