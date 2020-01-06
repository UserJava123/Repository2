package mycompany.invoice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.InvoiceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import DAO.BuyerDAO;
import DAO.InvoiceDAO;
import DAO.PositionDAO;
import entity.Buyer;
import entity.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;

public class BuyerListController implements Initializable{
	
	//Dodac odświeżanie widoku po edycji
	
	private ObservableList<entity.Buyer> buyers =
	        FXCollections.observableArrayList();
	
	private ObservableList<String> filters =
	        FXCollections.observableArrayList(); 
	
	@FXML
	private TableView<Buyer> tabBuyer;
	@FXML
	private TableColumn<Buyer,String> colName;
	@FXML
	private TableColumn<Buyer,String> colType;
	@FXML
	private TableColumn<Buyer,Hyperlink> colNip;
	@FXML
	private TableColumn<Buyer,String> colCity;
	@FXML
	private TableColumn<Buyer,String> colStreet;
	@FXML
	private TableColumn<Buyer,String> colPostCode;
	@FXML
	private TableColumn<Buyer,String> colState;
	@FXML
	private TableColumn<Buyer,String> colNrTel;
	@FXML
	private TableColumn<Buyer,String> colEmail;
	@FXML
	private TableColumn<Buyer,Hyperlink> colDelete;
	@FXML
	private Button btnDeleteFiltr;
	@FXML
	private Button btnFiltr;
	@FXML
	private ChoiceBox choiceFiltr;
	@FXML
	private TextField tfFiltr;

	public void initialize(URL location, ResourceBundle resources)
	{	

		colNip.setCellValueFactory(
                new PropertyValueFactory<Buyer,Hyperlink>("buyerLink"));
		
		colName.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("name"));
		
		colType.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("tnype"));
		
		colCity.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("city"));
		
		colStreet.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("street"));
		
		colPostCode.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("postCode"));
		
		colState.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("state"));
		
		colNrTel.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("nr_tel"));
		
		colEmail.setCellValueFactory(
                new PropertyValueFactory<Buyer,String>("email"));
		
		colDelete.setCellValueFactory(
                new PropertyValueFactory<Buyer,Hyperlink>("delete"));
		
		setBuyers(BuyerDAO.getInstance().getBuyers());
		tabBuyer.setItems(buyers);
		
		filters.add("Nazwa");
		filters.add("Nip/Pesel");
		
		choiceFiltr.setItems(filters);
	}
	
	public void setBuyers(List<Buyer> ar)
	{
		if(!buyers.isEmpty())
			buyers.clear();
		if(!ar.isEmpty()) {
			
			for(Buyer i: ar)
			{
				i.setBuyerLink();
				i.setDelete();
				i.setOnActionDelete(delete);
				i.setOnActionLink(edit);
				buyers.add(i);
			}
		}
	}
	
	@FXML
	public void deleteFiltr(ActionEvent e)
	{
		setBuyers(BuyerDAO.getInstance().getBuyers());
		tabBuyer.setItems(buyers);
	}
	
	@FXML
	public void filtr(ActionEvent e)
	{
		if(choiceFiltr.getValue() == "Nazwa")
		{
			setBuyers(BuyerDAO.getInstance().getBuyersByName(tfFiltr.getText()));
			tabBuyer.setItems(buyers);
		}
		else if(choiceFiltr.getValue() == "Nip/Pesel")
		{
			setBuyers(BuyerDAO.getInstance().getBuyersById(tfFiltr.getText()));
			tabBuyer.setItems(buyers);
		}	
		
	}
	
	public void openEdit(Buyer b) throws IOException
	{		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("addBuyer.fxml"));

		AnchorPane box = loader.load(); 

		AddBuyerController aic = loader.<AddBuyerController>getController();
		aic.initData(b);
			
		Scene scene = new Scene(box);			
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	EventHandler<ActionEvent> edit = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Hyperlink hl = (Hyperlink) event.getSource();
			Buyer b=BuyerDAO.getInstance().getBuyerById(hl.getId());
			System.out.println(b);
			try {
				openEdit(b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			try 
			{
				
				Hyperlink hl = (Hyperlink) event.getSource();
				String id = hl.getId().substring(1);
				Buyer b = BuyerDAO.getInstance().getBuyerById(id);
				if (!InvoiceDAO.getInstance().getInvoicesByBuyerId(id).isEmpty())
				{
					Alert alert = new Alert(AlertType.ERROR, "Nie możesz usunąc klienta jeżeli istnieją w bazie jego faktury!", ButtonType.CANCEL);
					alert.showAndWait();
					return;
				}
				BuyerDAO.getInstance().deleteBuyer(b);
				setBuyers(BuyerDAO.getInstance().getBuyers());
			}
			catch ( javax.persistence.RollbackException e)
			{
				Alert alert = new Alert(AlertType.ERROR, "Brak uprawnień do wykonania akcji", ButtonType.CANCEL);
				alert.showAndWait();
			}
		}
	};
}
