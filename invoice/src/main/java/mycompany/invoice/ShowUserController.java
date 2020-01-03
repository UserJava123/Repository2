package mycompany.invoice;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DAO.UserDAO;
import entity.Invoice;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;

public class ShowUserController implements Initializable{
	
	private ObservableList<entity.User> users =
	        FXCollections.observableArrayList();
	
	@FXML
	private TableView userTable;
	@FXML
	private TableColumn<User,String> colLogin;
	@FXML
	private TableColumn<User,String> colCompability;
	@FXML
	private TableColumn<User,Hyperlink> colDelete;

	private User user;
	
	public void initialize(URL location, ResourceBundle resources) {
				
		colLogin.setCellValueFactory(
                new PropertyValueFactory<User,String>("login"));
		
		colCompability.setCellValueFactory(
                new PropertyValueFactory<User,String>("type"));
		
		colDelete.setCellValueFactory(
                new PropertyValueFactory<User,Hyperlink>("deleteLink"));
		
		setUsers(UserDAO.getInstance().getUsers());
		userTable.setItems(users);
	}
	
	public void setUsers(List<User> ar)
	{
		if(!users.isEmpty())
			users.clear();
		if(!ar.isEmpty()) {
			
			for(User u: ar)
			{
				u.setDeleteLink();
				u.setOnActionDelete(delete);
				users.add(u);
			}
		}
	}
	
	public void setUser(User u)
	{
		this.user = u;
	}
	
	EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			try 
			{
				Hyperlink hl = (Hyperlink) event.getSource();
				String id = hl.getId().substring(1);
				User u = UserDAO.getInstance().getUserByLogin(id);
				if(u.getType().equals("boss"))
				{
					Alert alert = new Alert(AlertType.ERROR, "Nie możesz usunąc konta szefa", ButtonType.CANCEL);
					alert.showAndWait();
				}
				UserDAO.getInstance().deleteUser(u);
				setUsers(UserDAO.getInstance().getUsers());
			}
			catch ( javax.persistence.RollbackException e)
			{
				Alert alert = new Alert(AlertType.ERROR, "Brak uprawnień do wykonania akcji", ButtonType.CANCEL);
				alert.showAndWait();
			}
		}
	};
}
