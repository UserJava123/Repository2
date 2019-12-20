package fxController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.SellerDAO;
import entity.Buyer;
import entity.Invoice;
import entity.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditSellerController implements Initializable{
	
	SellerDAO sellerDAO;
	
	@FXML
	private AnchorPane apEdit;
	@FXML
	private TextField tfSellerName;
	@FXML
	private TextField tfSellerNip;
	@FXML
	private TextField tfSellerCity;
	@FXML
	private TextField tfSellerStreet;
	@FXML
	private TextField tfSellerCode;
	@FXML
	private TextField tfSellerState;
	@FXML
	private Button btnSave;

	public void initialize(URL location, ResourceBundle resources) {
		sellerDAO = SellerDAO.getInstance();
		if( sellerDAO.getSeller()!= null)
		{
			Seller s = sellerDAO.getSeller();
			tfSellerName.setText(s.getName());
			tfSellerNip.setText(s.getId());
			tfSellerCity.setText(s.getCity());
			tfSellerStreet.setText(s.getStreet());
			tfSellerCode.setText(s.getPostCode());
			tfSellerState.setText(s.getState());
		}
	}
	
	public void editSeller(ActionEvent e)
	{
		Seller s = new Seller();
		s.setName(tfSellerName.getText());
		s.setId(tfSellerNip.getText());
		s.setCity(tfSellerCity.getText());
		s.setStreet(tfSellerStreet.getText());
		s.setType("company");
		s.setState(tfSellerState.getText());
		s.setPostCode(tfSellerCode.getText());
		
		sellerDAO.setSeller(s);
		Stage stage = (Stage) btnSave.getScene().getWindow();
		stage.close();
	}
}
