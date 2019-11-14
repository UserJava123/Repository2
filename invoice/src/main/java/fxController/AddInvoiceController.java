package fxController;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import DAO.BuyerDAO;
import DAO.InvoiceDAO;
import DAO.PositionDAO;
import DAO.SellerDAO;
import entity.Buyer;
import entity.Invoice;
import entity.Position;
import entity.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.Calculator;
import service.InvoiceService;
import service.PositionLinkDeleteService;
import javafx.scene.control.Label;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//dodac event handlery dla hyperlinkow
//
public class AddInvoiceController implements Initializable{
	
	PositionDAO positionDAO;
	BuyerDAO buyerDAO;
	InvoiceDAO invoiceDAO;
	SellerDAO sellerDAO;
	Invoice invoice;
	BigDecimal nr;

	boolean isNew = false;
	
	private ObservableList<entity.Position> positions =
	        FXCollections.observableArrayList();
	
	@FXML
	VBox vb;
	@FXML
	AnchorPane anchorPane;
	@FXML
	private Label errorLabel;
	@FXML
	private TableView<entity.Position> tablePositions;
	@FXML
	private TextField tfBuyerState;
	@FXML
	private TextField tfBuyerStreet;
	@FXML
	private TextField tfBuyerName;
	@FXML
	private TextField tfBuyerNip;
	@FXML
	private TextField tfBuyerCity;
	@FXML
	private DatePicker dpDateOfSale;
	@FXML
	private DatePicker dpDateOfInvoice;
	@FXML
	private DatePicker dpDateOfPayment;
	@FXML
	private TextField tfPlaceOfInvoice;
	@FXML
	private TextField tfBuyerCode;
	@FXML
	private TextField tfComments;
	@FXML
	private TextField tfInvoiceNr;
	@FXML
	private ChoiceBox cobBuyerType;
	@FXML
	private CheckBox cbPaid;
	@FXML
	private TableColumn<entity.Position,Integer> colLP;
	@FXML
	private TableColumn<entity.Position,String> colName;
	@FXML
	private TableColumn<entity.Position,String> colJM;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colQuantity;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colPrice;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colVat;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colNettoSum;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colVatSum;
	@FXML
	private TableColumn<entity.Position,BigDecimal> colBruttoSum;
	@FXML
	private TableColumn<entity.Position,Hyperlink> colDelete;
	@FXML
	private TextField tfProductName;
	@FXML
	private TextField tfJM;
	@FXML
	private TextField tfQuantity;
	@FXML
	private TextField tfPrice;
	@FXML
	private Button btnAddPosition;
	@FXML
	private Button btnSave;
	@FXML
	private Label lNettoSum;
	@FXML
	private Label lVatSum;
	@FXML
	private TextField tfVat;
	@FXML
	private Label lBruttoSum;
	@FXML
	private Label lSellerName,lSellerNip,lSellerAdres;

	
	
	public void initData(entity.Invoice i)
	{
		if (i==null) {
			nr = invoiceDAO.getMaxNumber();
			nr = InvoiceService.formatNewInvoiceNumber(nr);
			tfInvoiceNr.setText(InvoiceService.invoiceNumberToString(nr));
			this.invoice = new Invoice();
			System.out.println(nr);
			isNew = true;
		}
		else {
			Buyer b = i.getBuyer();
			for(Position p: i.getPositions())
			{
				positions.add(p);
			}
			tablePositions.setItems(positions);
			countSum();
			
			setBuyer(b);
			
			tfInvoiceNr.setText(InvoiceService.formatExistingInvoiceNumber(i.getNumber()));
			tfComments.setText(i.getComments());
			tfPlaceOfInvoice.setText(i.getPlaceOfInvoice());
			dpDateOfInvoice.setValue(i.getDateOfInvoice());
			dpDateOfPayment.setValue(i.getDateOfPayment());
			dpDateOfSale.setValue(i.getDateOfSale());
			
			for (Node node: vb.getChildren())
			{
				if (node instanceof TextField)
				{
					((TextField) node).setEditable(false);
				}
			}
		}
	}



	public void initialize(URL location, ResourceBundle resources) {
		
		buyerDAO = new BuyerDAO();
		invoiceDAO = new InvoiceDAO();
		positionDAO = new PositionDAO();
		sellerDAO = new SellerDAO();
		
		colLP.setCellValueFactory(
                new PropertyValueFactory<entity.Position, Integer>("nr"));
		
		colName.setCellValueFactory(
                new PropertyValueFactory<entity.Position,String>("name"));
		
		colJM.setCellValueFactory(
                new PropertyValueFactory<entity.Position,String>("unit"));
		
		colQuantity.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("quantity"));
		
		colPrice.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("price"));
		
		colVat.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("vat"));
		
		colNettoSum.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("nettoSum"));
		
		colBruttoSum.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("bruttoSum"));
		
		colVatSum.setCellValueFactory(
                new PropertyValueFactory<entity.Position,BigDecimal>("vatSum"));
		
		colDelete.setCellValueFactory(
                new PropertyValueFactory<entity.Position, Hyperlink>("linkDelete"));
		
		cobBuyerType.getItems().add("NIP");
		cobBuyerType.getItems().add("PESEL");
		setSellerLabels();
	}
	
	public void addPosition(ActionEvent e)
	{
		//tutaj dodaję na razie tylko do tabeli nie do bazy
		errorLabel.setText(" ");
		entity.Position p = new entity.Position();
		try {
			p.setName(tfProductName.getText());		
			p.setPrice(new BigDecimal(tfPrice.getText()));
			p.setUnit(tfJM.getText());
			p.setVat(new BigDecimal(tfVat.getText()));
			p.setInvoice(invoice);
			p.setQuantity(new BigDecimal(tfQuantity.getText()));
			p.setNr(positions.size()+1); 
			p.setLinkDelete();
			p.setOnLinkDelete(delete);
		}
		catch (java.lang.NumberFormatException exc)
		{
			errorLabel.setText("Niepoprawny format, upewnij się że używasz . zamiast ,!");
			System.out.println("Błąd parsowania");
			return;
		}
		positions.add(p);
		tablePositions.setItems(positions);
		countSum();
		
	}
	
	public void persist()
	{
		if(!isNew) return;
		errorLabel.setText(" ");
		for (Node tf: anchorPane.getChildren())
		{
			if ((tf instanceof TextField && ((TextField) tf).getText().equals("")) || positions==null)
			{
				errorLabel.setText("Brak niektórych danych!");
				return;
			}
		}
		Buyer b = new Buyer();
		b.setId(tfBuyerNip.getText());
		b.setName(tfBuyerName.getText());
		b.setState(tfBuyerState.getText());
		b.setCity(tfBuyerCity.getText());
		b.setStreet(tfBuyerStreet.getText());
		b.setPostCode(tfBuyerCode.getText());
		b.setType(cobBuyerType.getValue().toString());
		
		invoice.setNumber(nr.add(new BigDecimal(LocalDate.now().getYear())));
		invoice.setBuyer(b);
		invoice.setSeller(sellerDAO.getSeller());
		invoice.setAlreadyPaid(new BigDecimal(0));
		invoice.setComments(tfComments.getText());
		invoice.setDateOfInvoice(dpDateOfInvoice.getValue());
		invoice.setDateOfSale(dpDateOfSale.getValue());
		invoice.setDateOfPayment(dpDateOfPayment.getValue());
		invoice.setPaid(cbPaid.isSelected());
		invoice.setPlaceOfInvoice(tfPlaceOfInvoice.getText());
		invoice.setPositions(positions);
		
		buyerDAO.addBuyer(b);
		invoiceDAO.addInvoice(invoice);
		
		for(Position p: positions)
		{
			positionDAO.addPosition(p);
		}
		
		Stage stage = (Stage) btnSave.getScene().getWindow();
		stage.close();
	}
	
	

	EventHandler<ActionEvent> delete = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			if(!isNew) return;
			Hyperlink hl = (Hyperlink) event.getSource();
			try {
				int i = PositionLinkDeleteService.findPositionByLink(positions,hl);
				PositionLinkDeleteService.matchPositionsNumbers(positions,i);
				countSum();
			}
			catch (NullPointerException e)
			{
				return;
			}
		}	
	};
	
	public void countSum()
	{
		lNettoSum.setText(Calculator.countNettoSum(positions).toString());
		lBruttoSum.setText(Calculator.countBruttoSum(positions).toString());
		lVatSum.setText(Calculator.countVatSum(positions).toString());
	}
	
	public void setSellerLabels(){
		Seller s = sellerDAO.getSeller();
		if(s!=null) {
		lSellerName.setText(s.getName());
		lSellerNip.setText(s.getId());
		lSellerAdres.setText(s.getCity() +  ", "+s.getStreet() + ", " + s.getPostCode());
		}
	}
	
	public void searchBuyer(KeyEvent ke)
	{
		if(ke.getCode().equals(KeyCode.ENTER)) {
			if(!tfBuyerName.getText().equals("")) {
				Buyer b = buyerDAO.getBuyerByName(tfBuyerName.getText());
				setBuyer(b);
			}
			else {
				clearBuyer();
			}
		}
	}
	
	public void setBuyer(Buyer b)
	{
		tfBuyerName.setText(b.getName());
		tfBuyerNip.setText(b.getId());
		tfBuyerCity.setText(b.getCity());
		tfBuyerStreet.setText(b.getStreet());
		tfBuyerCode.setText(b.getPostCode());
		tfBuyerState.setText(b.getState());
		cobBuyerType.setValue(b.getType());
	}
	
	public void clearBuyer()
	{
		tfBuyerName.setText("");
		tfBuyerNip.setText("");
		tfBuyerCity.setText("");
		tfBuyerStreet.setText("");
		tfBuyerCode.setText("");
		tfBuyerState.setText("");
	}
	
}
