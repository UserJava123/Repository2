package fxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.SellerDAO;
import entity.Seller;
import javafx.event.ActionEvent;

public class MainViewController implements Initializable{
	
	SellerDAO sellerDAO;
	@FXML
	BorderPane bpIndex;
	@FXML
	private Button btnEditSeller;
	@FXML
	private Button btnAddInvoice;
	@FXML
	private MenuItem miSeller;
	@FXML
	private Label lName;
	@FXML
	private Label lNip;
	@FXML
	private Label lAdres;
	private Seller seller;

	// Event Listener on Button[#btnAddInvoice].onAction
	@FXML
	public void addInvoice(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("newInvoice.fxml"));

		VBox box = loader.load(); 

		AddInvoiceController aic = loader.<AddInvoiceController>getController();
		aic.initData(null);
		
		Scene scene = new Scene(box);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void openEditSeller(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("newSeller.fxml"));

		AnchorPane box = loader.load(); 
		
		Scene scene = new Scene(box);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	public void setSeller(MouseEvent e)
	{
		lName.setText(seller.getName());
		lNip.setText("NIP: " + seller.getId());
		lAdres.setText(seller.getCity() +",\n" + seller.getStreet() +",\n" +seller.getPostCode());
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sellerDAO = new SellerDAO();
		seller = sellerDAO.getSeller();
		setSeller(null);
	}
}
